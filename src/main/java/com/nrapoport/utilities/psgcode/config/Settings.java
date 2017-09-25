/**
 * @author ubuntu - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 1, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapoport.utilities.psgcode.config;

import java.beans.Transient;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.Expose;
import com.nrapoport.utilities.psgcode.AbstractPDE;
import com.nrapoport.utilities.psgcode.PSGProcessingCode;
import com.nrapoport.utilities.psgcode.enums.ControlMode;
import com.nrapoport.utilities.psgcode.enums.Controls;
import com.nrapoport.utilities.psgcode.enums.Effects;
import com.nrapoport.utilities.psgcode.enums.FiringMode;
import com.nrapoport.utilities.psgcode.enums.Renderers;
import com.nrapoport.utilities.psgcode.enums.RunType;
import com.nrapoport.utilities.psgcode.util.Util;

import processing.core.PApplet;

/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>Manage settings for project TODO This class is enormous It need to be redesigned (map backed bean perhaps)</DD>
 * <DT>Date:</DT>
 * <DD>Sep 1, 2017</DD>
 * </DL>
 *
 * @author ubuntu - Nick Rapoport
 *
 */
public class Settings extends AbstractPDE {
    //   @SuppressWarnings("unused")
    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Settings.class);

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>This is to generated the default ./data/settings.json if there are none.</DD>
     * <DT>Date:</DT>
     * <DD>Sep 18, 2017</DD>
     * </DL>
     *
     * @param args
     */
    public static void main(final String[] args) {
        final Settings settings = new Settings(new PSGProcessingCode(), PSGProcessingCode.DEFAULT_SETTINGS);
        log.info("current Settings: \n{}", settings.toString());
    }

    private @Expose ConfigurationSettings configuration = new ConfigurationSettings();

    private transient String jSONFileName;

    private transient int updateCount = 0;

    private transient boolean soundsUpdate = false; //this is used by Sounds to determine if it has to reload the settings

    private final Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation()
        //           .excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT)
        .create();

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Settings Constructor</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aParent
     * @param aJSONFileName
     */
    public Settings(final PApplet aParent, final String aJSONFileName) {
        super(aParent);
        jSONFileName = aJSONFileName;
        try {
            loadSettings();
        } catch (final IOException ex) {
            log.error("caught IOException :", ex); //$NON-NLS-1$
            throw new RuntimeException("Could not create Settings", ex);
        }
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the flipControlMode method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#flipControlMode()
     */
    public void flipControlMode() {
        configuration.flipControlMode();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the flipControls method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#flipControls()
     */
    public void flipControls() {
        configuration.flipControls();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the flipxMinxMax method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#flipxMinxMax()
     */
    public void flipxMinxMax() {
        configuration.flipxMinxMax();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the flipyMinyMax method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#flipyMinyMax()
     */
    public void flipyMinyMax() {
        configuration.flipyMinyMax();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getAntSens method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getAntSens()
     */
    public int getAntSens() {
        return configuration.getAntSens();
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
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getCamHeight()
     */
    public int getCamHeight() {
        return configuration.getCamHeight();
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
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getCamWidth()
     */
    public int getCamWidth() {
        return configuration.getCamWidth();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the configuration property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of configuration field
     */
    public ConfigurationSettings getConfiguration() {
        return configuration;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getControlMode method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getControlMode()
     */
    public ControlMode getControlMode() {
        return configuration.getControlMode();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getControlModeInt method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getControlModeInt()
     */
    public int getControlModeInt() {
        return configuration.getControlModeInt();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getControls method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getControls()
     */
    public Controls getControls() {
        return configuration.getControls();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getCustomizable method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getCustomizable()
     */
    public CustomizableSettings getCustomizable() {
        return configuration.getCustomizable();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getDepartedSoundList method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 24, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getDepartedSoundList()
     */
    public List<String> getDepartedSoundList() {
        return configuration.getDepartedSoundList();
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
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getDeviceConfig()
     */
    public String getDeviceConfig() {
        return configuration.getDeviceConfig();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getDeviceTolerance method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 25, 2017</DD>
     * </DL>
     * 
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getDeviceTolerance()
     */
    public float getDeviceTolerance() {
        return configuration.getDeviceTolerance();
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
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getDiffPixelsColorArray()
     */
    public int[] getDiffPixelsColor() {
        return configuration.getDiffPixelsColorArray();
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
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getDiffPixelsColorArray()
     */
    public int[] getDiffPixelsColorArray() {
        return configuration.getDiffPixelsColorArray();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getDisplayX method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getDisplayX()
     */
    public int getDisplayX() {
        return configuration.getDisplayX();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getDisplayY method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getDisplayY()
     */
    public int getDisplayY() {
        return configuration.getDisplayY();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getEffect method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getEffect()
     */
    public Effects getEffect() {
        return configuration.getEffect();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getEffectInt method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getEffectInt()
     */
    public int getEffectInt() {
        return configuration.getEffectInt();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getFiringMode method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getFiringMode()
     */
    public FiringMode getFiringMode() {
        return configuration.getFiringMode();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getFiringModeIndex method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getFiringModeIndex()
     */
    public int getFiringModeIndex() {
        return configuration.getFiringModeIndex();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getFiringModeInt method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getFiringModeInt()
     */
    public int getFiringModeInt() {
        return configuration.getFiringModeInt();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the gson property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of gson field
     */
    public Gson getGson() {
        return gson;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getIdleSoundList method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 24, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getIdleSoundList()
     */
    public List<String> getIdleSoundList() {
        return configuration.getIdleSoundList();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getIdleTime method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getIdleTime()
     */
    public int getIdleTime() {
        return configuration.getIdleTime();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the jSONFileName property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @return the value of jSONFileName field
     */
    public String getjSONFileName() {
        return jSONFileName;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getMinBlobArea method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getMinBlobArea()
     */
    public int getMinBlobArea() {
        return configuration.getMinBlobArea();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getNbDot method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getNbDot()
     */
    public int getNbDot() {
        return configuration.getNbDot();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getNumberOfVoices method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 6, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getNumberOfVoices()
     */
    public int getNumberOfVoices() {
        return configuration.getNumberOfVoices();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getPropX method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getPropX()
     */
    public float getPropX() {
        return configuration.getPropX();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getPropY method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getPropY()
     */
    public float getPropY() {
        return configuration.getPropY();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getRendererType method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getRendererType()
     */
    public Renderers getRendererType() {
        return configuration.getRendererType();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getRetinaPeriodMillis method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 18, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getRetinaPeriodMillis()
     */
    public int getRetinaPeriodMillis() {
        return configuration.getRetinaPeriodMillis();
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
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getRunType()
     */
    public RunType getRunType() {
        return configuration.getRunType();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getSafeColor method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getSafeColor()
     */
    public ColorManagement getSafeColor() {
        return configuration.getSafeColor();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getSafeColorBlue method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getSafeColorBlue()
     */
    public int getSafeColorBlue() {
        return configuration.getSafeColorBlue();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getSafeColorGreen method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getSafeColorGreen()
     */
    public int getSafeColorGreen() {
        return configuration.getSafeColorGreen();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getSafeColorMinSize method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getSafeColorMinSize()
     */
    public int getSafeColorMinSize() {
        return configuration.getSafeColorMinSize();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getSafeColorRed method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getSafeColorRed()
     */
    public int getSafeColorRed() {
        return configuration.getSafeColorRed();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getSafeColorTolerance method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getSafeColorTolerance()
     */
    public int getSafeColorTolerance() {
        return configuration.getSafeColorTolerance();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getSamplerDelay method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 6, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getSamplerDelay()
     */
    public long getSamplerDelay() {
        return configuration.getSamplerDelay();
    }

    //    /**
    //     * <DL>
    //     * <DT>Description:</DT>
    //     * <DD>A delegate method for the getSoundFiles method</DD>
    //     * <DT>Date:</DT>
    //     * <DD>Sep 4, 2017</DD>
    //     * </DL>
    //     *
    //     * @return
    //     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getSoundFiles()
    //     */
    //    public List<String> getSoundFiles() {
    //        return configuration.getSoundFiles();
    //    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getSmoothingFactor method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getSmoothingFactor()
     */
    public float getSmoothingFactor() {
        return configuration.getSmoothingFactor();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getSoundFilesMap method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 18, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getSoundFilesMap()
     */
    public Map<String, String> getSoundFilesMap() {
        return configuration.getSoundFilesMap();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getSoundInterval method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getSoundInterval()
     */
    public int getSoundInterval() {
        return configuration.getSoundInterval();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getTolerance method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getTolerance()
     */
    public int getTolerance() {
        return configuration.getTolerance();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getTrackColor method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getTrackColor()
     */
    public ColorManagement getTrackColor() {
        return configuration.getTrackColor();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getTrackColorBlue method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getTrackColorBlue()
     */
    public int getTrackColorBlue() {
        return configuration.getTrackColorBlue();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getTrackColorGreen method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getTrackColorGreen()
     */
    public int getTrackColorGreen() {
        return configuration.getTrackColorGreen();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getTrackColorRed method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getTrackColorRed()
     */
    public int getTrackColorRed() {
        return configuration.getTrackColorRed();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getTrackColorTolerance method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getTrackColorTolerance()
     */
    public int getTrackColorTolerance() {
        return configuration.getTrackColorTolerance();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the updateCount property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of updateCount field
     */
    @Transient
    public int getUpdateCount() {
        return updateCount;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getWebsite method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getWebsite()
     */
    public String getWebsite() {
        return configuration.getWebsite();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getxMax method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getxMax()
     */
    public float getxMax() {
        return configuration.getxMax();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getxMin method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getxMin()
     */
    public float getxMin() {
        return configuration.getxMin();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getxRatio method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getxRatio()
     */
    public float getxRatio() {
        return configuration.getxRatio();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getyMax method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getyMax()
     */
    public float getyMax() {
        return configuration.getyMax();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getyMin method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getyMin()
     */
    public float getyMin() {
        return configuration.getyMin();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getyRatio method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#getyRatio()
     */
    public float getyRatio() {
        return configuration.getyRatio();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the isActiveSmoothing method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#isActiveSmoothing()
     */
    public boolean isActiveSmoothing() {
        return configuration.isActiveSmoothing();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the isLeadTarget method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#isLeadTarget()
     */
    public boolean isLeadTarget() {
        return configuration.isLeadTarget();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the isMirrorCam method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#isMirrorCam()
     */
    public boolean isMirrorCam() {
        return configuration.isMirrorCam();
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
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#isPrintFrameRate()
     */
    public boolean isPrintFrameRate() {
        return configuration.isPrintFrameRate();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the isRunWithoutArduino method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#isRunWithoutArduino()
     */
    public boolean isRunWithoutArduino() {
        return configuration.isRunWithoutArduino();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the isSafeColor method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#isSafeColor()
     */
    public boolean isSafeColor() {
        return configuration.isUseSafeColor();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the isSafety method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#isSafety()
     */
    public boolean isSafety() {
        return configuration.isSafety();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the isScanWhenIdle method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#isScanWhenIdle()
     */
    public boolean isScanWhenIdle() {
        return configuration.isScanWhenIdle();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the isShowCameraView method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#isShowCameraView()
     */
    public boolean isShowCameraView() {
        return configuration.isShowCameraView();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the isShowDifferentPixels method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#isShowDifferentPixels()
     */
    public boolean isShowDifferentPixels() {
        return configuration.isShowDifferentPixels();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the isShowRestrictedZones method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#isShowRestrictedZones()
     */
    public boolean isShowRestrictedZones() {
        return configuration.isShowRestrictedZones();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the isShowTargetBox method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#isShowTargetBox()
     */
    public boolean isShowTargetBox() {
        return configuration.isShowTargetBox();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the isSoundEffects method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#isSoundEffects()
     */
    public boolean isSoundEffects() {
        return configuration.isSoundEffects();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the soundsUpdate property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of soundsUpdate field
     */
    public boolean isSoundsUpdate() {
        return soundsUpdate;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the isTrackingColor method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#isTrackingColor()
     */
    public boolean isTrackingColor() {
        return configuration.isTrackingColor();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the isTrackingMotion method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#isTrackingMotion()
     */
    public boolean isTrackingMotion() {
        return configuration.isTrackingMotion();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the isUseArrowKeys method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#isUseArrowKeys()
     */
    public boolean isUseArrowKeys() {
        return configuration.isUseArrowKeys();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the isUseSafeColor method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     *         <DL>
     *         <DT><code>true</code></DT>
     *         <DD>Use Safe Color</DD>
     *         <DT><code>false</code></DT>
     *         <DD>otherwise</DD>
     *         </DL>
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#isUseSafeColor()
     */
    public boolean isUseSafeColor() {
        return configuration.isUseSafeColor();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>load settings from the default file name passed in the constructor</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @throws IOException
     */
    public void loadSettings() throws IOException {
        loadSettings(getjSONFileName());
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>load settings from JSON File</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @param jsonName
     *            the name of the file to load the settings from
     * @throws IOException
     */
    public void loadSettings(final String jsonName) throws IOException { //
        loadSettings(jsonName, 0);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>load settings from JSON File</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @param jsonName
     *            the name of the file to load the settings from
     * @param depth
     *            this method may call itself recursively, so this varibale is used to prevent stack overflow
     * @throws IOException
     */
    protected void loadSettings(final String jsonName, final int depth) throws IOException { //
        if (depth > 1) {
            final IOException ex = new IOException(
                String.format("Something went very very wrong could not load or save file %1$s", jsonName));
            ex.fillInStackTrace();
            throw ex;
        }
        //JSONObject json = null;
        //JSONObject settings = null;
        //FileReader fr = null;

        final Path jsonAbsoluteName = Util.validateLocation(jsonName);//FileSystems.getDefault().getPath(jsonName).toAbsolutePath();
        try (FileReader fr = new FileReader(jsonAbsoluteName.toFile())) {
            //fileReader = new FileReader(jsonAbsoluteName.toFile());
            final ConfigurationSettingsHolder csh = gson.fromJson(fr, ConfigurationSettingsHolder.class);
            final ConfigurationSettings cs = csh.getConfiguration();
            if (cs == null) {
                log.warn("assuming that this is the first time for this file and creating it"); //$NON-NLS-1$
                saveSettings(jsonName);
                loadSettings(jsonName, depth + 1);
            } else {
                setConfiguration(csh.getConfiguration());
                updateCount++;
            }
            // json = loadJSONObject(jsonName);
            // settings = json.getJSONObject("settings");
            // if (settings == null) {
            //log.warn("Assuming that this is the first time for this file and creating it"); //$NON-NLS-1$
            //saveSettings(jsonName);
            // }
            //        } catch (final FileNotFoundException ex) {
            //        } catch (final FileNotFoundException ex) {
            //        } catch (final Exception ex) {
            //                       log.error("caught Exception loading {}:", jsonName, ex); //$NON-NLS-1$
        } catch (final FileNotFoundException ex) {
            if (depth < 1) {
                log.warn("caught FileNotFoundException : {}", ex.getMessage()); //$NON-NLS-1$
                log.warn("assuming that this is the first time for this file and creating it"); //$NON-NLS-1$
                try {
                    Util.createNewJSONSettingsFile(jsonName);
                } catch (final IOException ex1) {
                    log.error("caught IOException :", ex1); //$NON-NLS-1$
                    ex.fillInStackTrace();
                    throw ex;
                }
                saveSettings(jsonName);
                loadSettings(jsonName, depth + 1);
            } else {
                log.error("could not create a new file and save settings to it for load"); //$NON-NLS-1$
                log.error("caught FileNotFoundException :", ex); //$NON-NLS-1$
                ex.fillInStackTrace();
                throw ex;
            }
        } catch (final JsonSyntaxException ex) {
            log.error("caught JsonSyntaxException :", ex); //$NON-NLS-1$
            if (depth < 1) {
                log.warn(
                    "assuming that this is the first time for this file '{}' and it's mangeled, so rewriting defaults", //$NON-NLS-1$
                    jsonAbsoluteName);
                saveSettings(jsonName);
                loadSettings(jsonName, depth + 1);
            } else {
                log.error("could not write defaults to this file {}", jsonAbsoluteName); //$NON-NLS-1$
                ex.fillInStackTrace();
                throw ex;
            }
        } catch (final JsonIOException ex) {
            log.error("caught JsonIOException :", ex); //$NON-NLS-1$
            log.error("check file permissions, could not read configuration from this file {}", jsonAbsoluteName); //$NON-NLS-1$
            ex.fillInStackTrace();
            throw ex;
        }
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the radioEffect method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#radioEffect()
     */
    public void radioEffect() {
        configuration.radioEffect();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Save the current settings to a JSON file using the configured name</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @throws IOException
     */
    public void saveSettings() throws IOException {
        saveSettings(getjSONFileName());
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>save the current settings to a JSON file</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param jsonName
     *            the name of the JSON file to create
     * @throws IOException
     *             if an error occurs
     */
    public void saveSettings(final String jsonName) throws IOException {
        //FileWriter fr = null;
        final Path jsonAbsoluteName = Util.validateLocation(jsonName);//FileSystems.getDefault().getPath(jsonName).toAbsolutePath();
        //final ConfigurationSettingsHolder csh = new ConfigurationSettingsHolder(getConfiguration());
        final String json = toString();
        Util.writeSettingsToFile(json, jsonAbsoluteName.toString());

    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setActiveSmoothing method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aActiveSmoothing
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setActiveSmoothing(boolean)
     */
    public void setActiveSmoothing(final boolean aActiveSmoothing) {
        configuration.setActiveSmoothing(aActiveSmoothing);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setAntSens method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aAntSens
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setAntSens(int)
     */
    public void setAntSens(final int aAntSens) {
        configuration.setAntSens(aAntSens);
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
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setCamHeight(int)
     */
    public void setCamHeight(final int aCamHeight) {
        configuration.setCamHeight(aCamHeight);
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
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setCamWidth(int)
     */
    public void setCamWidth(final int aCamWidth) {
        configuration.setCamWidth(aCamWidth);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the configuration property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aConfiguration
     *            new value for the configuration property
     */
    public void setConfiguration(final ConfigurationSettings aConfiguration) {
        configuration = aConfiguration;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setControlMode method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aControlMode
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setControlMode(com.nrapoport.utilities.psgcode.enums.ControlMode)
     */
    public void setControlMode(final ControlMode aControlMode) {
        configuration.setControlMode(aControlMode);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setControls method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aControls
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setControls(com.nrapoport.utilities.psgcode.enums.Controls)
     */
    public void setControls(final Controls aControls) {
        configuration.setControls(aControls);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setCustomizable method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aCustomizable
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setCustomizable(com.nrapoport.utilities.psgcode.config.CustomizableSettings)
     */
    public void setCustomizable(final CustomizableSettings aCustomizable) {
        configuration.setCustomizable(aCustomizable);
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
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setDeviceConfig(java.lang.String)
     */
    public void setDeviceConfig(final String aDeviceConfig) {
        configuration.setDeviceConfig(aDeviceConfig);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setDeviceTolerance method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 25, 2017</DD>
     * </DL>
     * 
     * @param aDeviceTolerance
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setDeviceTolerance(float)
     */
    public void setDeviceTolerance(final float aDeviceTolerance) {
        configuration.setDeviceTolerance(aDeviceTolerance);
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
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setDiffPixelsColor(com.nrapoport.utilities.psgcode.config.ConfigurationColor)
     */
    public void setDiffPixelsColor(final ConfigurationColor aDiffPixelsColor) {
        configuration.setDiffPixelsColor(aDiffPixelsColor);
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
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setDiffPixelsColorFromArray(int[])
     */
    public void setDiffPixelsColor(final int[] aDiffPixelsColor) {
        configuration.setDiffPixelsColorFromArray(aDiffPixelsColor);
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
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setDiffPixelsColorFromArray(int[])
     */
    public void setDiffPixelsColorFromArray(final int[] aDiffPixelsColor) {
        configuration.setDiffPixelsColorFromArray(aDiffPixelsColor);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setEffect method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aEffect
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setEffect(com.nrapoport.utilities.psgcode.enums.Effects)
     */
    public void setEffect(final Effects aEffect) {
        configuration.setEffect(aEffect);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setFiringMode method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aFiringMode
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setFiringMode(com.nrapoport.utilities.psgcode.enums.FiringMode)
     */
    public void setFiringMode(final FiringMode aFiringMode) {
        configuration.setFiringMode(aFiringMode);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setIdleTime method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aIdleTime
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setIdleTime(int)
     */
    public void setIdleTime(final int aIdleTime) {
        configuration.setIdleTime(aIdleTime);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the jSONFileName property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aJSONFileName
     *            new value for the jSONFileName property
     */
    public void setjSONFileName(final String aJSONFileName) {
        jSONFileName = aJSONFileName;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setLeadTarget method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aLeadTarget
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setLeadTarget(boolean)
     */
    public void setLeadTarget(final boolean aLeadTarget) {
        configuration.setLeadTarget(aLeadTarget);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setMinBlobArea method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aMinBlobArea
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setMinBlobArea(int)
     */
    public void setMinBlobArea(final int aMinBlobArea) {
        configuration.setMinBlobArea(aMinBlobArea);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setMirrorCam method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aMirrorCam
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setMirrorCam(boolean)
     */
    public void setMirrorCam(final boolean aMirrorCam) {
        configuration.setMirrorCam(aMirrorCam);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setNbDot method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aNbDot
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setNbDot(int)
     */
    public void setNbDot(final int aNbDot) {
        configuration.setNbDot(aNbDot);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setNumberOfVoices method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 6, 2017</DD>
     * </DL>
     *
     * @param aNumberOfVoices
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setNumberOfVoices(int)
     */
    public void setNumberOfVoices(final int aNumberOfVoices) {
        configuration.setNumberOfVoices(aNumberOfVoices);
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
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setPrintFrameRate(boolean)
     */
    public void setPrintFrameRate(final boolean aPrintFrameRate) {
        configuration.setPrintFrameRate(aPrintFrameRate);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setPropX method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aPropX
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setPropX(float)
     */
    public void setPropX(final float aPropX) {
        configuration.setPropX(aPropX);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setPropY method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aPropY
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setPropY(float)
     */
    public void setPropY(final float aPropY) {
        configuration.setPropY(aPropY);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setRendererType method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aRendererType
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setRendererType(com.nrapoport.utilities.psgcode.enums.Renderers)
     */
    public void setRendererType(final Renderers aRendererType) {
        configuration.setRendererType(aRendererType);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setRetinaPeriodMillis method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 18, 2017</DD>
     * </DL>
     *
     * @param aRetinaPeriodMillis
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setRetinaPeriodMillis(int)
     */
    public void setRetinaPeriodMillis(final int aRetinaPeriodMillis) {
        configuration.setRetinaPeriodMillis(aRetinaPeriodMillis);
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
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setRunType(com.nrapoport.utilities.psgcode.enums.RunType)
     */
    public void setRunType(final RunType aRunType) {
        configuration.setRunType(aRunType);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setRunWithoutArduino method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aRunWithoutArduino
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setRunWithoutArduino(boolean)
     */
    public void setRunWithoutArduino(final boolean aRunWithoutArduino) {
        configuration.setRunWithoutArduino(aRunWithoutArduino);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setSafeColor method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aSafeColor
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setSafeColor(com.nrapoport.utilities.psgcode.config.ColorManagement)
     */
    public void setSafeColor(final ColorManagement aSafeColor) {
        configuration.setSafeColor(aSafeColor);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setSafeColorBlue method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aSafeColorBlue
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setSafeColorBlue(int)
     */
    public void setSafeColorBlue(final int aSafeColorBlue) {
        configuration.setSafeColorBlue(aSafeColorBlue);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setSafeColorGreen method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aSafeColorGreen
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setSafeColorGreen(int)
     */
    public void setSafeColorGreen(final int aSafeColorGreen) {
        configuration.setSafeColorGreen(aSafeColorGreen);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setSafeColorMinSize method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aSafeColorMinSize
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setSafeColorMinSize(int)
     */
    public void setSafeColorMinSize(final int aSafeColorMinSize) {
        configuration.setSafeColorMinSize(aSafeColorMinSize);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setSafeColorRed method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aSafeColorRed
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setSafeColorRed(int)
     */
    public void setSafeColorRed(final int aSafeColorRed) {
        configuration.setSafeColorRed(aSafeColorRed);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setSafeColorTolerance method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aSafeColorTolerance
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setSafeColorTolerance(int)
     */
    public void setSafeColorTolerance(final int aSafeColorTolerance) {
        configuration.setSafeColorTolerance(aSafeColorTolerance);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setSafety method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aSafety
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setSafety(boolean)
     */
    public void setSafety(final boolean aSafety) {
        configuration.setSafety(aSafety);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setSamplerDelay method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 6, 2017</DD>
     * </DL>
     *
     * @param aSamplerDelay
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setSamplerDelay(long)
     */
    public void setSamplerDelay(final long aSamplerDelay) {
        configuration.setSamplerDelay(aSamplerDelay);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setScanWhenIdle method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aScanWhenIdle
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setScanWhenIdle(boolean)
     */
    public void setScanWhenIdle(final boolean aScanWhenIdle) {
        configuration.setScanWhenIdle(aScanWhenIdle);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setShowCameraView method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aShowCameraView
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setShowCameraView(boolean)
     */
    public void setShowCameraView(final boolean aShowCameraView) {
        configuration.setShowCameraView(aShowCameraView);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setShowDifferentPixels method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aShowDifferentPixels
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setShowDifferentPixels(boolean)
     */
    public void setShowDifferentPixels(final boolean aShowDifferentPixels) {
        configuration.setShowDifferentPixels(aShowDifferentPixels);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setShowRestrictedZones method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aShowRestrictedZones
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setShowRestrictedZones(boolean)
     */
    public void setShowRestrictedZones(final boolean aShowRestrictedZones) {
        configuration.setShowRestrictedZones(aShowRestrictedZones);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setShowTargetBox method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aShowTargetBox
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setShowTargetBox(boolean)
     */
    public void setShowTargetBox(final boolean aShowTargetBox) {
        configuration.setShowTargetBox(aShowTargetBox);
    }

    //    /**
    //     * <DL>
    //     * <DT>Description:</DT>
    //     * <DD>A delegate method for the setSoundFiles method</DD>
    //     * <DT>Date:</DT>
    //     * <DD>Sep 4, 2017</DD>
    //     * </DL>
    //     *
    //     * @param aSoundFiles
    //     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setSoundFiles(java.util.List)
    //     */
    //    public void setSoundFiles(final List<String> aSoundFiles) {
    //        configuration.setSoundFiles(aSoundFiles);
    //    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setSmoothingFactor method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aSmoothingFactor
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setSmoothingFactor(float)
     */
    public void setSmoothingFactor(final float aSmoothingFactor) {
        configuration.setSmoothingFactor(aSmoothingFactor);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setSoundEffects method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aSoundEffects
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setSoundEffects(boolean)
     */
    public void setSoundEffects(final boolean aSoundEffects) {
        configuration.setSoundEffects(aSoundEffects);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setSoundInterval method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aSoundInterval
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setSoundInterval(int)
     */
    public void setSoundInterval(final int aSoundInterval) {
        configuration.setSoundInterval(aSoundInterval);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the soundsUpdate property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aSoundsUpdate
     *            new value for the soundsUpdate property
     */
    public void setSoundsUpdate(final boolean aSoundsUpdate) {
        soundsUpdate = aSoundsUpdate;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setTolerance method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aTolerance
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setTolerance(int)
     */
    public void setTolerance(final int aTolerance) {
        configuration.setTolerance(aTolerance);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setTrackColor method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aTrackColor
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setTrackColor(com.nrapoport.utilities.psgcode.config.ColorManagement)
     */
    public void setTrackColor(final ColorManagement aTrackColor) {
        configuration.setTrackColor(aTrackColor);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setTrackColorBlue method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aTrackColorBlue
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setTrackColorBlue(int)
     */
    public void setTrackColorBlue(final int aTrackColorBlue) {
        configuration.setTrackColorBlue(aTrackColorBlue);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setTrackColorGreen method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aTrackColorGreen
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setTrackColorGreen(int)
     */
    public void setTrackColorGreen(final int aTrackColorGreen) {
        configuration.setTrackColorGreen(aTrackColorGreen);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setTrackColorRed method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aTrackColorRed
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setTrackColorRed(int)
     */
    public void setTrackColorRed(final int aTrackColorRed) {
        configuration.setTrackColorRed(aTrackColorRed);
    }

    //public static final int SETTINGS_COUNT = 41;

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setTrackColorTolerance method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aTrackColorTolerance
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setTrackColorTolerance(int)
     */
    public void setTrackColorTolerance(final int aTrackColorTolerance) {
        configuration.setTrackColorTolerance(aTrackColorTolerance);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setTrackingColor method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aTrackingColor
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setTrackingColor(boolean)
     */
    public void setTrackingColor(final boolean aTrackingColor) {
        configuration.setTrackingColor(aTrackingColor);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setTrackingMotion method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aTrackingMotion
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setTrackingMotion(boolean)
     */
    public void setTrackingMotion(final boolean aTrackingMotion) {
        configuration.setTrackingMotion(aTrackingMotion);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the updateCount property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aUpdateCount
     *            new value for the updateCount property
     */
    public void setUpdateCount(final int aUpdateCount) {
        updateCount = aUpdateCount;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setUseArrowKeys method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aUseArrowKeys
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setUseArrowKeys(boolean)
     */
    public void setUseArrowKeys(final boolean aUseArrowKeys) {
        configuration.setUseArrowKeys(aUseArrowKeys);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setUseSafeColor method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aSafeColor
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setUseSafeColor(boolean)
     */
    public void setUseSafeColor(final boolean aSafeColor) {
        configuration.setUseSafeColor(aSafeColor);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setWebsite method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aWebsite
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setWebsite(java.lang.String)
     */
    public void setWebsite(final String aWebsite) {
        configuration.setWebsite(aWebsite);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setxMax method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aXMax
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setxMax(float)
     */
    public void setxMax(final float aXMax) {
        configuration.setxMax(aXMax);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setxMin method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aXMin
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setxMin(float)
     */
    public void setxMin(final float aXMin) {
        configuration.setxMin(aXMin);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setyMax method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aYMax
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setyMax(float)
     */
    public void setyMax(final float aYMax) {
        configuration.setyMax(aYMax);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setyMin method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aYMin
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#setyMin(float)
     */
    public void setyMin(final float aYMin) {
        configuration.setyMin(aYMin);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the toggleUseArrowKeys method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @see com.nrapoport.utilities.psgcode.config.ConfigurationSettings#toggleUseArrowKeys()
     */
    public void toggleUseArrowKeys() {
        configuration.toggleUseArrowKeys();
    }

    /**
     * {@inheritDoc}
     * <DL>
     * <DT>Description:</DT>
     * <DD>get the current settings as a JSON string</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     */
    @Override
    public String toString() {
        final ConfigurationSettingsHolder csh = new ConfigurationSettingsHolder(getConfiguration());
        final String json = gson.toJson(csh, csh.getClass());
        return json;
    }
}
