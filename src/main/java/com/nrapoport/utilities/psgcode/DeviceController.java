/**
 * @author ubuntu - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 2, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapoport.utilities.psgcode;

import org.gamecontrolplus.ControlButton;
import org.gamecontrolplus.ControlDevice;
import org.gamecontrolplus.ControlIO;
import org.gamecontrolplus.ControlSlider;

import com.nrapoport.utilities.psgcode.config.RuntimeSettings;
import com.nrapoport.utilities.psgcode.config.Settings;
import com.nrapoport.utilities.psgcode.enums.ControlMode;
import com.nrapoport.utilities.psgcode.enums.Controls;

import processing.core.PApplet;

/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>Populate the device controller settings from the configuration file</DD>
 * <DT>Date:</DT>
 * <DD>Sep 2, 2017</DD>
 * </DL>
 *
 * @author ubuntu - Nick Rapoport
 *
 */
public class DeviceController extends AbstractPDE {
    //@SuppressWarnings("unused")
    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(DeviceController.class);

    private final Settings settings;

    private final RuntimeSettings runtimeSettings;

    private final ControlPanel controlPanel;

    private final Sounds sounds;

    private ControlButton[] buttons = new ControlButton[30];

    private ControlSlider[] sliders = new ControlSlider[10];

    private ControlButton[] fire_buttons = new ControlButton[0];

    private ControlButton[] preciseAim_buttons = new ControlButton[0];

    private ControlButton[] centerGun_buttons = new ControlButton[0];

    private ControlButton[] autoOn_buttons = new ControlButton[0];

    private ControlButton[] autoOff_buttons = new ControlButton[0];

    private ControlButton[] inputToggle_buttons = new ControlButton[0];

    private ControlButton[] randomSound_buttons = new ControlButton[0];

    private ControlSlider[] pan_sliders = new ControlSlider[0];

    private ControlSlider[] tilt_sliders = new ControlSlider[0];

    private ControlSlider[] panInvert_sliders = new ControlSlider[0];

    private ControlSlider[] tiltInvert_sliders = new ControlSlider[0];

    private ControlDevice inputDevice;

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>DeviceControler Constructor</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @param aParent
     */
    public DeviceController(final PSGProcessingCode aParent) {
        super(aParent);
        settings = aParent.getSettings();
        runtimeSettings = aParent.getRuntimeSettings();
        controlPanel = aParent.getControlPanel();
        sounds = aParent.getSounds();
    }

    void checkInputDevice() {
        if (!getRuntimeSettings().isInputDeviceIsSetup()) {
            setupInputDevice();
        } else {
            boolean manualModeButtonValue = false;
            for (int i = 0; i < autoOff_buttons.length; i++) {
                if (autoOff_buttons[i].pressed()) {
                    manualModeButtonValue = true;
                }
            }

            boolean autonomousModeButtonValue = false;
            for (int i = 0; i < autoOn_buttons.length; i++) {
                if (autoOn_buttons[i].pressed()) {
                    autonomousModeButtonValue = true;
                }
            }

            boolean soundEffectButtonValue = false;
            for (int i = 0; i < randomSound_buttons.length; i++) {
                if (randomSound_buttons[i].pressed()) {
                    soundEffectButtonValue = true;
                }
            }

            boolean activeButtonValue = false;
            for (int i = 0; i < inputToggle_buttons.length; i++) {
                if (inputToggle_buttons[i].pressed()) {
                    activeButtonValue = true;
                }
            }

            if (manualModeButtonValue) { // go to manual mode if appropriate control is pressed
                getSettings().setControlMode(ControlMode.Manual);
                //controlMode = false;
            }
            if (autonomousModeButtonValue) { // go to autonomous mode if appropriate control is pressed
                getSettings().setControlMode(ControlMode.Autonomous);
                //controlMode = true;
            }
            if (soundEffectButtonValue) { // play a random sound effect if appropriate control is pressed
                getSounds().randomIdleSound();
                while (soundEffectButtonValue) {
                    soundEffectButtonValue = false;
                    for (int i = 0; i < randomSound_buttons.length; i++) {
                        if (randomSound_buttons[i].pressed()) {
                            soundEffectButtonValue = true;
                        }
                    }
                }
            }
            if (activeButtonValue) {
                getSettings().flipControls();
                //useInputDevice = !useInputDevice;
                while (activeButtonValue) {
                    activeButtonValue = false;
                    for (int i = 0; i < inputToggle_buttons.length; i++) {
                        if (inputToggle_buttons[i].pressed()) {
                            activeButtonValue = true;
                        }
                    }
                }
            }
        }
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
        return controlPanel;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the inputDevice property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 3, 2017</DD>
     * </DL>
     *
     * @return the value of inputDevice field
     */
    public ControlDevice getInputDevice() {
        return inputDevice;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the runtimeSettings property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     * 
     * @return
     */
    public RuntimeSettings getRuntimeSettings() {
        return runtimeSettings;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the settings property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     * 
     * @return
     */
    public Settings getSettings() {
        return settings;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the sounds property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 3, 2017</DD>
     * </DL>
     *
     * @return the value of sounds field
     */
    public Sounds getSounds() {
        return sounds;
    }

    void setupInputDevice() {
        if (!getRuntimeSettings().isInputDeviceIsSetup()) {

            String[] loadData = new String[49];
            loadData = loadStrings("data/Input Device Setup Tool/settings_inputDevice.txt");

            final ControlIO controlIO = getRuntimeSettings().getControlIO();

            //final boolean error = false;
            try {
                inputDevice = controlIO.getDevice(loadData[2]);
                // println("Device loaded successfully!");
                log.info("Device loaded successfully!");
            } catch (final Exception e) {
                //println("ERROR: Specified input device is not connected!");
                log.error("ERROR: Specified input device is not connected!");
                getSettings().setControls(Controls.Mouse);
                //useInputDevice = false;
                getControlPanel().updateCheckboxUseInputDevice(false);//checkbox_useInputDevice.setSelected(useInputDevice);
                //            configJoystick();
                return;
            }

            inputDevice.setTolerance(0.025f);

            println("Device Selected = " + inputDevice.getName());

            final int numButtons = inputDevice.getNumberOfButtons();
            for (int i = 0; i < numButtons; i++) {
                if (i < buttons.length) {
                    buttons[i] = inputDevice.getButton(i);
                }
            }
            // println("numButtons = " + numButtons);

            final int numSliders = inputDevice.getNumberOfSliders();
            for (int i = 0; i < numSliders; i++) {
                if (i < sliders.length) {
                    sliders[i] = inputDevice.getSlider(i);
                }
            }
            // println("numSliders = " + numSliders);

            for (int i = 0; i <= 29; i++) {

                if (loadData[i + 6].equals("Fire")) {
                    fire_buttons = (ControlButton[]) PApplet.append(fire_buttons, buttons[i]);
                } else if (loadData[i + 6].equals("Precise Aim")) {
                    preciseAim_buttons = (ControlButton[]) PApplet.append(preciseAim_buttons, buttons[i]);
                } else if (loadData[i + 6].equals("Center Gun")) {
                    centerGun_buttons = (ControlButton[]) PApplet.append(centerGun_buttons, buttons[i]);
                } else if (loadData[i + 6].equals("Auto Aim On")) {
                    autoOn_buttons = (ControlButton[]) PApplet.append(autoOn_buttons, buttons[i]);
                } else if (loadData[i + 6].equals("Auto Aim Off")) {
                    autoOff_buttons = (ControlButton[]) PApplet.append(autoOff_buttons, buttons[i]);
                } else if (loadData[i + 6].equals("Input Dev On/Off")) {
                    inputToggle_buttons = (ControlButton[]) PApplet.append(inputToggle_buttons, buttons[i]);
                } else if (loadData[i + 6].equals("Random Sound")) {
                    randomSound_buttons = (ControlButton[]) PApplet.append(randomSound_buttons, buttons[i]);
                }
            }

            for (int i = 0; i <= 9; i++) {
                if (loadData[i + 39].equals("Pan")) {
                    pan_sliders = (ControlSlider[]) PApplet.append(pan_sliders, sliders[i]);
                } else if (loadData[i + 39].equals("Tilt")) {
                    tilt_sliders = (ControlSlider[]) PApplet.append(tilt_sliders, sliders[i]);
                } else if (loadData[i + 39].equals("Pan (Invert)")) {
                    panInvert_sliders = (ControlSlider[]) PApplet.append(panInvert_sliders, sliders[i]);
                } else if (loadData[i + 39].equals("Tilt (Invert)")) {
                    tiltInvert_sliders = (ControlSlider[]) PApplet.append(tiltInvert_sliders, sliders[i]);
                }
            }

            getRuntimeSettings().setInputDeviceIsSetup(true);
        }
    }

    void updateInputDevice() {
        if (!getRuntimeSettings().isInputDeviceIsSetup()) {
            setupInputDevice();
        } else {
            final int camWidth = getSettings().getCamWidth();
            final int camHeight = getSettings().getCamHeight();
            final float xRatio = getSettings().getxRatio();
            final float yRatio = getSettings().getyRatio();
            final float xMin = getSettings().getxMin();
            final float yMin = getSettings().getyMin();
            //            final float xMax = getSettings().getxMax();
            //            final float yMax = getSettings().getyMax();

            float xMotionValue = 0;
            for (int i = 0; i < pan_sliders.length; i++) {
                xMotionValue = xMotionValue + pan_sliders[i].getValue();
            }
            for (int i = 0; i < panInvert_sliders.length; i++) {
                xMotionValue = xMotionValue - panInvert_sliders[i].getValue();
            }

            float yMotionValue = 0;
            for (int i = 0; i < tilt_sliders.length; i++) {
                yMotionValue = yMotionValue + tilt_sliders[i].getValue();
            }
            for (int i = 0; i < tiltInvert_sliders.length; i++) {
                yMotionValue = yMotionValue - tiltInvert_sliders[i].getValue();
            }

            boolean triggerButtonValue = false;
            for (int i = 0; i < fire_buttons.length; i++) {
                if (fire_buttons[i].pressed()) {
                    triggerButtonValue = true;
                }
            }

            boolean centerButtonValue = false;
            for (int i = 0; i < centerGun_buttons.length; i++) {
                if (centerGun_buttons[i].pressed()) {
                    centerButtonValue = true;
                }
            }

            boolean precisionButtonValue = false;
            for (int i = 0; i < preciseAim_buttons.length; i++) {
                if (preciseAim_buttons[i].pressed()) {
                    precisionButtonValue = true;
                }
            }

            float aimSensitivityX =
                PApplet.map(PApplet.pow(PApplet.abs(xMotionValue), 2f), 0.0f, 1.0f, 1.0f, camWidth / 10); // set the sensitivity coeficcient for horizontal axis. Based on a quadratic correlation.
            float aimSensitivityY =
                PApplet.map(PApplet.pow(PApplet.abs(yMotionValue), 2f), 0.0f, 1.0f, 1.0f, camWidth / 10); // set the sensitivity coeficcient for vertical axis. Based on a quadratic correlation.

            if (precisionButtonValue) { // aim precisely if appropriate control is pressed
                aimSensitivityX *= 0.25;
                aimSensitivityY *= 0.25;
            }

            float xPosition = getRuntimeSettings().getxPosition();
            float yPosition = getRuntimeSettings().getyPosition();
            xPosition += aimSensitivityX * xMotionValue; // update the position of the crosshairs
            yPosition += aimSensitivityY * yMotionValue;

            xPosition = PApplet.constrain(xPosition, 0, camWidth); // don't let the crosshairs leave the camera view
            yPosition = PApplet.constrain(yPosition, 0, camHeight);

            if (centerButtonValue) { // center the crosshairs if appropriate control is pressed
                xPosition = camWidth / 2;
                yPosition = camHeight / 2;
            }
            getRuntimeSettings().setFiring(triggerButtonValue);
            //          if (triggerButtonValue) {   // fire if appropriate control is pressed
            //            fire = 1;
            //          }
            //          else {
            //            fire = 0;
            //          }
            getRuntimeSettings().setxPosition(xPosition);
            getRuntimeSettings().setyPosition(yPosition);

            getRuntimeSettings().setTargetX(Math.round(xPosition / xRatio + xMin)); // calculate position to go to based on mouse position
            getRuntimeSettings().setTargetY(Math.round((camHeight - yPosition) / yRatio + yMin)); //
            getRuntimeSettings().setDisplayX(Math.round(xPosition));
            getRuntimeSettings().setDisplayY(Math.round(yPosition));
        }
    }

}
