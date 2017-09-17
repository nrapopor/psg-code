/**
 * @author ubuntu - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 10, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapoport.utilities.psgcode.devices;

import com.nrapoport.utilities.psgcode.enums.ControlType;

import g4p_controls.GConstants;
import g4p_controls.GDropList;
import g4p_controls.GPanel;
import g4p_controls.GSlider;

/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>A wrapper class for a Slider</DD>
 * <DT>Date:</DT>
 * <DD>Sep 10, 2017</DD>
 * </DL>
 *
 * @author ubuntu - Nick Rapoport
 *
 */
public class SliderHolder extends AbstractDropListHolder {
    @SuppressWarnings("unused")
    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SliderHolder.class);

    //    private static final float baseY = 40.0f;

    //private static final float droplistXpos = 480.0f;

    // private final float sliderPosX = 370.0f;

    private final GSlider slider;

    //    private final float posStep = 30.0f;
    //
    //    private final float sliderWidth = 100.0f;
    //
    //    private final float sliderHeight = 20.0f;
    //
    //    private final float trackWidth = 10.0f;

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>ButtonHolder Constructor</DD>
     * <DT>Date:</DT>
     * <DD>Sep 10, 2017</DD>
     * </DL>
     *
     * @param aAction
     * @param aLabel
     */
    public SliderHolder(final GDropList aAction, final GSlider aLabel) {
        super(aAction, ControlType.Slider);
        slider = aLabel;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>SliderHolder Constructor</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param parent
     * @param index
     * @param panel
     */
    public SliderHolder(final IDimensionsAware parent, final int index, final GPanel panel) {
        super(parent, index, panel, ControlType.Slider);
        final float xOffset = getSliderX() - X_GAP - SLIDER_WIDTH - X_GAP;
        final float yOffset = Y_BASE + Y_STEP * index;

        slider = new GSlider(panel.getPApplet(), xOffset, yOffset, SLIDER_WIDTH, SLIDER_HEIGHT, SLIDER_TRACK_WIDTH);
        //slider_00_value = new GSlider(this, 520, 40, 100);
        slider.setNumberFormat(GConstants.DECIMAL, 2);
        slider.setLimits(0.0f, -1.0f, 1.0f);
        slider.setShowLimits(false);//setRenderMaxMinLabel(false);
        slider.setShowValue(false);//setRenderValueLabel(false);
        slider.setNbrTicks(0);//setTickCount(0);
        slider.setEasing(1);//.setInertia(1);
        panel.addControl(slider);
    }

    /** {@inheritDoc} */
    @Override
    public void activate(final String labelString) {
        super.activate(labelString);
        if (slider != null) {
            slider.setValue(0.0f);
            slider.setVisible(true);
        } else {
            throw new RuntimeException("ButtonHolder slider has not been initialized properly");
        }
    }

    /** {@inheritDoc} */
    @Override
    public void clear() {
        super.clear();
        if (slider != null) {
            slider.setValue(0.0f);
            slider.setVisible(false);
        }
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the slider property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 10, 2017</DD>
     * </DL>
     *
     * @return the value of slider field
     */
    public GSlider getSlider() {
        return slider;
    }

    //    /**
    //     * <DL>
    //     * <DT>Description:</DT>
    //     * <DD>return the current value for the slider</DD>
    //     * <DT>Date:</DT>
    //     * <DD>Sep 16, 2017</DD>
    //     * </DL>
    //     *
    //     * @return slider's float value
    //     */
    //    public float getValue() {
    //        return slider != null ? slider.getValueF() : 0.0f;
    //    }

}
