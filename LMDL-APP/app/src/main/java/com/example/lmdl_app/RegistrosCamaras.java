package com.example.lmdl_app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class RegistrosCamaras extends AppCompatActivity {

    private byte[] imgBytes; //img recibida en bytes con json

    Button btEncode, btDecode;
    TextView textView;
    ImageView imageView;
    String sImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2_2_1_camaras);


        btEncode = findViewById(R.id.bt_encode);
        btDecode = findViewById(R.id.bt_decode);
        textView = findViewById(R.id.text_view);
        imageView = findViewById(R.id.image_view);

        btEncode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Picasso.get().load("\"http://i.imgur.com/DvpvklR.png\"").into(imageView);
                /*
                File imgFile =  new File("/sdcard/temp.png");
                if (imgFile.exists()) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    ImageView myImage = (ImageView) findViewById(R.id.image_view);
                    myImage.setImageBitmap(myBitmap);
                }
                sImage = Base64.encodeToString(imgBytes, Base64.DEFAULT);
                textView.setText(sImage);

*/
            }
        });

        btDecode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DEcode base 64 string
                byte[] bytes = Base64.decode(sImage, Base64.DEFAULT);
                //Initialize bitmap
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0
                        , bytes.length);
                //Set bitmap on image view
                imageView.setImageBitmap(bitmap);
            }
        });


    }
}
