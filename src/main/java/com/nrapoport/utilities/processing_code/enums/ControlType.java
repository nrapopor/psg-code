/**
 * @author nvr - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 1, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapoport.utilities.processing_code.enums;

import java.util.List;

/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>Control type enum to handle different controls. Button, Slider or Hat</DD>
 * <DT>Date:</DT>
 * <DD>Sep 2, 2017</DD>
 * </DL>
 *
 * @author ubuntu - Nick Rapoport
 *
 */
public enum ControlType implements IAltNameAndIdAware {
    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>Button (ControlType) =</DD>
     * </DL>
     */
    Button(1),
    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>Hat (ControlType) =</DD>
     * </DL>
     */
    Hat(2),
    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>Slider (ControlType) =</DD>
     * </DL>
     */
    Slider(3); //, Descriptor(4);

    private volatile static DDEnum<ControlType> ddEnum = DDEnum.getNewInstance(ControlType.class);

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Return the ControlType with the passed id</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param in
     * @return
     */
    public static ControlType getById(final int in) {
        return ddEnum.getById(in);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Return the ControlType with the passed ordinal</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param in
     * @return
     */
    public static ControlType getByOrdinal(final int in) {
        return ddEnum.getByOrdinal(in);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Return the ControlType with the passed name or altName</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param in
     * @return
     */
    public static ControlType getByString(final String in) {
        return ddEnum.getByString(in);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Return the ControlType's list of altNames (or names if altName is null)</DD>
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
     * <DD>ControlType Constructor</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     * 
     * @param idIn
     */
    ControlType(final int idIn) {
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

}
