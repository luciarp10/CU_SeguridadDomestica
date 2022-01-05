package com.example.lmdl_app.data;
import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author lucyr
 */
public class Alerta {
    private int id_alerta;
    private Date fecha;
    private Timestamp hora;
    private String info;
    private int cod_sistema_sistema_seguridad;

    public Alerta(int id_alerta, Date fecha, Timestamp hora, String info, int cod_sistema_sistema_seguridad) {
        this.id_alerta = id_alerta;
        this.fecha = fecha;
        this.hora = hora;
        this.info = info;
        this.cod_sistema_sistema_seguridad = cod_sistema_sistema_seguridad;
    }

    public Alerta() {
    }

    public int getId_alerta() {
        return id_alerta;
    }

    public void setId_alerta(int id_alerta) {
        this.id_alerta = id_alerta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Timestamp getHora() {
        return hora;
    }

    public void setHora(Timestamp hora) {
        this.hora = hora;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getCod_sistema_sistema_seguridad() {
        return cod_sistema_sistema_seguridad;
    }

    public void setCod_sistema_sistema_seguridad(int cod_sistema_sistema_seguridad) {
        this.cod_sistema_sistema_seguridad = cod_sistema_sistema_seguridad;
    }



}
