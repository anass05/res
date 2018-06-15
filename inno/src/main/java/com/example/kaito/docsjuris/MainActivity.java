package com.example.kaito.docsjuris;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.example.kaito.docsjuris.helpers.DataHandler;
import com.example.kaito.docsjuris.helpers.ImageDownloader;
import com.example.kaito.docsjuris.fragments.DocumentsFragment;
import com.example.kaito.docsjuris.fragments.MainFragment;
import com.example.kaito.docsjuris.fragments.MessangerFragment;
import com.example.kaito.docsjuris.helpers.VolleyRequest;
import com.example.kaito.docsjuris.map.MapFragment;
import com.example.kaito.docsjuris.models.SnackBarNotification;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import it.sephiroth.android.library.bottomnavigation.BadgeProvider;
import it.sephiroth.android.library.bottomnavigation.BottomNavigation;

public class MainActivity extends AppCompatActivity implements BottomNavigation.OnMenuItemSelectionListener,
        VolleyRequest.RequestExecutedListener, ImageDownloader.DownloadImageListener {


    /**
     * The {@link ViewPager} that will host the section contents.
     */

    private FloatingActionButton fab;
    private BottomNavigation bottomNavigationView;
    private MaterialSearchView searchView;
    private CircleImageView profileImage;
    private SharedPreferences prefs;
    private static final int REQUEST_NOTIFICATIONS_BADGE = 0;
    private static final int REQUEST_MESSAGES_BADGE = 1;
    private ArrayList<SnackBarNotification> snackBarNotificationsQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Log.i("in", "image = " + prefs.getString("avatar", ""));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        snackBarNotificationsQueue = new ArrayList<>();

        profileImage = findViewById(R.id.profile);

        if (!prefs.getString("avatar", "").equals("") && DataHandler.PROFILE_IMAGE == null)
            new ImageDownloader(profileImage).execute(BuildConfig.API_URL + prefs.getString("avatar", ""));
        else
            profileImage.setImageBitmap(DataHandler.PROFILE_IMAGE);

        profileImage.setOnClickListener(e -> {
            Intent i = new Intent(this, ProfileActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(i);
        });


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, MainFragment.newInstance());
        transaction.commit();


        fab = findViewById(R.id.fab);
//        fab.setOnClickListener(view -> toast());

        bottomNavigationView = findViewById(R.id.BottomNavigation);


        bottomNavigationView.setOnMenuItemClickListener(this);


        searchView = findViewById(R.id.search_view);
        searchView.setVoiceSearch(false);
        //searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setEllipsize(true);
        searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Snackbar.make(findViewById(R.id.frame_layout), "Query: " + query, Snackbar.LENGTH_LONG)
                //       .show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });

        searchView.setOnItemClickListener((parent, view, position, id) -> {
            String query = (String) parent.getItemAtPosition(position);
            searchView.closeSearch();
            //Snackbar.make(findViewById(R.id.container), "Query: " + query, Snackbar.LENGTH_LONG)
            //      .show();
        });

        showBadges();
        startPusher();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (prefs.getBoolean("imageUpdated", false)) {
            if (!prefs.getString("avatar", "").equals(""))
                new ImageDownloader(profileImage).execute(BuildConfig.API_URL + prefs.getString("avatar", ""));
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("imageUpdated", false);
            editor.apply();
        }
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.item1);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.item2) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMenuItemSelect(final int itemId, final int position, final boolean fromUser) {
        Fragment selectedFragment = null;
        switch (position) {
            case 0:
                selectedFragment = MainFragment.newInstance();
                break;
            case 1:
                selectedFragment = MapFragment.newInstance();
                break;
            case 2:
                selectedFragment = DocumentsFragment.newInstance();
                break;
            case 3:
                selectedFragment = MessangerFragment.newInstance();
                break;
            case 4:
                selectedFragment = MessangerFragment.newInstance();
                break;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, selectedFragment);
        transaction.commit();
    }

    @Override
    public void onMenuItemReselect(@IdRes final int itemId, final int position, final boolean fromUser) {
        //Toast.makeText(getApplicationContext(), ""+position, Toast.LENGTH_LONG).show();
    }


    public void showBadges() {
        new VolleyRequest(BuildConfig.API_URL + "api/notifications/clients/showbadge/" + prefs.getInt("id", 0), Request.Method.GET,
                null, getApplicationContext(), this, REQUEST_NOTIFICATIONS_BADGE).execute();

        new VolleyRequest(BuildConfig.API_URL + "api/chatrooms/clients/showbadge/" + prefs.getInt("id", 0), Request.Method.GET,
                null, getApplicationContext(), this, REQUEST_MESSAGES_BADGE).execute();

    }

    @Override
    public void onRequestDone(String response, int requestCode) {
        if (requestCode == REQUEST_NOTIFICATIONS_BADGE) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getBoolean("show")) {
                    final BadgeProvider provider1 = bottomNavigationView.getBadgeProvider();
                    provider1.show(R.id.bbn_item2);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == REQUEST_MESSAGES_BADGE) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getBoolean("show")) {
                    final BadgeProvider provider1 = bottomNavigationView.getBadgeProvider();
                    provider1.show(R.id.action4);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void startPusher() {
        PusherOptions options = new PusherOptions();
        options.setCluster(BuildConfig.PUSHER_CLUSTER);
        Pusher pusher = new Pusher(BuildConfig.PUSHER_API_KEY, options);
        Channel channel = pusher.subscribe("client-" + prefs.getInt("id", 0));
        channel.bind("demande-viewed", (channelName, eventName, data) -> demandeViewedToast(data));
        channel.bind("demande-commented", (channelName, eventName, data) -> demandeCommentedToast(data));
        channel.bind("demande-treated", (channelName, eventName, data) -> demandeTreatedToast(data));

        pusher.connect();
    }

    private void demandeViewedToast(String data) {
        runOnUiThread(() -> {
            try {
                JSONObject jsonObject = new JSONObject(data);
                System.out.println(data);
                SnackBarNotification sn = new SnackBarNotification(
                        jsonObject.getString("whoViewedName") + " a juste consulté votre demande");
                snackBarNotificationsQueue.add(sn);
                new ImageDownloader(null,sn.getRequestCode(),this)
                        .execute(BuildConfig.API_URL+"uploads/assets/"+jsonObject.getString("avatar"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    private void demandeCommentedToast(String data) {
        runOnUiThread(() -> {
            try {
                JSONObject jsonObject = new JSONObject(data);
                SnackBarNotification sn = new SnackBarNotification(
                        jsonObject.getString("whoCommentedName") + " a juste commenté votre demande");
                snackBarNotificationsQueue.add(sn);
                new ImageDownloader(null,sn.getRequestCode(),this)
                        .execute(BuildConfig.API_URL+jsonObject.getString("avatar"));
                final BadgeProvider provider1 = bottomNavigationView.getBadgeProvider();
                provider1.show(R.id.bbn_item2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    private void demandeTreatedToast(String data) {
        runOnUiThread(() -> {
            try {
                JSONObject jsonObject = new JSONObject(data);
                //Toast.makeText(this, jsonObject.getString("whoTreatedName") + " a just traité votre demande", Toast.LENGTH_SHORT).show();
                SnackBarNotification sn = new SnackBarNotification(
                        jsonObject.getString("whoTreatedName") + " a juste traité votre demande");
                snackBarNotificationsQueue.add(sn);
                new ImageDownloader(null,sn.getRequestCode(),this)
                        .execute(BuildConfig.API_URL+jsonObject.getString("avatar"));
                final BadgeProvider provider1 = bottomNavigationView.getBadgeProvider();
                provider1.show(R.id.bbn_item2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    public void toast(Bitmap avatar, String message) {
        System.out.println("sdsd"+message);

        View view = getLayoutInflater().inflate(R.layout.snackbar_layout, null);
        Snackbar snackbar = Snackbar.make(findViewById(R.id.BottomNavigation), "", Snackbar.LENGTH_SHORT);

        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        TextView textView =  layout.findViewById(android.support.design.R.id.snackbar_text);
        textView.setVisibility(View.INVISIBLE);

        CircleImageView image = view.findViewById(R.id.image);
        image.setImageBitmap(avatar);
        TextView text = view.findViewById(R.id.text);
        text.setText(message);

        layout.setPadding(0, 0, 0, 0);
        layout.addView(view, 0);
        snackbar.show();

    }

    @Override
    public void onDownloadImageDone(int responseCode, Bitmap result) {
        String message = "";
        for (SnackBarNotification sn : snackBarNotificationsQueue) {
            if (sn.getRequestCode() == responseCode) {
                message = sn.getMessage();
                snackBarNotificationsQueue.remove(sn);
                break;
            }
        }
        toast(result, message);
    }
}
