/**
 * @author ubuntu - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 13, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapoport.utilities.processing_code.devices;

/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>TODO add description Dimensions</DD>
 * <DT>Date:</DT>
 * <DD>Sep 13, 2017</DD>
 * </DL>
 *
 * @author ubuntu - Nick Rapoport
 *
 */
public interface IGUIDimensions {

    public static float X_BASE = 0;

    public static float Y_BASE = 10;

    public static float X_GAP = 10;

    public static float Y_GAP = 10;

    public static float Y_STEP = 30;

    public static float ACTION_ROW = 25;

    public static int ROWS_VISIBLE = 4;

    public static float ACTION_HEIGHT = ACTION_ROW * ROWS_VISIBLE + 1.0f;

    public static float ACTION_WIDTH = 120f;

    public static float LABEL_WIDTH = 60f;

    public static float LABEL_HEIGHT = 20f;

    public static float SLIDER_WIDTH = 100f;

    public static float SLIDER_TRACK_WIDTH = 10f;

    public static float SLIDER_HEIGHT = 20f;

    public static float BUTTONS_ACTION_OFFSET = LABEL_WIDTH + X_GAP;

    public static float BUTTONS_X_STEP = LABEL_WIDTH + X_GAP + ACTION_WIDTH + X_GAP;

    public static float SLIDERS_X_STEP = SLIDER_WIDTH + X_GAP + ACTION_WIDTH + X_GAP;

    public static float SLIDERS_ACTION_X_STEP = ACTION_WIDTH + X_GAP;

    public static float BUTTON_HEIGHT = 20;

    public static float BUTTON_WIDTH = 80;

    public static float FILE_NAME_WIDTH = 300;

    public static float SAVE_BUTTON_X_OFFSET = BUTTON_WIDTH + 2 * X_GAP;

    public static float FILE_NAME_X_OFFSET = FILE_NAME_WIDTH + X_GAP + SAVE_BUTTON_X_OFFSET;

    // this represents the offset that when subtracted from the window height will provide total useful main panel height
    public static float TOTAL_ADMIN_HEIGHT = Y_GAP + ACTION_ROW + 1 + Y_GAP + Y_GAP + BUTTON_HEIGHT + Y_GAP;
}
