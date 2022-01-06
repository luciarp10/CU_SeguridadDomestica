package com.example.lmdl_app;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class NuevoUsuario extends AppCompatActivity {

    private EditText nombre_introducido;
    private EditText pass_introducida;
    private EditText repetir_pass;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4_1_new_user);
    }
}
