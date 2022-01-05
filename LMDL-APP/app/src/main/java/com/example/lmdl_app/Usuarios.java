package com.example.lmdl_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lmdl_app.R;

public class Usuarios extends AppCompatActivity {

    private Button botonCreaUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4_usuarios);

        this.botonCreaUser = this.findViewById(R.id.botonAñadirU);

        botonCreaUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Usuarios.this, NuevoUsuario.class);
                startActivity(i);
                //finish();
            }
        });
    }
}