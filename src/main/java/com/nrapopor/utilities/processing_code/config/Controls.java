/**
 * @author ubuntu - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 3, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapopor.utilities.processing_code.config;

import java.util.List;

public enum Controls implements IAltNameAndIdAware {
    /**
     * <DL>
     * <DT>Device</DT>
     * <DD>use a joystick or a controller</DD>
     * </DL>
     */
    Device(0),
    /**
     * <DL>
     * <DT>Mouse</DT>
     * <DD>use your computer's mouse</DD>
     * </DL>
     */
    Mouse(1);

    private volatile static DDEnum<Controls> ddEnum = DDEnum.getNewInstance(Controls.class);

    public static Controls flip(final Controls in) {
        if (in == Controls.Device) {
            return Controls.Mouse;
        }
        return Controls.Device;
    }

    public static Controls getById(final int in) {
        return ddEnum.getById(in);
    }

    public static Controls getByOrdinal(final int in) {
        return ddEnum.getByOrdinal(in);
    }

    public static int getByString(final String in) {
        return ddEnum.getByString(in);
    }

    public static List<String> getDropdownStrings() {
        return ddEnum.getDropdownStrings();
    }

    private final int id;

    Controls(final int idIn) {
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
