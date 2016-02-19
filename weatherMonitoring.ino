/**
 * This program simulates a simple weather monitoring system and graphs data on
 * Processing and Matlab. 
 */
 
#include "DHT.h"

#define LM35Pin 1
#define DHTPin 7
#define photocellPin 2
#define switchPin 11

int ambBrightness;      //the light intensity read from the photocell
float photocellVoltage; //actual voltage generated by photocell
float humidity;         //humidity measured by DHT11 sensor
float DHTTemp;          //temperature measured by DHT11 sensor
float LM35Temp;         //temperature measured by LM35 sensor

DHT dht(DHTPin, DHT11); //DHT object

void setup() {
  Serial.begin(9600);
  dht.begin();          //Initialize DHT11 sensor
  pinMode(LM35Pin, INPUT);
  pinMode(photocellPin, INPUT);
  pinMode(switchPin, INPUT);
}

void loop() {
  humidity = dht.readHumidity();
  DHTTemp = dht.readTemperature();
  
  //since voltages ranging from 0 to 5000 mV are mapped by analogRead()
  //to integers ranging from 0 to 1023, we multiply the return value by 5000/1024
  //to get the actual voltage (in mV) generated by a sensor.
  //Need to also divide by 10 for LM35 sensor to account for 10 mV per degree Celsius.
  LM35Temp = (500.0 * analogRead(LM35Pin)) / 1024;
  ambBrightness = analogRead(photocellPin);
  photocellVoltage = ambBrightness * 5000.0 / 1024;
  
  if(digitalRead(switchPin)) {
    // When switch is on, print one sensor's data to serial monitor
    printData(photocellVoltage);
  }
  else{
    // When switch is off, print detailed data of all sensors to serial monitor
    printDataWithText();
  }
}


/**
 * Prints temperature, ambient light level, and humidity data to serial 
 * monitor window with text/units
 */

void printDataWithText(){
  Serial.print("Humidity: ");
  Serial.print(humidity);
  Serial.print(" %\t");
  Serial.print("DHT11 Temperature: ");
  Serial.print(DHTTemp);
  Serial.print(" ");
  Serial.print(char(176));    //degree symbol
  Serial.print("C\t");
  Serial.print("LM35 Temperature: ");
  Serial.print(LM35Temp);
  Serial.print(" ");
  Serial.print(char(176));    //degree symbol
  Serial.print("C\t");
  Serial.print("Ambient Brightness (Photocell Voltage): ");
  Serial.print(photocellVoltage);
  Serial.println(" mV");
  delay(500);
}

/**
 * Prints one type of data to serial monitor window (only the value) 
 */

void printData(float data){
  Serial.println(data);
  delay(200);
}

