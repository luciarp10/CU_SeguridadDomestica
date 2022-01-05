package com.example.lmdl_app.data;

//import java.awt.Image;
//import android.graphics.*;
import android.os.Build;
import androidx.annotation.RequiresApi;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
//import javax.imageio.ImageIO;
//import logic.Log;

public class Registro_camara {
    private Date fecha;
    private String enlace_foto;
    private Timestamp hora;
    private int id_sensor_sensor;

    public Registro_camara(Date fecha, String enlace_foto, Timestamp hora, int id_sensor_sensor) {
        this.fecha = fecha;
        this.enlace_foto = enlace_foto;
        this.hora = hora;
        this.id_sensor_sensor = id_sensor_sensor;
    }

    public Registro_camara() {
    }



    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEnlace_foto() {
        return enlace_foto;
    }

    public void setEnlace_foto(String enlace_foto) {
        this.enlace_foto = enlace_foto;
    }

    public Timestamp getHora() {
        return hora;
    }

    public void setHora(Timestamp hora) {
        this.hora = hora;
    }

    public int getId_sensor_sensor() {
        return id_sensor_sensor;
    }

    public void setId_sensor_sensor(int id_sensor_sensor) {
        this.id_sensor_sensor = id_sensor_sensor;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String descargarFoto(String enlace) throws MalformedURLException, IOException{
        String ruta="/tmp/imagenes/"+LocalDate.now()+"_"+LocalTime.now(ZoneId.of("Europe/Madrid"))+".jpg";

        try{
            URL url = new URL(enlace);
            InputStream in = new BufferedInputStream(url.openStream());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n = 0;
            while (-1!=(n=in.read(buf)))
            {
                out.write(buf, 0, n);
            }
            out.close();
            in.close();
            byte[] response = out.toByteArray();

            FileOutputStream fos = new FileOutputStream(ruta);
            fos.write(response);
            fos.close();
        }
        catch (Exception e){
            System.out.println("error");
        }

        return ruta;
    }
}

