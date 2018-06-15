package com.example.kaito.docsjuris.helpers;

import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ImageUploader extends AsyncTask<String, Void, String> {

    private String filePath, BASE_URL;
    private UploadImageListener callingActivity;
    private int serverResponseCode,requestCode;

    public ImageUploader(String filePath, String BASE_URL, UploadImageListener callingActivity,int requestCode) {
        this.filePath = filePath;
        this.BASE_URL = BASE_URL;
        this.callingActivity = callingActivity;
        this.requestCode = requestCode;

    }

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

                    Scanner s = new java.util.Scanner(conn.getInputStream()).useDelimiter("\\A");


                    System.out.println(s.hasNext() ? s.next() : "");

                    if (serverResponseCode == 200) {
                        //RESULT = OK
                    }

                    // close the streams //
                    fileInputStream.close();
                    dos.flush();
                    dos.close();

                } catch (Exception e) {
                    // dialog.dismiss();
                    e.printStackTrace();

                }
                // dialog.dismiss();

            } // End else block


        } catch (Exception ex) {
            // dialog.dismiss();

            ex.printStackTrace();
        }

        System.out.println("done");
        return "Executed";
    }

    @Override
    protected void onPostExecute(String result) {
        callingActivity.onUploadImageDone(serverResponseCode,requestCode);
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }

    public interface UploadImageListener {
        void onUploadImageDone(int responseCode,int requestCode);
    }

}

