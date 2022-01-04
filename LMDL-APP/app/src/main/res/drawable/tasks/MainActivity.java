package com.example.lmdl_app.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lmdl_app.R;

public class MainActivity extends AppCompatActivity {

    private Button botonIniSesion;
    private EditText usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializamos botones y spinners
        this.botonIniSesion = this.findViewById(R.id.botonIniSesion);
        this.usuario = (EditText) this.findViewById(R.id.espacioUsuario);

        botonIniSesion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
              //  if (usuario.getText().toString() == "admin") { //Si es admin
                Intent i = new Intent(MainActivity.this, MenuSistemaAdmin.class);
                startActivity(i);
                finish();
             /*  } else {
                    Intent i = new Intent(MainActivity.this, MenuSistema.class);
                    //   i.putExtra("stationId", "station" + idStation);
                    //  i.putExtra("stationName", nameStation);
                    startActivity(i);
                    finish();
                }*/
            }

        });


    }
}