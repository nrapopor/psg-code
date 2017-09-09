/**
 * @author ubuntu - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 3, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapopor.utilities.processing_code.config;

import java.util.List;

public enum RunType implements IAltNameAndIdAware {
    /**
     * <DL>
     * <DT>Full</DT>
     * <DD>run with all the bells and whistles Control Panel, Sounds, etc</DD>
     * </DL>
     */
    Full(0),
    /**
     * <DL>
     * <DT>Mouse</DT>
     * <DD>Run with no bells and whistles (no Control Panel, Sounds, etc). This mode is for running on small hardware
     * like Raspberry PI</DD>
     * </DL>
     */
    Minimal(1),

    /**
     * <DL>
     * <DT>Mouse</DT>
     * <DD>Just like Minimal but add Sounds (because killer robots are expected to talk!). This mode is for running on
     * small hardware like Raspberry PI</DD>
     * </DL>
     */
    MinimalWithSounds(2);
    private volatile static DDEnum<RunType> ddEnum = DDEnum.getNewInstance(RunType.class);

    public static RunType getById(final int in) {
        return ddEnum.getById(in);
    }

    public static RunType getByOrdinal(final int in) {
        return ddEnum.getByOrdinal(in);
    }

    public static int getByString(final String in) {
        return ddEnum.getByString(in);
    }

    public static List<String> getDropdownStrings() {
        return ddEnum.getDropdownStrings();
    }

    private final int id;

    RunType(final int idIn) {
        id = idIn;
    }

    /** {@inheritDoc} */
    @Override
    public String altName() {
        return name();
    }

    @Override
    public int getId() {
        return id;
    }

    public boolean isSoundsAvailable() {
        if (this == Minimal) {
            return false;
        }
        return true;
    }

}
