
class movimiento{
  
  int led = 23; //Led en pin 18
  int PIR = 12; // Sensor de Movimiento en pin 2
  
  public: void inicializar() {
    pinMode(PIR, INPUT);  //inicializacion sensor de movimiento 
     //Delay necesario para estabilizar el sensor de movimiento
  }
  
  public: int detectar_valor(){
      int valor = digitalRead(PIR); //Almacenamos lectura del PIR
      Serial.print(" , PIR:");
      Serial.println(valor);  //Imprimimos lectura del PIR
        if (valor == HIGH) {
          return 1;     //presencia cerca  
  
        }
        else {
          return 0;
          
        }
        delay(100);

    
  }



};
