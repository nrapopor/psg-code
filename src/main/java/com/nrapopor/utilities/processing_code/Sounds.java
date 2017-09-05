package com.nrapopor.utilities.processing_code;

import java.util.ArrayList;
import java.util.List;

import ddf.minim.AudioSample;
import ddf.minim.Minim;

public class Sounds extends AbstractPDE implements AutoCloseable {
    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Sounds.class);

    private static final int NUMBER_OF_SOUNDS = 21;

    private volatile int soundTimer = 0;

    private int soundInterval = 1000;

    private boolean soundEffects = false; // set to true to enable sound effects by default

    Minim minim;

    final Settings settings;

    final List<AudioSample> players = new ArrayList<>(NUMBER_OF_SOUNDS);

    public Sounds(final PSGProcessingCode aParent) {
        super(aParent);
        minim = new Minim(aParent);
        //int seq = 1; //the old naming scheme started with 1
        settings = aParent.getSettings();
        resetSettingsManagedFields(true);
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
        getMinim().stop();
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
            for (final AudioSample audioSample : players) {
                try {
                    audioSample.close();
                } catch (final Exception ex) {
                    log.error("caught Exception while closing a player :", ex); //$NON-NLS-1$
                }
            }
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
    protected AudioSample get(final int index) {
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
    public List<AudioSample> getPlayers() {
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
            final AudioSample player = get(sound);
            player.trigger();
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
     * @param force
     *            <DL>
     *            <DT><code>true</code></DT>
     *            <DD>force the reset</DD>
     *            <DT><code>false</code></DT>
     *            <DD>do not force the reset. it may still happen</DD>
     *            </DL>
     */
    private void resetSettingsManagedFields(final boolean force) {

        if (getSettings().isSoundsUpdate() || force || players.isEmpty()) {
            closePlayers();
            for (final String sound : settings.getSoundFiles()) {
                players.add(minim.loadSample(sound));
            }
            getSettings().setSoundsUpdate(false);
        }
        soundInterval = settings.getSoundInterval();
        soundEffects = settings.isSoundEffects();
    }

}
