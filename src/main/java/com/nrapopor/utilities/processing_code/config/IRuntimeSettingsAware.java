/**
 * @author nvr - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 1, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapopor.utilities.processing_code.config;

/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>the awareness interface for the <code>RuntimeSettings</code> Object</DD>
 * <DT>Date:</DT>
 * <DD>Sep 2, 2017</DD>
 * </DL>
 *
 * @author nvr - Nick Rapoport
 *
 */
public interface IRuntimeSettingsAware {

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>This method returns a reference to the volatile settings</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @return the reference to the settings object
     */
    RuntimeSettings getRuntimeSettings();

}
