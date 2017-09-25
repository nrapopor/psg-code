/**
 * @author ubuntu - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 2, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapoport.utilities.psgcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.gamecontrolplus.ControlButton;
import org.gamecontrolplus.ControlDevice;
import org.gamecontrolplus.ControlIO;
import org.gamecontrolplus.ControlSlider;

import com.nrapoport.utilities.psgcode.config.RuntimeSettings;
import com.nrapoport.utilities.psgcode.config.Settings;
import com.nrapoport.utilities.psgcode.devices.ConfigLine;
import com.nrapoport.utilities.psgcode.enums.ControlMode;
import com.nrapoport.utilities.psgcode.enums.Controls;
import com.nrapoport.utilities.psgcode.enums.DeviceButtonActions;
import com.nrapoport.utilities.psgcode.enums.DeviceSliderActions;
import com.nrapoport.utilities.psgcode.enums.RunType;
import com.nrapoport.utilities.psgcode.util.Util;

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

    private static final String SLIDER_WARNING = "While it is acceptable to have multiple buttons, performing "
        + "the same function, only one slider may be configured for each type of action. '{}' is already "
        + "configured , ignoring the rest";

    private final Settings settings;

    private final RuntimeSettings runtimeSettings;

    private final ControlPanel controlPanel;

    private final Sounds sounds;

    private List<ControlButton> fire_buttons = new ArrayList<>();

    private List<ControlButton> preciseAim_buttons = new ArrayList<>();

    private List<ControlButton> centerGun_buttons = new ArrayList<>();

    private List<ControlButton> autoOn_buttons = new ArrayList<>();

    private List<ControlButton> autoOff_buttons = new ArrayList<>();

    private List<ControlButton> inputToggle_buttons = new ArrayList<>();

    private List<ControlButton> randomSound_buttons = new ArrayList<>();

    private ControlSlider pan_slider = null;

    private ControlSlider tilt_slider = null;

    private ControlSlider panInvert_slider = null;

    private ControlSlider tiltInvert_slider = null;

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
        if (getSettings().getRunType() == RunType.Full) {
            controlPanel = aParent.getControlPanel();
        } else {
            controlPanel = null;
        }
        sounds = aParent.getSounds();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Check input device state</DD>
     * <DT>Date:</DT>
     * <DD>Sep 25, 2017</DD>
     * </DL>
     */
    void checkInputDevice() {
        if (!getRuntimeSettings().isInputDeviceIsSetup()) {
            setupInputDevice();
        } else {
            final boolean manualModeButtonValue = pressed(autoOff_buttons);
            final boolean autonomousModeButtonValue = pressed(autoOn_buttons);
            boolean soundEffectButtonValue = pressed(randomSound_buttons);
            boolean activeButtonValue = pressed(inputToggle_buttons);

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
                    soundEffectButtonValue = pressed(randomSound_buttons); //TODO this does nothing ... validate
                    //
                    //                    soundEffectButtonValue = false;
                    //                    for (int i = 0; i < randomSound_buttons.length; i++) {
                    //                        if (randomSound_buttons[i].pressed()) {
                    //                            soundEffectButtonValue = true;
                    //                        }
                    //                    }
                }
            }
            if (activeButtonValue) {
                getSettings().flipControls();
                //useInputDevice = !useInputDevice;
                while (activeButtonValue) {
                    activeButtonValue = pressed(inputToggle_buttons); //TODO this does nothing ... validate
                    //                    for (int i = 0; i < inputToggle_buttons.length; i++) {
                    //                        if (inputToggle_buttons[i].pressed()) {
                    //                            activeButtonValue = true;
                    //                        }
                    //                    }
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

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Test if any button in the list has been pressed</DD>
     * <DT>Date:</DT>
     * <DD>Sep 24, 2017</DD>
     * </DL>
     *
     * @param buttonList
     * @return
     */
    protected boolean pressed(final List<ControlButton> buttonList) {
        for (final ControlButton controlButton : buttonList) {
            if (controlButton.pressed()) {
                return true;
            }
        }
        return false;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Process the passed ConfigLine as a button</DD>
     * <DT>Date:</DT>
     * <DD>Sep 25, 2017</DD>
     * </DL>
     *
     * @param configLine
     *            the config line representing a button
     */
    protected void processButton(final ConfigLine configLine) {
        final DeviceButtonActions key = DeviceButtonActions.getByString(configLine.getKey());
        switch (key) {
            case AutoAimOff:
                autoOff_buttons.add(inputDevice.getButton(configLine.getIndex()));
                break;

            case AutoAimOn:
                autoOn_buttons.add(inputDevice.getButton(configLine.getIndex()));
                break;

            case CenterGun:
                centerGun_buttons.add(inputDevice.getButton(configLine.getIndex()));
                break;

            case Fire:
                fire_buttons.add(inputDevice.getButton(configLine.getIndex()));
                break;

            case InputDevToggle:
                inputToggle_buttons.add(inputDevice.getButton(configLine.getIndex()));
                break;

            case RandomSound:
                randomSound_buttons.add(inputDevice.getButton(configLine.getIndex()));
                break;

            case PreciseAim:
                preciseAim_buttons.add(inputDevice.getButton(configLine.getIndex()));
                break;

            default: //NoAction
                break;
        }
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Process the passed ConfigLine as a slider</DD>
     * <DT>Date:</DT>
     * <DD>Sep 25, 2017</DD>
     * </DL>
     *
     * @param configLine
     *            a ConfigLine representing a Slider
     */
    protected void processSlider(final ConfigLine configLine) {
        final DeviceSliderActions sKey = DeviceSliderActions.getByString(configLine.getKey());
        switch (sKey) {
            case Pan:
                if (pan_slider != null) {
                    log.warn(SLIDER_WARNING, sKey.altName());
                    break;
                }
                pan_slider = inputDevice.getSlider(configLine.getIndex());
                break;

            case Tilt:
                if (tilt_slider != null) {
                    log.warn(SLIDER_WARNING, sKey.altName());
                    break;
                }
                tilt_slider = inputDevice.getSlider(configLine.getIndex());

                break;

            case InvertPan:
                if (panInvert_slider != null) {
                    log.warn(SLIDER_WARNING, sKey.altName());
                    break;
                }
                panInvert_slider = inputDevice.getSlider(configLine.getIndex());
                break;

            case InvertTilt:
                if (tiltInvert_slider != null) {
                    log.warn(SLIDER_WARNING, sKey.altName());
                    break;
                }
                tiltInvert_slider = inputDevice.getSlider(configLine.getIndex());
                break;

            default: //NoAction
                break;
        }
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setup configured device</DD>
     * <DT>Date:</DT>
     * <DD>Sep 24, 2017</DD>
     * </DL>
     */
    public void setupInputDevice() {
        final ControlIO controlIO = getRuntimeSettings().getControlIO();

        final Map<String, List<ConfigLine>> configFile = Util.loadDeviceConfig(getSettings().getDeviceConfig());
        final Map.Entry<String, List<ConfigLine>> entry = configFile.entrySet().iterator().next();
        final String controllerName = entry.getKey();
        final List<ConfigLine> configLines = entry.getValue();

        inputDevice = controlIO.getDevice(controllerName);

        if (inputDevice == null) {
            log.error("ERROR: Specified input device is not connected!");
            getSettings().setControls(Controls.Mouse);
            if (getSettings().getRunType() == RunType.Full) {
                getControlPanel().updateCheckboxUseInputDevice(false);
            }
            return;
        }
        log.info("Device Selected = '{}'", inputDevice.getName());
        inputDevice.setTolerance(getSettings().getDeviceTolerance());
        for (final ConfigLine configLine : configLines) {
            switch (configLine.getType()) {
                case Button:
                    processButton(configLine);
                    break;

                case Slider:
                    processSlider(configLine);
                    break;

                default: //TODO Implement Hat
                    break;
            }
        }
        getRuntimeSettings().setInputDeviceIsSetup(true);

    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>return slider value if slider exists or 0</DD>
     * <DT>Date:</DT>
     * <DD>Sep 25, 2017</DD>
     * </DL>
     *
     * @param slider
     *            slider to use
     * @return
     *         <DL>
     *         <DT><code>slider.getValue()</code></DT>
     *         <DD>if slider is not null</DD>
     *         <DT><code>0</code></DT>
     *         <DD>otherwise</DD>
     *         </DL>
     */
    protected float sliderValue(final ControlSlider slider) {
        if (slider != null) {
            return pan_slider.getValue();
        }
        return 0f;

    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>update targeting and position based on the state of the Input Device</DD>
     * <DT>Date:</DT>
     * <DD>Sep 25, 2017</DD>
     * </DL>
     */
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

            float xMotionValue = 0 + sliderValue(pan_slider);
            xMotionValue -= sliderValue(panInvert_slider);
            float yMotionValue = 0 + sliderValue(tilt_slider);
            yMotionValue -= sliderValue(tiltInvert_slider);

            final boolean triggerButtonValue = pressed(fire_buttons);
            final boolean centerButtonValue = pressed(centerGun_buttons);
            final boolean precisionButtonValue = pressed(preciseAim_buttons);

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
            xPosition += aimSensitivityX * xMotionValue; // update the position of the cross-hairs
            yPosition += aimSensitivityY * yMotionValue;

            xPosition = PApplet.constrain(xPosition, 0, camWidth); // don't let the cross-hairs leave the camera view
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
