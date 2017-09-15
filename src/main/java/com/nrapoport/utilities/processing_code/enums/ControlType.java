/**
 * @author nvr - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 1, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapoport.utilities.processing_code.enums;

import java.util.List;

/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>Control type enum to handle different controls. Button or Slider</DD>
 * <DT>Date:</DT>
 * <DD>Sep 2, 2017</DD>
 * </DL>
 *
 * @author ubuntu - Nick Rapoport
 *
 */
public enum ControlType implements IAltNameAndIdAware {
    Button(1), Hat(2), Slider(3); //, Descriptor(4);

    private volatile static DDEnum<ControlType> ddEnum = DDEnum.getNewInstance(ControlType.class);

    public static ControlType getById(final int in) {
        return ddEnum.getById(in);
    }

    public static ControlType getByOrdinal(final int in) {
        return ddEnum.getByOrdinal(in);
    }

    public static ControlType getByString(final String in) {
        return ddEnum.getByString(in);
    }

    public static List<String> getDropdownStrings() {
        return ddEnum.getDropdownStrings();
    }

    private final int id;

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
