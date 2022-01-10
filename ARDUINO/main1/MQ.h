#include "MQ135.h"

MQ135 sensor = MQ135(27);

float temperature = 11.0; // assume current temperature. Recommended to measure with DHT22
float humidity = 57.0; // assume current humidity. Recommended to measure with DHT22


class MQ{
 public: void detectarValor(){
   
  /*float rzero = mq135_sensor.getRZero();
  float correctedRZero = mq135_sensor.getCorrectedRZero(temperature, humidity);
  float resistance = mq135_sensor.getResistance();
  float calidad = mq135_sensor.getPPM();*/
  float correctedPPM = sensor.getCorrectedPPM(temperature, humidity);
  if (correctedPPM>20000 and correctedPPM<24000){
    Serial.print("\n CALIDAD: ");
    Serial.print(correctedPPM);
    Serial.print("\t Aire ambiente");
  }
  else{
    if (correctedPPM<20000){
      Serial.print("\n CALIDAD: ");
      Serial.print(correctedPPM);
      Serial.print("\t Viento");
    }
    else{
      if (correctedPPM>24000){
        Serial.print("\n CALIDAD: ");
        Serial.print(correctedPPM);
        Serial.print("\t Co2");
      }
    }
  }

  delay(1000);
 }
  
};

    

// MQ135 gas sensor
//
// Datasheet can be found here: https://www.olimex.com/Products/Components/Sensors/SNS-MQ135/resources/SNS-MQ135.pdf
//
// Application
// They are used in air quality control equipments for buildings/offices, are suitable for detecting of NH3, NOx, alcohol, Benzene, smoke, CO2, etc
//
// Original creator of this library: https://github.com/GeorgK/MQ135
