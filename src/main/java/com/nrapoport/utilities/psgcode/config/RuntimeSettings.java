/**
 * @author nvr - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 1, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapoport.utilities.psgcode.config;

import java.util.Random;

import org.gamecontrolplus.ControlIO;

import com.nrapoport.utilities.psgcode.PSGProcessingCode;

import processing.serial.Serial;

/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>run time settings for the project (these are not persisted anywhere). theya all marked volatile as I'm not sure
 * of the threading in the underlying Processing code</DD>
 * <DT>Date:</DT>
 * <DD>Sep 2, 2017</DD>
 * </DL>
 *
 * @author ubuntu - Nick Rapoport
 *
 */
public class RuntimeSettings implements ISettingsAware {

    private Settings settings;

    private volatile boolean connecting = false;

    private volatile String serPortUsed = "0";

    private volatile boolean selectingColor = false;

    private volatile boolean selectingSafeColor = false;

    private volatile Serial arduinoPort = null;

    private volatile boolean inputDeviceIsSetup = false;
    //        int ydiff; // smoothing
    //
    //private float xRatio; //derived

    private volatile int targetX = 0;

    private volatile int targetY = 0;

    private volatile int prevTargetX = 0;

    private volatile int prevTargetY = 0;

    private volatile int displayX = 0;

    private volatile int displayY = 0;

    private volatile float xPosition = 0;

    private volatile float yPosition = 0;

    private volatile float possibleX = 0;

    private volatile float possibleY = 0;

    private volatile boolean firing;

    private volatile int[] previouslyFired = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

    private boolean scan = false;

    private volatile ControlIO controlIO; // more stuff for using a joystick or game Controller for input

    private volatile Random random;

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>RuntimeSettings Constructor</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param aParent
     */
    public RuntimeSettings(final PSGProcessingCode aParent) {
        settings = aParent.getSettings();
        init();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the arduinoPort property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @return the value of arduinoPort field
     */
    public Serial getArduinoPort() {
        return arduinoPort;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the controlIO property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 3, 2017</DD>
     * </DL>
     *
     * @return the value of controlIO field
     */
    public ControlIO getControlIO() {
        return controlIO;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the displayX property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 3, 2017</DD>
     * </DL>
     *
     * @return the value of displayX field
     */
    public int getDisplayX() {
        return displayX;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the displayY property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 3, 2017</DD>
     * </DL>
     *
     * @return the value of displayY field
     */
    public int getDisplayY() {
        return displayY;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>return the integer equivalent of the firing property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 3, 2017</DD>
     * </DL>
     *
     * @return
     *         <DL>
     *         <DT><code>true</code></DT>
     *         <DD>1</DD>
     *         <DT><code>false</code></DT>
     *         <DD>0</DD>
     *         </DL>
     */
    public int getFiringInt() {
        return firing ? 1 : 0;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the possibleX property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 3, 2017</DD>
     * </DL>
     *
     * @return the value of possibleX field
     */
    public float getPossibleX() {
        return possibleX;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the possibleY property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 3, 2017</DD>
     * </DL>
     *
     * @return the value of possibleY field
     */
    public float getPossibleY() {
        return possibleY;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the previouslyFired property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 3, 2017</DD>
     * </DL>
     *
     * @return the value of previouslyFired field
     */
    public int[] getPreviouslyFired() {
        return previouslyFired;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the prevTargetX property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @return the value of prevTargetX field
     */
    public int getPrevTargetX() {
        return prevTargetX;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the prevTargetY property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @return the value of prevTargetY field
     */
    public int getPrevTargetY() {
        return prevTargetY;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the random property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 24, 2017</DD>
     * </DL>
     * 
     * @return the value of random field
     */
    public Random getRandom() {
        return random;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the scan property as an integr</DD>
     * <DT>Date:</DT>
     * <DD>Sep 3, 2017</DD>
     * </DL>
     *
     * @return
     *         <DL>
     *         <DT><code>true</code></DT>
     *         <DD>1</DD>
     *         <DT><code>false</code></DT>
     *         <DD>0</DD>
     *         </DL>
     */
    public int getScanInt() {
        return scan ? 1 : 0;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the serPortUsed property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @return the value of serPortUsed field
     */
    public String getSerPortUsed() {
        return serPortUsed;
    }

    /** {@inheritDoc} */
    @Override
    public Settings getSettings() {
        return settings;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the strtargetX property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @return the value of targetX field
     */
    public String getStrTargetX() {
        return String.format("%1$03d", getTargetX());
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the targetX property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @return the value of targetX field
     */
    public String getStrTargetY() {
        return String.format("%1$03d", getTargetY());
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the targetX property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @return the value of targetX field
     */
    public int getTargetX() {
        return targetX;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the targetY property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @return the value of targetY field
     */
    public int getTargetY() {
        return targetY;
    }
    //private float yRatio;

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the xPosition property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 3, 2017</DD>
     * </DL>
     *
     * @return the value of xPosition field
     */
    public float getxPosition() {
        return xPosition;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the yPosition property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 3, 2017</DD>
     * </DL>
     *
     * @return the value of yPosition field
     */
    public float getyPosition() {
        return yPosition;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>is there still a target</DD>
     * <DT>Date:</DT>
     * <DD>Sep 3, 2017</DD>
     * </DL>
     *
     * @return
     *         <DL>
     *         <DT><code>true</code></DT>
     *         <DD>Keep at it</DD>
     *         <DT><code>false</code></DT>
     *         <DD>Nope it's gone</DD>
     *         </DL>
     */
    public boolean hasTargetDepartedScreen() {
        updatePreviouslyFired();
        final int sumNewFire =
            previouslyFired[0] + previouslyFired[1] + previouslyFired[2] + previouslyFired[3] + previouslyFired[4];
        final int sumPrevFire =
            previouslyFired[5] + previouslyFired[6] + previouslyFired[7] + previouslyFired[8] + previouslyFired[9];

        return sumNewFire == 0 && sumPrevFire == 5; // target departed screen

    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>increment the displayX property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 3, 2017</DD>
     * </DL>
     *
     * @param aDisplayX
     *            value to use to increment displayX property
     */
    public void incrementDisplayX(final int aDisplayX) {
        displayX += aDisplayX;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>increment the displayY property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 3, 2017</DD>
     * </DL>
     *
     * @param aDisplayY
     *            value to use to increment displayY property
     */
    public void incrementDisplayY(final int aDisplayY) {
        displayY += aDisplayY;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>increment xPosition property by a passed value</DD>
     * <DT>Date:</DT>
     * <DD>Sep 3, 2017</DD>
     * </DL>
     *
     * @param aXPosition
     *            new value be added to the xPosition property
     */
    public void incrementxPosition(final float aXPosition) {
        xPosition += aXPosition;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>increment yPosition property by a passed value</DD>
     * <DT>Date:</DT>
     * <DD>Sep 3, 2017</DD>
     * </DL>
     *
     * @param aYPosition
     *            new value be added to the yPosition property
     */
    public void incrementyPosition(final float aYPosition) {
        yPosition += aYPosition;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Initialize runtime variables from settings</DD>
     * <DT>Date:</DT>
     * <DD>Sep 18, 2017</DD>
     * </DL>
     */
    private void init() {
        targetX = getSettings().getCamWidth() / 2;
        targetY = getSettings().getCamHeight() / 2;
        prevTargetX = targetX;
        prevTargetY = targetY;
        displayX = getSettings().getCamWidth() / 2;
        displayY = getSettings().getCamHeight() / 2;
        xPosition = getSettings().getCamWidth() / 2;
        yPosition = getSettings().getCamHeight() / 2;
        possibleX = getSettings().getCamWidth() / 2;
        possibleY = getSettings().getCamHeight() / 2;
        random = new Random(getSettings().getParent().millis());
    }

    //private int possibleX = camWidth / 2;

    //private int possibleY = camHeight / 2;

    //private int displayX = camWidth / 2;

    //private int displayY = camHeight / 2;

    //private int oldX = camWidth / 2; // smoothing (contributed by Adam S.)

    //private int oldY = camHeight / 2; // smoothing

    //
    //    String strTargetx;
    //
    //    String strTargety;
    //
    //    String fireSelector;
    //
    //    String scanSelector;
    //

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the connecting property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @return the value of connecting field
     */
    public boolean isConnecting() {
        return connecting;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the firing property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 3, 2017</DD>
     * </DL>
     *
     * @return the value of firing field
     */
    public boolean isFiring() {
        return firing;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the inputDeviceIsSetup property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @return the value of inputDeviceIsSetup field
     */
    public boolean isInputDeviceIsSetup() {
        return inputDeviceIsSetup;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>test if the previous target X coordinate is the same as the current one</DD>
     * <DT>Date:</DT>
     * <DD>Sep 3, 2017</DD>
     * </DL>
     *
     * @return
     *         <DL>
     *         <DT><code>true</code></DT>
     *         <DD>if PrevTargetX and TargetX are the same</DD>
     *         <DT><code>false</code></DT>
     *         <DD>otherwise</DD>
     *         </DL>
     */
    public boolean isPrevTargetXeqTargetX() {
        return getTargetX() == getPrevTargetX();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>test if the previous target Y coordinate is the same as the current one</DD>
     * <DT>Date:</DT>
     * <DD>Sep 3, 2017</DD>
     * </DL>
     *
     * @return
     *         <DL>
     *         <DT><code>true</code></DT>
     *         <DD>if PrevTargetY and TargetY are the same</DD>
     *         <DT><code>false</code></DT>
     *         <DD>otherwise</DD>
     *         </DL>
     */
    public boolean isPrevTargetYeqTargetY() {
        return getTargetY() == getPrevTargetY();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the scan property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 3, 2017</DD>
     * </DL>
     *
     * @return the value of scan field
     */
    public boolean isScan() {
        return scan;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the selectingColor property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @return the value of selectingColor field
     */
    public boolean isSelectingColor() {
        return selectingColor;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the selectingSafeColor property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @return the value of selectingSafeColor field
     */
    public boolean isSelectingSafeColor() {
        return selectingSafeColor;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the arduinoPort property. This method will also test to see if the port was set previously, and
     * stop it if it was</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @param aArduinoPort
     *            new value for the arduinoPort property
     */
    public void setArduinoPort(final Serial aArduinoPort) {
        if (arduinoPort != null) { //clean up
            arduinoPort.stop();
        }
        arduinoPort = aArduinoPort;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the connecting property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @param aConnecting
     *            new value for the connecting property
     */
    public void setConnecting(final boolean aConnecting) {
        connecting = aConnecting;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the controlIO property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 3, 2017</DD>
     * </DL>
     *
     * @param aControlIO
     *            new value for the controlIO property
     */
    public void setControlIO(final ControlIO aControlIO) {
        controlIO = aControlIO;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the displayX property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 3, 2017</DD>
     * </DL>
     *
     * @param aDisplayX
     *            new value for the displayX property
     */
    public void setDisplayX(final int aDisplayX) {
        displayX = aDisplayX;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the displayY property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 3, 2017</DD>
     * </DL>
     *
     * @param aDisplayY
     *            new value for the displayY property
     */
    public void setDisplayY(final int aDisplayY) {
        displayY = aDisplayY;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the firing property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 3, 2017</DD>
     * </DL>
     *
     * @param aFiring
     *            new value for the firing property
     */
    public void setFiring(final boolean aFiring) {
        firing = aFiring;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the inputDeviceIsSetup property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @param aInputDeviceIsSetup
     *            new value for the inputDeviceIsSetup property
     */
    public void setInputDeviceIsSetup(final boolean aInputDeviceIsSetup) {
        inputDeviceIsSetup = aInputDeviceIsSetup;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the possibleX property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 3, 2017</DD>
     * </DL>
     *
     * @param aPossibleX
     *            new value for the possibleX property
     */
    public void setPossibleX(final float aPossibleX) {
        possibleX = aPossibleX;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the possibleY property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 3, 2017</DD>
     * </DL>
     *
     * @param aPossibleY
     *            new value for the possibleY property
     */
    public void setPossibleY(final float aPossibleY) {
        possibleY = aPossibleY;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the previouslyFired property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 3, 2017</DD>
     * </DL>
     *
     * @param aPreviouslyFired
     *            new value for the previouslyFired property
     */
    public void setPreviouslyFired(final int[] aPreviouslyFired) {
        previouslyFired = aPreviouslyFired;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the prevTargetX property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @param aPrevTargetX
     *            new value for the prevTargetX property
     */
    public void setPrevTargetX(final int aPrevTargetX) {
        prevTargetX = aPrevTargetX;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the prevTargetY property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @param aPrevTargetY
     *            new value for the prevTargetY property
     */
    public void setPrevTargetY(final int aPrevTargetY) {
        prevTargetY = aPrevTargetY;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the scan property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 3, 2017</DD>
     * </DL>
     *
     * @param aScan
     *            new value for the scan property
     */
    public void setScan(final boolean aScan) {
        scan = aScan;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the selectingColor property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @param aSelectingColor
     *            new value for the selectingColor property
     */
    public void setSelectingColor(final boolean aSelectingColor) {
        selectingColor = aSelectingColor;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the selectingSafeColor property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @param aSelectingSafeColor
     *            new value for the selectingSafeColor property
     */
    public void setSelectingSafeColor(final boolean aSelectingSafeColor) {
        selectingSafeColor = aSelectingSafeColor;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the serPortUsed property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @param aSerPortUsed
     *            new value for the serPortUsed property
     */
    public void setSerPortUsed(final String aSerPortUsed) {
        serPortUsed = aSerPortUsed;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the targetX property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @param aTargetX
     *            new value for the targetX property
     */
    public void setTargetX(final int aTargetX) {
        targetX = aTargetX;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the targetY property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @param aTargetY
     *            new value for the targetY property
     */
    public void setTargetY(final int aTargetY) {
        targetY = aTargetY;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the xPosition property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 3, 2017</DD>
     * </DL>
     *
     * @param aXPosition
     *            new value for the xPosition property
     */
    public void setxPosition(final float aXPosition) {
        xPosition = aXPosition;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the yPosition property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 3, 2017</DD>
     * </DL>
     *
     * @param aYPosition
     *            new value for the yPosition property
     */
    public void setyPosition(final float aYPosition) {
        yPosition = aYPosition;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Update th status of the last 10 firing states in preparation for determination if we still have something to
     * shoot at.</DD>
     * <DT>Date:</DT>
     * <DD>Sep 3, 2017</DD>
     * </DL>
     */
    protected void updatePreviouslyFired() {
        for (int i = 9; i > 0; i--) {
            previouslyFired[i] = previouslyFired[i - 1];
        }
        previouslyFired[0] = getFiringInt();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>update the Previous X/Y coordinates to the Current ones.</DD>
     * <DT>Date:</DT>
     * <DD>Sep 3, 2017</DD>
     * </DL>
     */
    public void updatePrevTargetXY() {
        setPrevTargetX(getTargetX());
        setPrevTargetY(getTargetY());
    }

}
