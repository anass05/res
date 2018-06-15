package com.example.kaito.docsjuris;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kaito.docsjuris.helpers.DataHandler;
import com.example.kaito.docsjuris.helpers.ImageDownloader;
import com.example.kaito.docsjuris.helpers.VolleyRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements VolleyRequest.RequestExecutedListener,
        VolleyRequest.RequestErrorListener, ImageDownloader.DownloadImageListener {

    private EditText mEmailView;
    private EditText mPasswordView;
    private SharedPreferences prefs;
    private final int LOGIN_REQUEST_CODE = 1;
    private final int GETINFOS_REQUEST_CODE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        setContentView(R.layout.activity_login);
        mEmailView = findViewById(R.id.input_email);
        mPasswordView = findViewById(R.id.input_password);

    }

    public void loginn(View v) {
        HashMap<String, String> params = new HashMap<>();
        params.put("client_id", "3");
        params.put("client_secret", BuildConfig.CLIENT_SECRET);
        params.put("grant_type", "password");
        params.put("username", mEmailView.getText().toString());
        params.put("password", mPasswordView.getText().toString());
        params.put("theNewProvider", "client");

        new VolleyRequest(BuildConfig.API_URL + "oauth/token", Request.Method.POST,
                params, getApplicationContext(), this,this, LOGIN_REQUEST_CODE).execute();
    }

    @Override
    public void onRequestDone(String response, int requestCode) {
        if (requestCode == LOGIN_REQUEST_CODE) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                prefs = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("token", jsonObject.getString("access_token"));
                editor.apply();
                new VolleyRequest(BuildConfig.API_URL + "api/client", Request.Method.GET,
                        null, getApplicationContext(), this, GETINFOS_REQUEST_CODE).execute();
            } catch (JSONException e) {
                e.printStackTrace();
                //startActivity(new Intent(this, LoginActivity.class));
                Toast.makeText(this, " une erreur c'est produite veuillez réessayer ultérieurement", Toast.LENGTH_LONG).show();
                finish();
            }
        }

        if (requestCode == GETINFOS_REQUEST_CODE) {
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(response);
                saveUserStuff(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
//                startActivity(new Intent(this, LoginActivity.class));
                Toast.makeText(this, " une erreur c'est produite veuillez réessayer ultérieurement", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    public void saveUserStuff(JSONObject jsonObject) {
        try {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("token", token);
            editor.putInt("id", jsonObject.getInt("id"));
            editor.putString("nom", jsonObject.getString("nom"));
            editor.putString("prenom", jsonObject.getString("prenom"));
            editor.putString("avatar", jsonObject.getString("avatar"));
            editor.putString("profession", jsonObject.getString("profession"));
            editor.putString("ncin", jsonObject.getString("ncin"));
            editor.putString("npassport", jsonObject.getString("npassport"));
            editor.putString("adresse", jsonObject.getString("adresse"));
            editor.putString("ville", jsonObject.getString("ville"));
            editor.putString("tele", jsonObject.getString("tele"));
            editor.putString("date_naissance", jsonObject.getString("date_naissance"));
            editor.putString("ville_naissance", jsonObject.getString("ville_naissance"));
            editor.putString("email", jsonObject.getString("email"));
            editor.putString("type_compte", jsonObject.getString("type_compte"));
            editor.apply();
            if (!prefs.getString("avatar", "").equals("") && DataHandler.PROFILE_IMAGE == null)
                new ImageDownloader(null, 0, this).execute(BuildConfig.API_URL + prefs.getString("avatar", ""));
            else {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, " Une erreur c'est produite veuillez réessayer ultérieurement", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDownloadImageDone(int responseCode, Bitmap result) {
        System.out.println("Image downloaded");
        DataHandler.PROFILE_IMAGE = result;
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void openRegisterActivity(View v){
        startActivity(new Intent(this,RegisterActivity.class));
        finish();
    }

    @Override
    public void onError(String response, int requestCode) {
        Toast.makeText(this, " Email ou mot de passe incorrect ", Toast.LENGTH_LONG).show();
    }
}