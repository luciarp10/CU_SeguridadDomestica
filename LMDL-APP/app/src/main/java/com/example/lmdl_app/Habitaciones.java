package com.example.lmdl_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lmdl_app.data.Habitacion;
import com.example.lmdl_app.tasks.TaskSelectHabitacion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Habitaciones extends AppCompatActivity {

    private String tag = "SelectHabitacion";
    private Button botonEstadisticas;
    private Spinner spinnerHabitac;
    private int idHab = 0;
    private String nameHab = "";
    ArrayList<String> arrayHab;
    private ArrayList<Habitacion> listHabitacion;
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

        codigoSist = getIntent().getStringExtra("cod_sistema");

        this.botonEstadisticas = this.findViewById(R.id.botonEstadist);
        this.spinnerHabitac = this.findViewById(R.id.spinnerHabitac);

        this.listHabitacion = new ArrayList<>();

        spinnerHabitac.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int id = listHabitacion.get(i).getId_habitacion();//Get the id of the selected position
                Log.i(tag, "Habitacion selected:" + listHabitacion.get(i).getDescriptivo());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        botonEstadisticas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Habitaciones.this, Estadisticas.class);
                //i.putExtra("stationId", "station" + idStation);
                //i.putExtra("stationName", nameStation);
                startActivity(i);
                //finish();
            }
        });
        loadHabitaciones();
    }

    //Search the cities and fill the spinner with the information
    private void loadHabitaciones() {
        new TaskSelectHabitacion(this).
                execute("http://192.168.1.108:8080/LMDL_SERVER2/GetHabitacionesSistema?cod_sistema="+codigoSist);
              //execute("http://192.168.1.108:8080/LMDL_SERVER2/
             // ComprobarQR?codigo="+codigoQR+"&id_sistema="+1);

    }

    public void setListHabitacion(JSONArray jsonHabitaciones)
    {
        Log.e(tag,"Loading habitaciones " + jsonHabitaciones);
        try {
            ArrayList<String> arrayCities = new ArrayList<>();
            //listStation = new ArrayList<>();
            for (int i = 0; i < jsonHabitaciones.length(); i++) {
                JSONObject jsonobject = jsonHabitaciones.getJSONObject(i);
                listHabitacion.add(new Habitacion(jsonobject.getInt("id_habitacion"),
                        jsonobject.getInt("n_puertas"),jsonobject.getInt("n_ventanas"),
                        jsonobject.getInt("tamanno"),jsonobject.getInt("cod_sistema_sistema_seguridad"),
                        jsonobject.getString("descriptivo")));
                arrayHab.add(jsonobject.getString("descriptivo"));

            }

            spinnerHabitac.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, arrayHab));
        }catch (Exception e)
        {
            Log.e(tag,"Error: " + e);
        }

    }

}

