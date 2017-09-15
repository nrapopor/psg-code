/**
 * @author ubuntu - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 4, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapoport.utilities.processing_code.config;

import com.google.gson.annotations.Expose;

/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>this class hold a color description for persisting to/from JSON</DD>
 * <DT>Date:</DT>
 * <DD>Sep 4, 2017</DD>
 * </DL>
 *
 * @author ubuntu - Nick Rapoport
 *
 */
public class ConfigurationColor {
    private @Expose int red;

    private @Expose int green;

    private @Expose int blue;

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Color Constructor</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aRed
     * @param aGreen
     * @param aBlue
     */
    public ConfigurationColor(final int aRed, final int aGreen, final int aBlue) {
        super();
        red = aRed;
        green = aGreen;
        blue = aBlue;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>ConfigurationColor Constructor</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param color
     *            an int[] of three color values {red,green,blue}
     */
    public ConfigurationColor(final int[] color) {
        super();
        fromArray(color);
    }

    public int[] asArray() {
        return new int[] { red, green, blue };
    }

    public void fromArray(final int[] color) {
        red = color[0];
        green = color[1];
        blue = color[2];
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the blue property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of blue field
     */
    public int getBlue() {
        return blue;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the green property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of green field
     */
    public int getGreen() {
        return green;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the red property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of red field
     */
    public int getRed() {
        return red;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the blue property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aBlue
     *            new value for the blue property
     */
    public void setBlue(final int aBlue) {
        blue = aBlue;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the green property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aGreen
     *            new value for the green property
     */
    public void setGreen(final int aGreen) {
        green = aGreen;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the red property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aRed
     *            new value for the red property
     */
    public void setRed(final int aRed) {
        red = aRed;
    }

}
