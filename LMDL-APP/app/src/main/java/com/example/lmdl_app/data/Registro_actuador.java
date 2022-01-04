package com.example.lmdl_app.data;

import java.sql.Date;
import java.sql.Timestamp;

public class Registro_actuador{
    private Timestamp hora_on;
    private Date fecha_on;
    private float duracion;
    private int id_actuador_actuador;

    public Registro_actuador(Timestamp hora_on, Date fecha_on, float duracion, int id_actuador_actuador) {
        this.hora_on = hora_on;
        this.fecha_on = fecha_on;
        this.duracion = duracion;
        this.id_actuador_actuador = id_actuador_actuador;
    }

    public Registro_actuador() {
    }



    public Timestamp getHora_on() {
        return hora_on;
    }

    public void setHora_on(Timestamp hora_on) {
        this.hora_on = hora_on;
    }

    public Date getFecha_on() {
        return fecha_on;
    }

    public void setFecha_on(Date fecha_on) {
        this.fecha_on = fecha_on;
    }

    public float getDuracion() {
        return duracion;
    }

    public void setDuracion(float duracion) {
        this.duracion = duracion;
    }

    public int getId_actuador_actuador() {
        return id_actuador_actuador;
    }

    public void setId_actuador_actuador(int id_actuador_actuador) {
        this.id_actuador_actuador = id_actuador_actuador;
    }
}
