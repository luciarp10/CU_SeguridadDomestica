package com.example.lmdl_app.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.lmdl_app.Habitaciones;
import com.example.lmdl_app.RegistrarSimulacion;

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
import java.util.ArrayList;

/**
 * Task to connect with the API to request the list of cities and stations
 */
public class TaskSelectHabitacion extends AsyncTask<String,Void, String>
{
    private String tag = "TaskSelectHabitacion";
    private Habitaciones activity=null;
    private RegistrarSimulacion activityRegSim=null;
    private String urlStr = "";

    public TaskSelectHabitacion(Habitaciones activity)
    {
        this.activity = activity;
    }
    public TaskSelectHabitacion(RegistrarSimulacion activityRegSim){ this.activityRegSim = activityRegSim;}

    @Override
    protected String doInBackground(String... uri) {
        String response = "";
        this.urlStr = uri[0];
        try {
            URL url = new URL(uri[0]);
            HttpURLConnection urlConnection = null;
            urlConnection = (HttpURLConnection) url.openConnection();
            //Get the information from the url
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            response = convertStreamToString(in);
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return response;
    }
    //When the task is complete this method will be called to change the values in the UI
    protected void onPostExecute(String response)
    {
        super.onPostExecute(response);
        try
        {
            Log.d(tag, "get json: " + response);

            //Read Responses and fill the spinner
            if(urlStr.contains("GetHabitacionesSistema"))
            {
                JSONArray jsonarrayHabs = new JSONArray(response);
                if(activity!=null){
                    activity.setListHabitacion(jsonarrayHabs);
                }
                else {
                    activityRegSim.setListHabitacion(jsonarrayHabs);
                }

            }
            else {
                if (urlStr.contains("GetUltRegistrosEstadisticosHabitacion"))
                {
                    String[] response_separada;
                    response_separada=response.split("]");
                    response_separada[0]=response_separada[0]+"]";
                    response_separada[1]=response_separada[1]+"]";
                    JSONArray jsonSensores = new JSONArray(response_separada[0]);
                    JSONArray jsonRegistros = new JSONArray(response_separada[1]);
                    activity.setUltimosRegistros(jsonSensores, jsonRegistros);
                }
            }
        }catch (Exception e)
        {
            Log.e(tag, "Error on postExecute:" + e);
        }
    }

    //GEt the input strean and convert into String
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