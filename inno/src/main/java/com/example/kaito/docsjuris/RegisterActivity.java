package com.example.kaito.docsjuris;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.example.kaito.docsjuris.fragments.Register1Fragment;
import com.example.kaito.docsjuris.fragments.Register2Fragment;
import com.example.kaito.docsjuris.fragments.Register3Fragment;
import com.example.kaito.docsjuris.helpers.BCrypt;
import com.example.kaito.docsjuris.helpers.ImageUploader;
import com.example.kaito.docsjuris.helpers.VolleyRequest;
import com.layer_net.stepindicator.StepIndicator;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Random;

public class RegisterActivity extends AppCompatActivity implements ImageUploader.UploadImageListener, VolleyRequest.RequestExecutedListener {
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    private Button mNextButton;
    private Button mPrevButton;
    public HashMap<String, String> userData;
    public String passwordPlain;
    private String profileImagePath, documentPath;
    private Uri profileImageURI, documentURI;
    private Connector Frag1Connector, Frag3Connector;
    private Bitmap profileBitmap, documentBitmap;
    public boolean isCinChecked = true, uploadDocument = false;
    private final int REGISTER_REQUEST_CODE = 0;
    private final int LOGIN_REQUEST_CODE = 1;
    private final int GETINFOS_REQUEST_CODE = 2;
    private final int AVATAR_REQUEST_CODE = 3;
    private final int DOCUMENT_REQUEST_CODE = 4;
    private boolean imageDone = true;
    private boolean documentDone = false;

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userData = new HashMap<>();

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        profileBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.userhd);
        Frag1Connector = Register1Fragment.newInstance();
        Frag3Connector = Register3Fragment.newInstance();

        StepIndicator stepIndicator = (StepIndicator) findViewById(R.id.step_indicator);
        stepIndicator.setupWithViewPager(mViewPager);

        // Enable | Disable click on step number
        stepIndicator.setClickable(false);

        mNextButton = findViewById(R.id.next_button);
        mPrevButton = findViewById(R.id.prev_button);

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                updateBottomBar();
            }
        });

        mNextButton.setOnClickListener(e -> {
            {
                int position = mViewPager.getCurrentItem();
                if (position != 2)
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                else {
                    Connector c1, c2, c3;
                    c1 = Register1Fragment.newInstance();
                    c2 = Register2Fragment.newInstance();
                    c3 = Register3Fragment.newInstance();

                    if (c1.validateData() && c2.validateData() && c3.validateData()) {
                        c1.getData();
                        c2.getData();
                        c3.getData();
                        uploadData();
                    }
                }
            }
        });

        mPrevButton.setOnClickListener(e -> {
            int position = mViewPager.getCurrentItem();
            if (position != 0)
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onUploadImageDone(int responseCode, int requestCode) {
        if (requestCode == AVATAR_REQUEST_CODE) {
            deleteImage(profileImagePath);
            imageDone = true;
        }
        if (requestCode == DOCUMENT_REQUEST_CODE) {
            deleteImage(documentPath);
            documentDone = true;
        }
        if (documentDone && imageDone) {
            //startActivity(SPLASH_ACTIVITY);
            startActivity(new Intent(RegisterActivity.this, SplashActivity.class));
            finish();
        }
    }

    @Override
    public void onRequestDone(String response, int requestCode) {
        System.out.println(response);
        if (requestCode == REGISTER_REQUEST_CODE) {
            //loging in and them updating documents and avatar
            HashMap<String, String> params = new HashMap<>();
            params.put("client_id", "3");
            params.put("client_secret", BuildConfig.CLIENT_SECRET);
            params.put("grant_type", "password");
            params.put("username", userData.get("email"));
            params.put("password", passwordPlain);
            params.put("theNewProvider", "client");
            new VolleyRequest(BuildConfig.API_URL + "oauth/token", Request.Method.POST,
                    params, getApplicationContext(), this, LOGIN_REQUEST_CODE).execute();
        }
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
                //error password is wrong or i don't fucking know neither care
                e.printStackTrace();
            }
        }
        if (requestCode == GETINFOS_REQUEST_CODE) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(response);
                saveUserStuff(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment selectedFragment = null;
            switch (position) {
                case 0:
                    selectedFragment = Register1Fragment.newInstance();
                    break;
                case 1:
                    selectedFragment = Register2Fragment.newInstance();
                    break;
                case 2:
                    selectedFragment = Register3Fragment.newInstance();
                    break;
            }
            return selectedFragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

    }

    private void updateBottomBar() {
        int position = mViewPager.getCurrentItem();
        if (position == 2) {
            Connector connector = Register2Fragment.newInstance();
            connector.updateRadio(isCinChecked);
            mPrevButton.setVisibility(View.VISIBLE);
            mNextButton.setText(R.string.finish);

        } else if (position == 0) {
            Frag1Connector.updateImage(profileBitmap, null);
            mPrevButton.setVisibility(View.INVISIBLE);
            mNextButton.setText(R.string.next);
            mPrevButton.setText("");
        } else {
            mPrevButton.setVisibility(View.VISIBLE);
            mNextButton.setText(R.string.next);
            mPrevButton.setText(R.string.prev);
            TypedValue v = new TypedValue();
            getTheme().resolveAttribute(android.R.attr.textAppearanceMedium, v, true);
        }
    }

    public interface Connector {
        void getData();

        void updateImage(Bitmap bitmap, String path);

        void updateRadio(boolean isCinChecked);

        boolean validateData();
    }


    public void uploadData() {
        new VolleyRequest(BuildConfig.API_URL + "api/clients/", Request.Method.POST,
                userData, getApplicationContext(), this, REGISTER_REQUEST_CODE).execute();
    }


    public void imageBrowse() {
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
                profileImageURI = imageUri;
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
            if (profileImageURI != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // required permissions granted, start crop image activity
                startCropImageActivity(profileImageURI);
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
        if (uploadDocument) {
            documentBitmap = finalBmap;
            documentPath = getSavedImagePath(finalBmap);
            Frag3Connector.updateImage(finalBmap, documentPath);
        } else {
            profileBitmap = finalBmap;
            Frag1Connector.updateImage(finalBmap, null);
            profileImagePath = getSavedImagePath(finalBmap);
        }
        uploadDocument = false;
    }

    public String getSavedImagePath(Bitmap inImage) {
        File file;
        String path = Environment.getExternalStorageDirectory().toString();
        file = new File(path, "innojuris" + new Random().nextLong() + ".jpg");

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

    public void deleteImage(String path) {
        File fdelete = new File(path);
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                System.out.println("file Deleted :" + path);
            } else {
                System.out.println("file not Deleted :" + path);
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
            editor.putString("password", passwordPlain);
            editor.apply();
            if (profileImagePath != null) {
                imageDone = false;
                new ImageUploader(profileImagePath, BuildConfig.API_URL + "api/clients/upload/avatar/" + prefs.getInt("id", 0), this, AVATAR_REQUEST_CODE).execute("");
            }
            if (documentPath != null)
                new ImageUploader(documentPath, BuildConfig.API_URL + "api/clients/upload/document/" + prefs.getInt("id", 0), this, DOCUMENT_REQUEST_CODE).execute("");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}