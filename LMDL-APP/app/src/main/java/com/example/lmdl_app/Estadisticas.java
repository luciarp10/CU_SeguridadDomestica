package com.example.lmdl_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lmdl_app.data.Habitacion;
import com.example.lmdl_app.data.Registro_sensor;
import com.example.lmdl_app.tasks.TaskEstadisticas;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

public class Estadisticas extends AppCompatActivity {
    private String tag = "Estadisticas";
    private ArrayList<Habitacion> listHabitacion = new ArrayList<>();

    private Button botonInformes;
    private Button botonGraficas;
    private Spinner spinnerHabitaciones;
    private Spinner spinnerMedidas;
    private Spinner spinnerPeriodo;
    private EditText fechainicio;
    private final Context context;
    private String cod_sistema;
    private TextView mensajeError;
    LineChart lineChart;
    LineDataSet lineDataSet;

    private int idHab;
    private String medida_seleccionada;
    private String periodo_seleccionado;
    private String fecha_introducida;
    private ArrayList<Registro_sensor> registros_estadisticos = new ArrayList<>();

    public Estadisticas() {
        super();
        this.context=this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3_estadisticas);

        cod_sistema =getIntent().getStringExtra("cod_sistema");
        this.botonInformes = this.findViewById(R.id.botonVerInfo);
        this.spinnerHabitaciones=this.findViewById(R.id.spinnerListaCamaras);
        this.spinnerMedidas=this.findViewById(R.id.spinnerMedida);
        this.spinnerPeriodo=this.findViewById(R.id.spinnerPeriodo);
        this.fechainicio = this.findViewById(R.id.fecha_introducida);
        this.botonGraficas = this.findViewById(R.id.MostrarGrafica);
        this.mensajeError = this.findViewById(R.id.mensajeErrorFecha);
        mensajeError.setText("");
        String[] arrayMedidas = {"Temperatura", "Humedad", "Luminosidad","Calidad del aire"};
        spinnerMedidas.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, arrayMedidas));
        String[] arrayPeriodos={"Dia", "Semana", "Mes", "Año"};
        spinnerPeriodo.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, arrayPeriodos));

        //Poner gráfico vacío
        lineChart = this.findViewById(R.id.grafico);
        lineChart.setPinchZoom(true);
        lineChart.setNoDataText ("Pulsa Ver gráfica para cargar datos.");
        lineChart.getDescription().setEnabled(false);

        //Cargar habitaciones disponibles
        loadHabitaciones();
        botonInformes.setEnabled(false);
        botonInformes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Estadisticas.this, Informes.class);
                i.putExtra("media", ""+calcularMedia());
                i.putExtra("max", ""+buscarMax());
                i.putExtra("min", ""+buscarMin());
                i.putExtra("medida", medida_seleccionada);
                i.putExtra("periodo", periodo_seleccionado);
                i.putExtra("fecha", fecha_introducida);
                startActivity(i);

            }
        });

        botonGraficas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                botonInformes.setEnabled(false);
                idHab=listHabitacion.get(spinnerHabitaciones.getSelectedItemPosition()).getId_habitacion();
                medida_seleccionada=spinnerMedidas.getSelectedItem().toString();
                periodo_seleccionado=spinnerPeriodo.getSelectedItem().toString();
                fecha_introducida= fechainicio.getText().toString();

                if(!comprobarFormatoFecha(fecha_introducida)){
                    mensajeError.setText("El formato es incorrecto. Recuerda: yyyy-mm-dd");
                }
                else{
                    try {
                        loadRegistrosEstadisticos();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    private double calcularMedia() {
        int cont=0;
        double suma=0;
        for(int i=0; i<registros_estadisticos.size();i++){
            cont++;
            suma+=registros_estadisticos.get(i).getValor();
        }
        return suma/cont;
    }
    private double buscarMax() {
        double max=registros_estadisticos.get(0).getValor();
        for(int i=0; i<registros_estadisticos.size();i++){
            if(registros_estadisticos.get(i).getValor()>max){
                max=registros_estadisticos.get(i).getValor();
            }
        }
        return max;
    }
    private double buscarMin(){
        double min=registros_estadisticos.get(0).getValor();
        for(int i=0; i<registros_estadisticos.size();i++){
            if(registros_estadisticos.get(i).getValor()<min){
                min=registros_estadisticos.get(i).getValor();
            }
        }
        return min;
    }

    private void loadHabitaciones() {
        new TaskEstadisticas(this).
                execute("http://192.168.1.109:8080/LMDL_SERVER2/GetHabitacionesSistema?cod_sistema="+cod_sistema);

    }

    private void loadRegistrosEstadisticos() throws ParseException {
        String fecha_fin= calcularFecha(fecha_introducida, periodo_seleccionado);
        String[] fecha_fin_div = fecha_fin.split(" ");
        fecha_fin = fecha_fin_div[1]+" "+fecha_fin_div[2]+" "+fecha_fin_div[5];
        fecha_fin = transformarFecha(fecha_fin);

        Log.e(tag, fecha_fin);
        new TaskEstadisticas(this).
                execute("http://192.168.1.109:8080/LMDL_SERVER2/GetRegistrosSensoresHabitacionFecha?id_habitacion="+idHab+"&fecha_ini="+fecha_introducida+"&fecha_fin="+fecha_fin);
    }



    public void setListHabitacion(JSONArray jsonHabitaciones)
    {
        ArrayList<String> arrayHab=new ArrayList<>();
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
                arrayHab.add(jsonobject.getString("descriptivo"));

            }
            spinnerHabitaciones.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, arrayHab));
        }catch (Exception e)
        {
            Log.e(tag,"Error: " + e);
        }

    }

    public void representarResultados(JSONArray jsonSensores, JSONArray jsonRegistros){
        LineData lineData = new LineData();
        lineChart.setData(lineData);
        lineChart.getDescription().setEnabled(true);
        lineChart.getDescription().setText("No hay datos para el intervalo seleccionado");
        lineChart.invalidate();

        ArrayList<String> fechas_horas=new ArrayList<>();

        Log.e(tag,"Loading sensores " + jsonSensores);
        Log.e(tag,"Loading registros " + jsonRegistros);
        int id_sensor=-1;
        registros_estadisticos = new ArrayList<>();
        try {
            Log.i(tag, "medida seleccionada: "+medida_seleccionada);
            if(medida_seleccionada.equals("Calidad del aire")){
                for (int i = 0; i < jsonSensores.length(); i++) { //Guardar el id del sensor de la medida que se ha seleccionado
                    JSONObject jsonobject = jsonSensores.getJSONObject(i);

                    if(jsonobject.getString("tipo").contains("Humo")){
                        id_sensor = jsonobject.getInt("id_sensor");
                    }
                }
            }
            else{
                for (int i = 0; i < jsonSensores.length(); i++) { //Guardar el id del sensor de la medida que se ha seleccionado
                    JSONObject jsonobject = jsonSensores.getJSONObject(i);

                    if(jsonobject.getString("tipo").contains(medida_seleccionada)){
                        id_sensor = jsonobject.getInt("id_sensor");
                    }
                }
            }
            for (int i=0; i<jsonRegistros.length();i++){
                JSONObject jsonObject = jsonRegistros.getJSONObject(i);
                if(jsonObject.getInt("id_sensor_sensor")==id_sensor){
                    java.sql.Date date = Date.valueOf(transformarFecha(jsonObject.getString("fecha")));
                    java.sql.Timestamp hora = Timestamp.valueOf(transformarHora(jsonObject.getString("hora")));
                    Registro_sensor registro_recibido = new Registro_sensor();
                    registro_recibido.setFecha((Date) date);
                    registro_recibido.setHora(hora);
                    registro_recibido.setId_sensor_sensor(jsonObject.getInt("id_sensor_sensor"));
                    registro_recibido.setValor(jsonObject.getDouble("valor"));
                    registros_estadisticos.add(registro_recibido);
                    String[] hora_separada = hora.toString().split(" ");
                    fechas_horas.add(""+date+"\n"+hora_separada[1]);
                }
            }


        }catch (Exception e)
        {
            Log.e(tag,"Error: " + e);
        }

        // Enlazamos al XML
        lineChart = this.findViewById(R.id.grafico);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return fechas_horas.get((int) value);
            }
        };
        xAxis = lineChart.getXAxis();
        xAxis.setLabelRotationAngle(45);
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setTextSize(8f);
        xAxis.setValueFormatter(formatter);


        // Rellenamos datos
        Log.i(tag, "Tamaño: "+registros_estadisticos.size());
        ArrayList<Entry> lineEntries = new ArrayList<>();
        for (int i = 0; i<registros_estadisticos.size(); i++){
            lineEntries.add(new Entry((float) i, (float) registros_estadisticos.get(i).getValor()));
        }

        // Unimos los datos al data set
        lineDataSet = new LineDataSet(lineEntries, medida_seleccionada);

        // Asociamos al gráfico
        lineData = new LineData();

        if (lineEntries.isEmpty() || lineEntries.size()<2){
            lineChart.setData(lineData);
            lineChart.getDescription().setEnabled(true);
            lineChart.getDescription().setText("No hay datos para el intervalo seleccionado");
            lineChart.invalidate();
        }
        else {
            botonInformes.setEnabled(true);
            lineData.addDataSet(lineDataSet);
            lineChart.setData(lineData);
            lineChart.getDescription().setEnabled(false);
            lineChart.setDragEnabled(true); //Zoom
            lineChart.setExtraBottomOffset(20f);
            lineChart.setExtraRightOffset(30f);
            lineChart.setExtraLeftOffset (10f); // Espaciado
            lineChart.invalidate();

        }


    }

    private boolean comprobarFormatoFecha(String fecha){
        String[] fecha_separada = fecha.split("-");
        if (fecha_separada.length!=3){
            return false;
        }
        else{
            if (fecha_separada[0].length()!=4 || fecha_separada[1].length()!=2 || fecha_separada[1].length()!=2){
                return false;
            }
            else if((Integer.parseInt(fecha_separada[1]) > 12 || Integer.parseInt(fecha_separada[2])>31)){
                return false;
            }

        }
        return true;
    }

    private String calcularFecha(String fecha_introducida, String periodo_seleccionado) throws ParseException {
        int dias;
        if(periodo_seleccionado.equals("Dia")){
            dias=1;
        } else if (periodo_seleccionado.equals("Semana")){
            dias=7;
        } else if (periodo_seleccionado.equals("Mes")){
            dias=30;
        } else {
            dias=365;
        }
        java.sql.Date date = Date.valueOf(fecha_introducida);

        return sumarDiasAFecha(date, dias);
    }

    public String sumarDiasAFecha(Date fecha, int dias){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DAY_OF_YEAR, dias);
        Log.i(tag, ""+calendar.getTime());
        return ""+calendar.getTime();
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
        hora_modificada=hora_dividida[3];
        hora_modificada=fecha+" "+hora_modificada;
        return hora_modificada;
    }
}