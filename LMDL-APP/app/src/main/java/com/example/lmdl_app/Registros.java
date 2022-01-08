package com.example.lmdl_app;

import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lmdl_app.R;
import com.example.lmdl_app.data.Alerta;
import com.example.lmdl_app.data.Habitacion;
import com.example.lmdl_app.tasks.TaskEstadisticas;
import com.example.lmdl_app.tasks.TaskGetRegistrosAlertas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Registros extends AppCompatActivity {
    private String tag = "Registros";
    private TableLayout tabla_registros;
    private int cod_sistema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2_1_registros);

        cod_sistema = Integer.parseInt(getIntent().getStringExtra("cod_sistema"));
        this.tabla_registros = this.findViewById(R.id.tablaRegistros);
        loadRegistrosAlertas();
    }

    private void loadRegistrosAlertas() {
        new TaskGetRegistrosAlertas(this).
                execute("http://192.168.1.109:8080/LMDL_SERVER2/GetRegistrosAlertas?cod_sistema="+cod_sistema);
    }

    public void setTablaRegistros(JSONArray jsonarrayRegs) throws JSONException {
        Log.i(tag, "Registros recibidos: "+jsonarrayRegs);
        tabla_registros.setStretchAllColumns(true);
        tabla_registros.bringToFront();
        ArrayList<Alerta> registros_leidos = new ArrayList<Alerta>();

        for (int i = 0; i < jsonarrayRegs.length(); i++) {
            JSONObject jsonobject = jsonarrayRegs.getJSONObject(i);
            Alerta alerta_recibida = new Alerta();
            alerta_recibida.setInfo(jsonobject.getString("info"));
            java.sql.Date date = Date.valueOf(transformarFecha(jsonobject.getString("fecha")));
            java.sql.Timestamp hora = Timestamp.valueOf(transformarHora(jsonobject.getString("hora")));
            alerta_recibida.setFecha(date);
            alerta_recibida.setHora(hora);
            alerta_recibida.setCod_sistema_sistema_seguridad(jsonobject.getInt("cod_sistema_sistema_seguridad"));

            registros_leidos.add(alerta_recibida);

        }

        String[] hora_dividida;

        for(int i = 0; i < registros_leidos.size(); i++){
            TableRow tr =  new TableRow(this);
            TextView c1 = new TextView(this);
            c1.setText(registros_leidos.get(i).getFecha().toString());
            TextView c2 = new TextView(this);
            hora_dividida = String.valueOf(registros_leidos.get(i).getHora()).split(" ");
            c2.setText(hora_dividida[1]);
            TextView c3 = new TextView(this);
            String columna_info = String.valueOf(registros_leidos.get(i).getInfo());
            c3.setText(columna_info);
            tr.addView(c1);
            tr.addView(c2);
            tr.addView(c3);
            tabla_registros.addView(tr);
        }
    }

    private String transformarFecha(String fecha){
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

    private String transformarHora(String hora){
        String hora_modificada;
        String[] hora_dividida = hora.split(" ");
        String fecha = hora_dividida[0]+" "+hora_dividida[1]+" "+hora_dividida[2];
        fecha=transformarFecha(fecha);
        String[] hh_mm_ss = hora_dividida[3].split(":");
        if(hora_dividida[4].contains("PM")){
            hora_modificada=(Integer.parseInt(hh_mm_ss[0])+12)+":"+hh_mm_ss[1]+":"+hh_mm_ss[2];
        }
        else if (hh_mm_ss[0].contains("12")){
            hora_modificada="00:"+hh_mm_ss[1]+":"+hh_mm_ss[2];
        }
        else {
            hora_modificada=hora_dividida[3];
        }
        hora_modificada=fecha+" "+hora_modificada;
        return hora_modificada;
    }
}