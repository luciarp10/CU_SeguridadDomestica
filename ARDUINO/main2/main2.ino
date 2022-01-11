#include "teclado.h"
#include "magn.h"
#include "buzzer.h"
#include "movimiento.h"
#include "presion.h"
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


teclado sensor_teclado;
zumbador sensor_buzzer;
magn sensor_magnetico;
movimiento sensor_movimiento;
//presion sensor_presion;
boolean alarma_desactivada=true;

//variables callback
String topicManual = "ServidorSistema1/Actuador1"; //SistSeg1/Actuador1 
boolean manual =true;
String topicSistema = "ServidorSistema1/Sistema";
boolean sistema =true;
String sistemaActivado ="Conectar"; //SistSeg1/Sistema Activar
String sistemaDesactivado = "Desconectar"; //(((SistSeg1/Sistema Desactivar)))
boolean activado = true;


void setup() {
  initSerial();
  initWifi();
  initMQTTServer();
  client.subscribe("ServidorSistema1/Actuador1"); //para activarla manualmente
  client.subscribe("ServidorSistema1/Sistema"); //para activar y desactivar el sistema general 



  
  sensor_teclado.inicializar();
  sensor_movimiento.inicializar();
  
  
}

void loop() {
  if (activado){
    int valor = sensor_magnetico.detectar_valor();
    int valor1 = sensor_movimiento.detectar_valor();
    
    
    if(valor==1){
      client.publish("SistSeg1/Alarma", "VentanaAbierta"); 
      alarma_desactivada = false;
      client.publish("SistSeg1/Sonido", "Activado");
      client.publish("ServidorSistema1/Camara5", "fotoventana");
      activarAlarma(alarma_desactivada);
      client.publish("SistSeg1/Alarma", "VentanaCerrada");
    }
    if(valor==0){
      
      alarma_desactivada = true;
      //client.publish("esp/cam", " ");
      activarAlarma(alarma_desactivada);
      
    }
     if(valor1==1){
     client.publish("SistSeg1/Alarma", "MovimientoDetectado");
     alarma_desactivada = false;
     client.publish("SistSeg1/Sonido", "activado");
     client.publish("ServidorSistema1/Camara5", "fotomovimiento");
     activarAlarma(alarma_desactivada);
    }
    if(valor1==0){
      alarma_desactivada = true;
      //client.publish("esp/cam", " ");
      activarAlarma(alarma_desactivada);
    }
  }
  
  /*
  if(valor2>0){
   client.publish("esp/presion", "PresionDetectada");
   alarma_desactivada = false;
   client.publish("esp/alarma", "activada_presion");
   client.publish("esp/cam", "fotopresion");   
   activarAlarma(alarma_desactivada); 
  }
  if(valor2==0){
    client.publish("esp/presion", "...");
    alarma_desactivada = true;
    client.publish("esp/alarma", "desactivada");
    client.publish("esp/cam", " ");  
    activarAlarma(alarma_desactivada);  
  }*/
  
  client.loop();
  
  delay(1000);
 
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
  client.setCallback(callback);
  while (!client.connected()) {
    if (client.connect("ESP32Client3", "root", "ubicomp" )) {
      Serial.println("Connected to MQTT");
    } else {
      Serial.print("failed with state ");
      Serial.print(client.state());
      delay(2000);
    }
    
  }
}
//String alarmaIntencionada= "activada_Intencionada";
//boolean intencionada = true;

void callback(char *topic, byte *payload, unsigned int length)
{
    Serial.print("Received on ");
    Serial.print(topic);
    Serial.print(": ");
    String content = "";

    for (size_t i = 0; i < 26; i++)
    {
        if(topic[i]==topicManual[i]){
          manual=true;          
        }
        else{
          manual=false;
        }
    }
    if(manual){
        for (size_t i = 0; i < length; i++)
      {
          content.concat((char)payload[i]);
      }
      Serial.print(content);
      Serial.println();
      int tiempo = content.toInt();
      activarAlarmaTemp(false,tiempo);
   }
   for (size_t i = 0; i < 25; i++)
    {
        if(topic[i]==topicSistema[i]){
          sistema=true;          
        }
        else{
          sistema=false;
        }
    }
    if(sistema){

        for (size_t i = 0; i < length; i++)
      {
          content.concat((char)payload[i]);
      }
      Serial.print(content);
      Serial.println();
        for (size_t i = 0; i < 8; i++)
      {
          if(content[i]==sistemaActivado[i]){
            activado =true;
            sensor_teclado.encenderSistema();
          }

      }
      for (size_t i = 0; i < 11; i++)
      {
          if(content[i]==sistemaDesactivado[i]){
            activado=false;
            sensor_teclado.apagarSistema();

          }
      }
   }


    



    
    /*
    for (size_t i = 0; i < 18; i++)
    {
        if(content[i]==alarmaIntencionada[i]){
          intencionada=true;          
        }
        else{
          intencionada=false;
        }
    }
    if(intencionada){
      client.publish("esp/cam", "fotoIntencionada");
      activarAlarma(false);

    }*/
    
}

void activarAlarma(boolean alarma_desactivada){
    while (not alarma_desactivada){
      sensor_buzzer.sonar_alarma();
      if (sensor_teclado.introducir_contrasena()){
        alarma_desactivada = true;
        sensor_buzzer.parar_alarma();
        client.publish("SistSeg1/Sonido", "Desactivado");
        activado=false;
        sensor_teclado.apagarSistema();
      }
    }
}
  

void activarAlarmaTemp(boolean alarma_desactivada, int tiempo){
    
     sensor_buzzer.sonar_alarma(); //cambiar por tone O... crear yo un tono con noteDurations muy larga
     String valor ="";
     valor.concat(tiempo);
     char buf[50];
     valor.toCharArray(buf,50);
     client.publish("SistSeg1/Hab1/Actuador1", buf); //SistSeg1/Hab1/Actuador1
     delay(tiempo*1000);
     sensor_buzzer.parar_alarma(); 
     alarma_desactivada=true;
     //client.publish("esp/alarma", " ");    //SistSeg1/Hab1/Actuador1 no pasar desactivada, pasar TIEMPO en char
    
     
  
}
