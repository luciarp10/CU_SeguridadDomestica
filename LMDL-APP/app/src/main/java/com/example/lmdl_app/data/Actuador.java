package com.example.lmdl_app.data;
public class Actuador {
    private int id_actuador;
    private String tipo;
    private int id_habitacion;

    public Actuador(int id_actuador, String tipo, int id_habitacion) {
        this.id_actuador = id_actuador;
        this.tipo = tipo;
        this.id_habitacion = id_habitacion;
    }

    public Actuador() {
    }


    public int getId_actuador() {
        return id_actuador;
    }

    public void setId_actuador(int id_actuador) {
        this.id_actuador = id_actuador;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getId_habitacion() {
        return id_habitacion;
    }

    public void setId_habitacion(int id_habitacion) {
        this.id_habitacion = id_habitacion;
    }

}
