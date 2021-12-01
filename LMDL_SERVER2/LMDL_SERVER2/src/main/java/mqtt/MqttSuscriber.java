package mqtt;

import bbdd.ConexionBD;
import bbdd.Topic;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
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
        newTopic.setValor(Float.parseFloat(message.toString()));
     
        //Comprobar el topic en función de los nombres que les demos y según sea el topic, tomar una decisión u otra.
        if(topicRecibido.contains("Sensor"))
        {
            newTopic.setId_sistema(Integer.parseInt(topics[0].replace("SistSeg", "")));
            newTopic.setId_habitacion(Integer.parseInt(topics[1].replace("Hab", "")));
            newTopic.setId_sensor_actuador(Integer.parseInt(topics[2].replace("Sensor", "")));
            Log.logmqtt.info("Mensaje de SistSeg{} Habitacion{} Sensor{}: {}", 
    			   newTopic.getId_sistema(), newTopic.getId_habitacion(), newTopic.getId_sensor_actuador(), message.toString());
            
            //Guardar la información en la base de datos
            Logic.guardarRegistroSensor(newTopic);
        }else
        {
    	    if(topicRecibido.contains("Actuador"))
            {
    		newTopic.setId_sistema(Integer.parseInt(topics[0].replace("SistSeg", "")));
                newTopic.setId_habitacion(Integer.parseInt(topics[1].replace("Hab", "")));
                newTopic.setId_sensor_actuador(Integer.parseInt(topics[2].replace("Actuador", "")));
                Log.logmqtt.info("Mensaje de SistSeg{} Habitacion{} Actuador{}: {}", 
                        newTopic.getId_sistema(), newTopic.getId_habitacion(), newTopic.getId_sensor_actuador(), message.toString());
            
                //Guardar la información en la base de datos
                Logic.guardarRegistroActuador(newTopic);
            } //En principio no va a haber más tipos de topics pero se pueden añadir
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
    }
}