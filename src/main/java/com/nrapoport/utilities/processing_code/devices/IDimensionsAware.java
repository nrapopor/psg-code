/**
 * @author ubuntu - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 13, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapoport.utilities.processing_code.devices;

/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>An awareness interface for window sizes</DD>
 * <DT>Date:</DT>
 * <DD>Sep 13, 2017</DD>
 * </DL>
 *
 * @author ubuntu - Nick Rapoport
 *
 */
public interface IDimensionsAware {

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the height property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 13, 2017</DD>
     * </DL>
     *
     * @return the value of height field
     */
    float getCurrHeight();

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the width property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 13, 2017</DD>
     * </DL>
     *
     * @return the value of width field
     */
    float getCurrWidth();

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>maximum previous control's X position</DD>
     * <DT>Date:</DT>
     * <DD>Sep 13, 2017</DD>
     * </DL>
     *
     * @return
     */
    float getPrevMaxX();

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>maximum previous control's Y position</DD>
     * <DT>Date:</DT>
     * <DD>Sep 13, 2017</DD>
     * </DL>
     *
     * @return
     */
    float getPrevMaxY();

}
