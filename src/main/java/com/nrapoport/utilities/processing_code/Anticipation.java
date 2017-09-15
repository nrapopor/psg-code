/**
 * @author ubuntu - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 3, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapoport.utilities.processing_code;

import com.nrapoport.utilities.processing_code.config.RuntimeSettings;
import com.nrapoport.utilities.processing_code.config.Settings;

import processing.core.PApplet;

/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>TODO add description</DD>
 * <DT>Date:</DT>
 * <DD>Sep 3, 2017</DD>
 * </DL>
 *
 * @author ubuntu - Nick Rapoport
 *
 */
public class Anticipation extends AbstractPDE {
    @SuppressWarnings("unused")
    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Anticipation.class);

    private final Settings settings;

    private final RuntimeSettings runtimeSettings;

    int nbDot = 10; // number of dot for anticipation minimum 2

    int antSens = 10; // sensitivity of anticipation

    float propX = 0.67f; // proportionality of anticipation

    float propY = 0.11f; // 1 is Hight / more is Less

    int[] oldPossibleX;// = new int[nbDot + 1]; // 0 is actual position

    //boolean leadTarget = true;

    int[] oldPossibleY;// = new int[nbDot + 1];

    int[] accX; //= new int[nbDot - 1];

    int[] accY; //= new int[nbDot - 1];

    int[] travelX; //= new int[nbDot - 1];

    int[] travelY;// = new int[nbDot - 1];

    float antX = 0;

    float antY = 0;

    int prevTargetX;// = targetX;

    int prevTargetY;// = targetY;

    int camWidth;

    int camHeight;

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Anticipation Constructor</DD>
     * <DT>Date:</DT>
     * <DD>Sep 3, 2017</DD>
     * </DL>
     *
     * @param aParent
     */
    public Anticipation(final PSGProcessingCode aParent) {
        super(aParent);
        settings = aParent.getSettings();
        runtimeSettings = aParent.getRuntimeSettings();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>anticipate the target's movement and "lead" the target ... Shoot where they will be, rather then where they
     * are right now
     * <p>
     * <blockquote> <em><strong>Door Gunner:</strong> Everyone who runs is a VC... Everyone who does not run is a well
     * disciplined VC<br/>
     * <br/>
     * . . .<br/>
     * <br/>
     * <strong>Joker:</strong>(horrified) But, how can you shoot women... and children...? <br/>
     * <br/>
     * <strong>Door Gunner:</strong> Easy..., you just Don't lead them quite so much! <br/>
     * <br/>
     * </em>-- "Full Metal Jacket", helicopter scene </blockquote>
     *
     * </DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     */
    void anticipation() {
        //leadTarget = getSettings().isLeadTarget();

        if (!getSettings().isLeadTarget()) { // turned off
            return;
        }
        reinitLocalVariables();

        //            if (oldPossibleX.length != nbDot + 1) {
        //
        //                oldPossibleX = PApplet.expand(oldPossibleX, nbDot + 1);
        //            }
        //            if (oldPossibleY.length != nbDot + 1) {
        //                oldPossibleY = expand(oldPossibleY, nbDot + 1);
        //            }
        //            if (accX.length != nbDot - 1) {
        //                accX = expand(accX, nbDot - 1);
        //            }
        //            if (accY.length != nbDot - 1) {
        //                accY = expand(accY, nbDot - 1);
        //            }
        //            if (travelX.length != nbDot - 1) {
        //                travelX = expand(travelX, nbDot - 1);
        //            }
        //            if (travelY.length != nbDot - 1) {
        //                travelY = expand(travelY, nbDot - 1);
        //            }
        //
        //            oldPossibleX[0] = possibleX;
        //            oldPossibleY[0] = possibleY;

        // Acceleration between oldPossibleX and old possibleX-1
        for (int i = 0; i <= nbDot - 2; i++) {
            if (PApplet.abs(oldPossibleX[i] - oldPossibleX[i + 1]) < camWidth / antSens
                && PApplet.abs(oldPossibleX[i + 1] - oldPossibleX[i + 2]) < camWidth / antSens) {
                accX[i] = oldPossibleX[i] - oldPossibleX[i + 1] - (oldPossibleX[i + 1] - oldPossibleX[i + 2]);
            }
            if (PApplet.abs(oldPossibleY[i] - oldPossibleY[i + 1]) < camHeight / antSens
                && PApplet.abs(oldPossibleY[i + 1] - oldPossibleY[i + 2]) < camHeight / antSens) {
                accY[i] = oldPossibleY[i] - oldPossibleY[i + 1] - (oldPossibleY[i + 1] - oldPossibleY[i + 2]);
            }
        }

        // Travel between oldPossibleX and old possibleX-1
        for (int i = 0; i <= nbDot - 2; i++) {
            if (PApplet.abs(oldPossibleX[i] - oldPossibleX[i + 1]) < camWidth / antSens) {
                travelX[i] = oldPossibleX[i] - oldPossibleX[i + 1];
            } else {
                travelX[i] = 0;
            }
            if (PApplet.abs(oldPossibleY[i] - oldPossibleY[i + 1]) < camHeight / antSens) {
                travelY[i] = oldPossibleY[i] - oldPossibleY[i + 1];
            } else {
                travelY[i] = 0;
            }
        }

        // addition of speed and acceleration
        // Each term can be weighted to have an improved algorithm

        // antX = 0; moved to reinitLocalVariables();
        // antY = 0; moved to reinitLocalVariables();

        for (int i = 0; i <= nbDot - 2; i++) {
            antX = antX + travelX[i] + accX[i];
            antY = antY + travelY[i] + accY[i];
        }

        antX = antX * propX;
        antY = antY * propY;

        // Updating  positions

        for (int i = nbDot; i >= 1; i--) {
            oldPossibleX[i] = oldPossibleX[i - 1];
            oldPossibleY[i] = oldPossibleY[i - 1];
        }

        getRuntimeSettings().setPossibleX(Math.round(getRuntimeSettings().getPossibleX() + antX));
        getRuntimeSettings().setPossibleY(Math.round(getRuntimeSettings().getPossibleY() + antY));
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the runtimeSettings property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of runtimeSettings field
     */
    public RuntimeSettings getRuntimeSettings() {
        return runtimeSettings;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Getter for the settings property</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @return the value of settings field
     */
    public Settings getSettings() {
        return settings;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>(Re)initialize the int[] to a new size, or if's not initialized yet create it</DD></DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     *
     * @param intArray
     *            the array to (re)initialize
     * @param newSize
     *            the new size of the array
     * @return
     */
    private int[] initOrExpand(final int[] intArray, final int newSize) {
        int[] result = intArray;
        if (intArray == null) {
            result = new int[newSize]; // 0 is actual position
        } else if (intArray.length != newSize) {
            result = PApplet.expand(intArray, newSize);
        }
        return result;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>TODO add initLocalVariables description</DD>
     * <DT>Date:</DT>
     * <DD>Sep 4, 2017</DD>
     * </DL>
     */
    private void reinitLocalVariables() {
        camWidth = getSettings().getCamWidth();
        camHeight = getSettings().getCamHeight();

        nbDot = getSettings().getNbDot();
        antSens = getSettings().getAntSens();
        propX = getSettings().getPropX();
        propY = getSettings().getPropY();

        oldPossibleX = initOrExpand(oldPossibleX, nbDot + 1);
        oldPossibleY = initOrExpand(oldPossibleY, nbDot + 1);
        accX = initOrExpand(accX, nbDot - 1);
        accY = initOrExpand(accY, nbDot - 1);
        travelX = initOrExpand(travelX, nbDot - 1);
        travelY = initOrExpand(travelY, nbDot - 1);

        oldPossibleX[0] = Math.round(getRuntimeSettings().getPossibleX());
        oldPossibleY[0] = Math.round(getRuntimeSettings().getPossibleY());

        antX = 0;
        antY = 0;

        oldPossibleY = new int[nbDot + 1];
        accX = new int[nbDot - 1];
        accY = new int[nbDot - 1];
        //travelX = new int[nbDot - 1];
        //travelY = new int[nbDot - 1];
        prevTargetX = getRuntimeSettings().getTargetX();
        prevTargetY = getRuntimeSettings().getTargetY();
    }

}
