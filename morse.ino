/**
 * This program: 
 * 1) prompts user for speed of Morse Code generation in WPM (words per minute)
 * 2) prompts user for message
 * 3) Produces the Morse code of the message on the LED
 * 4) repeats 2)
 */

// initialize the 7-segment display LEDs
const int ledAnodeA = 1;    // pin 1 on 7-seg connected to pin 1 in i/o
const int ledAnodeB = 6;    // pin 6 on 7-seg connected to pin 6 in i/o
const int ledF = 2;         // ;pin 2 on 7-seg connected to pin 2 in i/o
const int ledG = 3;         // pin 3 on 7-seg connected to pin 3 in i/o
const int ledE = 4;         // pin 4 on 7-seg connected to pin 4 in i/o
const int ledD = 5;         // pin 5 on 7-seg connected to pin 5 in i/o
const int ledDP = 7;        // pin 7 on 7-seg connected to pin 7 in i/o
const int ledC = 8;         // pin 8 on 7-seg connected to pin 8 in i/o
const int ledB = 9;         // pin 9 on 7-seg connected to pin 9 in i/o
const int ledA = 12;        // pin 10 on 7-seg connected to pin 12 in i/o

const int LED = 11;         // pin number of LED

const int buzzer = 10;      // pin number for the buzzer 
const int buzzerFreq = 60;  // frequency of buzzer noise (Hz)
const int cNote = 523;      // frequency representing C (musical note)
const int dNote = 587;      // frequency representing D (musical note)
const int eNote = 660;      // frequency representing E (musical note)

int WPM;
int dotLength;
int dashLength;


void setup() {

  Serial.begin(9600);
  
  pinMode(LED, OUTPUT);
  pinMode(buzzer, OUTPUT);

  pinMode(ledAnodeA, INPUT);
  pinMode(ledAnodeB, INPUT);
  pinMode(ledF, OUTPUT);
  pinMode(ledG, OUTPUT);
  pinMode(ledE, OUTPUT);
  pinMode(ledD, OUTPUT);
  pinMode(ledDP, OUTPUT);
  pinMode(ledC, OUTPUT);
  pinMode(ledB, OUTPUT);
  pinMode(ledA, OUTPUT);


  digitalWrite(ledAnodeA, HIGH);
  digitalWrite(ledAnodeB, HIGH);

  //Turn off the seven segment display
  turnOff7SegLED();
  
  //prompt for WPM
  Serial.println("Enter the speed of the Morse Code generation (words per minute):");
  
  //Wait for user input
  while (Serial.available() == 0) {  
    delay(1);
  }
  //store the user input in WPM and print it back
  WPM = Serial.parseInt();
  Serial.print("You entered: ");
  Serial.print(WPM);
  Serial.println(" WPM");

  //set dot length and dash length
  dotLength = 1200 / WPM;
  dashLength = 3 * dotLength;
}

void loop() {
  //turn off seven segment display in the beginning of each loop
  turnOff7SegLED();

  //prompt for a message
  Serial.println("Enter a new message:");

  //Wait for user input
  while (Serial.available() == 0) {  
    delay(1);
  }
  //store the user input in message and print it back
  String message = Serial.readString();
  Serial.print("You entered: ");
  Serial.println(message);

  //copy message to error
  String error = message.substring(0);
  error.toLowerCase();
 
 //if error or erase is printed by user, generate error sequence. 
  if(error.equals("error") || error.equals("erase")){
    int errorSequence[9] = {0};
    errorSequence[8] = -1;
    getLedForChar('e');
    morseLED(errorSequence);
  }

 //if melody is entered by user, play melody
  else if(error.equals("melody")) {
    playMelody();
  }

  // generate LED, 7-seg, and buzzer sequence based on user input
  else {
    for(int i = 0; i < message.length(); i++){
      getLedForChar(message.charAt(i));
      morseLED(getMorseForChar(message.charAt(i)));
      
    }
  }
}

//Sets the LED sequence for a single character.
void morseLED(int *ptrToSequence){
  
  //given the sequence, call writeLED accordingly
  for(int i = 0; *(ptrToSequence + i) != -1; i++){
    
    if(*(ptrToSequence + i) == 0){
      tone(buzzer, buzzerFreq, dotLength);
      writeLED(dotLength, HIGH);
    }
    
    if(*(ptrToSequence + i) == 1){
      tone(buzzer, buzzerFreq, dashLength);
      writeLED(dashLength, HIGH);
    }

    if(*(ptrToSequence + i) == 2){
      writeLED(dashLength, LOW);
    }
    //If we are at the last part of the sequence,
    //LED is off for 3 dot lengths 
    if(*(ptrToSequence + i + 1) == -1){
      writeLED(3 * dotLength, LOW);
    }

    //Otherwise LED is off for one dot length
    else{
      writeLED(dotLength, LOW);
    }
    
  }

  
}

// control the state of the led
void writeLED(int length, int state){
  digitalWrite(LED, state);
  delay(length);
}



//generate an array for a character that is used by other functions to generate a LED sequence
// 0 = dot, 1 = dash, 2 = silence, -1 = end
int * getMorseForChar(char c) {

  static int sequence[10];
  
  switch(c){

      case('a'): case('A'):
        sequence[0] = 0; sequence[1] = 1; sequence[2] = -1;
        break;

      case('b'): case('B'):
        sequence[0] = 1; sequence[1] = 0; sequence[2] = 0; sequence[3] = 0; sequence[4] = -1; 
        break;

       case('c'): case('C'):
        sequence[0] = 1; sequence[1] = 0; sequence[2] = 1; sequence[3] = 0; sequence[4] = -1;
        break;

       case('d'): case('D'):
        sequence[0] = 1; sequence[1] = 0; sequence[2] = 0; sequence[3] = -1; 
        break;

      case('e'): case('E'):
        sequence[0] = 0; sequence[1] = -1; 
        break;

      case('f'): case('F'):
        sequence[0] = 0; sequence[1] = 0; sequence[2] = 1; sequence[3] = 0; sequence[4] = -1;
        break;

      case('g'): case('G'):
        sequence[0] = 1; sequence[1] = 1; sequence[2] = 0; sequence[3] = -1;
        break;

      case('h'): case('H'):
        sequence[0] = 0; sequence[1] = 0; sequence[2] = 0; sequence[3] = 0; sequence[4] = -1;
        break;

      case('i'): case('I'):
        sequence[0] = 0; sequence[1] = 0; sequence[2] = -1; 
        break;

      case('j'): case('J'):
        sequence[0] = 0; sequence[1] = 1; sequence[2] = 1; sequence[3] = 1; sequence[4] = -1;
        break;

      case('k'): case('K'):
        sequence[0] = 1; sequence[1] = 0; sequence[2] = 1; sequence[3] = -1;
        break;

      case('l'): case('L'):
        sequence[0] = 0; sequence[1] = 1; sequence[2] = 0; sequence[3] = 0; sequence[4] = -1;
        break;

      case('m'): case('M'):
        sequence[0] = 1; sequence[1] = 1; sequence[2] = -1; 
        break;

      case('n'): case('N'):
        sequence[0] = 1; sequence[1] = 0; sequence[2] = -1;
        break;

      case('o'): case('O'):
        sequence[0] = 1; sequence[1] = 1; sequence[2] = 1; sequence[3] = -1;
        break;

      case('p'): case('P'):
        sequence[0] = 0; sequence[1] = 1; sequence[2] = 1; sequence[3] = 0; sequence[4] = -1;
        break;

      case('q'): case('Q'):
        sequence[0] = 1; sequence[1] = 1; sequence[2] = 0; sequence[3] = 1; sequence[4] = -1;
        break;

      case('r'): case('R'):
        sequence[0] = 0; sequence[1] = 1; sequence[2] = 0; sequence[3] = -1; 
        break;

      case('s'): case('S'):
        sequence[0] = 0; sequence[1] = 0; sequence[2] = 0; sequence[3] = -1;
        break;

      case('t'): case('T'):
        sequence[0] = 1; sequence[1] = -1;
        break;

      case('u'): case('U'):
        sequence[0] = 0; sequence[1] = 0; sequence[2] = 1; sequence[3] = -1;
        break;

      case('v'): case('V'):
        sequence[0] = 0; sequence[1] = 0; sequence[2] = 0; sequence[3] = 1; sequence[4] = -1;
        break;

      case('w'): case('W'):
        sequence[0] = 0; sequence[1] = 1; sequence[2] = 1; sequence[3] = -1;
        break;

      case('x'): case('X'):
        sequence[0] = 1; sequence[1] = 0; sequence[2] = 0; sequence[3] = 1; sequence[4] = -1;
        break;

      case('y'): case('Y'):
        sequence[0] = 1; sequence[1] = 0; sequence[2] = 1; sequence[3] = 1; sequence[4] = -1;
        break;

      case('z'): case('Z'):
        sequence[0] = 1; sequence[1] = 1; sequence[2] = 0; sequence[3] = 0; sequence[4] = -1;
        break;

      case('1'):
        sequence[0] = 0; sequence[1] = 1; sequence[2] = 1; sequence[3] = 1; sequence[4] = 1;
        sequence[5] = -1;
        break;

      case('2'):
        sequence[0] = 0; sequence[1] = 0; sequence[2] = 1; sequence[3] = 1; sequence[4] = 1;
        sequence[5] = -1;
        break;

      case('3'):
        sequence[0] = 0; sequence[1] = 0; sequence[2] = 0; sequence[3] = 1; sequence[4] = 1;
        sequence[5] = -1;
        break;

      case('4'):
        sequence[0] = 0; sequence[1] = 0; sequence[2] = 0; sequence[3] = 0; sequence[4] = 1;
        sequence[5] = -1;
        break;

      case('5'):
        sequence[0] = 0; sequence[1] = 0; sequence[2] = 0; sequence[3] = 0; sequence[4] = 0;
        sequence[5] = -1;
        break;

      case('6'):
        sequence[0] = 1; sequence[1] = 0; sequence[2] = 0; sequence[3] = 0; sequence[4] = 0;
        sequence[5] = -1;
        break;

      case('7'):
        sequence[0] = 1; sequence[1] = 1; sequence[2] = 0; sequence[3] = 0; sequence[4] = 0;
        sequence[5] = -1;
        break;

      case('8'):
        sequence[0] = 1; sequence[1] = 1; sequence[2] = 1; sequence[3] = 0; sequence[4] = 0;
        sequence[5] = -1;
        break;

      case('9'):
        sequence[0] = 1; sequence[1] = 1; sequence[2] = 1; sequence[3] = 1; sequence[4] = 0;
        sequence[5] = -1;
        break;

      case('0'):
        sequence[0] = 1; sequence[1] = 1; sequence[2] = 1; sequence[3] = 1; sequence[4] = 1;
        sequence[5] = -1;
        break;

      case('.'):
        sequence[0] = 0; sequence[1] = 1; sequence[2] = 0; sequence[3] = 1; sequence[4] = 0;
        sequence[5] = 1; sequence[6] = -1;
        break;

      case(','):
        sequence[0] = 1; sequence[1] = 1; sequence[2] = 0; sequence[3] = 0; sequence[4] = 1;
        sequence[5] = 1; sequence[6] = -1;
        break;

      case(':'):
        sequence[0] = 1; sequence[1] = 1; sequence[2] = 1; sequence[3] = 0; sequence[4] = 0;
        sequence[5] = 0; sequence[6] = -1;
        break;
        
      case('-'):
        sequence[0] = 1; sequence[1] = 0; sequence[2] = 0; sequence[3] = 0; sequence[4] = 0;
        sequence[5] = 1; sequence[6] = -1;
        break;

      //space character represents 1 dot length 
      //of silence, to add to the 3 after the previous character
      //and the 3 that comes after the space (3 + 1 + 3 = 7)
      case(' '):
        sequence[0] = 2; sequence[1] = -1;
        break;

      default: 
        sequence[0] = 0; sequence[1] = 0; sequence[2] = 0; sequence[3] = 0; sequence[4] = 0;
        sequence[5] = 0; sequence[6] = 0; sequence[7] = 0; sequence[8] = -1;
        break;
    }
    
  
  return sequence;
  
}


//print the input character onto the 7-seg display. 
void getLedForChar(char c) {
  
  switch(c){
    
      case('a'): case('A'):
        set7SegLED(LOW, LOW, LOW, HIGH, HIGH, LOW, LOW, LOW);
        break;
      case('b'): case('B'):
        set7SegLED(LOW, LOW, LOW, LOW, HIGH, LOW, HIGH, HIGH);
        break;
       case('c'): case('C'):
        set7SegLED(LOW, HIGH, LOW, LOW, HIGH, HIGH, HIGH, LOW);
        break;
       case('d'): case('D'):
        set7SegLED(HIGH, LOW, LOW, LOW, HIGH, LOW, LOW, HIGH);
        break;
      case('e'): case('E'): default:
        set7SegLED(LOW, LOW, LOW, LOW, HIGH, HIGH, HIGH, LOW);
        break;
      case('f'): case('F'):
        set7SegLED(LOW, LOW, LOW, HIGH, HIGH, HIGH, HIGH, LOW);
        break;
      case('g'): case('G'): case('9'):
        set7SegLED(LOW, LOW, HIGH, LOW, HIGH, LOW, LOW, LOW);
        break;
      case('h'): case('H'): case('k'): case('K'): case('x'): case('X'):
        set7SegLED(LOW, LOW, LOW, HIGH, HIGH, LOW, LOW, HIGH);
        break;
      case('i'): case('I'):
        set7SegLED(LOW, HIGH, LOW, HIGH, HIGH, HIGH, HIGH, HIGH);
        break;
      case('j'): case('J'):
        set7SegLED(HIGH, HIGH, LOW, LOW, HIGH, LOW, LOW, HIGH);
        break;
      case('l'): case('L'):
        set7SegLED(LOW, HIGH, LOW, LOW, HIGH, HIGH, HIGH, HIGH);
        break;
      case('m'): case('M'):
        set7SegLED(HIGH, HIGH, LOW, HIGH, HIGH, LOW, HIGH, LOW);
        break;
      case('n'): case('N'):
        set7SegLED(HIGH, LOW, LOW, HIGH, HIGH, LOW, HIGH, HIGH);
        break;
      case('o'): case('O'): case('0'): 
        set7SegLED(LOW, HIGH, LOW, LOW, HIGH, LOW, LOW, LOW);
        break;
      case('p'): case('P'):
        set7SegLED(LOW, LOW, LOW, HIGH, HIGH, HIGH, LOW, LOW);
        break;
      case('q'): case('Q'): 
        set7SegLED(LOW, LOW, HIGH, HIGH, HIGH, LOW, LOW, LOW);
        break;
      case('r'): case('R'):
        set7SegLED(HIGH, LOW, LOW, HIGH, HIGH, HIGH, HIGH, HIGH);
        break;
      case('s'): case('S'): case('5'):
        set7SegLED(LOW, LOW, HIGH, LOW, HIGH, LOW, HIGH, LOW);
        break;
      case('t'): case('T'):
        set7SegLED(LOW, LOW, LOW, LOW, HIGH, HIGH, HIGH, HIGH);
        break;
      case('u'): case('U'):
        set7SegLED(LOW, HIGH, LOW, LOW, HIGH, LOW, LOW, HIGH);
        break;
      case('v'): case('V'):
        set7SegLED(HIGH, HIGH, LOW, LOW, HIGH, LOW, HIGH, HIGH);
        break;
      case('w'): case('W'):
        set7SegLED(LOW, HIGH, HIGH, LOW, HIGH, HIGH, LOW, HIGH);
        break;
      case('y'): case('Y'):
        set7SegLED(LOW, LOW, HIGH, LOW, HIGH, LOW, LOW, HIGH);
        break;
      case('z'): case('Z'): case('2'):
        set7SegLED(HIGH, LOW, LOW, LOW, HIGH, HIGH, LOW, LOW);
        break;
      case('1'):
        set7SegLED(HIGH, HIGH, HIGH, HIGH, HIGH, LOW, LOW, HIGH);
        break;
      case('3'):
        set7SegLED(HIGH, LOW, HIGH, LOW, HIGH, LOW, LOW, LOW);
        break;
      case('4'):
        set7SegLED(LOW, LOW, HIGH, HIGH, HIGH, LOW, LOW, HIGH);
        break;
      case('6'):
        set7SegLED(LOW, LOW, LOW, LOW, HIGH, LOW, HIGH, LOW);
        break;
      case('7'):
        set7SegLED(HIGH, HIGH, HIGH, HIGH, HIGH, LOW, LOW, LOW);
        break;
      case('8'):
        set7SegLED(LOW, LOW, LOW, LOW, HIGH, LOW, LOW, LOW);
        break;
      case('.'):
        set7SegLED(HIGH, HIGH, HIGH, HIGH, LOW, HIGH, HIGH, HIGH); 
        break;
        
      case(','): case(':'): case('-'): case(' '): 
        turnOff7SegLED();
        break;
    }
}

//turns off the LEDs in the 7-seg display
void turnOff7SegLED() {
    set7SegLED(HIGH, HIGH, HIGH, HIGH, HIGH, HIGH, HIGH, HIGH);
}

void set7SegLED(int stateF, int stateG, int stateE, int stateD, int stateDP, int stateC, int stateB, int stateA) {
    digitalWrite(ledF, stateF);
    digitalWrite(ledG, stateG);
    digitalWrite(ledE, stateE);
    digitalWrite(ledD, stateD);
    digitalWrite(ledDP, stateDP);
    digitalWrite(ledC, stateC);
    digitalWrite(ledB, stateB);
    digitalWrite(ledA, stateA);
}

//plays Merry had a Little Lamb on Buzzer and prints the note played on 7-seg
void playMelody() {

    tone(buzzer, eNote, dotLength);
    getLedForChar('E');
    delay(500);
    tone(buzzer, dNote, dotLength);
    getLedForChar('D');
    delay(500);
    tone(buzzer, cNote, dotLength);
    getLedForChar('C');
    delay(500);
    tone(buzzer, dNote, dotLength);
    getLedForChar('D');
    delay(500);
    tone(buzzer, eNote, dotLength);
    getLedForChar('E');
    delay(500);
    tone(buzzer, eNote, dotLength);
    delay(500);
    tone(buzzer, eNote, 2*dotLength);
    delay(1000);
    tone(buzzer, dNote, dotLength);
    getLedForChar('D');
    delay(500);
    tone(buzzer, dNote, dotLength);
    delay(500);
    tone(buzzer, dNote, 2*dotLength);
    delay(1000);
     tone(buzzer, eNote, dotLength);
     getLedForChar('E');
    delay(500);
    tone(buzzer, eNote, dotLength);
    delay(500);
    tone(buzzer, eNote, 2*dotLength);
    delay(1000);
    tone(buzzer, eNote, dotLength);
    delay(500);
    tone(buzzer, dNote, dotLength);
    getLedForChar('D');
    delay(500);
    tone(buzzer, cNote, dotLength);
    getLedForChar('C');
    delay(500);
    tone(buzzer, dNote, dotLength);
    getLedForChar('D');
    delay(500);
    tone(buzzer, eNote, dotLength);
    getLedForChar('E');
    delay(500);
    tone(buzzer, eNote, dotLength);
    delay(500);
    tone(buzzer, eNote, 2*dotLength);
    delay(1000);
    tone(buzzer, dNote, dotLength);
    getLedForChar('D');
    delay(500);
    tone(buzzer, dNote, dotLength);
    delay(500);
    tone(buzzer, eNote, dotLength);
    getLedForChar('E');
    delay(500);
    tone(buzzer, dNote, dotLength);
    getLedForChar('D');
    delay(500);
    tone(buzzer, cNote, 8*dotLength);
    getLedForChar('C');
    delay(4000);
    turnOff7SegLED();
}

