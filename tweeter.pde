/*
 This program reads incoming data (distances of an object) from the serial port and graphs it.
 In addition, it sporadically tweets the data. 
 */

import processing.serial.*;
import twitter4j.conf.*;
import twitter4j.*;
import twitter4j.auth.*;
import twitter4j.api.*;

Serial myPort;        // The serial port
float inData = 0;     // represents incoming data
float realData;       // second variable for holding value of inData

int xPos = 0;         // horizontal position of the graph
int timeCounter = 0;  // the starting value to increment from on the x-axis
int lastHeight = height;

static final int WINDOW_WIDTH = 600;
static final int WINDOW_HEIGHT = 400;
static final float DISTANCE_THRESHOLD = 15.0;     // in centimetres

String outOfRangeMsg = "No object within range.";

int interval = 1000;  // variables for controlling amount of time
int time;             // outOfRangeMsg displays on graph
boolean exceeded = false; 


Twitter twitter;

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

  // Store Keys and Tokens
  ConfigurationBuilder cb = new ConfigurationBuilder();
  cb.setOAuthConsumerKey("tB6RxrqJeikfnC6fZDW2n55PA");
  cb.setOAuthConsumerSecret("vb4IMyfEA1bdtVUM4dddBOK81ueeVhvKHTEMlVflAYxuUUNv5D");
  cb.setOAuthAccessToken("4859732989-yBPdYrhR8sZbZtOcHkH3QZQbeUsjl84B43dlNM8");
  cb.setOAuthAccessTokenSecret("ZNAE4SPWjcev8dkzlG0briqETaCyvbpLBzmsvAx6f8pHk");

  // Create and initialize twitter object
  twitter = new TwitterFactory(cb.build()).getInstance();

  time = millis();
  drawAxes(timeCounter);
}  

void draw() {
  textSize(15);
  text("Distance of Object(cm) VS Time (s)", 200, 50);
  stroke(0, 255, 0);

  line(xPos, lastHeight, xPos, height - inData);
  lastHeight = int(height - inData);

  // at the edge of the screen, go back to the beginning:
  if (xPos >= width) {
    xPos = 0;
    timeCounter += 10;
    background(0);
    drawAxes(timeCounter);
  } else {
    // increment the horizontal position:
    xPos++;
  }

  // Display outofRangeMsg when distance exceeds DISTANCE_THRESHOLD
  if (realData > DISTANCE_THRESHOLD) {
    textSize(12);
    text(outOfRangeMsg, WINDOW_WIDTH / 3, 4 * WINDOW_HEIGHT / 5);
    time = millis();
    exceeded = true;
  }

  // Erases outofRangeMsg after a specific interval
  if (millis() - time > interval && exceeded == true) {
    noStroke();
    fill(0, 0, 0);
    // draw a black rectangle to cover up text
    rect(WINDOW_WIDTH / 3, 4 * WINDOW_HEIGHT / 5 - 15, 140, 30);
    stroke(0, 255, 0);
    fill(0, 255, 0);
    exceeded = false;
  }
}


void serialEvent (Serial myPort) {
  // get the ASCII string:
  String inString = myPort.readStringUntil('\n');

  if (inString != null) {
    // trim off any whitespace:
    inString = trim(inString);

    // convert to a float and map to the screen height:
    inData = float(inString);
    
    realData = inData;

    // We only tweet if object is within DISTANCE_THRESHOLD from sensor
    if (inData < DISTANCE_THRESHOLD) {
      // Want infrequent tweets; use random number to decide if data should be tweeted
      if (int(random(100)) == 99) {
        tweetDistance(inString);
      }
    }

    inData = map(inData, 0, DISTANCE_THRESHOLD, 0, height);
  }
}

/*
Draws the x/y axes
 */

void drawAxes(int startX) {

  textSize(12);

  //Loops across window from left-right and 
  //sets up x-axis accordingly
  for (int x = 0; x <= width; x += (WINDOW_WIDTH / 10)) {
    fill(0, 255, 0);

    //print x-axis
    text(startX, x-20, height - 25);
    text("Time (s)", WINDOW_WIDTH/2, height - 10); 

    stroke(255);
    //print vertical lines
    line(x, height, x, 350);

    //time divided into 1 s intervals
    startX++;
  }

  //Loops across window from top-bottom and
  //sets up y-axis accordingly
  int distance = 0;
  for (int y = 0; y < height; y += (WINDOW_HEIGHT / 5)) {
    fill(0, 255, 0);

    //print y-axis
    text(DISTANCE_THRESHOLD - distance, 0, y);
    text("Distance of Object (cm)", 10, 30); 

    stroke(255);
    //print horizontal lines
    line(0, y, 50, y);

    //distance divided into 5 intervals
    distance += DISTANCE_THRESHOLD / 5;
  }
}

/*
Tweet the distance of a nearby object from the sensor. 
 */

void tweetDistance(String inString) {
  try
  {
    Status status = twitter.updateStatus("Nearby objected detected! It is located " + inString + " cm away from the sensor.");
    System.out.println("Status updated to [" + status.getText() + "].");
  }
  catch (TwitterException te)
  {
    System.out.println("Error: "+ te.getMessage());
  }
}