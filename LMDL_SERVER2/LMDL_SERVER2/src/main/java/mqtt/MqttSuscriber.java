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




public class MqttSuscriber implements MqttCallback {
    public void searchTopicsToSuscribe(MqttBroker broker) {
        ConexionBD conector = new ConexionBD();
        Connection con = null;
        ArrayList<String> topics = new ArrayList<>();
        try {
            con = conector.obtainConnection(true);
            Log.logmqtt.debug("Base de datos conectada");

            //Bucle para obtener todos los topics
            PreparedStatement psSistemas = ConexionBD.GetSistemas(con);
            Log.logmqtt.debug("Query apra buscar sistemas => {}", psSistemas.toString());
            ResultSet rsSistemas = psSistemas.executeQuery();
            while (rsSistemas.next()){
		topics.add("Sistema" + rsSistemas.getInt("ID")+"/#");
            }
            topics.add("test");
            suscribeTopic(broker, topics);			

        } catch (NullPointerException e) {
            //Log.logmqtt.error("Error: {}", e);
        } catch (Exception e) {
            //Log.logmqtt.error("Error:{}", e);
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
            Log.logmqtt.debug("Mqtt conectando al broker: " + MqttBroker.getBroker());
            sampleClient.connect(connOpts);
            Log.logmqtt.debug("Mqtt conectado");
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
        
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
    }
}