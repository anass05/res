package com.example.kaito.docsjuris.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class VolleyRequest {
    private Context context;
    private String BASE_URL;
    private int methode, requestCode;
    private HashMap<String, String> params;
    private SharedPreferences prefs;
    private RequestExecutedListener caller;
    private RequestErrorListener errorCaller;

    public VolleyRequest(String BASE_URL, int methode, HashMap<String, String> params,
                         Context context, RequestExecutedListener caller, int requestCode) {
        this.context = context;
        this.BASE_URL = BASE_URL;
        this.methode = methode;
        this.params = params;
        this.caller = caller;
        this.requestCode = requestCode;
        this.prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public VolleyRequest(String BASE_URL, int methode, HashMap<String, String> params,
                         Context context, RequestExecutedListener caller, RequestErrorListener errorCaller, int requestCode) {
        this(BASE_URL, methode, params, context, caller, requestCode);
        this.errorCaller = errorCaller;
    }

    public void execute() {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(methode, BASE_URL,
                response -> {
                    System.out.println(response);
                    if (caller != null)
                        caller.onRequestDone(response, requestCode);
                }
                , error -> {
            if (errorCaller != null)
                errorCaller.onError(error.getMessage(), requestCode);
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                if (!prefs.getString("token", "").equals(""))
                    params.put("Authorization", "Bearer " + prefs.getString("token", ""));
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public interface RequestExecutedListener {
        void onRequestDone(String response, int requestCode);
    }

    public interface RequestErrorListener {
        void onError(String response, int requestCode);
    }

}
