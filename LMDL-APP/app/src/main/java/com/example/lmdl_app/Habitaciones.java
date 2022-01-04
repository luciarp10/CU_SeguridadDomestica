package com.example.lmdl_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lmdl_app.data.Habitacion;
import com.example.lmdl_app.tasks.TaskSelectHabitacion;

import java.util.ArrayList;

public class Habitaciones extends AppCompatActivity {

    /*
    private String tag = "SelectStation";
    private Spinner spinnerCities;
    private Spinner spinnerStations;
    private Button buttonStation;
    private ArrayList<City> listCities;
    private ArrayList<Station> listStation;
    private final Context context;
    private int idStation = 0;
    private String nameStation = "";*/

    private String tag = "SelectHabitacion";
    private Button botonEstadisticas;
    private Spinner spinnerHabitac;
    private int idHab = 0;
    private String nameHab = "";
    private ArrayList<Habitacion> listHabitacion;
    //private final Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1_habitaciones);

        this.botonEstadisticas = this.findViewById(R.id.botonEstadist);
        this.spinnerHabitac = this.findViewById(R.id.spinnerHabitac);
        //this.spinnerCities = this.findViewById(R.id.spinnerCity);

        //this.listCities = new ArrayList<>();
        this.listHabitacion = new ArrayList<>();

        spinnerHabitac.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int id = listHabitacion.get(i).getId_habitacion();//Get the id of the selected position
                Log.i(tag, "City selected:" + listHabitacion.get(i).getDescriptivo());

                //Get the list of stations of the selected city
                //loadHabitaciones(listHabitacion.get(i).getId_habitacion());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        /*
        * //Add action when the spinner of the cities changes
        spinnerCities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int id = listCities.get(i).getId();//Get the id of the selected position
                Log.i(tag, "City selected:" + listCities.get(i).getName());

                //Get the list of stations of the selected city
                loadStations(listCities.get(i).getId());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });*/

        /*
        * buttonStation.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(SelectStation.this, StationActivity.class);
                i.putExtra("stationId", "station" + idStation);
                i.putExtra("stationName", nameStation);
                startActivity(i);
                finish();
            }
        });*/

        botonEstadisticas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Habitaciones.this, Estadisticas.class);
                startActivity(i);
                //finish();
            }
        });

        //loadCities();
        loadHabitaciones();
    }

    //Search the cities and fill the spinner with the information
    private void loadHabitaciones() {
        new TaskSelectHabitacion(this).
                execute("http://192.168.1.108:8080/LMDL_SERVER2/GetHabitaciones?");
              //execute("http://192.168.1.108:8080/LMDL_SERVER2/ComprobarQR?codigo="+codigoQR+"&id_sistema="+1);
    }
    /*
    * //Search the cities and fill the spinner with the information
    private void loadCities()
    {
        new TaskSelectStation(this).execute("http://192.168.1.131:8080/UbicompServerExample/GetCities");
    }


    public void setListCities(JSONArray jsonCities)
    {
        Log.e(tag,"Loading cities " + jsonCities);
        try {
            ArrayList<String> arrayCities = new ArrayList<>();
            //listStation = new ArrayList<>();
            for (int i = 0; i < jsonCities.length(); i++) {
                JSONObject jsonobject = jsonCities.getJSONObject(i);
                listCities.add(new City(jsonobject.getInt("id"), jsonobject.getString("name")));
                arrayCities.add(jsonobject.getString("name"));
            }

            spinnerCities.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, arrayCities));
        }catch (Exception e)
        {
            Log.e(tag,"Error: " + e);
        }
    }*/
}