#include <Keypad.h>
#define ROW_NUM     4 // four rows
#define COLUMN_NUM  4 // four columns
#include <LiquidCrystal_I2C.h>


class teclado{
  
  LiquidCrystal_I2C lcd = LiquidCrystal_I2C (0x27, 16, 2);
  private: char keys[ROW_NUM][COLUMN_NUM] = {
    {'1', '2', '3', 'A'},
    {'4', '5', '6', 'B'},
    {'7', '8', '9', 'C'},
    {'*', '0', '#', 'D'}
  };
  private: byte pin_rows[ROW_NUM]= {19, 18, 5, 17}; // GIOP19, GIOP18, GIOP5, GIOP17 connect to the row pins 
  private: byte pin_column[COLUMN_NUM] = {16, 4, 0, 2};   // GIOP16, GIOP4, GIOP0, GIOP2 connect to the column pins
  private: char a[4]; 
  
  private: int i=0,C=0,count =0, j=0;
  private: bool encontrado = true;
  
  Keypad keypad = Keypad( makeKeymap(keys), pin_rows, pin_column, ROW_NUM, COLUMN_NUM );

  public: void inicializar(){
      lcd.init();  
      lcd.backlight();
      lcd.print("C.UBICUA");
      lcd.setCursor(0,1);
      lcd.print("LMDL");  
      delay(3000); 
      lcd.clear();    
  }
  public: bool introducir_contrasena(){
    lcd.clear();
    char contrasena[4]={'1','2','3','A'};
    C=0;
    a[4]={};
    count =0;
    i=0, j=0;
    encontrado = true;
    
    
    while (count!=-1){
        lcd.setCursor(0,0);
    
        if(i>3){
          while (j<sizeof(a) and encontrado){
            
            if(a[j]!=contrasena[j]){
              /*
              Serial.println("*****");
              Serial.println(a[j]);
              Serial.println(contrasena[j]);
              Serial.println("****");*/
              encontrado=false;
            }
            j++;
            
                    
          }
          
          if (encontrado){
            lcd.setCursor(0,1); 
            lcd.print("CORRECTO!!");
            count=-1;
            return true;
            
          }
          else{
            lcd.setCursor(0,1); 
            lcd.print("INCORRECTO!!");
            count = -1;
            return false;
            
          }           
    
        }
        else{
          int key = (int) keypad.getKey();
          if (key) {
            lcd.setCursor(C,0);
            lcd.print("*");  
            
            a[i]=key;
            i++;
            C++;
          }
        }
    }
    
    
  }

  public: void encenderSistema(){
    lcd.clear();
    lcd.setCursor(0,0);
    lcd.print("SISTEMA");
    lcd.setCursor(0,1);
    lcd.print("ACTIVADO");
    delay(1000);
    
  }
  public: void apagarSistema(){
    lcd.clear();
    lcd.setCursor(0,0);
    lcd.print("SISTEMA");
    lcd.setCursor(0,1);
    lcd.print("DESACTIVADO");
    delay(1000);
    
  }

};
