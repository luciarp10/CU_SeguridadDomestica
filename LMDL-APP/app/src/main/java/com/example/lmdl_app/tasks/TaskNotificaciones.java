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
import java.sql.Timestamp;

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
        this.urlStr = uri[0];
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

            while (true) { //BUCLE INFINITO HASTA QUE MUERE LA ACTIVIDAD MENU
                Thread.sleep(10000); //Comprobamos cada 10 segundos
                @SuppressLint("WrongThread")
                String NuevaResp = this.doInBackground(urlStr);
                //Volvemos a pedir el JSON
                JSONObject nuevaJsonAlerta = new JSONObject(NuevaResp);

                //Creamos un objeto Alerta para almacenar los nuevos datos
                Alerta nueva_alerta_recibida = new Alerta();
                nueva_alerta_recibida.setInfo(nuevaJsonAlerta.getString("info"));
                java.sql.Date dateN = Date.valueOf(Comun.transformarFecha(nuevaJsonAlerta.getString("fecha")));
                java.sql.Time horaN = Time.valueOf(Comun.transformarHora(nuevaJsonAlerta.getString("hora")));
                nueva_alerta_recibida.setFecha(dateN);
                nueva_alerta_recibida.setHora(horaN);

                //Comparamos el nuevo con el ultimo
                if (alerta_recibida.getFecha() != nueva_alerta_recibida.getFecha() &&
                        alerta_recibida.getHora() != nueva_alerta_recibida.getHora()) {
                    //if(activity){ ESTO } else{ activity2.creaNotificacion(nueva_alerta_recibida.getInfo());...}
                    //Si son distintos creamos una alerta
                    activity.creaNotificacion(nueva_alerta_recibida.getInfo());
                    //En este caso la ultima alerta es igual a la nueva
                    alerta_recibida = nueva_alerta_recibida;
                }
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