/**
 * @author ubuntu - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 4, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapoport.utilities.processing_code;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.nrapoport.utilities.processing_code.enums.RunType;

/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>Tests for the Sounds class</DD>
 * <DT>Date:</DT>
 * <DD>Sep 4, 2017</DD>
 * </DL>
 *
 * @author ubuntu - Nick Rapoport
 *
 */
public class SoundsTest extends BasePDETestingAncestor {
    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SoundsTest.class);

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>initialize the data directory for testing</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        BasePDETestingAncestor.setUpBeforeClass();
        copyFiles("src/main/resources/data");
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Tear down after all the tests in the class Class completed</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
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
     * <DD>SetUp before each test</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
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
     * <DD>Teardown after each test</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for
     * {@link com.nrapoport.utilities.processing_code.Sounds#Sounds(processing.core.PApplet, com.nrapoport.utilities.processing_code.Settings)}.
     */
    @Test
    public void test010Sounds() {
        getPsgParent().getSettings().setRunType(RunType.MinimalWithSounds);
        getPsgParent().getSettings().setSoundEffects(true);
        final Sounds sounds = new Sounds(getPsgParent());
        assertNotNull(sounds);
        try {
            sounds.close();
        } catch (final Exception ex) {
            log.error("caught Exception :", ex); //$NON-NLS-1$
            fail(ex.getMessage());
        }
    }

    @Test
    public void test015SoundsMinimal() {
        getPsgParent().getSettings().setRunType(RunType.Minimal);
        getPsgParent().getSettings().setSoundEffects(true);
        final Sounds sounds = new Sounds(getPsgParent());
        assertNotNull(sounds);
        try {
            sounds.close();
        } catch (final Exception ex) {
            log.error("caught Exception :", ex); //$NON-NLS-1$
            fail(ex.getMessage());
        }
    }

    /**
     * Test method for {@link com.nrapoport.utilities.processing_code.Sounds#playSound(int)}.
     */
    @Test
    public void test020PlaySound() {
        getPsgParent().getSettings().setRunType(RunType.MinimalWithSounds);
        getPsgParent().getSettings().setSoundEffects(true);
        final Sounds sounds = new Sounds(getPsgParent());
        sounds.getSettings().setSoundEffects(true);
        assertNotNull(sounds);
        try {
            sounds.playSound(3);
            Thread.sleep(3000); // time to p[lay the sound
        } catch (final InterruptedException ex) {
            log.info("done waiting ... "); //$NON-NLS-1$
        }
        try {
            sounds.close();
        } catch (final Exception ex) {
            log.error("caught Exception :", ex); //$NON-NLS-1$
            fail(ex.getMessage());
        }
    }

    /**
     * Test method for {@link com.nrapoport.utilities.processing_code.Sounds#playSound(int)}.
     */
    @Test
    public void test025PlaySoundMinimal() {
        getPsgParent().getSettings().setRunType(RunType.Minimal);
        getPsgParent().getSettings().setSoundEffects(true);
        final Sounds sounds = new Sounds(getPsgParent());
        sounds.getSettings().setSoundEffects(true);
        assertNotNull(sounds);
        try {
            sounds.playSound(3);
            Thread.sleep(3000); // time to p[lay the sound
        } catch (final InterruptedException ex) {
            log.info("done waiting ... "); //$NON-NLS-1$
        }
        try {
            sounds.close();
        } catch (final Exception ex) {
            log.error("caught Exception :", ex); //$NON-NLS-1$
            fail(ex.getMessage());
        }
    }

    /**
     * Test method for {@link com.nrapoport.utilities.processing_code.Sounds#randomIdleSound()}.
     */
    @Test
    public void test030RandomIdleSound() {
        getPsgParent().getSettings().setRunType(RunType.MinimalWithSounds);
        getPsgParent().getSettings().setSoundEffects(true);
        final Sounds sounds = new Sounds(getPsgParent());
        sounds.getSettings().setSoundEffects(true);
        assertNotNull(sounds);
        try {
            sounds.randomIdleSound();
            Thread.sleep(3000); // time to p[lay the sound
        } catch (final InterruptedException ex) {
            log.info("done waiting ... "); //$NON-NLS-1$
        }
        try {
            sounds.close();
        } catch (final Exception ex) {
            log.error("caught Exception :", ex); //$NON-NLS-1$
            fail(ex.getMessage());
        }
    }

    /**
     * Test method for {@link com.nrapoport.utilities.processing_code.Sounds#randomIdleSound()}.
     */
    @Test
    public void test035RandomIdleSoundMinimal() {
        getPsgParent().getSettings().setRunType(RunType.Minimal);
        getPsgParent().getSettings().setSoundEffects(true);
        final Sounds sounds = new Sounds(getPsgParent());
        assertNotNull(sounds);
        try {
            sounds.randomIdleSound();
            Thread.sleep(3000); // time to p[lay the sound
        } catch (final InterruptedException ex) {
            log.info("done waiting ... "); //$NON-NLS-1$
        }
        try {
            sounds.close();
        } catch (final Exception ex) {
            log.error("caught Exception :", ex); //$NON-NLS-1$
            fail(ex.getMessage());
        }
    }

    /**
     * Test method for {@link com.nrapoport.utilities.processing_code.Sounds#close()}.
     */
    @Test
    public void test100PlayAllClose() {
        getPsgParent().getSettings().setRunType(RunType.MinimalWithSounds);
        getPsgParent().getSettings().setSoundEffects(true);
        final Sounds sounds = new Sounds(getPsgParent());
        sounds.getSettings().setSoundEffects(true);
        assertNotNull(sounds);
        final int size = sounds.getPlayers().size();
        for (int i = 1; i <= size; i++) {
            try {
                log.info("Playing sound {}", i);
                sounds.playSound(i);
                Thread.sleep(3000L); // time to p[lay the sound
            } catch (final InterruptedException ex) {
                log.info("done waiting ... "); //$NON-NLS-1$
            }
            log.info("Done Playing sound {}", i);
        }

        try {
            sounds.close();
        } catch (final Exception ex) {
            log.error("caught Exception :", ex); //$NON-NLS-1$
            fail(ex.getMessage());
        }
    }

    /**
     * Test method for {@link com.nrapoport.utilities.processing_code.Sounds#close()}.
     */
    @Test
    public void test105PlayAllClose() {
        getPsgParent().getSettings().setRunType(RunType.Minimal);
        getPsgParent().getSettings().setSoundEffects(true);
        final Sounds sounds = new Sounds(getPsgParent());
        assertNotNull(sounds);
        final int size = sounds.getPlayers().size();
        for (int i = 1; i <= size; i++) {
            try {
                log.info("Playing sound {}", i);
                sounds.playSound(i);
                Thread.sleep(3000L); // time to p[lay the sound
            } catch (final InterruptedException ex) {
                log.info("done waiting ... "); //$NON-NLS-1$
            }
            log.info("Done Playing sound {}", i);
        }

        try {
            sounds.close();
        } catch (final Exception ex) {
            log.error("caught Exception :", ex); //$NON-NLS-1$
            fail(ex.getMessage());
        }
    }

}
