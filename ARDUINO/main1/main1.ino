
#include "temp_hum_lum.h"
#include <WiFi.h>
#include <WiFiUdp.h>
#include <Wire.h>
#include <PubSubClient.h>
#include <string.h>
#include <SPI.h>


#define WIFI_SSID "Marixu"
#define WIFI_PASSWORD "12345678"
const char* mqtt_server = "172.20.10.2";
const char* namehost="Localhost";

WiFiClient espClient;
PubSubClient client(espClient);

temp_hum_lum_aire sensor_temp, sensor_hum, sensor_lum, sensor_aire;





void setup() {
  // put your setup code here, to run once:

  initSerial();
  initWifi();
  initMQTTServer();
  

  sensor_temp.inicializar();
  sensor_hum.inicializar();
  sensor_lum.inicializar();
  pinMode(13,OUTPUT);
  client.subscribe("SistSeg1/Hab1/Actuador2");


  //sensor de aire no es necesario

}

void loop() {
 
  
  float temperatura = sensor_temp.detectar_temperatura();delay(1000);

  char buffer[16];
  sprintf(buffer,"%.2f", temperatura);
  client.publish("SistSeg1/Hab1/Sensor1", buffer);
  
  float humedad= sensor_hum.detectar_humedad();delay(1000);
  char buffer1[16];
  sprintf(buffer1,"%.2f", humedad);
  client.publish("SistSeg1/Hab1/Sensor2", buffer1);

  
  float luz = sensor_lum.detectar_luminosidad();delay(1000);
  if (luz>1000){
    digitalWrite(13,HIGH);
    client.publish("SistSeg1/Hab1/Actuador2", "5");

    
  }
  else{
    digitalWrite(13,LOW);
    //client.publish("SistSeg1/Hab1/Actuador2", "5");

  }
  char buffer2[16];
  sprintf(buffer2,"%.2f", luz);
  client.publish("SistSeg1/Hab1/Sensor3", buffer2);

  /*float aire = sensor_aire.detectarValor();delay(1000);
  char buffer3[16];
  sprintf(buffer3,"%.2f", aire);
  client.publish("esp/viento", buffer3);
     */ 
  client.loop();
  delay(5000);
  
  

}

void initSerial(){
  Serial.begin(9600);
  Serial.setTimeout(5000);
  while(!Serial){
    
  }
  Serial.println("Booting...\n");
  Serial.println("\tDevice running");
}

void initWifi(){
  Serial.println();
  Serial.print("Wifi connecting to: ");
  Serial.println(WIFI_SSID);
  WiFi.hostname(namehost);
  WiFi.begin(WIFI_SSID,WIFI_PASSWORD);
  while( WiFi.status() != WL_CONNECTED ){
    delay(500);
    Serial.print(".");
  }
  Serial.println("Wifi connected!");
  Serial.print("NodeMCU IP Address : ");
  Serial.println(WiFi.localIP() );
}
void initMQTTServer(){
  client.setServer(mqtt_server, 1883);
  while (!client.connected()) {
    if (client.connect("ESP32Client1", "root", "ubicomp" )) {
      Serial.println("Connected to MQTT");
    } else {
      Serial.print("failed with state ");
      Serial.print(client.state());
      delay(2000);
    }
    
  }
}
