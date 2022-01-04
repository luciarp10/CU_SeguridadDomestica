package com.example.lmdl_app.data;

public class Identificacion {
    private int codigo_qr;
    private String password;
    private String nombre;
    private int cod_sistema_sistema_seguridad;
    private boolean admin;

    public Identificacion(int codigo_qr, String password, String nombre, int cod_sistema_sistema_seguridad, boolean admin) {
        this.codigo_qr = codigo_qr;
        this.password = password;
        this.nombre = nombre;
        this.cod_sistema_sistema_seguridad = cod_sistema_sistema_seguridad;
        this.admin=admin;
    }

    public Identificacion() {

    }

    public int getCodigo_qr() {
        return codigo_qr;
    }

    public void setCodigo_qr(int codigo_qr) {
        this.codigo_qr = codigo_qr;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCod_sistema_sistema_seguridad() {
        return cod_sistema_sistema_seguridad;
    }

    public void setCod_sistema_sistema_seguridad(int cod_sistema_sistema_seguridad) {
        this.cod_sistema_sistema_seguridad = cod_sistema_sistema_seguridad;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }



}

