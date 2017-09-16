/**
 * @author ubuntu - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 3, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapoport.utilities.processing_code.enums;

import java.util.List;

/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>Enum representing the type of run</DD>
 * <DT>Date:</DT>
 * <DD>Sep 16, 2017</DD>
 * </DL>
 *
 * @author ubuntu - Nick Rapoport
 *
 */
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

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Return the RunType with the passed id</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param in
     * @return
     */
    public static RunType getById(final int in) {
        return ddEnum.getById(in);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Return the RunType with the passed ordinal</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param in
     * @return
     */
    public static RunType getByOrdinal(final int in) {
        return ddEnum.getByOrdinal(in);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Return the RunType with the passed name or altName</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param in
     * @return
     */
    public static RunType getByString(final String in) {
        return ddEnum.getByString(in);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Return the RunType's list of altNames (or names if altName is null)</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param in
     * @return
     */
    public static List<String> getDropdownStrings() {
        return ddEnum.getDropdownStrings();
    }

    private final int id;

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>RunType Constructor</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param idIn
     */
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

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>true if sounds are available with this RunType</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @return
     */
    public boolean isSoundsAvailable() {
        if (this == Minimal) {
            return false;
        }
        return true;
    }

}
