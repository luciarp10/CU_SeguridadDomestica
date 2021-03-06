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

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.lmdl_app.R;
import com.example.lmdl_app.data.Alerta;
import com.example.lmdl_app.tasks.TaskNotificaciones;

public class MenuSistema  extends AppCompatActivity {
    private Button botonHabitaciones;
    private Button botonSeguridad;
    private Button botonEstadisticas;

    private String codigoSist = ""; //parametro que pasa main
    private String usuariologin = ""; //Parametro que pasa main

    private final static String CHANNEL_ID = "cod_sistema";
    private final static int NOTIFICATION_ID=0;
    private Alerta ultima_alerta=new Alerta();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_0_menu);
        getSupportActionBar().setTitle("LMDL-MENÚ SISTEMA");

        //Inicializamos botones y spinners
        this.botonHabitaciones = this.findViewById(R.id.botonHabi);
        this.botonSeguridad = this.findViewById(R.id.botonSeguri);
        this.botonEstadisticas = this.findViewById(R.id.botonEstadi);

        codigoSist = getIntent().getStringExtra("cod_sistema");
        usuariologin = getIntent().getStringExtra("usuario");
        botonHabitaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuSistema.this, Habitaciones.class);
                i.putExtra("cod_sistema", codigoSist); //Se pasa el codigo de sistema al resto de actividades
                startActivity(i);
                //finish();
            }
        });

        botonSeguridad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuSistema.this, Seguridad.class);
                i.putExtra("cod_sistema", codigoSist);
                i.putExtra("usuario", usuariologin);
                startActivity(i);
                //finish();
            }
        });

        botonEstadisticas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuSistema.this, Estadisticas.class);
                i.putExtra("cod_sistema", codigoSist);
                startActivity(i);
                //finish();
            }


        });

        ejecutarTask();
    }


    public void ejecutarTask() {

        new TaskNotificaciones(this).
                execute(Comun.ruta_servlets + "GetUltimaAlerta?cod_sistema=" + codigoSist);
    }

    public Alerta getUltima_alerta() {
        return ultima_alerta;
    }

    public void setUltima_alerta(Alerta ultima_alerta) {
        this.ultima_alerta = ultima_alerta;
    }

    public void creaNotificacion(String infoAlerta){
        //Si la ha generado él mismo, no se le muestra.
        if(!infoAlerta.contains(usuariologin)){
            createNotificationChannel();
            createNotification("LMDL-ADVANCED SECURITY", infoAlerta);
        }
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
        builder.setColor(Color.YELLOW);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.BLUE, 1000, 1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);
        //Show the notification
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }
}