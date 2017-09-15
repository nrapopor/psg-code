/**
 * @author ubuntu - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 1, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapoport.utilities.processing_code;

import processing.core.PApplet;

/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>TODO add description</DD>
 * <DT>Date:</DT>
 * <DD>Sep 1, 2017</DD>
 * </DL>
 *
 * @author ubuntu - Nick Rapoport
 *
 */
public interface IPAppletAware {
    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>returns the reference to the PApplet parent</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     * 
     * @return PApplet parent;
     */
    PApplet getParent();
}
