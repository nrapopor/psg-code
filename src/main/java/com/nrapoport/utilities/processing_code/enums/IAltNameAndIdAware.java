/**
 * @author ubuntu - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 2, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapoport.utilities.processing_code.enums;

/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>Interface used by enums that support an id in addition to the ordinal value and an alternate name</DD>
 * <DT>Date:</DT>
 * <DD>Sep 2, 2017</DD>
 * </DL>
 *
 * @author ubuntu - Nick Rapoport
 *
 */
public interface IAltNameAndIdAware {

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>An alternate name for this enum value. Can have characters invalid in an enum name like spaces and special
     * chararacters</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @return
     */
    public String altName();

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>An arbitrary integer id for this enum value</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @return
     */
    public int getId();

}
