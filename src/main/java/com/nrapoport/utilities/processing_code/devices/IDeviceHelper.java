/**
 * @author ubuntu - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 11, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapoport.utilities.processing_code.devices;

import com.nrapoport.utilities.processing_code.enums.ControlType;

/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>This interface describes the Device Controller element wrappers</DD>
 * <DT>Date:</DT>
 * <DD>Sep 11, 2017</DD>
 * </DL>
 *
 * @author ubuntu - Nick Rapoport
 *
 */
public interface IDeviceHelper {

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>returns the type of control this Helper class is wrapping (Button or Slider)</DD>
     * <DT>Date:</DT>
     * <DD>Sep 11, 2017</DD>
     * </DL>
     *
     * @return
     *         <DL>
     *         <DT><code>ControlType.Button</code></DT>
     *         <DD>if control is a button</DD>
     *         <DT><code>ControlType.Slider</code></DT>
     *         <DD>if control is a slider</DD>
     *         </DL>
     */
    ControlType getControlType();

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>returns the name of the control (used for config file)</DD>
     * <DT>Date:</DT>
     * <DD>Sep 11, 2017</DD>
     * </DL>
     *
     * @return the name of the control control
     */
    String getName();

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Get the next X position that a new GUI control can be added without overlapping this one</DD>
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
     * <DD>Get the next Y position that a new GUI control can be added without overlapping this one</DD>
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
     * <DD>Get Selected Text for action</DD>
     * <DT>Date:</DT>
     * <DD>Sep 11, 2017</DD>
     * </DL>
     *
     * @return
     */
    String getSelectedText();

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Set Selected Text for Action</DD>
     * <DT>Date:</DT>
     * <DD>Sep 14, 2017</DD>
     * </DL>
     *
     * @param drIndex
     */
    void setSelectedText(int drIndex);

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>update the GUI control from the device control</DD>
     * <DT>Date:</DT>
     * <DD>Sep 11, 2017</DD>
     * </DL>
     */
    void update();

}
