/**
 * @author ubuntu - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 10, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapoport.utilities.processing_code.devices;

import java.util.List;

import org.apache.commons.lang.NotImplementedException;

import com.nrapoport.utilities.processing_code.enums.ControlType;
import com.nrapoport.utilities.processing_code.enums.DeviceButtonActions;
import com.nrapoport.utilities.processing_code.enums.DeviceSliderActions;

import g4p_controls.GDropList;
import g4p_controls.GPanel;

/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>This is the ancestor of the Type holders for Button, Slider and Hat. it manages the action drop list that allows
 * for selection of valid actions for a particular control</DD>
 * <DT>Date:</DT>
 * <DD>Sep 10, 2017</DD>
 * </DL>
 *
 * @author ubuntu - Nick Rapoport
 *
 */
public abstract class AbstractDropListHolder implements IGControlHolder, IGUIDimensions {
    @SuppressWarnings("unused")
    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AbstractDropListHolder.class);

    private static final List<String> BUTTON_ACTIONS = DeviceButtonActions.getDropdownStrings();

    private static final List<String> SLIDER_ACTIONS = DeviceSliderActions.getDropdownStrings();

    private static float sliderX;

    //    protected static final int BREAK_ROW = 6;

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the sliderX property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 13, 2017</DD>
     * </DL>
     *
     * @return the value of sliderX field
     */
    public static float getSliderX() {
        return sliderX;
    }

    private final GDropList action;

    //    private final float posStep = 30.0f;
    //
    //    private final float droplistWidth = 100.0f;
    //
    //    private final int dropListShowRows = 4;
    //
    //    private final int dropListRowHeight = 25;

    private final ControlType controlType;

    private float nextXpos;

    private float nextYpos;

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>AbstractDropListHolder Constructor</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param aAction
     * @param aControlType
     */
    public AbstractDropListHolder(final GDropList aAction, final ControlType aControlType) {
        super();
        action = aAction;
        controlType = aControlType;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>AbstractDropListHolder Constructor</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param parent
     * @param index
     * @param panel
     * @param aControlType
     */
    public AbstractDropListHolder(final IDimensionsAware parent, final int index, final GPanel panel,
            final ControlType aControlType) {
        super();
        controlType = aControlType;

        float xPos = X_BASE + BUTTONS_ACTION_OFFSET;
        float yPos = Y_BASE + Y_STEP * index;
        switch (controlType) {
            case Button:
                final int breakRow = determineBreakRow(parent);
                final int breakCount = index / breakRow;

                xPos += breakCount * BUTTONS_X_STEP;
                yPos = Y_BASE + Y_STEP * (index - breakCount * breakRow);
                break;
            case Slider:
                //final int breakRow = (int) ((parent.getHeight() - Y_BASE) / Y_STEP);
                //final int breakCount = index / breakRow;
                sliderX = index == 0 ? parent.getPrevMaxX() + X_GAP + SLIDER_WIDTH + X_GAP : sliderX;
                xPos = sliderX;
                break;
            default:
                break;
        }

        action = new GDropList(panel.getPApplet(), xPos, yPos, ACTION_WIDTH, ACTION_HEIGHT, ROWS_VISIBLE);

        switch (controlType) {
            case Button:
                action.setItems(BUTTON_ACTIONS, 0);
                break;

            case Slider:
                action.setItems(SLIDER_ACTIONS, 0);
                break;

            default: //TODO implement Hat
                throw new NotImplementedException("This is not implemented yet for " + controlType.name());
        }
        action.setSelected(0);
        panel.addControl(action);
        nextXpos = action.getX() + action.getWidth() + X_GAP;
        nextYpos = action.getY() + action.getHeight() + Y_GAP;
    }

    private void activate() {
        if (action != null) {
            action.setSelected(0);
            action.setVisible(true);
        } else {
            throw new RuntimeException("ButtonHolder action has not been initialized properly");
        }
    }

    /** {@inheritDoc} */
    @Override
    public void activate(final String aLabelString) {
        activate();
    }

    /** {@inheritDoc} */
    @Override
    public void clear() {
        if (action != null) {
            action.setSelected(0);
            action.setVisible(false);
        }
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>determine where to wrap the button list to another column</DD>
     * <DT>Date:</DT>
     * <DD>Sep 13, 2017</DD>
     * </DL>
     *
     * @param parent
     * @return the row of Buttons which will be used a the breaking (wrapping) row
     */
    protected int determineBreakRow(final IDimensionsAware parent) {
        int breakRow = (int) ((parent.getCurrHeight() - Y_BASE - BUTTON_HEIGHT - Y_GAP) / Y_STEP);
        breakRow = breakRow < 15 ? 15 : breakRow;
        return breakRow;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the action property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 10, 2017</DD>
     * </DL>
     *
     * @return the value of action field
     */
    public GDropList getAction() {
        return action;
    }

    /** {@inheritDoc} */
    @Override
    public float getNextXpos() {
        return nextXpos;
    }

    /** {@inheritDoc} */
    @Override
    public float getNextYpos() {
        return nextYpos;
    }

    /** {@inheritDoc} */
    @Override
    public String getSelectedText() {
        return action != null ? action.getSelectedText() : "";
    }

    /** {@inheritDoc} */
    @Override
    public void setSelectedText(final int ordinal) {
        if (action != null) {
            int verified = ordinal < 0 ? 0 : ordinal;
            switch (controlType) {
                case Button:
                    verified = verified >= DeviceButtonActions.values().length ? DeviceButtonActions.values().length - 1
                        : verified;
                    //DeviceButtonActions dba = DeviceButtonActions.getByOrdinal(verified);
                    action.setSelected(verified);
                    action.draw();
                    break;

                case Slider:
                    verified = verified >= DeviceSliderActions.values().length ? DeviceSliderActions.values().length - 1
                        : verified;
                    //DeviceButtonActions dba = DeviceButtonActions.getByOrdinal(verified);
                    action.setSelected(verified);
                    action.draw();
                    break;

                default: //TODO implement Hat
                    throw new NotImplementedException("This is not implemented yet for " + controlType.name());
            }

        }
    }

    /** {@inheritDoc} */
    @Override
    public void setSelectedText(final String value) {
        if (action != null) {
            switch (controlType) {
                case Button:
                    final DeviceButtonActions dba = DeviceButtonActions.getByString(value);
                    action.setSelected(dba.ordinal());
                    break;

                case Slider:
                    final DeviceSliderActions dsa = DeviceSliderActions.getByString(value);
                    action.setSelected(dsa.ordinal());
                    break;

                default: //TODO implement Hat
                    throw new NotImplementedException("This is not implemented yet for " + controlType.name());
            }
        }
    }

}
