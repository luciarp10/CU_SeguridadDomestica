package com.example.lmdl_app.data;

public class Cliente {
    private String dni;
    private int id_cliente;
    private String direccion;
    private String telefono;
    private String nombre;

    public Cliente(String dni, int id_cliente, String direccion, String telefono, String nombre) {
        this.dni = dni;
        this.id_cliente = id_cliente;
        this.direccion = direccion;
        this.telefono = telefono;
        this.nombre = nombre;
    }

    public Cliente() {
    }


    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}