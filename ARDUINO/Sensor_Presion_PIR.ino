int sensor = 27; //Sensor de presion en pin 27
int led = 18; //Led en pin 18
int PIR = 2; // Sensor de Movimiento en pin 2


void setup() {

  pinMode(PIR, INPUT);  //inicializacion sensor de movimiento
  pinMode(sensor, INPUT); //inicializacion sensor de presion
  pinMode(led, OUTPUT); //inicializacion led
  Serial.begin(9600);
  delay(60000); //Delay necesario para estabilizar el sensor de movimiento
}

void loop() {

  int lectura = analogRead(sensor); //Almacenamos lectura del sensor
  Serial.print("Lectura Analogica = ");
  Serial.println(lectura); //Imprimimos lectura del sensor

  int valor = digitalRead(PIR); //Almacenamos lectura del PIR
  Serial.print(" , PIR:");
  Serial.println(valor);  //Imprimimos lectura del PIR

  if (valor == HIGH || lectura > 0) { //Si la lectura es mayor que 0 (rango entre 0 y 200) 
    digitalWrite(led, HIGH);          //o tenemos un valor del PIR, encendemos el LED
  }
  else {
    digitalWrite(led, LOW);
  }
  delay(100);

}
