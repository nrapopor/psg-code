package com.nrapopor.utilities.processing_code;

import java.util.ArrayList;
import java.util.List;

import com.nrapopor.utilities.processing_code.config.Settings;

import ddf.minim.AudioOutput;
import ddf.minim.Minim;
import ddf.minim.ugens.Sampler;

public class Sounds extends AbstractPDE implements AutoCloseable {
    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Sounds.class);

    private static final int NUMBER_OF_SOUNDS = 21;

    private volatile int soundTimer = 0;

    private int soundInterval = 1000;

    private int numberOfVoices = 4;

    private boolean soundEffects = false; // set to true to enable sound effects by default

    private Minim minim;

    private volatile AudioOutput out;

    final Settings settings;

    final List<Sampler> players = new ArrayList<>(NUMBER_OF_SOUNDS);

    public Sounds(final PSGProcessingCode aParent) {
        super(aParent);
        settings = aParent.getSettings();
        resetSettingsManagedFields(true);
        log.debug("Using Sounds {}", soundEffects);
        initMinim();
        //int seq = 1; //the old naming scheme started with 1
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>clean up all the players and the Minim instance</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @throws Exception
     */
    @Override
    public void close() throws Exception {
        closePlayers();
        if (getMinim() != null) {
            getMinim().stop();
        }
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>close all the AudioPlayers in the list</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     */
    private void closePlayers() {
        if (!players.isEmpty()) {
            //            for (final AudioSample audioSample : players) {
            //                try {
            //                    audioSample.close();
            //                } catch (final Exception ex) {
            //                    log.error("caught Exception while closing a player :", ex); //$NON-NLS-1$
            //                }
            //            }
            players.clear();
        }
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>get a reference to the audio player (1 based index, first player is 1) keeping the old pattern</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @param aKey
     * @return
     * @see java.util.List#get(int)
     */
    protected Sampler get(final int index) {
        int idx = index;
        if (idx == 0) {
            idx = 1;
        }
        if (idx > players.size()) {
            idx = players.size();
        }
        return players.get(index - 1);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the minim property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @return the value of minim field
     */
    protected Minim getMinim() {
        return minim;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the players property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 5, 2017</DD>
     * </DL>
     *
     * @return the value of players field
     */
    public List<Sampler> getPlayers() {
        return players;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the settings property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of settings field
     */
    public Settings getSettings() {
        return settings;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the soundInterval property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @return the value of soundInterval field
     */
    protected int getSoundInterval() {
        return soundInterval;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the soundTimer property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @return the value of soundTimer field
     */
    protected int getSoundTimer() {
        return soundTimer;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>increment soundTimer property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     */
    protected void incrementSoundTimer() {
        soundTimer++;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>TODO add initMinim description</DD>
     * <DT>Date:</DT>
     * <DD>Sep 9, 2017</DD>
     * </DL>
     *
     * @param aParent
     */
    private void initMinim() {
        if (soundEffects) {
            minim = new Minim(getParent());
            out = minim.getLineOut();
        } else {
            minim = null;
            out = null;
        }
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Test to see if it's time to make sounds ...</DD>
     * <DT>Date:</DT>
     * <DD>Sep 3, 2017</DD>
     * </DL>
     *
     * @return
     *         <DL>
     *         <DT><code>true</code></DT>
     *         <DD>Make sounds now</DD>
     *         <DT><code>false</code></DT>
     *         <DD>not yet or ever (if sounds are disabled</DD>
     *         </DL>
     */
    public boolean isItSoundTime() {
        resetSettingsManagedFields(false);
        if (soundEffects) {
            incrementSoundTimer();
            if (soundTimer >= soundInterval) {
                soundTimer = 0;
                return true;
            }
        }
        return false;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Play the sound at the passed index (1 based) , if sound effects are enabled</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @param sound
     *            the 1 based index of the sound;
     */
    public void playSound(final int sound) {
        resetSettingsManagedFields(false);
        if (soundEffects) {
            final Sampler sampler = get(sound);
            sampler.patch(out);
            sampler.trigger();
            new Thread(() -> {
                try {
                    Thread.sleep(getSettings().getSamplerDelay());
                } catch (final InterruptedException ex) {
                    Sounds.log.debug("caught InterruptedException :", ex); //$NON-NLS-1$
                } finally {
                    synchronized (sampler) {
                        sampler.unpatch(out);
                    }
                }
            }).start();
            //            try {
            //                Thread.sleep(2000);
            //            } catch (final InterruptedException ex) {
            //                log.debug("caught InterruptedException :", ex); //$NON-NLS-1$
            //            }
            //            player.unpatch(out);
        }
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Randomly play any of the first 10 sounds in the list, if sound effects are enabled</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     */
    void randomIdleSound() {
        resetSettingsManagedFields(false);
        if (soundEffects) {
            final int sound = (int) Math.floor(getParent().random(1, 11));
            playSound(sound);
        }
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>reset all the settings for sounds that are managed by Settings. this is done only if there are relevant
     * changes</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param init
     *            <DL>
     *            <DT><code>true</code></DT>
     *            <DD>force the reset</DD>
     *            <DT><code>false</code></DT>
     *            <DD>do not force the reset. it may still happen</DD>
     *            </DL>
     */
    private void resetSettingsManagedFields(final boolean init) {
        soundInterval = settings.getSoundInterval();
        soundEffects = settings.isSoundEffects() && settings.getRunType().isSoundsAvailable();
        numberOfVoices = settings.getNumberOfVoices();
        if (soundEffects && (getSettings().isSoundsUpdate() || init || players.isEmpty())) {
            if (soundEffects && getMinim() == null) {
                //we must have turned on sounds mid run
                initMinim();
            }
            closePlayers();
            for (final String sound : settings.getSoundFiles()) {
                //players.add(minim.loadSample(sound));
                players.add(new Sampler(sound, numberOfVoices, minim));
            }
            getSettings().setSoundsUpdate(false);
        }
    }

}
