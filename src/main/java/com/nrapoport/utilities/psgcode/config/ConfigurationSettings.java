/**
 * @author ubuntu - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 4, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapoport.utilities.psgcode.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.annotations.Expose;
import com.nrapoport.utilities.psgcode.devices.InputDeviceSetupTool;
import com.nrapoport.utilities.psgcode.enums.ControlMode;
import com.nrapoport.utilities.psgcode.enums.Controls;
import com.nrapoport.utilities.psgcode.enums.Effects;
import com.nrapoport.utilities.psgcode.enums.FiringMode;
import com.nrapoport.utilities.psgcode.enums.Renderers;
import com.nrapoport.utilities.psgcode.enums.RunType;

/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>A class used as a structure to serialize and deserialize JSON config</DD>
 * <DT>Date:</DT>
 * <DD>Sep 4, 2017</DD>
 * </DL>
 *
 * @author ubuntu - Nick Rapoport
 */

public class ConfigurationSettings {

    //   <===============================================================================================>
    //   Begin custom values - change these camera dimensions to work with your turret
    //   <===============================================================================================>

    private final String comments_default = ""//
        + "printFrameRate=set to true to print the framerate at the bottom of the IDE window, default: false;" //$NON-NLS-1$
        + "camWidth=camera width (pixels),   usually 160*n, default: 320;" //$NON-NLS-1$
        + "camHeight=camera height (pixels),  usually 120*n, default: 240;" //$NON-NLS-1$
        + "diffPixelsColor=Red, green, blue values (0-255) to show pixel as marked as target, default: {255, 255, 0};" //$NON-NLS-1$
        + "runtType=the level of resouces to use for this run: one of Full, Minimal, MinimalWithSounds. default: Minimal;" //$NON-NLS-1$
        + "deviceConfig=the name of the device configuration file in the data folder. default: " //$NON-NLS-1$
        + InputDeviceSetupTool.DEFAULT_DEVICE_FILENAME + ";" //$NON-NLS-1$
        + "effect=one of Opaque, Transparent, Negative, NegativeAndTransparent, default: Opaque;" //$NON-NLS-1$
        + "mirrorCam=set true to mirror camera image;" //$NON-NLS-1$
        + "xMin=calibration default: 0.0;" //$NON-NLS-1$
        + "xMax=calibration default: 180.0;" //$NON-NLS-1$
        + "yMin=calibration default: 0.0;" //$NON-NLS-1$
        + "yMax=calibration default: 180.0;" //$NON-NLS-1$
        + "minBlobArea=minimum target size (pixels) default: 30;" //$NON-NLS-1$
        + "tolerance=sensitivity to motion default: 100;" //$NON-NLS-1$
        + "runWithoutArduino=If Arduino board is not available default: false;" //$NON-NLS-1$
        + "rendererType=The type of render to use (this is a Processing display option). One of P2D, P3D, OPENGL, FX2D, PDF, SVG, DXF default: P2D;" //$NON-NLS-1$
        + "smoothingFactor=smoothing factor, default: 0.8f;" //$NON-NLS-1$
        + "firingMode=One of Automatic, SemiAuto default: SemiAuto;" //$NON-NLS-1$
        + "safety=Enable the weapon (allow to fire), default: true;" //$NON-NLS-1$
        + "controlMode=One of Manual, Autonomous, default: Manual;" //$NON-NLS-1$
        + "soundEffects=set to true to enable sound effects by default, default: false;" //$NON-NLS-1$
        + "idleTime=how many milliseconds to wait until scanning (when in scan mode), default: 10000;" //$NON-NLS-1$
        + "trackColor=the color that's used for tracking, default: minSize 0 tolerance 100 and RGB {255, 255, 255};" //$NON-NLS-1$
        + "safeColor=the color that's used for safe, default: minSize 500 tolerance 100, and RGB {255, 255, 255};" //$NON-NLS-1$
        + "controls=One of Device or Mouse, default: Mouse;" //$NON-NLS-1$
        + "leadTarget=Do the anticipation,('lead') calculations, default: true;" //$NON-NLS-1$
        + "nbDot=number of dot for anticipation minimum 2, default: 10;" //$NON-NLS-1$
        + "antSens=sensitivity of anticipation, default: 10;" //$NON-NLS-1$
        + "useArrowKeys=use arrow keys to finely adjust the aiming (in manual mode), default: false;" //$NON-NLS-1$
        + "propX=proportionality of anticipation 1 is Width / more is Less, default:0.67;" //$NON-NLS-1$
        + "propY=proportionality of anticipation 1 is Hight / more is Less, default: 0.11;" //$NON-NLS-1$
        + "soundInterval=simple counter limit that will determine how often to play a random sound (if enabled) default: 1000;" //$NON-NLS-1$
        + "numberOfVoices=The number of voices passed to the Sampler (see Minim documentation) , default: 21;" //$NON-NLS-1$
        + "samplerDelay=Time (in milliseconds) to wait to unpatch a Sampler (see Minim documentation), default: 2500;" //$NON-NLS-1$
        + "soundFiles=list of sound files that will play sounds for both random and as a response to activity if sounds are enabled, order is important;" //$NON-NLS-1$
    ;

    private final @Expose Map<String, String> comments = Arrays.stream(comments_default.split(";"))
        .map(s -> s.split("=")).collect(Collectors.toMap( //
            a -> a[0], //key
            a -> a[1], //value
            (u, v) -> {
                throw new IllegalStateException(String.format("Duplicate key %s", u));
            }, LinkedHashMap::new //I want to keep the order
    ));

    private @Expose CustomizableSettings customizable = new CustomizableSettings();

    private @Expose Effects effect = Effects.Opaque;

    private @Expose boolean mirrorCam = false; //   set true to mirror camera image

    private @Expose float xMin = 0.0f; //  Actual calibration values are loaded from "settings.json".

    private @Expose float xMax = 180.0f; //  If "settings.txt" is broken / unavailable, these defaults are used instead -

    private @Expose float yMin = 0.0f; //  otherwise, changing these lines will have no effect on your gun's calibration.

    private @Expose float yMax = 180.0f; //

    private @Expose int minBlobArea = 30; //   minimum target size (pixels)

    private @Expose int tolerance = 100; //   sensitivity to motion

    private @Expose boolean runWithoutArduino = false;

    private @Expose Renderers rendererType = Renderers.P2D;

    private @Expose float smoothingFactor = 0.8f; // smoothing

    private @Expose boolean activeSmoothing = true;

    //    private @Expose boolean printFrameRate = false; // set to true to print the framerate at the bottom of the IDE window
    //
    //    private @Expose int camWidth = 320; //   camera width (pixels),   usually 160*n
    //
    //    private @Expose int camHeight = 240; //   camera height (pixels),  usually 120*n
    //
    //    private @Expose ConfigurationColor diffPixelsColor = new ConfigurationColor(255, 255, 0); // Red, green, blue values (0-255)  to show pixel as marked as target
    //
    //    // private int[] diffPixelsColorx = { 255, 255, 0 }; // Red, green, blue values (0-255)  to show pixel as marked as target

    //   <===============================================================================================>
    //   End custom values
    //   <===============================================================================================>

    private @Expose boolean useSafeColor = false;

    private @Expose boolean showDifferentPixels = false;

    private @Expose boolean showTargetBox = true;

    private @Expose boolean showCameraView = true;

    //private @Expose boolean firingMode = true; // true = semi,        false = auto
    private @Expose FiringMode firingMode = FiringMode.SemiAuto;

    //private @Expose int effect = 0; // Effect

    private @Expose boolean safety = true;

    //private @Expose boolean controlMode = false; // true = autonomous,  false = manual
    private @Expose ControlMode controlMode = ControlMode.Manual;

    private @Expose boolean soundEffects = false; // set to true to enable sound effects by default

    private @Expose boolean scanWhenIdle = true;

    private @Expose boolean trackingMotion = true;

    //
    private @Expose int idleTime = 10000; // how many milliseconds to wait until scanning (when in scan mode)
    //
    //    int idleBeginTime = 0;
    //
    //    boolean scan = false;
    //
    //    public String serPortUsed;
    //
    //    int[][] fireRestrictedZones = new int[30][4];
    //
    //    int restrictedZone = 1;
    //

    private @Expose boolean showRestrictedZones = false;

    private @Expose boolean trackingColor = false;

    private @Expose ColorManagement trackColor = new ColorManagement(0, 100, new ConfigurationColor(255, 255, 255));

    private @Expose ColorManagement safeColor = new ColorManagement(500, 100, new ConfigurationColor(255, 255, 255));
    //    private @Expose int trackColorTolerance = 100;

    private @Expose Controls controls = Controls.Mouse;

    boolean leadTarget = true;

    private @Expose int nbDot = 10; // nomber of dot for anticipation minimum 2

    private @Expose int antSens = 10; // sensitivity of anticipation

    private @Expose boolean useArrowKeys = false; // use arrow keys to finely adjust the aiming (in manual mode)

    private @Expose float propX = 0.67f; // proportionality of anticipation 1 is Width / more is Less

    private @Expose float propY = 0.11f; // proportionality of anticipation 1 is Hight / more is Less

    private @Expose int soundInterval = 1000;

    private @Expose int numberOfVoices = 21;

    private @Expose long samplerDelay = 2500;

    private @Expose List<String> soundFiles = new ArrayList<>(Arrays.asList( //
        "data/your business is appreciated.wav", //$NON-NLS-1$
        "data/who's there.wav", //$NON-NLS-1$
        "data/there you are.wav", //$NON-NLS-1$
        "data/there you are(2).wav", //$NON-NLS-1$
        "data/target lost.wav", //$NON-NLS-1$
        "data/target aquired.wav", //$NON-NLS-1$
        "data/sleep mode activated.wav", //$NON-NLS-1$
        "data/sentry mode activated.wav", //$NON-NLS-1$
        "data/no hard feelings.wav", //$NON-NLS-1$
        "data/is anyone there.wav", //$NON-NLS-1$
        "data/i see you.wav", //$NON-NLS-1$
        "data/i dont hate you.wav", //$NON-NLS-1$
        "data/i dont blame you.wav", //$NON-NLS-1$
        "data/hey its me.wav", //$NON-NLS-1$
        "data/hello.wav", //$NON-NLS-1$
        "data/gotcha.wav", //$NON-NLS-1$
        "data/dispensing product.wav", //$NON-NLS-1$
        "data/deploying.wav", //$NON-NLS-1$
        "data/could you come over here.wav", //$NON-NLS-1$
        "data/are you still there.wav", //$NON-NLS-1$
        "data/activated.wav" //$NON-NLS-1$
    ));

    private @Expose String website = "http://psg.rudolphlabs.com/";

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>com.nrapoport.utilities.psgcode.ConfigurationSettings Constructor</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     */
    public ConfigurationSettings() {
        super();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Flip the enum controlMode property value between {@link ControlMode#Autonomous } and
     * {@link ControlMode#Manual}</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     */
    public void flipControlMode() {
        setControlMode(ControlMode.flip(getControlMode()));
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Flip the enum controls property value between {@link Controls#Device} and {@link Controls#Mouse}</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     */
    public void flipControls() {
        setControls(Controls.flip(getControls()));
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>swap xMin and xMax values</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     */
    public void flipxMinxMax() {
        final float oldxMin = getxMin();
        final float oldxMax = getxMax();
        setxMin(oldxMax);
        setxMax(oldxMin);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>swap yMin and yMax values</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     */
    public void flipyMinyMax() {
        final float oldyMin = getyMin();
        final float oldyMax = getyMax();
        setyMin(oldyMax);
        setyMax(oldyMin);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the antSens property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of antSens field
     */
    public int getAntSens() {
        return antSens;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getCamHeight method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.CustomizableSettings#getCamHeight()
     */
    public int getCamHeight() {
        return customizable.getCamHeight();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getCamWidth method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.CustomizableSettings#getCamWidth()
     */
    public int getCamWidth() {
        return customizable.getCamWidth();
    }

    //    private @Expose int trackColorRed = 255;

    //    private @Expose int trackColorGreen = 255;

    //    private @Expose int trackColorBlue = 255;

    //    private @Expose int safeColorMinSize = 500;

    //    private @Expose int safeColorTolerance = 100;

    //    private @Expose int safeColorRed = 0;

    //   private @Expose int safeColorGreen = 255;

    //    private @Expose int safeColorBlue = 0;

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the controlMode property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of controlMode field
     */
    public ControlMode getControlMode() {
        return controlMode;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the controlMode property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @return the value of controlMode field
     */
    public int getControlModeInt() {
        return controlMode.getId();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the controls property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of controls field
     */
    public Controls getControls() {
        return controls;
    }

    //private @Expose boolean useInputDevice = false; // use a joystick or game Controller as input (in manual mode) now it's controls enum

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the customizable property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of customizable field
     */
    public CustomizableSettings getCustomizable() {
        return customizable;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getDeviceConfig method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.CustomizableSettings#getDeviceConfig()
     */
    public String getDeviceConfig() {
        return customizable.getDeviceConfig();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getDiffPixelsColor method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.CustomizableSettings#getDiffPixelsColor()
     */
    public ConfigurationColor getDiffPixelsColor() {
        return customizable.getDiffPixelsColor();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getDiffPixelsColorArray method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.CustomizableSettings#getDiffPixelsColorArray()
     */
    public int[] getDiffPixelsColorArray() {
        return customizable.getDiffPixelsColorArray();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the displayX property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @return the value of displayX field
     */
    public int getDisplayX() {
        return getCamWidth() / 2;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the displayY property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @return the value of displayY field
     */
    public int getDisplayY() {
        return getCamHeight() / 2;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the effect property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of effect field
     */
    public Effects getEffect() {
        return effect;
    }

    //private int soundTimer = 0;

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the effect property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @return the value of effect field
     */
    public int getEffectInt() {
        return getEffect().getId();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the firingMode property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of firingMode field
     */
    public FiringMode getFiringMode() {
        return firingMode;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the firingMode index property (used with the drop down in the ControlPanel</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @return the value of firingMode field
     */
    public int getFiringModeIndex() {
        return firingMode.ordinal();
    }

    //    /**
    //     * <DL>
    //     * <DT>Description:</DT>
    //     * <DD>com.nrapoport.utilities.psgcode.ConfigurationSettings Constructor</DD>
    //     * <DT>Date:</DT>
    //     * <DD>Sep 4, 2017</DD>
    //     * </DL>
    //     */
    //    public ConfigurationSettings(final boolean aPrintFrameRate, final int aCamWidth, final int aCamHeight,
    //            final int[] aDiffPixelsColor, final Effects aEffect, final boolean aMirrorCam, final float aXMin,
    //            final float aXMax, final float aYMin, final float aYMax, final int aMinBlobArea, final int aTolerance,
    //            final boolean aRunWithoutArduino, final Renderers aRendererType, final float aSmoothingFactor,
    //            final boolean aActiveSmoothing, final boolean aSafeColor, final boolean aShowDifferentPixels,
    //            final boolean aShowTargetBox, final boolean aShowCameraView, final FiringMode aFiringMode,
    //            final boolean aSafety, final ControlMode aControlMode, final boolean aSoundEffects,
    //            final boolean aScanWhenIdle, final boolean aTrackingMotion, final int aIdleTime,
    //            final boolean aShowRestrictedZones, final boolean aTrackingColor, final int aTrackColorTolerance,
    //            final int aTrackColorRed, final int aTrackColorGreen, final int aTrackColorBlue,
    //            final int aSafeColorMinSize, final int aSafeColorTolerance, final int aSafeColorRed,
    //            final int aSafeColorGreen, final int aSafeColorBlue, final Controls aControls, final boolean aLeadTarget,
    //            final int aNbDot, final int aAntSens, final boolean aUseArrowKeys, final float aPropX, final float aPropY,
    //            final int aSoundInterval, final List<String> aSoundFiles, final String aWebsite) {
    //        printFrameRate = aPrintFrameRate;
    //        camWidth = aCamWidth;
    //        camHeight = aCamHeight;
    //        diffPixelsColor = aDiffPixelsColor;
    //        effect = aEffect;
    //        mirrorCam = aMirrorCam;
    //        xMin = aXMin;
    //        xMax = aXMax;
    //        yMin = aYMin;
    //        yMax = aYMax;
    //        minBlobArea = aMinBlobArea;
    //        tolerance = aTolerance;
    //        runWithoutArduino = aRunWithoutArduino;
    //        rendererType = aRendererType;
    //        smoothingFactor = aSmoothingFactor;
    //        activeSmoothing = aActiveSmoothing;
    //        useSafeColor = aSafeColor;
    //        showDifferentPixels = aShowDifferentPixels;
    //        showTargetBox = aShowTargetBox;
    //        showCameraView = aShowCameraView;
    //        firingMode = aFiringMode;
    //        safety = aSafety;
    //        controlMode = aControlMode;
    //        soundEffects = aSoundEffects;
    //        scanWhenIdle = aScanWhenIdle;
    //        trackingMotion = aTrackingMotion;
    //        idleTime = aIdleTime;
    //        showRestrictedZones = aShowRestrictedZones;
    //        trackingColor = aTrackingColor;
    //        trackColorTolerance = aTrackColorTolerance;
    //        trackColorRed = aTrackColorRed;
    //        trackColorGreen = aTrackColorGreen;
    //        trackColorBlue = aTrackColorBlue;
    //        safeColorMinSize = aSafeColorMinSize;
    //        safeColorTolerance = aSafeColorTolerance;
    //        safeColorRed = aSafeColorRed;
    //        safeColorGreen = aSafeColorGreen;
    //        safeColorBlue = aSafeColorBlue;
    //        controls = aControls;
    //        leadTarget = aLeadTarget;
    //        nbDot = aNbDot;
    //        antSens = aAntSens;
    //        useArrowKeys = aUseArrowKeys;
    //        propX = aPropX;
    //        propY = aPropY;
    //        soundInterval = aSoundInterval;
    //        soundFiles = aSoundFiles;
    //        website = aWebsite;
    //    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the firingMode property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @return the value of firingMode field
     */
    public int getFiringModeInt() {
        return firingMode.getId();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the idleTime property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of idleTime field
     */
    public int getIdleTime() {
        return idleTime;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the minBlobArea property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of minBlobArea field
     */
    public int getMinBlobArea() {
        return minBlobArea;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the nbDot property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of nbDot field
     */
    public int getNbDot() {
        return nbDot;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the numberOfVoices property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 6, 2017</DD>
     * </DL>
     *
     * @return the value of numberOfVoices field
     */
    public int getNumberOfVoices() {
        return numberOfVoices;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the propX property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of propX field
     */
    public float getPropX() {
        return propX;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the propY property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of propY field
     */
    public float getPropY() {
        return propY;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the rendererType property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of rendererType field
     */
    public Renderers getRendererType() {
        return rendererType;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getRunType method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 9, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.CustomizableSettings#getRunType()
     */
    public RunType getRunType() {
        return customizable.getRunType();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the safeColor property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of safeColor field
     */
    public ColorManagement getSafeColor() {
        return safeColor;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the safeColorBlue property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of safeColorBlue field
     */
    public int getSafeColorBlue() {
        return getSafeColor().getColor().getBlue();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the safeColorGreen property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of safeColorGreen field
     */
    public int getSafeColorGreen() {
        return getSafeColor().getColor().getGreen();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the safeColorMinSize property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of safeColorMinSize field
     */
    public int getSafeColorMinSize() {
        return getSafeColor().getMinSize();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the safeColorRed property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of safeColorRed field
     */
    public int getSafeColorRed() {
        return getSafeColor().getColor().getRed();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the safeColorTolerance property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of safeColorTolerance field
     */
    public int getSafeColorTolerance() {
        return getSafeColor().getTolerance();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the samplerDelay property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 6, 2017</DD>
     * </DL>
     *
     * @return the value of samplerDelay field
     */
    public long getSamplerDelay() {
        return samplerDelay;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the smoothingFactor property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of smoothingFactor field
     */
    public float getSmoothingFactor() {
        return smoothingFactor;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the soundFiles property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of soundFiles field
     */
    public List<String> getSoundFiles() {
        return new ArrayList<>(soundFiles);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the soundInterval property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of soundInterval field
     */
    public int getSoundInterval() {
        return soundInterval;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the tolerance property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of tolerance field
     */
    public int getTolerance() {
        return tolerance;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the trackColor property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of trackColor field
     */
    public ColorManagement getTrackColor() {
        return trackColor;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the trackColorBlue property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of trackColorBlue field
     */
    public int getTrackColorBlue() {
        return getTrackColor().getColor().getBlue();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the trackColorGreen property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of trackColorGreen field
     */
    public int getTrackColorGreen() {
        return getTrackColor().getColor().getGreen();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the trackColorRed property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of trackColorRed field
     */
    public int getTrackColorRed() {
        return getTrackColor().getColor().getRed();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the trackColorTolerance property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of trackColorTolerance field
     */
    public int getTrackColorTolerance() {
        return getTrackColor().getTolerance();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the website property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of website field
     */
    public String getWebsite() {
        return website;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the xMax property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of xMax field
     */
    public float getxMax() {
        return xMax;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the xMin property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of xMin field
     */
    public float getxMin() {
        return xMin;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>return a calculated xRatio getCamWidth() / (xMax - xMin);</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return a calculated xRatio
     */
    public float getxRatio() {
        return getCamWidth() / (xMax - xMin);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the yMax property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of yMax field
     */
    public float getyMax() {
        return yMax;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the yMin property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of yMin field
     */
    public float getyMin() {
        return yMin;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the yRatio property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @return the value of yRatio field
     */
    public float getyRatio() {
        return getCamHeight() / (yMax - yMin);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the activeSmoothing property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of activeSmoothing field
     */
    public boolean isActiveSmoothing() {
        return activeSmoothing;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the leadTarget property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of leadTarget field
     */
    public boolean isLeadTarget() {
        return leadTarget;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the mirrorCam property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of mirrorCam field
     */
    public boolean isMirrorCam() {
        return mirrorCam;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the isPrintFrameRate method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.CustomizableSettings#isPrintFrameRate()
     */
    public boolean isPrintFrameRate() {
        return customizable.isPrintFrameRate();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the runWithoutArduino property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of runWithoutArduino field
     */
    public boolean isRunWithoutArduino() {
        return runWithoutArduino;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the safety property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of safety field
     */
    public boolean isSafety() {
        return safety;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the scanWhenIdle property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of scanWhenIdle field
     */
    public boolean isScanWhenIdle() {
        return scanWhenIdle;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the showCameraView property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of showCameraView field
     */
    public boolean isShowCameraView() {
        return showCameraView;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the showDifferentPixels property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of showDifferentPixels field
     */
    public boolean isShowDifferentPixels() {
        return showDifferentPixels;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the showRestrictedZones property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of showRestrictedZones field
     */
    public boolean isShowRestrictedZones() {
        return showRestrictedZones;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the showTargetBox property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of showTargetBox field
     */
    public boolean isShowTargetBox() {
        return showTargetBox;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the soundEffects property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of soundEffects field
     */
    public boolean isSoundEffects() {
        return soundEffects;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the trackingColor property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of trackingColor field
     */
    public boolean isTrackingColor() {
        return trackingColor;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the trackingMotion property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of trackingMotion field
     */
    public boolean isTrackingMotion() {
        return trackingMotion;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the useArrowKeys property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of useArrowKeys field
     */
    public boolean isUseArrowKeys() {
        return useArrowKeys;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the useSafeColor property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of useSafeColor field
     */
    public boolean isUseSafeColor() {
        return useSafeColor;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Rotate over the various <code>Effects</effect> for the effect property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @param id
     */
    public void radioEffect() {
        setEffect(Effects.radioEffect(getEffect()));
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the activeSmoothing property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aActiveSmoothing
     *            new value for the activeSmoothing property
     */
    public void setActiveSmoothing(final boolean aActiveSmoothing) {
        activeSmoothing = aActiveSmoothing;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the antSens property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aAntSens
     *            new value for the antSens property
     */
    public void setAntSens(final int aAntSens) {
        antSens = aAntSens;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setCamHeight method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aCamHeight
     * @see com.nrapoport.utilities.psgcode.config.CustomizableSettings#setCamHeight(int)
     */
    public void setCamHeight(final int aCamHeight) {
        customizable.setCamHeight(aCamHeight);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setCamWidth method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aCamWidth
     * @see com.nrapoport.utilities.psgcode.config.CustomizableSettings#setCamWidth(int)
     */
    public void setCamWidth(final int aCamWidth) {
        customizable.setCamWidth(aCamWidth);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the controlMode property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aControlMode
     *            new value for the controlMode property
     */
    public void setControlMode(final ControlMode aControlMode) {
        controlMode = aControlMode;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the controls property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aControls
     *            new value for the controls property
     */
    public void setControls(final Controls aControls) {
        controls = aControls;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the customizable property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aCustomizable
     *            new value for the customizable property
     */
    public void setCustomizable(final CustomizableSettings aCustomizable) {
        customizable = aCustomizable;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setDeviceConfig method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param aDeviceConfig
     * @see com.nrapoport.utilities.psgcode.config.CustomizableSettings#setDeviceConfig(java.lang.String)
     */
    public void setDeviceConfig(final String aDeviceConfig) {
        customizable.setDeviceConfig(aDeviceConfig);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setDiffPixelsColor method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aDiffPixelsColor
     * @see com.nrapoport.utilities.psgcode.config.CustomizableSettings#setDiffPixelsColor(com.nrapoport.utilities.psgcode.config.ConfigurationColor)
     */
    public void setDiffPixelsColor(final ConfigurationColor aDiffPixelsColor) {
        customizable.setDiffPixelsColor(aDiffPixelsColor);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setDiffPixelsColorFromArray method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aDiffPixelsColor
     * @see com.nrapoport.utilities.psgcode.config.CustomizableSettings#setDiffPixelsColorFromArray(int[])
     */
    public void setDiffPixelsColorFromArray(final int[] aDiffPixelsColor) {
        customizable.setDiffPixelsColorFromArray(aDiffPixelsColor);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the effect property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aEffect
     *            new value for the effect property
     */
    public void setEffect(final Effects aEffect) {
        effect = aEffect;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the firingMode property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aFiringMode
     *            new value for the firingMode property
     */
    public void setFiringMode(final FiringMode aFiringMode) {
        firingMode = aFiringMode;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the idleTime property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aIdleTime
     *            new value for the idleTime property
     */
    public void setIdleTime(final int aIdleTime) {
        idleTime = aIdleTime;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the leadTarget property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aLeadTarget
     *            new value for the leadTarget property
     */
    public void setLeadTarget(final boolean aLeadTarget) {
        leadTarget = aLeadTarget;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the minBlobArea property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aMinBlobArea
     *            new value for the minBlobArea property
     */
    public void setMinBlobArea(final int aMinBlobArea) {
        minBlobArea = aMinBlobArea;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the mirrorCam property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aMirrorCam
     *            new value for the mirrorCam property
     */
    public void setMirrorCam(final boolean aMirrorCam) {
        mirrorCam = aMirrorCam;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the nbDot property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aNbDot
     *            new value for the nbDot property
     */
    public void setNbDot(final int aNbDot) {
        nbDot = aNbDot;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the numberOfVoices property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 6, 2017</DD>
     * </DL>
     *
     * @param aNumberOfVoices
     *            new value for the numberOfVoices property
     */
    public void setNumberOfVoices(final int aNumberOfVoices) {
        numberOfVoices = aNumberOfVoices;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setPrintFrameRate method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aPrintFrameRate
     * @see com.nrapoport.utilities.psgcode.config.CustomizableSettings#setPrintFrameRate(boolean)
     */
    public void setPrintFrameRate(final boolean aPrintFrameRate) {
        customizable.setPrintFrameRate(aPrintFrameRate);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the propX property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aPropX
     *            new value for the propX property
     */
    public void setPropX(final float aPropX) {
        propX = aPropX;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the propY property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aPropY
     *            new value for the propY property
     */
    public void setPropY(final float aPropY) {
        propY = aPropY;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the rendererType property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aRendererType
     *            new value for the rendererType property
     */
    public void setRendererType(final Renderers aRendererType) {
        rendererType = aRendererType;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setRunType method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 9, 2017</DD>
     * </DL>
     *
     * @param aRunType
     * @see com.nrapoport.utilities.psgcode.config.CustomizableSettings#setRunType(com.nrapoport.utilities.psgcode.enums.RunType)
     */
    public void setRunType(final RunType aRunType) {
        customizable.setRunType(aRunType);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the runWithoutArduino property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aRunWithoutArduino
     *            new value for the runWithoutArduino property
     */
    public void setRunWithoutArduino(final boolean aRunWithoutArduino) {
        runWithoutArduino = aRunWithoutArduino;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the safeColor property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aSafeColor
     *            new value for the safeColor property
     */
    public void setSafeColor(final ColorManagement aSafeColor) {
        safeColor = aSafeColor;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the safeColorBlue property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aSafeColorBlue
     *            new value for the safeColorBlue property
     */
    public void setSafeColorBlue(final int aSafeColorBlue) {
        getSafeColor().getColor().setBlue(aSafeColorBlue);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the safeColorGreen property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aSafeColorGreen
     *            new value for the safeColorGreen property
     */
    public void setSafeColorGreen(final int aSafeColorGreen) {
        getSafeColor().getColor().setGreen(aSafeColorGreen);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the safeColorMinSize property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aSafeColorMinSize
     *            new value for the safeColorMinSize property
     */
    public void setSafeColorMinSize(final int aSafeColorMinSize) {
        getSafeColor().setMinSize(aSafeColorMinSize);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the safeColorRed property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aSafeColorRed
     *            new value for the safeColorRed property
     */
    public void setSafeColorRed(final int aSafeColorRed) {
        getSafeColor().getColor().setRed(aSafeColorRed);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the safeColorTolerance property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aSafeColorTolerance
     *            new value for the safeColorTolerance property
     */
    public void setSafeColorTolerance(final int aSafeColorTolerance) {
        getSafeColor().setTolerance(aSafeColorTolerance);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the safety property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aSafety
     *            new value for the safety property
     */
    public void setSafety(final boolean aSafety) {
        safety = aSafety;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the samplerDelay property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 6, 2017</DD>
     * </DL>
     *
     * @param aSamplerDelay
     *            new value for the samplerDelay property
     */
    public void setSamplerDelay(final long aSamplerDelay) {
        samplerDelay = aSamplerDelay;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the scanWhenIdle property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aScanWhenIdle
     *            new value for the scanWhenIdle property
     */
    public void setScanWhenIdle(final boolean aScanWhenIdle) {
        scanWhenIdle = aScanWhenIdle;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the showCameraView property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aShowCameraView
     *            new value for the showCameraView property
     */
    public void setShowCameraView(final boolean aShowCameraView) {
        showCameraView = aShowCameraView;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the showDifferentPixels property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aShowDifferentPixels
     *            new value for the showDifferentPixels property
     */
    public void setShowDifferentPixels(final boolean aShowDifferentPixels) {
        showDifferentPixels = aShowDifferentPixels;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the showRestrictedZones property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aShowRestrictedZones
     *            new value for the showRestrictedZones property
     */
    public void setShowRestrictedZones(final boolean aShowRestrictedZones) {
        showRestrictedZones = aShowRestrictedZones;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the showTargetBox property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aShowTargetBox
     *            new value for the showTargetBox property
     */
    public void setShowTargetBox(final boolean aShowTargetBox) {
        showTargetBox = aShowTargetBox;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the smoothingFactor property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aSmoothingFactor
     *            new value for the smoothingFactor property
     */
    public void setSmoothingFactor(final float aSmoothingFactor) {
        smoothingFactor = aSmoothingFactor;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the soundEffects property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aSoundEffects
     *            new value for the soundEffects property
     */
    public void setSoundEffects(final boolean aSoundEffects) {
        soundEffects = aSoundEffects;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the soundFiles property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aSoundFiles
     *            new value for the soundFiles property
     */
    public void setSoundFiles(final List<String> aSoundFiles) {
        soundFiles = aSoundFiles;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the soundInterval property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aSoundInterval
     *            new value for the soundInterval property
     */
    public void setSoundInterval(final int aSoundInterval) {
        soundInterval = aSoundInterval;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the tolerance property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aTolerance
     *            new value for the tolerance property
     */
    public void setTolerance(final int aTolerance) {
        tolerance = aTolerance;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the trackColor property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aTrackColor
     *            new value for the trackColor property
     */
    public void setTrackColor(final ColorManagement aTrackColor) {
        trackColor = aTrackColor;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the trackColorBlue property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aTrackColorBlue
     *            new value for the trackColorBlue property
     */
    public void setTrackColorBlue(final int aTrackColorBlue) {
        getTrackColor().getColor().setBlue(aTrackColorBlue);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the trackColorGreen property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aTrackColorGreen
     *            new value for the trackColorGreen property
     */
    public void setTrackColorGreen(final int aTrackColorGreen) {
        getTrackColor().getColor().setGreen(aTrackColorGreen);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the trackColorRed property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aTrackColorRed
     *            new value for the trackColorRed property
     */
    public void setTrackColorRed(final int aTrackColorRed) {
        getTrackColor().getColor().setRed(aTrackColorRed);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the trackColorTolerance property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aTrackColorTolerance
     *            new value for the trackColorTolerance property
     */
    public void setTrackColorTolerance(final int aTrackColorTolerance) {
        getTrackColor().setTolerance(aTrackColorTolerance);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the trackingColor property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aTrackingColor
     *            new value for the trackingColor property
     */
    public void setTrackingColor(final boolean aTrackingColor) {
        trackingColor = aTrackingColor;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the trackingMotion property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aTrackingMotion
     *            new value for the trackingMotion property
     */
    public void setTrackingMotion(final boolean aTrackingMotion) {
        trackingMotion = aTrackingMotion;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the useArrowKeys property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aUseArrowKeys
     *            new value for the useArrowKeys property
     */
    public void setUseArrowKeys(final boolean aUseArrowKeys) {
        useArrowKeys = aUseArrowKeys;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the useSafeColor property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aSafeColor
     *            new value for the useSafeColor property
     */
    public void setUseSafeColor(final boolean aSafeColor) {
        useSafeColor = aSafeColor;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the website property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aWebsite
     *            new value for the website property
     */
    public void setWebsite(final String aWebsite) {
        website = aWebsite;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the xMax property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aXMax
     *            new value for the xMax property
     */
    public void setxMax(final float aXMax) {
        xMax = aXMax;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the xMin property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aXMin
     *            new value for the xMin property
     */
    public void setxMin(final float aXMin) {
        xMin = aXMin;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the yMax property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aYMax
     *            new value for the yMax property
     */
    public void setyMax(final float aYMax) {
        yMax = aYMax;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the yMin property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aYMin
     *            new value for the yMin property
     */
    public void setyMin(final float aYMin) {
        yMin = aYMin;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Toggle the useArrowKeys boolean property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     */
    public void toggleUseArrowKeys() {
        setUseArrowKeys(!isUseArrowKeys());
    }
}
