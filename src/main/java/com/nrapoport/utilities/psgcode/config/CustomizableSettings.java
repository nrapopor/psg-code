/**
 * @author ubuntu - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 4, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapoport.utilities.psgcode.config;

import com.google.gson.annotations.Expose;
import com.nrapoport.utilities.psgcode.devices.InputDeviceSetupTool;
import com.nrapoport.utilities.psgcode.enums.RunType;

/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>CustomizableSettings hold settings that can be tweaked safely to match your environment</DD>
 * <DT>Date:</DT>
 * <DD>Sep 4, 2017</DD>
 * </DL>
 *
 * @author ubuntu - Nick Rapoport
 *
 */
public class CustomizableSettings {
    @SuppressWarnings("unused")
    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CustomizableSettings.class);

    private @Expose boolean printFrameRate = false; // set to true to print the framerate at the bottom of the IDE window

    private @Expose int camWidth = 320; //   camera width (pixels),   usually 160*n

    private @Expose int camHeight = 240; //   camera height (pixels),  usually 120*n

    private @Expose ConfigurationColor diffPixelsColor = new ConfigurationColor(255, 255, 0); // Red, green, blue values (0-255)  to show pixel as marked as target

    private @Expose int retinaPeriodMillis = 50; // length of period to use for setting the retina image for aiming

    private @Expose RunType runType = RunType.Minimal;

    private @Expose String deviceConfig = InputDeviceSetupTool.DEFAULT_DEVICE_FILENAME;

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>CustomizableSettings Constructor</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     */
    public CustomizableSettings() {
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the camHeight property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of camHeight field
     */
    public int getCamHeight() {
        return camHeight;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the camWidth property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of camWidth field
     */
    public int getCamWidth() {
        return camWidth;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the deviceConfig property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @return the value of deviceConfig field
     */
    public String getDeviceConfig() {
        return deviceConfig;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the diffPixelsColor property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of diffPixelsColor field
     */
    public ConfigurationColor getDiffPixelsColor() {
        return diffPixelsColor;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the diffPixelsColor property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of diffPixelsColor field
     */
    public int[] getDiffPixelsColorArray() {
        return diffPixelsColor.asArray();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the retinaPeriodMillis property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 18, 2017</DD>
     * </DL>
     *
     * @return the value of retinaPeriodMillis field
     */
    public int getRetinaPeriodMillis() {
        return retinaPeriodMillis;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the runType property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 9, 2017</DD>
     * </DL>
     *
     * @return the value of runType field
     */
    public RunType getRunType() {
        return runType;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the printFrameRate property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of printFrameRate field
     */
    public boolean isPrintFrameRate() {
        return printFrameRate;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the camHeight property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aCamHeight
     *            new value for the camHeight property
     */
    public void setCamHeight(final int aCamHeight) {
        camHeight = aCamHeight;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the camWidth property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aCamWidth
     *            new value for the camWidth property
     */
    public void setCamWidth(final int aCamWidth) {
        camWidth = aCamWidth;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the deviceConfig property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param aDeviceConfig
     *            new value for the deviceConfig property
     */
    public void setDeviceConfig(final String aDeviceConfig) {
        deviceConfig = aDeviceConfig;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the diffPixelsColor property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aDiffPixelsColor
     *            new value for the diffPixelsColor property
     */
    public void setDiffPixelsColor(final ConfigurationColor aDiffPixelsColor) {
        diffPixelsColor = aDiffPixelsColor;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the diffPixelsColor property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aDiffPixelsColor
     *            new value for the diffPixelsColor property
     */
    public void setDiffPixelsColorFromArray(final int[] aDiffPixelsColor) {
        diffPixelsColor.fromArray(aDiffPixelsColor);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the printFrameRate property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aPrintFrameRate
     *            new value for the printFrameRate property
     */
    public void setPrintFrameRate(final boolean aPrintFrameRate) {
        printFrameRate = aPrintFrameRate;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the retinaPeriodMillis property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 18, 2017</DD>
     * </DL>
     *
     * @param aRetinaPeriodMillis
     *            new value for the retinaPeriodMillis property
     */
    public void setRetinaPeriodMillis(final int aRetinaPeriodMillis) {
        retinaPeriodMillis = aRetinaPeriodMillis;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the runType property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 9, 2017</DD>
     * </DL>
     *
     * @param aRunType
     *            new value for the runType property
     */
    public void setRunType(final RunType aRunType) {
        runType = aRunType;
    }

}
