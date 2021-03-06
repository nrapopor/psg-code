/**
 * @author ubuntu - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 2, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapoport.utilities.psgcode.config;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.nrapoport.utilities.psgcode.BasePDETestingAncestor;

/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>Test Settings class</DD>
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
     * <DD>Set Up Befor eClass</DD>
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
     * <DD>Tear Down After Class</DD>
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
     * <DD>Set up before test</DD>
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
     * <DD>Tear down after test</DD>
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
     * Test method for {@link com.nrapoport.utilities.psgcode.config.Settings#loadSettings()}.
     */
    @Test
    public void testLoadSettingsWhenFileEmpty() {
        final String jSONFileName = getDatadir() + "/test-settings.json";
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
        deleteJson(jSONFileName);
        //fail("Not yet implemented");
    }

    /**
     * Test method for {@link com.nrapoport.utilities.psgcode.config.Settings#loadSettings()}.
     */
    @Test
    public void testLoadSettingsWhenFileMissing() {
        final String jSONFileName = getDatadir() + "/test-settings.json";
        deleteJson(jSONFileName);
        final Settings settings = new Settings(getPsgParent(), jSONFileName);
        assertNotNull(settings);
        final int before = settings.getUpdateCount();
        try {
            log.info("loading ... ");
            settings.loadSettings();
        } catch (final IOException ex) {
            log.error("caught IOException :", ex); //$NON-NLS-1$
            fail("Could not read settings");
        }
        final int after = settings.getUpdateCount();
        assertTrue("Settings update count mismatch", after == before + 1);
        deleteJson(jSONFileName);

        //fail("Not yet implemented");
    }

    /**
     * Test method for {@link com.nrapoport.utilities.psgcode.config.Settings#saveSettings(java.lang.String)}.
     */
    @Test
    public void testSaveSettingsString() {
        final String jSONFileName = getDatadir() + "/gson-settings.json";
        final String jSONFileName2 = getDatadir() + "/gson-settings-changed.json";
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
        deleteJson(jSONFileName);
        deleteJson(jSONFileName2);

    }

}
