package com.example.lmdl_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lmdl_app.data.Habitacion;
import com.example.lmdl_app.data.Registro_sensor;
import com.example.lmdl_app.data.Sensor;
import com.example.lmdl_app.tasks.TaskSelectHabitacion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Habitaciones extends AppCompatActivity {

    private String tag = "SelectHabitacion";
    private Button botonEstadisticas;
    private Spinner spinnerHabitac;
    private int idHab = 0;
    private ArrayList<String> arrayHab = new ArrayList<>();
    private ArrayList<Habitacion> listHabitacion = new ArrayList<>();
    private ArrayList<Sensor> sensoresHabitacion = new ArrayList<>();
    private ArrayList<Registro_sensor> ultimosRegistros = new ArrayList<>();
    private final Context context;

    private String codigoSist = ""; //parametro que pasa main

    public Habitaciones() {
        super();
        this.context = this;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1_habitaciones);

        this.codigoSist= getIntent().getStringExtra("cod_sistema");
        this.botonEstadisticas = this.findViewById(R.id.botonEstadist);
        this.spinnerHabitac = this.findViewById(R.id.spinnerHabitac);
        this.listHabitacion = new ArrayList<>();


        spinnerHabitac.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                borrarRegistros();
                idHab = listHabitacion.get(i).getId_habitacion();//Get the id of the selected position
                Log.i(tag, "Habitacion selecionada:" + listHabitacion.get(i).getDescriptivo());
                loadUltimosRegistros();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        botonEstadisticas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Habitaciones.this, Estadisticas.class);
                i.putExtra("cod_sistema", codigoSist);
                startActivity(i);
                //finish();
            }
        });
        loadHabitaciones();
    }


    private void loadHabitaciones() {
        new TaskSelectHabitacion(this).
                execute(Comun.ruta_servlets+"GetHabitacionesSistema?cod_sistema="+codigoSist);


    }

    private void loadUltimosRegistros (){
        new TaskSelectHabitacion(this).
            execute(Comun.ruta_servlets+"GetUltRegistrosEstadisticosHabitacion?id_habitacion="+idHab);

    }

    public void setListHabitacion(JSONArray jsonHabitaciones)
    {
        Log.e(tag,"Loading habitaciones " + jsonHabitaciones);
        try {

            for (int i = 0; i < jsonHabitaciones.length(); i++) {
                JSONObject jsonobject = jsonHabitaciones.getJSONObject(i);
                Habitacion habitacion_recibida = new Habitacion();
                habitacion_recibida.setId_habitacion(jsonobject.getInt("id_habitacion"));
                habitacion_recibida.setDescriptivo(jsonobject.getString("descriptivo"));
                habitacion_recibida.setN_puertas(jsonobject.getInt("n_puertas"));
                habitacion_recibida.setN_ventanas(jsonobject.getInt("n_ventanas"));
                habitacion_recibida.setTamanno(jsonobject.getInt("tamanno"));
                habitacion_recibida.setCod_sistema_sistema_seguridad(jsonobject.getInt("cod_sistema_sistema_seguridad"));

                listHabitacion.add(habitacion_recibida);
                arrayHab.add(jsonobject.getString("descriptivo"));

            }

            spinnerHabitac.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, arrayHab));
        }catch (Exception e)
        {
            Log.e(tag,"Error: " + e);
        }

    }

    public void setUltimosRegistros (JSONArray jsonSensores, JSONArray jsonRegistros){
        Log.e(tag,"Loading sensores " + jsonSensores);
        Log.e(tag,"Loading ultimos registros " + jsonRegistros);
        try {

            for (int i = 0; i < jsonSensores.length(); i++) {
                JSONObject jsonobject = jsonSensores.getJSONObject(i);
                Sensor sensor_recibido = new Sensor();
                sensor_recibido.setId_sensor(jsonobject.getInt("id_sensor"));
                sensor_recibido.setId_habitacion_habitacion(jsonobject.getInt("id_habitacion_habitacion"));
                sensor_recibido.setTipo(jsonobject.getString("tipo"));
                sensoresHabitacion.add(sensor_recibido);
            }
            for (int i=0; i<jsonRegistros.length();i++){
                JSONObject jsonObject = jsonRegistros.getJSONObject(i);

                java.sql.Date date = Date.valueOf(Comun.transformarFecha(jsonObject.getString("fecha")));
                java.sql.Time hora = Time.valueOf(Comun.transformarHora(jsonObject.getString("hora")));
                Registro_sensor registro_recibido = new Registro_sensor();
                registro_recibido.setFecha((Date) date);
                registro_recibido.setHora(hora);
                registro_recibido.setId_sensor_sensor(jsonObject.getInt("id_sensor_sensor"));
                registro_recibido.setValor(jsonObject.getDouble("valor"));
                ultimosRegistros.add(registro_recibido);
            }

            Log.i(tag, ""+sensoresHabitacion.toString());
            Log.i(tag, ""+ultimosRegistros.toString());

            for (int i=0;i<ultimosRegistros.size();i++){

                if(sensoresHabitacion.get(i).getTipo().contains("Temperatura")){
                    EditText espacioTemp = (EditText) this.findViewById(R.id.espacioMedida);
                    espacioTemp.setText(""+ultimosRegistros.get(i).getValor()+" ºC");
                }
                else if(sensoresHabitacion.get(i).getTipo().contains("Humedad")){
                    EditText espacioHum = (EditText) this.findViewById(R.id.espacioPeriodo);
                    espacioHum.setText(""+ultimosRegistros.get(i).getValor()+ " %RH");
                }
                else if(sensoresHabitacion.get(i).getTipo().contains("Luminosidad")){
                    EditText espacioLum = (EditText) this.findViewById(R.id.espacioFecha);
                    espacioLum.setText(""+ultimosRegistros.get(i).getValor()+" lum");
                }
                else if(sensoresHabitacion.get(i).getTipo().contains("Humo")){
                    EditText espacioCAire = (EditText) this.findViewById(R.id.espacioCAire);
                    espacioCAire.setText(""+ultimosRegistros.get(i).getValor()+" ICA");
                }
            }

            //Para dejar los arrays vacios por si se consulta otra habitación rellenarlos de nuevo
            ultimosRegistros=new ArrayList<>();
            sensoresHabitacion=new ArrayList<>();
        }catch (Exception e)
        {
            Log.e(tag,"Error: " + e);
        }
    }

    private void borrarRegistros(){
        EditText espacioTemp = (EditText) this.findViewById(R.id.espacioMedida);
        EditText espacioHum = (EditText) this.findViewById(R.id.espacioPeriodo);
        EditText espacioLum = (EditText) this.findViewById(R.id.espacioFecha);
        EditText espacioCAire = (EditText) this.findViewById(R.id.espacioCAire);
        espacioTemp.setText(" ");
        espacioTemp.setFocusable(false);
        espacioHum.setText(" ");
        espacioHum.setFocusable(false);
        espacioLum.setText(" ");
        espacioLum.setFocusable(false);
        espacioCAire.setText(" ");
        espacioCAire.setFocusable(false);

    }

}

