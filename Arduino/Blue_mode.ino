#include <Adafruit_NeoPixel.h>

#define PIN 6
#define NUM_LIGHTS  4

Adafruit_NeoPixel strip = Adafruit_NeoPixel(4, 6, NEO_GRB + NEO_KHZ800);

void setup() {
  strip.begin();
  strip.show(); // Initialize all pixels to 'off'
}

void loop() {
  Blue();
}
 
void Blue() {
  
    uint32_t low = strip.Color(0, 0, 0); 
    uint32_t high = strip.Color(0,0,200);
  
    // Turn them off
    for( int i = 0; i<NUM_LIGHTS; i++){
        strip.setPixelColor(i, high);
        strip.show();
    }   
}
