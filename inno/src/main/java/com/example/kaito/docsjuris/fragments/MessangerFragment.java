package com.example.kaito.docsjuris.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.kaito.docsjuris.BuildConfig;
import com.example.kaito.docsjuris.MessengerActivity;
import com.example.kaito.docsjuris.R;
import com.example.kaito.docsjuris.adapters.ChatRoomAdapter;
import com.example.kaito.docsjuris.helpers.VolleyRequest;
import com.example.kaito.docsjuris.models.ChatRoom;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import java.util.Locale;

/**
 * Created by KaiTo on 4/28/2018.
 */

public class MessangerFragment extends Fragment implements VolleyRequest.RequestExecutedListener {

    private View rootView;
    private SwipeMenuListView listView;
    private ArrayList<ChatRoom> chatRooms;
    private ChatRoomAdapter listAdapter;
    private Context context;
    private SharedPreferences prefs;

    public static MessangerFragment newInstance() {
        return new MessangerFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new VolleyRequest(BuildConfig.API_URL + "api/chatrooms/clients/" + prefs.getInt("id", 0), Request.Method.GET,
                null, getContext(), this, 0).execute();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_messenger, container, false);
        context = rootView.getContext();
        rootView.findViewById(R.id.container).setOnClickListener(e -> context.startActivity(new Intent(context, MessengerActivity.class)));
        return rootView;
    }


    SwipeMenuCreator creator = new SwipeMenuCreator() {
        @Override
        public void create(SwipeMenu menu) {


            // create "delete" item
            SwipeMenuItem deleteItem = new SwipeMenuItem(rootView.getContext());
            // set item background
            deleteItem.setBackground(new ColorDrawable(Color.parseColor("#F45557")));
            // set item width
            deleteItem.setWidth(150);

            deleteItem.setTitle("Delete");
            deleteItem.setTitleColor(Color.WHITE);
            deleteItem.setTitleSize(15);
            // add to menu
            menu.addMenuItem(deleteItem);
        }
    };

    @Override
    public void onRequestDone(String response, int requestCode) {
        chatRooms = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray array = jsonObject.getJSONArray("data");
            for (int i = 0; i < array.length(); i++) {

                JSONObject jcr = array.getJSONObject(i);
                chatRooms.add(new ChatRoom(jcr.getInt("id"),
                        formatDate(jcr.getString("last_action")),
                        jcr.getInt("total_unseen"),
                        BuildConfig.API_URL + "" + jcr.getJSONObject("expert").getString("avatar"),
                        jcr.getString("preview"),
                        jcr.getJSONObject("expert").getString("nom") + " " + jcr.getJSONObject("expert").getString("prenom")));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        fillListView();

    }

    public String formatDate(String date) {
        try {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRENCH);

            c.setTime(sdf.parse(date));
            Formatter fmt = new Formatter();
            switch (isToday(c)) {
                case -2:
                    SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH);
                    return format1.format(c.getTime());
                case 0:
                    return fmt.format("%tH:%tM", c, c).toString();
                case -1:
                    return "yesterday";

            }


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * return 0 if today
     * return -1 if yesterday
     * return -2 if other day
     */
    public int isToday(Calendar date) {
        Calendar now = Calendar.getInstance();
        //equal
        if (now.get(Calendar.ERA) == date.get(Calendar.ERA) &&
                now.get(Calendar.YEAR) == date.get(Calendar.YEAR) &&
                now.get(Calendar.DAY_OF_YEAR) == date.get(Calendar.DAY_OF_YEAR))
            return 0;

        now.add(Calendar.DAY_OF_YEAR, -1);
        if (now.get(Calendar.YEAR) == date.get(Calendar.YEAR)
                && now.get(Calendar.DAY_OF_YEAR) == date.get(Calendar.DAY_OF_YEAR))
            return -1;

        return -2;

    }


    public void fillListView() {

        listView = rootView.findViewById(R.id.listview);
        listView.setDivider(null);
        listAdapter = new ChatRoomAdapter(getActivity(), chatRooms);
        listView.setAdapter(listAdapter);
        listView.setMenuCreator(creator);
        listView.setOnLongClickListener(e -> {
            Toast.makeText(rootView.getContext(), "Hello", Toast.LENGTH_SHORT).show();
            return true;
        });

        listView.setOnMenuItemClickListener((position, menu, index) -> {
            switch (index) {
                case 0:
                    Toast.makeText(rootView.getContext(), "Delete", Toast.LENGTH_SHORT).show();
                    Log.e("item", String.valueOf(listView.getAdapter().getItem(position)));
                    Log.e("name", String.valueOf(chatRooms.get(position).getName()));

                    chatRooms.remove(position);
                    listAdapter.notifyDataSetChanged();
                    break;
                case 1:
                    // delete
                    break;
            }
            // false : close the menu; true : not close the menu
            return false;
        });


    }
}