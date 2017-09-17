package com.nrapoport.utilities.psgcode.enums;

import java.util.List;

/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>Gun firing modes</DD>
 * <DT>Date:</DT>
 * <DD>Sep 16, 2017</DD>
 * </DL>
 *
 * @author ubuntu - Nick Rapoport
 *
 */
public enum FiringMode implements IAltNameAndIdAware {
    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>Automatic (FiringMode) =</DD>
     * </DL>
     */
    Automatic(1, null),
    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>SemiAuto (FiringMode) =</DD>
     * </DL>
     */
    SemiAuto(3, "Semi-Automatic");

    private volatile static DDEnum<FiringMode> ddEnum = DDEnum.getNewInstance(FiringMode.class);

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Return the FiringMode with the passed id</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param in
     * @return
     */
    public static FiringMode getById(final int in) {
        return ddEnum.getById(in);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Return the FiringMode with the passed ordinal</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param in
     * @return
     */
    public static FiringMode getByOrdinal(final int in) {
        return ddEnum.getByOrdinal(in);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Return the FiringMode with the passed name or altName</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param in
     * @return
     */
    public static FiringMode getByString(final String in) {
        return ddEnum.getByString(in);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Return the FiringMode's list of altNames (or names if altName is null)</DD>
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

    private final String altName;

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>FiringMode Constructor</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     * 
     * @param idIn
     * @param aAltName
     */
    FiringMode(final int idIn, final String aAltName) {
        id = idIn;
        altName = aAltName == null ? name() : aAltName;
        ;
    }

    /** {@inheritDoc} */
    @Override
    public String altName() {
        return altName;
    }

    @Override
    public int getId() {
        return id;
    }
}
