/**
 * @author ubuntu - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 13, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapoport.utilities.psgcode.devices;

/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>Constants used to position Device GUI elements</DD>
 * <DT>Date:</DT>
 * <DD>Sep 13, 2017</DD>
 * </DL>
 *
 * @author ubuntu - Nick Rapoport
 *
 */
public interface IGUIDimensions {

    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>X_BASE (float) =</DD>
     * </DL>
     */
    public static float X_BASE = 0;

    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>Y_BASE (float) =</DD>
     * </DL>
     */
    public static float Y_BASE = 10;

    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>X_GAP (float) =</DD>
     * </DL>
     */
    public static float X_GAP = 10;

    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>Y_GAP (float) =</DD>
     * </DL>
     */
    public static float Y_GAP = 10;

    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>Y_STEP (float) =</DD>
     * </DL>
     */
    public static float CONTROL_HEIGHT = 20f;

    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>Y_STEP (float) =</DD>
     * </DL>
     */
    public static float Y_STEP = 30;

    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>ACTION_ROW (float) =</DD>
     * </DL>
     */
    public static float ACTION_ROW = 25;

    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>ROWS_VISIBLE (int) =</DD>
     * </DL>
     */
    public static int ROWS_VISIBLE = 4;

    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>ACTION_HEIGHT (float) =</DD>
     * </DL>
     */
    public static float ACTION_HEIGHT = ACTION_ROW * ROWS_VISIBLE + 1.0f;

    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>ACTION_WIDTH (float) =</DD>
     * </DL>
     */
    public static float ACTION_WIDTH = 120f;

    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>LABEL_WIDTH (float) =</DD>
     * </DL>
     */
    public static float LABEL_WIDTH = 60f;

    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>LABEL_HEIGHT (float) =</DD>
     * </DL>
     */
    public static float LABEL_HEIGHT = CONTROL_HEIGHT;

    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>SLIDER_WIDTH (float) =</DD>
     * </DL>
     */
    public static float SLIDER_WIDTH = 100f;

    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>SLIDER_TRACK_WIDTH (float) =</DD>
     * </DL>
     */
    public static float SLIDER_TRACK_WIDTH = 10f;

    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>SLIDER_HEIGHT (float) =</DD>
     * </DL>
     */
    public static float SLIDER_HEIGHT = CONTROL_HEIGHT;

    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>CHECKBOX_WIDTH = 120</DD>
     * </DL>
     */
    public static float CHECKBOX_WIDTH = 120;

    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>BUTTONS_ACTION_OFFSET (float) =</DD>
     * </DL>
     */
    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>BUTTONS_ACTION_OFFSET (float) =</DD>
     * </DL>
     */
    public static float BUTTONS_ACTION_OFFSET = LABEL_WIDTH + X_GAP;

    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>BUTTONS_X_STEP (float) =</DD>
     * </DL>
     */
    public static float BUTTONS_X_STEP = LABEL_WIDTH + X_GAP + ACTION_WIDTH + X_GAP;

    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>SLIDERS_X_STEP (float) =</DD>
     * </DL>
     */
    public static float SLIDERS_X_STEP = SLIDER_WIDTH + X_GAP + ACTION_WIDTH + X_GAP;

    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>SLIDERS_ACTION_X_STEP (float) =</DD>
     * </DL>
     */
    public static float SLIDERS_ACTION_X_STEP = ACTION_WIDTH + X_GAP;

    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>BUTTON_HEIGHT (float) =</DD>
     * </DL>
     */
    public static float BUTTON_HEIGHT = CONTROL_HEIGHT;

    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>BUTTON_WIDTH (float) =</DD>
     * </DL>
     */
    public static float BUTTON_WIDTH = 80;

    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>FILE_NAME_WIDTH (float) =</DD>
     * </DL>
     */
    public static float FILE_NAME_WIDTH = 300;

    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>SAVE_BUTTON_X_OFFSET (float) =</DD>
     * </DL>
     */
    public static float SAVE_BUTTON_X_OFFSET = BUTTON_WIDTH + 2 * X_GAP;

    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>FILE_NAME_X_OFFSET (float) =</DD>
     * </DL>
     */
    public static float FILE_NAME_X_OFFSET = FILE_NAME_WIDTH + X_GAP + SAVE_BUTTON_X_OFFSET;

    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>FILE_NAME_X_OFFSET (float) =</DD>
     * </DL>
     */
    public static float CHECKBOX_X_OFFSET = CHECKBOX_WIDTH + X_GAP + FILE_NAME_X_OFFSET;

    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>TOTAL_ADMIN_HEIGHT (float) = this represents the offset that when subtracted from the window height will
     * provide total useful main panel height</DD>
     * </DL>
     */
    public static float TOTAL_ADMIN_HEIGHT = Y_GAP + ACTION_ROW + 1 + Y_GAP + Y_GAP + BUTTON_HEIGHT + Y_GAP;
}
