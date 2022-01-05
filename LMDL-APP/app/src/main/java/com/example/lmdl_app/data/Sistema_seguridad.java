package com.example.lmdl_app.data;

public class Sistema_seguridad {
    private int cod_sistema;
    private String direccion;
    private String nombre;
    private int id_cliente_cliente;
    private boolean estado;

    public Sistema_seguridad(int cod_sistema, String direccion, String nombre, int id_cliente_cliente, boolean estado) {
        this.cod_sistema = cod_sistema;
        this.direccion = direccion;
        this.nombre = nombre;
        this.id_cliente_cliente = id_cliente_cliente;
        this.estado=estado;
    }

    public Sistema_seguridad() {
    }



    public int getCod_sistema() {
        return cod_sistema;
    }

    public void setCod_sistema(int cod_sistema) {
        this.cod_sistema = cod_sistema;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId_cliente_cliente() {
        return id_cliente_cliente;
    }

    public void setId_cliente_cliente(int id_cliente_cliente) {
        this.id_cliente_cliente = id_cliente_cliente;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }


}