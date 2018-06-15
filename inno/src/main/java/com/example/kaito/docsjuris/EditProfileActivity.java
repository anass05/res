package com.example.kaito.docsjuris;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kaito.docsjuris.helpers.DataHandler;
import com.example.kaito.docsjuris.helpers.ImageDownloader;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {
    private CircleImageView profileImage;
    private TextView adresse, ville, telephone, save;
    private SharedPreferences prefs;
    private int serverResponseCode;
    private ProgressDialog dialog;
    private boolean imageDone, infosDone;
    public static String BASE_URL;
    private String filePath;
    private Uri mCropImageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        profileImage = findViewById(R.id.imageprofile);
        adresse = findViewById(R.id.adresse);
        ville = findViewById(R.id.ville);
        telephone = findViewById(R.id.number);
        save = findViewById(R.id.save);

        BASE_URL = BuildConfig.API_URL + "api/clients/upload/avatar/" + prefs.getInt("id", 0);

        if (!prefs.getString("avatar", "").equals("") && DataHandler.PROFILE_IMAGE == null)
            new ImageDownloader(profileImage).execute(BuildConfig.API_URL + prefs.getString("avatar", ""));
        else
            profileImage.setImageBitmap(DataHandler.PROFILE_IMAGE);

        ville.setText(prefs.getString("ville", ""));
        adresse.setText(prefs.getString("adresse", ""));
        telephone.setText(prefs.getString("tele", ""));

        profileImage.setOnClickListener(e -> imageBrowse());
        save.setOnClickListener(e -> updateInfos());
    }

    public void updateInfos() {
        dialog = ProgressDialog.show(EditProfileActivity.this, "",
                "Updating. Please wait...", true);
        if (filePath != null) {
            imageUpload();
        } else {
            infosUpload();
            imageDone = true;
        }

//        infosDone = true;

    }

    public void infosUpload() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = BuildConfig.API_URL + "/api/clients/" + prefs.getInt("id", 0);

        StringRequest stringRequest = new StringRequest(Request.Method.PATCH, url,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        System.out.println(jsonObject.toString());
                        saveUserStuff(jsonObject);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    dismissTheDialog();
                    Log.d("taf", response);
                }, error -> {
            System.out.println(error);
            infosDone = true;
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("adresse", adresse.getText().toString());
                params.put("ville", ville.getText().toString());
                params.put("tele", telephone.getText().toString());
                return params;
            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                params.put("Authorization", "Bearer " + prefs.getString("token", ""));
                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);


    }

    private void imageBrowse() {
        CropImage.startPickImageActivity(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);

            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()
                mCropImageUri = imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
            } else {
                // no permissions required or already granted, can start crop image activity
                startCropImageActivity(imageUri);
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            Uri resultUri = result.getUri();
            rescaleImage(resultUri);
        }
    }


    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE) {
            if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // required permissions granted, start crop image activity
                startCropImageActivity(mCropImageUri);
            } else {
                Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri).setAspectRatio(1, 1).start(this);
    }

    public void rescaleImage(Uri imageUri) {
        try {
            Bitmap bMap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            Bitmap bMapScaled = Bitmap.createScaledBitmap(bMap, 600, 600, true);
            compressBitmap(bMapScaled);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void compressBitmap(Bitmap bmap) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bmap.compress(Bitmap.CompressFormat.JPEG, 70, os);

        byte[] array = os.toByteArray();
        Bitmap finalBmap = BitmapFactory.decodeByteArray(array, 0, array.length);
        profileImage.setImageBitmap(finalBmap);
        filePath = getSavedImagePath(finalBmap);
    }

    public String getSavedImagePath(Bitmap inImage) {
        File file;
        String path = Environment.getExternalStorageDirectory().toString();
        file = new File(path, "UniqueFileName" + new Random().nextLong() + ".jpg");

        try {
            OutputStream stream = null;
            stream = new FileOutputStream(file);
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.flush();
            stream.close();
        } catch (IOException e) // Catch the exception
        {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    private void imageUpload() {
        System.out.println(BASE_URL);
        int REQUEST_CODE = 200;
        System.out.println("uploading");
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        new UploadFileAsync().execute("");

    }

    private class UploadFileAsync extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            try {

                HttpURLConnection conn = null;
                DataOutputStream dos = null;
                String lineEnd = "\r\n";
                String twoHyphens = "--";
                String boundary = "*****";
                int bytesRead, bytesAvailable, bufferSize;
                byte[] buffer;
                int maxBufferSize = 1 * 1024 * 1024;
                File sourceFile = new File(filePath);
                System.out.println("dddd " + sourceFile.getTotalSpace());
                System.out.println("dddd " + sourceFile.getName());
                System.out.println("dddd " + sourceFile.getPath());
                if (sourceFile.isFile()) {

                    try {
                        // open a URL connection to the Servlet
                        FileInputStream fileInputStream = new FileInputStream(sourceFile);
                        URL url = new URL(BASE_URL);
                        // Open a HTTP connection to the URL
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setDoInput(true); // Allow Inputs
                        conn.setDoOutput(true); // Allow Outputs
                        conn.setUseCaches(false); // Don't use a Cached Copy
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Connection", "Keep-Alive");
                        conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                        conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                        conn.setRequestProperty("document", filePath);
                        dos = new DataOutputStream(conn.getOutputStream());

                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"document\";filename=\"" + filePath + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);

                        // create a buffer of maximum size
                        bytesAvailable = fileInputStream.available();

                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        buffer = new byte[bufferSize];

                        // read file and write it into form...
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                        while (bytesRead > 0) {

                            dos.write(buffer, 0, bufferSize);
                            bytesAvailable = fileInputStream.available();
                            bufferSize = Math.min(bytesAvailable, maxBufferSize);
                            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                        }

                        // send multipart form data necesssary after file
                        // data...
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                        // Responses from the server (code and message)
                        serverResponseCode = conn.getResponseCode();
                        String serverResponseMessage = conn.getResponseMessage();

                        Scanner s = new java.util.Scanner(conn.getInputStream()).useDelimiter("\\A");


                        System.out.println(s.hasNext() ? s.next() : "");

                        if (serverResponseCode == 200) {
                            imageDone = true;
                            dismissTheDialog();
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putBoolean("imageUpdated", true);
                            editor.apply();
                        }

                        // close the streams //
                        fileInputStream.close();
                        dos.flush();
                        dos.close();

                    } catch (Exception e) {

                        imageDone = true;
                        dismissTheDialog();
                        // dialog.dismiss();
                        e.printStackTrace();

                    }
                    // dialog.dismiss();

                    imageDone = true;
                    dismissTheDialog();
                } // End else block

                imageDone = true;
                dismissTheDialog();

            } catch (Exception ex) {
                // dialog.dismiss();

                imageDone = true;
                dismissTheDialog();
                ex.printStackTrace();
            }

            imageDone = true;
            dismissTheDialog();
            infosUpload();
            System.out.println("done");
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    public void saveUserStuff(JSONObject dataJsonObject) {
        try {
            JSONObject jsonObject = dataJsonObject.getJSONObject("data");
            SharedPreferences.Editor editor = prefs.edit();
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
            new ImageDownloader(profileImage).execute(BuildConfig.API_URL + prefs.getString("avatar", ""));
            Thread.sleep(2000);
            infosDone = true;
            dismissTheDialog();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void dismissTheDialog() {
        if (imageDone && infosDone) {
            dialog.dismiss();
//            startActivity(new Intent(EditProfileActivity.this, ProfileActivity.class));

            finish();
        }
    }
}

