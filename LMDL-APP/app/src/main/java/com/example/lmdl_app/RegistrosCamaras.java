package com.example.lmdl_app;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lmdl_app.data.Habitacion;
import com.example.lmdl_app.data.Registro_camara;
import com.example.lmdl_app.tasks.TaskCamaras;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;


public class RegistrosCamaras extends AppCompatActivity {

    String tag="RegistrosCamaras";
    private byte[] imgBytes; //img recibida en bytes con json
    private TextView fecha_mostrar;
    private Spinner fotos_del_dia;
    private ImageView imagen;
    private TextView mensajeError;

    private String cam_seleccionada;
    private ArrayList<Registro_camara> registro_camaras = new ArrayList<>();

    private String cod_sistema = ""; //parametro que pasa main
    private String usuariologin = ""; //Parametro que pasa main
    private final Context context;



    public RegistrosCamaras() {
        super();
        this.context = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2_2_1_camaras);
        getSupportActionBar().setTitle("LMDL-FOTOS");

        this.fecha_mostrar=this.findViewById(R.id.textDateCam);
        this.fotos_del_dia=this.findViewById(R.id.spinnerListaFotos);
        this.imagen=this.findViewById(R.id.image_view_foto);
        this.mensajeError=this.findViewById(R.id.mensajeErrorNoFotos);

        fecha_mostrar.setText(getIntent().getStringExtra("fecha_introducida"));
        cam_seleccionada=getIntent().getStringExtra("camara_seleccionada");
        cod_sistema = getIntent().getStringExtra("cod_sistema");
        usuariologin = getIntent().getStringExtra("usuario");


        fotos_del_dia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int reg_elegido= fotos_del_dia.getSelectedItemPosition();
                mensajeError.setText("");
                loadImagenSeleccionada(registro_camaras.get(reg_elegido).getEnlace_foto());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        mensajeError.setText("");
        loadRegistrosFecha();
    }

    private void loadRegistrosFecha() {
        new TaskCamaras(this).
                execute(Comun.ruta_servlets+"GetRegistrosImagenes?cod_sistema="+cod_sistema+"&fecha="+fecha_mostrar.getText()+"&cam_select="+cam_seleccionada);
    }

    private void loadImagenSeleccionada(String ruta) {
        new TaskCamaras(this).
                execute(Comun.ruta_servlets+"GetImagen?enlace_foto="+ruta);
    }

    public void setListImagenes(JSONArray jsonRegistros) {
        Log.i(tag, "Lista de imagenes: "+jsonRegistros);
        ArrayList<String> arrayHoras=new ArrayList<>();
        registro_camaras = new ArrayList<>();
        try {

            for (int i = 0; i < jsonRegistros.length(); i++) {
                JSONObject jsonobject = jsonRegistros.getJSONObject(i);

                java.sql.Date date = Date.valueOf(Comun.transformarFecha(jsonobject.getString("fecha")));
                Time hora = Time.valueOf(Comun.transformarHora(jsonobject.getString("hora")));

                Registro_camara registro_recibido = new Registro_camara();
                registro_recibido.setFecha(date);
                registro_recibido.setHora(hora);
                registro_recibido.setId_sensor_sensor(jsonobject.getInt("id_sensor_sensor"));
                registro_recibido.setEnlace_foto(jsonobject.getString("enlace_foto"));

                arrayHoras.add(""+hora);
                registro_camaras.add(registro_recibido);
            }
            if(arrayHoras.size()==0){
                mensajeError.setText("No hay fotos guardadas en la fecha seleccionada.");
            }
            fotos_del_dia.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, arrayHoras));
        }catch (Exception e)
        {
            Log.e(tag,"Error: " + e);
        }
    }

    public void transformarFoto(String foto_bytes ) {
        if (foto_bytes.equals("")){
            mensajeError.setText("Este registro corresponde a una simulación \nde carga masiva de la base de datos.");
            imagen.setImageResource(R.drawable.logo_lmdl);
        }
        else{
            Log.i(tag, "Imagen: "+foto_bytes);
            //Quitar corchetes a la cadena recibida
            foto_bytes=foto_bytes.substring(1,foto_bytes.length()-2);
            //separar por comas
            String[] bytes = foto_bytes.split(",");

            //ArrayList pasando cada cadena a tipo Byte
            ArrayList<Byte> bytes_int=new ArrayList<>();
            for (int i=0;i<bytes.length;i++){
                bytes_int.add((byte) Integer.parseInt(bytes[i]));
            }
            //Pasar arrayList a array -> Byte[]
            imgBytes= new byte[bytes_int.size()];
            for (int i = 0; i < imgBytes.length; i++) {
                imgBytes[i] = (byte) bytes_int.get(i);
            }

            //Decodificar la imagen
            Bitmap bitmap = BitmapFactory.decodeByteArray(imgBytes, 0
                    , imgBytes.length);
            imagen.setImageBitmap(bitmap);
        }

    }



}

