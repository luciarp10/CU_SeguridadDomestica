package com.example.lmdl_app.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lmdl_app.R;
import com.example.lmdl_app.data.Habitacion;

import java.util.ArrayList;

public class Habitaciones extends AppCompatActivity {

    private Button botonEstadisticas;
    private Spinner spinnerHabitac;
    private int idHab = 0;
    private String nameHab = "";
    private ArrayList<Habitacion> listHabitacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1_habitaciones);

        this.botonEstadisticas = this.findViewById(R.id.botonEstadist);
        this.spinnerHabitac = this.findViewById(R.id.spinnerHabitac);

        this.listHabitacion = new ArrayList<>();
        Habitacion hab1 = new Habitacion(1,"Hab1");
        Habitacion hab2 = new Habitacion(2,"Hab2");
        listHabitacion.add(hab1);
        listHabitacion.add(hab2);

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
               // try {
                    idHab = listHabitacion.get(i).getId();//Get the id of the selected position
                    nameHab = listHabitacion.get(i).getDescriptivo();//Get the name of the selected position
                   /* Log.i(tag, "Station selected:" + listStation.get(i).getName());
                }catch (Exception e)
                {
                    Log.e(tag, "Error selecting the station:" + e);
                }*/
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        /*private void loadHabitaciones()
        {
            new TaskSelectStation(this).execute("http://192.168.1.131:8080/UbicompServerExample/GetStationsCity?cityId="+cityId);

        }*/
    }
}