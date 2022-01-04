package com.example.lmdl_app.data;

public class Topic {
    private String id_topic;
    private int id_sistema;
    private int id_habitacion;
    private int id_sensor_actuador;
    private float valor;

    public Topic(String id_topic, int id_sistema, int id_habitacion, int id_sensor_actuador,float valor) {
        this.id_topic = id_topic;
        this.id_sistema = id_sistema;
        this.id_habitacion = id_habitacion;
        this.id_sensor_actuador = id_sensor_actuador;
        this.valor = valor;
    }



    public Topic() {
        this.id_topic="";
        this.id_sistema=0;
        this.id_habitacion=0;
        this.id_sensor_actuador=0;
        this.setValor(0);
    }



    public String getId_topic() {
        return id_topic;
    }

    public void setId_topic(String id_topic) {
        this.id_topic = id_topic;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public int getId_sensor_actuador() {
        return id_sensor_actuador;
    }

    public void setId_sensor_actuador(int id_sensor) {
        this.id_sensor_actuador= id_sensor;
    }

    public int getId_habitacion() {
        return id_habitacion;
    }

    public void setId_habitacion(int id_habitacion) {
        this.id_habitacion = id_habitacion;
    }

    public int getId_sistema() {
        return id_sistema;
    }

    public void setId_sistema(int id_sistema) {
        this.id_sistema = id_sistema;
    }


}
