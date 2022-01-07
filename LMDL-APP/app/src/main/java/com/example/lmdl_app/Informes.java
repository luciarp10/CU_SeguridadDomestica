package com.example.lmdl_app;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class Informes extends AppCompatActivity {

    private String media;
    private String maximo;
    private String minimo;
    private String medida;
    private String periodo;
    private String fecha;

    private EditText medida_text;
    private EditText periodo_text;
    private EditText fecha_text;
    private TextView media_text;
    private TextView max_text;
    private TextView min_text;
    private TextView evaluacion_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3_1_informes);

        this.media = getIntent().getStringExtra("media");
        this.maximo = getIntent().getStringExtra("max");
        this.minimo = getIntent().getStringExtra("min");
        this.medida = getIntent().getStringExtra("medida");
        this.periodo = getIntent().getStringExtra("periodo");
        this.fecha = getIntent().getStringExtra("fecha");

        this.medida_text = this.findViewById(R.id.espacioMedida);
        this.periodo_text = this.findViewById(R.id.espacioPeriodo);
        this.fecha_text = this.findViewById(R.id.espacioFecha);
        this.media_text = this.findViewById(R.id.espacioMedia);
        this.max_text = this.findViewById(R.id.espacioMax);
        this.min_text = this.findViewById(R.id.espacioMin);
        this.evaluacion_text = this.findViewById(R.id.espacioEvalGlo);

        medida_text.setFocusable(false);
        periodo_text.setFocusable(false);
        fecha_text.setFocusable(false);

        medida_text.setText(medida);
        periodo_text.setText(periodo);
        fecha_text.setText(fecha);

        media_text.setText(media);
        max_text.setText(maximo);
        min_text.setText(minimo);

        //Evaluacion global
        calcularEvaluacionglobal();

    }

    private void calcularEvaluacionglobal() {
        if(medida.equals("Temperatura")){
            if(Double.parseDouble(media)>=20 && Double.parseDouble(media)<=21){
                evaluacion_text.setText("Muy buena");
            }
            else if (Double.parseDouble(media)>=18 && Double.parseDouble(media)<=24){
                evaluacion_text.setText("Normal");
            }
            else {
                evaluacion_text.setText("Mala");
            }
        }
        else if (medida.equals("Humedad")){
            if(Double.parseDouble(media)>=30 && Double.parseDouble(media)<=50){
                evaluacion_text.setText("Muy buena");
            }
            else if (Double.parseDouble(media)>=25 && Double.parseDouble(media)<=55){
                evaluacion_text.setText("Normal");
            }
            else {
                evaluacion_text.setText("Mala");
            }
        }

        else if (medida.equals("Calidad del aire")){
            if(Double.parseDouble(media)>=0 && Double.parseDouble(media)<=50){
                evaluacion_text.setText("Muy buena");
            }
            else if (Double.parseDouble(media)>=51 && Double.parseDouble(media)<=100){
                evaluacion_text.setText("Normal");
            }
            else if (Double.parseDouble(media)>=101 && Double.parseDouble(media)<=150){
                evaluacion_text.setText("Mala");
            }
            else if (Double.parseDouble(media)>=151 && Double.parseDouble(media)<=300){
                evaluacion_text.setText("Dañina para la salud");
            }
            else {
                evaluacion_text.setText("Peligrosa");
            }
        }

        else if(medida.equals("Luminosidad")){
            if(Double.parseDouble(media)>=300 && Double.parseDouble(media)<=500){
                evaluacion_text.setText("Muy buena");
            }
            else if (Double.parseDouble(media)>=200 && Double.parseDouble(media)<=800){
                evaluacion_text.setText("Normal");
            }
            else {
                evaluacion_text.setText("Mala");
            }
        }
    }
}