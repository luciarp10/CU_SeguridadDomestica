package com.example.lmdl_app.tasks;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lmdl_app.R;

public class EstadoAlarma extends AppCompatActivity {

    private Button botonCambiarEst;
    private String imag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2_4_estado_alarma);

        this.botonCambiarEst = this.findViewById(R.id.botonCambiar);


        botonCambiarEst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Intent i = new Intent(EstadoAlarma.this, Informes.class);
                startActivity(i);
                //finish();*/
            }
        });

    }
}