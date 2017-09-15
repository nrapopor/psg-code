/**
 * @author ubuntu - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 3, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapoport.utilities.processing_code.enums;

import java.util.List;

public enum DeviceButtonActions implements IAltNameAndIdAware {
    /**
     * <DL>
     * <DT>NoAction</DT>
     * <DD>Do not use this control</DD>
     * </DL>
     */
    NoAction(0, "No Action"),
    /**
     * <DL>
     * <DT>Fire</DT>
     * <DD>Use this control to dispense product ;-)</DD>
     * </DL>
     */
    Fire(2, null),
    /**
     * <DL>
     * <DT>PreciseAim</DT>
     * <DD>use this control for fine tuning your aim (Manual mode)</DD>
     * </DL>
     */
    PreciseAim(3, "Precise Aim"),
    /**
     * <DL>
     * <DT>CenterGun</DT>
     * <DD>Center the gun in the middile of the view</DD>
     * </DL>
     */
    CenterGun(4, "Center Gun"),
    /**
     * <DL>
     * <DT>AutoAimOn</DT>
     * <DD>Turn on automatic aiming</DD>
     * </DL>
     */
    AutoAimOn(5, "Auto Aim On"),
    /**
     * <DL>
     * <DT>AutoAimOff</DT>
     * <DD>Turn off automatic aiming</DD>
     * </DL>
     */
    AutoAimOff(6, "Auto Aim Off"),
    /**
     * <DL>
     * <DT>InputDevToggle</DT>
     * <DD>toggle the input device (on/off)</DD>
     * </DL>
     */
    InputDevToggle(7, "Input Dev On/Off"),
    /**
     * <DL>
     * <DT>RandomSound</DT>
     * <DD>Regale the customer with a random saying, (soundEffects and runType must allow sounds)</DD>
     * </DL>
     */
    RandomSound(8, "Random Sound");

    private volatile static DDEnum<DeviceButtonActions> ddEnum = DDEnum.getNewInstance(DeviceButtonActions.class);

    public static DeviceButtonActions getById(final int in) {
        return ddEnum.getById(in);
    }

    public static DeviceButtonActions getByOrdinal(final int in) {
        return ddEnum.getByOrdinal(in);
    }

    public static DeviceButtonActions getByString(final String in) {
        return ddEnum.getByString(in);
    }

    public static List<String> getDropdownStrings() {
        return ddEnum.getDropdownStrings();
    }

    private final int id;

    private final String altName;

    DeviceButtonActions(final int idIn, final String aAltName) {
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
