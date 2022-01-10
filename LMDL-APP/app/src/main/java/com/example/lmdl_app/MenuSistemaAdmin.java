package com.example.lmdl_app;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.lmdl_app.tasks.TaskNotificaciones;

public class MenuSistemaAdmin extends AppCompatActivity {

    private Button botonHabitaciones;
    private Button botonSeguridad;
    private Button botonEstadisticas;
    private Button botonAdminUsers;

    private String codigoSist = ""; //parametro que pasa main
    private String usuariologin = ""; //Parametro que pasa main

    private final static String CHANNEL_ID = "cod_sistema";
    private final static int NOTIFICATION_ID=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_0_1_menu_admi);
        getSupportActionBar().setTitle("LMDL-MENÚ SISTEMA");
        //Inicializamos botones y spinners
        this.botonHabitaciones = this.findViewById(R.id.botonHab);
        this.botonSeguridad = this.findViewById(R.id.botonSeg);
        this.botonEstadisticas = this.findViewById(R.id.botonEst);
        this.botonAdminUsers = this.findViewById(R.id.botonAdminUser);

        codigoSist = getIntent().getStringExtra("cod_sistema");
        usuariologin = getIntent().getStringExtra("usuario");

        botonHabitaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuSistemaAdmin.this, Habitaciones.class);
                i.putExtra("cod_sistema", codigoSist); //Se pasa el codigo de sistema al resto de actividades
                startActivity(i);
                //finish();
            }
        });

        botonSeguridad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuSistemaAdmin.this, Seguridad.class);
                i.putExtra("cod_sistema", codigoSist);
                i.putExtra("usuario", usuariologin);
                startActivity(i);
                //finish();
            }
        });

        botonEstadisticas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuSistemaAdmin.this, Estadisticas.class);
                i.putExtra("cod_sistema", codigoSist);
                startActivity(i);
                //finish();
            }
        });

        botonAdminUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuSistemaAdmin.this, Usuarios.class);
                i.putExtra("cod_sistema", codigoSist);
                i.putExtra("usuario", usuariologin);
                startActivity(i);
                //finish();
            }
        });

        /*new TaskNotificaciones(this).
                execute(Comun.ruta_servlets+"GetUltimaAlerta?cod_sistema="+codigoSist);*/

    }
    public void creaNotificacion(String infoAlerta){
        createNotificationChannel();
        createNotification("Alert", infoAlerta);
    }

    //Method to create the notification channel in new versions
    private void createNotificationChannel()
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            CharSequence name = "Notificación";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    //Method to create a notfication with the title and the message
    private void createNotification(String title, String msn)
    {
        //Configure the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID);
        builder.setSmallIcon(R.drawable.logo_lmdl);
        builder.setContentTitle(title);
        builder.setContentText(msn);
        builder.setColor(Color.RED);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.BLUE, 1000, 1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);

        //Show the notification
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }

}