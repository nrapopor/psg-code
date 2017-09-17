/**
 * @author ubuntu - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 4, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapoport.utilities.psgcode.config;

import com.google.gson.annotations.Expose;

/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>This class is used exclusively for serialization to/from JSON</DD>
 * <DT>Date:</DT>
 * <DD>Sep 4, 2017</DD>
 * </DL>
 *
 * @author ubuntu - Nick Rapoport
 *
 */
public class ConfigurationSettingsHolder {
    @SuppressWarnings("unused")
    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ConfigurationSettingsHolder.class);

    private @Expose ConfigurationSettings configuration = null;

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>ConfigurationSettingsHolder Constructor</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     */
    public ConfigurationSettingsHolder() {
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>ConfigurationSettingsHolder Constructor</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aConfiguration
     */
    public ConfigurationSettingsHolder(final ConfigurationSettings aConfiguration) {
        super();
        configuration = aConfiguration;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the configuration property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of configuration field
     */
    public ConfigurationSettings getConfiguration() {
        return configuration;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the configuration property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param aConfiguration
     *            new value for the configuration property
     */
    public void setConfiguration(final ConfigurationSettings aConfiguration) {
        configuration = aConfiguration;
    }

}
