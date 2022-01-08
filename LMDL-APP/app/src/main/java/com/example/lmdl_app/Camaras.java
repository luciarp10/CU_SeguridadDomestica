package com.example.lmdl_app;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lmdl_app.data.Identificacion;
import com.example.lmdl_app.tasks.TaskCamaras;
import com.example.lmdl_app.tasks.TaskUsuarios;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;

public class Camaras extends AppCompatActivity {

    private String tag = "Camaras";

    private Button botonVerFotos;
    private Button botonHacerFoto;
    private Spinner camarasDisponibles;
    private TextView mensajeErrorFecha;
    private EditText fecha_introducida;
    private final Context context;

    private String cod_sistema = ""; //parametro que pasa main
    private String usuariologin = ""; //Parametro que pasa main

    public Camaras() {
        super();
        this.context = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2_2_camaras);

        this.botonVerFotos = this.findViewById(R.id.botonVerFotos);
        this.botonHacerFoto = this.findViewById(R.id.botonHacFot);
        this.camarasDisponibles = this.findViewById(R.id.spinnerListaCamaras);
        this.fecha_introducida = this.findViewById(R.id.editTextDate);
        this.mensajeErrorFecha = this.findViewById(R.id.mensajeErrorFechaFoto);


        cod_sistema = getIntent().getStringExtra("cod_sistema");
        usuariologin = getIntent().getStringExtra("usuario");

        botonVerFotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mensajeErrorFecha.setText("");
                String fecha_intro = fecha_introducida.getText().toString();
                if(!comprobarFormatoFecha(fecha_intro)){
                    mensajeErrorFecha.setText("El formato es incorrecto. Recuerda: yyyy-mm-dd");
                }
                else {
                    Intent i = new Intent(Camaras.this, RegistrosCamaras.class);
                    i.putExtra("cod_sistema", cod_sistema);
                    i.putExtra("fecha_introducida", fecha_introducida.getText().toString());
                    String id_camara=camarasDisponibles.getSelectedItem().toString();
                    id_camara=""+id_camara.charAt(id_camara.length()-1); //solo nos interesa el identificador
                    i.putExtra("camara_seleccionada", id_camara);
                    startActivity(i);
                }

            }
        });

        botonHacerFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solicitarFoto();
            }
        });

        loadCamarasDisponibles();
    }


    private void loadCamarasDisponibles() {
        new TaskCamaras(this).
                execute("http://192.168.1.109:8080/LMDL_SERVER2/GetCamarasDisponibles?cod_sistema="+cod_sistema);
    }

    private void solicitarFoto() {
        String id_camara=camarasDisponibles.getSelectedItem().toString();
        id_camara=""+id_camara.charAt(id_camara.length()-1);
        new TaskCamaras(this).
                execute("http://192.168.1.109:8080/LMDL_SERVER2/HacerFoto?cod_sistema="+cod_sistema+"&usuario="+usuariologin+"&id_camara="+id_camara);
    }


    public void setListCamaras(JSONArray jsonCamaras) {
        Log.e(tag,"Loading camaras disponibles " + jsonCamaras);
        ArrayList<String>camaras = new ArrayList<>();

        try {
            for (int i = 0; i < jsonCamaras.length(); i++) {
                String camara_recibida = ""+jsonCamaras.get(i);
                camaras.add(camara_recibida);
            }
            camarasDisponibles.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, camaras));
        }catch (Exception e)
        {
            Log.e(tag,"Error: " + e);
        }
    }

    private boolean comprobarFormatoFecha(String fecha){
        String[] fecha_separada = fecha.split("-");
        if (fecha_separada.length!=3){
            return false;
        }
        else{
            if (fecha_separada[0].length()!=4 || fecha_separada[1].length()!=2 || fecha_separada[1].length()!=2){
                return false;
            }
            else if((Integer.parseInt(fecha_separada[1]) > 12 || Integer.parseInt(fecha_separada[2])>31)){
                return false;
            }

        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void mostrarUltimaFoto(String response){
        if(!response.contains("-1")){
            botonVerFotos.setEnabled(false);
            botonHacerFoto.setText("Solicitando...");
            new Thread(new Runnable() {
                public void run() {
                    try {
                        synchronized (this) {
                            wait(3000);
                            Intent i = new Intent(Camaras.this, RegistrosCamaras.class);
                            i.putExtra("cod_sistema", cod_sistema);
                            i.putExtra("fecha_introducida", LocalDate.now().toString());
                            String id_camara=camarasDisponibles.getSelectedItem().toString();
                            id_camara=""+id_camara.charAt(id_camara.length()-1); //solo nos interesa el identificador
                            i.putExtra("camara_seleccionada", id_camara);
                            startActivity(i);
                            Camaras.super.finish();
                        }
                    } catch (InterruptedException e) { }
                }
            }).start();

        }
    }

}