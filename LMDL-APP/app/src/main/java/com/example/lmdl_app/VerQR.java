package com.example.lmdl_app;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.lmdl_app.R;
import com.example.lmdl_app.tasks.TaskEstadisticas;
import com.example.lmdl_app.tasks.TaskGetQR;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONArray;
import org.json.JSONObject;

public class VerQR extends AppCompatActivity {
    private String tag = "VerQR";
    private ImageView qR;
    private String textQr = "";
    private int codigo_qr=-1; //Codigo recibido en json
    private String nombre_usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2_5_qr);
        nombre_usuario=getIntent().getStringExtra("usuario");

        //Se enlaza la imagen del layout
        qR = findViewById(R.id.imgQR);

        //Se genera valor random para añadir al codigo del usuario
        int random = (int)(Math.random()*999999999);

        loadCodigoQR();

    }

    private void loadCodigoQR() {
        new TaskGetQR(this).
                execute("http://192.168.1.109:8080/LMDL_SERVER2/GetCodQR?usuario="+nombre_usuario);

    }

    public void setCodUsuario(String jsonobjectCodigo) {
        Log.i(tag, "Codigo de usuario: "+jsonobjectCodigo);
        int random = (int)(Math.random()*999999999);
        textQr=jsonobjectCodigo+random;
        String[] textQr_split = textQr.split("\n");
        textQr=textQr_split[0]+textQr_split[1];
        Log.i(tag, "QR_generado: "+textQr);
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(textQr, BarcodeFormat.QR_CODE, 750, 750);
            qR.setImageBitmap(bitmap);
        } catch(Exception e) {}

    }
}