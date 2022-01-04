package com.example.lmdl_app.data;

public class Sensor {
    private int id_sensor;
    private String tipo;
    private int id_habitacion_habitacion;

    public Sensor(int id_sensor, String tipo, int id_habitacion_habitacion) {
        this.id_sensor = id_sensor;
        this.tipo = tipo;
        this.id_habitacion_habitacion = id_habitacion_habitacion;
    }

    public Sensor() {
    }

    public int getId_sensor() {
        return id_sensor;
    }

    public void setId_sensor(int id_sensor) {
        this.id_sensor = id_sensor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getId_habitacion_habitacion() {
        return id_habitacion_habitacion;
    }

    public void setId_habitacion_habitacion(int id_habitacion_habitacion) {
        this.id_habitacion_habitacion = id_habitacion_habitacion;
    }
}
