#include <Adafruit_NeoPixel.h>

#define PIN 7
#define NUM_LIGHTS  20

Adafruit_NeoPixel strip = Adafruit_NeoPixel(20, 7, NEO_GRB + NEO_KHZ800);

void setup() {
  strip.begin();
  strip.show(); // Initialize all pixels to 'off'
  pinMode(10, INPUT);
   pinMode(9, INPUT);
   pinMode(8, INPUT);
   pinMode(3, INPUT);
   pinMode(12, INPUT);
   Serial.begin(9600);
}
  
void loop() {
   
  if (digitalRead(10) == 1) {
    
        Red();
            Serial.println("Red");
    }

  
  else if (digitalRead(9) == 1) {
    
  Blue();
  Serial.println("Blue");
}
  
  else if (digitalRead(8) == 1) {
    
  Yellow();
 Serial.println("Yellow");
  }
   
  else if (digitalRead(3) == 1) {
    
  colorWipe(strip.Color(225, 0, 0), 50); // Red
  colorWipe(strip.Color(225, 0, 0), 50); // Red2
  colorWipe(strip.Color(225, 225, 225), 50); // White
  colorWipe(strip.Color(225, 225, 225), 50); //White2
  colorWipe(strip.Color(0, 0, 225), 50); // Blue
   colorWipe(strip.Color(0, 0, 225), 50); // Blue2
   Serial.println("'MURICA");
  }
   
  else if (digitalRead(12) == 1) {
     
   rainbow(20);
  rainbowCycle(20);
  Serial.println("GrOoVy");
   }


 

}


  
void Red() {


  
    uint32_t low = strip.Color(0, 0, 0); 
    uint32_t high = strip.Color(225,0,0);
  
    // Turn them off
    for( int i = 0; i<NUM_LIGHTS; i++){
        strip.setPixelColor(i, high);
        strip.show();
        }
  }


void Blue() {


  
    uint32_t low = strip.Color(0, 0, 0); 
    uint32_t high = strip.Color(0,0,225);
  
    // Turn them off
    for( int i = 0; i<NUM_LIGHTS; i++){
        strip.setPixelColor(i, high);
        strip.show();
        }
  }


 
void Yellow() {


  
    uint32_t low = strip.Color(0, 0, 0); 
    uint32_t high = strip.Color(225,225,0);
  
    // Turn them off
    for( int i = 0; i<NUM_LIGHTS; i++){
        strip.setPixelColor(i, high);
        strip.show();
        }
  }

  
void colorWipe(uint32_t c, uint8_t wait) {

  
  for(uint16_t i=0; i<strip.numPixels(); i++) {
      strip.setPixelColor(i, c);
      strip.show();
      delay(wait);
  }
}
void rainbow(uint8_t wait) {

  uint16_t i, j;

  for(j=0; j<256; j++) {
    for(i=0; i<strip.numPixels(); i++) {
      strip.setPixelColor(i, Wheel((i+j) & 225));
    }
    strip.show();
    delay(wait);
  }
}

// Slightly different, this makes the rainbow equally distributed throughout
void rainbowCycle(uint8_t wait) {
  
  uint16_t i, j;

  for(j=0; j<256*5; j++) { // 5 cycles of all colors on wheel
    for(i=0; i< strip.numPixels(); i++) {
      strip.setPixelColor(i, Wheel(((i * 256 / strip.numPixels()) + j) & 255));
    }
    strip.show();
    delay(wait);
  }
}



// Input a value 0 to 255 to get a color value.
// The colours are a transition r - g - b - back to r.
uint32_t Wheel(byte WheelPos) {
  if(WheelPos < 85) {
   return strip.Color(WheelPos * 3, 255 - WheelPos * 3, 0);
  } else if(WheelPos < 170) {
   WheelPos -= 85;
   return strip.Color(255 - WheelPos * 3, 0, WheelPos * 3);
  } else {
   WheelPos -= 170;
   return strip.Color(0, WheelPos * 3, 255 - WheelPos * 3);
  }
}

