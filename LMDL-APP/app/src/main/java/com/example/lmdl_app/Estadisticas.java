package com.example.lmdl_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Estadisticas extends AppCompatActivity {

    private Button botonInformes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3_estadisticas);

        this.botonInformes = this.findViewById(R.id.botonVerInfo);

        botonInformes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Estadisticas.this, Informes.class);
                startActivity(i);
                //finish();
            }
        });

    }
}