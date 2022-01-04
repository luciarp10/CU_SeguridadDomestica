package com.example.lmdl_app.tasks;

import android.util.Log;

import com.example.lmdl_app.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerConnectionThread extends Thread{
    private MainActivity activity;
    private String tag = "ServerConnectionThread";
    private String urlStr = "";

    public ServerConnectionThread(MainActivity activ, String url)    {
        activity = activ;
        urlStr = url;
        start();
    }

    @Override
    public void run()    {
        String response = "";
        try {
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = null;
            urlConnection = (HttpURLConnection) url.openConnection();
            //Get the information from the url
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            response = convertStreamToString(in);
            Log.d(tag, "get json: " + response);
            activity.setRespuestaServidor(response);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Get the input stream and convert into String
    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}



