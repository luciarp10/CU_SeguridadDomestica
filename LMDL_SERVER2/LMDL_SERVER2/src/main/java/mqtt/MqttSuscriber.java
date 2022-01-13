package mqtt;

import bbdd.Alerta;
import bbdd.ConexionBD;
import bbdd.Registro_camara;
import bbdd.Topic;
import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import logic.Log;
import logic.Logic;




public class MqttSuscriber implements MqttCallback {
    public void searchTopicsToSuscribe(MqttBroker broker) {
        Log.logmqtt.info("Buscando topics para suscribir");
        ConexionBD conector = new ConexionBD();
        Connection con = null;
        ArrayList<String> topics = new ArrayList<>();
        try {
            con = conector.obtainConnection(true);
            Log.logmqtt.info("Base de datos conectada");

            //Bucle para obtener todos los topics
            
            PreparedStatement psSistemas = ConexionBD.GetSistemas(con);
            Log.logmqtt.info("Query para buscar sistemas => {}", psSistemas.toString());
            ResultSet rsSistemas = psSistemas.executeQuery();
            while (rsSistemas.next()){
		topics.add("SistSeg" + rsSistemas.getInt("Cod_sistema")+"/#");
            }
            suscribeTopic(broker, topics);	

        } catch (NullPointerException e) {
            Log.logmqtt.error("Error: {}", e);
        } catch (Exception e) {
            Log.logmqtt.error("Error:{}", e);
        } finally {
            conector.closeConnection(con);
        }
    }

    public void suscribeTopic(MqttBroker broker, ArrayList<String> topics) {
        Log.logmqtt.debug("Suscribiéndose a los topics");
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            MqttClient sampleClient = new MqttClient(MqttBroker.getBroker(), MqttBroker.getClientId(), persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            Log.logmqtt.info("Mqtt conectando al broker: " + MqttBroker.getBroker());
            sampleClient.connect(connOpts);
            Log.logmqtt.info("Mqtt conectado");
            sampleClient.setCallback(this);
            for (int i = 0; i < topics.size(); i++) {
                sampleClient.subscribe(topics.get(i));
                Log.logmqtt.info("Suscrito a {}", topics.get(i));
            }

        } catch (MqttException me) {
            Log.logmqtt.error("Error suscribiéndose al topic: {}", me);
        } catch (Exception e) {
            Log.logmqtt.error("Error suscriéndose al topic: {}", e);
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
    }

    @Override
    public void messageArrived(String topicRecibido, MqttMessage message) throws Exception {
        Log.logmqtt.info("{}: {}", topicRecibido, message.toString());
        String[] topics = topicRecibido.split("/");
        Topic newTopic = new Topic();
        Random aleatorio = new Random(); 
        float num_aleatorio=5.1f;
        DecimalFormat formato = new DecimalFormat("#.##");
        
        //Comprobar el topic en función de los nombres que les demos y según sea el topic, tomar una decisión u otra.
        if(topicRecibido.contains("Sensor")) //SistSeg1/Hab1/Sensor1
        {
            newTopic.setId_sistema(Integer.parseInt(topics[0].replace("SistSeg", "")));
            newTopic.setId_habitacion(Integer.parseInt(topics[1].replace("Hab", "")));
            newTopic.setId_sensor_actuador(Integer.parseInt(topics[2].replace("Sensor", "")));
            newTopic.setValor(Float.parseFloat(message.toString()));
            Log.logmqtt.info("Mensaje de SistSeg{} Habitacion{} Sensor{}: {}", 
    			   newTopic.getId_sistema(), newTopic.getId_habitacion(), newTopic.getId_sensor_actuador(), message.toString());
            
            //Guardar la información en la base de datos
            Logic.guardarRegistroSensor(newTopic);
            
            
    
            //Simular mismo topic para el resto de las habitaciones (prototipo)
            Topic newTopicHab2 = new Topic();
            newTopicHab2.setId_sistema(Integer.parseInt(topics[0].replace("SistSeg", "")));
            newTopicHab2.setId_habitacion(2);
            newTopicHab2.setId_sensor_actuador(Integer.parseInt(topics[2].replace("Sensor", ""))+5); //Por el orden de registro en la bd
            num_aleatorio=aleatorio.nextFloat();//Numero aleatorio entre 0 y 1
            newTopicHab2.setValor(Float.parseFloat(message.toString())+Float.parseFloat(formato.format(num_aleatorio)));
            Logic.guardarRegistroSensor(newTopicHab2);
            
            Topic newTopicHab3 = new Topic();
            newTopicHab3.setId_sistema(Integer.parseInt(topics[0].replace("SistSeg", "")));
            newTopicHab3.setId_habitacion(2);
            newTopicHab3.setId_sensor_actuador(Integer.parseInt(topics[2].replace("Sensor", ""))+10); //Por el orden de registro en la bd
            num_aleatorio=aleatorio.nextFloat();//Numero aleatorio entre 0 y 1
            newTopicHab3.setValor(Float.parseFloat(message.toString())+Float.parseFloat(formato.format(num_aleatorio)));
            Logic.guardarRegistroSensor(newTopicHab3);
            
            
            
            //Para simular el sensor de calidad del aire suponemos buena calidad siempre (no tenemos sensor)
            for (int i = 4; i<15;i+=5){ //Sensor de humo tiene id 4, 9 y 14
                Topic newTopicCA = new Topic();
                newTopicCA.setId_sistema(Integer.parseInt(topics[0].replace("SistSeg", "")));
                newTopicCA.setId_habitacion(2);
                newTopicCA.setId_sensor_actuador(i); //Por el orden de registro en la bd
                num_aleatorio=45+aleatorio.nextInt(56-45);//Numero aleatorio entre 45 y 55 (valores normales)
                newTopicCA.setValor(num_aleatorio);
                Logic.guardarRegistroSensor(newTopicCA);
            }
        }else
        {
    	    if(topicRecibido.contains("Actuador")) //SistSeg1/Hab1/Actuador1
            {
    		newTopic.setId_sistema(Integer.parseInt(topics[0].replace("SistSeg", "")));
                newTopic.setId_habitacion(Integer.parseInt(topics[1].replace("Hab", "")));
                newTopic.setId_sensor_actuador(Integer.parseInt(topics[2].replace("Actuador", "")));
                newTopic.setValor(Float.parseFloat(message.toString()));
                Log.logmqtt.info("Mensaje de SistSeg{} Habitacion{} Actuador{}: {}", 
                        newTopic.getId_sistema(), newTopic.getId_habitacion(), newTopic.getId_sensor_actuador(), message.toString());
            
                //Guardar la información en la base de datos
                Logic.guardarRegistroActuador(newTopic);
                //Si el actuador es el irrigador (id_actuador=3), registramos mala calidad del aire (SIMULACION)
                if(newTopic.getId_sensor_actuador()==3){
                    for (int i = 4; i<15;i+=5){ //Sensor de humo tiene id 4, 9 y 14
                        Topic newTopicCA = new Topic();
                        newTopicCA.setId_sistema(Integer.parseInt(topics[0].replace("SistSeg", "")));
                        newTopicCA.setId_habitacion(2);
                        newTopicCA.setId_sensor_actuador(i); //Por el orden de registro en la bd
                        num_aleatorio=aleatorio.nextInt(200)+101;//Numero aleatorio entre 101 y 301 (valores de riesgo)
                        newTopicCA.setValor(num_aleatorio);
                        Logic.guardarRegistroSensor(newTopicCA);
                    }
                }
            } 
            else if (topicRecibido.contains("Camara")){ //SistSeg1/Hab1/Camara1
                newTopic.setId_sistema(Integer.parseInt(topics[0].replace("SistSeg", "")));
                newTopic.setId_habitacion(Integer.parseInt(topics[1].replace("Hab", "")));
                newTopic.setId_sensor_actuador(Integer.parseInt(topics[2].replace("Camara", "")));
                Log.logmqtt.info("Mensaje de SistSeg{} Habitacion{} Camara{}: {}", 
                        newTopic.getId_sistema(), newTopic.getId_habitacion(), newTopic.getId_sensor_actuador(), message.toString());
                Registro_camara nuevaFoto = new Registro_camara();
                nuevaFoto.setEnlace_foto(nuevaFoto.descargarFoto(message.toString()));
                nuevaFoto.setId_sensor_sensor(newTopic.getId_sensor_actuador());
                Logic.insertarRegistroCamara(nuevaFoto);
            }
            else if (topicRecibido.contains("Alarma")){ //SistSeg1/Alarma
                //Registrar que ha saltado la alarma o que se ha desconectado desde el pinpad 
                if(message.toString().equals("MovimientoDetectado")){
                    Alerta alerta_nueva = new Alerta();
                    alerta_nueva.setId_alerta(Logic.getUltimaAlerta(Integer.parseInt(topics[0].replace("SistSeg", "")))+1);
                    alerta_nueva.setInfo("Se ha detectado un movimiento y ha saltado la alarma");
                    alerta_nueva.setCod_sistema_sistema_seguridad(Integer.parseInt(topics[0].replace("SistSeg", "")));
                    Logic.insertarAlerta(alerta_nueva);
                } 
                else if(message.toString().equals("VentanaAbierta")){
                    Alerta alerta_nueva = new Alerta();
                    alerta_nueva.setId_alerta(Logic.getUltimaAlerta(Integer.parseInt(topics[0].replace("SistSeg", "")))+1);
                    alerta_nueva.setInfo("Se ha abierto la ventana y ha saltado la alarma");
                    alerta_nueva.setCod_sistema_sistema_seguridad(Integer.parseInt(topics[0].replace("SistSeg", "")));
                    Logic.insertarAlerta(alerta_nueva);
                }
                else if (message.toString().equals("VentanaCerrada")){
                    Alerta alerta_nueva = new Alerta();
                    alerta_nueva.setId_alerta(Logic.getUltimaAlerta(Integer.parseInt(topics[0].replace("SistSeg", "")))+1);
                    alerta_nueva.setInfo("Se ha cerrado la ventana");
                    alerta_nueva.setCod_sistema_sistema_seguridad(Integer.parseInt(topics[0].replace("SistSeg", "")));
                    Logic.insertarAlerta(alerta_nueva);
                }
            }
            else if (topicRecibido.contains("Sonido")){ //SistSeg1/Sonido
                
                if (message.toString().equals("Desactivado")){
                    Alerta alerta_nueva = new Alerta();
                    alerta_nueva.setId_alerta(Logic.getUltimaAlerta(Integer.parseInt(topics[0].replace("SistSeg", "")))+1);
                    alerta_nueva.setInfo("La alarma se ha desconectado desde el pinpad");
                    alerta_nueva.setCod_sistema_sistema_seguridad(Integer.parseInt(topics[0].replace("SistSeg", "")));
                    Logic.cambiarEstadoSistema(0, Integer.parseInt(topics[0].replace("SistSeg", "")));
                    Logic.insertarAlerta(alerta_nueva);
                }
                /*
                else if (message.toString().equals("Activado")){
                    //alarma sonando
                    Alerta alerta_nueva = new Alerta();
                    alerta_nueva.setId_alerta(Logic.getUltimaAlerta(Integer.parseInt(topics[0].replace("SistSeg", "")))+1);
                    alerta_nueva.setInfo("Ha saltado la alarma");
                    alerta_nueva.setCod_sistema_sistema_seguridad(Integer.parseInt(topics[0].replace("SistSeg", "")));
                    Logic.insertarAlerta(alerta_nueva);
                } */
            }
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
    }
}