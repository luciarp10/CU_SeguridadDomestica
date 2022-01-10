package com.example.lmdl_app.tasks;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.example.lmdl_app.Comun;
import com.example.lmdl_app.MenuSistema;
import com.example.lmdl_app.MenuSistemaAdmin;
import com.example.lmdl_app.Registros;
import com.example.lmdl_app.data.Alerta;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;

/**
 * Task to connect with the API to request the list of cities and stations
 */
public class TaskNotificaciones extends AsyncTask<String, Void, String> {
    private String tag = "TaskNotificaciones";
    private MenuSistemaAdmin activity;
    private MenuSistema activity2;
    private String urlStr = "";


    public TaskNotificaciones(MenuSistemaAdmin activity) {
        this.activity = activity;
    }

    public TaskNotificaciones(MenuSistema activity2) {
        this.activity2 = activity2;
    }

    @Override
    protected String doInBackground(String... uri) {

        String response = "";
        /*
        this.urlStr = uri[0];
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        try {
            URL url = new URL(uri[0]);
            HttpURLConnection urlConnection = null;
            urlConnection = (HttpURLConnection) url.openConnection();
            //Get the information from the url
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            response = convertStreamToString(in);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return response;
    }

    //When the task is complete this method will be called to change the values in the UI
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
        //David
        try {
            Log.d(tag, "get json: " + response);
            JSONObject jsonAlerta = new JSONObject(response);
            //Creamos un objeto Alerta para almacenar esos datos
            Alerta alerta_recibida = new Alerta();
            alerta_recibida.setInfo(jsonAlerta.getString("info"));
            java.sql.Date date = Date.valueOf(Comun.transformarFecha(jsonAlerta.getString("fecha")));
            java.sql.Time hora = Time.valueOf(Comun.transformarHora(jsonAlerta.getString("hora")));
            alerta_recibida.setFecha(date);
            alerta_recibida.setHora(hora);
            //Comprobamos que la última sea diferente a la que hemos recibido ahora y si lo es, lanzamos la notificacion y actualizamos
            if(activity!=null){
                if(!alerta_recibida.getInfo().equals(activity.getUltima_alerta().getInfo())){
                        activity.creaNotificacion(alerta_recibida.getInfo());
                        activity.setUltima_alerta(alerta_recibida);
                }
                activity.ejecutarTask();
            }
            else {
                if(!alerta_recibida.getInfo().equals(activity2.getUltima_alerta().getInfo())){
                    activity2.creaNotificacion(alerta_recibida.getInfo());
                    activity2.setUltima_alerta(alerta_recibida);
                }
                activity2.ejecutarTask();
            }




        } catch (Exception e) {
            Log.e(tag, "Error on comprobacion JSON:" + e);
        }
        //David
    }

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