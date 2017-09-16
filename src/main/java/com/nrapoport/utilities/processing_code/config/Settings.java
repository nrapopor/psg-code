/**
 * @author ubuntu - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 1, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapoport.utilities.processing_code.config;

import java.beans.Transient;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.EnumSet;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.Expose;
import com.nrapoport.utilities.processing_code.AbstractPDE;
import com.nrapoport.utilities.processing_code.enums.ControlMode;
import com.nrapoport.utilities.processing_code.enums.Controls;
import com.nrapoport.utilities.processing_code.enums.Effects;
import com.nrapoport.utilities.processing_code.enums.FiringMode;
import com.nrapoport.utilities.processing_code.enums.Renderers;
import com.nrapoport.utilities.processing_code.enums.RunType;

import processing.core.PApplet;

/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>manage settings for project</DD>
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
            // TODO Auto-generated catch block
            log.error("caught IOException :", ex); //$NON-NLS-1$
            //System.err.println("caught IOException :"+ex.toString()); //$NON-NLS-1$
            //ex.printStackTrace(System.err);
        }
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Create a new JSON file for the passed name. if that file already exists, do nothing</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param jsonFile
     *            the file to create
     * @throws IOException
     *             if an error occurs
     */
    protected void createNewJSONSettingsFile(final String jsonFile) throws IOException {
        final Path jsonAbsoluteName = FileSystems.getDefault().getPath(jsonFile).toAbsolutePath();
        final Path jsonFileName = jsonAbsoluteName.getFileName();
        final Path parent = jsonAbsoluteName.getParent();
        if (!Files.exists(jsonAbsoluteName)) {
            if (Files.exists(parent)) {
                try {
                    Files.createFile(jsonAbsoluteName,
                        PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwxrwxr--")));
                    //EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)
                    //StandardOpenOption WRITE
                } catch (final IOException ex) {
                    log.error("caught IOException :", ex); //$NON-NLS-1$
                    ex.fillInStackTrace();
                    throw ex;
                }
            } else {
                final NotDirectoryException ex =
                    new NotDirectoryException("Invalid parent path specified for '" + jsonFileName + "' : " + parent);
                ex.fillInStackTrace();
                throw ex;
            }
        }
        //        final FileChannel wFC = FileChannel.open(jsonAbsoluteName,
        //            EnumSet.of(StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING),
        //            PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwxrwxr--")));
        //
        //        final String emptyJSON = "{}";
        //        final ByteBuffer buffer = ByteBuffer.allocate(emptyJSON.getBytes().length);
        //        buffer.put(emptyJSON.getBytes());
        //        buffer.flip();
        //        wFC.write(buffer);
        //        wFC.force(true);
        //        wFC.close();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the flipControlMode method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#flipControlMode()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#flipControls()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#flipxMinxMax()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#flipyMinyMax()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getAntSens()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getCamHeight()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getCamWidth()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getControlMode()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getControlModeInt()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getControls()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getCustomizable()
     */
    public CustomizableSettings getCustomizable() {
        return configuration.getCustomizable();
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getDiffPixelsColorArray()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getDiffPixelsColorArray()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getDisplayX()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getDisplayY()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getEffect()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getEffectInt()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getFiringMode()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getFiringModeIndex()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getFiringModeInt()
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
     * <DD>A delegate method for the getIdleTime method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getIdleTime()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getMinBlobArea()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getNbDot()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getNumberOfVoices()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getPropX()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getPropY()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getRendererType()
     */
    public Renderers getRendererType() {
        return configuration.getRendererType();
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getRunType()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getSafeColor()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getSafeColorBlue()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getSafeColorGreen()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getSafeColorMinSize()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getSafeColorRed()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getSafeColorTolerance()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getSamplerDelay()
     */
    public long getSamplerDelay() {
        return configuration.getSamplerDelay();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getSmoothingFactor method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getSmoothingFactor()
     */
    public float getSmoothingFactor() {
        return configuration.getSmoothingFactor();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the getSoundFiles method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getSoundFiles()
     */
    public List<String> getSoundFiles() {
        return configuration.getSoundFiles();
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getSoundInterval()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getTolerance()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getTrackColor()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getTrackColorBlue()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getTrackColorGreen()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getTrackColorRed()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getTrackColorTolerance()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getWebsite()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getxMax()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getxMin()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getxRatio()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getyMax()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getyMin()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#getyRatio()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#isActiveSmoothing()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#isLeadTarget()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#isMirrorCam()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#isPrintFrameRate()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#isRunWithoutArduino()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#isSafeColor()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#isSafety()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#isScanWhenIdle()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#isShowCameraView()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#isShowDifferentPixels()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#isShowRestrictedZones()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#isShowTargetBox()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#isSoundEffects()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#isTrackingColor()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#isTrackingMotion()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#isUseArrowKeys()
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#isUseSafeColor()
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
        FileReader fr = null;
        final Path jsonAbsoluteName = FileSystems.getDefault().getPath(jsonName).toAbsolutePath();
        try {
            fr = new FileReader(jsonAbsoluteName.toFile());
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
                    createNewJSONSettingsFile(jsonName);
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
        } finally {
            if (fr != null) {
                fr.close();
            }
        }
        //String[] loadData = new String[40];
        //loadData = loadStrings("settings.txt");
        //        if (settings != null) {
        //            //fromJSON(settings);
        //            log.info("Successfully loaded settings from '{}'", jsonName);
        //        } else {
        //            final String msg =
        //                "Could not load the settings .... Something went very, very, very, wrong please contact your developer";
        //            log.error(msg);
        //            final IOException ex = new IOException(msg);
        //            ex.fillInStackTrace();
        //            throw ex;
        //        }
        //println("Successfully loaded settings from \"settings.txt\"");
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the radioEffect method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#radioEffect()
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
        final Path jsonAbsoluteName = FileSystems.getDefault().getPath(jsonName).toAbsolutePath();
        final ConfigurationSettingsHolder csh = new ConfigurationSettingsHolder(getConfiguration());
        final String json = gson.toJson(csh, csh.getClass());
        writeSettingsToFile(json, jsonAbsoluteName.toString());

        //        //final String[] saveData = new String[40];
        //        final JSONObject settings = toJSONObject();
        //        //        final Map<String, Object> current = toMap();
        //        //        for (final String key : current.keySet()) {
        //        //            settings.put(key, current.get(key));
        //        //
        //        //        }
        //        final JSONObject json = new JSONObject();
        //        json.put("settings", settings);
        //        saveJSONObject(json, jsonName);
        //        log.info("Successfully saved settings to '{}'", jsonName);

        //        saveData[0] = str(camWidth);
        //        saveData[1] = str(camHeight);
        //        saveData[2] = str(xMin);
        //        saveData[3] = str(xMax);
        //        saveData[4] = str(yMin);
        //        saveData[5] = str(yMax);
        //        saveData[6] = str(effect);
        //        saveData[7] = str(mirrorCam);
        //        saveData[8] = str(minBlobArea);
        //        saveData[9] = str(tolerance);
        //        saveData[10] = str(runWithoutArduino);
        //        saveData[11] = str(smoothingFactor);
        //        saveData[12] = str(activeSmoothing);
        //        saveData[13] = str(showDifferentPixels);
        //        saveData[14] = str(showTargetBox);
        //        saveData[15] = str(showCameraView);
        //        saveData[16] = str(firingMode);
        //        saveData[17] = str(safety);
        //        saveData[18] = str(controlMode);
        //        saveData[19] = str(soundEffects);
        //        saveData[20] = str(scanWhenIdle);
        //        saveData[21] = str(trackingMotion);
        //        saveData[22] = str(showRestrictedZones);
        //        saveData[23] = str(trackingColor);
        //        saveData[24] = str(trackColorTolerance);
        //        saveData[25] = str(trackColorRed);
        //        saveData[26] = str(trackColorGreen);
        //        saveData[27] = str(trackColorBlue);
        //        saveData[28] = str(useSafeColor);
        //        saveData[29] = str(safeColorMinSize);
        //        saveData[30] = str(safeColorTolerance);
        //        saveData[31] = str(safeColorRed);
        //        saveData[32] = str(safeColorGreen);
        //        saveData[33] = str(safeColorBlue);
        //        saveData[34] = str(useInputDevice);
        //        saveData[35] = str(leadTarget);
        //        saveData[36] = str(nbDot);
        //        saveData[37] = str(antSens);
        //        saveData[38] = str(propX);
        //        saveData[39] = str(propY);

        //saveStrings("data/settings.txt", saveData);
        //println("Successfully saved settings to \"settings.txt\"");

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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setActiveSmoothing(boolean)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setAntSens(int)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setCamHeight(int)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setCamWidth(int)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setControlMode(com.nrapoport.utilities.processing_code.enums.ControlMode)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setControls(com.nrapoport.utilities.processing_code.enums.Controls)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setCustomizable(com.nrapoport.utilities.processing_code.config.CustomizableSettings)
     */
    public void setCustomizable(final CustomizableSettings aCustomizable) {
        configuration.setCustomizable(aCustomizable);
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setDiffPixelsColor(com.nrapoport.utilities.processing_code.config.ConfigurationColor)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setDiffPixelsColorFromArray(int[])
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setDiffPixelsColorFromArray(int[])
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setEffect(com.nrapoport.utilities.processing_code.enums.Effects)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setFiringMode(com.nrapoport.utilities.processing_code.enums.FiringMode)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setIdleTime(int)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setLeadTarget(boolean)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setMinBlobArea(int)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setMirrorCam(boolean)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setNbDot(int)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setNumberOfVoices(int)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setPrintFrameRate(boolean)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setPropX(float)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setPropY(float)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setRendererType(com.nrapoport.utilities.processing_code.enums.Renderers)
     */
    public void setRendererType(final Renderers aRendererType) {
        configuration.setRendererType(aRendererType);
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setRunType(com.nrapoport.utilities.processing_code.enums.RunType)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setRunWithoutArduino(boolean)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setSafeColor(com.nrapoport.utilities.processing_code.config.ColorManagement)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setSafeColorBlue(int)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setSafeColorGreen(int)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setSafeColorMinSize(int)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setSafeColorRed(int)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setSafeColorTolerance(int)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setSafety(boolean)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setSamplerDelay(long)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setScanWhenIdle(boolean)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setShowCameraView(boolean)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setShowDifferentPixels(boolean)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setShowRestrictedZones(boolean)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setShowTargetBox(boolean)
     */
    public void setShowTargetBox(final boolean aShowTargetBox) {
        configuration.setShowTargetBox(aShowTargetBox);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setSmoothingFactor method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aSmoothingFactor
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setSmoothingFactor(float)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setSoundEffects(boolean)
     */
    public void setSoundEffects(final boolean aSoundEffects) {
        configuration.setSoundEffects(aSoundEffects);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setSoundFiles method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aSoundFiles
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setSoundFiles(java.util.List)
     */
    public void setSoundFiles(final List<String> aSoundFiles) {
        configuration.setSoundFiles(aSoundFiles);
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setSoundInterval(int)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setTolerance(int)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setTrackColor(com.nrapoport.utilities.processing_code.config.ColorManagement)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setTrackColorBlue(int)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setTrackColorGreen(int)
     */
    public void setTrackColorGreen(final int aTrackColorGreen) {
        configuration.setTrackColorGreen(aTrackColorGreen);
    }

    //public static final int SETTINGS_COUNT = 41;

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setTrackColorRed method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aTrackColorRed
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setTrackColorRed(int)
     */
    public void setTrackColorRed(final int aTrackColorRed) {
        configuration.setTrackColorRed(aTrackColorRed);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setTrackColorTolerance method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aTrackColorTolerance
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setTrackColorTolerance(int)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setTrackingColor(boolean)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setTrackingMotion(boolean)
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

    //   <===============================================================================================>
    //   Begin custom values - change these camera dimensions to work with your turret
    //   <===============================================================================================>
    //private boolean printFrameRate = false; // set to true to print the framerate at the bottom of the IDE window

    //private int camWidth = 320; //   camera width (pixels),   usually 160*n

    //private int camHeight = 240; //   camera height (pixels),  usually 120*n

    //private int[] diffPixelsColor = { 255, 255, 0 }; // Red, green, blue values (0-255)  to show pixel as marked as target

    //private Effects effect = Effects.Opaque;

    //private boolean mirrorCam = false; //   set true to mirror camera image

    //   <===============================================================================================>
    //   End custom values
    //   <===============================================================================================>

    //    private float xMin = 0.0f; //  Actual calibration values are loaded from "settings.json".
    //
    //    private float xMax = 180.0f; //  If "settings.txt" is broken / unavailable, these defaults are used instead -
    //
    //    private float yMin = 0.0f; //  otherwise, changing these lines will have no effect on your gun's calibration.
    //
    //    //private int effect = 0; // Effect
    //
    //    private float yMax = 180.0f; //
    //
    //    private int minBlobArea = 30; //   minimum target size (pixels)
    //
    //    private int tolerance = 100; //   sensitivity to motion
    //
    //    private boolean runWithoutArduino = false;
    //
    //    private Renderers rendererType = Renderers.P2D;
    //
    //    private float smoothingFactor = 0.8f; // smoothing
    //
    //    private boolean activeSmoothing = true;
    //
    //    protected boolean useSafeColor = false;
    //
    //    private boolean showDifferentPixels = false;
    //
    //    private boolean showTargetBox = true;
    //
    //    private boolean showCameraView = true;
    //
    //    //private boolean firingMode = true; // true = semi,        false = auto
    //    FiringMode firingMode = FiringMode.SemiAuto;
    //
    //    private boolean safety = true;
    //
    //    //private boolean controlMode = false; // true = autonomous,  false = manual
    //    private ControlMode controlMode = ControlMode.Manual;
    //
    //    private boolean soundEffects = false; // set to true to enable sound effects by default
    //
    //    private boolean scanWhenIdle = true;
    //
    //    private boolean trackingMotion = true;
    //
    //    //
    //    private int idleTime = 10000; // how many milliseconds to wait until scanning (when in scan mode)
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

    //    private boolean showRestrictedZones = false;
    //
    //    private boolean trackingColor = false;
    //
    //    private int trackColorTolerance = 100;
    //
    //    private int trackColorRed = 255;
    //
    //    private int trackColorGreen = 255;
    //
    //    private int trackColorBlue = 255;
    //
    //    private int safeColorMinSize = 500;
    //
    //    private int safeColorTolerance = 100;
    //
    //    private int safeColorRed = 0;
    //
    //    private int safeColorGreen = 255;
    //
    //    private int safeColorBlue = 0;
    //
    //    private Controls controls = Controls.Mouse;
    //
    //    boolean leadTarget = true;
    //
    //    private int nbDot = 10; // nomber of dot for anticipation minimum 2
    //
    //    //private boolean useInputDevice = false; // use a joystick or game Controller as input (in manual mode) now it's controls enum
    //
    //    private int antSens = 10; // sensitivity of anticipation
    //
    //    private boolean useArrowKeys = false; // use arrow keys to finely adjust the aiming (in manual mode)
    //
    //    private float propX = 0.67f; // proportionality of anticipation
    //
    //    private float propY = 0.11f; // 1 is Hight / more is Less
    //
    //    private int soundInterval = 1000;
    //
    //    //private int soundTimer = 0;
    //
    //    List<String> soundFiles = new ArrayList<>(Arrays.asList( //
    //        "data/your business is appreciated.wav", //$NON-NLS-1$
    //        "data/who's there.wav", //$NON-NLS-1$
    //        "data/there you are.wav", //$NON-NLS-1$
    //        "data/there you are(2).wav", //$NON-NLS-1$
    //        "data/target lost.wav", //$NON-NLS-1$
    //        "data/target aquired.wav", //$NON-NLS-1$
    //        "data/sleep mode activated.wav", //$NON-NLS-1$
    //        "data/sentry mode activated.wav", //$NON-NLS-1$
    //        "data/no hard feelings.wav", //$NON-NLS-1$
    //        "data/is anyone there.wav", //$NON-NLS-1$
    //        "data/i see you.wav", //$NON-NLS-1$
    //        "data/i dont hate you.wav", //$NON-NLS-1$
    //        "data/i dont blame you.wav", //$NON-NLS-1$
    //        "data/hey its me.wav", //$NON-NLS-1$
    //        "data/hello.wav", //$NON-NLS-1$
    //        "data/gotcha.wav", //$NON-NLS-1$
    //        "data/dispensing product.wav", //$NON-NLS-1$
    //        "data/deploying.wav", //$NON-NLS-1$
    //        "data/could you come over here.wav", //$NON-NLS-1$
    //        "data/are you still there.wav", //$NON-NLS-1$
    //        "data/activated.wav" //$NON-NLS-1$
    //    ));
    //
    //    private String website = "http://psg.rudolphlabs.com/";

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setUseArrowKeys method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aUseArrowKeys
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setUseArrowKeys(boolean)
     */
    public void setUseArrowKeys(final boolean aUseArrowKeys) {
        configuration.setUseArrowKeys(aUseArrowKeys);
    }

    //    /**
    //     * <DL>
    //     * <DT>Description:</DT>
    //     * <DD>parse the settings from a json file</DD>
    //     * <DT>Date:</DT>
    //     * <DD>Sep 1, 2017</DD>
    //     * </DL>
    //     *
    //     * @param settings
    //     *            the name of the JSON file
    //     */
    //    protected void fromJSON(final JSONObject settings) {
    //        camWidth = settings.getInt("camWidth", camWidth); // loadData[0]
    //        camHeight = settings.getInt("camHeight", camHeight); // loadData[1]
    //        printFrameRate = settings.getBoolean("printFrameRate", printFrameRate);
    //        if (settings.hasKey("calibration")) {
    //            final JSONObject calibration = settings.getJSONObject("calibration");
    //            xMin = calibration.getFloat("xMin", xMin); // loadData[2]
    //            xMax = calibration.getFloat("xMax", xMax); // loadData[3]
    //            yMin = calibration.getFloat("yMin", yMin); // loadData[4]
    //            yMax = calibration.getFloat("yMax", yMax); // loadData[5]
    //            //effect = calibration.getInt("effect", effect); // loadData[6]
    //            try {
    //                setEffect(Effects.valueOf(calibration.getString("effect", getEffect().name()))); //loadData[42] //new
    //            } catch (final IllegalArgumentException ex) {
    //                log.warn("Invalid value passed for the 'effect' using  {} ", getEffect().name()); //$NON-NLS-1$
    //                String values = "";
    //                for (final Effects em : Effects.values()) {
    //                    values += "'" + em.name() + "' ";
    //                }
    //                log.warn("valid values are  {} ", values); //$NON-NLS-1$
    //            }
    //            mirrorCam = calibration.getBoolean("mirrorCam", mirrorCam); // loadData[7]
    //            minBlobArea = calibration.getInt("minBlobArea", minBlobArea); // loadData[8]
    //            tolerance = calibration.getInt("tolerance", tolerance); // loadData[9]
    //            smoothingFactor = calibration.getFloat("smoothingFactor", smoothingFactor); // loadData[11]
    //            activeSmoothing = calibration.getBoolean("activeSmoothing", activeSmoothing); // loadData[12]
    //        }
    //        runWithoutArduino = settings.getBoolean("runWithoutArduino", runWithoutArduino); // loadData[10]
    //        showDifferentPixels = settings.getBoolean("showDifferentPixels", showDifferentPixels); // loadData[13]
    //        showTargetBox = settings.getBoolean("showTargetBox", showTargetBox); // loadData[14]
    //        showCameraView = settings.getBoolean("showCameraView", showCameraView); // loadData[15]
    //        //firingMode = settings.getBoolean("firingMode", firingMode); // loadData[16]
    //        try {
    //            firingMode = FiringMode.valueOf(settings.getString("firingMode", firingMode.name())); //loadData[16] //new
    //        } catch (final IllegalArgumentException ex) {
    //            log.warn("Invalid value passed for the 'firingMode' using  {} ", firingMode.name()); //$NON-NLS-1$
    //            String values = "";
    //            for (final FiringMode em : FiringMode.values()) {
    //                values += "'" + em.name() + "' ";
    //            }
    //            log.warn("valid values are  {} ", values); //$NON-NLS-1$
    //        }
    //
    //        safety = settings.getBoolean("safety", safety); // loadData[17]
    //        //controlMode = settings.getBoolean("controlMode", controlMode); // loadData[18]
    //        try {
    //            controlMode = ControlMode.valueOf(settings.getString("controlMode", controlMode.name())); //loadData[18] //new
    //        } catch (final IllegalArgumentException ex) {
    //            log.warn("Invalid value passed for the 'controlMode' using  {} ", controlMode.name()); //$NON-NLS-1$
    //            String values = "";
    //            for (final ControlMode em : ControlMode.values()) {
    //                values += "'" + em.name() + "' ";
    //            }
    //            log.warn("valid values are  {} ", values); //$NON-NLS-1$
    //        }
    //
    //        scanWhenIdle = settings.getBoolean("scanWhenIdle", scanWhenIdle); // loadData[20]
    //        idleTime = settings.getInt("idleTime", idleTime); // loadData[48]
    //        trackingMotion = settings.getBoolean("trackingMotion", trackingMotion); // loadData[21]
    //        showRestrictedZones = settings.getBoolean("showRestrictedZones", showRestrictedZones); // loadData[22]
    //        trackingColor = settings.getBoolean("trackingColor", trackingColor); // loadData[23]
    //        trackColorTolerance = settings.getInt("trackColorTolerance", trackColorTolerance); // loadData[24]
    //        trackColorRed = settings.getInt("trackColorRed", trackColorRed); // loadData[25]
    //        trackColorGreen = settings.getInt("trackColorGreen", trackColorGreen); // loadData[26]
    //        trackColorBlue = settings.getInt("trackColorBlue", trackColorBlue); // loadData[27]
    //        useSafeColor = settings.getBoolean("useSafeColor", useSafeColor); // loadData[28]
    //        safeColorMinSize = settings.getInt("safeColorMinSize", safeColorMinSize); // loadData[29]
    //        safeColorTolerance = settings.getInt("safeColorTolerance", safeColorTolerance); // loadData[30]
    //        safeColorRed = settings.getInt("safeColorRed", safeColorRed); // loadData[31]
    //        safeColorGreen = settings.getInt("safeColorGreen", safeColorGreen); // loadData[32]
    //        safeColorBlue = settings.getInt("safeColorBlue", safeColorBlue); // loadData[33]
    //        //useInputDevice = settings.getBoolean("useInputDevice", useInputDevice); // loadData[34]
    //        leadTarget = settings.getBoolean("leadTarget", leadTarget); // loadData[35]
    //        nbDot = settings.getInt("nbDot", nbDot); // loadData[36]
    //        antSens = settings.getInt("antSens", antSens); // loadData[37]
    //        propX = settings.getFloat("propX", propX); // loadData[38]
    //        propY = settings.getFloat("propY", propY); // loadData[39]
    //        setUseArrowKeys(settings.getBoolean("useArrowKeys", isUseArrowKeys())); // loadData[40] //new
    //        website = settings.getString("website", website); // loadData[48] //new
    //        if (settings.hasKey("diffPixelsColor")) {
    //            final JSONObject dpc = settings.getJSONObject("diffPixelsColor"); //loadData[41] //new
    //            diffPixelsColor[0] = dpc.getInt("red", diffPixelsColor[0]);
    //            diffPixelsColor[1] = dpc.getInt("green", diffPixelsColor[1]);
    //            diffPixelsColor[2] = dpc.getInt("blue", diffPixelsColor[2]);
    //        }
    //        try {
    //            rendererType = Renderers.valueOf(settings.getString("rendererType", rendererType.name())); //loadData[42] //new
    //        } catch (final IllegalArgumentException ex) {
    //            log.warn("Invalid value passed for the 'rendererType' using  {} ", rendererType.name()); //$NON-NLS-1$
    //            String values = "";
    //            for (final Renderers em : Renderers.values()) {
    //                values += "'" + em.name() + "' ";
    //            }
    //            log.warn("valid values are  {} ", values); //$NON-NLS-1$
    //        }
    //        try {
    //            controls = Controls.valueOf(settings.getString("controls", controls.name())); //$NON-NLS-1$
    //        } catch (final IllegalArgumentException ex) {
    //            log.warn("Invalid value passed for the 'controls' using  {} ", controls); //$NON-NLS-1$
    //            String values = "";
    //            for (final Controls em : Controls.values()) {
    //                values += "'" + em.name() + "' ";
    //            }
    //            log.warn("valid values are  {} ", values); //$NON-NLS-1$
    //        }
    //
    //        if (settings.hasKey("sounds")) {
    //            soundsUpdate = true;
    //            final JSONObject sounds = settings.getJSONObject("sounds");
    //            soundEffects = sounds.getBoolean("soundEffects", soundEffects); // loadData[19]
    //            //soundTimer = sounds.getInt("soundTimer", soundTimer); // loadData[44]
    //            soundInterval = sounds.getInt("soundInterval", soundInterval); // loadData[45]
    //            if (sounds.hasKey("soundFiles")) {
    //                final JSONArray soundsF = sounds.getJSONArray("soundFiles"); //loadData[43] //new
    //                soundFiles.clear();
    //                soundFiles.addAll(Arrays.asList(soundsF.getStringArray()));
    //            }
    //        }
    //
    //        updateCount++;
    //    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>A delegate method for the setUseSafeColor method</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aSafeColor
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setUseSafeColor(boolean)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setWebsite(java.lang.String)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setxMax(float)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setxMin(float)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setyMax(float)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#setyMin(float)
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
     * @see com.nrapoport.utilities.processing_code.config.ConfigurationSettings#toggleUseArrowKeys()
     */
    public void toggleUseArrowKeys() {
        configuration.toggleUseArrowKeys();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Create a new JSON file for the passed name. if that file already exists, do nothing</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param json
     *            the serialized JSON content to save
     * @param jsonFile
     *            the file to create
     * @throws IOException
     *             if an error occurs
     */
    protected void writeSettingsToFile(final String json, final String jsonFile) throws IOException {
        final Path jsonAbsoluteName = FileSystems.getDefault().getPath(jsonFile).toAbsolutePath();
        final Path jsonFileName = jsonAbsoluteName.getFileName();
        final Path parent = jsonAbsoluteName.getParent();
        if (!Files.exists(jsonAbsoluteName)) {
            if (Files.exists(parent)) {
                try {
                    Files.createFile(jsonAbsoluteName,
                        PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwxrwxr--")));
                    //EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)
                    //StandardOpenOption WRITE
                } catch (final IOException ex) {
                    log.error("caught IOException :", ex); //$NON-NLS-1$
                    ex.fillInStackTrace();
                    throw ex;
                }
            } else {
                final NotDirectoryException ex =
                    new NotDirectoryException("Invalid parent path specified for '" + jsonFileName + "' : " + parent);
                ex.fillInStackTrace();
                throw ex;
            }
        }
        final FileChannel wFC = FileChannel.open(jsonAbsoluteName,
            EnumSet.of(StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING));
        //PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwxrwxr--")));
        final byte[] jsonBytes = json.getBytes();
        final ByteBuffer buffer = ByteBuffer.allocate(jsonBytes.length);
        buffer.put(jsonBytes);
        buffer.flip();
        wFC.write(buffer);
        wFC.force(true);
        wFC.close();
    }

    //    public Map<String, Object> toMap() {
    //        final Map<String, Object> contents = new LinkedHashMap<String, Object>(SETTINGS_COUNT);
    //        contents.put("camWidth", camWidth); // loadData[0]
    //        contents.put("camHeight", camHeight); // loadData[1]
    //        contents.put("xMin", xMin); // loadData[2]
    //        contents.put("xMax", xMax); // loadData[3]
    //        contents.put("yMin", yMin); // loadData[4]
    //        contents.put("yMax", yMax); // loadData[5]
    //        contents.put("effect", effect); // loadData[6]
    //        contents.put("mirrorCam", mirrorCam); // loadData[7]
    //        contents.put("minBlobArea", minBlobArea); // loadData[8]
    //        contents.put("tolerance", tolerance); // loadData[9]
    //        contents.put("runWithoutArduino", runWithoutArduino); // loadData[10]
    //        contents.put("smoothingFactor", smoothingFactor); // loadData[11]
    //        contents.put("activeSmoothing", activeSmoothing); // loadData[12]
    //        contents.put("showDifferentPixels", showDifferentPixels); // loadData[13]
    //        contents.put("showTargetBox", showTargetBox); // loadData[14]
    //        contents.put("showCameraView", showCameraView); // loadData[15]
    //        contents.put("firingMode", firingMode); // loadData[16]
    //        contents.put("safety", safety); // loadData[17]
    //        contents.put("controlMode", controlMode); // loadData[18]
    //        contents.put("soundEffects", soundEffects); // loadData[19]
    //        contents.put("scanWhenIdle", scanWhenIdle); // loadData[20]
    //        contents.put("trackingMotion", trackingMotion); // loadData[21]
    //        contents.put("showRestrictedZones", showRestrictedZones); // loadData[22]
    //        contents.put("trackingColor", trackingColor); // loadData[23]
    //        contents.put("trackColorTolerance", trackColorTolerance); // loadData[24]
    //        contents.put("trackColorRed", trackColorRed); // loadData[25]
    //        contents.put("trackColorGreen", trackColorGreen); // loadData[26]
    //        contents.put("trackColorBlue", trackColorBlue); // loadData[27]
    //        contents.put("useSafeColor", useSafeColor); // loadData[28]
    //        contents.put("safeColorMinSize", safeColorMinSize); // loadData[29]
    //        contents.put("safeColorTolerance", safeColorTolerance); // loadData[30]
    //        contents.put("safeColorRed", safeColorRed); // loadData[31]
    //        contents.put("safeColorGreen", safeColorGreen); // loadData[32]
    //        contents.put("safeColorBlue", safeColorBlue); // loadData[33]
    //        contents.put("useInputDevice", useInputDevice); // loadData[34]
    //        contents.put("leadTarget", leadTarget); // loadData[35]
    //        contents.put("nbDot", nbDot); // loadData[36]
    //        contents.put("antSens", antSens); // loadData[37]
    //        contents.put("propX", propX); // loadData[38]
    //        contents.put("propY", propY); // loadData[39]
    //        contents.put("useArrowKeys", useArrowKeys); // loadData[40] //new
    //        final JSONObject diffPixelsColorObj = new JSONObject();
    //        diffPixelsColorObj.put("red", diffPixelsColor[0]);
    //        diffPixelsColorObj.put("green", diffPixelsColor[1]);
    //        diffPixelsColorObj.put("blue", diffPixelsColor[2]);
    //
    //        contents.put("diffPixelsColor", diffPixelsColorObj); //loadData[41] //new
    //
    //        return contents;
    //
    //    }
    //    /**
    //     * <DL>
    //     * <DT>Description:</DT>
    //     * <DD>store the current property values of this object into JSON</DD>
    //     * <DT>Date:</DT>
    //     * <DD>Sep 1, 2017</DD>
    //     * </DL>
    //     *
    //     * @return
    //     */
    //    public JSONObject toJSONObject() {
    //        //final Map<String, Object> contents = new LinkedHashMap<String, Object>(SETTINGS_COUNT);
    //        final JSONObject contents = new JSONObject();
    //        contents.put("camWidth", camWidth); // loadData[0]
    //        contents.put("camHeight", camHeight); // loadData[1]
    //        contents.put("printFrameRate", printFrameRate); // loadData[47]
    //        final JSONObject calibration = new JSONObject();
    //        calibration.put("xMin", xMin); // loadData[2]
    //        calibration.put("xMax", xMax); // loadData[3]
    //        calibration.put("yMin", yMin); // loadData[4]
    //        calibration.put("yMax", yMax); // loadData[5]
    //        calibration.put("effect", getEffect().name()); // loadData[6]
    //        calibration.put("mirrorCam", mirrorCam); // loadData[7]
    //        calibration.put("minBlobArea", minBlobArea); // loadData[8]
    //        calibration.put("tolerance", tolerance); // loadData[9]
    //        calibration.put("smoothingFactor", smoothingFactor); // loadData[11]
    //        calibration.put("activeSmoothing", activeSmoothing); // loadData[12]
    //        contents.put("calibration", calibration);
    //        contents.put("runWithoutArduino", runWithoutArduino); // loadData[10]
    //        contents.put("showDifferentPixels", showDifferentPixels); // loadData[13]
    //        contents.put("showTargetBox", showTargetBox); // loadData[14]
    //        contents.put("showCameraView", showCameraView); // loadData[15]
    //        contents.put("firingMode", firingMode.name()); // loadData[16]
    //        contents.put("safety", safety); // loadData[17]
    //        contents.put("controlMode", controlMode.name()); // loadData[18]
    //        contents.put("scanWhenIdle", scanWhenIdle); // loadData[20]
    //        contents.put("idleTime", idleTime); // loadData[47]
    //        contents.put("trackingMotion", trackingMotion); // loadData[21]
    //        contents.put("showRestrictedZones", showRestrictedZones); // loadData[22]
    //        contents.put("trackingColor", trackingColor); // loadData[23]
    //        contents.put("trackColorTolerance", trackColorTolerance); // loadData[24]
    //        contents.put("trackColorRed", trackColorRed); // loadData[25]
    //        contents.put("trackColorGreen", trackColorGreen); // loadData[26]
    //        contents.put("trackColorBlue", trackColorBlue); // loadData[27]
    //        contents.put("useSafeColor", useSafeColor); // loadData[28]
    //        contents.put("safeColorMinSize", safeColorMinSize); // loadData[29]
    //        contents.put("safeColorTolerance", safeColorTolerance); // loadData[30]
    //        contents.put("safeColorRed", safeColorRed); // loadData[31]
    //        contents.put("safeColorGreen", safeColorGreen); // loadData[32]
    //        contents.put("safeColorBlue", safeColorBlue); // loadData[33]
    //        //contents.put("useInputDevice", useInputDevice); // loadData[34]
    //        contents.put("leadTarget", leadTarget); // loadData[35]
    //        contents.put("nbDot", nbDot); // loadData[36]
    //        contents.put("antSens", antSens); // loadData[37]
    //        contents.put("propX", propX); // loadData[38]
    //        contents.put("propY", propY); // loadData[39]
    //        contents.put("useArrowKeys", isUseArrowKeys()); // loadData[40] //new
    //        contents.put("website", website); // loadData[48] //new
    //        final JSONObject diffPixelsColorObj = new JSONObject();
    //        diffPixelsColorObj.put("red", diffPixelsColor[0]);
    //        diffPixelsColorObj.put("green", diffPixelsColor[1]);
    //        diffPixelsColorObj.put("blue", diffPixelsColor[2]);
    //        contents.put("diffPixelsColor", diffPixelsColorObj); //loadData[41] //new
    //        contents.put("rendererType", rendererType.name()); //loadData[42] //new
    //        contents.put("controls", controls.name()); //loadData[43] //new
    //        final JSONObject sounds = new JSONObject();
    //        //sounds.put("soundTimer", soundTimer); //loadData[44] //new
    //        sounds.put("soundInterval", soundInterval); //loadData[45] //new
    //        sounds.put("soundEffects", soundEffects); // loadData[19]
    //        final JSONArray soundsF = new JSONArray();
    //        for (final String sound : soundFiles) {
    //            soundsF.append(sound);
    //        }
    //        sounds.put("soundFiles", soundsF); //loadData[46] //new
    //        contents.put("sounds", sounds);
    //        return contents;
    //    }

}
