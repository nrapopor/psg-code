/**
 * @author ubuntu - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 10, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapoport.utilities.psgcode.devices;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.gamecontrolplus.ControlButton;
import org.gamecontrolplus.ControlInput;
import org.gamecontrolplus.ControlSlider;
import org.gamecontrolplus.PCPconstants;

import com.nrapoport.utilities.psgcode.enums.ControlType;
import com.nrapoport.utilities.psgcode.enums.DeviceButtonActions;
import com.nrapoport.utilities.psgcode.enums.DeviceSliderActions;

import g4p_controls.GPanel;

/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>This class is used to hold a device element such as Button or Slider (TODO No Hat support yet)</DD>
 * <DT>Date:</DT>
 * <DD>Sep 10, 2017</DD>
 * </DL>
 *
 * @author ubuntu - Nick Rapoport
 *
 */
public class DeviceHelper implements IDeviceHelper {

    @SuppressWarnings("unused")
    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(DeviceHelper.class);

    private static final List<ButtonHolder> buttons = new ArrayList<>(30); //current max supported buttons

    private static final List<SliderHolder> sliders = new ArrayList<>(10); //current max supported sliders

    private static final List<IGControlHolder> controls = new ArrayList<>(buttons.size() + sliders.size());

    //public static final String SEPARATOR = "\t";

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>clear and hide all sliders labels and drop down lists</DD>
     * <DT>Date:</DT>
     * <DD>Sep 10, 2017</DD>
     * </DL>
     */
    public static void clear() {
        final List<IGControlHolder> lcontrols = getControls();
        for (final IGControlHolder control : lcontrols) {
            if (control != null) {
                control.clear();
            }
        }
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the buttons property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 10, 2017</DD>
     * </DL>
     *
     * @return the value of buttons field
     */
    public static List<ButtonHolder> getButtons() {
        return buttons;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the controls property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 10, 2017</DD>
     * </DL>
     *
     * @return the value of controls field
     */
    public static List<IGControlHolder> getControls() {
        return controls;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the sliders property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 10, 2017</DD>
     * </DL>
     *
     * @return the value of sliders field
     */
    public static List<SliderHolder> getSliders() {
        return sliders;
    }

    private final IDimensionsAware parent;

    private final GPanel panel;

    private final ControlType controlType;

    private final ControlInput control;

    private final int index;

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>DeviceHelper Constructor</DD>
     * <DT>Date:</DT>
     * <DD>Sep 10, 2017</DD>
     * </DL>
     *
     * @param aParent
     * @param aPanel
     * @param aControl
     * @param aIndex
     */
    public DeviceHelper(final IDimensionsAware aParent, final GPanel aPanel, final ControlInput aControl,
            final int aIndex) {
        super();
        panel = aPanel;
        control = aControl;
        index = aIndex;
        parent = aParent;
        if (aControl instanceof ControlButton) {
            controlType = ControlType.Button;
            ((ControlButton) control).plug(this, "update", PCPconstants.ON_PRESS);
        } else if (aControl instanceof ControlSlider) {
            controlType = ControlType.Slider;
        } else {
            controlType = ControlType.Hat; //TODO implement Hat
        }
        init();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>The string representing the name of the selected element for the action drop down. See the
     * <code>DeviceButtonActions</code> and <code>DeviceSliderActions</code> for possible values</DD>
     * <DT>Date:</DT>
     * <DD>Sep 12, 2017</DD>
     * </DL>
     *
     * @return
     */
    protected String actionName() {
        String result = "";
        switch (controlType) {
            case Button:
                result = DeviceButtonActions.getByString(getSelectedText()).name();
                break;
            case Slider:
                result = DeviceSliderActions.getByString(getSelectedText()).name();
                break;

            default: //TODO implement Hat
                throw new NotImplementedException("This is not implemented yet for " + controlType.name());
        }
        return result;
    }

    /** {@inheritDoc} */
    @Override
    public ControlType getControlType() {
        return controlType;
    }

    /** {@inheritDoc} */
    @Override
    public String getName() {
        return control == null ? "" : control.getName();
    }

    /** {@inheritDoc} */
    @Override
    public float getNextXpos() {
        IGControlHolder holder = null;
        int idx = index;
        if (idx < 0) {
            idx = 0;
        }
        switch (controlType) {
            case Button:
                if (idx < getButtons().size()) {
                    holder = getButtons().get(idx);
                } else {
                    holder = getButtons().get(getButtons().size() - 1);
                }
                break;
            case Slider:
                if (idx < getSliders().size()) {
                    holder = getSliders().get(idx);
                } else {
                    holder = getSliders().get(getSliders().size() - 1);
                }
                break;

            default: //TODO implement Hat
                throw new NotImplementedException("This is not implemented yet for " + controlType.name());
        }
        return holder.getNextXpos();

    }

    /** {@inheritDoc} */
    @Override
    public float getNextYpos() {
        IGControlHolder holder = null;
        int idx = index;
        if (idx < 0) {
            idx = 0;
        }
        switch (controlType) {
            case Button:
                if (idx < getButtons().size()) {
                    holder = getButtons().get(idx);
                } else {
                    holder = getButtons().get(getButtons().size() - 1);
                }
                break;
            case Slider:
                if (idx < getSliders().size()) {
                    holder = getSliders().get(idx);
                } else {
                    holder = getSliders().get(getSliders().size() - 1);
                }
                break;

            default: //TODO implement Hat
                throw new NotImplementedException("This is not implemented yet for " + controlType.name());
        }
        return holder.getNextYpos();

    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the panel property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 10, 2017</DD>
     * </DL>
     *
     * @return the value of panel field
     */
    public GPanel getPanel() {
        return panel;
    }

    /** {@inheritDoc} */
    @Override
    public String getSelectedText() {
        switch (controlType) {
            case Button:
                if (index > -1 && index < getButtons().size()) {
                    return getButtons().get(index).getSelectedText();
                }
                break;
            case Slider:
                if (index > -1 && index < getSliders().size()) {
                    return getSliders().get(index).getSelectedText();
                }
                break;

            default: //TODO implement Hat
                throw new NotImplementedException("This is not implemented yet for " + controlType.name());
        }
        return "";
    }

    protected void init() {
        IGControlHolder holder = null;
        int idx = index;
        if (idx < 0) {
            idx = 0;
        }
        switch (controlType) {
            case Button:
                if (idx < getButtons().size()) {
                    holder = getButtons().get(idx);
                }
                break;
            case Slider:
                if (idx < getSliders().size()) {
                    holder = getSliders().get(idx);
                }
                break;

            default: //TODO implement Hat
                throw new NotImplementedException("This is not implemented yet for " + controlType.name());
        }
        if (holder == null) {
            initNew(idx);
        } else {
            holder.activate(getName());
        }
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>initialize new GUI control (since non were found for this index)</DD>
     * <DT>Date:</DT>
     * <DD>Sep 13, 2017</DD>
     * </DL>
     *
     * @param idx
     */
    protected void initNew(final int idx) {
        IGControlHolder holder;
        switch (controlType) {
            case Button:
                holder = new ButtonHolder(parent, idx, getPanel(), getName());
                getButtons().add(idx, (ButtonHolder) holder);
                break;

            case Slider:
                holder = new SliderHolder(parent, idx, getPanel());
                getSliders().add(idx, (SliderHolder) holder);
                break;

            default: //TODO implement Hat
                throw new NotImplementedException("This is not implemented yet for " + controlType.name());
        }
        getControls().add(holder);

    }

    /** {@inheritDoc} */
    @Override
    public void setSelectedText(final int aDrIndex) {
        if (control != null) {
            switch (controlType) {
                case Button:
                    if (index > -1 && index < getButtons().size()) {
                        getButtons().get(index).setSelectedText(aDrIndex);
                    }
                    break;

                case Slider:
                    if (index > -1 && index < getSliders().size()) {
                        getSliders().get(index).setSelectedText(aDrIndex);
                    }
                    break;

                default: //TODO implement Hat
                    throw new NotImplementedException("This is not implemented yet for " + controlType.name());
            }
        }
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Construct a tab separated configuration file line that will represent this mapping in the config file for
     * example: <blockquote><code> SHADOW Drop shadow 1 BUTTON 0 0 0.0 0.0</code> </blockquote>
     * <DL>
     * <DT>SHADOW</DT>
     * <DD>is the key that the application will use to retrieve the mapping</DD>
     * <DT>Drop Shadow</DT>
     * <DD>is the description of the key</DD>
     * <DT>1</DT>
     * <DD>code describing the control type (in this case a button) 1,2 or 3</DD>
     * <DT>BUTTON</DT>
     * <DD>The name of the control type BUTTON, HAT or SLIDER</DD>
     * <DT>0</DT>
     * <DD>Reserved</DD>
     * <DT>0.0</DT>
     * <DD>Multiplier</DD>
     * <DT>0.0</DT>
     * <DD>Tolerance</DD>
     * </DL>
     * </DD>
     * <DT>Date:</DT>
     * <DD>Sep 13, 2017</DD>
     * </DL>
     */
    @Override
    public String toString() {
        final ConfigLine cline = new ConfigLine(actionName(), getSelectedText(), controlType, control.getName(), 0,
            control.getMultiplier(), control.getTolerance(), index);
        return cline.toString();
        //        String result = "";
        //        final String key = actionName();
        //        if (!key.isEmpty() && !key.equals(DeviceButtonActions.NoAction.name())) { //using the DeviceButtonActions but DeviceSliderActions has the same value
        //            final String desc = getSelectedText();
        //            final int typeID = controlType.getId();
        //            final String type = controlType.name().toUpperCase();
        //            final String inputName = control.getName();
        //            final int inputConNo = 0;
        //            final float multiplier = control.getMultiplier();
        //            final float tolerance = control.getTolerance();
        //
        //            result = key + SEPARATOR + desc + SEPARATOR + typeID + SEPARATOR + type + SEPARATOR + inputName + SEPARATOR
        //                + inputConNo + SEPARATOR + multiplier + SEPARATOR + tolerance + SEPARATOR + index
        //                + System.lineSeparator();
        //        }
        //        return result;
    }

    @Override
    public void update() {
        if (control != null) {
            switch (controlType) {
                case Button:
                    if (index > -1 && index < getButtons().size()) {
                        getButtons().get(index).getLabel().setOpaque(((ControlButton) control).pressed());
                    }
                    break;

                case Slider:
                    if (index > -1 && index < getSliders().size()) {
                        getSliders().get(index).getSlider().setValue(control.getValue());
                    }
                    break;

                default: //TODO implement Hat
                    throw new NotImplementedException("This is not implemented yet for " + controlType.name());
            }
        }
    }

}
