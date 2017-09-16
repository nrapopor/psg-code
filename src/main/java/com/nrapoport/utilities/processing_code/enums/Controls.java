/**
 * @author ubuntu - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 3, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapoport.utilities.processing_code.enums;

import java.util.List;

/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>control types</DD>
 * <DT>Date:</DT>
 * <DD>Sep 16, 2017</DD>
 * </DL>
 *
 * @author ubuntu - Nick Rapoport
 *
 */
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

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>flip between Mous and Device</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param in
     * @return
     */
    public static Controls flip(final Controls in) {
        if (in == Controls.Device) {
            return Controls.Mouse;
        }
        return Controls.Device;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Return the Controls with the passed id</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param in
     * @return
     */
    public static Controls getById(final int in) {
        return ddEnum.getById(in);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Return the Controls with the passed ordinal</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param in
     * @return
     */
    public static Controls getByOrdinal(final int in) {
        return ddEnum.getByOrdinal(in);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Return the Controls with the passed name or altName</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param in
     * @return
     */
    public static Controls getByString(final String in) {
        return ddEnum.getByString(in);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Return the Controls' list of altNames (or names if altName is null)</DD>
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
     * <DD>Controls Constructor</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param idIn
     */
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
