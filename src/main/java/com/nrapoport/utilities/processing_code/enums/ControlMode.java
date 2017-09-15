/**
 * @author nvr - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 1, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapoport.utilities.processing_code.enums;

import java.util.List;

/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>Control mode enum to handle gun operations. autonomous or manual</DD>
 * <DT>Date:</DT>
 * <DD>Sep 2, 2017</DD>
 * </DL>
 *
 * @author ubuntu - Nick Rapoport
 *
 */
public enum ControlMode implements IAltNameAndIdAware {
    Manual(0), Autonomous(1);

    private volatile static DDEnum<ControlMode> ddEnum = DDEnum.getNewInstance(ControlMode.class);

    public static ControlMode flip(final ControlMode in) {
        if (in == ControlMode.Autonomous) {
            return ControlMode.Manual;
        }
        return ControlMode.Autonomous;
    }

    public static ControlMode getById(final int in) {
        return ddEnum.getById(in);
    }

    public static ControlMode getByOrdinal(final int in) {
        return ddEnum.getByOrdinal(in);
    }

    public static ControlMode getByString(final String in) {
        return ddEnum.getByString(in);
    }

    public static List<String> getDropdownStrings() {
        return ddEnum.getDropdownStrings();
    }

    private final int id;

    ControlMode(final int idIn) {
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
