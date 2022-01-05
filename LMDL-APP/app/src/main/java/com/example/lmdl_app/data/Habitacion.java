package com.example.lmdl_app.data;

public class Habitacion {
    private int id_habitacion;
    private int n_puertas;
    private int n_ventanas;
    private int tamanno;
    private int cod_sistema_sistema_seguridad;
    private String descriptivo;

    public Habitacion(int id_habitacion, int n_puertas, int n_ventanas, int tamanno, int cod_sistema_sistema_seguridad, String descriptivo) {
        this.id_habitacion = id_habitacion;
        this.n_puertas = n_puertas;
        this.n_ventanas = n_ventanas;
        this.tamanno = tamanno;
        this.cod_sistema_sistema_seguridad = cod_sistema_sistema_seguridad;
        this.descriptivo=descriptivo;
    }

    public Habitacion() {
    }

    public int getId_habitacion() {
        return id_habitacion;
    }

    public void setId_habitacion(int id_habitacion) {
        this.id_habitacion = id_habitacion;
    }

    public int getN_puertas() {
        return n_puertas;
    }

    public void setN_puertas(int n_puertas) {
        this.n_puertas = n_puertas;
    }

    public int getN_ventanas() {
        return n_ventanas;
    }

    public void setN_ventanas(int n_ventanas) {
        this.n_ventanas = n_ventanas;
    }

    public int getTamanno() {
        return tamanno;
    }

    public void setTamanno(int tamanno) {
        this.tamanno = tamanno;
    }

    public int getCod_sistema_sistema_seguridad() {
        return cod_sistema_sistema_seguridad;
    }

    public void setCod_sistema_sistema_seguridad(int cod_sistema_sistema_seguridad) {
        this.cod_sistema_sistema_seguridad = cod_sistema_sistema_seguridad;
    }

    public String getDescriptivo() {
        return descriptivo;
    }

    public void setDescriptivo(String descriptivo) {
        this.descriptivo = descriptivo;
    }


    @Override
    public String toString() {
        return "Habitacion{" +
                "id_habitacion=" + id_habitacion +
                ", n_puertas=" + n_puertas +
                ", n_ventanas=" + n_ventanas +
                ", tamanno=" + tamanno +
                ", cod_sistema_sistema_seguridad=" + cod_sistema_sistema_seguridad +
                ", descriptivo='" + descriptivo + '\'' +
                '}';
    }
}

