/**
 * @author nvr - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 1, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapoport.utilities.psgcode.config;

/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>the awareness interface for the <code>Settings</code> Object</DD>
 * <DT>Date:</DT>
 * <DD>Sep 2, 2017</DD>
 * </DL>
 *
 * @author nvr - Nick Rapoport
 *
 */
public interface ISettingsAware {

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>This method returns a reference to the Persistent settings (stored in JSON file) and potentially manipulated
     * with the settings dialogs</DD>
     * <DT>Date:</DT>
     * <DD>Sep 2, 2017</DD>
     * </DL>
     *
     * @return the reference to the settings object
     */
    Settings getSettings();

}
