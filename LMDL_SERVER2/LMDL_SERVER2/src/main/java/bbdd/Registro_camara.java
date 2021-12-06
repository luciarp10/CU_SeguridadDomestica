package bbdd;

import java.sql.Date;
import java.sql.Timestamp;

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
}
