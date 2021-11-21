package mqtt;
public class MQTTBroker
{
    private static int qos = 2;
    private static String broker = "tcp://127.0.0.1:1883";
    private static String clientID = "UbicompUah";
}
public MQTTBroker()
{
}

public static int getQos(){
    return qos;
}

public static String getBroker(){
    return broker;
}

public String getClientID(){
    return clientId;
}