package com.example.lmdl_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lmdl_app.tasks.ServerConnectionThread;


public class MainActivity extends AppCompatActivity {

    private Button botonIniSesion;
    private EditText usuario;
    private EditText password;
    private String respuestaServidor;
    private TextView mensajeError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializamos elementos
        this.botonIniSesion = this.findViewById(R.id.botonIniSesion);
        this.usuario = (EditText) this.findViewById(R.id.espacioUsuario);
        this.password = (EditText) this.findViewById(R.id.espacioCAire);
        this.mensajeError = (TextView) this.findViewById(R.id.mensajeError);

       botonIniSesion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                comprobarDatos();
            }

        });

        /*
        botonIniSesion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MenuSistemaAdmin.class);
                startActivity(i);
                finish();
            }
        });*/

    }



    public void comprobarDatos() {
        String urlStr = "http://192.168.1.109:8080/LMDL_SERVER2/InicioSesion?usuario=" + usuario.getText().toString() + "&password=" + password.getText().toString();
        ServerConnectionThread thread = new ServerConnectionThread(this, urlStr);
        try {
            thread.join();
        } catch (InterruptedException e) {
        }
        if (!respuestaServidor.contains("-1")) {
            String[] respuesta_separada = respuestaServidor.split("\n");
            if (respuesta_separada[1].equals("true")) {
                Intent i = new Intent(MainActivity.this, MenuSistemaAdmin.class);
                i.putExtra("cod_sistema",respuesta_separada[0]);
                i.putExtra("usuario", usuario.getText().toString());

                startActivity(i);
                finish();
            } else {
                Intent i = new Intent(MainActivity.this, MenuSistema.class);
                i.putExtra("cod_sistema",respuesta_separada[0]);
                i.putExtra("usuario", usuario.getText().toString());
                startActivity(i);
                finish();
            }

        } else {
            //mensaje de error en el usuario y contraseña introducidos
            mensajeError.setText("El usuario o contraseña introducidos no son correctos.");
        }
    }

    public void setRespuestaServidor(String respuestaServidor) {
        this.respuestaServidor = respuestaServidor;
    }
}