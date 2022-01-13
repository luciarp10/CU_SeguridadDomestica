#include "OV7670.h"

#include <Adafruit_GFX.h>    // Core graphics library
//#include <Adafruit_ST7735.h> // Hardware-specific library

#include <WiFiMulti.h>

#include <WiFi.h>
#include <WiFiUdp.h>
#include <Wire.h>
#include <PubSubClient.h>
#include <string.h>

#include <WiFiClient.h>
#include "BMP.h"

const int SIOD = 19; //SDA
const int SIOC = 22; //SCL

const int VSYNC = 34;
const int HREF = 35;

const int XCLK = 32;
const int PCLK = 33;

const int D0 = 27;
const int D1 = 17;
const int D2 = 16;
const int D3 = 15;
const int D4 = 21;
const int D5 = 13;
const int D6 = 12;
const int D7 = 4;

const int TFT_DC = 2;
const int TFT_CS = 5;

boolean foto = true;
//DIN <- MOSI 23
//CLK <- SCK 18

#define ssid1        "TRISKEL64"
#define password1    "VOLVORETAjali2016"
//#define ssid2        ""
//#define password2    ""

#define WIFI_SSID "TRISKEL64"
#define WIFI_PASSWORD "VOLVORETAjali2016"
const char* mqtt_server = "192.168.1.109";
const char* namehost="Localhost";

WiFiClient espClient;
PubSubClient client(espClient);



//Adafruit_ST7735 tft = Adafruit_ST7735(TFT_CS,  TFT_DC, 0/*no reset*/);
OV7670 *camera;

WiFiMulti wifiMulti;
WiFiServer server(80);

unsigned char bmpHeader[BMP::headerSize];

void serve()
{
  //client.loop();
  
  WiFiClient client = server.available();
  if (client) 
  {
    Serial.println("New Client.");
    String currentLine = "";
    while (client.connected()) 
    {
      if (client.available()) 
      {
        char c = client.read();
        //Serial.write(c);
        if (c == '\n') 
        {
          if (currentLine.length() == 0) 
          {
            client.println("HTTP/1.1 200 OK");
            client.println("Content-type:text/html");
            client.println();
            client.print(
              "<style>body{margin: 0}\nimg{height: 100%; width: auto}</style>"
              "<img id='a' src='/camera' onload='this.style.display=\"initial\"; var b = document.getElementById(\"b\"); b.style.display=\"none\"; b.src=\"camera?\"+Date.now(); '>"
              "<img id='b' style='display: none' src='/camera' onload='this.style.display=\"initial\"; var a = document.getElementById(\"a\"); a.style.display=\"none\"; a.src=\"camera?\"+Date.now(); '>");
            client.println();
            break;
          } 
          else 
          {
            currentLine = "";
          }
        } 
        else if (c != '\r') 
        {
          currentLine += c;
        }
        
        if(currentLine.endsWith("GET /camera"))
        {
            client.println("HTTP/1.1 200 OK");
            client.println("Content-type:image/bmp");
            client.println();
            
            client.write(bmpHeader, BMP::headerSize);
            client.write(camera->frame, camera->xres * camera->yres * 2); //enviar este contenido por un mensaje de MQTT
        }
      }
    }
    // close the connection:
    client.stop();
    Serial.println("Client Disconnected.");
  }  
}

void setup() 
{
  delay(1000);
  initSerial();
  initWifi();
  initMQTTServer();

  wifiMulti.addAP(ssid1, password1);
  //wifiMulti.addAP(ssid2, password2);
  Serial.println("Connecting Wifi...");
  if(wifiMulti.run() == WL_CONNECTED) {
      Serial.println("");
      Serial.println("WiFi connected");
      Serial.println("IP address: ");
      Serial.println(WiFi.localIP());
  }
  client.subscribe("ServidorSistema1/Camara5"); //SistSeg1/Camara5 
  client.publish("ServidorSistema1/Camara5","Hacer foto"); //SistSeg1/Camara5 Hacer foto


  
  camera = new OV7670(OV7670::Mode::QQVGA_RGB565, SIOD, SIOC, VSYNC, HREF, XCLK, PCLK, D0, D1, D2, D3, D4, D5, D6, D7);
  BMP::construct16BitHeader(bmpHeader, camera->xres, camera->yres);
  
  //tft.initR(INITR_BLACKTAB);
  //tft.fillScreen(0);
  server.begin();
}

void displayY8(unsigned char * frame, int xres, int yres)
{
  //tft.setAddrWindow(0, 0, yres - 1, xres - 1);
  int i = 0;
  for(int x = 0; x < xres; x++)
    for(int y = 0; y < yres; y++)
    {
      i = y * xres + x;
      unsigned char c = frame[i];
      unsigned short r = c >> 3;
      unsigned short g = c >> 2;
      unsigned short b = c >> 3;
      //tft.pushColor(r << 11 | g << 5 | b);
    }  
}

void displayRGB565(unsigned char * frame, int xres, int yres)
{
  //tft.setAddrWindow(0, 0, yres - 1, xres - 1);
  int i = 0;
  for(int x = 0; x < xres; x++)
    for(int y = 0; y < yres; y++)
    {
      i = (y * xres + x) << 1;
      //tft.pushColor((frame[i] | (frame[i+1] << 8)));
    }  
}

void loop()
{
  
  client.loop();
  serve();
  //client.loop();
  
  
    
    
}

void hacer_foto(){
   
   //if (foto){ //me falta una variable para que solo se haga una vez
      camera->oneFrame();    
      displayRGB565(camera->frame, camera->xres, camera->yres);  
      client.publish("SistSeg1/Hab1/Camara5", "http://192.168.1.103/camera?1642059409139"); //SistSeg1/Hab1/Camara1
       
   //}
    
   //foto = false;
   
  
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
    if (client.connect("ESP32Client4", "root", "ubicomp" )) {
      Serial.println("Connected to MQTT");
    } else {
      Serial.print("failed with state ");
      Serial.print(client.state());
      delay(2000);
    }
    
  }
}
String fotoIntencionada= "Hacer foto";
boolean intencionada = true;
String fotopresion = "fotopresion";
boolean presion = true;
String fotomovimiento = "fotomovimiento";
boolean movimiento = true;
String fotoventana = "fotoventana";
boolean ventana = true;
void callback(char *topic, byte *payload, unsigned int length)
{
    Serial.print("Received on ");
    Serial.print(topic);
    Serial.print(": ");
    String content = "";
    for (size_t i = 0; i < length; i++)
    {
        content.concat((char)payload[i]);
    }
    Serial.print(content);

    for (size_t i = 0; i < 16; i++)
    {
        if(content[i]==fotoIntencionada[i]){
          intencionada=true;          
        }
        else{
          intencionada=false;
        }
    }
    for (size_t i = 0; i < 11; i++)
    {
        if(content[i]==fotopresion[i]){
          presion=true;          
        }
        else{
          presion=false;
        }
    }

    for (size_t i = 0; i < 14; i++)
    {
        if(content[i]==fotomovimiento[i]){
          movimiento=true;          
        }
        else{
          movimiento=false;
        }
    }
        
        
    for (size_t i = 0; i < 11; i++)
    {
        if(content[i]==fotoventana[i]){
          ventana=true;          
        }
        else{
          ventana=false;
        }
    }        
        
    
    if(intencionada|presion|movimiento|ventana){
      foto=true;
      hacer_foto();
    }
    

}
