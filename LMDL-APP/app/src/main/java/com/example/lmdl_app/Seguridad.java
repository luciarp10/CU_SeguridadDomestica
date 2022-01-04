package com.example.lmdl_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class Seguridad extends AppCompatActivity {

    private Button botonRegistros;
    private Button botonCamaras;
    private Button botonSimuladorPres;
    private Button botonEstadoAlarma;
    private Button botonVerQR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2_seguridad);

        this.botonRegistros = this.findViewById(R.id.botonReg);
        this.botonCamaras = this.findViewById(R.id.botonCam);
        this.botonSimuladorPres = this.findViewById(R.id.botonSimPres);
        this.botonEstadoAlarma = this.findViewById(R.id.botonEstAlar);
        this.botonVerQR = this.findViewById(R.id.botonMiQR);

        botonRegistros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Seguridad.this, Registros.class);
                startActivity(i);
                //finish();
            }
        });

        botonCamaras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Seguridad.this, Camaras.class);
                startActivity(i);
                //finish();
            }
        });

        botonSimuladorPres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Seguridad.this, SimulacionPresencia.class);
                startActivity(i);
                //finish();
            }
        });

        botonEstadoAlarma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Seguridad.this, EstadoAlarma.class);
                startActivity(i);
                //finish();
            }
        });

        botonVerQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Seguridad.this, VerQR.class);
                startActivity(i);
                //finish();
            }
        });
    }
}