#include "MQ135.h"

#define PIN_MQ135 27
MQ135 mq135_sensor = MQ135(PIN_MQ135);

float t = 21.0; // assume current temperature. Recommended to measure with DHT22
float h = 25.0; // assume current humidity. Recommended to measure with DHT22

#include <WiFi.h>
#include <WiFiUdp.h>
#include <Wire.h>
#include <PubSubClient.h>
#include <string.h>
#include <SPI.h>


#define WIFI_SSID "TRISKEL64"
#define WIFI_PASSWORD "VOLVORETAjali2016"
const char* mqtt_server = "192.168.1.109";
const char* namehost="Localhost";

WiFiClient espClient;
PubSubClient client(espClient);






void setup() {
  // put your setup code here, to run once:

  initSerial();
  initWifi();
  initMQTTServer();

  pinMode(19,OUTPUT);

  client.subscribe("SistSeg1/Hab1/Sensor1");
  client.subscribe("SistSeg1/Hab1/Sensor2");
  client.subscribe("SistSeg1/Hab1/Sensor3");



  //sensor de aire no es necesario

}

void loop() {
  
  float correctedPPM = PARA * pow((getResistance()/RZERO), -PARB);
  Serial.print("\t Corrected PPM: ");
  Serial.print(correctedPPM);
  Serial.println("ppm");
  if(correctedPPM>8000){
    digitalWrite(19,LOW);
    //client.publish("SistSeg1/Hab1/Actuador3","desactivado");

  }
  else{
        digitalWrite(19,HIGH);
        String mensaje = "";
        int n = random(3,10);
        
        mensaje.concat(n);
        
        char buf[50];
        mensaje.toCharArray(buf,50);
        Serial.println(buf);
        client.publish("SistSeg1/Hab1/Actuador3", buf);
        delay(n*1000); 
  }
  client.loop();
  delay(500);
  
  

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
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
  }
  Serial.println("Connected to the WiFi");
}

void initMQTTServer(){
  client.setServer(mqtt_server, 1883);
  while (!client.connected()) {
    if (client.connect("ESP32Client2", "root", "ubicomp" )) {
      Serial.println("Connected to MQTT");
    } else {
      Serial.print("failed with state ");
      Serial.print(client.state());
      delay(2000);
    }
    
  }
}














float getCorrectedResistance(float t, float h){
    return getResistance()/getCorrectionFactor(t, h);

}
float getCorrectionFactor(float t, float h){
    return CORA * t * t - CORB * t + CORC - (h-33.)*CORD;
}
float getResistance(){
  int val = analogRead(35);
  return ((1023./(float)val) * 5. - 1.)*RLOAD;
}
