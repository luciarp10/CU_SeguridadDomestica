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
import java.sql.Time;
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
                execute(Comun.ruta_servlets+"GetRegistrosAlertas?cod_sistema="+cod_sistema);
    }

    public void setTablaRegistros(JSONArray jsonarrayRegs) throws JSONException {
        Log.i(tag, "Registros recibidos: "+jsonarrayRegs.length());
        tabla_registros.setStretchAllColumns(true);
        tabla_registros.bringToFront();
        ArrayList<Alerta> registros_leidos = new ArrayList<Alerta>();

        for (int i = 0; i < jsonarrayRegs.length(); i++) {
            JSONObject jsonobject = jsonarrayRegs.getJSONObject(i);
            Alerta alerta_recibida = new Alerta();
            alerta_recibida.setInfo(jsonobject.getString("info"));
            java.sql.Date date = Date.valueOf(Comun.transformarFecha(jsonobject.getString("fecha")));
            alerta_recibida.setFecha(date);
            alerta_recibida.setHora(Time.valueOf(Comun.transformarHora(jsonobject.getString("hora"))));
            alerta_recibida.setCod_sistema_sistema_seguridad(jsonobject.getInt("cod_sistema_sistema_seguridad"));

            registros_leidos.add(alerta_recibida);

        }


        for(int i = 0; i < registros_leidos.size(); i++){
            TableRow tr =  new TableRow(this);
            TextView c1 = new TextView(this);
            c1.setText(registros_leidos.get(i).getFecha().toString());
            TextView c2 = new TextView(this);
            c2.setText(""+registros_leidos.get(i).getHora());
            TextView c3 = new TextView(this);
            String columna_info = String.valueOf(registros_leidos.get(i).getInfo());
            c3.setText(columna_info);
            tr.addView(c1);
            tr.addView(c2);
            tr.addView(c3);
            tabla_registros.addView(tr);
        }
    }

}