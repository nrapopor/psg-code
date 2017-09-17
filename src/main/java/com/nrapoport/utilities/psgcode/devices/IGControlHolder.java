/**
 * @author ubuntu - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 10, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapoport.utilities.psgcode.devices;

/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>A gui element control interface</DD>
 * <DT>Date:</DT>
 * <DD>Sep 10, 2017</DD>
 * </DL>
 *
 * @author ubuntu - Nick Rapoport
 *
 */
public interface IGControlHolder {

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>reset values and show GUI components associated with this control</DD>
     * <DT>Date:</DT>
     * <DD>Sep 10, 2017</DD>
     * </DL>
     *
     * @param labelString
     */
    void activate(String labelString);

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>reset values and hide GUI components associated with this control</DD>
     * <DT>Date:</DT>
     * <DD>Sep 10, 2017</DD>
     * </DL>
     */
    void clear();

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Get the next Y position that a new control can be added without overlapping this one</DD>
     * <DT>Date:</DT>
     * <DD>Sep 13, 2017</DD>
     * </DL>
     *
     * @return
     */
    float getNextXpos();

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Get the next X position that a new control can be added without overlapping this one</DD>
     * <DT>Date:</DT>
     * <DD>Sep 13, 2017</DD>
     * </DL>
     *
     * @return
     */
    float getNextYpos();

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>return the selected value for the drop down list associated with this control</DD>
     * <DT>Date:</DT>
     * <DD>Sep 10, 2017</DD>
     * </DL>
     *
     * @return
     */
    public String getSelectedText();

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Set the selected action drop down to the passed value by index (ordinal). Use the appropriate list (Slider or
     * Button)</DD>
     * <DT>Date:</DT>
     * <DD>Sep 12, 2017</DD>
     * </DL>
     *
     * @param ordinal
     *            which value to set being selected
     */
    void setSelectedText(final int ordinal);

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Set the selected action drop down to the passed value by String value. Use the appropriate list (Slider or
     * Button)</DD>
     * <DT>Date:</DT>
     * <DD>Sep 12, 2017</DD>
     * </DL>
     *
     * @param value
     *            which value from the dropDown to set being selected
     */
    void setSelectedText(final String value);

}
