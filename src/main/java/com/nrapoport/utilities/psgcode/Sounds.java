/**
 * @author ubuntu - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 2, 2017 ) Nick Rapoport all rights reserved.
 */

package com.nrapoport.utilities.psgcode;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.nrapoport.utilities.psgcode.config.RuntimeSettings;
import com.nrapoport.utilities.psgcode.config.Settings;

import ddf.minim.AudioOutput;
import ddf.minim.Minim;
import ddf.minim.MultiChannelBuffer;
import ddf.minim.ugens.Sampler;

/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>Play sounds, cause killer robots are not silent!</DD>
 * <DT>Date:</DT>
 * <DD>Sep 16, 2017</DD>
 * </DL>
 *
 * @author ubuntu - Nick Rapoport
 *
 */
public class Sounds extends AbstractPDE implements AutoCloseable {
    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Sounds.class);

    private static final int NUMBER_OF_SOUNDS = 21;

    private volatile int soundTimer = 0;

    private int soundInterval = 1000;

    private int numberOfVoices = 4;

    private boolean soundEffects = false; // set to true to enable sound effects by default

    private Minim minim;

    private volatile AudioOutput out;

    private final Settings settings;

    private final RuntimeSettings runtimeSettings;

    private final Map<String, Sampler> players = new LinkedHashMap<>(NUMBER_OF_SOUNDS);
    //Delay delay;

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Sounds Constructor</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param aParent
     */
    public Sounds(final PSGProcessingCode aParent) {
        super(aParent);
        settings = aParent.getSettings();
        runtimeSettings = aParent.getRuntimeSettings();
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
        if (out != null) {
            out.close();
        }
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
     * @param index
     *
     * @return return a Sampler with the passed index
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
        return (Sampler) players.values().toArray()[idx - 1];
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>get a reference to the audio player by name</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @param name
     *            the name of the particular sound (all lower case)
     *
     * @return return a Sampler with the passed index
     * @see java.util.Map#get(int)
     * @throws IllegalArgumentException
     *             if the sound name is invalid
     */

    protected Sampler get(final String name) {
        if (players.containsKey(name.toLowerCase())) {
            return players.get(name.toLowerCase());
        }
        final String msg =
            String.format("No such sound: '%1$s' Make sure that all sound names are in lower case", name.toLowerCase());
        final RuntimeException newEx = new IllegalArgumentException(msg);
        newEx.fillInStackTrace();
        log.error("Invalid sound name passed int", newEx);
        throw newEx;
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
    public Map<String, Sampler> getPlayers() {
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
     * <DD>Initialize the Minim audio library</DD>
     * <DT>Date:</DT>
     * <DD>Sep 9, 2017</DD>
     * </DL>
     *
     * @param aParent
     */
    private void initMinim() {
        if (soundEffects) {
            minim = new Minim(getParent());
            //out = minim.getLineOut();
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

    //    /**
    //     * <DL>
    //     * <DT>Description:</DT>
    //     * <DD>Play the sound at the passed index (1 based) , if sound effects are enabled</DD>
    //     * <DT>Date:</DT>
    //     * <DD>Sep 2, 2017</DD>
    //     * </DL>
    //     *
    //     * @param sound
    //     *            the 1 based index of the sound;
    //     */
    //    public void playSound(final int sound) {
    //        resetSettingsManagedFields(false);
    //        if (soundEffects) {
    //            final Sampler sampler = get(sound);
    //            @SuppressWarnings("unchecked")
    //            final String soundStr = ((Map.Entry<String, Sampler>) players.entrySet().toArray()[sound - 1]).getKey();
    //
    //            //getSettings().getSoundFilesMap().values().toArray()[players.indexOf(sampler)].toString();
    //            playSound(soundStr, sampler);
    //        }
    //    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>play a random sound from the passed list of sound names</DD>
     * <DT>Date:</DT>
     * <DD>Sep 24, 2017</DD>
     * </DL>
     *
     * @param soundList
     */
    protected void playListRandomSound(final List<String> soundList) {
        final int s = runtimeSettings.getRandom().nextInt(soundList.size());
        log.debug("playing sound, {} ", s);
        playSound(soundList.get(s));
        log.debug("done playing sound, {} ", soundList.get(s));
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
    public void playSound(final String sound) {
        resetSettingsManagedFields(false);
        if (soundEffects) {
            final Sampler sampler = get(sound);
            final String soundStr = sound.toLowerCase();
            playSound(soundStr, sampler);
        }

    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Play the selected sound</DD>
     * <DT>Date:</DT>
     * <DD>Sep 18, 2017</DD>
     * </DL>
     *
     * @param sound
     * @param sampler
     */
    protected void playSound(final String sound, final Sampler sampler) {
        Sounds.log.debug("{} waiting to Play sound: {}", Thread.currentThread().getName(), sound);
        new Thread(() -> {
            try {
                synchronized (out) {
                    sampler.patch(out);
                    TimeUnit.MILLISECONDS.sleep(100);
                    Sounds.log.debug("{} Playing sound: {}", Thread.currentThread().getName(), sound);
                    sampler.trigger();
                }
                TimeUnit.MILLISECONDS.sleep(getSettings().getSamplerDelay());
            } catch (final InterruptedException ex) {
                Sounds.log.debug("caught InterruptedException :", ex); //$NON-NLS-1$
            } finally {
                synchronized (out) {
                    sampler.unpatch(out);
                }
                Sounds.log.debug("{} Done Playing sound: {}", Thread.currentThread().getName(), sound);
            }
        }).start();
        //            try {
        //                Thread.sleep(2000);
        //            } catch (final InterruptedException ex) {
        //                log.debug("caught InterruptedException :", ex); //$NON-NLS-1$
        //            }
        //            player.unpatch(out);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Play a random sound from the departed sound list</DD>
     * <DT>Date:</DT>
     * <DD>Sep 24, 2017</DD>
     * </DL>
     */
    public void randomDepartedSound() {
        resetSettingsManagedFields(false);
        if (soundEffects) {
            playListRandomSound(getSettings().getDepartedSoundList());
        }
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Randomly play any of the sounds from the idleRandomSoundList</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     */
    public void randomIdleSound() {
        resetSettingsManagedFields(false);
        if (soundEffects) {
            playListRandomSound(getSettings().getIdleSoundList());
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
            float sampleRate = 0;
            if (soundEffects && getMinim() == null) {
                //we must have turned on sounds mid run
                initMinim();
            }
            closePlayers();
            for (final String soundKey : settings.getSoundFilesMap().keySet()) {
                //players.add(minim.loadSample(sound));
                final MultiChannelBuffer sampleData = new MultiChannelBuffer(1024, 2);
                final float rate = minim.loadFileIntoBuffer(settings.getSoundFilesMap().get(soundKey), sampleData);
                final Sampler sampler = new Sampler(sampleData, rate, numberOfVoices);
                if (rate > sampleRate) {
                    sampleRate = rate;
                    log.debug("sampleRate: {},{}", sampleRate, rate);
                }
                players.put(soundKey.toLowerCase(), sampler);
            }
            getSettings().setSoundsUpdate(false);
            if (out != null) {
                out.close();
            }
            out = minim.getLineOut(Minim.STEREO, 1024, sampleRate);
        }
    }

}
