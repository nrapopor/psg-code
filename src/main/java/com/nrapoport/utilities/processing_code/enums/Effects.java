/**
 * @author ubuntu - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 3, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapoport.utilities.processing_code.enums;

import java.util.List;

/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>Valid effects</DD>
 * <DT>Date:</DT>
 * <DD>Sep 16, 2017</DD>
 * </DL>
 *
 * @author ubuntu - Nick Rapoport
 *
 */
public enum Effects implements IAltNameAndIdAware {
    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>Opaque (Effects) =</DD>
     * </DL>
     */
    Opaque(0, null),
    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>Transparent (Effects) =</DD>
     * </DL>
     */
    Transparent(1, null),
    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>Negative (Effects) =</DD>
     * </DL>
     */
    Negative(2, null),
    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>NegativeAndTransparent (Effects) =</DD>
     * </DL>
     */
    NegativeAndTransparent(3, "Negative & Transparent");

    private volatile static DDEnum<Effects> ddEnum = DDEnum.getNewInstance(Effects.class);

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
    public static Effects getById(final int in) {
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
    public static Effects getByOrdinal(final int in) {
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
    public static Effects getByString(final String in) {
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

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>rotate through all the effects in order</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param effect
     * @return
     */
    public static Effects radioEffect(final Effects effect) {
        int newEffectId = effect.getId() + 1;
        if (newEffectId >= Effects.values().length) {
            newEffectId = 0; //rollover
        }
        return Effects.getById(newEffectId);
    }

    private final int id;

    private final String altName;

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Effects Constructor</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     * 
     * @param idIn
     * @param aAltName
     */
    Effects(final int idIn, final String aAltName) {
        id = idIn;
        altName = aAltName == null ? name() : aAltName;
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
