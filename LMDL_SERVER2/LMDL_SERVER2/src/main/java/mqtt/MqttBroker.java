package mqtt;

public class MqttBroker {
    private static int qos = 2;
    private static String broker = "tcp://192.168.1.109:1883";
    private static String clientId = "id_cliente";

    public MqttBroker() {
    }

    public static int getQos() {
        return qos;
    }

    public static String getBroker() {
        return broker;
    }

    public static String getClientId() {
        return clientId;
    }
}
