package com.example.lmdl_app;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
    private TextView mensajeCamSimulada;
    private EditText fecha_introducida;
    private final Context context;

    private String cod_sistema = ""; //parametro que pasa main
    private String usuariologin = ""; //Parametro que pasa main

    private ArrayList<Integer> id_camaras=new ArrayList<>();

    public Camaras() {
        super();
        this.context = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2_2_camaras);
        getSupportActionBar().setTitle("LMDL-CÁMARAS");

        this.botonVerFotos = this.findViewById(R.id.botonVerFotos);
        this.botonHacerFoto = this.findViewById(R.id.botonHacFot);
        this.camarasDisponibles = this.findViewById(R.id.spinnerListaCamaras);
        this.fecha_introducida = this.findViewById(R.id.editTextDate);
        this.mensajeErrorFecha = this.findViewById(R.id.mensajeErrorFechaFoto);
        this.mensajeCamSimulada = this.findViewById(R.id.mensajeCamSimulada);


        cod_sistema = getIntent().getStringExtra("cod_sistema");
        usuariologin = getIntent().getStringExtra("usuario");

        botonVerFotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mensajeErrorFecha.setText("");
                String fecha_intro = fecha_introducida.getText().toString();
                if(!Comun.comprobarFormatoFecha(fecha_intro)){
                    mensajeErrorFecha.setText("El formato es incorrecto. Recuerda: yyyy-mm-dd");
                }
                else {
                    Intent i = new Intent(Camaras.this, RegistrosCamaras.class);
                    i.putExtra("cod_sistema", cod_sistema);
                    i.putExtra("fecha_introducida", fecha_introducida.getText().toString());
                    String id_camara_sel=""+id_camaras.get(camarasDisponibles.getSelectedItemPosition()); //solo nos interesa el identificador
                    i.putExtra("camara_seleccionada", id_camara_sel);
                    mensajeErrorFecha.setText("");
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

        //Sirve para inhabilitar las cámaras simuladas. En una versión real se eliminaría esta parte
        camarasDisponibles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mensajeCamSimulada.setText("");
                botonHacerFoto.setEnabled(true);
                int id_camara_sel=id_camaras.get(camarasDisponibles.getSelectedItemPosition());
                if(id_camara_sel==10 || id_camara_sel==15){
                    botonHacerFoto.setEnabled(false);
                    mensajeCamSimulada.setText("Lo sentimos. Esta cámara corresponde a un registro simulado. No puede hacer fotos.");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        loadCamarasDisponibles();
    }


    private void loadCamarasDisponibles() {
        new TaskCamaras(this).
                execute(Comun.ruta_servlets+"GetCamarasDisponibles?cod_sistema="+cod_sistema);
    }

    private void solicitarFoto() {
        String id_camara=camarasDisponibles.getSelectedItem().toString();
        id_camara=""+id_camara.charAt(id_camara.length()-1);
        new TaskCamaras(this).
                execute(Comun.ruta_servlets+"HacerFoto?cod_sistema="+cod_sistema+"&usuario="+usuariologin+"&id_camara="+id_camara);
    }


    public void setListCamaras(JSONArray jsonCamaras) {
        Log.e(tag,"Loading camaras disponibles " + jsonCamaras);
        ArrayList<String>camaras = new ArrayList<>();

        try {
            for (int i = 0; i < jsonCamaras.length(); i++) {
                String camara_recibida = ""+jsonCamaras.get(i);
                camaras.add(camara_recibida);
                String[] camara_split = camara_recibida.split(":");
                String id=camara_split[1].substring(1);
                id_camaras.add(Integer.parseInt(id));
            }
            camarasDisponibles.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, camaras));
        }catch (Exception e)
        {
            Log.e(tag,"Error: " + e);
        }
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
                            wait(5000);
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