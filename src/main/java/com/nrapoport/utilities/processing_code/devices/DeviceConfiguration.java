/**
 * @author ubuntu - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 11, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapoport.utilities.processing_code.devices;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.annotations.Expose;
import com.nrapoport.utilities.processing_code.enums.DeviceButtonActions;
import com.nrapoport.utilities.processing_code.enums.DeviceSliderActions;

/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>TODO add description</DD>
 * <DT>Date:</DT>
 * <DD>Sep 11, 2017</DD>
 * </DL>
 *
 * @author ubuntu - Nick Rapoport
 *
 */
public class DeviceConfiguration {
    @SuppressWarnings("unused")
    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(DeviceConfiguration.class);

    private @Expose Map<String, DeviceButtonActions> deviceButtons = new LinkedHashMap<>();

    private @Expose Map<String, DeviceSliderActions> deviceSliders = new LinkedHashMap<>();

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>DeviceConfiguration Constructor</DD>
     * <DT>Date:</DT>
     * <DD>Sep 11, 2017</DD>
     * </DL>
     */
    public DeviceConfiguration() {
        // TODO Auto-generated constructor stub
    }

    public void addControl() {

    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the deviceButtons property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 11, 2017</DD>
     * </DL>
     *
     * @return the value of deviceButtons field
     */
    public Map<String, DeviceButtonActions> getDeviceButtons() {
        return deviceButtons;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the deviceSliders property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 11, 2017</DD>
     * </DL>
     *
     * @return the value of deviceSliders field
     */
    public Map<String, DeviceSliderActions> getDeviceSliders() {
        return deviceSliders;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the deviceButtons property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 11, 2017</DD>
     * </DL>
     *
     * @param aDeviceButtons
     *            new value for the deviceButtons property
     */
    public void setDeviceButtons(final Map<String, DeviceButtonActions> aDeviceButtons) {
        deviceButtons = aDeviceButtons;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Setter for the deviceSliders property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 11, 2017</DD>
     * </DL>
     *
     * @param aDeviceSliders
     *            new value for the deviceSliders property
     */
    public void setDeviceSliders(final Map<String, DeviceSliderActions> aDeviceSliders) {
        deviceSliders = aDeviceSliders;
    }

}
