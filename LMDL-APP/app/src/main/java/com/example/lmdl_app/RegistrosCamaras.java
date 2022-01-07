package com.example.lmdl_app;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


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

            /*
            //Este es la funcion
            //imgBytes->array de bytes recibida
                Bitmap bitmap = BitmapFactory.decodeByteArray(imgBytes, 0
                        , bytes.length);
                imageView.setImageBitmap(bitmap);
            */
            //Todo esto va fuera simplemente era para hacer pruebas
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.foto);
                try {
                    //Initialize bitmap
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver()
                            , uri);
                    //Initialize bute stream
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    //Compress bitmap
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    //Initialize byte array
                    byte[] bytes = stream.toByteArray();
                    //Getbase 64 encoded string
                    sImage = Base64.encodeToString(bytes, Base64.DEFAULT);
                    //Set encoded text on text view
                    textView.setText(sImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
        //HAsta aqui
    }


}

