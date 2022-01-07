package com.example.lmdl_app;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lmdl_app.tasks.TaskUsuarios;

public class NuevoUsuario extends AppCompatActivity {

    private EditText nombre_introducido;
    private EditText pass_introducida;
    private EditText repetir_pass;
    private TextView mensaje_error;
    private TextView mensaje_exito;

    private Button boton_registrar;

    private String cod_sistema;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4_1_new_user);

        this.cod_sistema  = getIntent().getStringExtra("cod_sistema");
        this.nombre_introducido = this.findViewById(R.id.editTextTextPersonName);
        this.pass_introducida = this.findViewById(R.id.editTextTextPassword);
        this.repetir_pass = this.findViewById(R.id.editTextReptPass);
        this.boton_registrar = this.findViewById(R.id.botonRegUser);
        this.mensaje_error = this.findViewById(R.id.mensajeErrorRegistro);
        this.mensaje_exito = this.findViewById(R.id.mensajeExitoRegistro);

        boton_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mensaje_error.setText("");
                mensaje_exito.setText("");
                if(comprobarDatosIntroducidos()){
                    enviarDatosUsuarioNuevo();
                }
            }
        });


    }
    private void enviarDatosUsuarioNuevo() {
        new TaskUsuarios(this).
                execute("http://192.168.1.109:8080/LMDL_SERVER2/InsertarUsuarioSistema?cod_sistema="+cod_sistema+"&nombre_usuario="+nombre_introducido.getText()+"&password="+pass_introducida.getText());
    }

    private boolean comprobarDatosIntroducidos(){
        Log.i("RegistroUsuario", "Datos:"+nombre_introducido.getText()+pass_introducida.getText()+repetir_pass.getText());
        if(nombre_introducido.getText().toString().equals("") || pass_introducida.getText().toString().equals("") || repetir_pass.getText().toString().equals("")){
            mensaje_error.setText("Error. Hay campos vacios.");
            return false;
        }
        else {
            if (!pass_introducida.getText().toString().equals(repetir_pass.getText().toString())){
                mensaje_error.setText("Error. Las contraseñas no coinciden.");
                return false;
            }
        }
        return true;
    }

    public void comprobarResultadoRegistro(String response){
        if (response.contains("-1")){
            mensaje_error.setText("El nombre de usuario ya existe.");
        }
        else {
            nombre_introducido.setText("");
            pass_introducida.setText("");
            repetir_pass.setText("");
            mensaje_exito.setText("El usuario se ha registrado correctamente");

        }
    }
}
