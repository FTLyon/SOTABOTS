#include <Adafruit_NeoPixel.h>
Adafruit_NeoPixel strip = Adafruit_NeoPixel(8, 7, NEO_GRB + NEO_KHZ800);
void setup(){                          //#leds port
  strip.begin();//prepares strip
  strip.show();
}
void loop(){
  dotpartspace();  //2
  dotpartspace();
  dashpartspace();
  dashpartspace();
  dashletterspace();
  
  dashpartspace();  //c
  dotpartspace();
  dashpartspace();
  dotletterspace();
  
  dotpartspace();    //h
  dotpartspace();
  dotpartspace();
  dotletterspace();
  
  dotpartspace();    //a
  dashletterspace();
  
  dotpartspace();    //i
  dotletterspace();
  
  dashpartspace();    //n
  dotletterspace();
  
  dashpartspace();    //z
  dashpartspace();
  dotpartspace();
  dotspace();
  
}
void dotpartspace(){
  strip.Color(235, 255, 0), 50;
  strip.setPixelColor(8, 7);
  delay(63);
  strip.show();
  delay(63);
}
void dashpartspace(){
  strip.Color(235, 255, 0), 50;
  strip.setPixelColor(8, 7);
  delay(188);
  strip.show();
  delay(63);
}
void dotletterspace(){
  strip.Color(235, 255, 0), 50;
  strip.setPixelColor(8, 7);
  delay(63);
  strip.show();
  delay(188);
}
void dashletterspace(){
  strip.Color(235, 255, 0), 50;
  strip.setPixelColor(8, 7);
  delay(188);
  strip.show();
  delay(188);
}
void dotspace(){
  strip.Color(235, 255, 0), 50;
  strip.setPixelColor(8, 7);
  delay(63);
  strip.show();
  delay(438);
}
void dashspace(){
  strip.Color(235, 255, 0), 50;
  strip.setPixelColor(8, 7);
  delay(188);
  strip.show();
  delay(438);
}
  
