/**
 * This program uses the HC-SR04 as an ultrasonic
 * range finder to calculate the distance of nearby objects
 * and prints the data to the serial monitor.
 */

#define LM35Pin 1
#define trigPin 5   //set digital I/O pin 5 to Trig terminal on HC_SR04
#define echoPin 6   //set digital I/O pin 6 to Echo terminal on HC_SR04

float duration; //stores the length of the pulse received from ECHO. 
float distance = 0;
float betterDistance = 0;
float LM35Temp;         //temperature measured by LM35 sensor
float stime = 0;
float ptime = 0;

void setup()
{ 
  Serial.begin(9600);
  pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT);
  pinMode(LM35Pin, INPUT);
}

void loop()
{
  LM35Temp = (500.0 * analogRead(LM35Pin)) / 1024; //obtain the temperature in degrees celsius
  stime = 331.5+(0.6*LM35Temp); //obtain the speed of sound in m/s
  ptime = 1/(stime/20000); //convert the speed of sound to cm/us and find the recriprocal
  digitalWrite(trigPin, HIGH); //set a 10us pulse as a trigger signal
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);
  duration = pulseIn(echoPin, HIGH); //calculate the duration of the pulse time
  distance = duration/ptime; //calculate the distance of the object from the sensor
  Serial.println(distance); //print the value onto the serial monitor
  delay(300); //set the cycle time to 300ms
}
