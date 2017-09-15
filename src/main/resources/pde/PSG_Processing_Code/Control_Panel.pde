// current issues: cannot show the frame around control panel while using a picture background. to see areas of code related to this, do a search/find for "(tag frame)"

public int controlPanelWindowX = 50;         // x position on screen of upper-left corner of control panel
public int controlPanelWindowY = 100;        // y position on screen of upper-left corner of control panel

//import guicomponents.*;


GPanel panel_main; // control panel
PImage panelBackgroundImg;

GLabel label_serialOut, label_targetX, label_targetY, label_fire, label_fireSelector, label_scanSelector, label_runWithoutArduino, label_xMin, label_xMax, label_yMin, label_yMax, label_setxMin, label_setxMax, label_setyMin, label_setyMax;   // text labels on control panel
GCheckbox checkbox_leadTarget, checkbox_showRestrictedZones, checkbox_trackingColor, checkbox_safeColor, checkbox_trackingMotion, checkbox_showDifferentPixels, checkbox_showTargetBox, checkbox_mirrorCam, checkbox_controlMode, checkbox_safety, checkbox_showCameraView, checkbox_scanWhenIdle, checkbox_soundEffects, checkbox_activeSmoothing, checkbox_useInputDevice, checkbox_useArrowKeys;// checkboxes
GButton button_viewCameraSettings, button_setBackground, button_selectColor, button_selectSafeColor, button_openWebsite, button_playRandomSound, button_saveSettings, button_loadSettings, button_retryArduinoConnect, button_saveAndExit, button_configJoystick, button_resetCalibration, button_flipX, button_flipY;	// buttons
GSlider slider_tolerance, slider_trackColorTolerance, slider_safeColorTolerance, slider_safeColorMinSize, slider_minBlobArea, slider_nbDot, slider_antSens, slider_propX, slider_propY, slider_smoothingFactor; //sliders
GLabel label_slider_tolerance, label_slider_trackColorTolerance, label_slider_safeColorTolerance, label_slider_safeColorMinSize, label_slider_minBlobArea, label_slider_nbDot, label_slider_antSens, label_slider_propX, label_slider_propY, label_smoothingFactor; // value readouts for sliders
// GTextField txfSomeText;   // textfield
GDropList dropdown_effect, dropdown_firingMode, dropdown_comPort;   // dropdown menus
//GActivityBar acyBar;   // activity bar
//GTimer tmrTimer;       // timer

//GOptionGroup opgMouseOver;
//GOption optHand, optXhair, optMove, optText, optWait;

// G4P components for second windowl
GWindow window_main;

int sliderInertia = 3;

void drawControlPanel() {
  G4P.messagesEnabled(false);
  G4P.setGlobalColorScheme(GCScheme.BLUE_SCHEME);
  G4P.setCursor(ARROW);
  surface.setTitle("Settings");
  panelBackgroundImg = loadImage("Panel_Background.png");
  surface.setTitle("Control Panel");

  window_main = GWindow.getWindow(this, "Main", 50, 100, 600, 600, P2D);
  window_main.addDrawHandler(this, "drawController");
  G4P.setMouseOverEnabled(true);

  // create Panels
  panel_main = new GPanel(window_main, 0, 0, 600, 600, "Main");
  panel_main.setOpaque(false);
  panel_main.setCollapsed(false);

  // create labels
  label_serialOut = new GLabel(this, 300, 475, 150, 20, "Serial Out:           ");
  //label_serialOut.setBorder(0);
  label_serialOut.setOpaque(false);
  label_serialOut.setLocalColorScheme(GCScheme.BLUE_SCHEME);
  panel_main.addControl(label_serialOut);

  label_targetX = new GLabel(this, 300, 495, 150, 20, "Pan Servo Position:    ");
  //label_targetX.setBorder(0);
  label_targetX.setOpaque(false);
  label_targetX.setLocalColorScheme(GCScheme.BLUE_SCHEME);
  panel_main.addControl(label_targetX);

  label_targetY = new GLabel(this, 300, 515, 150, 20, "Tilt Servo Position:    ");
  //label_targetY.setBorder(0);
  label_targetY.setOpaque(false);
  label_targetY.setLocalColorScheme(GCScheme.BLUE_SCHEME);
  panel_main.addControl(label_targetY);	

  label_fire = new GLabel(this, 300, 535, 150, 20, "Not Firing");
  //label_fire.setBorder(0);
  label_fire.setOpaque(false); 
  label_fire.setLocalColorScheme(GCScheme.RED_SCHEME);
  panel_main.addControl(label_fire);	

  label_fireSelector = new GLabel(this, 300, 555, 150, 20, "Automatic");
  //label_fireSelector.setBorder(0);
  label_fireSelector.setOpaque(false);
  label_fireSelector.setLocalColorScheme(GCScheme.BLUE_SCHEME);
  panel_main.addControl(label_fireSelector);	

  label_scanSelector = new GLabel(this, 300, 575, 150, 20, "Scan When Idle");
  //label_scanSelector.setBorder(0);
  label_scanSelector.setOpaque(false);
  label_scanSelector.setLocalColorScheme(GCScheme.BLUE_SCHEME);
  panel_main.addControl(label_scanSelector);	

  label_runWithoutArduino = new GLabel(this, 460, 475, 120, 20, "No Controller");
  //label_runWithoutArduino.setBorder(0);
  label_runWithoutArduino.setOpaque(false);
  label_runWithoutArduino.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
  panel_main.addControl(label_runWithoutArduino);	

  label_xMin = new GLabel(this, 35, 362, 150, 20, "xMin: 000");
  //label_xMin.setBorder(0);
  label_xMin.setOpaque(false);
  label_xMin.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
  panel_main.addControl(label_xMin);	

  label_xMax = new GLabel(this, 145, 362, 150, 20, "xMax: 180");
  //label_xMax.setBorder(0);
  label_xMax.setOpaque(false);
  label_xMax.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
  panel_main.addControl(label_xMax);	

  label_yMin = new GLabel(this, 35, 392, 150, 20, "yMin: 000");
  //label_yMin.setBorder(0);
  label_yMin.setOpaque(false);
  label_yMin.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
  panel_main.addControl(label_yMin);	

  label_yMax = new GLabel(this, 145, 392, 150, 20, "yMax: 180");
  //label_yMax.setBorder(0);
  label_yMax.setOpaque(false);
  label_yMax.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
  panel_main.addControl(label_yMax);	

  label_setxMin = new GLabel(this, 10, 375, 120, 10, "to set xMin press A");
  panel_main.addControl(label_setxMin);

  label_setxMax = new GLabel(this, 120, 375, 120, 10, "to set xMax press D");
  panel_main.addControl(label_setxMax);

  label_setyMin = new GLabel(this, 10, 405, 120, 10, "to set yMin press S");
  panel_main.addControl(label_setyMin);

  label_setyMax = new GLabel(this, 120, 405, 120, 10, "to set yMax press W");
  panel_main.addControl(label_setyMax);

  // create checkboxes
  checkbox_leadTarget = new GCheckbox(this, 310, 325, 120, 20);
  checkbox_leadTarget.setText("Enable Target Anticipation");

  checkbox_leadTarget.setSelected(leadTarget);
  //  checkbox_leadTarget.setBorder(0);
//  checkbox_leadTarget.addEventHandler(this, "checkbox_click");
  panel_main.addControl(checkbox_leadTarget);

  checkbox_showRestrictedZones = new GCheckbox(this, 10, 480, 220, 20);
  checkbox_showRestrictedZones.setText("Show Restricted Zones  (to set, hold R and click+drag)");
  checkbox_showRestrictedZones.setSelected(showRestrictedZones);
  //  //checkbox_showRestrictedZones.setBorder(0);
//  checkbox_showRestrictedZones.addEventHandler(this, "checkbox_click");
  panel_main.addControl(checkbox_showRestrictedZones);

  checkbox_trackingColor = new GCheckbox(this, 10, 255, 120, 20);
  checkbox_trackingColor.setText("Track A Color");
  checkbox_trackingColor.setSelected(trackingColor);
  //checkbox_trackingColor.setBorder(0);
//  checkbox_trackingColor.addEventHandler(this, "checkbox_click");
  panel_main.addControl(checkbox_trackingColor);

  checkbox_safeColor = new GCheckbox(this, 310, 175, 120, 20);
  checkbox_safeColor.setText("Enable Safe Color");
  checkbox_safeColor.setSelected(safeColor);
  //  checkbox_safeColor.setBorder(0);
//  checkbox_safeColor.addEventHandler(this, "checkbox_click");
  panel_main.addControl(checkbox_safeColor);

  checkbox_trackingMotion = new GCheckbox(this, 10, 205, 120, 20);
  checkbox_trackingMotion.setText("Track Motion");
  checkbox_trackingMotion.setSelected(trackingMotion);
  //  checkbox_trackingMotion.setBorder(0);
//  checkbox_trackingMotion.addEventHandler(this, "checkbox_click");
  panel_main.addControl(checkbox_trackingMotion);

  checkbox_showDifferentPixels = new GCheckbox(this, 10, 500, 120, 20);
  checkbox_showDifferentPixels.setText("Show Different Pixels");
  checkbox_showDifferentPixels.setSelected(showDifferentPixels);
  //  checkbox_showDifferentPixels.setBorder(0);
//  checkbox_showDifferentPixels.addEventHandler(this, "checkbox_click");
  panel_main.addControl(checkbox_showDifferentPixels);

  checkbox_showTargetBox = new GCheckbox(this, 10, 520, 120, 20);
  checkbox_showTargetBox.setText("Show Target Box");
  checkbox_showTargetBox.setSelected(showTargetBox);
  //  checkbox_showTargetBox.setBorder(0);
//  checkbox_showTargetBox.addEventHandler(this, "checkbox_click");
  panel_main.addControl(checkbox_showTargetBox);

  checkbox_mirrorCam = new GCheckbox(this, 10, 540, 120, 20);
  checkbox_mirrorCam.setText("Mirror Webcam");
  checkbox_mirrorCam.setSelected(mirrorCam);
  //  checkbox_mirrorCam.setBorder(0);
//  checkbox_mirrorCam.addEventHandler(this, "checkbox_click");
  panel_main.addControl(checkbox_mirrorCam);

  checkbox_controlMode = new GCheckbox(this, 10, 25, 120, 20);
  checkbox_controlMode.setText("Enable Autonomous Mode (press SPACE to toggle)");
  checkbox_controlMode.setSelected(controlMode);
  //  checkbox_controlMode.setBorder(0);
//  checkbox_controlMode.addEventHandler(this, "checkbox_click");
  panel_main.addControl(checkbox_controlMode);

  checkbox_safety = new GCheckbox(this, 10, 325, 120, 20);
  checkbox_safety.setText("Enable Weapon");
  checkbox_safety.setSelected(safety);
  //  checkbox_safety.setBorder(0);
//  checkbox_safety.addEventHandler(this, "checkbox_click");
  panel_main.addControl(checkbox_safety);

  checkbox_showCameraView = new GCheckbox(this, 10, 560, 120, 20);
  checkbox_showCameraView.setText("Show Camera View");
  checkbox_showCameraView.setSelected(showCameraView);
  //  checkbox_showCameraView.setBorder(0);
//  checkbox_showCameraView.addEventHandler(this, "checkbox_click");
  panel_main.addControl(checkbox_showCameraView);

  checkbox_scanWhenIdle = new GCheckbox(this, 10, 345, 120, 20);
  checkbox_scanWhenIdle.setText("Scan When Idle");
  checkbox_scanWhenIdle.setSelected(scanWhenIdle);
  //  checkbox_scanWhenIdle.setBorder(0);
//  checkbox_scanWhenIdle.addEventHandler(this, "checkbox_click");
  panel_main.addControl(checkbox_scanWhenIdle);

  checkbox_soundEffects = new GCheckbox(this, 315, 25, 120, 20);
  checkbox_soundEffects.setText("Enable Sounds Effects");
  checkbox_soundEffects.setSelected(soundEffects);
  //  checkbox_soundEffects.setBorder(0);
//  checkbox_soundEffects.addEventHandler(this, "checkbox_click");
  panel_main.addControl(checkbox_soundEffects);

  checkbox_activeSmoothing = new GCheckbox(this, 460, 25, 120, 20);
  checkbox_activeSmoothing.setText("Smoothing");
  checkbox_activeSmoothing.setSelected(activeSmoothing);
  //  checkbox_activeSmoothing.setBorder(0);
//  checkbox_activeSmoothing.addEventHandler(this, "checkbox_click");
  panel_main.addControl(checkbox_activeSmoothing);

  checkbox_useInputDevice = new GCheckbox(this, 10, 45, 220, 20);
  checkbox_useInputDevice.setText("Use Joystick/Game Controller Input");
  checkbox_useInputDevice.setSelected(useInputDevice);
  //  checkbox_useInputDevice.setBorder(0);
//  checkbox_useInputDevice.addEventHandler(this, "checkbox_click");
  panel_main.addControl(checkbox_useInputDevice);

  checkbox_useArrowKeys = new GCheckbox(this, 10, 65, 220, 20);
  checkbox_useArrowKeys.setText("Use Arrow Keys to Fine Adjust (press SHIFT to toggle)");
  checkbox_useArrowKeys.setSelected(useArrowKeys);
  //  checkbox_useArrowKeys.setBorder(0);
//  checkbox_useArrowKeys.addEventHandler(this, "checkbox_click");
  panel_main.addControl(checkbox_useArrowKeys);


  // create buttons
  button_viewCameraSettings = new GButton(this, 460, 75, 120, 10, "Webcam Settings");
//  button_viewCameraSettings.addEventHandler(this, "button_click");
  panel_main.addControl(button_viewCameraSettings);

  button_setBackground = new GButton(this, 110, 205, 185, 10, "Save Current Image as Background");
//  button_setBackground.addEventHandler(this, "button_click");
  panel_main.addControl(button_setBackground);

  button_selectColor = new GButton(this, 110, 255, 185, 10, "Select Color to Track");
//  button_selectColor.addEventHandler(this, "button_click");
  panel_main.addControl(button_selectColor);

  button_selectSafeColor = new GButton(this, 310, 200, 20, 10, "Select Safe Color  ");
//  button_selectSafeColor.addEventHandler(this, "button_click");
  panel_main.addControl(button_selectSafeColor);

  // with image
  button_openWebsite = new GButton(this, 545, 560, 36, 24);
  button_openWebsite.setIcon("Sentry_Logo_Tiny.png", 1, GAlign.NORTH, GAlign.CENTER, GAlign.MIDDLE);


//  button_openWebsite.addEventHandler(this, "button_click");
  panel_main.addControl(button_openWebsite);

  button_playRandomSound = new GButton(this, 320, 75, 120, 10, "Play a Random Sound");
//  button_playRandomSound.addEventHandler(this, "button_click");
  panel_main.addControl(button_playRandomSound);

  button_saveSettings = new GButton(this, 320, 110, 120, 10, "Save Settings");
//  button_saveSettings.addEventHandler(this, "button_click");
  panel_main.addControl(button_saveSettings);

  button_loadSettings = new GButton(this, 460, 110, 120, 10, "Re-Load Settings");
//  button_loadSettings.addEventHandler(this, "button_click");
  panel_main.addControl(button_loadSettings);

  button_saveAndExit = new GButton(this, 10, 95, 280, 40, "Save Settings & Exit");
//  button_saveAndExit.addEventHandler(this, "button_click");
  panel_main.addControl(button_saveAndExit);

  button_retryArduinoConnect = new GButton(this, 460, 500, 120, 10, "Retry/Connect");
//  button_retryArduinoConnect.addEventHandler(this, "button_click");
  panel_main.addControl(button_retryArduinoConnect);

  button_configJoystick = new GButton(this, 200, 45, 70, 10, "Configure");
//  button_configJoystick.addEventHandler(this, "button_click");
  panel_main.addControl(button_configJoystick);

  button_resetCalibration = new GButton(this, 10, 425, 220, 10, "Reset Calibration");
//  button_resetCalibration.addEventHandler(this, "button_click");
  panel_main.addControl(button_resetCalibration);

  button_flipX = new GButton(this, 240, 370, 40, 10, "Flip X");
//  button_flipX.addEventHandler(this, "button_click");
  panel_main.addControl(button_flipX);

  button_flipY = new GButton(this, 240, 400, 40, 10, "Flip Y");
//  button_flipY.addEventHandler(this, "button_click");
  panel_main.addControl(button_flipY);

  // create sliders
  slider_tolerance = new GSlider(this, 10, 225, 200, 20, 10.0);
  slider_tolerance.setLimits(tolerance, 0, 200);
  slider_tolerance.setShowLimits(false);
  //slider_tolerance.setShowLimits(false);
  //slider_tolerance.setShowValue(false);
  slider_tolerance.setNbrTicks(10);
  slider_tolerance.setEasing(sliderInertia);
//  slider_tolerance.addEventHandler(this, "slider_click");
  panel_main.addControl(slider_tolerance);
  label_slider_tolerance = new GLabel(this, 210, 225, 150, 20, "Tolerance: ");
  //  label_slider_tolerance.setBorder(0);
  label_slider_tolerance.setOpaque(false);
  label_slider_tolerance.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
  panel_main.addControl(label_slider_tolerance);

  slider_trackColorTolerance = new GSlider(this, 10, 275, 200, 20, 10.0);
  slider_trackColorTolerance.setLimits(trackColorTolerance, 0, 300);
  slider_trackColorTolerance.setShowLimits(false);
  slider_trackColorTolerance.setShowValue(false);
  slider_trackColorTolerance.setNbrTicks(12);
  slider_trackColorTolerance.setEasing(sliderInertia);
//  slider_trackColorTolerance.addEventHandler(this, "slider_click");
  panel_main.addControl(slider_trackColorTolerance);
  label_slider_trackColorTolerance = new GLabel(this, 210, 275, 150, 20, "Tolerance: ");
  //  label_slider_trackColorTolerance.setBorder(0);
  label_slider_trackColorTolerance.setOpaque(false);
  label_slider_trackColorTolerance.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
  panel_main.addControl(label_slider_trackColorTolerance);

  slider_safeColorTolerance = new GSlider(this, 310, 230, 200, 20, 10.0);
  slider_safeColorTolerance.setLimits(safeColorTolerance, 0, 300);
  slider_safeColorTolerance.setShowLimits(false);
  slider_safeColorTolerance.setShowValue(false);
  slider_safeColorTolerance.setNbrTicks(12);
  slider_safeColorTolerance.setEasing(sliderInertia);
//  slider_safeColorTolerance.addEventHandler(this, "slider_click");
  panel_main.addControl(slider_safeColorTolerance);
  label_slider_safeColorTolerance = new GLabel(this, 510, 230, 150, 20, "Tolerance: ");
  //  label_slider_safeColorTolerance.setBorder(0);
  label_slider_safeColorTolerance.setOpaque(false);
  label_slider_safeColorTolerance.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
  panel_main.addControl(label_slider_safeColorTolerance);

  slider_safeColorMinSize = new GSlider(this, 310, 260, 200, 20, 10.0);
  slider_safeColorMinSize.setLimits(safeColorMinSize, 0, 5000);
  slider_safeColorMinSize.setShowLimits(false);
  slider_safeColorMinSize.setShowValue(false);
  slider_safeColorMinSize.setNbrTicks(10);
  slider_safeColorMinSize.setEasing(sliderInertia);
//  slider_safeColorMinSize.addEventHandler(this, "slider_click");
  panel_main.addControl(slider_safeColorMinSize);
  label_slider_safeColorMinSize = new GLabel(this, 510, 260, 150, 20, "Min Area: ");
  //  label_slider_safeColorMinSize.setBorder(0);
  label_slider_safeColorMinSize.setOpaque(false);
  label_slider_safeColorMinSize.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
  panel_main.addControl(label_slider_safeColorMinSize);

  slider_minBlobArea = new GSlider(this, 10, 175, 200, 20, 10.0);
  slider_minBlobArea.setLimits(minBlobArea, 0, 10000);
  slider_minBlobArea.setShowLimits(false);
  slider_minBlobArea.setShowValue(false);
  slider_minBlobArea.setNbrTicks(10);
  slider_minBlobArea.setEasing(sliderInertia);
//  slider_minBlobArea.addEventHandler(this, "slider_click");
  panel_main.addControl(slider_minBlobArea);
  label_slider_minBlobArea = new GLabel(this, 210, 175, 150, 20, "Min Size: ");
  //  label_slider_minBlobArea.setBorder(0);
  label_slider_minBlobArea.setOpaque(false);
  label_slider_minBlobArea.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
  panel_main.addControl(label_slider_minBlobArea);

  slider_nbDot = new GSlider(this, 310, 350, 200, 20, 10.0);
  slider_nbDot.setLimits(nbDot, 2, 22);
  slider_nbDot.setShowLimits(false);
  slider_nbDot.setShowValue(false);
  slider_nbDot.setNbrTicks(10);
  slider_nbDot.setEasing(sliderInertia);
//  slider_nbDot.addEventHandler(this, "slider_click");
  panel_main.addControl(slider_nbDot);
  label_slider_nbDot = new GLabel(this, 510, 350, 150, 20, "Memory: ");
  //  label_slider_nbDot.setBorder(0);
  label_slider_nbDot.setOpaque(false);
  label_slider_nbDot.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
  panel_main.addControl(label_slider_nbDot);

  slider_antSens = new GSlider(this, 310, 375, 200, 20, 10.0);
  slider_antSens.setLimits(antSens, 1, 100);
  slider_antSens.setShowLimits(false);
  slider_antSens.setShowValue(false);
  slider_antSens.setNbrTicks(10);
  slider_antSens.setEasing(sliderInertia);
//  slider_antSens.addEventHandler(this, "slider_click");
  panel_main.addControl(slider_antSens);
  label_slider_antSens = new GLabel(this, 510, 375, 150, 20, "Sensitivity: ");
  //  label_slider_antSens.setBorder(0);
  label_slider_antSens.setOpaque(false);
  label_slider_antSens.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
  panel_main.addControl(label_slider_antSens);

  slider_propX = new GSlider(this, 310, 400, 120, 20, 10.0);
  slider_propX.setLimits(propX, 0.00, 3.00);
  slider_propX.setNumberFormat(G4P.DECIMAL, 2);
  slider_propX.setShowLimits(false);
  slider_propX.setShowValue(false);
  slider_propX.setNbrTicks(7);
  slider_propX.setEasing(sliderInertia);
//  slider_propX.addEventHandler(this, "slider_click");
  panel_main.addControl(slider_propX);
  label_slider_propX = new GLabel(this, 430, 400, 300, 20, "X Degree of Anticipation: ");
  //  label_slider_propX.setBorder(0);
  label_slider_propX.setOpaque(false);
  label_slider_propX.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
  panel_main.addControl(label_slider_propX);

  slider_propY = new GSlider(this, 310, 425, 120, 20, 10.0);
  slider_propY.setLimits(propY, 0.00, 3.00);
  slider_propY.setNumberFormat(G4P.DECIMAL, 2);
  slider_propY.setShowLimits(false);
  slider_propY.setShowValue(false);
  slider_propY.setNbrTicks(7);
  slider_propY.setEasing(sliderInertia);
//  slider_propY.addEventHandler(this, "slider_click");
  panel_main.addControl(slider_propY);
  label_slider_propY = new GLabel(this, 430, 425, 300, 20, "Y Degree of Anticipation: ");
  //  label_slider_propY.setBorder(0);
  label_slider_propY.setOpaque(false);
  label_slider_propY.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
  panel_main.addControl(label_slider_propY); 

  slider_smoothingFactor = new GSlider(this, 310, 50, 180, 20, 10.0);
  slider_smoothingFactor.setLimits(smoothingFactor, 0.00, 1.00);
  slider_smoothingFactor.setNumberFormat(G4P.DECIMAL, 2);
  slider_smoothingFactor.setShowLimits(false);
  slider_smoothingFactor.setShowValue(false);
  slider_smoothingFactor.setNbrTicks(10);
  slider_smoothingFactor.setEasing(sliderInertia);
//  slider_smoothingFactor.addEventHandler(this, "slider_click");
  panel_main.addControl(slider_smoothingFactor);
  label_smoothingFactor = new GLabel(this, 490, 50, 100, 20, "Smoothing Factor");
  //  label_smoothingFactor.setBorder(0);
  label_smoothingFactor.setOpaque(false);
  label_smoothingFactor.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
  panel_main.addControl(label_smoothingFactor); 


  // createCombos (dropdown boxes)
  String[] entries_1 = new String[] {
    "Opaque", "Transparent", "Negative", "Negative & Transparent"
  };
  dropdown_effect = new GDropList(this, 140, 500, 140, 76, entries_1.length);

  dropdown_effect.setItems(entries_1, 0);        // this, String[] of entries, dropdown # of enteries shown at once, xPosition, yPosition, width
  dropdown_effect.setSelected(effect);                                                  // which entry to show as selected (first entry is 0, second is 1, etc.)
//  dropdown_effect.addEventHandler(this, "dropdown_click");
  panel_main.addControl(dropdown_effect);

  String[] entries_2 = new String[] {
    "Automatic", "Semi-Auto"
  };
  dropdown_firingMode = new GDropList(this, 140, 320, 100, 76, entries_2.length);
  dropdown_firingMode.setItems(entries_2, 0);        // this, String[] of entries, dropdown # of enteries shown at once, xPosition, yPosition, width
  dropdown_firingMode.setSelected(int(firingMode));                                                  // which entry to show as selected (first entry is 0, second is 1, etc.)
//  dropdown_firingMode.addEventHandler(this, "dropdown_click");
  panel_main.addControl(dropdown_firingMode);


  if (Serial.list().length > 0) {
    String[] entries_3 = append(Serial.list(), "Select to Override");
    dropdown_comPort = new GDropList(this, 460, 520, 120, 76, entries_3.length);
    dropdown_comPort.setItems(entries_3, 0);  
    dropdown_comPort.setSelected(entries_3.length-1);
//    dropdown_comPort.addEventHandler(this, "dropdown_click");
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
  ////window_main.addControl(panel_main);
  //window_main.addDrawHandler(this, "drawController");
  //panel_main.setXY(0, 0);
}


public void updateControlPanels() {
  setLabelText(label_serialOut, "Serial Out: " + 'a' + strTargetx + strTargety + str(fire) + fireSelector + scanSelector);
  setLabelText(label_targetX, "Pan Servo Position: " + strTargetx);
  setLabelText(label_slider_tolerance, "Tolerance: " + str(tolerance));
  setLabelText(label_slider_trackColorTolerance, "Tolerance: " + str(trackColorTolerance));
  setLabelText(label_slider_safeColorTolerance, "Tolerance: " + str(safeColorTolerance));
  setLabelText(label_slider_safeColorMinSize, "Min Area: " + str(safeColorMinSize)); 
  setLabelText(label_slider_minBlobArea, "Min Size: " + str(minBlobArea));
  setLabelText(label_slider_nbDot, "Memory: " + str(nbDot));
  setLabelText(label_slider_antSens, "Sensitivity: " + str(antSens));
  setLabelText(label_slider_propX, "X Degree of Anitcipation: " + str(propX));
  setLabelText(label_slider_propY, "Y Degree of Anitcipation: " + str(propY));
  setLabelText(label_xMin, "xMin: " + str(xMin));
  setLabelText(label_xMax, "xMax: " + str(xMax));
  setLabelText(label_yMin, "yMin: " + str(yMin));
  setLabelText(label_yMax, "yMax: " + str(yMax));

  if (prevTargetX != targetX) {
    label_targetX.setOpaque(true);
  } else {
    label_targetX.setOpaque(false);
  }
  if (prevTargetY != targetY) {
    label_targetY.setOpaque(true);
  } else {
    label_targetY.setOpaque(false);
  }
  setLabelText(label_targetY, "Tilt Servo Position: " + strTargety);
  if (boolean(fire)) {
    label_fire.setOpaque(true);
    setLabelText(label_fire, "Firing");
  } else {
    label_fire.setOpaque(false);
    setLabelText(label_fire, "Not Firing");
  }
  if (firingMode) {
    setLabelText(label_fireSelector, "Semi-Automatic");
  } else {
    setLabelText(label_fireSelector, "Automatic");
  }
  if (!runWithoutArduino) {
    label_runWithoutArduino.setOpaque(true);
    setLabelText(label_runWithoutArduino, "Controller on " + serPortUsed);
  } else {
    label_runWithoutArduino.setOpaque(false);
    setLabelText(label_runWithoutArduino, "No Controller");
  }
  if (connecting) {
    label_runWithoutArduino.setOpaque(true);
    setLabelText(label_runWithoutArduino, "connecting...");
  }
  if (scanWhenIdle) {
    setLabelText(label_scanSelector, "Scan When Idle");
  } else {
    setLabelText(label_scanSelector, "Don't Scan When Idle");
  }
  checkbox_controlMode.setSelected(controlMode);
  checkbox_useInputDevice.setSelected(useInputDevice);
  checkbox_useArrowKeys.setSelected(useArrowKeys);
}

//public void dropList_click(GDropList source, GEvent event) { //_CODE_:dropList1:458653:
//  println("dropList1 - GDropList >> GEvent." + event + " @ " + millis());
//} //_CODE_:dropList1:458653:

public void handleDropListEvents(GDropList combo, GEvent event) {
//public void handleComboEvents(GCombo combo) {
  if (combo == dropdown_effect) {
    effect = dropdown_effect.getSelectedIndex();
  }
  if (combo == dropdown_firingMode) {
    firingMode = boolean(dropdown_firingMode.getSelectedIndex());
  }
  if (combo == dropdown_comPort) {
    if (dropdown_comPort.getSelectedIndex() < Serial.list().length) {
      if (!runWithoutArduino) {
        connecting = true;
        println("Manual override. Stopping old serial connection...");
        arduinoPort.stop();
        println("Stopped old serial connection.");
      }
      println("New COM port selected manually: " + Serial.list()[dropdown_comPort.getSelectedIndex()]);
      arduinoPort = new Serial(this, Serial.list()[dropdown_comPort.getSelectedIndex()], 4800);
      println("Serial Port used = " + Serial.list()[dropdown_comPort.getSelectedIndex()]);
      serPortUsed = Serial.list()[dropdown_comPort.getSelectedIndex()];
      runWithoutArduino = false;
      connecting = false;
    }
  }
}
public void handleSliderEvents(GValueControl slider, GEvent event) {
//public void handleSliderEvents(GSlider slider) {
  if (slider == slider_tolerance) {
    tolerance = slider_tolerance.getValueI();
  }
  if (slider == slider_trackColorTolerance) {
    trackColorTolerance = slider_trackColorTolerance.getValueI();
  }
  if (slider == slider_safeColorTolerance) {
    safeColorTolerance = slider_safeColorTolerance.getValueI();
  }
  if (slider == slider_minBlobArea) {
    minBlobArea = slider_minBlobArea.getValueI();
  }
  if (slider == slider_safeColorMinSize) {
    safeColorMinSize = slider_safeColorMinSize.getValueI();
  }
  if (slider == slider_nbDot) {
    nbDot = slider_nbDot.getValueI();
  }
  if (slider == slider_antSens) {
    antSens = slider_antSens.getValueI();
  }
  if (slider == slider_propX) {
    propX = slider_propX.getValueF();
  }
  if (slider == slider_propY) {
    propY = slider_propY.getValueF();
  }
  if (slider == slider_smoothingFactor) {
    smoothingFactor = slider_smoothingFactor.getValueF();
  }
}

public void handleButtonEvents(GButton button, GEvent event) {
//public void handleButtonEvents(GButton button) {
  if (button == button_viewCameraSettings && event == GEvent.CLICKED) {
    viewCameraSettings();
  }
  if (button == button_setBackground && event == GEvent.CLICKED) {
    setBackground();
  }
  if (button == button_selectColor && event == GEvent.CLICKED) {
    selectColor();
  }
  if (button == button_selectSafeColor && event == GEvent.CLICKED) {
    selectSafeColor();
  }
  if (button == button_openWebsite && event == GEvent.CLICKED) {
    openWebsite();
  }
  if (button == button_playRandomSound && event == GEvent.CLICKED) {
    playRandomSound();
  }
  if (button == button_saveSettings && event == GEvent.CLICKED) {
    saveSettings();
  }
  if (button == button_loadSettings && event == GEvent.CLICKED) {
    loadSettings();
  }
  if (button == button_saveAndExit && event == GEvent.CLICKED) {
    saveSettings();
    delay(100);
    exit();
  }
  if (button == button_retryArduinoConnect && event == GEvent.CLICKED) {
    if (!runWithoutArduino) {
      arduinoPort.stop();
    }
    runWithoutArduino = false;
    retryArduinoConnect();
  }
  if (button == button_configJoystick  && event == GEvent.CLICKED) {
    configJoystick();
  }
  if (button == button_resetCalibration && event == GEvent.CLICKED) {
    xMin =60;
    xMax = 120;
    yMin = 90;
    yMax = 100;
    xRatio = (camWidth / (xMax - xMin));   
    yRatio = (camHeight/ (yMax - yMin));
  }
  if (button == button_flipX && event == GEvent.CLICKED) {
    float oldxMin = xMin;
    float oldxMax = xMax;
    xMin = oldxMax;
    xMax = oldxMin;
    xRatio = (camWidth / (xMax - xMin));
  }
  if (button == button_flipY && event == GEvent.CLICKED) {
    float oldyMin = yMin;
    float oldyMax = yMax;
    yMin = oldyMax;
    yMax = oldyMin;
    yRatio = (camHeight/ (yMax - yMin));
  }
}

public void handleToggleControlEvents(GToggleControl cbox, GEvent event) {
//public void handleCheckboxEvents(GCheckbox cbox) {
  if (cbox == checkbox_leadTarget) {
    leadTarget = checkbox_leadTarget.isSelected();
  }
  if (cbox == checkbox_showRestrictedZones) {
    showRestrictedZones = checkbox_showRestrictedZones.isSelected();
  }
  if (cbox == checkbox_trackingColor) {
    trackingColor = checkbox_trackingColor.isSelected();
  }
  if (cbox == checkbox_safeColor) {
    safeColor = checkbox_safeColor.isSelected();
  }
  if (cbox == checkbox_trackingMotion) {
    trackingMotion = checkbox_trackingMotion.isSelected();
  }
  if (cbox == checkbox_showDifferentPixels) {
    showDifferentPixels = checkbox_showDifferentPixels.isSelected();
  }
  if (cbox == checkbox_showTargetBox) {
    showTargetBox = checkbox_showTargetBox.isSelected();
  }
  if (cbox == checkbox_mirrorCam) {
    mirrorCam = checkbox_mirrorCam.isSelected();
  }
  if (cbox == checkbox_controlMode) {
    controlMode = checkbox_controlMode.isSelected();
  }
  if (cbox == checkbox_safety) {
    safety = checkbox_safety.isSelected();
  }
  if (cbox == checkbox_showCameraView) {
    showCameraView = checkbox_showCameraView.isSelected();
  }
  if (cbox == checkbox_scanWhenIdle) {
    scanWhenIdle = checkbox_scanWhenIdle.isSelected();
  }
  if (cbox == checkbox_soundEffects) {
    soundEffects = checkbox_soundEffects.isSelected();
  }
  if (cbox == checkbox_activeSmoothing) {
    activeSmoothing = checkbox_activeSmoothing.isSelected();
  }
  if (cbox == checkbox_useInputDevice) {
    useInputDevice = checkbox_useInputDevice.isSelected();
  }
  if (cbox == checkbox_useArrowKeys) {
    useArrowKeys = checkbox_useArrowKeys.isSelected();
  }
}

public void drawController(PApplet appc, GWinData data) {
  appc.background(panelBackgroundImg);
  
}

public void setLabelText(GLabel label, String text) {
  try {
    label.setText(text);
  }
  catch (NullPointerException e) { // ignore
  }
}