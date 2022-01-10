#include "pitches.h"
#define buzzer 27

int playing = 0;
int melody[] = {NOTE_C4,NOTE_G3,NOTE_G3,NOTE_A3,NOTE_G3,0,NOTE_B3,NOTE_C4};
int noteDurations[] = {4,8,8,4,4,4,4,4};

class zumbador{
    //Variables globales
  
  
  // notes in the melody:
  
  
  //Como es una ESP32 tengo que declarar Tone y noTone
  public: void tone(byte pin, int freq, int duration) {
    ledcSetup(0, 2000, 8); // setup beeper
    ledcAttachPin(buzzer, 0); // attach beeper
    ledcWriteTone(0, freq); // play tone
    playing = pin; // store pin
  }
  
  public: void noTone() {
    tone(playing, 0, 1000);
  }
  
  // note durations: 4 = quarter note, 8 = eighth note, etc.:  
  
  
  public: void sonar_alarma() {
  
    // iterate over the notes of the melody:
    for (int thisNote = 0; thisNote < 8; thisNote++) {
  
      // to calculate the note duration, take one second divided by the note type.
  
      //e.g. quarter note = 1000 / 4, eighth note = 1000/8, etc.
  
      int noteDuration = 1000 / noteDurations[thisNote];
  
      tone(8, melody[thisNote], noteDuration);
  
      // to distinguish the notes, set a minimum time between them.
  
      // the note's duration + 30% seems to work well:
  
      int pauseBetweenNotes = noteDuration * 1.30;
  
      delay(pauseBetweenNotes);
    
  
    }
  }
  public: void parar_alarma(){
    noTone();
  }

  public: void sonar_temporal(int tiempo){
    tone(8, melody[7], tiempo*1000);
  }
  
  //no es necesario loop
    
};
