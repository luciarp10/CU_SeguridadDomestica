package com.example.lmdl_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lmdl_app.data.Identificacion;
import com.example.lmdl_app.tasks.TaskUsuarios;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Usuarios extends AppCompatActivity {

    private String tag = "Usuarios";

    private TextView mensajeSinUsuarios;
    private Button botonCreaUser;
    private Button botonBorraUser;
    private Spinner usuariosSistema;
    private String cod_sistema = ""; //parametro que pasa main
    private String usuariologin = "";
    private final Context context;

    ArrayList<String> usuarios = new ArrayList<>();

    public Usuarios() {
        super();
        this.context = this;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4_usuarios);

        this.botonCreaUser = this.findViewById(R.id.botonAñadirU);
        this.botonBorraUser = this.findViewById(R.id.botonBorrUser);
        this.usuariosSistema = this.findViewById(R.id.spinnerListaCamaras);
        this.mensajeSinUsuarios = this.findViewById(R.id.textViewSinUsuarios);

        this.cod_sistema = getIntent().getStringExtra("cod_sistema");
        usuariologin = getIntent().getStringExtra("usuario");


        botonCreaUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Usuarios.this, NuevoUsuario.class);
                i.putExtra("cod_sistema", cod_sistema);
                startActivity(i);
                Usuarios.super.finish();
            }
        });
        botonBorraUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre_seleccionado = usuarios.get(usuariosSistema.getSelectedItemPosition());
                borrarUsuarioSistema(nombre_seleccionado);
            }
        });

        loadUsuariosSistema();
    }

    private void loadUsuariosSistema() {
        new TaskUsuarios(this).
                execute("http://192.168.1.109:8080/LMDL_SERVER2/GetUsuariosSistema?cod_sistema="+cod_sistema);
    }

    private void borrarUsuarioSistema(String nombre){
        new TaskUsuarios(this).
                execute("http://192.168.1.109:8080/LMDL_SERVER2/BorrarUsuarioSistema?nombre_usuario="+nombre);
    }

    public void setListUsuarios(JSONArray jsonUsuarios) {
        Log.e(tag,"Loading usuarios " + jsonUsuarios);
        usuarios = new ArrayList<>();
        try {
            for (int i = 0; i < jsonUsuarios.length(); i++) {
                JSONObject jsonobject = jsonUsuarios.getJSONObject(i);
                Identificacion usuario_recibido = new Identificacion();
                usuario_recibido.setNombre(jsonobject.getString("nombre"));
                if(!usuario_recibido.getNombre().equals(usuariologin)){ //Para que no pueda borrarse a sí mismo
                    usuarios.add(usuario_recibido.getNombre());
                }
                botonBorraUser.setEnabled(true);
            }
            if(usuarios.size()==0){
                mensajeSinUsuarios.setText("No hay más usuarios registrados.");
                botonBorraUser.setEnabled(false);
            }
            usuariosSistema.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, usuarios));
        }catch (Exception e)
        {
            Log.e(tag,"Error: " + e);
        }
    }

    public void actualizar() {
        mensajeSinUsuarios.setText("");
        loadUsuariosSistema();
    }
}