/**
 * @author ubuntu - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 2, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapoport.utilities.psgcode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.gamecontrolplus.ControlIO;

import com.nrapoport.utilities.psgcode.config.IRuntimeSettingsAware;
import com.nrapoport.utilities.psgcode.config.ISettingsAware;
import com.nrapoport.utilities.psgcode.config.RuntimeSettings;
import com.nrapoport.utilities.psgcode.config.Settings;
import com.nrapoport.utilities.psgcode.enums.ControlMode;
import com.nrapoport.utilities.psgcode.enums.Controls;
import com.nrapoport.utilities.psgcode.enums.Effects;
import com.nrapoport.utilities.psgcode.enums.RunType;

import blobDetection.Blob;
import blobDetection.BlobDetection;
// import JMyron.JMyron;
import gohai.glvideo.GLCapture;
import gohai.glvideo.GLVideo;
import processing.core.PApplet;
import processing.serial.Serial;

/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>Main PSG code class. it will initialize and loop (in draw) to perform the PSG activities</DD>
 * <DT>Date:</DT>
 * <DD>Sep 2, 2017</DD>
 * </DL>
 *
 * @author ubuntu - Nick Rapoport
 *
 */
public class PSGProcessingCode extends PApplet implements ISettingsAware, IRuntimeSettingsAware {
    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>DEFAULT_SETTINGS (String) =</DD>
     * </DL>
     */
    public static final String DEFAULT_SETTINGS = "data/settings.json";

    //@SuppressWarnings("unused")
    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PSGProcessingCode.class);

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Standard external launcher for the project</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @param args
     */
    public static void main(final String[] args) {
        final String[] nameArray = new String[] { PSGProcessingCode.class.getName() };
        if (args != null) {
            PApplet.main(PApplet.concat(nameArray, args));
        } else {
            PApplet.main(nameArray);
        }
    }

    private Settings settings;

    private RuntimeSettings runtimeSettings;

    private Sounds sounds;

    private ControlPanel controlPanel;

    private DeviceController deviceController;

    private Anticipation anticipation;

    //JMyron camInput;
    private GLCapture camInput;

    private int[] deltaBackground;

    private int[] rawImage;

    private int[] rawBackground;

    private int[] lastBackground;

    private int[] currFrame;

    private int[] screenPixels;

    private BlobDetection target;

    private Blob blob;

    private Blob biggestBlob;

    private int[][] fireRestrictedZones = new int[30][4];

    private int restrictedZone = 1;

    private int idleBeginTime = 0;

    private float oldX;// = getSettings().getCamWidth() / 2; // smoothing (contributed by Adam S.)

    private float oldY;// = getSettings().getCamHeight() / 2; // smoothing

    private int lastTime = millis();

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>PSGProcessingCode Constructor</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     */
    public PSGProcessingCode() {
        super();
        log.debug("Working on sketch path: {}", sketchPath());
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>This method is the autonomous mode for the robot. it will decide when and who to shoot</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @return
     */
    protected boolean autonomousMode() {

        if (getRuntimeSettings().isInputDeviceIsSetup()) {
            getDeviceController().checkInputDevice();
        }
        final int camWidth = getSettings().getCamWidth();
        final int camHeight = getSettings().getCamHeight();
        final float xRatio = getSettings().getxRatio();
        final float yRatio = getSettings().getyRatio();
        final float xMin = getSettings().getxMin();
        final float yMin = getSettings().getyMin();
        final float xMax = getSettings().getxMax();
        // final float yMax = getSettings().getyMax();

        if (getRuntimeSettings().isSelectingColor() || getRuntimeSettings().isSelectingSafeColor()) {
            cursor(CROSS);
        } else {
            cursor(ARROW);
        }
        if (!updateCamera(false)) {
            return false;
        }

        //camInput.update();
        //rawBackground = camInput.retinaImage();
        //rawImage = camInput.image();
        if (getSettings().isMirrorCam()) {
            for (int i = 0; i < camWidth * camHeight; i++) {
                final int y = floor(i / camWidth);
                int x = i - y * camWidth;
                x = camWidth - x;
                currFrame[i] = rawImage[y * camWidth + x - 1];
                deltaBackground[i] = lastBackground[y * camWidth + x - 1];
            }
        } else {
            currFrame = rawImage;
            deltaBackground = lastBackground;
        }

        loadPixels();
        int safeColorPixelsCounter = 0;

        for (int i = 0; i < camWidth * camHeight; i++) {
            if (getSettings().isShowCameraView()) {
                pixels[i] = currFrame[i];
            } else {
                pixels[i] = color(0, 0, 0);
            }

            //TODO move this into Settings as this manipulates everything from there
            final int tolerance = getSettings().getTolerance();
            final boolean trackingMotion = getSettings().isTrackingMotion();
            final int trackColorRed = getSettings().getTrackColorRed();
            final int trackColorGreen = getSettings().getTrackColorGreen();
            final int trackColorBlue = getSettings().getTrackColorBlue();
            final int trackColorTolerance = getSettings().getTrackColorTolerance();
            final boolean trackingColor = getSettings().isTrackingColor();
            final int safeColorRed = getSettings().getSafeColorRed();
            final int safeColorGreen = getSettings().getSafeColorGreen();
            final int safeColorBlue = getSettings().getSafeColorBlue();
            final int safeColorTolerance = getSettings().getSafeColorTolerance();
            final boolean safeColor = getSettings().isUseSafeColor();

            final boolean motion =
                abs(red(currFrame[i]) - red(deltaBackground[i])) + abs(green(currFrame[i]) - green(deltaBackground[i]))
                    + abs(blue(currFrame[i]) - blue(deltaBackground[i])) > 200 - tolerance && trackingMotion;
            final boolean isTrackedColor =
                abs(red(currFrame[i]) - trackColorRed) + abs(green(currFrame[i]) - trackColorGreen)
                    + abs(blue(currFrame[i]) - trackColorBlue) < trackColorTolerance && trackingColor;

            final boolean isSafeColor =
                abs(red(currFrame[i]) - safeColorRed) + abs(green(currFrame[i]) - safeColorGreen)
                    + abs(blue(currFrame[i]) - safeColorBlue) < safeColorTolerance && safeColor;

            final Effects effect = getSettings().getEffect();
            if (motion || isTrackedColor) {
                screenPixels[i] = color(255, 255, 255);
                if (getSettings().isShowDifferentPixels()) {
                    final int[] diffPixelsColor = getSettings().getDiffPixelsColor(); //get the DiffPixelsColor as an array
                    switch (effect) {
                        case Opaque:
                            pixels[i] = color(diffPixelsColor[0], diffPixelsColor[1], diffPixelsColor[2]);
                            break;
                        case Negative:
                            pixels[i] = color((diffPixelsColor[0] + red(currFrame[i])) / 2,
                                (diffPixelsColor[1] + green(currFrame[i])) / 2,
                                (diffPixelsColor[2] + blue(currFrame[i])) / 2);
                            break;
                        case Transparent:
                            pixels[i] =
                                color(255 - red(currFrame[i]), 255 - green(currFrame[i]), 255 - blue(currFrame[i]));
                            break;
                        case NegativeAndTransparent:
                            pixels[i] = color((diffPixelsColor[0] + (255 - red(currFrame[i]))) / 2,
                                (diffPixelsColor[1] + (255 - green(currFrame[i]))) / 2,
                                (diffPixelsColor[2] + (255 - blue(currFrame[i]))) / 2);
                            break;
                    }
                    getSettings().setDiffPixelsColor(diffPixelsColor); //its not backed by an array under the covers
                    //        if (effect == 0) {
                    //          pixels[i] = color(diffPixelsColor[0], diffPixelsColor[1], diffPixelsColor[2]);
                    //        }
                    //        else if (effect == 1) {
                    //          pixels[i] = color((diffPixelsColor[0] + red(currFrame[i]))/2, (diffPixelsColor[1] + green(currFrame[i]))/2, (diffPixelsColor[2] + blue(currFrame[i]))/2);
                    //        }
                    //        else if (effect == 2) {
                    //          pixels[i] = color(255-red(currFrame[i]), 255-green(currFrame[i]), 255-blue(currFrame[i]));
                    //        }
                    //        else if (effect == 3) {
                    //          pixels[i] = color((diffPixelsColor[0] + (255-red(currFrame[i])))/2, (diffPixelsColor[1] + (255-green(currFrame[i])))/2, (diffPixelsColor[2] + (255-blue(currFrame[i])))/2);
                    //        }
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
        for (int i = 0; i < target.getBlobNb() - 1; i++) {
            blob = target.getBlob(i);
            final int blobWidth = Math.round(blob.w * camWidth);
            final int blobHeight = Math.round(blob.h * camHeight);
            if (blobWidth * blobHeight >= biggestBlobArea) {
                biggestBlob = target.getBlob(i);
                biggestBlobArea = Math.round(biggestBlob.w * camWidth * biggestBlob.h * camHeight);
            }
        }
        final int minBlobArea = getSettings().getMinBlobArea();
        float possibleX = 0;
        float possibleY = 0;

        if (biggestBlobArea >= minBlobArea) {
            possibleX = Math.round(biggestBlob.x * camWidth);
            possibleY = Math.round(biggestBlob.y * camHeight);
        }

        if (biggestBlobArea >= minBlobArea) {
            getRuntimeSettings().setFiring(true);
            if (getSettings().isShowTargetBox()) {
                stroke(255, 50, 50);
                strokeWeight(3);
                fill(255, 50, 50, 150);
                rect(biggestBlob.xMin * camWidth, biggestBlob.yMin * camHeight,
                    Math.round(biggestBlob.xMax - biggestBlob.xMin) * camWidth,
                    Math.round(biggestBlob.yMax - biggestBlob.yMin) * camHeight);
            }
            getRuntimeSettings().setPossibleX(possibleX);
            getRuntimeSettings().setPossibleY(possibleY);

            getAnticipation().anticipation();

            final float smoothingFactor = getSettings().getSmoothingFactor();
            possibleX = getRuntimeSettings().getPossibleX();
            possibleX = getRuntimeSettings().getPossibleY();
            if (getSettings().isActiveSmoothing()) {
                final float xdiff = possibleX - oldX; // smoothing
                final float ydiff = possibleY - oldY; // smoothing
                possibleX = oldX + xdiff * (1.0f - smoothingFactor); // smoothing
                possibleY = oldY + ydiff * (1.0f - smoothingFactor); // smoothing
            }
            int displayX = Math.round(possibleX);
            int displayY = Math.round(possibleY);
            getRuntimeSettings().setDisplayX(displayX);
            getRuntimeSettings().setDisplayY(displayY);
            getRuntimeSettings().setPossibleY(possibleY);
            getRuntimeSettings().setPossibleX(possibleX);
            getRuntimeSettings().setPossibleY(possibleY);

            if (displayX < 0) {
                displayX = 0;
            }
            if (displayX > camWidth) {
                displayX = camWidth;
            }
            if (displayY < 0) {
                displayY = 0;
            }
            if (displayY > camHeight) {
                displayY = camHeight; //TODO  verify that this is ok ... used to be zero
            }
            getRuntimeSettings().setDisplayX(displayX);
            getRuntimeSettings().setDisplayY(displayY);

            getRuntimeSettings().setTargetX(Math.round(possibleX / xRatio + xMin));
            getRuntimeSettings().setTargetY(Math.round((camHeight - possibleY) / yRatio + yMin));
            oldX = possibleX; // smoothing
            oldY = possibleY; // smoothing
        } else {
            getRuntimeSettings().setFiring(false);
        }

        //boolean clearOfZones = true;
        for (int col = 0; col <= restrictedZone; col++) {
            if (possibleX > fireRestrictedZones[col][0] && possibleX < fireRestrictedZones[col][1]
                && possibleY > fireRestrictedZones[col][2] && possibleY < fireRestrictedZones[col][3]) {
                //clearOfZones = false;
                getRuntimeSettings().setFiring(false);
            }
        }

        if (safeColorPixelsCounter > getSettings().getSafeColorMinSize() && getSettings().isUseSafeColor()) {
            noStroke();
            fill(0, 255, 0, 150);
            rect(0, 0, width, height);
            getRuntimeSettings().setFiring(false);
            getRuntimeSettings().setTargetX(Math.round((xMin + xMax) / 2.0f));
            getRuntimeSettings().setTargetY(Math.round(yMin));
            getRuntimeSettings().setDisplayX(camWidth / 2);
            getRuntimeSettings().setDisplayY(camHeight);
        }
        //lastBackground = rawBackground;
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public void draw() {

        if (getRuntimeSettings().isConnecting()) {
            final int currWidth = getSettings().getCamWidth();
            final int currHeight = getSettings().getCamHeight();
            background(0);
            final String msg = "Connecting to Arduino...";
            final int msgSize = msg.length();
            final int textSize = Math.round(currWidth / msgSize);
            textSize(textSize);
            fill(255);
            text(msg, (currWidth - msgSize) / 2, currHeight / 2 - textSize / 2);
            return;
        }

        if (getSettings().isPrintFrameRate()) {
            println(frameRate);
        }

        boolean newImageAvalable = false;
        switch (getSettings().getControlMode()) {
            case Autonomous:
                newImageAvalable = autonomousMode();
                break;

            case Manual:
                newImageAvalable = manualMode();
                break;
        }
        if (!newImageAvalable) {
            return;
        }
        //        if ( == ControlMode.Autonomous) { // autonomous mode
        //            if (!autonomousMode()) //
        //            {
        //                return;
        //            }
        //        } else { // manual mode
        //            if (!manualMode()) {
        //                return;
        //            } //
        //        }

        if (getRuntimeSettings().isFiring()) {
            idleBeginTime = millis();
            getRuntimeSettings().setScan(false);
        } else {
            final boolean timeToScan = millis() > idleBeginTime + getSettings().getIdleTime(); // it's Time!!
            final boolean scanAllowed =
                getSettings().getControlMode() == ControlMode.Autonomous && getSettings().isScanWhenIdle(); // Can I?

            getRuntimeSettings().setScan(timeToScan && scanAllowed);
            //            if ( timeToScan&& ) {
            //                scan = true;
            //            } else {
            //                scan = false;
            //            }
        }

        if (!getSettings().isSafety()) {
            getRuntimeSettings().setFiring(false);
        }

        //        if (!safety) {
        //            fire = 0;
        //        }

        //strTargetx = "000" + str(targetX); // make into 3-digit numbers
        //strTargetx = strTargetx.substring(strTargetx.length() - 3);
        //strTargety = "000" + str(targetY);
        //strTargety = strTargety.substring(strTargety.length() - 3);
        //        fireSelector = str(0);
        //        if (firingMode) {
        //            fireSelector = str(1);
        //        } else {
        //            fireSelector = str(3);
        //        }
        //        if (scan) {
        //            scanSelector = str(1);
        //        } else {
        //            scanSelector = str(0);
        //        }
        //println('a' + strTargetx + strTargety  + str(fire) + fireSelector + scanSelector);
        if (!getSettings().isRunWithoutArduino() && !getRuntimeSettings().isConnecting()) {
            getRuntimeSettings().getArduinoPort()
                .write('a' + getRuntimeSettings().getStrTargetX() + getRuntimeSettings().getStrTargetY()
                    + getRuntimeSettings().getFiringInt() + getSettings().getFiringMode().getId()
                    + getRuntimeSettings().getScanInt()); // send to arduino
            //+ getRuntimeSettings().getStrTargetX() + getRuntimeSettings().getStrTargetY()
            //+ getRuntimeSettings().getFiringInt() + getSettings().getFiringMode().getId() + getRuntimeSettings().getScanInt();
        }

        if (keyPressed && key == 't' || getSettings().isShowRestrictedZones()) {
            for (int col = 0; col <= restrictedZone; col++) {
                noStroke();
                fill(0, 255, 0, 100);
                rect(fireRestrictedZones[col][0], fireRestrictedZones[col][2],
                    fireRestrictedZones[col][1] - fireRestrictedZones[col][0],
                    fireRestrictedZones[col][3] - fireRestrictedZones[col][2]);
            }
        }
        if (getRuntimeSettings().isInputDeviceIsSetup()) {
            stroke(190, 0, 190);
            strokeWeight(2);
            fill(red(currFrame[mouseY * width + mouseX]), green(currFrame[mouseY * width + mouseX]),
                blue(currFrame[mouseY * width + mouseX]));
            rect(mouseX + 2, mouseY + 2, 30, 30);
        }

        if (getRuntimeSettings().isSelectingSafeColor()) {
            stroke(0, 255, 0);
            strokeWeight(2);
            fill(red(currFrame[mouseY * width + mouseX]), green(currFrame[mouseY * width + mouseX]),
                blue(currFrame[mouseY * width + mouseX]));
            rect(mouseX + 2, mouseY + 2, 30, 30);
        }

        //        soundTimer++;
        //        if (soundTimer == soundInterval) {
        //            randomIdleSound();
        //            soundTimer = 0;
        //        }

        if (getSounds().isItSoundTime()) {
            getSounds().randomIdleSound();
        }

        //        for (int i = 9; i > 0; i--) {
        //            prevFire[i] = prevFire[i - 1];
        //        }
        //        prevFire[0] = fire;

        //        final int sumNewFire = prevFire[0] + prevFire[1] + prevFire[2] + prevFire[3] + prevFire[4];
        //        final int sumPrevFire = prevFire[5] + prevFire[6] + prevFire[7] + prevFire[8] + prevFire[9];
        //
        //        if (sumNewFire == 0 && sumPrevFire == 5) { // target departed screen

        if (getRuntimeSettings().hasTargetDepartedScreen()) {
            //final int[] soundList = { 1, 2, 9, 12, 13, 20 };
            //final String[] soundList = { "business", "who", "nohardfeel", "donthate", "dontblame", "stillthere" };
            final List<String> soundList = getSettings().getDepartedSoundList();
            final int s = getRuntimeSettings().getRandom().nextInt(soundList.size());
            log.debug("playing sound, {} ", s);
            getSounds().playSound(soundList.get(s));
            log.debug("done playing sound, {} ", soundList.get(s));
            //            if (s == 0) {
            //                playSound(1);
            //            }
            //            if (s == 1) {
            //                playSound(5);
            //            }
            //            if (s == 2) {
            //                playSound(9);
            //            }
            //            if (s == 3) {
            //                playSound(12);
            //            }
            //            if (s == 4) {
            //                playSound(13);
            //            }
            //            if (s == 5) {
            //                playSound(20);
            //            }
        }
        strokeWeight(getRuntimeSettings().isFiring() ? 3 : 1);
        //        if (fire == 1) {
        //            strokeWeight(3);
        //        }
        //        if (fire == 0) {
        //            strokeWeight(1);
        //        }
        stroke(255, 0, 0); //draw cross-hairs
        noFill(); //
        line(getRuntimeSettings().getDisplayX(), 0, getRuntimeSettings().getDisplayX(), getSettings().getCamHeight()); //
        line(0, getRuntimeSettings().getDisplayY(), getSettings().getCamWidth(), getRuntimeSettings().getDisplayY()); //
        ellipse(getRuntimeSettings().getDisplayX(), getRuntimeSettings().getDisplayY(), 20, 20); // 20,20
        ellipse(getRuntimeSettings().getDisplayX(), getRuntimeSettings().getDisplayY(), 28, 28); // 28,22
        ellipse(getRuntimeSettings().getDisplayX(), getRuntimeSettings().getDisplayY(), 36, 36); // 36,24

        if (getSettings().getRunType() == RunType.Full) {
            getControlPanel().updateControlPanels();
        }
        getRuntimeSettings().updatePrevTargetXY();
        //prevTargetX = targetX;
        //prevTargetY = targetY;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the anticipation property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of anticipation field
     */
    public Anticipation getAnticipation() {
        if (anticipation == null) {
            anticipation = new Anticipation(this);
        }
        return anticipation;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the camInput property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @return the value of camInput field
     */
    public GLCapture getCamInput() {
        return camInput;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the controlPanel property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 3, 2017</DD>
     * </DL>
     *
     * @return the value of controlPanel field
     */
    public ControlPanel getControlPanel() {
        if (controlPanel == null) {
            controlPanel = new ControlPanel(this);
        }
        return controlPanel;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the deviceController property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 3, 2017</DD>
     * </DL>
     *
     * @return the value of deviceController field
     */
    public DeviceController getDeviceController() {
        if (deviceController == null) {
            deviceController = new DeviceController(this);
        }
        return deviceController;
    }

    /** {@inheritDoc} */
    @Override
    public RuntimeSettings getRuntimeSettings() {
        if (runtimeSettings == null) {
            runtimeSettings = new RuntimeSettings(this);
        }
        return runtimeSettings;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the settings property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @return the value of settings field
     */
    @Override
    public Settings getSettings() {
        if (settings == null) {
            settings = new Settings(this, PSGProcessingCode.DEFAULT_SETTINGS);
            //            try {
            //                settings.loadSettings();
            //            } catch (final IOException ex) {
            //                log.error("caught IOException :", ex); //$NON-NLS-1$
            //                final RuntimeException re = new RuntimeException(
            //                    "Failed to load the settings for the application! Reason : " + ex.getMessage(), ex);
            //                re.fillInStackTrace();
            //                throw re;
            //            }
        }
        return settings;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the sounds property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @return the value of sounds field
     */
    public Sounds getSounds() {
        if (sounds == null) {
            sounds = new Sounds(this);
        }
        return sounds;
    }

    /** {@inheritDoc} */
    @Override
    public void keyReleased() {
        final int targetX = getRuntimeSettings().getTargetX();
        final int targetY = getRuntimeSettings().getTargetY();

        switch (key) {
            case ' ':
                //controlMode = !controlMode;
                getSettings().flipControlMode();
                break;

            case 'a':
                getSettings().setxMin(targetX);
                //xRatio = camWidth / (xMax - xMin); // used to align sights with cross-hairs on PC // dynamically calculated getSettings().getxRatio()
                break;
            case 'b':
                retinaImage(true);
                getSounds().playSound("hello"); // 15
                break;

            case 'd':
                //xMax = targetX;
                getSettings().setxMax(targetX);
                //xRatio = camWidth / (xMax - xMin); // used to align sights with cross-hairs on PC // dynamically calculated getSettings().getxRatio()
                break;
            case 'h':
                final String msgFmt = "%1$5s : = %2$s \n"; //$NON-NLS-1$
                final String msg = String.format(msgFmt, "SPACE", "Toggle Manual and Autonomous modes") //$NON-NLS-1$ //$NON-NLS-2$
                    + String.format(msgFmt, "a", "set min X") //$NON-NLS-1$ //$NON-NLS-2$
                    + String.format(msgFmt, "b", "Reset retina image to curre3nt background") //$NON-NLS-1$ //$NON-NLS-2$
                    + String.format(msgFmt, "d", "set max X") //$NON-NLS-1$ //$NON-NLS-2$
                    + String.format(msgFmt, "h", "print key help") //$NON-NLS-1$ //$NON-NLS-2$
                    + String.format(msgFmt, "H", "print current state") //$NON-NLS-1$ //$NON-NLS-2$
                    + String.format(msgFmt, "p", "Play random sound") //$NON-NLS-1$ //$NON-NLS-2$
                    + String.format(msgFmt, "r", "reset calibration to defaults") //$NON-NLS-1$ //$NON-NLS-2$
                    + String.format(msgFmt, "s", "set min Y") //$NON-NLS-1$ //$NON-NLS-2$
                    + String.format(msgFmt, "w", "set max Y") //$NON-NLS-1$ //$NON-NLS-2$
                    + String.format(msgFmt, "SHIFT", "Toggle the use of arrow keys for aiming"); //$NON-NLS-1$ //$NON-NLS-2$
                log.info("\nAvalable Keys : \n{}", msg);
                getSettings().setxMax(targetX);
                //xRatio = camWidth / (xMax - xMin); // used to allign sights with crosshairs on PC // dynamically calculated getSettings().getxRatio()
                break;
            case 'H':
                printCurrentState();
                break;

            case 'p':
                getSounds().randomIdleSound();
                break;

            case 'r':
                getSettings().setxMin(0.0f);
                getSettings().setxMax(180.0f);
                getSettings().setyMin(0.0f);
                getSettings().setyMax(180);
                break;

            case 's':
                //yMin = targetY;
                getSettings().setyMin(targetY);

                //yRatio = camHeight / (yMax - yMin); // dynamically calculated getSettings().getyRatio()
                break;

            case 'w':
                //yMax = targetY;
                getSettings().setyMax(targetY);
                //yRatio = camHeight / (yMax - yMin); // dynamically calculated getSettings().getyRatio()
                break;
            case CODED:
                if (keyCode == SHIFT) { // shift key was pressed, toggle aim with arrow keys
                    getSettings().toggleUseArrowKeys();
                }
                break;
            default:
                break;
        }

        //        if (key == CODED && keyCode == SHIFT) { // shift key was pressed, toggle aim with arrow keys
        //            getSettings().toggleUseArrowKeys();
        //        }
        //        if (key == ' ') {
        //        }
        //        if (key == 'a') {
        //            //xMin = targetX;
        //            getSettings().setxMin(targetX);
        //        }
        //        if (key == 'b') {
        //            //camInput.adapt();
        //            getSounds().playSound("hello"); // 15
        //        }
        //        if (key == 'd') {
        //            //xMax = targetX;
        //            getSettings().setxMax(targetX);
        //            //xRatio = camWidth / (xMax - xMin); // used to allign sights with crosshairs on PC // dynamically calculated getSettings().getxRatio()
        //        }
        //        if (key == 'p') {
        //            getSounds().randomIdleSound();
        //        }
        //        if (key == 'r') {
        //            getSettings().setxMin(0.0f);
        //            getSettings().setxMax(180.0f);
        //            getSettings().setyMin(0.0f);
        //            getSettings().setyMax(180);
        //        }
        //        if (key == 's') {
        //            //yMin = targetY;
        //            getSettings().setyMin(targetY);
        //
        //            //yRatio = camHeight / (yMax - yMin); // dynamically calculated getSettings().getyRatio()
        //        }
        //        if (key == 'w') {
        //            //yMax = targetY;
        //            getSettings().setyMax(targetY);
        //            //yRatio = camHeight / (yMax - yMin); // dynamically calculated getSettings().getyRatio()
        //        }

    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Use your controller or mouse to aim and shoot ...</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @return
     */
    protected boolean manualMode() {
        //  cursor(1);
        if (!updateCamera(false)) {
            return false;
        }

        //camInput.update();
        //rawBackground = camInput.retinaImage();
        //rawImage = camInput.image();
        final int camWidth = getSettings().getCamWidth();
        final int camHeight = getSettings().getCamHeight();
        if (getSettings().isMirrorCam()) {
            for (int i = 0; i < camWidth * camHeight; i++) {
                final int y = floor(i / camWidth);
                int x = i - y * camWidth;
                x = camWidth - x;
                currFrame[i] = rawImage[y * camWidth + x - 1];
                deltaBackground[i] = lastBackground[y * camWidth + x - 1];
            }
        } else {
            currFrame = rawImage;
            deltaBackground = lastBackground;
        }

        loadPixels(); //draw camera view to screen
        for (int i = 0; i < camWidth * camHeight; i++) { //
            pixels[i] = currFrame[i]; //
        } //
        updatePixels(); //

        if (getRuntimeSettings().isInputDeviceIsSetup()) {
            getDeviceController().checkInputDevice();
        }
        if (getSettings().getControls() == Controls.Device) {
            getDeviceController().updateInputDevice(); // determine control values using the input device (see declaration in Input_Device tab (DeviceController class))
            if (getSettings().isUseArrowKeys()) { // use the arrow keys to aim one pixel at a time
                // use arrow keys to aim - see keyReleased() below
                if (keyPressed) {
                    switch (keyCode) {
                        case LEFT:
                            getRuntimeSettings().incrementxPosition(-1);
                            break;
                        case UP:
                            getRuntimeSettings().incrementyPosition(-1);
                            break;
                        case RIGHT:
                            getRuntimeSettings().incrementxPosition(1);
                            break;
                        case DOWN:
                            getRuntimeSettings().incrementyPosition(1);
                            break;
                    }
                    //                    if (keyCode == LEFT) { // left arrow 37
                    //                        getRuntimeSettings().incrementxPosition(-1);
                    //                    }
                    //                    if (keyCode == UP) { // up arrow 38
                    //                        getRuntimeSettings().incrementyPosition(-1);
                    //                    }
                    //                    if (keyCode == RIGHT) { // right arrow 39
                    //                        getRuntimeSettings().incrementxPosition(1);
                    //                    }
                    //                    if (keyCode == DOWN) { // down arrow 40
                    //                        getRuntimeSettings().incrementyPosition(1);
                    //                    }
                    getRuntimeSettings().setFiring(false);

                }
            }
        } else {
            if (getSettings().isUseArrowKeys()) { // use the arrow keys to aim one pixel at a time
                // use arrow keys to aim - see keyReleased() below
                if (keyPressed) {
                    switch (keyCode) {
                        case LEFT:
                            getRuntimeSettings().incrementDisplayX(-1);
                            break;
                        case UP:
                            getRuntimeSettings().incrementDisplayY(-1);
                            break;
                        case RIGHT:
                            getRuntimeSettings().incrementDisplayX(1);
                            break;
                        case DOWN:
                            getRuntimeSettings().incrementDisplayY(1);
                            break;
                    }//                    if (keyCode == 37) { // left arrow
                     //                        getRuntimeSettings().incrementDisplayX(-1);
                     //                    }
                     //                    if (keyCode == 38) { // up arrow
                     //                        getRuntimeSettings().incrementDisplayY(-1);
                     //                    }
                     //                    if (keyCode == 39) { // right arrow
                     //                        getRuntimeSettings().incrementDisplayX(1);
                     //                    }
                     //                    if (keyCode == 40) { // down arrow
                     //                        getRuntimeSettings().incrementDisplayY(1);
                     //
                     //                    }
                    getRuntimeSettings().setFiring(false);
                }
            } else {
                getRuntimeSettings().setDisplayX(mouseX);
                getRuntimeSettings().setDisplayY(mouseY);
                getRuntimeSettings().setFiring(mousePressed);

                //          if (mousePressed) {
                //            fire = 1;
                //          }
                //          else {
                //            fire = 0;
                //          }
            }
            final int displayX = getRuntimeSettings().getDisplayX();
            final int displayY = getRuntimeSettings().getDisplayY();
            final float xRatio = getSettings().getxRatio();
            final float yRatio = getSettings().getyRatio();
            final float xMin = getSettings().getxMin();
            final float yMin = getSettings().getyMin();

            getRuntimeSettings().setTargetX(Math.round(constrain(displayX / xRatio + xMin, 0, 180))); // calculate position to go to based on mouse position

            getRuntimeSettings().setTargetY(Math.round(constrain((camHeight - displayY) / yRatio + yMin, 0, 180))); //

        }
        //lastBackground = rawBackground;
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public void mousePressed() {

        if (keyPressed && key == 'r') {
            print("constraints:" + mouseX + ", " + mouseY);
            fireRestrictedZones[restrictedZone][0] = mouseX;
            fireRestrictedZones[restrictedZone][2] = mouseY;
        }
        if (getRuntimeSettings().isSelectingColor()) {
            getSettings().setTrackColorRed(Math.round(red(currFrame[mouseY * width + mouseX])));
            getSettings().setTrackColorBlue(Math.round(blue(currFrame[mouseY * width + mouseX])));
            getSettings().setTrackColorGreen(Math.round(green(currFrame[mouseY * width + mouseX])));
            getRuntimeSettings().setSelectingColor(false);
        }

        if (getRuntimeSettings().isSelectingSafeColor()) {
            getSettings().setSafeColorRed(Math.round(red(currFrame[mouseY * width + mouseX])));
            getSettings().setSafeColorBlue(Math.round(blue(currFrame[mouseY * width + mouseX])));
            getSettings().setSafeColorGreen(Math.round(green(currFrame[mouseY * width + mouseX])));
            getRuntimeSettings().setSelectingSafeColor(false);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void mouseReleased() {
        if (keyPressed && key == 'r') {
            println(" ... " + mouseX + ", " + mouseY);
            fireRestrictedZones[restrictedZone][1] = mouseX;
            fireRestrictedZones[restrictedZone][3] = mouseY;
            if (fireRestrictedZones[restrictedZone][1] > fireRestrictedZones[restrictedZone][0]
                && fireRestrictedZones[restrictedZone][1] > fireRestrictedZones[restrictedZone][2]) {
                restrictedZone++;
            }
        }
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>print current state</DD>
     * <DT>Date:</DT>
     * <DD>Sep 18, 2017</DD>
     * </DL>
     */
    public void printCurrentState() {
        final String msgFmt = " %1$-25s : = %2$s\n";
        final String status = String.format(msgFmt, "Serial Out",
            'a' + getRuntimeSettings().getStrTargetX() + getRuntimeSettings().getStrTargetY()
                + getRuntimeSettings().getFiringInt() + getSettings().getFiringMode().getId()
                + getRuntimeSettings().getScanInt())
            + String.format(msgFmt, "Pan Servo Position", getRuntimeSettings().getStrTargetX())
            + String.format(msgFmt, "Tilt Servo Position", getRuntimeSettings().getStrTargetY())
            + String.format(msgFmt, "Track Color Tolerance", getSettings().getTolerance())
            + String.format(msgFmt, "Safe Color Tolerance", getSettings().getSafeColorTolerance())
            + String.format(msgFmt, "Min Area", getSettings().getSafeColorMinSize())
            + String.format(msgFmt, "Min Size", getSettings().getMinBlobArea())
            + String.format(msgFmt, "Memory", getSettings().getNbDot())
            + String.format(msgFmt, "Sensitivity", getSettings().getAntSens())
            + String.format(msgFmt, "X Degree of Anitcipation", getSettings().getPropX())
            + String.format(msgFmt, "Y Degree of Anitcipation", getSettings().getPropY())
            + String.format(msgFmt, "xMin", getSettings().getxMin())
            + String.format(msgFmt, "xMax", getSettings().getxMax())
            + String.format(msgFmt, "yMin", getSettings().getyMin())
            + String.format(msgFmt, "yMax", getSettings().getyMax())
            + String.format(msgFmt, "Firing?", getRuntimeSettings().isFiring() ? "" : "Not " + "Firing")
            + String.format(msgFmt, "Firing Mode", getSettings().getFiringMode().altName())
            + String.format(msgFmt, "Controller on port",
                getSettings().isRunWithoutArduino() ? "No Controller" : getRuntimeSettings().getSerPortUsed())
            + String.format(msgFmt, "Controller State",
                getRuntimeSettings().isConnecting() ? "Connecting ..."
                    : (getRuntimeSettings().getSerPortUsed() == null || getRuntimeSettings().getSerPortUsed().isEmpty()
                        ? "Not c" : "C") + "onnected")
            + String.format(msgFmt, "Scan Mode", (getSettings().isScanWhenIdle() ? "S" : "Don't s") + "can when idle")
            + String.format(msgFmt, "Control Mode", getSettings().getControlMode().name())
            + String.format(msgFmt, "Controls", getSettings().getControls().name())
            + String.format(msgFmt, "Use Arrow Keys", getSettings().isUseArrowKeys());
        log.info("\nCurrent State: \n{}", status);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Capture an image every 'retinaPeriodMillis'. used for aiming ...</DD>
     * <DT>Date:</DT>
     * <DD>Sep 18, 2017</DD>
     * </DL>
     *
     * @param force
     */
    protected void retinaImage(final boolean force) {
        final int time = millis();
        if (time - lastTime > getSettings().getRetinaPeriodMillis() || force) {
            lastBackground = rawBackground;
            lastTime = time;
        }

    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>connect to Arduino (the sketch that's running on it send a 'T' periodically) so test all the Serial ports
     * found for this heartbeat.</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     */
    public void retryArduinoConnect() {
        log.debug("in retryArduinoConnect");
        if (getSettings().isRunWithoutArduino()) {
            getRuntimeSettings().setConnecting(false);
            log.debug("isRunWithoutArduino is {} exiting", getSettings().isRunWithoutArduino());
            return;
        }

        log.debug("in retryArduinoConnect : trying");
        getRuntimeSettings().setConnecting(true);
        //          try{
        //            arduinoPort.stop();
        //          }catch(Exception e) {
        //            delay(10);
        //          }

        // Find Serial Port that the arduino is on
        // The arduino is sending out a 'T' every 100 milliseconds. Contributed by Don K.
        //long millisStart;
        int i = 0;
        int len = Serial.list().length; //get number of ports available
        final List<String> ports = new ArrayList<>(len);
        log.info("Serial Port Count = {}", len);
        for (final String port : Serial.list()) {
            if (!port.startsWith("/dev/ttyS")) {
                log.info("port: {}", port);
                ports.add(port);
            }
        }
        len = ports.size();
        //println(Serial.list()); //print list of ports to screen

        //println("Serial Port Count = " + len); //print count of ports to screen
        if (len == 0) {
            getSettings().setRunWithoutArduino(true);
            log.info("no Arduino detected (Is it connected?) Will run without Arduino. Cheers");
        }
        String currentPort = "0";
        Serial arduinoPort = null;
        for (i = len - 1; i > -1; i--) { //going backwards in the expectation that the Arduino port will be last
            currentPort = ports.get(i);
            log.info("Testing port {}", currentPort);
            arduinoPort = new Serial(this, currentPort, 4800); // Open 1st port in list
            delay(2000);
            //            millisStart = millis();
            //            while (millis() - millisStart < 2000) {
            //                ; //wait for USB port reset (Guessed at 3 secs)
            //            }
            // can't use delay() call in setup() //ahhh but we are not in setup anymore
            arduinoPort.clear(); // empty buffer(incase of trash)
            arduinoPort.bufferUntil('T'); //buffer until there is a 'T'
            delay(100);
            //try {
            //        Thread.sleep(100);
            //} catch (InterruptedException ex) {
            //    // nothing
            //}
            //            millisStart = millis();
            //            while (millis() - millisStart < 100) {
            //                ; //collect some chars
            //            }
            if (arduinoPort.available() > 0) //if we have a character
            {
                final char c = arduinoPort.readChar(); //get the character
                if (c == 'T') //if we got a 'T'
                {
                    break; //leave for loop
                }
            } else {
                arduinoPort.stop(); //if no 'T', stop port
            }
            if (i == 0) {
                getSettings().setRunWithoutArduino(true);
                log.info(
                    "no Arduino detected, (I found some ports... did you load the sketch?) Will run without Arduino. Cheers");
            }
        }
        if (!getSettings().isRunWithoutArduino() && !currentPort.equals("0")) {
            log.info("Arduino found on port used {} ", currentPort);
            getRuntimeSettings().setSerPortUsed(currentPort);
            getRuntimeSettings().setArduinoPort(arduinoPort); // I clean up in the setter
            delay(5000); //TODO Why is there a 5 second pause here?
            //try {
            //        Thread.sleep(5000);
            //} catch (InterruptedException ex) {
            //    // nothing
            //}
            //millisStart = millis();
            //while (millis() - millisStart < 5000) {
            //  ;
            //}
        }
        getRuntimeSettings().setConnecting(false);
    }

    @Override
    public void settings() {
        //size(320, 240, "P2D");
        size(getSettings().getCamWidth(), getSettings().getCamHeight(), getSettings().getRendererType().altName());
    }

    @Override
    public void setup() {
        log.debug("in setup");
        //size(getSettings().getCamWidth(), getSettings().getCamHeight(), getSettings().getRendererTypeString());
        oldX = getSettings().getCamWidth() / 2; // smoothing (contributed by Adam S.)
        oldY = getSettings().getCamHeight() / 2; // smoothing
        //void setup() {
        //size(getSettings().getCamWidth(), getSettings().getCamHeight());
        //   loadSettings();

        //   size(camWidth, camHeight);                  // some users have reported a faster framerate when the code utilizes OpenGL. To try this, comment out this line and uncomment the line below.
        //  size(camWidth, camHeight, OPENGL);
        //sounds.
        getSounds().playSound("deploying"); //18

        final String jar = GLVideo.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        log.debug("String jar = {}", jar);
        final String nativeLib = jar.substring(0, jar.lastIndexOf(File.separatorChar));
        log.debug("String nativeLib = {}", nativeLib);
        final File symlink = new File(nativeLib + "/linux-armv6hf/libGLESv2.so");
        log.debug("File symlink = {}", symlink);

        //        minim = new Minim(this);
        //        loadSounds();
        //        playSound(18);
        final String[] devices = GLCapture.list();
        println("Video Devices:");
        PApplet.printArray(devices);

        camInput = new GLCapture(this, devices[0], getSettings().getCamWidth(), getSettings().getCamHeight());
        //camInput.start(camWidth, camHeight);
        camInput.start();

        //camInput = new JMyron();
        getRuntimeSettings().setControlIO(ControlIO.getInstance(this));

        background(0);
        updateCamera(true);
        //camInput.
        //camInput.findGlobs(0);
        //camInput.adaptivity(1.01);
        //camInput.update();
        //currFrame = camInput.image();
        //rawImage = camInput.image();
        //deltaBackground = camInput.image();
        //rawBackground = camInput.image();

        //screenPixels = camInput.image();
        target = new BlobDetection(getSettings().getCamWidth(), getSettings().getCamHeight());
        target.setThreshold(0.9f);
        target.setPosDiscrimination(true);
        getRuntimeSettings().setConnecting(true);
        getSettings().setRunWithoutArduino(false);

        thread("retryArduinoConnect");

        //xRatio = camWidth / (xMax - xMin); // used to allign sights with crosshairs on PC
        //yRatio = camHeight / (yMax - yMin); //
        if (getSettings().getRunType() == RunType.Full) {
            getControlPanel().drawControlPanel();
        }
        //}
    }

    @Override
    public void stop() {
        try {
            getSounds().close();
        } catch (final Exception ex) {
            log.error("caught Exception :", ex); //$NON-NLS-1$
        } finally {
            if (!getSettings().isRunWithoutArduino()) {
                getRuntimeSettings().getArduinoPort().write("z0000000");
                delay(500);
                getRuntimeSettings().getArduinoPort().stop();
            }
            //camInput.stop();
            camInput.close();
            super.stop();
        }
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>update the camera image</DD>
     * <DT>Date:</DT>
     * <DD>Sep 9, 2017</DD>
     * </DL>
     *
     * @param init
     *            <DL>
     *            <DT><code>true</code></DT>
     *            <DD>Running from Setup</DD>
     *            <DT><code>false</code></DT>
     *            <DD>Running From Draw</DD>
     *            </DL>
     * @return
     *         <DL>
     *         <DT><code>true</code></DT>
     *         <DD>Camera was ready and a new image is available</DD>
     *         <DT><code>false</code></DT>
     *         <DD>otherwise</DD>
     *         </DL>
     */
    public boolean updateCamera(final boolean init) {
        //background(0);
        if (camInput.available()) {
            camInput.read();
            image(camInput, 0, 0, width, height);
            camInput.loadPixels();
            //loadPixels();
            rawImage = camInput.pixels;
            rawBackground = camInput.pixels;
            if (init) {
                deltaBackground = camInput.pixels;
                lastBackground = camInput.pixels;
                screenPixels = camInput.pixels;
                currFrame = camInput.pixels;
                retinaImage(true);
            } else {
                if (lastBackground == null) {
                    lastBackground = rawBackground;
                }
                if (screenPixels == null) {
                    screenPixels = rawImage;
                }
                retinaImage(false);
            }
            return true;
        }
        return false;
    }
}
