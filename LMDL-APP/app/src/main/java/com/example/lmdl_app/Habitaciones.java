package com.example.lmdl_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

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

    private Button botonEstadisticas;
    private Spinner spinnerHabitac;
    private int idHab = 0;
    private String nameHab = "";
    //private ArrayList<Habitacion> listHabitacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1_habitaciones);

        this.botonEstadisticas = this.findViewById(R.id.botonEstadist);
        this.spinnerHabitac = this.findViewById(R.id.spinnerHabitac);

        //this.spinnerCities = this.findViewById(R.id.spinnerCity);

        //this.listCities = new ArrayList<>();

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

        spinnerHabitac.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        //loadCities();
    }
    /*
    * //Search the cities and fill the spinner with the information
    private void loadCities()
    {
        new TaskSelectStation(this).execute("http://192.168.1.131:8080/UbicompServerExample/GetCities");
    }

    //Search the stations of the selected city and fill the spinner with the information
    private void loadStations(final int cityId)
    {
        new TaskSelectStation(this).execute("http://192.168.1.131:8080/UbicompServerExample/GetStationsCity?cityId="+cityId);

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