/**
 * @author ubuntu - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 4, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapoport.utilities.processing_code.config;

import com.google.gson.annotations.Expose;

/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>TODO add ConfigurationSettings description</DD>
 * <DT>Date:</DT>
 * <DD>Sep 4, 2017</DD>
 * </DL>
 *
 * @author ubuntu - Nick Rapoport
 *
 */
public class ColorManagement {
    private @Expose int minSize = 0;

    private @Expose int tolerance = 100;

    private @Expose ConfigurationColor color;

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>ColorManagement Constructor</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     */
    public ColorManagement() {
        super();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>ColorManagement Constructor</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aMinSize
     * @param aColor
     * @param aTolerance
     */
    public ColorManagement(final int aMinSize, final int aTolerance, final ConfigurationColor aColor) {
        super();
        minSize = aMinSize;
        color = aColor;
        tolerance = aTolerance;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the color property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of color field
     */
    public ConfigurationColor getColor() {
        return color;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the minSize property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of minSize field
     */
    public int getMinSize() {
        return minSize;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the tolerance property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of tolerance field
     */
    public int getTolerance() {
        return tolerance;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the color property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aColor
     *            new value for the color property
     */
    public void setColor(final ConfigurationColor aColor) {
        color = aColor;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the minSize property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aMinSize
     *            new value for the minSize property
     */
    public void setMinSize(final int aMinSize) {
        minSize = aMinSize;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the tolerance property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aTolerance
     *            new value for the tolerance property
     */
    public void setTolerance(final int aTolerance) {
        tolerance = aTolerance;
    }
}
