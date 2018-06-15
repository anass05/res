package com.example.kaito.docsjuris;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kaito.docsjuris.helpers.DataHandler;
import com.example.kaito.docsjuris.helpers.ImageDownloader;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    private CircleImageView profileImage;
    private TextView name, profession, demandes, demandesNumber, typeCompte;
    private SharedPreferences prefs;
    private Integer totalDemandes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        totalDemandes = 0;
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        profileImage = findViewById(R.id.imageprofile);
        name = findViewById(R.id.name);
        profession = findViewById(R.id.profession);
        demandes = findViewById(R.id.demandes);
        demandesNumber = findViewById(R.id.demandes_number);
        typeCompte = findViewById(R.id.type_compte);

        fillData();

    }

    public void fillData(){

        name.setText(prefs.getString("prenom", "") + " " + prefs.getString("nom", ""));
        profession.setText(prefs.getString("profession", ""));
        typeCompte.setText(prefs.getString("type_compte", "Free").substring(0, 1).toUpperCase() + prefs.getString("type_compte", "Free").substring(1));

        if (!prefs.getString("avatar", "").equals("") && DataHandler.PROFILE_IMAGE == null)
            new ImageDownloader(profileImage).execute(BuildConfig.API_URL + prefs.getString("avatar", ""));
        else {
            profileImage.setImageBitmap(DataHandler.PROFILE_IMAGE);
            System.out.println("loading from cache");
        }

        profileImage.bringToFront();
        getTotalDemandes();

        findViewById(R.id.edit).setOnClickListener(e -> startActivity(new Intent(this, EditProfileActivity.class)));
        getTotalDemandes();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fillData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void getTotalDemandes() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = BuildConfig.API_URL + "api/demandes/client/total/" + prefs.getInt("id", 0);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    totalDemandes = Integer.parseInt(response);
                    demandesNumber.setText("" + totalDemandes);
                    if (totalDemandes < 2)
                        demandes.setText("Demande");
                }, error -> System.out.println(error)) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + prefs.getString("token", ""));
                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
