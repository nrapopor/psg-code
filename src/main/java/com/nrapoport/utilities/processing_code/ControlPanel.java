/**
 * @author ubuntu - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 2, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapoport.utilities.processing_code;

import java.io.IOException;

import com.nrapoport.utilities.processing_code.config.IRuntimeSettingsAware;
import com.nrapoport.utilities.processing_code.config.ISettingsAware;
import com.nrapoport.utilities.processing_code.config.RuntimeSettings;
import com.nrapoport.utilities.processing_code.config.Settings;
import com.nrapoport.utilities.processing_code.devices.InputDeviceSetupTool;
import com.nrapoport.utilities.processing_code.enums.ControlMode;
import com.nrapoport.utilities.processing_code.enums.Controls;
import com.nrapoport.utilities.processing_code.enums.Effects;
import com.nrapoport.utilities.processing_code.enums.FiringMode;

import g4p_controls.G4P;
import g4p_controls.GAlign;
import g4p_controls.GButton;
import g4p_controls.GCheckbox;
import g4p_controls.GConstants;
import g4p_controls.GDropList;
import g4p_controls.GEvent;
import g4p_controls.GLabel;
import g4p_controls.GPanel;
import g4p_controls.GSlider;
import g4p_controls.GToggleControl;
import g4p_controls.GValueControl;
import g4p_controls.GWinData;
import g4p_controls.GWindow;
import gohai.glvideo.GLCapture;
// import JMyron.JMyron;
// import guicomponents.G4P;
// import guicomponents.GButton;
// import guicomponents.GCScheme;
// import guicomponents.GCheckbox;
// import guicomponents.GCombo;
// import guicomponents.GConstants;
// import guicomponents.GLabel;
// import guicomponents.GOption;
// import guicomponents.GOptionGroup;
// import guicomponents.GPanel;
// import guicomponents.GSlider;
// import guicomponents.GWSlider;
// import guicomponents.GWinApplet;
// import guicomponents.GWinData;
// import guicomponents.GWindow;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.serial.Serial;

/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>TODO add description</DD>
 * <DT>Date:</DT>
 * <DD>Sep 2, 2017</DD>
 * </DL>
 *
 * @author ubuntu - Nick Rapoport
 *
 */
public class ControlPanel extends AbstractPDE implements ISettingsAware, IRuntimeSettingsAware {
    // @SuppressWarnings("unused")
    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ControlPanel.class);

    final Settings settings;

    final RuntimeSettings runtimeSettings;

    final Sounds sounds;

    //final JMyron camInput;
    GLCapture camInput;

    public int controlPanelWindowX = 50; // x position on screen of upper-left corner of control panel

    public int controlPanelWindowY = 100; // y position on screen of upper-left corner of control panel

    GPanel panel_main; // control panel

    PImage panelBackgroundImg;

    GLabel label_serialOut, label_targetX, label_targetY, label_fire, label_fireSelector, label_scanSelector,
            label_runWithoutArduino, label_xMin, label_xMax, label_yMin, label_yMax, label_setxMin, label_setxMax,
            label_setyMin, label_setyMax; // text labels on control panel

    GCheckbox checkbox_leadTarget, checkbox_showRestrictedZones, checkbox_trackingColor, checkbox_safeColor,
            checkbox_trackingMotion, checkbox_showDifferentPixels, checkbox_showTargetBox, checkbox_mirrorCam,
            checkbox_controlMode, checkbox_safety, checkbox_showCameraView, checkbox_scanWhenIdle,
            checkbox_soundEffects, checkbox_activeSmoothing, checkbox_useInputDevice, checkbox_useArrowKeys;// checkboxes

    GButton button_viewCameraSettings, button_setBackground, button_selectColor, button_selectSafeColor,
            button_openWebsite, button_playRandomSound, button_saveSettings, button_loadSettings,
            button_retryArduinoConnect, button_saveAndExit, button_configJoystick, button_resetCalibration,
            button_flipX, button_flipY; // buttons

    GSlider slider_tolerance, slider_trackColorTolerance, slider_safeColorTolerance, slider_safeColorMinSize,
            slider_minBlobArea, slider_nbDot, slider_antSens, slider_propX, slider_propY, slider_smoothingFactor; //sliders

    GLabel label_slider_tolerance, label_slider_trackColorTolerance, label_slider_safeColorTolerance,
            label_slider_safeColorMinSize, label_slider_minBlobArea, label_slider_nbDot, label_slider_antSens,
            label_slider_propX, label_slider_propY, label_smoothingFactor; // value readouts for sliders
    // GTextField txfSomeText;   // textfield

    GDropList dropdown_effect, dropdown_firingMode, dropdown_comPort; // dropdown menus
    //GActivityBar acyBar;   // activity bar
    //GTimer tmrTimer;       // timer

    //GOptionGroup opgMouseOver;

    //GOption optHand, optXhair, optMove, optText, optWait;

    // G4P components for second windowl
    GWindow window_main;

    int sliderInertia = 3;

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>ControlPanel Constructor</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @param aParent
     */
    public ControlPanel(final PSGProcessingCode aParent) {
        super(aParent);
        settings = aParent.getSettings();
        runtimeSettings = aParent.getRuntimeSettings();
        sounds = aParent.getSounds();
        camInput = aParent.getCamInput();

    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>TODO add configJoystick description</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     */
    void configJoystick() {
        println("Opening InputDeviceSetupTool...");
        getSettings().setControls(Controls.Mouse);
        //useInputDevice = false;
        checkbox_useInputDevice.setSelected(false);
        getRuntimeSettings().setInputDeviceIsSetup(false);
        // try {
        // open(dataPath("Input Device Setup Tool/InputDeviceSetupTool.exe"));
        try {
            InputDeviceSetupTool.main((String[]) null);
        } catch (final Exception ex) {
            log.error("caught Exception launching InputDeviceSetupTool :", ex); //$NON-NLS-1$
        }
    }

    public void drawController(final PApplet appc, final GWinData data) {
        appc.background(panelBackgroundImg);

    }

    void drawControlPanel() {
        G4P.messagesEnabled(false);
        G4P.setGlobalColorScheme(GConstants.BLUE_SCHEME);
        G4P.setCursor(PConstants.ARROW);
        getParent().getSurface().setTitle("Settings");
        panelBackgroundImg = loadImage("Panel_Background.png");
        getParent().getSurface().setTitle("Control Panel");

        window_main = GWindow.getWindow(getParent(), "Main", 50, 100, 600, 600, PConstants.P2D);
        window_main.addDrawHandler(this, "drawController");
        G4P.setMouseOverEnabled(true);

        // create Panels
        panel_main = new GPanel(window_main, 0, 0, 600, 600, "Main");
        panel_main.setOpaque(false);
        panel_main.setCollapsed(false);

        // create labels
        label_serialOut = new GLabel(window_main, 300, 475, 150, 20, "Serial Out:           ");
        //label_serialOut.setBorder(0);
        label_serialOut.setOpaque(false);
        label_serialOut.setLocalColorScheme(GConstants.BLUE_SCHEME);
        panel_main.addControl(label_serialOut);

        label_targetX = new GLabel(window_main, 300, 495, 150, 20, "Pan Servo Position:    ");
        //label_targetX.setBorder(0);
        label_targetX.setOpaque(false);
        label_targetX.setLocalColorScheme(GConstants.BLUE_SCHEME);
        panel_main.addControl(label_targetX);

        label_targetY = new GLabel(window_main, 300, 515, 150, 20, "Tilt Servo Position:    ");
        //label_targetY.setBorder(0);
        label_targetY.setOpaque(false);
        label_targetY.setLocalColorScheme(GConstants.BLUE_SCHEME);
        panel_main.addControl(label_targetY);

        label_fire = new GLabel(window_main, 300, 535, 150, 20, "Not Firing");
        //label_fire.setBorder(0);
        label_fire.setOpaque(false);
        label_fire.setLocalColorScheme(GConstants.RED_SCHEME);
        panel_main.addControl(label_fire);

        label_fireSelector = new GLabel(window_main, 300, 555, 150, 20, "Automatic");
        //label_fireSelector.setBorder(0);
        label_fireSelector.setOpaque(false);
        label_fireSelector.setLocalColorScheme(GConstants.BLUE_SCHEME);
        panel_main.addControl(label_fireSelector);

        label_scanSelector = new GLabel(window_main, 300, 575, 150, 20, "Scan When Idle");
        //label_scanSelector.setBorder(0);
        label_scanSelector.setOpaque(false);
        label_scanSelector.setLocalColorScheme(GConstants.BLUE_SCHEME);
        panel_main.addControl(label_scanSelector);

        label_runWithoutArduino = new GLabel(window_main, 460, 475, 120, 20, "No Controller");
        //label_runWithoutArduino.setBorder(0);
        label_runWithoutArduino.setOpaque(false);
        label_runWithoutArduino.setLocalColorScheme(GConstants.YELLOW_SCHEME);
        panel_main.addControl(label_runWithoutArduino);

        label_xMin = new GLabel(window_main, 35, 362, 150, 20, "xMin: 000");
        //label_xMin.setBorder(0);
        label_xMin.setOpaque(false);
        label_xMin.setLocalColorScheme(GConstants.YELLOW_SCHEME);
        panel_main.addControl(label_xMin);

        label_xMax = new GLabel(window_main, 145, 362, 150, 20, "xMax: 180");
        //label_xMax.setBorder(0);
        label_xMax.setOpaque(false);
        label_xMax.setLocalColorScheme(GConstants.YELLOW_SCHEME);
        panel_main.addControl(label_xMax);

        label_yMin = new GLabel(window_main, 35, 392, 150, 20, "yMin: 000");
        //label_yMin.setBorder(0);
        label_yMin.setOpaque(false);
        label_yMin.setLocalColorScheme(GConstants.YELLOW_SCHEME);
        panel_main.addControl(label_yMin);

        label_yMax = new GLabel(window_main, 145, 392, 150, 20, "yMax: 180");
        //label_yMax.setBorder(0);
        label_yMax.setOpaque(false);
        label_yMax.setLocalColorScheme(GConstants.YELLOW_SCHEME);
        panel_main.addControl(label_yMax);

        label_setxMin = new GLabel(window_main, 10, 375, 120, 10, "to set xMin press A");
        panel_main.addControl(label_setxMin);

        label_setxMax = new GLabel(window_main, 120, 375, 120, 10, "to set xMax press D");
        panel_main.addControl(label_setxMax);

        label_setyMin = new GLabel(window_main, 10, 405, 120, 10, "to set yMin press S");
        panel_main.addControl(label_setyMin);

        label_setyMax = new GLabel(window_main, 120, 405, 120, 10, "to set yMax press W");
        panel_main.addControl(label_setyMax);

        // create checkboxes
        checkbox_leadTarget = new GCheckbox(window_main, 310, 325, 120, 20);
        checkbox_leadTarget.setText("Enable Target Anticipation");

        checkbox_leadTarget.setSelected(getSettings().isLeadTarget());
        //  checkbox_leadTarget.setBorder(0);
        //  checkbox_leadTarget.addEventHandler(this, "checkbox_click");
        panel_main.addControl(checkbox_leadTarget);

        checkbox_showRestrictedZones = new GCheckbox(window_main, 10, 480, 220, 20);
        checkbox_showRestrictedZones.setText("Show Restricted Zones  (to set, hold R and click+drag)");
        checkbox_showRestrictedZones.setSelected(getSettings().isShowRestrictedZones());
        //  //checkbox_showRestrictedZones.setBorder(0);
        //  checkbox_showRestrictedZones.addEventHandler(this, "checkbox_click");
        panel_main.addControl(checkbox_showRestrictedZones);

        checkbox_trackingColor = new GCheckbox(window_main, 10, 255, 120, 20);
        checkbox_trackingColor.setText("Track A Color");
        checkbox_trackingColor.setSelected(getSettings().isTrackingColor());
        //checkbox_trackingColor.setBorder(0);
        //  checkbox_trackingColor.addEventHandler(this, "checkbox_click");
        panel_main.addControl(checkbox_trackingColor);

        checkbox_safeColor = new GCheckbox(window_main, 310, 175, 120, 20);
        checkbox_safeColor.setText("Enable Safe Color");
        checkbox_safeColor.setSelected(getSettings().isSafeColor());
        //  checkbox_safeColor.setBorder(0);
        //  checkbox_safeColor.addEventHandler(this, "checkbox_click");
        panel_main.addControl(checkbox_safeColor);

        checkbox_trackingMotion = new GCheckbox(window_main, 10, 205, 120, 20);
        checkbox_trackingMotion.setText("Track Motion");
        checkbox_trackingMotion.setSelected(getSettings().isTrackingMotion());
        //  checkbox_trackingMotion.setBorder(0);
        //  checkbox_trackingMotion.addEventHandler(this, "checkbox_click");
        panel_main.addControl(checkbox_trackingMotion);

        checkbox_showDifferentPixels = new GCheckbox(window_main, 10, 500, 120, 20);
        checkbox_showDifferentPixels.setText("Show Different Pixels");
        checkbox_showDifferentPixels.setSelected(getSettings().isShowDifferentPixels());
        //  checkbox_showDifferentPixels.setBorder(0);
        //  checkbox_showDifferentPixels.addEventHandler(this, "checkbox_click");
        panel_main.addControl(checkbox_showDifferentPixels);

        checkbox_showTargetBox = new GCheckbox(window_main, 10, 520, 120, 20);
        checkbox_showTargetBox.setText("Show Target Box");
        checkbox_showTargetBox.setSelected(getSettings().isShowTargetBox());
        //  checkbox_showTargetBox.setBorder(0);
        //  checkbox_showTargetBox.addEventHandler(this, "checkbox_click");
        panel_main.addControl(checkbox_showTargetBox);

        checkbox_mirrorCam = new GCheckbox(window_main, 10, 540, 120, 20);
        checkbox_mirrorCam.setText("Mirror Webcam");
        checkbox_mirrorCam.setSelected(getSettings().isMirrorCam());
        //  checkbox_mirrorCam.setBorder(0);
        //  checkbox_mirrorCam.addEventHandler(this, "checkbox_click");
        panel_main.addControl(checkbox_mirrorCam);

        checkbox_controlMode = new GCheckbox(window_main, 10, 25, 120, 20);
        checkbox_controlMode.setText("Enable Autonomous Mode (press SPACE to toggle)");
        checkbox_controlMode.setSelected(getSettings().getControlMode() == ControlMode.Autonomous);
        //  checkbox_controlMode.setBorder(0);
        //  checkbox_controlMode.addEventHandler(this, "checkbox_click");
        panel_main.addControl(checkbox_controlMode);

        checkbox_safety = new GCheckbox(window_main, 10, 325, 120, 20);
        checkbox_safety.setText("Enable Weapon");
        checkbox_safety.setSelected(getSettings().isSafety());
        //  checkbox_safety.setBorder(0);
        //  checkbox_safety.addEventHandler(this, "checkbox_click");
        panel_main.addControl(checkbox_safety);

        checkbox_showCameraView = new GCheckbox(window_main, 10, 560, 120, 20);
        checkbox_showCameraView.setText("Show Camera View");
        checkbox_showCameraView.setSelected(getSettings().isShowCameraView());
        //  checkbox_showCameraView.setBorder(0);
        //  checkbox_showCameraView.addEventHandler(this, "checkbox_click");
        panel_main.addControl(checkbox_showCameraView);

        checkbox_scanWhenIdle = new GCheckbox(window_main, 10, 345, 120, 20);
        checkbox_scanWhenIdle.setText("Scan When Idle");
        checkbox_scanWhenIdle.setSelected(getSettings().isScanWhenIdle());
        //  checkbox_scanWhenIdle.setBorder(0);
        //  checkbox_scanWhenIdle.addEventHandler(this, "checkbox_click");
        panel_main.addControl(checkbox_scanWhenIdle);

        checkbox_soundEffects = new GCheckbox(window_main, 315, 25, 120, 20);
        checkbox_soundEffects.setText("Enable Sounds Effects");
        checkbox_soundEffects.setSelected(getSettings().isSoundEffects());
        //  checkbox_soundEffects.setBorder(0);
        //  checkbox_soundEffects.addEventHandler(this, "checkbox_click");
        panel_main.addControl(checkbox_soundEffects);

        checkbox_activeSmoothing = new GCheckbox(window_main, 460, 25, 120, 20);
        checkbox_activeSmoothing.setText("Smoothing");
        checkbox_activeSmoothing.setSelected(getSettings().isActiveSmoothing());
        //  checkbox_activeSmoothing.setBorder(0);
        //  checkbox_activeSmoothing.addEventHandler(this, "checkbox_click");
        panel_main.addControl(checkbox_activeSmoothing);

        checkbox_useInputDevice = new GCheckbox(window_main, 10, 45, 220, 20);
        checkbox_useInputDevice.setText("Use Joystick/Game Controller Input");
        checkbox_useInputDevice.setSelected(getSettings().getControls() == Controls.Device);
        //  checkbox_useInputDevice.setBorder(0);
        //  checkbox_useInputDevice.addEventHandler(this, "checkbox_click");
        panel_main.addControl(checkbox_useInputDevice);

        checkbox_useArrowKeys = new GCheckbox(window_main, 10, 65, 220, 20);
        checkbox_useArrowKeys.setText("Use Arrow Keys to Fine Adjust (press SHIFT to toggle)");
        checkbox_useArrowKeys.setSelected(getSettings().isUseArrowKeys());
        //  checkbox_useArrowKeys.setBorder(0);
        //  checkbox_useArrowKeys.addEventHandler(this, "checkbox_click");
        panel_main.addControl(checkbox_useArrowKeys);

        // create buttons
        button_viewCameraSettings = new GButton(window_main, 460, 75, 120, 10, "Webcam Settings");
        //  button_viewCameraSettings.addEventHandler(this, "button_click");
        panel_main.addControl(button_viewCameraSettings);

        button_setBackground = new GButton(window_main, 110, 205, 185, 10, "Save Current Image as Background");
        //  button_setBackground.addEventHandler(this, "button_click");
        panel_main.addControl(button_setBackground);

        button_selectColor = new GButton(window_main, 110, 255, 185, 10, "Select Color to Track");
        //  button_selectColor.addEventHandler(this, "button_click");
        panel_main.addControl(button_selectColor);

        button_selectSafeColor = new GButton(window_main, 310, 200, 20, 10, "Select Safe Color  ");
        //  button_selectSafeColor.addEventHandler(this, "button_click");
        panel_main.addControl(button_selectSafeColor);

        // with image
        button_openWebsite = new GButton(window_main, 545, 560, 36, 24);
        button_openWebsite.setIcon("Sentry_Logo_Tiny.png", 1, GAlign.NORTH, GAlign.CENTER, GAlign.MIDDLE);

        //  button_openWebsite.addEventHandler(this, "button_click");
        panel_main.addControl(button_openWebsite);

        button_playRandomSound = new GButton(window_main, 320, 75, 120, 10, "Play a Random Sound");
        //  button_playRandomSound.addEventHandler(this, "button_click");
        panel_main.addControl(button_playRandomSound);

        button_saveSettings = new GButton(window_main, 320, 110, 120, 10, "Save Settings");
        //  button_saveSettings.addEventHandler(this, "button_click");
        panel_main.addControl(button_saveSettings);

        button_loadSettings = new GButton(window_main, 460, 110, 120, 10, "Re-Load Settings");
        //  button_loadSettings.addEventHandler(this, "button_click");
        panel_main.addControl(button_loadSettings);

        button_saveAndExit = new GButton(window_main, 10, 95, 280, 40, "Save Settings & Exit");
        //  button_saveAndExit.addEventHandler(this, "button_click");
        panel_main.addControl(button_saveAndExit);

        button_retryArduinoConnect = new GButton(window_main, 460, 500, 120, 10, "Retry/Connect");
        //  button_retryArduinoConnect.addEventHandler(this, "button_click");
        panel_main.addControl(button_retryArduinoConnect);

        button_configJoystick = new GButton(window_main, 200, 45, 70, 10, "Configure");
        //  button_configJoystick.addEventHandler(this, "button_click");
        panel_main.addControl(button_configJoystick);

        button_resetCalibration = new GButton(window_main, 10, 425, 220, 10, "Reset Calibration");
        //  button_resetCalibration.addEventHandler(this, "button_click");
        panel_main.addControl(button_resetCalibration);

        button_flipX = new GButton(window_main, 240, 370, 40, 10, "Flip X");
        //  button_flipX.addEventHandler(this, "button_click");
        panel_main.addControl(button_flipX);

        button_flipY = new GButton(window_main, 240, 400, 40, 10, "Flip Y");
        //  button_flipY.addEventHandler(this, "button_click");
        panel_main.addControl(button_flipY);

        // create sliders
        slider_tolerance = new GSlider(window_main, 10, 225, 200, 20, 10.0f);
        slider_tolerance.setLimits(getSettings().getTolerance(), 0, 200);
        slider_tolerance.setShowLimits(false);
        //slider_tolerance.setShowLimits(false);
        //slider_tolerance.setShowValue(false);
        slider_tolerance.setNbrTicks(10);
        slider_tolerance.setEasing(sliderInertia);
        //  slider_tolerance.addEventHandler(this, "slider_click");
        panel_main.addControl(slider_tolerance);
        label_slider_tolerance = new GLabel(window_main, 210, 225, 150, 20, "Tolerance: ");
        //  label_slider_tolerance.setBorder(0);
        label_slider_tolerance.setOpaque(false);
        label_slider_tolerance.setLocalColorScheme(GConstants.YELLOW_SCHEME);
        panel_main.addControl(label_slider_tolerance);

        slider_trackColorTolerance = new GSlider(window_main, 10, 275, 200, 20, 10.0f);
        slider_trackColorTolerance.setLimits(getSettings().getTrackColorTolerance(), 0, 300);
        slider_trackColorTolerance.setShowLimits(false);
        slider_trackColorTolerance.setShowValue(false);
        slider_trackColorTolerance.setNbrTicks(12);
        slider_trackColorTolerance.setEasing(sliderInertia);
        //  slider_trackColorTolerance.addEventHandler(this, "slider_click");
        panel_main.addControl(slider_trackColorTolerance);
        label_slider_trackColorTolerance = new GLabel(window_main, 210, 275, 150, 20, "Tolerance: ");
        //  label_slider_trackColorTolerance.setBorder(0);
        label_slider_trackColorTolerance.setOpaque(false);
        label_slider_trackColorTolerance.setLocalColorScheme(GConstants.YELLOW_SCHEME);
        panel_main.addControl(label_slider_trackColorTolerance);

        slider_safeColorTolerance = new GSlider(window_main, 310, 230, 200, 20, 10.0f);
        slider_safeColorTolerance.setLimits(getSettings().getSafeColorTolerance(), 0, 300);
        slider_safeColorTolerance.setShowLimits(false);
        slider_safeColorTolerance.setShowValue(false);
        slider_safeColorTolerance.setNbrTicks(12);
        slider_safeColorTolerance.setEasing(sliderInertia);
        //  slider_safeColorTolerance.addEventHandler(this, "slider_click");
        panel_main.addControl(slider_safeColorTolerance);
        label_slider_safeColorTolerance = new GLabel(window_main, 510, 230, 150, 20, "Tolerance: ");
        //  label_slider_safeColorTolerance.setBorder(0);
        label_slider_safeColorTolerance.setOpaque(false);
        label_slider_safeColorTolerance.setLocalColorScheme(GConstants.YELLOW_SCHEME);
        panel_main.addControl(label_slider_safeColorTolerance);

        slider_safeColorMinSize = new GSlider(window_main, 310, 260, 200, 20, 10.0f);
        slider_safeColorMinSize.setLimits(getSettings().getSafeColorMinSize(), 0, 5000);
        slider_safeColorMinSize.setShowLimits(false);
        slider_safeColorMinSize.setShowValue(false);
        slider_safeColorMinSize.setNbrTicks(10);
        slider_safeColorMinSize.setEasing(sliderInertia);
        //  slider_safeColorMinSize.addEventHandler(this, "slider_click");
        panel_main.addControl(slider_safeColorMinSize);
        label_slider_safeColorMinSize = new GLabel(window_main, 510, 260, 150, 20, "Min Area: ");
        //  label_slider_safeColorMinSize.setBorder(0);
        label_slider_safeColorMinSize.setOpaque(false);
        label_slider_safeColorMinSize.setLocalColorScheme(GConstants.YELLOW_SCHEME);
        panel_main.addControl(label_slider_safeColorMinSize);

        slider_minBlobArea = new GSlider(window_main, 10, 175, 200, 20, 10.0f);
        slider_minBlobArea.setLimits(getSettings().getMinBlobArea(), 0, 10000);
        slider_minBlobArea.setShowLimits(false);
        slider_minBlobArea.setShowValue(false);
        slider_minBlobArea.setNbrTicks(10);
        slider_minBlobArea.setEasing(sliderInertia);
        //  slider_minBlobArea.addEventHandler(this, "slider_click");
        panel_main.addControl(slider_minBlobArea);
        label_slider_minBlobArea = new GLabel(window_main, 210, 175, 150, 20, "Min Size: ");
        //  label_slider_minBlobArea.setBorder(0);
        label_slider_minBlobArea.setOpaque(false);
        label_slider_minBlobArea.setLocalColorScheme(GConstants.YELLOW_SCHEME);
        panel_main.addControl(label_slider_minBlobArea);

        slider_nbDot = new GSlider(window_main, 310, 350, 200, 20, 10.0f);
        slider_nbDot.setLimits(getSettings().getNbDot(), 2, 22);
        slider_nbDot.setShowLimits(false);
        slider_nbDot.setShowValue(false);
        slider_nbDot.setNbrTicks(10);
        slider_nbDot.setEasing(sliderInertia);
        //  slider_nbDot.addEventHandler(this, "slider_click");
        panel_main.addControl(slider_nbDot);
        label_slider_nbDot = new GLabel(window_main, 510, 350, 150, 20, "Memory: ");
        //  label_slider_nbDot.setBorder(0);
        label_slider_nbDot.setOpaque(false);
        label_slider_nbDot.setLocalColorScheme(GConstants.YELLOW_SCHEME);
        panel_main.addControl(label_slider_nbDot);

        slider_antSens = new GSlider(window_main, 310, 375, 200, 20, 10.0f);
        slider_antSens.setLimits(getSettings().getAntSens(), 1, 100);
        slider_antSens.setShowLimits(false);
        slider_antSens.setShowValue(false);
        slider_antSens.setNbrTicks(10);
        slider_antSens.setEasing(sliderInertia);
        //  slider_antSens.addEventHandler(this, "slider_click");
        panel_main.addControl(slider_antSens);
        label_slider_antSens = new GLabel(window_main, 510, 375, 150, 20, "Sensitivity: ");
        //  label_slider_antSens.setBorder(0);
        label_slider_antSens.setOpaque(false);
        label_slider_antSens.setLocalColorScheme(GConstants.YELLOW_SCHEME);
        panel_main.addControl(label_slider_antSens);

        slider_propX = new GSlider(window_main, 310, 400, 120, 20, 10.0f);
        slider_propX.setLimits(getSettings().getPropX(), 0.00f, 3.00f);
        slider_propX.setNumberFormat(GConstants.DECIMAL, 2);
        slider_propX.setShowLimits(false);
        slider_propX.setShowValue(false);
        slider_propX.setNbrTicks(7);
        slider_propX.setEasing(sliderInertia);
        //  slider_propX.addEventHandler(this, "slider_click");
        panel_main.addControl(slider_propX);
        label_slider_propX = new GLabel(window_main, 430, 400, 300, 20, "X Degree of Anticipation: ");
        //  label_slider_propX.setBorder(0);
        label_slider_propX.setOpaque(false);
        label_slider_propX.setLocalColorScheme(GConstants.YELLOW_SCHEME);
        panel_main.addControl(label_slider_propX);

        slider_propY = new GSlider(window_main, 310, 425, 120, 20, 10.0f);
        slider_propY.setLimits(getSettings().getPropY(), 0.00f, 3.00f);
        slider_propY.setNumberFormat(GConstants.DECIMAL, 2);
        slider_propY.setShowLimits(false);
        slider_propY.setShowValue(false);
        slider_propY.setNbrTicks(7);
        slider_propY.setEasing(sliderInertia);
        //  slider_propY.addEventHandler(this, "slider_click");
        panel_main.addControl(slider_propY);
        label_slider_propY = new GLabel(window_main, 430, 425, 300, 20, "Y Degree of Anticipation: ");
        //  label_slider_propY.setBorder(0);
        label_slider_propY.setOpaque(false);
        label_slider_propY.setLocalColorScheme(GConstants.YELLOW_SCHEME);
        panel_main.addControl(label_slider_propY);

        slider_smoothingFactor = new GSlider(window_main, 310, 50, 180, 20, 10.0f);
        slider_smoothingFactor.setLimits(getSettings().getSmoothingFactor(), 0.00f, 1.00f);
        slider_smoothingFactor.setNumberFormat(GConstants.DECIMAL, 2);
        slider_smoothingFactor.setShowLimits(false);
        slider_smoothingFactor.setShowValue(false);
        slider_smoothingFactor.setNbrTicks(10);
        slider_smoothingFactor.setEasing(sliderInertia);
        //  slider_smoothingFactor.addEventHandler(this, "slider_click");
        panel_main.addControl(slider_smoothingFactor);
        label_smoothingFactor = new GLabel(window_main, 490, 50, 100, 20, "Smoothing Factor");
        //  label_smoothingFactor.setBorder(0);
        label_smoothingFactor.setOpaque(false);
        label_smoothingFactor.setLocalColorScheme(GConstants.YELLOW_SCHEME);
        panel_main.addControl(label_smoothingFactor);

        // createCombos (dropdown boxes)
        final String[] entries_1 = Effects.getDropdownStrings().toArray(new String[0]);
        //new String[0] {"Opaque", "Transparent", "Negative", "Negative & Transparent" };
        dropdown_effect = new GDropList(window_main, 140, 500, 140, 76, entries_1.length);
        dropdown_effect.setItems(entries_1, 0); // this, String[] of entries, dropdown # of enteries shown at once, xPosition, yPosition, width
        dropdown_effect.setSelected(getSettings().getEffect().ordinal()); // which entry to show as selected (first entry is 0, second is 1, etc.)
        //      panelMain.add(dropdown_effect);
        //        dropdown_effect.setSelected(effect);                                                  // which entry to show as selected (first entry is 0, second is 1, etc.)
        //  dropdown_effect.addEventHandler(this, "dropdown_click");
        panel_main.addControl(dropdown_effect);

        //        String[] entries_2 = new String[] {
        //          "Automatic", "Semi-Auto"
        //        };
        final String[] entries_2 = FiringMode.getDropdownStrings().toArray(new String[0]);
        dropdown_firingMode = new GDropList(window_main, 140, 320, 100, 76, entries_2.length);
        //      //new String[] { "Automatic", "Semi-Auto" };
        //      dropdown_firingMode = new GCombo(getParent(), entries_2, entries_2.length, 140, 320, 100); // getParent(), String[] of entries, drop down # of entries shown at once, xPosition, yPosition, width
        //      dropdown_firingMode.setSelected(getSettings().getFiringModeIndex()); // which entry to show as selected (first entry is 0, second is 1, etc.) enum ordinal takes care of that
        //      panelMain.add(dropdown_firingMode);
        dropdown_firingMode.setItems(entries_2, 0); // this, String[] of entries, dropdown # of enteries shown at once, xPosition, yPosition, width
        dropdown_firingMode.setSelected(getSettings().getFiringMode().ordinal()); // which entry to show as selected (first entry is 0, second is 1, etc.) enum ordinal takes care of that
        //       dropdown_firingMode.setSelected(int(firingMode));                                                  // which entry to show as selected (first entry is 0, second is 1, etc.)
        //  dropdown_firingMode.addEventHandler(this, "dropdown_click");
        panel_main.addControl(dropdown_firingMode);

        if (Serial.list().length > 0) {
            final String[] entries_3 = PApplet.append(Serial.list(), "Select to Override");
            dropdown_comPort = new GDropList(window_main, 460, 520, 120, 76, entries_3.length);
            dropdown_comPort.setItems(entries_3, 0);
            dropdown_comPort.setSelected(entries_3.length - 1);
            //          dropdown_comPort.addEventHandler(this, "dropdown_click");
            panel_main.addControl(dropdown_comPort);
        }

        // Enable mouse over image changes
        // G4P.setMouseOverEnabled(true);

        //panelBackgroundImg = loadImage("Panel_Background.png");
        //surface.setTitle("Control Panel");
        //// create new window  (tag frame)
        //// window_main = new GWindow(this, "Control Panel", controlPanelWindowX, controlPanelWindowY, panelBackgroundImg, true, null);
        ////  window_main.setBackground(180);
        //// window_main.setOnTop(false);
        ////window_main.addControl(panelMain);
        //window_main.addDrawHandler(this, "drawController");
        //panelMain.setXY(0, 0);
    }

    //    void drawControlPanel() {
    //
    //        G4P.setColorScheme(getParent(), GCScheme.GREY_SCHEME);
    //        G4P.messagesEnabled(false);
    //
    //        // create Panels
    //        panelMain = new GPanel(getParent(), "Main", 0, 0, 600, 600);
    //        panelMain.setOpaque(false);
    //        panelMain.setCollapsed(false);
    //
    //        // create labels
    //        label_serialOut = new GLabel(getParent(), "Serial Out:           ", 300, 475, 150, 20);
    //        label_serialOut.setBorder(0);
    //        label_serialOut.setOpaque(false);
    //        label_serialOut.setColorScheme(GCScheme.GREY_SCHEME);
    //        panelMain.add(label_serialOut);
    //
    //        label_targetX = new GLabel(getParent(), "Pan Servo Position:    ", 300, 495, 150, 20);
    //        label_targetX.setBorder(0);
    //        label_targetX.setOpaque(false);
    //        label_targetX.setColorScheme(GCScheme.GREY_SCHEME);
    //        panelMain.add(label_targetX);
    //
    //        label_targetY = new GLabel(getParent(), "Tilt Servo Position:    ", 300, 515, 150, 20);
    //        label_targetY.setBorder(0);
    //        label_targetY.setOpaque(false);
    //        label_targetY.setColorScheme(GCScheme.GREY_SCHEME);
    //        panelMain.add(label_targetY);
    //
    //        label_fire = new GLabel(getParent(), "Not Firing", 300, 535, 150, 20);
    //        label_fire.setBorder(0);
    //        label_fire.setOpaque(false);
    //        label_fire.setColorScheme(GConstants.RED_SCHEME);
    //        panelMain.add(label_fire);
    //
    //        label_fireSelector = new GLabel(getParent(), "Automatic", 300, 555, 150, 20);
    //        label_fireSelector.setBorder(0);
    //        label_fireSelector.setOpaque(false);
    //        label_fireSelector.setColorScheme(GCScheme.GREY_SCHEME);
    //        panelMain.add(label_fireSelector);
    //
    //        label_scanSelector = new GLabel(getParent(), "Scan When Idle", 300, 575, 150, 20);
    //        label_scanSelector.setBorder(0);
    //        label_scanSelector.setOpaque(false);
    //        label_scanSelector.setColorScheme(GCScheme.GREY_SCHEME);
    //        panelMain.add(label_scanSelector);
    //
    //        label_runWithoutArduino = new GLabel(getParent(), "No Controller", 460, 475, 120, 20);
    //        label_runWithoutArduino.setBorder(0);
    //        label_runWithoutArduino.setOpaque(false);
    //        label_runWithoutArduino.setColorScheme(GConstants.YELLOW_SCHEME);
    //        panelMain.add(label_runWithoutArduino);
    //
    //        label_xMin = new GLabel(getParent(), "xMin: 000", 35, 362, 150, 20);
    //        label_xMin.setBorder(0);
    //        label_xMin.setOpaque(false);
    //        label_xMin.setColorScheme(GConstants.YELLOW_SCHEME);
    //        panelMain.add(label_xMin);
    //
    //        label_xMax = new GLabel(getParent(), "xMax: 180", 145, 362, 150, 20);
    //        label_xMax.setBorder(0);
    //        label_xMax.setOpaque(false);
    //        label_xMax.setColorScheme(GConstants.YELLOW_SCHEME);
    //        panelMain.add(label_xMax);
    //
    //        label_yMin = new GLabel(getParent(), "yMin: 000", 35, 392, 150, 20);
    //        label_yMin.setBorder(0);
    //        label_yMin.setOpaque(false);
    //        label_yMin.setColorScheme(GConstants.YELLOW_SCHEME);
    //        panelMain.add(label_yMin);
    //
    //        label_yMax = new GLabel(getParent(), "yMax: 180", 145, 392, 150, 20);
    //        label_yMax.setBorder(0);
    //        label_yMax.setOpaque(false);
    //        label_yMax.setColorScheme(GConstants.YELLOW_SCHEME);
    //        panelMain.add(label_yMax);
    //
    //        label_setxMin = new GLabel(getParent(), "to set xMin press A", 10, 375, 120, 10);
    //        panelMain.add(label_setxMin);
    //
    //        label_setxMax = new GLabel(getParent(), "to set xMax press D", 120, 375, 120, 10);
    //        panelMain.add(label_setxMax);
    //
    //        label_setyMin = new GLabel(getParent(), "to set yMin press S", 10, 405, 120, 10);
    //        panelMain.add(label_setyMin);
    //
    //        label_setyMax = new GLabel(getParent(), "to set yMax press W", 120, 405, 120, 10);
    //        panelMain.add(label_setyMax);
    //
    //        // create checkboxes
    //        checkbox_leadTarget = new GCheckbox(getParent(), "Enable Target Anticipation", 310, 325, 10);
    //        checkbox_leadTarget.setSelected(getSettings().isLeadTarget());
    //        checkbox_leadTarget.setBorder(0);
    //        panelMain.add(checkbox_leadTarget);
    //
    //        checkbox_showRestrictedZones =
    //            new GCheckbox(getParent(), "Show Restricted Zones  (to set, hold R and click+drag)", 10, 480, 10);
    //        checkbox_showRestrictedZones.setSelected(getSettings().isShowRestrictedZones());
    //        checkbox_showRestrictedZones.setBorder(0);
    //        panelMain.add(checkbox_showRestrictedZones);
    //
    //        checkbox_trackingColor = new GCheckbox(getParent(), "Track A Color", 10, 255, 10);
    //        checkbox_trackingColor.setSelected(getSettings().isTrackingColor());
    //        checkbox_trackingColor.setBorder(0);
    //        panelMain.add(checkbox_trackingColor);
    //
    //        checkbox_safeColor = new GCheckbox(getParent(), "Enable Safe Color", 310, 175, 10);
    //        checkbox_safeColor.setSelected(getSettings().isUseSafeColor());
    //        checkbox_safeColor.setBorder(0);
    //        panelMain.add(checkbox_safeColor);
    //
    //        checkbox_trackingMotion = new GCheckbox(getParent(), "Track Motion", 10, 205, 10);
    //        checkbox_trackingMotion.setSelected(getSettings().isTrackingMotion());
    //        checkbox_trackingMotion.setBorder(0);
    //        panelMain.add(checkbox_trackingMotion);
    //
    //        checkbox_showDifferentPixels = new GCheckbox(getParent(), "Show Different Pixels", 10, 500, 10);
    //        checkbox_showDifferentPixels.setSelected(getSettings().isShowDifferentPixels());
    //        checkbox_showDifferentPixels.setBorder(0);
    //        panelMain.add(checkbox_showDifferentPixels);
    //
    //        checkbox_showTargetBox = new GCheckbox(getParent(), "Show Target Box", 10, 520, 10);
    //        checkbox_showTargetBox.setSelected(getSettings().isShowTargetBox());
    //        checkbox_showTargetBox.setBorder(0);
    //        panelMain.add(checkbox_showTargetBox);
    //
    //        checkbox_mirrorCam = new GCheckbox(getParent(), "Mirror Webcam", 10, 540, 10);
    //        checkbox_mirrorCam.setSelected(getSettings().isMirrorCam());
    //        checkbox_mirrorCam.setBorder(0);
    //        panelMain.add(checkbox_mirrorCam);
    //
    //        checkbox_controlMode = new GCheckbox(getParent(), "Enable Autonomous Mode (press SPACE to toggle)", 10, 25, 10);
    //        checkbox_controlMode.setSelected(getSettings().getControlMode() == ControlMode.Autonomous);
    //        checkbox_controlMode.setBorder(0);
    //        panelMain.add(checkbox_controlMode);
    //
    //        checkbox_safety = new GCheckbox(getParent(), "Enable Weapon", 10, 325, 10);
    //        checkbox_safety.setSelected(getSettings().isSafety());
    //        checkbox_safety.setBorder(0);
    //        panelMain.add(checkbox_safety);
    //
    //        checkbox_showCameraView = new GCheckbox(getParent(), "Show Camera View", 10, 560, 10);
    //        checkbox_showCameraView.setSelected(getSettings().isShowCameraView());
    //        checkbox_showCameraView.setBorder(0);
    //        panelMain.add(checkbox_showCameraView);
    //
    //        checkbox_scanWhenIdle = new GCheckbox(getParent(), "Scan When Idle", 10, 345, 10);
    //        checkbox_scanWhenIdle.setSelected(getSettings().isScanWhenIdle());
    //        checkbox_scanWhenIdle.setBorder(0);
    //        panelMain.add(checkbox_scanWhenIdle);
    //
    //        checkbox_soundEffects = new GCheckbox(getParent(), "Enable Sounds Effects", 315, 25, 10);
    //        checkbox_soundEffects.setSelected(getSettings().isSoundEffects());
    //        checkbox_soundEffects.setBorder(0);
    //        panelMain.add(checkbox_soundEffects);
    //
    //        checkbox_activeSmoothing = new GCheckbox(getParent(), "Smoothing", 460, 25, 10);
    //        checkbox_activeSmoothing.setSelected(getSettings().isActiveSmoothing());
    //        checkbox_activeSmoothing.setBorder(0);
    //        panelMain.add(checkbox_activeSmoothing);
    //
    //        checkbox_useInputDevice = new GCheckbox(getParent(), "Use Joystick/Game Controller Input", 10, 45, 10);
    //        checkbox_useInputDevice.setSelected(getSettings().getControls() == Controls.Device);
    //        checkbox_useInputDevice.setBorder(0);
    //        panelMain.add(checkbox_useInputDevice);
    //
    //        checkbox_useArrowKeys =
    //            new GCheckbox(getParent(), "Use Arrow Keys to Fine Adjust (press SHIFT to toggle)", 10, 65, 10);
    //        checkbox_useArrowKeys.setSelected(getSettings().isUseArrowKeys());
    //        checkbox_useArrowKeys.setBorder(0);
    //        panelMain.add(checkbox_useArrowKeys);
    //
    //        // create buttons
    //        button_viewCameraSettings = new GButton(getParent(), "Webcam Settings", 460, 75, 120, 10);
    //        panelMain.add(button_viewCameraSettings);
    //
    //        button_setBackground = new GButton(getParent(), "Save Current Image as deltaBackground", 110, 205, 185, 10);
    //        panelMain.add(button_setBackground);
    //
    //        button_selectColor = new GButton(getParent(), "Select Color to Track", 110, 255, 185, 10);
    //        panelMain.add(button_selectColor);
    //
    //        button_selectSafeColor = new GButton(getParent(), "Select Safe Color  ", 310, 200, 20, 10);
    //        panelMain.add(button_selectSafeColor);
    //
    //        // with image
    //        button_openWebsite = new GButton(getParent(), "", "Sentry_Logo_Tiny.png", 1, 545, 560, 36, 24);
    //        panelMain.add(button_openWebsite);
    //
    //        button_playRandomSound = new GButton(getParent(), "Play a Random Sound", 320, 75, 120, 10);
    //        panelMain.add(button_playRandomSound);
    //
    //        button_saveSettings = new GButton(getParent(), "Save Settings", 320, 110, 120, 10);
    //        panelMain.add(button_saveSettings);
    //
    //        button_loadSettings = new GButton(getParent(), "Re-Load Settings", 460, 110, 120, 10);
    //        panelMain.add(button_loadSettings);
    //
    //        button_saveAndExit = new GButton(getParent(), "Save Settings & Exit", 10, 95, 280, 40);
    //        panelMain.add(button_saveAndExit);
    //
    //        button_retryArduinoConnect = new GButton(getParent(), "Retry/Connect", 460, 500, 120, 10);
    //        panelMain.add(button_retryArduinoConnect);
    //
    //        button_configJoystick = new GButton(getParent(), "Configure", 200, 45, 70, 10);
    //        panelMain.add(button_configJoystick);
    //
    //        button_resetCalibration = new GButton(getParent(), "Reset Calibration", 10, 425, 220, 10);
    //        panelMain.add(button_resetCalibration);
    //
    //        button_flipX = new GButton(getParent(), "Flip X", 240, 370, 40, 10);
    //        panelMain.add(button_flipX);
    //
    //        button_flipY = new GButton(getParent(), "Flip Y", 240, 400, 40, 10);
    //        panelMain.add(button_flipY);
    //
    //        // create sliders
    //        slider_tolerance = new GWSlider(getParent(), 10, 225, 200);
    //        slider_tolerance.setLimits(getSettings().getTolerance(), 0, 200);
    //        slider_tolerance.setRenderMaxMinLabel(false);
    //        slider_tolerance.setRenderValueLabel(false);
    //        slider_tolerance.setTickCount(10);
    //        slider_tolerance.setInertia(sliderInertia);
    //        panelMain.add(slider_tolerance);
    //        label_slider_tolerance = new GLabel(getParent(), "Tolerance: ", 210, 225, 150, 20);
    //        label_slider_tolerance.setBorder(0);
    //        label_slider_tolerance.setOpaque(false);
    //        label_slider_tolerance.setColorScheme(GConstants.YELLOW_SCHEME);
    //        panelMain.add(label_slider_tolerance);
    //
    //        slider_trackColorTolerance = new GWSlider(getParent(), 10, 275, 200);
    //        slider_trackColorTolerance.setLimits(getSettings().getTrackColorTolerance(), 0, 300);
    //        slider_trackColorTolerance.setRenderMaxMinLabel(false);
    //        slider_trackColorTolerance.setRenderValueLabel(false);
    //        slider_trackColorTolerance.setTickCount(12);
    //        slider_trackColorTolerance.setInertia(sliderInertia);
    //        panelMain.add(slider_trackColorTolerance);
    //        label_slider_trackColorTolerance = new GLabel(getParent(), "Tolerance: ", 210, 275, 150, 20);
    //        label_slider_trackColorTolerance.setBorder(0);
    //        label_slider_trackColorTolerance.setOpaque(false);
    //        label_slider_trackColorTolerance.setColorScheme(GConstants.YELLOW_SCHEME);
    //        panelMain.add(label_slider_trackColorTolerance);
    //
    //        slider_safeColorTolerance = new GWSlider(getParent(), 310, 230, 200);
    //        slider_safeColorTolerance.setLimits(getSettings().getSafeColorTolerance(), 0, 300);
    //        slider_safeColorTolerance.setRenderMaxMinLabel(false);
    //        slider_safeColorTolerance.setRenderValueLabel(false);
    //        slider_safeColorTolerance.setTickCount(12);
    //        slider_safeColorTolerance.setInertia(sliderInertia);
    //        panelMain.add(slider_safeColorTolerance);
    //        label_slider_safeColorTolerance = new GLabel(getParent(), "Tolerance: ", 510, 230, 150, 20);
    //        label_slider_safeColorTolerance.setBorder(0);
    //        label_slider_safeColorTolerance.setOpaque(false);
    //        label_slider_safeColorTolerance.setColorScheme(GConstants.YELLOW_SCHEME);
    //        panelMain.add(label_slider_safeColorTolerance);
    //
    //        slider_safeColorMinSize = new GWSlider(getParent(), 310, 260, 200);
    //        slider_safeColorMinSize.setLimits(getSettings().getSafeColorMinSize(), 0, 5000);
    //        slider_safeColorMinSize.setRenderMaxMinLabel(false);
    //        slider_safeColorMinSize.setRenderValueLabel(false);
    //        slider_safeColorMinSize.setTickCount(10);
    //        slider_safeColorMinSize.setInertia(sliderInertia);
    //        panelMain.add(slider_safeColorMinSize);
    //        label_slider_safeColorMinSize = new GLabel(getParent(), "Min Area: ", 510, 260, 150, 20);
    //        label_slider_safeColorMinSize.setBorder(0);
    //        label_slider_safeColorMinSize.setOpaque(false);
    //        label_slider_safeColorMinSize.setColorScheme(GConstants.YELLOW_SCHEME);
    //        panelMain.add(label_slider_safeColorMinSize);
    //
    //        slider_minBlobArea = new GWSlider(getParent(), 10, 175, 200);
    //        slider_minBlobArea.setLimits(getSettings().getMinBlobArea(), 0, 10000);
    //        slider_minBlobArea.setRenderMaxMinLabel(false);
    //        slider_minBlobArea.setRenderValueLabel(false);
    //        slider_minBlobArea.setTickCount(10);
    //        slider_minBlobArea.setInertia(sliderInertia);
    //        panelMain.add(slider_minBlobArea);
    //        label_slider_minBlobArea = new GLabel(getParent(), "Min Size: ", 210, 175, 150, 20);
    //        label_slider_minBlobArea.setBorder(0);
    //        label_slider_minBlobArea.setOpaque(false);
    //        label_slider_minBlobArea.setColorScheme(GConstants.YELLOW_SCHEME);
    //        panelMain.add(label_slider_minBlobArea);
    //
    //        slider_nbDot = new GWSlider(getParent(), 310, 350, 200);
    //        slider_nbDot.setLimits(getSettings().getNbDot(), 2, 22);
    //        slider_nbDot.setRenderMaxMinLabel(false);
    //        slider_nbDot.setRenderValueLabel(false);
    //        slider_nbDot.setTickCount(10);
    //        slider_nbDot.setInertia(sliderInertia);
    //        panelMain.add(slider_nbDot);
    //        label_slider_nbDot = new GLabel(getParent(), "Memory: ", 510, 350, 150, 20);
    //        label_slider_nbDot.setBorder(0);
    //        label_slider_nbDot.setOpaque(false);
    //        label_slider_nbDot.setColorScheme(GConstants.YELLOW_SCHEME);
    //        panelMain.add(label_slider_nbDot);
    //
    //        slider_antSens = new GWSlider(getParent(), 310, 375, 200);
    //        slider_antSens.setLimits(getSettings().getAntSens(), 1, 100);
    //        slider_antSens.setRenderMaxMinLabel(false);
    //        slider_antSens.setRenderValueLabel(false);
    //        slider_antSens.setTickCount(10);
    //        slider_antSens.setInertia(sliderInertia);
    //        panelMain.add(slider_antSens);
    //        label_slider_antSens = new GLabel(getParent(), "Sensitivity: ", 510, 375, 150, 20);
    //        label_slider_antSens.setBorder(0);
    //        label_slider_antSens.setOpaque(false);
    //        label_slider_antSens.setColorScheme(GConstants.YELLOW_SCHEME);
    //        panelMain.add(label_slider_antSens);
    //
    //        slider_propX = new GWSlider(getParent(), 310, 400, 120);
    //        slider_propX.setLimits(getSettings().getPropX(), 0.00f, 3.00f);
    //        slider_propX.setValueType(GConstants.DECIMAL);
    //        slider_propX.setRenderMaxMinLabel(false);
    //        slider_propX.setRenderValueLabel(false);
    //        slider_propX.setTickCount(7);
    //        slider_propX.setInertia(sliderInertia);
    //        panelMain.add(slider_propX);
    //        label_slider_propX = new GLabel(getParent(), "X Degree of Anticipation: ", 430, 400, 300, 20);
    //        label_slider_propX.setBorder(0);
    //        label_slider_propX.setOpaque(false);
    //        label_slider_propX.setColorScheme(GConstants.YELLOW_SCHEME);
    //        panelMain.add(label_slider_propX);
    //
    //        slider_propY = new GWSlider(getParent(), 310, 425, 120);
    //        slider_propY.setLimits(getSettings().getPropY(), 0.00f, 3.00f);
    //        slider_propY.setValueType(GConstants.DECIMAL);
    //        slider_propY.setRenderMaxMinLabel(false);
    //        slider_propY.setRenderValueLabel(false);
    //        slider_propY.setTickCount(7);
    //        slider_propY.setInertia(sliderInertia);
    //        panelMain.add(slider_propY);
    //        label_slider_propY = new GLabel(getParent(), "Y Degree of Anticipation: ", 430, 425, 300, 20);
    //        label_slider_propY.setBorder(0);
    //        label_slider_propY.setOpaque(false);
    //        label_slider_propY.setColorScheme(GConstants.YELLOW_SCHEME);
    //        panelMain.add(label_slider_propY);
    //
    //        slider_smoothingFactor = new GWSlider(getParent(), 310, 50, 180);
    //        slider_smoothingFactor.setLimits(getSettings().getSmoothingFactor(), 0.00f, 1.00f);
    //        slider_smoothingFactor.setValueType(GConstants.DECIMAL);
    //        slider_smoothingFactor.setRenderMaxMinLabel(false);
    //        slider_smoothingFactor.setRenderValueLabel(false);
    //        slider_smoothingFactor.setTickCount(10);
    //        slider_smoothingFactor.setInertia(sliderInertia);
    //        panelMain.add(slider_smoothingFactor);
    //        label_smoothingFactor = new GLabel(getParent(), "Smoothing Factor", 490, 50, 100, 20);
    //        label_smoothingFactor.setBorder(0);
    //        label_smoothingFactor.setOpaque(false);
    //        label_smoothingFactor.setColorScheme(GConstants.YELLOW_SCHEME);
    //        panelMain.add(label_smoothingFactor);
    //
    //        // createCombos (dropdown boxes)
    //
    //        final String[] entries_1 = Effects.getDropdownStrings().toArray(new String[0]);
    //        //new String[0] {"Opaque", "Transparent", "Negative", "Negative & Transparent" };
    //        dropdown_effect = new GCombo(getParent(), entries_1, entries_1.length, 140, 500, 140); // getParent(), String[] of entries, dropdown # of enteries shown at once, xPosition, yPosition, width
    //        dropdown_effect.setSelected(getSettings().getEffect().getId()); // which entry to show as selected (first entry is 0, second is 1, etc.)
    //        panelMain.add(dropdown_effect);
    //
    //        final String[] entries_2 = FiringMode.getDropdownStrings().toArray(new String[0]);
    //        //new String[] { "Automatic", "Semi-Auto" };
    //        dropdown_firingMode = new GCombo(getParent(), entries_2, entries_2.length, 140, 320, 100); // getParent(), String[] of entries, drop down # of entries shown at once, xPosition, yPosition, width
    //        dropdown_firingMode.setSelected(getSettings().getFiringModeIndex()); // which entry to show as selected (first entry is 0, second is 1, etc.) enum ordinal takes care of that
    //        panelMain.add(dropdown_firingMode);
    //
    //        if (Serial.list().length > 0) {
    //            getParent();
    //            final String[] entries_3 = PApplet.append(Serial.list(), "Select to Override");
    //            dropdown_comPort = new GCombo(getParent(), entries_3, entries_3.length, 460, 520, 120);
    //            dropdown_comPort.setSelected(entries_3.length - 1);
    //            panelMain.add(dropdown_comPort);
    //        }
    //
    //        // Enable mouse over image changes
    //        G4P.setMouseOverEnabled(true);
    //
    //        panelBackgroundImg = loadImage("Panel_Background.png");
    //        // create new window  (tag frame)
    //        window_main = new GWindow(getParent(), "Control Panel", controlPanelWindowX, controlPanelWindowY,
    //            panelBackgroundImg, true, null);
    //        //  window_main.setBackground(180);
    //        window_main.setOnTop(false);
    //        window_main.add(panelMain);
    //        window_main.addDrawHandler(getParent(), "drawController");
    //        panelMain.setXY(0, 0);
    //    }

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

    /** {@inheritDoc} */
    @Override
    public RuntimeSettings getRuntimeSettings() {
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
        return sounds;
    }

    public void handleButtonEvents(final GButton button, final GEvent event) {
        //   public void handleButtonEvents(final GButton control) {
        if (button == button_viewCameraSettings && event == GEvent.CLICKED) {
            viewCameraSettings();
        }
        if (button == button_setBackground && event == GEvent.CLICKED) {
            setBackground();
        }
        if (button == button_selectColor && event == GEvent.CLICKED) {
            getRuntimeSettings().setSelectingColor(true);
        }
        if (button == button_selectSafeColor && event == GEvent.CLICKED) {
            getRuntimeSettings().setSelectingSafeColor(true);

        }
        if (button == button_openWebsite && event == GEvent.CLICKED) {
            openWebsite();
        }
        if (button == button_playRandomSound && event == GEvent.CLICKED) {
            playRandomSound();
        }
        if (button == button_saveSettings && event == GEvent.CLICKED) {
            try {
                getSettings().saveSettings();
            } catch (final IOException ex) {
                // TODO Auto-generated catch block
                log.error("caught IOException :", ex); //$NON-NLS-1$
            }
        }
        if (button == button_loadSettings && event == GEvent.CLICKED) {
            try {
                //TODO add event for refresh of settings
                getSettings().loadSettings();
            } catch (final IOException ex) {
                log.error("caught IOException :", ex); //$NON-NLS-1$
            }
        }
        if (button == button_saveAndExit && event == GEvent.CLICKED) {
            try {
                getSettings().saveSettings();
            } catch (final IOException ex1) {
                // TODO Auto-generated catch block
                log.error("caught IOException :", ex1); //$NON-NLS-1$
            }
            delay(100);
            try {
                getSounds().close();// as per Minim requirement when running outside of the Processing environment
            } catch (final Exception ex) {
                log.error("caught Exception :", ex); //$NON-NLS-1$
            }
            exit();
        }
        if (button == button_retryArduinoConnect && event == GEvent.CLICKED) {
            if (!getSettings().isRunWithoutArduino()) {
                //arduinoPort.stop(); don't need it the clean up, happens in the setter
            }
            getSettings().setRunWithoutArduino(false);
            ((PSGProcessingCode) getParent()).retryArduinoConnect();
        }
        if (button == button_configJoystick && event == GEvent.CLICKED) {
            configJoystick();
        }
        if (button == button_resetCalibration && event == GEvent.CLICKED) {
            getSettings().setxMin(0.0f);
            getSettings().setxMax(180.0f);
            getSettings().setyMin(0.0f);
            getSettings().setyMax(180);
            //xRatio = camWidth / (xMax - xMin); //dynamically recalculated in the  getSettings().getxRatio()
            //yRatio = camHeight / (yMax - yMin);//dynamically recalculated in the  getSettings().getyRatio()
        }
        if (button == button_flipX && event == GEvent.CLICKED) {
            getSettings().flipxMinxMax();
            //xRatio = camWidth / (xMax - xMin);
        }
        if (button == button_flipY && event == GEvent.CLICKED) {
            getSettings().flipyMinyMax();
            //            final float oldyMin = yMin;
            //            final float oldyMax = yMax;
            //            yMin = oldyMax;
            //            yMax = oldyMin;
            //            yRatio = camHeight / (yMax - yMin);
        }
    }

    //    public void handleComboEvents(final GCombo combo) {
    public void handleDropListEvents(final GDropList combo, final GEvent event) {
        if (combo == dropdown_effect) {
            getSettings().setEffect(Effects.getByOrdinal(dropdown_effect.getSelectedIndex()));
        }
        if (combo == dropdown_firingMode) {
            getSettings().setFiringMode(FiringMode.getByOrdinal(dropdown_firingMode.getSelectedIndex()));
        }
        if (combo == dropdown_comPort) {
            if (dropdown_comPort.getSelectedIndex() < Serial.list().length) {
                if (getSettings().isRunWithoutArduino()) {
                    getRuntimeSettings().setConnecting(true);
                    log.info("Manual override. Stopping old Serial connection...");
                    getRuntimeSettings().setArduinoPort(null); //clean up is done in setter
                    log.info("Stopped old serial connection.");
                }
                final String port = Serial.list()[dropdown_comPort.getSelectedIndex()];
                log.info("New COM port selected manually: {}", port);
                getRuntimeSettings().setArduinoPort(new Serial(getParent(), port, 4800));
                log.info("Serial Port used = {}" + port);
                getRuntimeSettings().setSerPortUsed(port);
                getSettings().setRunWithoutArduino(false);
                getRuntimeSettings().setConnecting(false);
            }
        }
    }

    public void handleSliderEvents(final GValueControl slider, final GEvent event) {
        //    public void handleSliderEvents(final GSlider slider) {
        if (slider == slider_tolerance) {
            getSettings().setTolerance(slider_tolerance.getValueI());
        }
        if (slider == slider_trackColorTolerance) {
            getSettings().setTrackColorTolerance(slider_trackColorTolerance.getValueI());
        }
        if (slider == slider_safeColorTolerance) {
            getSettings().setSafeColorTolerance(slider_safeColorTolerance.getValueI());
        }
        if (slider == slider_minBlobArea) {
            getSettings().setMinBlobArea(slider_minBlobArea.getValueI());
        }
        if (slider == slider_safeColorMinSize) {
            getSettings().setSafeColorMinSize(slider_safeColorMinSize.getValueI());
        }
        if (slider == slider_nbDot) {
            getSettings().setNbDot(slider_nbDot.getValueI());
        }
        if (slider == slider_antSens) {
            getSettings().setAntSens(slider_antSens.getValueI());
        }
        if (slider == slider_propX) {
            getSettings().setPropX(slider_propX.getValueF());
        }
        if (slider == slider_propY) {
            getSettings().setPropY(slider_propY.getValueF());
        }
        if (slider == slider_smoothingFactor) {
            getSettings().setSmoothingFactor(slider_smoothingFactor.getValueF());
        }
    }

    public void handleToggleControlEvents(final GToggleControl cbox, final GEvent event) {
        //    public void handleCheckboxEvents(final GCheckbox cbox) {
        if (cbox == checkbox_leadTarget) {
            getSettings().setLeadTarget(checkbox_leadTarget.isSelected());
        }
        if (cbox == checkbox_showRestrictedZones) {
            getSettings().setShowRestrictedZones(checkbox_showRestrictedZones.isSelected());
        }
        if (cbox == checkbox_trackingColor) {
            getSettings().setTrackingColor(checkbox_trackingColor.isSelected());
        }
        if (cbox == checkbox_safeColor) {
            getSettings().setUseSafeColor(checkbox_safeColor.isSelected());
        }
        if (cbox == checkbox_trackingMotion) {
            getSettings().setTrackingMotion(checkbox_trackingMotion.isSelected());
        }
        if (cbox == checkbox_showDifferentPixels) {
            getSettings().setShowDifferentPixels(checkbox_showDifferentPixels.isSelected());
        }
        if (cbox == checkbox_showTargetBox) {
            getSettings().setShowTargetBox(checkbox_showTargetBox.isSelected());
        }
        if (cbox == checkbox_mirrorCam) {
            getSettings().setMirrorCam(checkbox_mirrorCam.isSelected());
        }
        if (cbox == checkbox_controlMode) {
            getSettings()
                .setControlMode(checkbox_controlMode.isSelected() ? ControlMode.Autonomous : ControlMode.Manual);
        }
        if (cbox == checkbox_safety) {
            getSettings().setSafety(checkbox_safety.isSelected());
        }
        if (cbox == checkbox_showCameraView) {
            getSettings().setShowCameraView(checkbox_showCameraView.isSelected());
        }
        if (cbox == checkbox_scanWhenIdle) {
            getSettings().setScanWhenIdle(checkbox_scanWhenIdle.isSelected());
        }
        if (cbox == checkbox_soundEffects) {
            getSettings().setSoundEffects(checkbox_soundEffects.isSelected());
        }
        if (cbox == checkbox_activeSmoothing) {
            getSettings().setActiveSmoothing(checkbox_activeSmoothing.isSelected());
        }
        if (cbox == checkbox_useInputDevice) {
            if (checkbox_useInputDevice.isSelected()) {
                getSettings().setControls(Controls.Device);
            } else {
                getSettings().setControls(Controls.Mouse);
            }
            //getSettings().setUseInputDevice(checkbox_useInputDevice.isSelected());
        }
        if (cbox == checkbox_useArrowKeys) {
            getSettings().setUseArrowKeys(cbox.isSelected());
        }
    }

    public void openWebsite() {
        link(getSettings().getWebsite());
        getSounds().playSound(15);
    }

    public void playRandomSound() {
        getSounds().randomIdleSound();
    }

    public void setBackground() {
        //getCamInput().adapt();
        getSounds().playSound(11);
    }

    public void setLabelText(final GLabel label, final String text) {
        try {
            label.setText(text);
        } catch (final NullPointerException e) { // ignore
        }
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>update the state of the Use Input Device Checkbox</DD>
     * <DT>Date:</DT>
     * <DD>Sep 3, 2017</DD>
     * </DL>
     *
     * @param state
     *            <DL>
     *            <DT><code>true</code></DT>
     *            <DD>checked</DD>
     *            <DT><code>false</code></DT>
     *            <DD>unchecked</DD>
     *            </DL>
     */
    public void updateCheckboxUseInputDevice(final boolean state) {
        checkbox_useInputDevice.setSelected(state);
    }

    public void updateControlPanels() {
        setLabelText(label_serialOut,
            "Serial Out: " + 'a' + getRuntimeSettings().getStrTargetX() + getRuntimeSettings().getStrTargetY()
                + getRuntimeSettings().getFiringInt() + getSettings().getFiringMode().getId()
                + getRuntimeSettings().getScanInt());
        setLabelText(label_targetX, "Pan Servo Position: " + getRuntimeSettings().getStrTargetX());// strTargetx);
        setLabelText(label_slider_tolerance, "Tolerance: " + getSettings().getTolerance()); //str(tolerance));
        setLabelText(label_slider_trackColorTolerance, "Tolerance: " + getSettings().getTrackColorTolerance()); //str(trackColorTolerance));
        setLabelText(label_slider_safeColorTolerance, "Tolerance: " + getSettings().getSafeColorTolerance()); //str(safeColorTolerance));
        setLabelText(label_slider_safeColorMinSize, "Min Area: " + getSettings().getSafeColorMinSize()); //str(safeColorMinSize));
        setLabelText(label_slider_minBlobArea, "Min Size: " + getSettings().getMinBlobArea());//str(minBlobArea));
        setLabelText(label_slider_nbDot, "Memory: " + getSettings().getNbDot()); //str(nbDot));
        setLabelText(label_slider_antSens, "Sensitivity: " + getSettings().getAntSens());//str(antSens));
        setLabelText(label_slider_propX, "X Degree of Anitcipation: " + getSettings().getPropX());//str(propX));
        setLabelText(label_slider_propY, "Y Degree of Anitcipation: " + getSettings().getPropY());//str(propY));
        setLabelText(label_xMin, "xMin: " + getSettings().getxMin());//str(xMin));
        setLabelText(label_xMax, "xMax: " + getSettings().getxMax());//str(xMax));
        setLabelText(label_yMin, "yMin: " + getSettings().getyMin());//str(yMin));
        setLabelText(label_yMax, "yMax: " + getSettings().getyMax());//str(yMax));

        //if (prevTargetX != targetX) {
        label_targetX.setOpaque(!getRuntimeSettings().isPrevTargetXeqTargetX());
        //} else {
        //    label_targetX.setOpaque(false);
        //}
        //if (prevTargetY != targetY) {
        label_targetY.setOpaque(!getRuntimeSettings().isPrevTargetYeqTargetY());
        // } else {
        //    label_targetY.setOpaque(false);
        // }
        setLabelText(label_targetY, "Tilt Servo Position: " + getRuntimeSettings().getStrTargetY()); //strTargety);
        //if (boolean(fire)) {
        final boolean fire = getRuntimeSettings().isFiring();
        label_fire.setOpaque(fire);
        setLabelText(label_fire, (fire ? "" : "Not ") + "Firing");
        //} else {
        //   label_fire.setOpaque(false);
        //   setLabelText(label_fire, "Not Firing");
        //}
        setLabelText(label_fireSelector, getSettings().getFiringMode().altName());
        //        if (firingMode) {
        //            setLabelText(label_fireSelector, "Semi-Automatic");
        //        } else {
        //            setLabelText(label_fireSelector, "Automatic");
        //        }
        if (!getSettings().isRunWithoutArduino()) {
            label_runWithoutArduino.setOpaque(true);
            setLabelText(label_runWithoutArduino, "Controller on " + getRuntimeSettings().getSerPortUsed());
        } else {
            label_runWithoutArduino.setOpaque(false);
            setLabelText(label_runWithoutArduino, "No Controller");
        }
        if (getRuntimeSettings().isConnecting()) {
            label_runWithoutArduino.setOpaque(true);
            setLabelText(label_runWithoutArduino, "connecting...");
        }
        if (getSettings().isScanWhenIdle()) {
            setLabelText(label_scanSelector, "Scan When Idle");
        } else {
            setLabelText(label_scanSelector, "Don't Scan When Idle");
        }
        checkbox_controlMode.setSelected(getSettings().getControlMode() == ControlMode.Autonomous);
        checkbox_useInputDevice.setSelected(getSettings().getControls() == Controls.Device);
        checkbox_useArrowKeys.setSelected(getSettings().isUseArrowKeys());
    }

    public void viewCameraSettings() {
        //camInput.settings();
        final String[] devices = GLCapture.list();
        println("Device:");
        //printArray(devices);
        if (0 < devices.length) {
            final String[] configs = GLCapture.configs(devices[0]);
            println("Configs:");
            getParent();
            PApplet.printArray(configs);
            println("------------------------------------------------");
        }
        //getCamInput().settings();
        if (getSettings().isSoundEffects()) {
            getSounds().playSound(21);
        }
    }
}
