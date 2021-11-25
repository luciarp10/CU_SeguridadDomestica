package bbdd;

import java.sql.Date;
import java.sql.Timestamp;

public class Registro_sensor{
    private Date fecha_user;
    private float valor_user;
    private Timestamp hora_user;
    private int id_sensor_sensor;

    public Registro_sensor(Date fecha_user, float valor_user, Timestamp hora_user, int id_sensor_sensor) {
        this.fecha_user = fecha_user;
        this.valor_user = valor_user;
        this.hora_user = hora_user;
        this.id_sensor_sensor = id_sensor_sensor;
    }

    public Date getFecha_user() {
        return fecha_user;
    }

    public void setFecha_user(Date fecha_user) {
        this.fecha_user = fecha_user;
    }

    public float getValor_user() {
        return valor_user;
    }

    public void setValor_user(float valor_user) {
        this.valor_user = valor_user;
    }

    public Timestamp getHora_user() {
        return hora_user;
    }

    public void setHora_user(Timestamp hora_user) {
        this.hora_user = hora_user;
    }

    public int getId_sensor_sensor() {
        return id_sensor_sensor;
    }

    public void setId_sensor_sensor(int id_sensor_sensor) {
        this.id_sensor_sensor = id_sensor_sensor;
    }
}
