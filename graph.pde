 //<>//
// This program takes ASCII-encoded strings
// from the serial port at 9600 baud and graphs them. It expects values in the
// range 0 to 5000, followed by a newline, or newline and carriage return

import processing.serial.*;

Serial myPort;        // The serial port
int xPos = 0;         // horizontal position of the graph
float inByte = 0;     // represents incoming data
int timeCounter = 0;  // the starting value to increment from on the x-axis
int lastHeight = height;
static final int WINDOW_WIDTH = 600;
static final int WINDOW_HEIGHT = 400;

void setup () {
  // set the window size:
  size(600, 400);

  // List all the available serial ports
  printArray(Serial.list());

  // Open Serial.list()[0] for Arduino
  myPort = new Serial(this, Serial.list()[0], 9600);

  // don't generate a serialEvent() unless you get a newline character:
  myPort.bufferUntil('\n');

  // set inital background:
  background(0);

  drawGrid(timeCounter);
}
void draw () {
  textSize(15);
  text("Photocell Voltage (mV) VS Time (s)", 200, 50);
  stroke(0, 255, 0);

  line(xPos, lastHeight, xPos, height - inByte);
  lastHeight = int(height - inByte);

  // at the edge of the screen, go back to the beginning:
  if (xPos >= width) {
    xPos = 0;
    timeCounter += 10;
    drawGrid(timeCounter);
  } else {
    // increment the horizontal position:
    xPos++;
  }
}


void serialEvent (Serial myPort) {
  // get the ASCII string:
  String inString = myPort.readStringUntil('\n');

  if (inString != null) {
    // trim off any whitespace:
    inString = trim(inString);

    // convert to an int and map to the screen height:
    inByte = float(inString);
    // prints incoming data to console
    println(inByte);
    inByte = map(inByte, 0, 5000, 0, height);
  }
}


/*
Draws coordinate grid and the x/y axes
 */

void drawGrid(int startX) {

  textSize(12);

  //Clear window
  background(0);

  //Loops across window from left-right and 
  //sets grid up accordingly
  for (int x = 0; x <= width; x += (WINDOW_WIDTH / 10)) {
    fill(0, 255, 0);

    //print x-axis
    text(startX, x-20, height - 25);
    text("Time (s)", WINDOW_WIDTH/2, height - 10); 

    stroke(255);
    //print vertical lines
    line(x, height, x, 0);

    //time divided into 1 s intervals
    startX++;
  }

  //Loops across window from top-bottom and
  //sets grid up accordingly
  int voltageValue = 0;
  for (int y = 0; y < height; y += (WINDOW_HEIGHT / 5)) {
    fill(0, 255, 0);

    //print y-axis
    text(5000 - voltageValue, 0, y);
    text("Photocell Voltage (mV)", 10, 30); 

    stroke(255);
    //print horizontal lines
    line(0, y, width, y);

    //voltage divided into 1000 mV intervals
    voltageValue += 1000;
  }
}