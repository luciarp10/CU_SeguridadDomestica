package com.example.lmdl_app;

import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lmdl_app.data.Actuador;
import com.example.lmdl_app.data.Registro_actuador;
import com.example.lmdl_app.tasks.TaskGetRegistrosSimulaciones;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Array;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;


public class SimulacionesProgramadas extends AppCompatActivity {

    private String tag = "RegistrosSimulaciones";
    private TableLayout tabla_registros;
    private String cod_sistema = "";
    private String usuariologin = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2_3_1_sim_prog);
        getSupportActionBar().setTitle("LMDL-PROGRAMAR SIMULACION");

        cod_sistema = getIntent().getStringExtra("cod_sistema");
        this.tabla_registros = this.findViewById(R.id.tabla_registros_sim);
        loadRegistrosSimulaciones();
    }

    private void loadRegistrosSimulaciones() {
        new TaskGetRegistrosSimulaciones(this).
                execute(Comun.ruta_servlets+"GetRegistrosActuadores?cod_sistema="+cod_sistema);
    }

    public void setTablaSimulaciones(JSONArray jsonActuadores, JSONArray jsonarrayRegs) {
        Log.e(tag,"Loading sensores " + jsonActuadores);
        Log.e(tag,"Loading registros " + jsonarrayRegs);

        ArrayList<Actuador> actuadores_sistema = new ArrayList<>();
        ArrayList<Registro_actuador> registros_simulaciones = new ArrayList<>();

        try {

            for (int i = 0; i < jsonActuadores.length(); i++) { //Guardar los sensores que hay registrados en el sistema
                JSONObject jsonobject = jsonActuadores.getJSONObject(i);
                Actuador actuador_recibido = new Actuador();
                actuador_recibido.setId_actuador(jsonobject.getInt("id_actuador"));
                actuador_recibido.setTipo(jsonobject.getString("tipo"));
                actuadores_sistema.add(actuador_recibido);
            }
            for (int i=0; i<jsonarrayRegs.length();i++){
                JSONObject jsonObject = jsonarrayRegs.getJSONObject(i);

                java.sql.Date date = Date.valueOf(Comun.transformarFecha(jsonObject.getString("fecha_on")));
                java.sql.Time hora = Time.valueOf(Comun.transformarHora(jsonObject.getString("hora_on")));
                Registro_actuador registro_recibido = new Registro_actuador();
                registro_recibido.setFecha_on((Date) date);
                registro_recibido.setHora_on(hora);
                registro_recibido.setDuracion(jsonObject.getInt("duracion"));
                registro_recibido.setId_actuador_actuador(jsonObject.getInt("id_actuador_actuador"));
                registros_simulaciones.add(registro_recibido);
            }


        }catch (Exception e)
        {
            Log.e(tag,"Error: " + e);
        }

        String[] hora_dividida;

        for(int i = 0; i < registros_simulaciones.size(); i++){
            TableRow tr =  new TableRow(this);
            TextView c1 = new TextView(this);
            c1.setText(registros_simulaciones.get(i).getFecha_on().toString());
            TextView c2 = new TextView(this);
            c2.setText(""+registros_simulaciones.get(i).getHora_on());
            TextView c3 = new TextView(this);
            c3.setText(buscarTipoActuador(registros_simulaciones.get(i).getId_actuador_actuador(), actuadores_sistema));
            TextView c4 = new TextView(this);
            c4.setText(""+registros_simulaciones.get(i).getDuracion());
            tr.addView(c1);
            tr.addView(c2);
            tr.addView(c3);
            tr.addView(c4);
            tabla_registros.addView(tr);
        }


    }

    private String buscarTipoActuador(int id_actuador_actuador, ArrayList<Actuador> actuadores_sistema) {
        Log.i(tag, ""+actuadores_sistema.size());
        for (int i=0;i<actuadores_sistema.size();i++){
            Log.i(tag, ""+actuadores_sistema.get(i).getId_actuador()+ "\n"+id_actuador_actuador);
            if(actuadores_sistema.get(i).getId_actuador()==id_actuador_actuador){
                return actuadores_sistema.get(i).getTipo();
            }
        }
        return "-1";
    }

}
