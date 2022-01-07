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
                loadImagenSeleccionada(registro_camaras.get(reg_elegido).getEnlace_foto());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        loadRegistrosFecha();
    }

    private void loadRegistrosFecha() {
        new TaskCamaras(this).
                execute("http://192.168.1.109:8080/LMDL_SERVER2/GetRegistrosImagenes?cod_sistema="+cod_sistema+"&fecha="+fecha_mostrar.getText());
    }

    private void loadImagenSeleccionada(String ruta) {
        new TaskCamaras(this).
                execute("http://192.168.1.109:8080/LMDL_SERVER2/GetImagen?enlace_foto="+ruta);
    }

    public void setListImagenes(JSONArray jsonRegistros) {
        Log.i(tag, "Lista de imagenes: "+jsonRegistros);
        ArrayList<String> arrayHoras=new ArrayList<>();
        registro_camaras = new ArrayList<>();
        try {

            for (int i = 0; i < jsonRegistros.length(); i++) {
                JSONObject jsonobject = jsonRegistros.getJSONObject(i);

                java.sql.Date date = Date.valueOf(transformarFecha(jsonobject.getString("fecha")));
                java.sql.Timestamp hora = Timestamp.valueOf(transformarHora(jsonobject.getString("hora")));

                Registro_camara registro_recibido = new Registro_camara();
                registro_recibido.setFecha(date);
                registro_recibido.setHora(hora);
                registro_recibido.setId_sensor_sensor(jsonobject.getInt("id_sensor_sensor"));
                registro_recibido.setEnlace_foto(jsonobject.getString("enlace_foto"));

                String[] hora_dividida=registro_recibido.getHora().toString().split(" ");
                arrayHoras.add(hora_dividida[1]);
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

    private String transformarFecha(String fecha){
        String fecha_modificada=fecha;
        if(fecha.contains("Jan")){
            fecha_modificada=fecha.replace("Jan","01");
        }
        else if (fecha.contains("Feb")){
            fecha_modificada=fecha.replace("Feb","02");
        }
        else if (fecha.contains("Mar")){
            fecha_modificada=fecha.replace("Mar","03");
        }
        else if (fecha.contains("Apr")){
            fecha_modificada=fecha.replace("Apr","04");
        }
        else if (fecha.contains("May")){
            fecha_modificada=fecha.replace("May","05");
        }
        else if (fecha.contains("Jun")){
            fecha_modificada=fecha.replace("Jun","06");
        }
        else if (fecha.contains("Jul")){
            fecha_modificada=fecha.replace("Jul","07");
        }
        else if (fecha.contains("Aug")){
            fecha_modificada=fecha.replace("Aug","08");
        }
        else if (fecha.contains("Sep")){
            fecha_modificada=fecha.replace("Sep","09");
        }
        else if (fecha.contains("Oct")){
            fecha_modificada=fecha.replace("Oct","10");
        }
        else if (fecha.contains("Nov")){
            fecha_modificada=fecha.replace("Nov","11");
        }
        else if (fecha.contains("Dec")){
            fecha_modificada=fecha.replace("Dec","12");
        }
        fecha_modificada=fecha_modificada.replace(" ","-");
        fecha_modificada=fecha_modificada.replace(",","");
        String[] fecha_dividida = fecha_modificada.split("-");
        fecha_modificada=fecha_dividida[2]+"-"+fecha_dividida[0]+"-"+fecha_dividida[1];
        return fecha_modificada;
    }

    private String transformarHora(String hora){
        String hora_modificada;
        String[] hora_dividida = hora.split(" ");
        String fecha = hora_dividida[0]+" "+hora_dividida[1]+" "+hora_dividida[2];
        fecha=transformarFecha(fecha);
        hora_modificada=hora_dividida[3];
        hora_modificada=fecha+" "+hora_modificada;
        return hora_modificada;
    }


}

