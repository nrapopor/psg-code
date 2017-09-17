/**
 * @author ubuntu - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 10, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapoport.utilities.psgcode.devices;

import com.nrapoport.utilities.psgcode.enums.ControlType;

import g4p_controls.GDropList;
import g4p_controls.GLabel;
import g4p_controls.GPanel;

/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>A wrapper class for managing the Button GUI</DD>
 * <DT>Date:</DT>
 * <DD>Sep 10, 2017</DD>
 * </DL>
 *
 * @author ubuntu - Nick Rapoport
 *
 */
public class ButtonHolder extends AbstractDropListHolder {
    @SuppressWarnings("unused")
    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ButtonHolder.class);

    // private static final float baseY = 40.0f;

    //private static final float droplistXpos = 80.0f;

    //private static final float dropListXpos2 = 260.0f;

    private final GLabel label;

    //private final float labelWidth = 60.0f;

    // private final float labelHeight = 20.0f;

    // private final float btnLabelX = 10.0f;

    //   private final float btnLabelX2 = 190.0f;

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
    public ButtonHolder(final GDropList aAction, final GLabel aLabel) {
        super(aAction, ControlType.Button);
        label = aLabel;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>ButtonHolder Constructor</DD>
     * <DT>Date:</DT>
     * <DD>Sep 13, 2017</DD>
     * </DL>
     *
     * @param parent
     * @param index
     * @param panel
     * @param labelString
     */
    public ButtonHolder(final IDimensionsAware parent, final int index, final GPanel panel, final String labelString) {
        super(parent, index, panel, ControlType.Button);
        final int breakRow = determineBreakRow(parent);
        final int breakCount = index / breakRow;

        final float xOffset = X_BASE + breakCount * BUTTONS_X_STEP;
        final float yOffset = Y_BASE + Y_STEP * (index - breakCount * breakRow);
        //index < BREAK_ROW ? baseY + getPosStep() * index : baseY + getPosStep() * (index - BREAK_ROW);
        label = new GLabel(panel.getPApplet(), xOffset, yOffset, LABEL_WIDTH, LABEL_HEIGHT, labelString);
        panel.addControl(label);
    }

    /** {@inheritDoc} */
    @Override
    public void activate(final String labelString) {
        super.activate(labelString);
        if (label != null) {
            label.setText(labelString);
            label.setVisible(true);
        } else {
            throw new RuntimeException("ButtonHolder label has not been initialized properly");
        }
    }

    /** {@inheritDoc} */
    @Override
    public void clear() {
        super.clear();
        if (label != null) {
            label.setText("");
            label.setVisible(false);
        }
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the label property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 10, 2017</DD>
     * </DL>
     *
     * @return the value of label field
     */
    public GLabel getLabel() {
        return label;
    }

    //    /** {@inheritDoc} */
    //    @Override
    //    public float getValue() {
    //        return 0f;
    //    }

}
