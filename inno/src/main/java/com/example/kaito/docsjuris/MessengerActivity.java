package com.example.kaito.docsjuris;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.example.kaito.docsjuris.adapters.ChatAdapter;
import com.example.kaito.docsjuris.helpers.ImageDownloader;
import com.example.kaito.docsjuris.helpers.VolleyRequest;
import com.example.kaito.docsjuris.models.Message;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessengerActivity extends AppCompatActivity implements ImageDownloader.DownloadImageListener, VolleyRequest.RequestExecutedListener {

    private CircleImageView avatar;
    private TextView name;
    private int chatRoomId;
    private Bitmap profile;
    private int page;
    private ArrayList<Message> messages;
    private ArrayList<Message> sentMessages;
    private ChatAdapter adapter;
    private RecyclerView recyclerView;
    private EditText messageInput;
    private LinearLayout noMessages;
    private boolean gotNewMessages;
    private boolean hasMessages;
    private ProgressBar loadingMessages;
    private static final int REQUEST_MESSAGES_CODE = 0;
    private static final int SEND_MESSAGE_CODE = 1;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);

        Toolbar toolbar = findViewById(R.id.toolbar_chat);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        page = 1;
        avatar = toolbar.findViewById(R.id.avatar);
        name = toolbar.findViewById(R.id.name);
        recyclerView = findViewById(R.id.chat_recycler);
        messageInput = findViewById(R.id.message_input);
        noMessages = findViewById(R.id.no_messages);
        loadingMessages = findViewById(R.id.loading_messages);
        messages = new ArrayList<>();
        sentMessages = new ArrayList<>();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        toolbar.findViewById(R.id.back).setOnClickListener(e -> onBackPressed());
        findViewById(R.id.send).setOnClickListener(e -> addMessage());

        new ImageDownloader(avatar, 0, this).execute(getIntent().getStringExtra("avatar"));
        name.setText(getIntent().getStringExtra("name"));
        chatRoomId = getIntent().getIntExtra("chatRoomId", 0);

        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        manager.setReverseLayout(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new ChatAdapter(this, messages, profile);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (manager.findLastCompletelyVisibleItemPosition() == adapter.getItemCount() - 1 && gotNewMessages) {
                    gotNewMessages = false;
                    toggleLoading(true);
                    getMessages(++page);
                    System.out.println("loading for page " + page);
                }
            }
        });

        getMessages(page);
        startPusher();
    }

    @Override
    public void onDownloadImageDone(int responseCode, Bitmap result) {
        profile = result;
        if (adapter != null)
            adapter.setAvatar(result);
    }

    public void getMessages(int pageNumber) {
        new VolleyRequest(BuildConfig.API_URL + "api/chatrooms/messages/" + chatRoomId + "?page=" + pageNumber, Request.Method.GET,
                null, getApplicationContext(), this, REQUEST_MESSAGES_CODE).execute();
    }

    @Override
    public void onRequestDone(String response, int requestCode) {
        if (requestCode == REQUEST_MESSAGES_CODE)
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray messagesArray = jsonObject.getJSONArray("data");
                int myId = prefs.getInt("id", 0);
                if (messagesArray.length() > 0 || hasMessages) {
                    hasMessages = true;
                    noMessages.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    noMessages.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
                messages = new ArrayList<>();
                for (int i = 0; i < messagesArray.length(); i++) {
                    JSONObject m = messagesArray.getJSONObject(i);
                    messages.add(new Message(
                            m.getInt("id"),
                            m.getInt("sender_id") == myId,
                            m.getString("content"),
                            formatMessageDate(m.getString("created_at")),
                            0
                    ));
                    gotNewMessages = true;
                }
                toggleLoading(false);
                adapter.loadOldMessages(messages);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        if (requestCode == SEND_MESSAGE_CODE) {
            System.out.println(response);
            try {
                JSONObject obj = new JSONObject(response);
                int messageRef = obj.getJSONObject("data").getInt("android_reference");
                for (Message m : sentMessages) {
                    if (m.getReference() == messageRef) {
                        m.setDate(formatMessageDate(obj.getJSONObject("data").getString("created_at")));
                        m.setDrawable(0);
                        m.setSound(R.raw.message_sent);
                        adapter.notifyDataSetChanged();
                        sentMessages.remove(m);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public String formatMessageDate(String date) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRENCH);
        Formatter fmt = new Formatter();
        try {
            if (date != null)
                c.setTime(sdf.parse(date));
            return fmt.format("%tH:%tM", c, c).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void addMessage() {
        if (invalidText())
            return;

        if (!hasMessages)
            goneAnimation();
        hasMessages = true;

        Message m = new Message(
                0,
                true,
                messageInput.getText().toString(),
                "",
                new Random().nextInt(Integer.MAX_VALUE),
                R.drawable.baseline_alarm_white_24
        );
        messageInput.setText("");
        sendMessage(m);
        sentMessages.add(m);
        adapter.writeMessage(m);
        recyclerView.smoothScrollToPosition(0);
        /*new Handler().postDelayed(() -> {
            m.setDate(formatMessageDate(null));
            m.setDrawable(0);
            adapter.notifyDataSetChanged();
        }, 1500);*/
    }

    public void goneAnimation() {
        Animation fadeOut = new ScaleAnimation(
                1f, 0f,
                1f, 0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        fadeOut.setFillAfter(true);
        fadeOut.setInterpolator(new AnticipateInterpolator());
        fadeOut.setDuration(950);

        AnimationSet animation = new AnimationSet(false);
        animation.addAnimation(fadeOut);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                noMessages.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        noMessages.setAnimation(animation);
    }

    public void toggleLoading(boolean showLoading) {
        if (showLoading) {
            loadingMessages.setVisibility(View.VISIBLE);
        } else {
            loadingMessages.setVisibility(View.INVISIBLE);
        }
    }

    public void sendMessage(Message m) {
        HashMap<String, String> params = new HashMap<>();
        params.put("seen", "0");
        params.put("chatroom_id", chatRoomId + "");
        params.put("sender_type", "client");
        params.put("sender_id", prefs.getInt("id", 0) + "");
        params.put("content", m.getContent());
        params.put("android_reference", m.getReference() + "");

        new VolleyRequest(BuildConfig.API_URL + "api/messages", Request.Method.POST,
                params, getApplicationContext(), this, null, SEND_MESSAGE_CODE).execute();
    }

    public boolean invalidText() {
        if (messageInput.getText().toString().length() == 0)
            return true;
        return false;
    }


    private void startPusher() {
        PusherOptions options = new PusherOptions();
        options.setCluster(BuildConfig.PUSHER_CLUSTER);
        Pusher pusher = new Pusher(BuildConfig.PUSHER_API_KEY, options);
        Channel channel = pusher.subscribe("chatroom-" + chatRoomId);
        channel.bind("chatting_event", (channelName, eventName, data) -> {
            runOnUiThread(() -> {
                try {
                    JSONObject m = new JSONObject(data);
                    //i'm not the sender
                    if (m.getInt("sender_id") != prefs.getInt("id", 0)) {
                        adapter.writeMessage(new Message(
                                m.getInt("id"),
                                false,
                                m.getString("content"),
                                formatMessageDate(m.getString("created_at")),
                                0
                        ));
                        recyclerView.smoothScrollToPosition(0);
                        hasMessages = true;
                        noMessages.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        new VolleyRequest(BuildConfig.API_URL + "api/chatrooms/clients/seen/" + chatRoomId, Request.Method.GET,
                                null, getApplicationContext(), null, 0).execute();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });

        });
        System.out.println("pusher running on channel chatroom-" + chatRoomId);
        pusher.connect();
    }

}
