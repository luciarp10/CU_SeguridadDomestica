package com.example.lmdl_app;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lmdl_app.R;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class VerQR extends AppCompatActivity {

    private ImageView qR;
    private String textQr = "";
    private int codigo_qr = 1234567; //Codigo recibido en json
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2_5_qr);

        //Se enlaza la imagen del layout
        qR = findViewById(R.id.imgQR);

        //Se genera valor random
        int random = (int)(Math.random()*999999999);

        //Texto a generar como QR
        textQr = ""+codigo_qr+random;

        //Se genera QR
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(textQr, BarcodeFormat.QR_CODE, 750, 750);
            qR.setImageBitmap(bitmap);
        } catch(Exception e) {

        }
    }

}