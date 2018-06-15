package com.example.kaito.docsjuris.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
    private CircleImageView bmImage;
    private Bitmap bitmap;
    private int requestCode;
    private DownloadImageListener caller;

    public ImageDownloader(CircleImageView bmImage, int requestCode, DownloadImageListener caller) {
        this.bmImage = bmImage;
        this.requestCode = requestCode;
        this.caller = caller;
    }

    public ImageDownloader(CircleImageView bmImage) {
        this(bmImage, 0);
    }

    public ImageDownloader(CircleImageView bmImage, int requestCode) {
        this(bmImage, requestCode, null);
    }


    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        bitmap = mIcon11;
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        DataHandler.PROFILE_IMAGE = result;
        if (bmImage != null)
            bmImage.setImageBitmap(result);
        if (caller != null)
            caller.onDownloadImageDone(requestCode, bitmap);
    }

    public interface DownloadImageListener {
        void onDownloadImageDone(int responseCode, Bitmap result);
    }
}
