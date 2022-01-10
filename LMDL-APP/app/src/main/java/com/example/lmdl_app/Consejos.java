package com.example.lmdl_app;

import android.os.Bundle;
import java.util.ArrayList;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;


public class Consejos extends AppCompatActivity{

    private String tag = "Consejo";
    private String consejo = "consejo";
    private Button buttonSiguiente;
    private Button buttonVolver;
    private EditText consejos_text;
    private ArrayList <String> info = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3_2_consejos);


        this.buttonSiguiente = this.findViewById(R.id.buttonSiguiente);
        this.buttonVolver = this.findViewById(R.id.buttonVolver);
        this.consejos_text = this.findViewById(R.id.espacioConsejos);
        this.consejo = getIntent().getStringExtra("consejo");
        this.info = info;

        int i = 0;
        info = lista();

        buttonSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(tag, "Consejo: "+ consejo);
                mostrarConsejo(i);
            }
        });

        buttonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(tag, "Volver: "+ buttonVolver);
                finish();
            }
        });

    }
    private ArrayList <String> lista () {
        ArrayList <String> lista = new ArrayList<String>();
        String consejo1 = "· La temperatura más confortable para el ser humano en estado de reposo es de entre 18º y 20ºC. \n" +
                "· Si está trabajando la cifra desciende al intervalo comprendido entre 15º y 18ºC, según el tipo de movimiento y la intensidad con la que se realiza.\n";
        String consejo2 = "Un ambiente agradable debe tener una humedad relativa de 50-60% y se considera aceptable entre un 40% y un 70%. Un ambiente demasiado húmedo favorece el desarrollo de gérmenes nocivos y hongos.\n" +
                "Es importante mantener los niveles de humedad dentro de un rango específico para que los extremos no te afecten. No solo se trata de mantener las alergias bajo control, sino también la seguridad.\n" +
                "Se recomienda más humedad en el interior cuanto más calor haga fuera y menos humedad en el interior más frío haga en el exterior.";
        String consejo3 = "Uno de los mecanismos clave de enfriamiento del cuerpo es la sudoración:\n"+
                "La concentración de agua en el aire en relación con la temperatura determina la velocidad a la que el agua puede evaporarse de la piel.\n"+
                "Cuando el aire contiene más humedad, es más difícil que este absorba el sudor de la piel.";
        String consejo4 = "Las plantas pueden ser una forma de equilibrar la humedad del ambiente en entornos húmedos, además nos ayudan a mejorar la calidad del aire de nuestra vivienda, ya que absorben el CO2 que nosotros expulsamos y liberan oxígeno.";
        String consejo5 = "¡Ventila con frecuencia!\n" + "Al respirar soltamos componentes como el anhídrido carbónico y, puesto que es tóxico, es necesaria una correcta ventilación.";
        String consejo6 = "La medición de los niveles de contaminación en el ambiente determina si el aire que respiramos es o no de calidad.\n"+
                "Las personas más vulnerables a una mala calidad del aire son los niños, los ancianos, los asmáticos, las personas con insuficiencia respiratoria y las embarazadas.\n"+
                "Por la mala calidad del aire se pueden desarrollar enfermedades tales como:\n"+
                "–\tAlergias\n" +
                "–\tAsma\n" +
                "–\tDolores de cabeza\n" +
                "–\tBronquitis\n" +
                "–\tEnfisema pulmonar\n" +
                "–\tCáncer\n";
        String consejo7 = "El rango del ICA divide la calidad del aire en seis tramos:\n" +
                "•\tBuena: Color verde (ICA de 0 a 50)\n" +
                "•\tModerada: Color amarillo (ICA de 51 a 100)\n" +
                "•\tDañina a la salud para grupos sensibles: Color naranja (ICA de 101 a 150)\n" +
                "•\tDañina a la salud: Color rojo (ICA 151 a 200)\n" +
                "•\tMuy dañina a la salud: Color morado (ICA 201 a 300)\n"+
                "•\tPeligrosa: Color marrón (ICA superior a 300)\n";
        String consejo8 = "Aunque algunos de los contaminantes del aire interior proceden del exterior, lo cierto es que la mayor parte se liberan dentro del propio edificio";
        String consejo9 = "6 sebcillos pasos con los que puedes mejorar la calidad del aire de tu hogar:\n" +
                "1.\tAbre las ventanas cada día para ventilar\n" +
                "2.\tInstala sistemas de ventilación\n" +
                "3.\tUtiliza aparatos de renovación del aire para mejorar la calidad\n" +
                "4.\tPurificadores para una buena calidad del aire interior\n" +
                "5.\tNo fumar en casa\n" +
                "6.\tTener plantas\n";
        String consejo10 = "Cada espacio de nuestro hogar tiene una iluminación concreta adecuada:\n"+
                "•\tCocina:\n" +
                "\uF0FC\tIluminación general 300 lux.\n" +
                "\uF0FC\t En la zona de cortar y de preparado: 500-600.\n" +
                "•\tBaño: \n" +
                "\uF0FC\tIluminación general 200 lux.\n" +
                "\uF0FC\tPara maquillarse o afeitarse: 300-500.\n" +
                "•\tDormitorio: \n" +
                "\uF0FC\tIluminación general: 100-200 lux\n" +
                "\uF0FC\tPara leer: 500.\n" +
                "•\tCuarto de los niños:\n" +
                "\uF0FC\tIluminación general 200-300 lux.\n" +
                "\uF0FC\tDonde hagan trabajos manuales:500-750 \n" +
                "•\tSala de estar: \n" +
                "\uF0FC\tIluminación general: 100 lux.\n" +
                "\uF0FC\tPara ver la tele: 50-70.\n" +
                "\uF0FC\tPara leer: 500 lx.\n";
        lista.add(consejo1);
        lista.add(consejo2);
        lista.add(consejo3);
        lista.add(consejo4);
        lista.add(consejo5);
        lista.add(consejo6);
        lista.add(consejo7);
        lista.add(consejo8);
        lista.add(consejo9);
        lista.add(consejo10);

        return lista;
    }

    private void mostrarConsejo(int i){
        if (i == info.size()){
            i = 0;
        } else {
            i+= i;
        }
        consejos_text.setText(info.get(i));
    }
}
