package com.example.lmdl_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrosActuadores extends AppCompatActivity {

    private Button botonVerReg;
    private Button botonProgSim;
    private String cod_sistema = ""; //Parametro que recibe de menuSistema o menuSistemaAdmin
    private String usuariologin = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2_3_sim_pres);
        getSupportActionBar().setTitle("LMDL-ACTUADORES");

        usuariologin = getIntent().getStringExtra("usuario");
        cod_sistema = getIntent().getStringExtra("cod_sistema");
        this.botonVerReg = this.findViewById(R.id.botonVerRegSim);
        this.botonProgSim = this.findViewById(R.id.botonProgSim);

        botonVerReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegistrosActuadores.this, SimulacionesProgramadas.class);
                i.putExtra("cod_sistema", cod_sistema );
                i.putExtra("usuario", usuariologin);
                startActivity(i);
                //finish();
            }
        });

        botonProgSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegistrosActuadores.this, RegistrarSimulacion.class);
                i.putExtra("cod_sistema", cod_sistema );
                i.putExtra("usuario", usuariologin);
                startActivity(i);
                //finish();
            }
        });
    }
}