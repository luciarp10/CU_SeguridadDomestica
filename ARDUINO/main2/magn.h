#define mc 14   //Sensor magnético en pin 14

class magn{

  public: int detectar_valor(){
    
    if(digitalRead(mc) == LOW) {     //Si la puerta está abierta, se enciende el led e imprime 1
      Serial.println("ventana cerrada");
      return 0;
    }else{
      Serial.println("ventana abierta");

      return 1;
    }
  }   
};
