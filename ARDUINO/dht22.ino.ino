#include <DHT.h>           //librería DHT
#define DHTTYPE  DHT22   //Se define el modelo del sensor DHT22
#define DHTPIN    4     // Se define el pin D4 del ESP32 para conectar el sensor DHT22
DHT dht(DHTPIN, DHTTYPE, 22); 
void setup() {
  Serial.begin(115200);   //Se inicia la comunicación serie
  dht.begin(); 
}

void loop() {
  float humedad = dht.readHumidity(); //Se lee la humedad 
  float temperatura = dht.readTemperature(); //Se lee la temperatura

 //Se imprimen las variables
  Serial.println("Humedad: "); 
  Serial.println(humedad);
  Serial.println("Temperatura: ");
  Serial.println(temperatura);
  delay(2000);  
}
