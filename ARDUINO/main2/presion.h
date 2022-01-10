
class presion{
  int sensor = 13; //Sensor de presion en pin 13
  public: void inicializar(){
      pinMode(sensor, INPUT); //inicializacion sensor de presion  
  }
  public: int detectar_valorpresion(){
      int valor = analogRead(sensor); //Almacenamos lectura del sensor
      Serial.print("valor presion = ");
      Serial.println(valor); //Imprimimos lectura del sensor
      return valor;
      
    
  }
  
};
