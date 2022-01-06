package com.example.lmdl_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lmdl_app.R;

public class MenuSistema  extends AppCompatActivity {
    private Button botonHabitaciones;
    private Button botonSeguridad;
    private Button botonEstadisticas;

    private String codigoSist = ""; //parametro que pasa main
    private String usuariologin = ""; //Parametro que pasa main

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_0_menu);

        //Inicializamos botones y spinners
        this.botonHabitaciones = this.findViewById(R.id.botonHabi);
        this.botonSeguridad = this.findViewById(R.id.botonSeguri);
        this.botonEstadisticas = this.findViewById(R.id.botonEstadi);

        codigoSist = getIntent().getStringExtra("cod_sistema");
        usuariologin = getIntent().getStringExtra("usuario");
        botonHabitaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuSistema.this, Habitaciones.class);
                i.putExtra("cod_sistema", codigoSist); //Se pasa el codigo de sistema al resto de actividades
                startActivity(i);
                //finish();
            }
        });

        botonSeguridad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuSistema.this, Seguridad.class);
                i.putExtra("cod_sistema", codigoSist);
                i.putExtra("usuario", usuariologin);
                startActivity(i);
                //finish();
            }
        });

        botonEstadisticas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuSistema.this, Estadisticas.class);
                i.putExtra("cod_sistema", codigoSist);
                startActivity(i);
                //finish();
            }
        });



    }
}