package com.example.lmdl_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lmdl_app.R;

public class RegistrarSimulacion extends AppCompatActivity {

    private Button botonRegProgam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2_3_2_reg_sim);

        this.botonRegProgam = this.findViewById(R.id.botonRegSim);

        botonRegProgam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent i = new Intent(RegistrarSimulacion.this, .class);
                startActivity(i);
                //finish();*/
            }
        });
    }
}
