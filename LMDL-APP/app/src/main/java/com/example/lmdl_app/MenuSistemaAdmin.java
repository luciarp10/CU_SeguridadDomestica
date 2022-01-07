package com.example.lmdl_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MenuSistemaAdmin extends AppCompatActivity {

    private Button botonHabitaciones;
    private Button botonSeguridad;
    private Button botonEstadisticas;
    private Button botonAdminUsers;

    private String codigoSist = ""; //parametro que pasa main
    private String usuariologin = ""; //Parametro que pasa main

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_0_1_menu_admi);

        //Inicializamos botones y spinners
        this.botonHabitaciones = this.findViewById(R.id.botonHab);
        this.botonSeguridad = this.findViewById(R.id.botonSeg);
        this.botonEstadisticas = this.findViewById(R.id.botonEst);
        this.botonAdminUsers = this.findViewById(R.id.botonAdminUser);

        codigoSist = getIntent().getStringExtra("cod_sistema");
        usuariologin = getIntent().getStringExtra("usuario");

        botonHabitaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuSistemaAdmin.this, Habitaciones.class);
                i.putExtra("cod_sistema", codigoSist); //Se pasa el codigo de sistema al resto de actividades
                startActivity(i);
                //finish();
            }
        });

        botonSeguridad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuSistemaAdmin.this, Seguridad.class);
                i.putExtra("cod_sistema", codigoSist);
                i.putExtra("usuario", usuariologin);
                startActivity(i);
                //finish();
            }
        });

        botonEstadisticas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuSistemaAdmin.this, Estadisticas.class);
                i.putExtra("cod_sistema", codigoSist);
                startActivity(i);
                //finish();
            }
        });

        botonAdminUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuSistemaAdmin.this, Usuarios.class);
                i.putExtra("cod_sistema", codigoSist);
                i.putExtra("usuario", usuariologin);
                startActivity(i);
                //finish();
            }
        });

    }
}