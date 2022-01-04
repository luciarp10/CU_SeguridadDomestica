package com.example.lmdl_app.data;

import java.util.ArrayList;

public class Habitacion {
    private int id;
    private int num_puertas;
    private int num_ventanas;
    private int tamanno;
    private ArrayList<Actuador> actuadores;
    private ArrayList<Sensor> sensores;
    private String descriptivo;

    public Habitacion(int id, String nombre) {
        this.id = id;
        this.descriptivo = nombre;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNum_puertas() {
        return num_puertas;
    }

    public void setNum_puertas(int num_puertas) {
        this.num_puertas = num_puertas;
    }

    public int getNum_ventanas() {
        return num_ventanas;
    }

    public void setNum_ventanas(int num_ventanas) {
        this.num_ventanas = num_ventanas;
    }

    public int getTamanno() {
        return tamanno;
    }

    public void setTamanno(int tamanno) {
        this.tamanno = tamanno;
    }

    public ArrayList<Actuador> getActuadores() {
        return actuadores;
    }

    public void setActuadores(ArrayList<Actuador> actuadores) {
        this.actuadores = actuadores;
    }

    public ArrayList<Sensor> getSensores() {
        return sensores;
    }

    public void setSensores(ArrayList<Sensor> sensores) {
        this.sensores = sensores;
    }

    public String getDescriptivo() {
        return descriptivo;
    }

    public void setDescriptivo(String descriptivo) {
        this.descriptivo = descriptivo;
    }
}
