#include <Adafruit_NeoPixel.h>

#define PIN 6
#define NUM_LIGHTS  4

Adafruit_NeoPixel strip = Adafruit_NeoPixel(4, 6, NEO_GRB + NEO_KHZ800);

void setup() {
  strip.begin();
  strip.show(); // Initialize all pixels to 'off'
}

void loop() {

  Yellow();
}
 
void Yellow() {
    int dot = 63;
    int dash = 188;
    int partspace = 63;
    int letterspace = 189;
    int space = 438;
    
    
    uint32_t low = strip.Color(0, 0, 0); 
    uint32_t high = strip.Color(200,200,0);
  
    // Turn them off
    for( int i = 0; i<NUM_LIGHTS; i++){
        strip.setPixelColor(i, high);
        delay(dot);
        strip.setPixelColor(i, low);
        delay(partspace);
        strip.setPixelColor(i, high);
        delay(dot);
        strip.setPixelColor(i, low);
        delay(partspace);
        strip.setPixelColor(i, high);
        delay(dash);
        strip.setPixelColor(i, low);
        delay(partspace);
        strip.setPixelColor(i, high);
        delay(dash);
        strip.setPixelColor(i, low);
        delay(partspace);
        strip.setPixelColor(i, high);
        delay(dash);
        strip.setPixelColor(i, low);
        delay(letterspace);
        strip.setPixelColor(i, high);
        delay(dash);
        strip.setPixelColor(i, low);
        delay(partspace);
        strip.setPixelColor(i, high);
        delay(dash);
        strip.setPixelColor(i, low);
        delay(partspace);
        strip.setPixelColor(i, high);
        delay(dot);
        strip.setPixelColor(i, low);
        delay(letterspace);
        strip.setPixelColor(i, high);
        delay(dot);
        strip.setPixelColor(i, low);
        delay(partspace);
        strip.setPixelColor(i, high);
        delay(dot);
        strip.setPixelColor(i, low);
        delay(partspace);
        strip.setPixelColor(i, high);
        delay(dot);
        strip.setPixelColor(i, low);
        delay(partspace);
        strip.setPixelColor(i, high);
        delay(dot);
        strip.setPixelColor(i, low);
        delay(letterspace);
        strip.setPixelColor(i, high);
        delay(dash);
        strip.setPixelColor(i, low);
        delay(letterspace);
        strip.setPixelColor(i, high);
        delay(dot);
        strip.setPixelColor(i, low);
        delay(partspace);
        strip.setPixelColor(i, high);
        delay(dot);
        strip.setPixelColor(i, low);
        delay(letterspace);
        strip.setPixelColor(i, high);
        delay(dash);
        strip.setPixelColor(i, low);
        delay(partspace);
        strip.setPixelColor(i, high);
        delay(dot);
        strip.setPixelColor(i, low);
        delay(letterspace);
        strip.setPixelColor(i, high);
        delay(dash);
        strip.setPixelColor(i, low);
        delay(partspace);
        strip.setPixelColor(i, high);
        delay(dash);
        strip.setPixelColor(i, low);
        delay(partspace);
        strip.setPixelColor(i, high);
        delay(dot);
        strip.setPixelColor(i, low);
        delay(partspace);
        strip.setPixelColor(i, high);
        delay(dot);
        strip.setPixelColor(i, low);
        delay(space);
    }   
}
