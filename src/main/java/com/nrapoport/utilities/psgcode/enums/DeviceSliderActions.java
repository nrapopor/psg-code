/**
 * @author ubuntu - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 3, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapoport.utilities.psgcode.enums;

import java.util.List;

/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>Valid actions that can assigned to a Slider</DD>
 * <DT>Date:</DT>
 * <DD>Sep 16, 2017</DD>
 * </DL>
 *
 * @author ubuntu - Nick Rapoport
 *
 */
public enum DeviceSliderActions implements IAltNameAndIdAware {
    /**
     * <DL>
     * <DT>NoAction</DT>
     * <DD>Do not use this slider</DD>
     * </DL>
     */
    NoAction(0, "No Action"),
    /**
     * <DL>
     * <DT>Pan</DT>
     * <DD>Use this slider to keep the customer in the sights</DD>
     * </DL>
     */
    Pan(2, null),
    /**
     * <DL>
     * <DT>Tilt</DT>
     * <DD>tilt the camera</DD>
     * </DL>
     */
    Tilt(3, null),
    /**
     * <DL>
     * <DT>InvertPan</DT>
     * <DD>invert the panning motion</DD>
     * </DL>
     */
    InvertPan(4, "Pan (Invert)"),
    /**
     * <DL>
     * <DT>InvertTilt</DT>
     * <DD>Invert the tilting motion</DD>
     * </DL>
     */
    InvertTilt(5, "Tilt (Invert)");

    private volatile static DDEnum<DeviceSliderActions> ddEnum = DDEnum.getNewInstance(DeviceSliderActions.class);

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Return the DeviceSliderActions with the passed id</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param in
     * @return
     */
    public static DeviceSliderActions getById(final int in) {
        return ddEnum.getById(in);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Return the DeviceSliderActions with the passed ordinal</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param in
     * @return
     */
    public static DeviceSliderActions getByOrdinal(final int in) {
        return ddEnum.getByOrdinal(in);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Return the DeviceSliderActions with the passed name or altName</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param in
     * @return
     */
    public static DeviceSliderActions getByString(final String in) {
        return ddEnum.getByString(in);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Return the DeviceSliderActions' list of altNames (or names if altName is null)</DD>
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
     * <DD>DeviceSliderActions Constructor</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     * 
     * @param idIn
     * @param aAltName
     */
    DeviceSliderActions(final int idIn, final String aAltName) {
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
