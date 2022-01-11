package com.example.lmdl_app;

import android.util.Log;

import java.sql.Date;
import java.util.Calendar;

public class Comun {
    public static String ruta_servlets = "http://192.168.1.109:8080/LMDL_SERVER2/";

    public Comun() {
    }

    public static String sumarDiasAFecha(Date fecha, int dias){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DAY_OF_YEAR, dias);
        return ""+calendar.getTime();
    }

    public static String transformarFecha(String fecha){
        String fecha_modificada=fecha;
        if(fecha.contains("Jan")){
            fecha_modificada=fecha.replace("Jan","01");
        }
        else if (fecha.contains("Feb")){
            fecha_modificada=fecha.replace("Feb","02");
        }
        else if (fecha.contains("Mar")){
            fecha_modificada=fecha.replace("Mar","03");
        }
        else if (fecha.contains("Apr")){
            fecha_modificada=fecha.replace("Apr","04");
        }
        else if (fecha.contains("May")){
            fecha_modificada=fecha.replace("May","05");
        }
        else if (fecha.contains("Jun")){
            fecha_modificada=fecha.replace("Jun","06");
        }
        else if (fecha.contains("Jul")){
            fecha_modificada=fecha.replace("Jul","07");
        }
        else if (fecha.contains("Aug")){
            fecha_modificada=fecha.replace("Aug","08");
        }
        else if (fecha.contains("Sep")){
            fecha_modificada=fecha.replace("Sep","09");
        }
        else if (fecha.contains("Oct")){
            fecha_modificada=fecha.replace("Oct","10");
        }
        else if (fecha.contains("Nov")){
            fecha_modificada=fecha.replace("Nov","11");
        }
        else if (fecha.contains("Dec")){
            fecha_modificada=fecha.replace("Dec","12");
        }
        fecha_modificada=fecha_modificada.replace(" ","-");
        fecha_modificada=fecha_modificada.replace(",","");
        String[] fecha_dividida = fecha_modificada.split("-");
        fecha_modificada=fecha_dividida[2]+"-"+fecha_dividida[0]+"-"+fecha_dividida[1];
        return fecha_modificada;
    }

    public static String transformarHora(String hora){
        String hora_modificada;
        String[] hora_dividida = hora.split(":");
        String[] am_pm = hora_dividida[2].split(" ");
        if(am_pm[1].contains("PM")){
            hora_modificada=(Integer.parseInt(hora_dividida[0])+12)+":"+hora_dividida[1]+":"+am_pm[0];
        }
        else {
            hora_modificada=hora_dividida[0]+":"+hora_dividida[1]+":"+am_pm[0];
        }
        return hora_modificada;
    }

    public static boolean comprobarFormatoFecha(String fecha){
        String[] fecha_separada = fecha.split("-");
        if (fecha_separada.length!=3){
            return false;
        }
        else{
            if (fecha_separada[0].length()!=4 || fecha_separada[1].length()!=2 || fecha_separada[1].length()!=2){
                return false;
            }
            else if(Integer.parseInt(fecha_separada[1]) > 12 || Integer.parseInt(fecha_separada[1])<1 || Integer.parseInt(fecha_separada[2])>31
                    || Integer.parseInt(fecha_separada[2])<1){
                return false;
            }

        }
        return true;
    }
}
