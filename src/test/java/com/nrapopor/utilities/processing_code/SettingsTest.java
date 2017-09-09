/**
 * @author ubuntu - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 2, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapopor.utilities.processing_code;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.nrapopor.utilities.processing_code.config.Settings;

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
public class SettingsTest extends BasePDETestingAncestor {

    //   @SuppressWarnings("unused")
    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SettingsTest.class);

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>TODO add setUpBeforeClass description</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        BasePDETestingAncestor.setUpBeforeClass();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>TODO add tearDownAfterClass description</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>TODO add setUp description</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>TODO add tearDown description</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for {@link com.nrapopor.utilities.processing_code.config.Settings#LoadSettings()}.
     */
    @Test
    public void testLoadSettingsWhenFileEmpty() {
        final String jSONFileName = getTestsettingsdatadir() + "/test-settings.json";
        deleteJson(jSONFileName);
        createEmptyJson(jSONFileName);
        final Settings settings = new Settings(getPsgParent(), jSONFileName);
        assertNotNull(settings);
        final int before = settings.getUpdateCount();
        try {
            log.info("loading ... ");
            settings.loadSettings();
        } catch (final IOException ex) {
            // TODO Auto-generated catch block
            log.error("caught IOException :", ex); //$NON-NLS-1$
            fail("Could not read settings");
            //System.err.println("caught IOException :"+ex.toString()); //$NON-NLS-1$
            //ex.printStackTrace(System.err);
        }
        final int after = settings.getUpdateCount();
        assertTrue("Settings update count mismatch", after == before + 1);

        //fail("Not yet implemented");
    }

    /**
     * Test method for {@link com.nrapopor.utilities.processing_code.config.Settings#LoadSettings()}.
     */
    @Test
    public void testLoadSettingsWhenFileMissing() {
        final String jSONFileName = getTestsettingsdatadir() + "/test-settings.json";
        deleteJson(jSONFileName);
        final Settings settings = new Settings(getPsgParent(), jSONFileName);
        assertNotNull(settings);
        final int before = settings.getUpdateCount();
        try {
            log.info("loading ... ");
            settings.loadSettings();
        } catch (final IOException ex) {
            // TODO Auto-generated catch block
            log.error("caught IOException :", ex); //$NON-NLS-1$
            fail("Could not read settings");
            //System.err.println("caught IOException :"+ex.toString()); //$NON-NLS-1$
            //ex.printStackTrace(System.err);
        }
        final int after = settings.getUpdateCount();
        assertTrue("Settings update count mismatch", after == before + 1);

        //fail("Not yet implemented");
    }

    /**
     * Test method for {@link com.nrapopor.utilities.processing_code.config.Settings#saveSettings(java.lang.String)}.
     */
    @Test
    public void testSaveSettingsString() { //TODO Start Here extract the ConfigurationSettings object mark it exportable
        final String jSONFileName = getTestsettingsdatadir() + "/gson-settings.json";
        final String jSONFileName2 = getTestsettingsdatadir() + "/gson-settings-changed.json";
        //final String jSONFileName = jSONFilePath + "/" + jSONFile;
        final Settings settings = new Settings(getPsgParent(), jSONFileName);
        assertNotNull(settings);
        try {
            settings.saveSettings();
        } catch (final IOException ex) {
            log.error("caught IOException :", ex); //$NON-NLS-1$
            fail(ex.getMessage());
        }
        log.info("original UseArrowKeys {}", settings.isUseArrowKeys());

        settings.setUseArrowKeys(true);
        final boolean newArrowKeys = settings.isUseArrowKeys();
        log.info("new UseArrowKeys {}", newArrowKeys);
        settings.setjSONFileName(jSONFileName2);
        try {
            settings.saveSettings();
        } catch (final IOException ex) {
            log.error("caught IOException :", ex); //$NON-NLS-1$
            fail(ex.getMessage());
        }

        //        final ConfigurationSettings configuration = new ConfigurationSettings();
        //        final Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation()
        //            //           .excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT)
        //            .create();
        //        ;
        //        final String json = gson.toJson(settings);
        //        log.info(json);
        //        final Settings settings2 = gson.fromJson(json, Settings.class);
        final Settings settings2 = new Settings(getPsgParent(), jSONFileName2);
        assertNotNull(settings2);
        //        try {
        //            settings2.loadSettings();
        //        } catch (final IOException ex) {
        //            log.error("caught IOException :", ex); //$NON-NLS-1$
        //            fail(ex.getMessage());
        //        }
        final boolean chagedArrowKeys = settings2.isUseArrowKeys();
        log.info("loaded UseArrowKeys {}", chagedArrowKeys);
        assertTrue(chagedArrowKeys == newArrowKeys);
    }

}
