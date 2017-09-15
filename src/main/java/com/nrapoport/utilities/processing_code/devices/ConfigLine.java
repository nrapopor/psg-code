/**
 * @author ubuntu - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 14, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapoport.utilities.processing_code.devices;

import com.nrapoport.utilities.processing_code.enums.ControlType;
import com.nrapoport.utilities.processing_code.enums.DeviceButtonActions;

/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>TODO add description</DD>
 * <DT>Date:</DT>
 * <DD>Sep 14, 2017</DD>
 * </DL>
 *
 * @author ubuntu - Nick Rapoport
 *
 */
public class ConfigLine {
    //@SuppressWarnings("unused")
    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ConfigLine.class);

    public static final String SEPARATOR = "\t";

    private final String key;

    private final String desc;

    private final ControlType type;

    private final String inputName;

    private final int inputConNo;

    private final float multiplier;

    private final float tolerance;

    private final int index;

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>ConfigLine Constructor</DD>
     * <DT>Date:</DT>
     * <DD>Sep 14, 2017</DD>
     * </DL>
     */
    public ConfigLine(final String line) {
        final String[] tokens = line.split(SEPARATOR);
        if (tokens.length < 10) {
            key = tokens[0];
            desc = tokens[1];
            type = ControlType.getById(Integer.parseInt(tokens[2]));
            inputName = tokens[4];
            inputConNo = Integer.parseInt(tokens[5]);
            multiplier = Float.parseFloat(tokens[6]);
            tolerance = Float.parseFloat(tokens[7]);
            index = Integer.parseInt(tokens[8]);
        } else {
            final String msg = String.format("Passed invalid argument '%1$s' to the ConfigLine constructor, "
                + "expecting tab separated string with 9 elements, but got: %2$d", line, tokens.length);
            final RuntimeException ex = new RuntimeException(msg);
            log.error(msg, ex);
            throw ex;
        }
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>ConfigLine Constructor</DD>
     * <DT>Date:</DT>
     * <DD>Sep 14, 2017</DD>
     * </DL>
     *
     * @param aKey
     * @param aDesc
     * @param aType
     * @param aInputName
     * @param aInputConNo
     * @param aMultiplier
     * @param aTolerance
     * @param aIndex
     */
    public ConfigLine(final String aKey, final String aDesc, final ControlType aType, final String aInputName,
            final int aInputConNo, final float aMultiplier, final float aTolerance, final int aIndex) {
        super();
        key = aKey;
        desc = aDesc;
        type = aType;
        inputName = aInputName;
        inputConNo = aInputConNo;
        multiplier = aMultiplier;
        tolerance = aTolerance;
        index = aIndex;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the desc property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 14, 2017</DD>
     * </DL>
     *
     * @return the value of desc field
     */
    public String getDesc() {
        return desc;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the index property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 14, 2017</DD>
     * </DL>
     *
     * @return the value of index field
     */
    public int getIndex() {
        return index;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the inputConNo property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 14, 2017</DD>
     * </DL>
     *
     * @return the value of inputConNo field
     */
    public int getInputConNo() {
        return inputConNo;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the inputName property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 14, 2017</DD>
     * </DL>
     *
     * @return the value of inputName field
     */
    public String getInputName() {
        return inputName;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the key property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 14, 2017</DD>
     * </DL>
     *
     * @return the value of key field
     */
    public String getKey() {
        return key;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the multiplier property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 14, 2017</DD>
     * </DL>
     *
     * @return the value of multiplier field
     */
    public float getMultiplier() {
        return multiplier;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the tolerance property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 14, 2017</DD>
     * </DL>
     *
     * @return the value of tolerance field
     */
    public float getTolerance() {
        return tolerance;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the type property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 14, 2017</DD>
     * </DL>
     *
     * @return the value of type field
     */
    public ControlType getType() {
        return type;
    }

    /** {@inheritDoc} */
    @Override

    public String toString() {
        String result = "";
        //        final String key = actionName();
        if (!key.isEmpty() && !key.equals(DeviceButtonActions.NoAction.name())) { //using the DeviceButtonActions but DeviceSliderActions has the same value
            result = key + SEPARATOR + desc + SEPARATOR + type.getId() + SEPARATOR + type.name().toUpperCase()
                + SEPARATOR + inputName + SEPARATOR + inputConNo + SEPARATOR + multiplier + SEPARATOR + tolerance
                + SEPARATOR + index + System.lineSeparator();
        }
        return result;
    }

}
