
//import guicomponents.*;



/*
 -------------------- Project Sentry Gun --------------------
 ============================================================
 ----- An Open-Source Project, initiated by Bob Rudolph -----
 
 
 Help & Reference: http://projectsentrygun.rudolphlabs.com/make-your-own/using-the-software
 Forum: http://projectsentrygun.rudolphlabs.com/forum
 
 
 A few keyboard shortcuts:
 press 'p' for a random sound effect
 press 'b' to set background image
 hold 'r' and click+drag to form a rectangle  "fire-restricted" zone
 press SPACEBAR to toggle manual/autonomous modes
 press SHIFT to toggle arrow-key aiming in manual mode
 
 */


//   <===============================================================================================>
//   Begin custom values - change these camera dimensions to work with your turret
//   <===============================================================================================>

public int camWidth = 320;                   //   camera width (pixels),   usually 160*n
public int camHeight = 240;                  //   camera height (pixels),  usually 120*n

public boolean use_surf = false; //Use Surf Classifier to search for Reference Object
public int surf_sensivity = 3; //Recognition Sensitivity lower=higher sensivity=higher false positives
//public String surfRefFile = "D:/Sentry/robot_live2.jpg"; //Reference frame to recognize
public String surfRefFile = "data/robot_image.jpg"; //Reference frame to recognize

//   <===============================================================================================>
//   End custom values
//   <===============================================================================================>

boolean PRINT_FRAMERATE = true;     // set to true to print the framerate at the bottom of the IDE window

int[] diffPixelsColor = {
  255, 255, 0
};  // Red, green, blue values (0-255)  to show pixel as marked as target
public int effect = 0;                // Effect

public boolean mirrorCam = false;            //   set true to mirror camera image

public float xMin = 40.0;      //  Actual calibration values are loaded from "settings.txt".
public float xMax = 140.0;    //  If "settings.txt" is borken / unavailable, these defaults are used instead - 
public float yMin = 75.0;      //  otherwise, changing these lines will have no effect on your gun's calibration.
public float yMax = 105.0;    //

//import JMyron.*;
import g4p_controls.*;
import gohai.glvideo.*;
import blobDetection.*;
import processing.serial.*;
import ddf.minim.*;
import ddf.minim.ugens.*;
import java.awt.Frame;
import processing.opengl.*;                  // see note on OpenGL in void setup() 
import net.java.games.input.*;
import org.gamecontrolplus.*;
import org.gamecontrolplus.gui.*;
import org.apache.commons.math.optimization.linear.*;
import org.apache.commons.math.stat.clustering.*;
import org.apache.commons.math.stat.descriptive.summary.*;
import org.junit.runner.manipulation.*;
import junit.runner.*;
import com.stromberglabs.util.*;
import org.apache.commons.math.analysis.polynomials.*;
import org.hamcrest.internal.*;
import junit.extensions.*;
import org.apache.commons.math.analysis.integration.*;
import junit.framework.*;
import org.apache.commons.math.stat.inference.*;
import org.junit.runners.*;
import org.apache.commons.math.stat.*;
import org.apache.commons.math.complex.*;
import org.apache.commons.math.stat.descriptive.*;
import org.apache.commons.math.ode.jacobians.*;
import org.junit.experimental.max.*;
import org.junit.runners.model.*;
import org.apache.commons.math.special.*;
import org.apache.commons.math.stat.descriptive.rank.*;
import org.junit.internal.runners.statements.*;
import org.apache.commons.math.*;
import org.apache.commons.math.stat.correlation.*;
import org.junit.internal.matchers.*;
import org.junit.experimental.theories.internal.*;
import org.junit.runner.notification.*;
import org.apache.commons.math.stat.regression.*;
import org.junit.runner.*;
import org.junit.internal.runners.model.*;
import org.junit.*;
import org.apache.commons.math.util.*;
import org.junit.experimental.theories.suppliers.*;
import org.apache.commons.math.analysis.solvers.*;
import org.apache.commons.math.optimization.general.*;
import org.junit.experimental.results.*;
import org.junit.experimental.*;
import org.junit.experimental.theories.*;
import com.stromberglabs.jopensurf.*;
import org.apache.commons.math.analysis.interpolation.*;
import org.apache.commons.math.analysis.*;
import org.apache.commons.math.stat.descriptive.moment.*;
import org.apache.commons.math.ode.sampling.*;
import org.apache.commons.math.optimization.univariate.*;
import org.apache.commons.math.estimation.*;
import org.apache.commons.math.geometry.*;
import org.apache.commons.math.fraction.*;
import org.apache.commons.math.ode.events.*;
import org.junit.internal.*;
import org.apache.commons.math.genetics.*;
import junit.textui.*;
import org.junit.internal.runners.*;
import org.junit.experimental.runners.*;
import org.apache.commons.math.ode.*;
import org.apache.commons.math.random.*;
import org.apache.commons.math.transform.*;
import com.stromberglabs.cluster.*;
import org.apache.commons.math.optimization.direct.*;
import org.apache.commons.math.ode.nonstiff.*;
import org.apache.commons.math.linear.*;
import org.junit.matchers.*;
import org.junit.internal.requests.*;
import org.junit.internal.builders.*;
import org.hamcrest.*;
import org.hamcrest.core.*;
import org.apache.commons.math.optimization.*;
import org.apache.commons.math.distribution.*;
import org.apache.commons.math.stat.ranking.*;
import org.apache.commons.math.optimization.fitting.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.imageio.ImageIO;

public int minBlobArea = 30;                    //   minimum target size (pixels)
public int tolerance = 100;                      //   sensitivity to motion

public boolean runWithoutArduino = true;
public boolean connecting = false;

public Surf mSurfRef;
public Surf mSurfCap;
public Map<SURFInterestPoint, SURFInterestPoint> mAMatchingPoints;
public Map<SURFInterestPoint, SURFInterestPoint> mBMatchingPoints;
public Map<SURFInterestPoint, SURFInterestPoint> pointsA;
public Map<SURFInterestPoint, SURFInterestPoint> pointsB;
public BufferedImage imageRef;
public  BufferedImage captureImage;

public Serial arduinoPort;
//JMyron camInput;
GLCapture camInput;
BlobDetection target;
Blob blob;
Blob biggestBlob;


int[] Background;
int[] rawImage;
int[] rawBackground;
int[] currFrame;
int[] screenPixels;
public int targetX = camWidth/2;
public int targetY = camHeight/2;
int fire = 0;
int[] prevFire = {
  0, 0, 0, 0, 0, 0, 0, 0, 0, 0
};

float xRatio;
float yRatio;

int possibleX = camWidth/2;
int possibleY = camHeight/2;

int displayX = camWidth/2;
int displayY = camHeight/2;

int oldX = camWidth/2; // smoothing (contributed by Adam S.)
int oldY = camHeight/2; // smoothing
int xdiff; // smoothing
int ydiff; // smoothing
public float smoothingFactor = 0.8; // smoothing
public boolean activeSmoothing = true;

String strTargetx;
String strTargety;
String fireSelector;
String scanSelector;


public boolean showDifferentPixels = false;
public boolean showTargetBox = true;
public boolean showCameraView = true;
public boolean firingMode = true;             // true = semi,        false = auto
public boolean safety = true;
public boolean controlMode = false;           // true = autonomous,  false = manual
public boolean soundEffects =  false;         // set to true to enable sound effects by default
public boolean scanWhenIdle = true;
public boolean trackingMotion = true;

int idleTime = 10000;          // how many milliseconds to wait until scanning (when in scan mode)
int idleBeginTime = 0;
boolean scan = false;

public String serPortUsed;

int[][] fireRestrictedZones = new int[30][4];
int restrictedZone = 1;
boolean showRestrictedZones = false;

boolean selectingColor = false;
boolean trackingColor = false;
int trackColorTolerance = 100;
int trackColorRed = 255;
int trackColorGreen = 255;
int trackColorBlue = 255;

boolean selectingSafeColor = false;
boolean safeColor = false;
int safeColorMinSize = 500;
int safeColorTolerance = 100;
int safeColorRed = 0;
int safeColorGreen = 255;
int safeColorBlue = 0;

boolean useArrowKeys = false;   // use arrow keys to finely adjust the aiming (in manual mode)

public boolean useInputDevice = false;  // use a joystick or game Controler as input (in manual mode)
public boolean inputDeviceIsSetup = false;

public ControlIO controlIO;         // more stuff for using a joystick or game controller for input
public ControlDevice inputDevice;

public ControlButton[] buttons = new ControlButton[30];
public ControlSlider[] sliders = new ControlSlider[10];

public ControlButton[] fire_buttons = new ControlButton[0];
public ControlButton[] preciseAim_buttons = new ControlButton[0];
public ControlButton[] centerGun_buttons = new ControlButton[0];
public ControlButton[] autoOn_buttons = new ControlButton[0];
public ControlButton[] autoOff_buttons = new ControlButton[0];
public ControlButton[] inputToggle_buttons = new ControlButton[0];
public ControlButton[] randomSound_buttons = new ControlButton[0];

public ControlSlider[] pan_sliders = new ControlSlider[0];
public ControlSlider[] tilt_sliders = new ControlSlider[0];
public ControlSlider[] panInvert_sliders = new ControlSlider[0];
public ControlSlider[] tiltInvert_sliders = new ControlSlider[0];


public float xPosition = camWidth/2;
public float yPosition = camHeight/2;


String[] inStringSplit;  // buffer for backup
int controlMode_i, safety_i, firingMode_i, scanWhenIdle_i, trackingMotion_i, trackingColor_i, leadTarget_i, safeColor_i, 
  showRestrictedZones_i, showDifferentPixels_i, showTargetBox_i, showCameraView_i, mirrorCam_i, soundEffects_i;


void setup() {

  loadSettings();
  soundEffects =  false;  
  //size(camWidth, camHeight);                  // some users have reported a faster framerate when the code utilizes OpenGL. To try this, comment out this line and uncomment the line below.
  //  size(camWidth, camHeight, OPENGL);
  size (320, 240, P2D);
  minim = new Minim(this);

  //loadSounds();
  playSound(18);
  //camInput = new JMyron();
  camInput = new GLCapture(this);
  camInput.start();
  background(0);
  if (camInput.available()) {
    camInput.read();
    image(camInput, 0, 0, width, height);
    camInput.loadPixels();
    loadPixels();
    currFrame = camInput.pixels;
    rawImage = camInput.pixels;
    Background = camInput.pixels;
    rawBackground = camInput.pixels;
    screenPixels = camInput.pixels;
  }
  //camInput.start(camWidth, camHeight);
  //camInput.findGlobs(0);
  //camInput.adaptivity(1.01);
  //camInput.update();
  //currFrame = camInput.image();
  //rawImage = camInput.image();
  //Background = camInput.image();
  //rawBackground = camInput.image();
  //screenPixels = camInput.image();
  target = new BlobDetection(camWidth, camHeight);
  target.setThreshold(0.9);
  target.setPosDiscrimination(true);

  if (use_surf) {  
    captureImage = new BufferedImage(camWidth, camHeight, BufferedImage.OPAQUE);
    try {
      imageRef = ImageIO.read(new File(surfRefFile));
    } 
    catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    mSurfRef = new Surf(imageRef);
  }

  retryArduinoConnect();

  xRatio = (camWidth / (xMax - xMin));                         // used to allign sights with crosshairs on PC
  yRatio = (camHeight/ (yMax - yMin));                         //
  //drawControlPanel();
}



void draw() {
  if (PRINT_FRAMERATE) {
    println(frameRate);
  }

  if (controlMode) {              // autonomous mode
    autonomousMode();            //
  } else if (!controlMode) {        // manual mode
    manualMode();                //
  }

  if (fire == 1) {
    idleBeginTime = millis();
    scan = false;
  } else {
    if (millis() > idleBeginTime + idleTime && controlMode && scanWhenIdle) {
      scan = true;
    } else {
      scan = false;
    }
  }

  if (!safety) {
    fire = 0;
  }

  strTargetx = "000" + str(targetX);                   // make into 3-digit numbers
  strTargetx = strTargetx.substring(strTargetx.length()-3);
  strTargety = "000" + str(targetY);
  strTargety = strTargety.substring(strTargety.length()-3);
  fireSelector = str(0);
  if (firingMode) {
    fireSelector = str(1);
  } else {
    fireSelector = str(3);
  }
  if (scan) {
    scanSelector = str(1);
  } else {
    scanSelector = str(0);
  }
  //println('a' + strTargetx + strTargety  + str(fire) + fireSelector + scanSelector);
  if (!runWithoutArduino && !connecting) {
    arduinoPort.write('a' + strTargetx + strTargety + str(fire) + fireSelector + scanSelector);   // send to arduino
  }

  if ((keyPressed && key == 't') || showRestrictedZones) {
    for (int col = 0; col <= restrictedZone; col++) {
      noStroke();
      fill(0, 255, 0, 100);
      rect(fireRestrictedZones[col][0], fireRestrictedZones[col][2], fireRestrictedZones[col][1]-fireRestrictedZones[col][0], fireRestrictedZones[col][3]-fireRestrictedZones[col][2]);
    }
  }
  if (selectingColor) {
    stroke(190, 0, 190);
    strokeWeight(2);
    fill(red(currFrame[(mouseY*width)+mouseX]), green(currFrame[(mouseY*width)+mouseX]), blue(currFrame[(mouseY*width)+mouseX]));
    rect(mouseX+2, mouseY+2, 30, 30);
  }

  if (selectingSafeColor) {
    stroke(0, 255, 0);
    strokeWeight(2);
    fill(red(currFrame[(mouseY*width)+mouseX]), green(currFrame[(mouseY*width)+mouseX]), blue(currFrame[(mouseY*width)+mouseX]));
    rect(mouseX+2, mouseY+2, 30, 30);
  }

  soundTimer++;
  if (soundTimer == soundInterval) {
    randomIdleSound();
    soundTimer = 0;
  }

  for (int i = 9; i >= 1; i--) {
    prevFire[i] = prevFire[i-1];
  }
  prevFire[0] = fire;
  int sumNewFire = prevFire[0] + prevFire[1] + prevFire[2] + prevFire[3] + prevFire[4];
  int sumPrevFire = prevFire[5] + prevFire[6] + prevFire[7] + prevFire[8] + prevFire[9];

  if (sumNewFire == 0 && sumPrevFire == 5) {     // target departed screen
    int s = int(random(0, 6));
    if (s == 0)
      playSound(1);
    if (s == 1)
      playSound(5);
    if (s == 2)
      playSound(9);
    if (s == 3)
      playSound(12);
    if (s == 4)
      playSound(13);
    if (s == 5)
      playSound(20);
  }

  if (fire == 1)
    strokeWeight(3);
  if (fire == 0)
    strokeWeight(1);
  stroke(255, 0, 0);                     //draw crosshairs
  noFill();                            // 
  line(displayX, 0, displayX, camHeight);  //
  line(0, displayY, camWidth, displayY);   //
  ellipse(displayX, displayY, 20, 20);     //
  ellipse(displayX, displayY, 28, 22);     //
  ellipse(displayX, displayY, 36, 24);     //

  updateControlPanels();
  prevTargetX = targetX;
  prevTargetY = targetY;
}

void autonomousMode() {
  if (inputDeviceIsSetup) {
    checkInputDevice();
  }

  if (selectingColor || selectingSafeColor) {
    cursor(1);
  } else {
    cursor(0);
  }
  updateCamera();
  rawBackground = camInput.pixels;
  rawImage = camInput.pixels;
  //camInput.update();
  //rawBackground = camInput.retinaImage();
  //rawImage = camInput.image();


  if (mirrorCam) {
    for (int i = 0; i < camWidth*camHeight; i++) {
      int y = floor(i/camWidth);
      int x = i - (y*camWidth);
      x = camWidth-x;
      currFrame[i] = rawImage[(y*camWidth) + x-1];
      Background[i] = rawBackground[(y*camWidth) + x-1];
    }
  } else {
    currFrame = rawImage;
    Background = rawBackground;
  }

  loadPixels();
  int safeColorPixelsCounter = 0;

  for (int i = 0; i < camWidth*camHeight; i++) {
    if (showCameraView) {
      pixels[i] = currFrame[i];
    } else {
      pixels[i] = color(0, 0, 0);
    }        

    boolean motion = (((abs(red(currFrame[i])-red(Background[i])) + abs(green(currFrame[i])-green(Background[i])) + abs(blue(currFrame[i])-blue(Background[i]))) > (200-tolerance)) && trackingMotion);
    boolean isTrackedColor = (((abs(red(currFrame[i])-trackColorRed) + abs(green(currFrame[i])-trackColorGreen) + abs(blue(currFrame[i])-trackColorBlue)) < trackColorTolerance) && trackingColor);

    boolean isSafeColor = (((abs(red(currFrame[i])-safeColorRed) + abs(green(currFrame[i])-safeColorGreen) + abs(blue(currFrame[i])-safeColorBlue)) < safeColorTolerance) && safeColor);

    if (motion || isTrackedColor) {
      screenPixels[i] = color(255, 255, 255);
      if (showDifferentPixels) {
        if (effect == 0) {
          pixels[i] = color(diffPixelsColor[0], diffPixelsColor[1], diffPixelsColor[2]);
        } else if (effect == 1) {
          pixels[i] = color((diffPixelsColor[0] + red(currFrame[i]))/2, (diffPixelsColor[1] + green(currFrame[i]))/2, (diffPixelsColor[2] + blue(currFrame[i]))/2);
        } else if (effect == 2) {
          pixels[i] = color(255-red(currFrame[i]), 255-green(currFrame[i]), 255-blue(currFrame[i]));
        } else if (effect == 3) {
          pixels[i] = color((diffPixelsColor[0] + (255-red(currFrame[i])))/2, (diffPixelsColor[1] + (255-green(currFrame[i])))/2, (diffPixelsColor[2] + (255-blue(currFrame[i])))/2);
        }
      }
    } else {
      screenPixels[i] = color(0, 0, 0);
    }

    if (isSafeColor) {
      safeColorPixelsCounter++;
      pixels[i] = color(0, 255, 0);
      screenPixels[i] = color(0, 0, 0);
    }
  }



  updatePixels();

  int biggestBlobArea = 0;
  target.computeBlobs(screenPixels);
  for (int i = 0; i < target.getBlobNb()-1; i++) {
    blob = target.getBlob(i);
    int blobWidth = int(blob.w*camWidth);
    int blobHeight = int(blob.h*camHeight);
    if (blobWidth*blobHeight >= biggestBlobArea) {
      biggestBlob = target.getBlob(i);
      biggestBlobArea = int(biggestBlob.w*camWidth)*int(biggestBlob.h*camHeight);
    }
  }
  possibleX = 0;
  possibleY = 0;

  if (biggestBlobArea >= minBlobArea) {
    possibleX = int(biggestBlob.x * camWidth);
    possibleY = int(biggestBlob.y * camHeight);
  }


  if ((biggestBlobArea >= minBlobArea)) {
    fire = 1;
    if (showTargetBox) {
      stroke(255, 50, 50);
      strokeWeight(3);
      fill(255, 50, 50, 150);
      rect(int(biggestBlob.xMin*camWidth), int(biggestBlob.yMin*camHeight), int((biggestBlob.xMax-biggestBlob.xMin)*camWidth), int((biggestBlob.yMax-biggestBlob.yMin)*camHeight));
    }

    anticipation();

    if (activeSmoothing) {
      xdiff = possibleX - oldX; // smoothing
      ydiff = possibleY - oldY; // smoothing
      possibleX = int(oldX + xdiff*(1.0-smoothingFactor)); // smoothing
      possibleY = int(oldY + ydiff*(1.0-smoothingFactor)); // smoothing
    }

    displayX = possibleX;
    displayY = possibleY;
    if (displayX < 0)
      displayX = 0;
    if (displayX > camWidth)
      displayX = camWidth;
    if (displayY < 0)
      displayY = 0;
    if (displayY > camHeight)
      displayY = 0;  
    targetX = int((possibleX/xRatio)+xMin);         
    targetY = int(((camHeight-possibleY)/yRatio)+yMin);
    oldX = possibleX; // smoothing
    oldY = possibleY; // smoothing
  } else {
    fire = 0;
  }

  boolean clearOfZones = true;
  for (int col = 0; col <= restrictedZone; col++) {
    if (possibleX > fireRestrictedZones[col][0] && possibleX < fireRestrictedZones[col][1] && possibleY > fireRestrictedZones[col][2] && possibleY < fireRestrictedZones[col][3]) {
      clearOfZones = false;
      fire = 0;
    }
  }


  if (safeColorPixelsCounter > safeColorMinSize && safeColor) {
    noStroke();
    fill(0, 255, 0, 150);
    rect(0, 0, width, height);
    fire = 0;
    targetX = int((xMin+xMax)/2.0);
    targetY = int(yMin);
    displayX = camWidth/2;
    displayY = camHeight;
  }

  if (use_surf) {
    captureImage.setRGB(0, 0, camWidth, camHeight, currFrame, 0, camWidth);
    mSurfCap = new Surf(captureImage);
    mBMatchingPoints = mSurfCap.getMatchingPoints(mSurfRef, true);
    System.out.println(mBMatchingPoints.size());

    if (mBMatchingPoints.size()>=surf_sensivity) {
      noStroke();
      fill(0, 255, 0, 150);
      rect(0, 0, width, height);
      fire = 0;
      targetX = int((xMin+xMax)/2.0);
      targetY = int(yMin);
      displayX = camWidth/2;
      displayY = camHeight;
    }
  }
}

void updateCamera() {
  if (camInput.available()) {
    camInput.read();
  }
  image(camInput, 0, 0, width, height);
  camInput.loadPixels();
}
void manualMode() {
  updateCamera();
  rawBackground = camInput.pixels;
  rawImage = camInput.pixels;

  //  cursor(1);
  //camInput.update();
  //rawBackground = camInput.retinaImage();
  //rawImage = camInput.image();
  if (mirrorCam) {
    for (int i = 0; i < camWidth*camHeight; i++) {
      int y = floor(i/camWidth);
      int x = i - (y*camWidth);
      x = camWidth-x;
      currFrame[i] = rawImage[(y*camWidth) + x-1];
      Background[i] = rawBackground[(y*camWidth) + x-1];
    }
  } else {
    currFrame = rawImage;
    Background = rawBackground;
  }

  loadPixels();                                 //draw camera view to screen
  for (int i = 0; i < camWidth*camHeight; i++) {  //
    pixels[i] = currFrame[i];                   //
  }                                             //
  updatePixels();                               //

  if (inputDeviceIsSetup) {
    checkInputDevice();
  }
  if (useInputDevice) {
    updateInputDevice();                        // determine control values using the input device (see declaration in Input_Device tab)
    if (useArrowKeys) {   // use the arrow keys to aim one pixel at a time
      // use arrow keys to aim - see keyReleased() below
      if (keyPressed) {
        if (keyCode == 37) {                       // left arrow
          xPosition -= 1;
        }
        if (keyCode == 38) {                       // up arrow
          yPosition -= 1;
        }
        if (keyCode == 39) {                       // right arrow
          xPosition += 1;
        }
        if (keyCode == 40) {                       // down arrow
          yPosition += 1;
        }
        fire = 0;
      }
    }
  } else {  
    if (useArrowKeys) {   // use the arrow keys to aim one pixel at a time
      // use arrow keys to aim - see keyReleased() below
      if (keyPressed) {
        if (keyCode == 37) {                       // left arrow
          displayX -= 1;
        }
        if (keyCode == 38) {                       // up arrow
          displayY -= 1;
        }
        if (keyCode == 39) {                       // right arrow
          displayX += 1;
        }
        if (keyCode == 40) {                       // down arrow
          displayY += 1;
        }
        fire = 0;
      }
    } else {    
      displayX = mouseX;
      displayY = mouseY;
      if (mousePressed) {
        fire = 1;
      } else {
        fire = 0;
      }
    }
    targetX = constrain(int((displayX/xRatio)+xMin), 0, 180);                 // calculate position to go to based on mouse position

    targetY = constrain(int(((camHeight-displayY)/yRatio)+yMin), 0, 180);     //
  }
}


void mousePressed() {
  if (keyPressed && key == 'r') {
    print("constraints:" + mouseX + ", " + mouseY);
    fireRestrictedZones[restrictedZone][0] = mouseX;
    fireRestrictedZones[restrictedZone][2] = mouseY;
  }
  if (selectingColor) {
    trackColorRed = int(red(currFrame[(mouseY*width)+mouseX]));
    trackColorBlue = int(blue(currFrame[(mouseY*width)+mouseX]));
    trackColorGreen = int(green(currFrame[(mouseY*width)+mouseX]));
    selectingColor = false;
  }

  if (selectingSafeColor) {
    safeColorRed = int(red(currFrame[(mouseY*width)+mouseX]));
    safeColorBlue = int(blue(currFrame[(mouseY*width)+mouseX]));
    safeColorGreen = int(green(currFrame[(mouseY*width)+mouseX]));
    selectingSafeColor = false;
  }
}

void mouseReleased() {
  if (keyPressed && key == 'r') {
    println(" ... " + mouseX + ", " + mouseY);
    fireRestrictedZones[restrictedZone][1] = mouseX;
    fireRestrictedZones[restrictedZone][3] = mouseY;
    if (fireRestrictedZones[restrictedZone][1]>fireRestrictedZones[restrictedZone][0] && fireRestrictedZones[restrictedZone][1]>fireRestrictedZones[restrictedZone][2]) {
      restrictedZone++;
    }
  }
}

void keyReleased() {
  if ( key == 'p') {
    randomIdleSound();
  }

  if (key == ' ') {
    controlMode = !controlMode;
  }

  if (key == 'b') {
    //camInput.adapt();
    println("I dont know what this means");
    playSound(15);
  }
  if (key == 'a') {
    xMin = float(targetX);
    xRatio = (camWidth / (xMax - xMin));                         // used to allign sights with crosshairs on PC
  }
  if (key == 'd') {
    xMax = float(targetX);
    xRatio = (camWidth / (xMax - xMin));                         // used to allign sights with crosshairs on PC
  }
  if (key == 's') {
    yMin = float(targetY);
    yRatio = (camHeight/ (yMax - yMin));                         //
  }
  if (key == 'w') {
    yMax = float(targetY);
    yRatio = (camHeight/ (yMax - yMin));                         //
  }
  if (key == CODED && keyCode == 16) {      // shift key was pressed, toggle aim with arrow keys
    useArrowKeys = !useArrowKeys;
  }
}

void listDevices() {
  String[] devices = GLCapture.list();
  println("Device:");
  //printArray(devices);
  if (0 < devices.length) {
    String[] configs = GLCapture.configs(devices[0]);
    println("Configs:");
    printArray(configs);
    println("------------------------------------------------");
  }
}

public void viewCameraSettings() {
  //camInput.settings();
  String[] devices = GLCapture.list();
  println("Device:");
  //printArray(devices);
  if (0 < devices.length) {
    String[] configs = GLCapture.configs(devices[0]);
    println("Configs:");
    printArray(configs);
    println("------------------------------------------------");
  }
  playSound(21);
}

public void openWebsite() {
  link("http://psg.rudolphlabs.com/");
  playSound(15);
}

public void setBackground() {
  //camInput.adapt();
  playSound(11);
}

public void playRandomSound() {
  randomIdleSound();
}

public void selectColor() {
  selectingColor = true;
}

public void selectSafeColor() {
  selectingSafeColor = true;
}

public void radioEffect(int ID) {
  effect = ID + 1;
}


public void stop() {
  if (soundEffects) {
    playSampler(s1);
    //s1.trigger();
    //s1.play();
    delay(2500);
    //s1.close();
    //s2.close();
    //s3.close();
    //s4.close();
    //s5.close();
    //s7.close();
    //s6.close();
    //s8.close();
    //s9.close();
    //s10.close();
    //s11.close();
    //s12.close();
    //s13.close();
    //s14.close();
    //s15.close();
    //s16.close();
    //s17.close();
    //s18.close();
    //s19.close();
    //s20.close();
    //s21.close();
    minim.stop();
  }
  if (!runWithoutArduino) {
    arduinoPort.write("z0000000");
    delay(500);
    arduinoPort.stop();
  }
  //camInput.stop();
  super.stop();
}