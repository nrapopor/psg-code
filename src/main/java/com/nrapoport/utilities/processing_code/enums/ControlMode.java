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
    /**
     * <DL>
     * <DT>Manual</DT>
     * <DD>manual mode using a controller or mouse</DD>
     * </DL>
     */
    Manual(0),
    /**
     * <DL>
     * <DT>Autonomous</DT>
     * <DD>automatic mode shooting when the robot decides</DD>
     * </DL>
     */
    Autonomous(1);

    private volatile static DDEnum<ControlMode> ddEnum = DDEnum.getNewInstance(ControlMode.class);

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>flip between autonomous and manual modes</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param in
     * @return
     */
    public static ControlMode flip(final ControlMode in) {
        if (in == ControlMode.Autonomous) {
            return ControlMode.Manual;
        }
        return ControlMode.Autonomous;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Return the ControlMode with the passed id</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param in
     * @return
     */
    public static ControlMode getById(final int in) {
        return ddEnum.getById(in);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Return the ControlMode with the passed ordinal</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param in
     * @return
     */
    public static ControlMode getByOrdinal(final int in) {
        return ddEnum.getByOrdinal(in);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Return the ControlMode with the passed nsme ot altName</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param in
     * @return
     */
    public static ControlMode getByString(final String in) {
        return ddEnum.getByString(in);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Return the ControlMode's list of altNames (or names if altName is null)</DD>
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
     * <DD>ControlMode Constructor</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param idIn
     */
    ControlMode(final int idIn) {
        id = idIn;
    }

    /** {@inheritDoc} */
    @Override
    public String altName() {
        return name();
    }

    /** {@inheritDoc} */
    @Override
    public int getId() {
        return id;
    }

}
