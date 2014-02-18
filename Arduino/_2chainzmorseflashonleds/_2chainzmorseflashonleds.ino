#include <Adafruit_NeoPixel.h>
int leds = 8;
int port = 7;
#define PIN 6

#define NUM_LIGHTS  8
Adafruit_NeoPixel strip = Adafruit_NeoPixel(leds, port, NEO_GRB + NEO_KHZ800);
void setup(){
  strip.begin();//prepares strip
  strip.show();
}
void loop(){
  dotpartspace();            /*2*/
  dotpartspace();
  dashpartspace();
  dashpartspace();
  dashletterspace();
  
  dashpartspace();           /*c*/
  dotpartspace();
  dashpartspace();
  dotletterspace();
  
  dotpartspace();            /*h*/
  dotpartspace();
  dotpartspace();
  dotletterspace();
  
  dotpartspace();            /*a*/
  dashletterspace();
  
  dotpartspace();            /*i*/
  dotletterspace();
  
  dashpartspace();           /*n*/
  dotletterspace();
  
  dashpartspace();           /*z*/
  dashpartspace();
  dotpartspace();
  dotspace();
  
}
void dotpartspace(){
  strip.Color(235, 255, 0), 50;
  strip.setPixelColor(leds, port);
  delay(63);
  strip.show();
  delay(63);
}
void dashpartspace(){
  strip.Color(235, 255, 0), 50;
  strip.setPixelColor(leds, port);
  delay(188);
  strip.show();
  delay(63);
}
void dotletterspace(){
  strip.Color(235, 255, 0), 50;
  strip.setPixelColor(leds, port);
  delay(63);
  strip.show();
  delay(188);
}
void dashletterspace(){
  strip.Color(235, 255, 0), 50;
  strip.setPixelColor(leds, port);
  delay(188);
  strip.show();
  delay(188);
}
void dotspace(){
  strip.Color(235, 255, 0), 50;
  strip.setPixelColor(leds, port);
  delay(63);
  strip.show();
  delay(438);
}
void dashspace(){
  strip.Color(235, 255, 0), 50;
  strip.setPixelColor(leds, port);
  delay(188);
  strip.show();
  delay(438);
}
  
