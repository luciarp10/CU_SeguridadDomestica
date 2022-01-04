package com.example.lmdl_app.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lmdl_app.R;

public class Camaras extends AppCompatActivity {

    private Button botonVerFotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2_2_camaras);

        this.botonVerFotos = this.findViewById(R.id.botonVerFotos);

        botonVerFotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Camaras.this, RegistrosCamaras.class);
                startActivity(i);
                //finish();
            }
        });
    }
}