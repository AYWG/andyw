/*
 * Program that interfaces Arduino with LCD and numeric keypad. Includes:
 * - Scrolling text
 * - "Guess the Number" game
 */

/*
 * SETUP FOR PART 1: LCD
 */
//Display on, no cursor
#define DISPLAY_ON_NOCURS 0x0C
#define CURS_SHIFT_LEFT 0x10
#define DISPLAY_OFF 0x08
#define FUNCTION_SET 0x28
#define ENTRY_MODE_SET 0x06
#define CLEAR_DISPLAY 0x01

//Only for initialization procedure
#define INITIAL_FUNCTION_SET 0x03

// digital IO pin numbers for LCD pins
#define lcdRS 12
#define lcdEnable 11
#define lcdD4 5
#define lcdD5 4
#define lcdD6 3
#define lcdD7 2

// the data pins of the LCD (for 4-bit operations)
static const int dataPins[4] = {lcdD7, lcdD6, lcdD5, lcdD4};

/*
 * SETUP FOR PART 2: KEYPAD
 */

const byte row0 = 13;
const byte row1 = 10;
const byte row2 = 9;
const byte row3 = 8;

const byte coL0 = 7;
const byte coL1 = 6;
const byte coL2 = 1;

bool button_press = false;
int indexRow = 0;
int indexCol = 0;

//Instantiates Keypad object that uses pins 13, 10, 9, 8 as row pins
//and 7, 6, 1 as column pins. 
//This keypad has 4 rows and 3 columns, resulting in 12 keys. 
const byte rows = 4; //four rows
const byte cols = 3; //three columns
const char keys[rows][cols] = {
  {'1','2','3'},
  {'4','5','6'},
  {'7','8','9'},
  {'*','0','#'}
};

byte rowPins[rows] = {row0, row1, row2, row3}; //array of pins used as input for rows of keypad (rows 0-3)
byte colPins[cols] = {coL0, coL1, coL2}; //array of pins used as output for columns of keypad (columns 0-2)

/*
 * SETUP FOR PART 3: GUESSING GAME
 */
bool win = false;
int guess, playerGuess, min, max = 0;
int guessNum = 3;
char key;
bool difficulty = false;

long lastDebounceTime = 0;
long debounceDelay = 50;




void setup() {
  //set the row pins as inputs
 for(int i = 0; i < rows; i++)
  {
    pinMode(rowPins[i], INPUT_PULLUP);
  }
  
  //set column pins as outputs
  for(int j = 0; j < cols; j++)
  {
    pinMode(colPins[j], OUTPUT);
  }

  //initially set all output pins as LOW
  turnOffOutputs();

  //set lcd pins as outputs
  pinMode(lcdRS, OUTPUT);
  pinMode(lcdEnable, OUTPUT);
  pinMode(lcdD4, OUTPUT);
  pinMode(lcdD5, OUTPUT);
  pinMode(lcdD6, OUTPUT);
  pinMode(lcdD7, OUTPUT);
  
  initializeLCD();
  commandLCD(DISPLAY_ON_NOCURS);
  startingMessages();
  delay(3000);
  clearLCD();
}

//Implement the Number Game
  void loop() {

  //first prompt user to set difficulty level
  printToLCD("Click * for easy");
  moveCursor(1, 6);
  printToLCD("# for hard");
  
  if(getKey() == '#')
  {
    guessNum = 6;
    guess = random(0, 99);
    difficulty = true;
  }

  else 
  {
  guessNum = 3;
  guess = random(0, 9);
  difficulty = false;
  }
  clearLCD();
  
  //start the game
  printToLCD("Take A Guess: ");
  while(guessNum != 0) 
  {
    playerGuess = getNumber(difficulty); //obtain the input from the player
    clearLCD();
    //if the player guessed correctly
    if(playerGuess == guess) 
    {
      win = true;
      break;
    }

    //if the player guessed too low
    if(playerGuess < guess)
    {
      guessNum--;
      if(playerGuess < guess - 10)
      {
        printToLCD("Way 2 Low");    
      }
      else
      {
      printToLCD("Guess Higher");
      }
      
    }

    //if the player guessed too high
    if(playerGuess > guess)
    {
       guessNum--;
      if(playerGuess > guess + 10)
      {
        printToLCD("Way 2 HIGH");    
      }
      else
      {
        printToLCD("Guess Lower");
      }
    }
  }
  clearLCD();
  
  if(win == false)
  {
    printToLCD("YOU LOST :(");
  }
  else
  {
    printToLCD("YOU WON!");
    win = false;
  }
  delay(2000);
  clearLCD(); 
}

/*****************PART 1 FUNCTIONS START********************/

/**
 * Implements the required starting messages for Part 1
 */
 
void startingMessages() {
  printToLCD("Hello & welcome");
  moveCursor(1, 0);
  printToLCD("to our demo!");
  delay(1000);

  clearLCD();
  printToLCD("ELEC 291-20C");
  moveCursor(1, 0);
  printToLCD("Team L2C-6A");

  //Add copy to top row to create illusion of 
  //text scrolling from bottom-left to top-right
  moveCursor(0, 16);
  printToLCD("Team L2C-6A");
  delay(500);

  int i = 0;
  
  //Loop that implements the appearance of scrolling text on LCD
  while (i < 27) {    // repeat 27 times to scroll message completely
    
    // Controls "ELEC 291-20C"
    moveCursor(0, 0);
    for (int j = 0; j < i; j++){
      commandLCD(CURS_SHIFT_LEFT);
    }
    if( i < 12) {    // stop writing this part after 12 iterations
      printToLCD("ELEC 291-20C");
    }

    // Controls the "Team L2C-6A" that appears
    // on the top row
    moveCursor(0, 16);
    for (int j = 0; j < i; j++){
      commandLCD(CURS_SHIFT_LEFT);
    }
    printToLCD("Team L2C-6A");

    // Controls the "Team L2C-6A" that appears
    // on the bottom row
    moveCursor(1, 0);
    for (int j = 0; j < i; j++){
      commandLCD(CURS_SHIFT_LEFT);
    }
    if(i < 11) {    // stop writing this part after 11 iterations
      printToLCD("Team L2C-6A");
    }
    
    i++;
    delay(500);
    clearLCD();
    
  }
}

/**
 * Initializes the LCD after powering on.
 */
 
void initializeLCD() {
  
  delay(50);    //Wait 50 ms after LCD powers on
  digitalWrite(lcdRS, LOW);
  digitalWrite(lcdEnable, LOW);
  
  writeHighBits(INITIAL_FUNCTION_SET);
  delay(5);
  writeHighBits(INITIAL_FUNCTION_SET);
  delayMicroseconds(150);
  writeHighBits(INITIAL_FUNCTION_SET);
  writeHighBits(0x02);

  //4-bit interface, 2 display lines, 5x8 font
  commandLCD(FUNCTION_SET);
  
  commandLCD(DISPLAY_OFF);
  clearLCD();

  //set address counter to increment after subsequent write operations
  commandLCD(ENTRY_MODE_SET);
}

/**
 * Clears LCD.
 */
 
void clearLCD() {
  commandLCD(CLEAR_DISPLAY);
  delay(2);
}

/**
 * Prints the given string on to the LCD (at the
 * location of the cursor)
 */
void printToLCD(const char *string) {
  for (int i = 0; *(string + i) != '\0'; i++) {
    writeLCD((*(string + i)));
  }
}

/**
 * Moves the LCD cursor to the specified row and col on the display, 
 * where the top row corresponds to row = 0, the bottom
 * row corresponds to row = 1, the left-most column corresponds
 * to col = 0, and the right-most column corresponds to col = 15
 */

void moveCursor(int row, int col) {
  int address = 0;
  if (row == 0) {
    //First column of first row corresponds to address 0x80, which is 128
    address = 128; 
  }
  // row = 1
  else {
    //First column of second row corresponds to addres 0xC0, which is 192
    address = 192; 
  }

  //add col to address to get correct location on LCD
  address += col;

  commandLCD((byte)(address));
}

/**
 * Sends data to be displayed on the LCD
 */
 
void writeLCD(byte data) {
  digitalWrite(lcdRS, HIGH);
  commandLCD(data);
  digitalWrite(lcdRS, LOW);
}

/**
 * Sends an instruction (data) to the LCD
 */
 
void commandLCD(byte data) {
  writeHighBits(data);
  writeLowBits(data);
}

/*
 * Writes most significant 4 bits of data to pins D7-D4 of the LCD 
 */

void writeHighBits (byte data) {
  byte mask;
  int i = 0;

  // Loops through the most significant 4 bits of data
  // and writes HIGH to the corresponding pin if bit is equal to 1
  for (mask = 0x80; mask != 0x08; mask >>= 1) {
    digitalWrite(dataPins[i], mask & data);
    i++;
  }
  flashEnable();
}

/**
 * Writes least significant 4 bits of data to pins D7-D4 of the LCD
 */

void writeLowBits (byte data) {
  byte mask;
  int i = 0;

  // Loops through the least significant 4 bits of data
  // and writes HIGH to the corresponding pin if bit is equal to 1
  for (mask = 0x08; mask != 0x00; mask >>= 1) {
    digitalWrite(dataPins[i], mask & data);
    i++;
  }
  flashEnable();
}

/**
 * Writes LOW, HIGH, then LOW to the Enable pin on the LCD
 * so that the LCD can transmit data. 
 */
 
void flashEnable() {
  digitalWrite(lcdEnable, HIGH);
  digitalWrite(lcdEnable, LOW);
  delayMicroseconds(40); //typical command execution time
}

/*****************PART 1 FUNCTIONS END********************/

//function that reads from the keypad and returns a character corresponding
//to the button pressed
char getKey()
{
  turnOffOutputs();
  button_press = false;
  //if the button has not been pressed yet, keep checking the input pins
  while(button_press == false) 
  {
    //checks every input pin
    for(indexRow = 0; indexRow < rows; indexRow++)
    {
      //if the user has pressed a key, stop checking and move on
      if(digitalRead(rowPins[indexRow]) == LOW)
      {
        button_press = true; //the key has been pressed
        break;
        }
      }
    }
  
  //set the output to be HIGH one at a time
  for(int indexCol = 0; indexCol < cols; indexCol++)
  {
    digitalWrite(colPins[indexCol], HIGH);
    //obtain which two input and output pins are connected
    //and based on this, find the corresponding pressed key on the keypad
    if(digitalRead(rowPins[indexRow]) == HIGH)
    {
      button_press = false;
      digitalWrite(colPins[indexCol], LOW);
      while(1) {
        if(digitalRead(rowPins[indexRow]) == HIGH) {
          break;
        }
      }
      //return the matched character from the 2D array
      return keys[indexRow][indexCol];
    }
    digitalWrite(colPins[indexCol], LOW);
  }
}

//Function to make all output as LOW
void turnOffOutputs() {
  for(int index = 0; index < cols; index++) { 
        digitalWrite(colPins[index],LOW);
  }
}

//returns the character obtained from the user as its corresponding
//number, and determines if the returned number should be one digit
//or two digits based on the set difficulty
int getNumber(bool diff)
{
  if(diff == true) //if the user chose the hard game
  {
  int a = getKey()-48; //obtain the first integer
  a *= 10; 
  int b = getKey()-48; //obtain the second integer
  return a + b; // return the resulting two-digit value
  }

  else
  {
    int c = getKey() - 48; //return the single-digit number
    return c;
  }
}


