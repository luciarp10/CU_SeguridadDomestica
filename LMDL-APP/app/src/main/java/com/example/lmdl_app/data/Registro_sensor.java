package com.example.lmdl_app.data;

import java.sql.Date;
import java.sql.Timestamp;

public class Registro_sensor{
    private Date fecha;
    private double valor;
    private Timestamp hora;
    private int id_sensor_sensor;

    public Registro_sensor(Date fecha, double valor, Timestamp hora, int id_sensor_sensor) {
        this.fecha = fecha;
        this.valor = valor;
        this.hora = hora;
        this.id_sensor_sensor = id_sensor_sensor;
    }

    public Registro_sensor() {
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
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