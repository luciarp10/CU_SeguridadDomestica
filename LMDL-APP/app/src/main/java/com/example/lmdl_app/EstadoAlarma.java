package com.example.lmdl_app;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lmdl_app.R;
import com.example.lmdl_app.tasks.TaskEstadisticas;
import com.example.lmdl_app.tasks.TaskEstadoAlarma;

public class EstadoAlarma extends AppCompatActivity {
    private String tag = "EstadoAlarma";

    private ImageView candado;
    private Button botonCambiarEst;
    private String cod_sistema;
    private String usuario;
    private String estado_actual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2_4_estado_alarma);

        this.botonCambiarEst = this.findViewById(R.id.botonCambiar);
        this.candado = this.findViewById(R.id.imageEstado);
        this.cod_sistema = getIntent().getStringExtra("cod_sistema");
        this.usuario = getIntent().getStringExtra("usuario");
        loadEstadoAlarma();

        botonCambiarEst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(tag, "Estado actual: "+estado_actual);
                cambiarEstadoAlarma(estado_actual);
            }
        });

    }

    private void loadEstadoAlarma() {
        new TaskEstadoAlarma(this).
                execute("http://192.168.1.109:8080/LMDL_SERVER2/GetEstadoAlarma?cod_sistema="+cod_sistema);

    }

    private void cambiarEstadoAlarma(String estado_actual){
        if (estado_actual.contains("0")){
            setEstado_actual("1");
            new TaskEstadoAlarma(this).
                    execute("http://192.168.1.109:8080/LMDL_SERVER2/CambiarEstadoAlarma?cod_sistema="+cod_sistema+"&usuario="+usuario+"&estado="+1);
        }
        else {
            setEstado_actual("0");
            new TaskEstadoAlarma(this).
                    execute("http://192.168.1.109:8080/LMDL_SERVER2/CambiarEstadoAlarma?cod_sistema="+cod_sistema+"&usuario="+usuario+"&estado="+0);

        }
    }

    public void setEstado_actual(String estado_recibido){
        this.estado_actual=estado_recibido;
        cambiarFoto();
    }

    public void cambiarFoto (){
        if (estado_actual.contains("0")){
            candado.setImageResource(R.drawable.abierto);
        }
        else {
            candado.setImageResource(R.drawable.cerrado);
        }
    }

}