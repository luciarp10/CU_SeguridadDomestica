#include <DHT.h>           //librería DHT
#include <Wire.h>
#include <Adafruit_Sensor.h>
#include <Adafruit_TSL2561_U.h>


#define DHTTYPE  DHT22   //Se define el modelo del sensor DHT22
#define DHTPIN    4     // Se define el pin D4 del ESP32 para conectar el sensor DHT22
Adafruit_TSL2561_Unified tsl = Adafruit_TSL2561_Unified(TSL2561_ADDR_FLOAT, 12345);
DHT dht(DHTPIN, DHTTYPE, 22); 

class temp_hum_lum_aire{
  
  
  public: void configureSensor(void)
  {
  tsl.enableAutoRange(true);            
  tsl.setIntegrationTime(TSL2561_INTEGRATIONTIME_13MS);      

  }
  public: void inicializar(){
    dht.begin(); 
    Serial.println("Light Sensor Test"); Serial.println("");
    tsl.begin();  
    configureSensor();  
    Serial.println(""); 
  }
  public: float detectar_temperatura(){
    float temperatura = dht.readTemperature(); //Se lee la temperatura
    Serial.println("Temperatura: ");
    Serial.println(temperatura);
    return temperatura;
    delay(2000); 
  } 
  public: float detectar_humedad(){
    float humedad = dht.readHumidity(); //Se lee la humedad 
  
   //Se imprimen las variables
    Serial.println("Humedad: "); 
    Serial.println(humedad);
    return humedad;
    delay(2000);  
    /* Get a new sensor event */ 
    
  }
  public: float detectar_luminosidad(){
    sensors_event_t event;
    tsl.getEvent(&event);
    if (event.light)
    {
      Serial.print("Luminosidad:");
      Serial.print(event.light); Serial.println(" lux");
      return event.light;
    }
    else
    {
      /* If event.light = 0 lux the sensor is probably saturated
         and no reliable data could be generated! */
      Serial.println("Sensor overload");
    }
    delay(2000);
  }
 
  
};
