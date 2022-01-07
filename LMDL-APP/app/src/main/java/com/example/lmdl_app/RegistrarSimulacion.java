package com.example.lmdl_app;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lmdl_app.data.Actuador;
import com.example.lmdl_app.data.Habitacion;
import com.example.lmdl_app.tasks.TaskRegistrarSimulacion;
import com.example.lmdl_app.tasks.TaskSelectHabitacion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class RegistrarSimulacion extends AppCompatActivity {
    private String tag = "RegistrarSimulacion";
    private Button botonRegProgam;
    private Spinner selectorActuador;
    private EditText selectorTiempo;
    private TextView mensajeError;
    private TextView mensajeExito;

    private String usuariologin;
    private String cod_sistema;
    private final Context context;
    private ArrayList<Habitacion> listHabitacion = new ArrayList<>();
    private ArrayList<Actuador> lista_actuadores = new ArrayList<>();
    private ArrayList<String> arrayIdActuador= new ArrayList<>();
    private String tiempo;

    public RegistrarSimulacion() {
        super();
        this.context = this;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2_3_2_reg_sim);

        usuariologin = getIntent().getStringExtra("usuario");
        cod_sistema = getIntent().getStringExtra("cod_sistema");
        this.botonRegProgam = this.findViewById(R.id.botonRegSim);
        this.selectorActuador = this.findViewById(R.id.spinnerSensorSim);
        this.selectorTiempo = this.findViewById(R.id.editTextSelectTiempo);
        this.mensajeError = this.findViewById(R.id.mensajeErrorSim);
        this.mensajeExito = this.findViewById(R.id.mensajeExito);

        botonRegProgam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mensajeError.setText("");
                mensajeExito.setText("");
                String idAct =arrayIdActuador.get(selectorActuador.getSelectedItemPosition());
                if(!selectorTiempo.getText().toString().equals("")){
                    tiempo = selectorTiempo.getText().toString();
                    sendRegistroSim(idAct);
                }
                else {
                    mensajeError.setText("Introduce el tiempo que durará la simulacion");
                }
            }
        });

        loadHabitaciones();
        loadActuadores();
    }

    private void loadActuadores() {
        new TaskRegistrarSimulacion(this).
                execute("http://192.168.1.109:8080/LMDL_SERVER2/GetActuadoresSistema?cod_sistema="+cod_sistema);
    }

    private void loadHabitaciones() {
        new TaskSelectHabitacion(this).
                execute("http://192.168.1.109:8080/LMDL_SERVER2/GetHabitacionesSistema?cod_sistema="+cod_sistema);


    }

    private void sendRegistroSim(String idAct) {
        new TaskRegistrarSimulacion(this).
                execute("http://192.168.1.109:8080/LMDL_SERVER2/RegistrarSimulacion?cod_sistema="+cod_sistema+"&usuario="+usuariologin+"&id_actuador="+idAct+"&tiempo="+tiempo);
    }

    public void setListActuadores(JSONArray jsonarrayActuadores) {
        Log.e(tag,"Loading actuadores " + jsonarrayActuadores);
        ArrayList<String> valoresSelector = new ArrayList<>();
        try {
            lista_actuadores = new ArrayList<>();

            for (int i = 0; i < jsonarrayActuadores.length(); i++) {
                JSONObject jsonobject = jsonarrayActuadores.getJSONObject(i);
                Actuador actuador_recibido = new Actuador();
                actuador_recibido.setId_habitacion(jsonobject.getInt("id_habitacion"));
                actuador_recibido.setId_actuador(jsonobject.getInt("id_actuador"));
                actuador_recibido.setTipo(jsonobject.getString("tipo"));

                if(actuador_recibido.getTipo().contains("Zumbador")){
                    lista_actuadores.add(actuador_recibido);
                }

            }
            for (int i=0; i<lista_actuadores.size();i++){
                for (int j=0; j<listHabitacion.size();j++){
                    if(lista_actuadores.get(i).getId_habitacion() == listHabitacion.get(j).getId_habitacion()){
                        valoresSelector.add(lista_actuadores.get(i).getTipo()+" "+listHabitacion.get(j).getDescriptivo());
                        arrayIdActuador.add(""+lista_actuadores.get(i).getId_actuador()); //Para saber cual se selecciona despues
                    }
                }
            }
            selectorActuador.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, valoresSelector));

        }catch (Exception e)
        {
            Log.e(tag,"Error: " + e);
        }
    }

    public void setListHabitacion(JSONArray jsonHabitaciones) {
        arrayIdActuador=new ArrayList<>();
        Log.e(tag,"Loading habitaciones " + jsonHabitaciones);
        try {

            for (int i = 0; i < jsonHabitaciones.length(); i++) {
                JSONObject jsonobject = jsonHabitaciones.getJSONObject(i);
                Habitacion habitacion_recibida = new Habitacion();
                habitacion_recibida.setId_habitacion(jsonobject.getInt("id_habitacion"));
                habitacion_recibida.setDescriptivo(jsonobject.getString("descriptivo"));
                habitacion_recibida.setN_puertas(jsonobject.getInt("n_puertas"));
                habitacion_recibida.setN_ventanas(jsonobject.getInt("n_ventanas"));
                habitacion_recibida.setTamanno(jsonobject.getInt("tamanno"));
                habitacion_recibida.setCod_sistema_sistema_seguridad(jsonobject.getInt("cod_sistema_sistema_seguridad"));

                listHabitacion.add(habitacion_recibida);
            }


        }catch (Exception e)
        {
            Log.e(tag,"Error: " + e);
        }
    }

    public void simulacionRegistrada(String resultado){
        Log.i(tag, "Resultado: "+resultado);
        if (resultado.contains("-1")){
            mensajeError.setText("Error al registrar la simulación.");
        }
        else {
            selectorTiempo.setText("");
            mensajeExito.setText("Simulación registrada correctamente");
        }
    }
}
