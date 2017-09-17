/**
 * @author ubuntu - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 2, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapoport.utilities.psgcode.devices;

// import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang.NotImplementedException;
import org.gamecontrolplus.ControlDevice;
import org.gamecontrolplus.ControlIO;

import com.nrapoport.utilities.psgcode.PSGProcessingCode;
import com.nrapoport.utilities.psgcode.config.Settings;
import com.nrapoport.utilities.psgcode.enums.DeviceButtonActions;
import com.nrapoport.utilities.psgcode.enums.DeviceSliderActions;
import com.nrapoport.utilities.psgcode.util.Util;

import g4p_controls.G4P;
import g4p_controls.GAlign;
import g4p_controls.GButton;
import g4p_controls.GCheckbox;
import g4p_controls.GConstants;
import g4p_controls.GDropList;
import g4p_controls.GEditableTextControl;
import g4p_controls.GEvent;
import g4p_controls.GPanel;
import g4p_controls.GTextField;
// import guicomponents.GCombo;
// import guicomponents.GWSlider;
import processing.core.PApplet;

/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>Create a Device Configuration GUI</DD>
 * <DT>Date:</DT>
 * <DD>Sep 2, 2017</DD>
 * </DL>
 *
 * @author ubuntu - Nick Rapoport
 *
 */
public class InputDeviceSetupTool extends PApplet implements IDimensionsAware, IGUIDimensions {
    // @SuppressWarnings("unused")
    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(InputDeviceSetupTool.class);

    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>DEFAULT_DEVICE_FILENAME (String) = "settings_inputDevice.cfg"</DD>
     * </DL>
     */
    public final static String DEFAULT_DEVICE_FILENAME = "settings-inputDevice.cfg";

    //    protected static void createDataPath() {
    //        final Path absolutePath = FileSystems.getDefault().getPath("data").toAbsolutePath();
    //        try {
    //            Files.createDirectories(absolutePath,
    //                PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwxrwxr--")));
    //        } catch (final IOException ex) {
    //            log.error("caught IOException :", ex); //$NON-NLS-1$
    //            throw new RuntimeException("Could not create the projects ./data directory", ex);
    //        }
    //    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>launch Processing main method for this class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param arrstring
     */
    public static void launchMain(final String[] arrstring) {
        final String[] arrstring2 = new String[] { InputDeviceSetupTool.class.getName() };
        if (arrstring != null) {
            PApplet.main(PApplet.concat(arrstring2, arrstring));
        } else {
            PApplet.main(arrstring2);
        }
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>start this application</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param args
     */
    public static void main(final String[] args) {
        launchMain(args);
    }

    private Settings settings;

    private final List<IDeviceHelper> deviceButtons = new ArrayList<>(30);

    private final List<IDeviceHelper> deviceSliders = new ArrayList<>(30);

    private ControlIO controlIO;

    private ControlDevice tryDevice;

    private ControlDevice inputDevice;

    private GPanel panelMain;

    private GPanel panelAdminTop;

    private GPanel panelAdminBottom;

    private GDropList dropdown_whichDevice;

    private GDropList dropdown_selectFile;

    private GCheckbox checkboxUpdateSettings;

    private GButton saveButton;

    private GTextField textFileName;

    private GButton exitButton;

    private int whichDevice = 0;

    private List<String> listDevices = new ArrayList<>(20);

    private int numDevices = 0;

    private float currHeight = 600;

    private float currWidth = 800;

    private float prevMaxX;

    private float prevMaxY;

    private float nextAdminX;

    private List<String> fileNames;

    private GPanel borderTop;

    private GPanel borderBottom;

    private float adminMinHeight;

    private int lastSelectedFile = -1;

    // private final int ddShowSize = 4;

    private Map<String, Path> configFiles = new LinkedHashMap<>();

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>InputDeviceSetupTool Constructor</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     */
    public InputDeviceSetupTool() {
    }

    protected float calcDropDownLength(final float current, final float newStrLen, final float min) {
        //25 chars per 200 width
        final float perChar = 200 / 25;
        final float newLen = newStrLen * perChar;
        final float currLen = current == 0 ? min : current;
        return newLen > currLen ? newLen : currLen;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>close currently selected device</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     */
    public void closeDevice() {
        if (inputDevice != null) {
            inputDevice.close();
            PApplet.println("Device Closed.");
        }
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Create the window and add all the GUI controls to it</DD>
     * <DT>Date:</DT>
     * <DD>Sep 14, 2017</DD>
     * </DL>
     */
    protected void configureWindow() {
        G4P.setWindowColorScheme(this, GConstants.GREEN_SCHEME);
        surface.setResizable(true);
        G4P.messagesEnabled(false);
        panelAdminTop = new GPanel(this, X_GAP, 0, getCurrWidth() - 2 * X_GAP, Y_GAP + ACTION_ROW + Y_GAP, "Top");
        panelAdminTop.setOpaque(false);
        panelAdminTop.setCollapsed(false);

        panelMain = new GPanel(this, X_GAP, ACTION_ROW + 1 + Y_GAP, getCurrWidth() - 2 * X_GAP,
            getCurrHeight() - Y_GAP + BUTTON_HEIGHT + Y_GAP, "Main");
        panelMain.setOpaque(false);
        panelMain.setCollapsed(false);

        dropdown_whichDevice = createDeviceDropDown();

        setNextAdminX(dropdown_whichDevice.getX() + dropdown_whichDevice.getWidth() + X_GAP);

        dropdown_selectFile = createConfigsDropDown(getNextAdminX());

        setNextAdminX(dropdown_selectFile.getX() + dropdown_selectFile.getWidth() + 2 * X_GAP);

        panelAdminTop.addControl(dropdown_whichDevice);
        panelAdminTop.addControl(dropdown_selectFile);

        final int xPosSave = Math.round(this.getCurrWidth() - SAVE_BUTTON_X_OFFSET);
        final int xPosFileName = Math.round(this.getCurrWidth() - FILE_NAME_X_OFFSET);
        final int xPosCheckBoxUS = Math.round(this.getCurrWidth() - CHECKBOX_X_OFFSET);
        final int yPosPanel = Math.round(this.getCurrHeight() - BUTTON_HEIGHT - 2 * Y_GAP);

        textFileName =
            new GTextField(this, xPosFileName, Y_GAP, FILE_NAME_WIDTH, BUTTON_HEIGHT, GConstants.SCROLLBARS_NONE);
        textFileName.setText(DEFAULT_DEVICE_FILENAME);
        textFileName.setPromptText("Enter the name of the settings file (.cfg) to save");
        textFileName.setOpaque(false);
        //textFileName.addEventHandler(this, "textfield1_change1");

        checkboxUpdateSettings = new GCheckbox(this, xPosCheckBoxUS, Y_GAP, CHECKBOX_WIDTH, BUTTON_HEIGHT);
        checkboxUpdateSettings.setIconAlign(GAlign.LEFT, GAlign.MIDDLE);
        checkboxUpdateSettings.setText("Update Settings");
        checkboxUpdateSettings.setOpaque(false);
        checkboxUpdateSettings.setSelected(true);

        //checkboxUpdateSettings.addEventHandler(this, "checkbox1_clicked1");

        panelAdminBottom =
            new GPanel(this, X_GAP, yPosPanel, getCurrWidth() - 2 * X_GAP, BUTTON_HEIGHT + 2 * Y_GAP, "Bottom");
        panelAdminBottom.setOpaque(false);
        panelAdminBottom.setCollapsed(false);

        saveButton = new GButton(this, xPosSave, Y_GAP, BUTTON_WIDTH, BUTTON_HEIGHT, "Save & Exit");
        exitButton = new GButton(this, X_GAP, Y_GAP, BUTTON_WIDTH, BUTTON_HEIGHT, "Exit");

        panelAdminBottom.addControl(exitButton);
        panelAdminBottom.addControl(checkboxUpdateSettings);
        panelAdminBottom.addControl(textFileName);
        panelAdminBottom.addControl(saveButton);

        float currAdmin = getNextAdminX();
        final float botomAdmin = 7 * X_GAP + 2 * BUTTON_WIDTH + FILE_NAME_WIDTH + CHECKBOX_WIDTH;
        currAdmin = currAdmin > botomAdmin ? currAdmin : botomAdmin;
        setNextAdminX(currAdmin);
        setAdminMinHeight(TOTAL_ADMIN_HEIGHT);
        drawBorders(panelMain);
    }

    protected GDropList createConfigsDropDown(final float xOffset) {
        //        ControlDevice gpad = controlIO.getMatchedDevice("gamepad_eyes");
        //        if (gpad == null) {
        //          println("No suitable device configured");
        //          System.exit(-1); // End the program NOW!
        //        }
        final Path data = FileSystems.getDefault().getPath("data");
        if (!Files.exists(data)) {
            Util.initData();
        }
        //FileSystems.getDefault().getPathMatcher("./data/*.cfg");
        float len = 0;
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(data, "*.cfg")) {
            for (final Path path : stream) {
                log.info("Found Path {} ", path.toString());
                final String fileName = path.getFileName().toString();
                len = calcDropDownLength(len, fileName.length(), 200f);
                configFiles.put(fileName, path);
            }
        } catch (final IOException ex) {
            final String msg = String.format("Error reading from path '{}'", data.toString());
            log.error(msg, ex);
            final RuntimeException newEx = new RuntimeException(msg, ex);
            newEx.fillInStackTrace();
            throw newEx;
        }
        len = max(len, 200f);
        fileNames = new ArrayList<>();
        fileNames.add("Select Config File");
        fileNames.addAll(configFiles.keySet());
        final GDropList dropList =
            new GDropList(panelMain.getPApplet(), xOffset, Y_GAP, len, ACTION_ROW * ROWS_VISIBLE + 1, ROWS_VISIBLE);
        dropList.setItems(fileNames, 0);
        //       dropList.addEventHandler(panelMain.getPApplet(), "processDropListEvents");
        dropList.setSelected(0);
        return dropList;

        //data.
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Create a new GDropList listing all available devices</DD>
     * <DT>Date:</DT>
     * <DD>Sep 14, 2017</DD>
     * </DL>
     *
     * @return a new, populated GDropList
     */
    protected GDropList createDeviceDropDown() {
        controlIO = ControlIO.getInstance(this);
        numDevices = controlIO.getNumberOfDevices();
        float len = 0;
        int n = 0;
        listDevices.add("Select Input Device");
        while (n < numDevices) {
            tryDevice = controlIO.getDevice(n);
            final String devName = tryDevice.getName();
            listDevices.add(devName);
            len = calcDropDownLength(len, devName.length(), 200f);
            tryDevice.close();
            ++n;
        }

        final GDropList dropList =
            new GDropList(panelMain.getPApplet(), 0, Y_GAP, len, ACTION_ROW * ROWS_VISIBLE + 1, ROWS_VISIBLE);
        dropList.setItems(listDevices, 0);
        dropList.setSelected(0);
        //dropList.addEventHandler(panelMain.getPApplet(), "processDropListEvents");
        return dropList;
    }

    /** {@inheritDoc} */
    @Override
    public void draw() {
        this.background(150);
        this.updateAllButtonsAndSliders();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>draw a top and bottom border for the passed panel based on window height</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param target
     */
    void drawBorders(final GPanel target) {
        borderTop = makeBorder(target.getPApplet(), 0, 0, getCurrWidth() - 2 * X_GAP, 3);
        borderBottom =
            makeBorder(target.getPApplet(), 0, getCurrHeight() - TOTAL_ADMIN_HEIGHT, getCurrWidth() - 2 * X_GAP, 3);
        target.addControl(borderTop);
        target.addControl(borderBottom);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the adminMinHeight property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 14, 2017</DD>
     * </DL>
     *
     * @return the value of adminMinHeight field
     */
    protected float getAdminMinHeight() {
        return adminMinHeight;
    }

    /** {@inheritDoc} */
    @Override
    public float getCurrHeight() {
        return currHeight;
    }

    /** {@inheritDoc} */
    @Override
    public float getCurrWidth() {
        return currWidth;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the nextAdminX property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 14, 2017</DD>
     * </DL>
     *
     * @return the value of nextAdminX field
     */
    protected float getNextAdminX() {
        return nextAdminX;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the prevMaxX property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 13, 2017</DD>
     * </DL>
     *
     * @return the value of prevMaxX field
     */
    @Override
    public float getPrevMaxX() {
        return prevMaxX;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the prevMaxY property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 13, 2017</DD>
     * </DL>
     *
     * @return the value of prevMaxY field
     */
    @Override
    public float getPrevMaxY() {
        return prevMaxY;
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
     * <DD>Handle Button Events</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param button
     * @param event
     */
    public void handleButtonEvents(final GButton button, final GEvent event) {
        if (button == saveButton && event == GEvent.CLICKED) {
            PApplet.print("Saving settings... ");
            this.saveSettings();
            PApplet.println("Settings saved.");
            this.closeDevice();
            //            PApplet.println(
            //                "IMPORTANT: Remember to copy \"settings_inputDevice.txt\" from \\Processing\\InputDeviceSetupTool\\ to \\Processing\\Processing_Turret_06_09\\data\\ ");
            this.exit();
        } else if (button == exitButton && event == GEvent.CLICKED) {
            PApplet.print("Exiting with no save... ");
            this.closeDevice();
            //            PApplet.println(
            //                "IMPORTANT: Remember to copy \"settings_inputDevice.txt\" from \\Processing\\InputDeviceSetupTool\\ to \\Processing\\Processing_Turret_06_09\\data\\ ");
            this.exit();
        }

    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>handle DropList Events</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param combo
     * @param event
     */
    public void handleDropListEvents(final GDropList combo, final GEvent event) {
        log.info("in Handler for {}", combo == dropdown_whichDevice ? "dropdown_whichDevice" : "dropdown_selectFile");
        if (combo == dropdown_whichDevice) {
            whichDevice = dropdown_whichDevice.getSelectedIndex() - 1;
            if (whichDevice != numDevices) {
                this.closeDevice();
                this.openNewDevice(whichDevice);
                surface.setSize(Math.round(getCurrWidth()), Math.round(getCurrHeight()));
                //final int xPos = Math.round(this.getCurrWidth() - BUTTON_WIDTH - X_GAP);
                //final int yPos = Math.round(this.getCurrHeight() - BUTTON_HEIGHT - Y_GAP);
                final int xPosSave = Math.round(this.getCurrWidth() - SAVE_BUTTON_X_OFFSET);
                final int xPosFileName = Math.round(this.getCurrWidth() - FILE_NAME_X_OFFSET);
                final int xPosCheckBoxUS = Math.round(this.getCurrWidth() - CHECKBOX_X_OFFSET);
                final int yPosPanel = Math.round(this.getCurrHeight() - BUTTON_HEIGHT - 2 * Y_GAP);

                panelAdminBottom.moveTo(X_GAP, yPosPanel);
                saveButton.moveTo(xPosSave, Y_GAP);
                textFileName.moveTo(xPosFileName, Y_GAP);
                checkboxUpdateSettings.moveTo(xPosCheckBoxUS, Y_GAP);
                exitButton.moveTo(X_GAP, Y_GAP);
            } else {
                this.closeDevice();
            }
            dropdown_selectFile.setSelected(0);
            textFileName.setText(DEFAULT_DEVICE_FILENAME);
            lastSelectedFile = -1;
        } else if (combo == dropdown_selectFile) {
            final int selectedFile = dropdown_selectFile.getSelectedIndex();
            if (selectedFile != 0 && selectedFile != lastSelectedFile) {
                populateController(configFiles.get(fileNames.get(selectedFile)));
                textFileName.setText(fileNames.get(selectedFile));
                lastSelectedFile = selectedFile;
            }

        }
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Handle Text Events</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param textarea
     * @param event
     */
    public void handleTextEvents(final GEditableTextControl textarea, final GEvent event) {
        if (textarea == textFileName) {
            log.info("textFileName - GTextField >> GEvent. {} @ {}", event, millis());
        }
        //println(event);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>
     * <DD>Handle Text Events</DD></DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param source
     * @param event
     */
    public void handleTextEvents(final GTextField source, final GEvent event) { //_CODE_:textfield1:857853:
        println("textfield1 - GTextField >> GEvent." + event + " @ " + millis());
    } //_CODE_:textfield1:857853:

    GPanel makeBorder(final PApplet window, final float x, final float y, final float pWidth, final float pHeight) {
        final GPanel border = new GPanel(window, x, y, pWidth, pHeight, "");
        border.setCollapsible(false);
        border.setDraggable(false);
        border.setOpaque(true);
        border.setLocalColor(4, color(0, 0, 0));
        return border;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>open new control device</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param n
     */
    public void openNewDevice(final int n) {
        inputDevice = controlIO.getDevice(n);
        PApplet.println("Device Selected = " + inputDevice.getName());
        final int numButtons = inputDevice.getNumberOfButtons();
        //        final String[] buttonNames = new String[numButtons + 1];
        //        int n2 = 0;
        //        while (n2 < numButtons) {
        //            buttonNames[n2] = inputDevice.getButton(n2).getName();
        //            ++n2;
        //        }
        //        buttonNames[numButtons] = "Select a Button";
        final int numSliders = inputDevice.getNumberOfSliders();
        //        final String[] arrstring2 = new String[numSliders];
        //        int n3 = 0;
        //        while (n3 < numSliders) {
        //            arrstring2[n3] = inputDevice.getSlider(n3).getName();
        //            ++n3;
        //        }

        DeviceHelper.clear();
        deviceButtons.clear();
        deviceSliders.clear();

        setPrevMaxX(0);
        setPrevMaxY(0);

        for (int i = 0; i < numButtons; i++) {
            IDeviceHelper helper = null;
            //            if (i < deviceButtons.size()) {
            //                helper = deviceButtons.get(i);
            //            } else {
            helper = new DeviceHelper(this, panelMain, inputDevice.getButton(i), i);
            deviceButtons.add(helper);
            final float currMaxX = getPrevMaxX();
            final float currMaxY = getPrevMaxY();
            setPrevMaxX(helper.getNextXpos() > currMaxX ? helper.getNextXpos() : currMaxX);
            setPrevMaxY(helper.getNextYpos() > currMaxY ? helper.getNextYpos() : currMaxY);

            //           }
        }
        for (int i = 0; i < numSliders; i++) {
            IDeviceHelper helper = null;
            //            if (i < deviceSliders.size()) {
            //                helper = deviceSliders.get(i);
            //            } else {
            helper = new DeviceHelper(this, panelMain, inputDevice.getSlider(i), i);
            deviceSliders.add(helper);
            final float currMaxX = getPrevMaxX();
            final float currMaxY = getPrevMaxY();
            setPrevMaxX(helper.getNextXpos() > currMaxX ? helper.getNextXpos() : currMaxX);
            setPrevMaxY(helper.getNextYpos() > currMaxY ? helper.getNextYpos() : currMaxY);
            //            }
        }
        final float maxX = getPrevMaxX() > getNextAdminX() ? getPrevMaxX() : getNextAdminX();
        final float maxY = getPrevMaxY() > getAdminMinHeight() ? getPrevMaxY() : getAdminMinHeight();
        setCurrWidth(maxX);
        setCurrHeight(maxY);
        removeBorders(panelMain);
        drawBorders(panelMain);
        //panelMain.draw();

    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Populate current Control window with the settings from the config file if it is not the same control do the
     * best you can based on indexes</DD>
     * <DT>Date:</DT>
     * <DD>Sep 14, 2017</DD>
     * </DL>
     *
     * @param aPath
     */
    private void populateController(final Path aPath) {

        final Path jsonAbsoluteName = aPath.toAbsolutePath();
        String controllerName = "";
        final List<ConfigLine> configLines = new ArrayList<>();
        try (BufferedReader bfr = new BufferedReader(new FileReader(jsonAbsoluteName.toFile()))) {
            final Stream<String> lines = bfr.lines();
            final List<String> lineArray = lines.collect(Collectors.toList());
            int idx = 0;
            for (final String line : lineArray) {
                if (idx++ == 0) {
                    controllerName = line;
                } else {
                    configLines.add(new ConfigLine(line));
                }

            }
        } catch (final FileNotFoundException ex) {
            final String msg = String.format("Not Found Error reading from file '{}'", jsonAbsoluteName.toString());
            log.error(msg, ex);
            final RuntimeException newEx = new RuntimeException(msg, ex);
            newEx.fillInStackTrace();
            throw newEx;
        } catch (final IOException ex) {
            final String msg = String.format("Error reading from file '{}'", jsonAbsoluteName.toString());
            log.error(msg, ex);
            final RuntimeException newEx = new RuntimeException(msg, ex);
            newEx.fillInStackTrace();
            throw newEx;
        }
        if (dropdown_whichDevice.getSelectedText().equalsIgnoreCase(controllerName)) {
            //deviceButtons;
            for (final IDeviceHelper helper : deviceButtons) {
                helper.setSelectedText(0);
            }
            for (final IDeviceHelper helper : deviceSliders) {
                helper.setSelectedText(0);
            }

            for (final ConfigLine configLine : configLines) {
                int selected = -1;
                switch (configLine.getType()) {
                    case Button:
                        selected = DeviceButtonActions.getByString(configLine.getKey()).ordinal();
                        deviceButtons.get(configLine.getIndex()).setSelectedText(selected);
                        break;
                    case Slider:
                        selected = DeviceSliderActions.getByString(configLine.getKey()).ordinal();
                        deviceSliders.get(configLine.getIndex()).setSelectedText(selected);
                        break;

                    default: //TODO implement Hat
                        throw new NotImplementedException(
                            "This is not implemented yet for " + configLine.getType().name());
                }
            }
        }
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>process DropList Events</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param combo
     * @param event
     */
    public void processDropListEvents(final GDropList combo, final GEvent event) {
        log.info("in Handler 2 for {}", combo.toString());
        //handleDropListEvents(combo, event);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>remove old borders, because there is no resize in the G4P library</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param target
     */
    void removeBorders(final GPanel target) {
        borderTop.setVisible(false);
        borderTop.dispose();
        borderTop = null;
        borderBottom.setVisible(false);
        borderBottom.dispose();
        borderBottom = null;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Save settings to a file</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     */
    public void saveSettings() {

        final String[] arrstring = new String[49];
        String config = dropdown_whichDevice.getSelectedText() + System.lineSeparator();
        int idx = 0;
        arrstring[idx++] = "========================================";
        arrstring[idx++] = "---------------- Device ----------------";
        arrstring[idx++] = dropdown_whichDevice.getSelectedText();
        arrstring[idx++] = "";
        arrstring[idx++] = "========================================";
        arrstring[idx++] = "--------------- Buttons ----------------";
        for (final IDeviceHelper helper : deviceButtons) {
            arrstring[idx++] = helper.getSelectedText();
            config += helper.toString();
            if (idx == 36) {
                break;
            }
        }
        for (int i = idx; i < 36; i++) {
            arrstring[i] = "";
            idx = i;
        }
        idx++;
        arrstring[idx++] = "";
        arrstring[idx++] = "========================================";
        arrstring[idx++] = "--------------- Sliders ----------------";
        //idx = 39;
        for (final IDeviceHelper helper : deviceSliders) {
            arrstring[idx++] = helper.getSelectedText();
            config += helper.toString();
            if (idx == 49) {
                break;
            }
        }
        for (int i = idx; i < 49; i++) {
            arrstring[i] = "";
            idx = i;
        }
        String name = textFileName.getText();
        if (!name.toLowerCase().endsWith(".cfg")) {
            name += ".cfg";
        }
        name = name.replaceAll("\\.[Cc][Ff][Gg]$", ".cfg");
        if (name.toLowerCase().startsWith("data[\\/\\\\]+")) {
            name = name.replaceFirst("^data.", "");
        }
        final String textName = name.replaceAll("\\.cfg$", ".txt");

        log.info("Settings File Name: {}", name);
        log.info("Settings Text File Name: {}", textName);
        Util.writeFile("data" + File.separatorChar + textName, arrstring);
        Util.writeFile("data" + File.separatorChar + name, config.getBytes());
        if (checkboxUpdateSettings.isSelected()) {
            if (!getSettings().getDeviceConfig().equals(name)) {
                getSettings().setDeviceConfig(name);
                try {
                    getSettings().saveSettings();
                    log.info("Application settings 'deviceConfig' updated to {}", name);
                } catch (final IOException ex) {
                    log.error("caught IOException :", ex); //$NON-NLS-1$
                    final RuntimeException newEx = new RuntimeException("Error saving Application Settings", ex);
                    newEx.fillInStackTrace();
                    throw newEx;
                }
            }
        }

        this.delay(100);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the adminMinHeight property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 14, 2017</DD>
     * </DL>
     *
     * @param aAdminMinHeight
     *            new value for the adminMinHeight property
     */
    protected void setAdminMinHeight(final float aAdminMinHeight) {
        adminMinHeight = aAdminMinHeight;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the currHeight property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 13, 2017</DD>
     * </DL>
     *
     * @param aHeight
     *            new value for the currHeight property
     */
    protected void setCurrHeight(final float aHeight) {
        currHeight = aHeight;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the currWidth property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 13, 2017</DD>
     * </DL>
     *
     * @param aWidth
     *            new value for the currWidth property
     */
    protected void setCurrWidth(final float aWidth) {
        currWidth = aWidth;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the nextAdminX property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 14, 2017</DD>
     * </DL>
     *
     * @param aNextAdminX
     *            new value for the nextAdminX property
     */
    protected void setNextAdminX(final float aNextAdminX) {
        nextAdminX = aNextAdminX;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the prevMaxX property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 13, 2017</DD>
     * </DL>
     *
     * @param aPrevMaxX
     *            new value for the prevMaxX property
     */
    public void setPrevMaxX(final float aPrevMaxX) {
        prevMaxX = aPrevMaxX;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the prevMaxY property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 13, 2017</DD>
     * </DL>
     *
     * @param aPrevMaxY
     *            new value for the prevMaxY property
     */
    public void setPrevMaxY(final float aPrevMaxY) {
        prevMaxY = aPrevMaxY;
    }

    @Override
    public void settings() {
        setCurrHeight(displayHeight / 2 < getCurrHeight() ? getCurrHeight() : displayHeight / 2);
        setCurrWidth(displayWidth / 2 < getCurrWidth() ? getCurrWidth() : displayWidth / 2);

        log.info("opening a panel with dimensions {}x{}", getCurrWidth(), getCurrHeight());
        this.size(Math.round(getCurrWidth()), Math.round(getCurrHeight()), JAVA2D);

    }

    /** {@inheritDoc} */
    @Override
    public void setup() {
        //this.size(800, 600);
        configureWindow();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Update All Buttons and Sliders from the device</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     */
    public void updateAllButtonsAndSliders() {
        for (final IDeviceHelper helper : deviceButtons) {
            helper.update();
        }
        for (final IDeviceHelper helper : deviceSliders) {
            helper.update();
        }
        dropdown_whichDevice.draw();
    }

    //    public void updateButton(final ControlButton controllButton, final GLabel gLabel) {
    //        if (controllButton != null) {
    //            if (controllButton.pressed()) {
    //                gLabel.setOpaque(true);
    //            } else {
    //                gLabel.setOpaque(false);
    //            }
    //        }
    //    }
    //
    //    public void updateSlider(final ControlSlider controllSlider, final GSlider gWSlider) {
    //        if (controllSlider != null) {
    //            gWSlider.setValue(controllSlider.getValue());
    //        }
    //    }
}
