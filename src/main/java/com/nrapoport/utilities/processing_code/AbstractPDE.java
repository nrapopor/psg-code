/**
 * @author nvr - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 1, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapoport.utilities.processing_code;

import java.beans.Transient;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PMatrix;
import processing.core.PMatrix2D;
import processing.core.PMatrix3D;
import processing.core.PShape;
import processing.core.PStyle;
import processing.core.PSurface;
import processing.data.JSONArray;
import processing.data.JSONObject;
import processing.data.Table;
import processing.data.XML;
import processing.event.Event;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
import processing.opengl.PGL;
import processing.opengl.PShader;

/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>The abstract class to serve as an base for Processing PDE classes. delegates all known methods of PApplet</DD>
 * <DT>Date:</DT>
 * <DD>Sep 1, 2017</DD>
 * </DL>
 *
 * @author nvr - Nick Rapoport
 *
 */
public abstract class AbstractPDE implements IPAppletAware {
    @SuppressWarnings("unused")
    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AbstractPDE.class);

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>println</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param variables
     * @see processing.core.PApplet#println()
     */
    public static void println() {
        PApplet.println();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>println</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param what
     * @see processing.core.PApplet#println(boolean)
     */
    public static void println(final boolean what) {
        PApplet.println(what);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>println</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param what
     * @see processing.core.PApplet#println(byte)
     */
    public static void println(final byte what) {
        PApplet.println(what);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>println</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param what
     * @see processing.core.PApplet#println(char)
     */
    public static void println(final char what) {
        PApplet.println(what);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>println</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param what
     * @see processing.core.PApplet#println(double)
     */
    public static void println(final double what) {
        PApplet.println(what);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>println</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param what
     * @see processing.core.PApplet#println(float)
     */
    public static void println(final float what) {
        PApplet.println(what);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>println</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param what
     * @see processing.core.PApplet#println(int)
     */
    public static void println(final int what) {
        PApplet.println(what);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>println</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param what
     * @see processing.core.PApplet#println(long)
     */
    public static void println(final long what) {
        PApplet.println(what);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>println</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param variables
     * @see processing.core.PApplet#println(Object...)
     */
    public static void println(final Object... variables) {
        PApplet.println(variables);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>println</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param what
     * @see processing.core.PApplet#println(Object)
     */
    public static void println(final Object what) {
        PApplet.println(what);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>println</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param what
     * @see processing.core.PApplet#println(String)
     */
    public static void println(final String what) {
        PApplet.println(what);
    }

    private transient PApplet parent;

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>AbstractPDE Constructor that sets the parent</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aParent
     */
    public AbstractPDE(final PApplet aParent) {
        parent = aParent;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>alpha</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aRgb
     * @return
     * @see processing.core.PApplet#alpha(int)
     */
    public final float alpha(final int aRgb) {
        return parent.alpha(aRgb);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>ambient</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aGray
     * @see processing.core.PApplet#ambient(float)
     */
    public void ambient(final float aGray) {
        parent.ambient(aGray);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>ambient</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aV1
     * @param aV2
     * @param aV3
     * @see processing.core.PApplet#ambient(float, float, float)
     */
    public void ambient(final float aV1, final float aV2, final float aV3) {
        parent.ambient(aV1, aV2, aV3);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>ambient</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aRgb
     * @see processing.core.PApplet#ambient(int)
     */
    public void ambient(final int aRgb) {
        parent.ambient(aRgb);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>ambientLight</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aV1
     * @param aV2
     * @param aV3
     * @see processing.core.PApplet#ambientLight(float, float, float)
     */
    public void ambientLight(final float aV1, final float aV2, final float aV3) {
        parent.ambientLight(aV1, aV2, aV3);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>ambientLight</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aV1
     * @param aV2
     * @param aV3
     * @param aX
     * @param aY
     * @param aZ
     * @see processing.core.PApplet#ambientLight(float, float, float, float, float, float)
     */
    public void ambientLight(final float aV1, final float aV2, final float aV3, final float aX, final float aY,
        final float aZ) {
        parent.ambientLight(aV1, aV2, aV3, aX, aY, aZ);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>applyMatrix</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aN00
     * @param aN01
     * @param aN02
     * @param aN10
     * @param aN11
     * @param aN12
     * @see processing.core.PApplet#applyMatrix(float, float, float, float, float, float)
     */
    public void applyMatrix(final float aN00, final float aN01, final float aN02, final float aN10, final float aN11,
        final float aN12) {
        parent.applyMatrix(aN00, aN01, aN02, aN10, aN11, aN12);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>applyMatrix</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aN00
     * @param aN01
     * @param aN02
     * @param aN03
     * @param aN10
     * @param aN11
     * @param aN12
     * @param aN13
     * @param aN20
     * @param aN21
     * @param aN22
     * @param aN23
     * @param aN30
     * @param aN31
     * @param aN32
     * @param aN33
     * @see processing.core.PApplet#applyMatrix(float, float, float, float, float, float, float, float, float, float,
     *      float, float, float, float, float, float)
     */
    public void applyMatrix(final float aN00, final float aN01, final float aN02, final float aN03, final float aN10,
        final float aN11, final float aN12, final float aN13, final float aN20, final float aN21, final float aN22,
        final float aN23, final float aN30, final float aN31, final float aN32, final float aN33) {
        parent.applyMatrix(aN00, aN01, aN02, aN03, aN10, aN11, aN12, aN13, aN20, aN21, aN22, aN23, aN30, aN31, aN32,
            aN33);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>applyMatrix</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aSource
     * @see processing.core.PApplet#applyMatrix(processing.core.PMatrix)
     */
    public void applyMatrix(final PMatrix aSource) {
        parent.applyMatrix(aSource);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>applyMatrix</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aSource
     * @see processing.core.PApplet#applyMatrix(processing.core.PMatrix2D)
     */
    public void applyMatrix(final PMatrix2D aSource) {
        parent.applyMatrix(aSource);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>applyMatrix</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aSource
     * @see processing.core.PApplet#applyMatrix(processing.core.PMatrix3D)
     */
    public void applyMatrix(final PMatrix3D aSource) {
        parent.applyMatrix(aSource);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>arc</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aA
     * @param aB
     * @param aC
     * @param aD
     * @param aStart
     * @param aStop
     * @see processing.core.PApplet#arc(float, float, float, float, float, float)
     */
    public void arc(final float aA, final float aB, final float aC, final float aD, final float aStart,
        final float aStop) {
        parent.arc(aA, aB, aC, aD, aStart, aStop);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>arc</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aA
     * @param aB
     * @param aC
     * @param aD
     * @param aStart
     * @param aStop
     * @param aMode
     * @see processing.core.PApplet#arc(float, float, float, float, float, float, int)
     */
    public void arc(final float aA, final float aB, final float aC, final float aD, final float aStart,
        final float aStop, final int aMode) {
        parent.arc(aA, aB, aC, aD, aStart, aStop, aMode);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>attrib</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aName
     * @param aValues
     * @see processing.core.PApplet#attrib(java.lang.String, boolean[])
     */
    public void attrib(final String aName, final boolean... aValues) {
        parent.attrib(aName, aValues);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>attrib</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aName
     * @param aValues
     * @see processing.core.PApplet#attrib(java.lang.String, float[])
     */
    public void attrib(final String aName, final float... aValues) {
        parent.attrib(aName, aValues);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>attrib</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aName
     * @param aValues
     * @see processing.core.PApplet#attrib(java.lang.String, int[])
     */
    public void attrib(final String aName, final int... aValues) {
        parent.attrib(aName, aValues);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>attribColor</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aName
     * @param aColor
     * @see processing.core.PApplet#attribColor(java.lang.String, int)
     */
    public void attribColor(final String aName, final int aColor) {
        parent.attribColor(aName, aColor);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>attribNormal</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aName
     * @param aNx
     * @param aNy
     * @param aNz
     * @see processing.core.PApplet#attribNormal(java.lang.String, float, float, float)
     */
    public void attribNormal(final String aName, final float aNx, final float aNy, final float aNz) {
        parent.attribNormal(aName, aNx, aNy, aNz);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>attribPosition</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aName
     * @param aX
     * @param aY
     * @param aZ
     * @see processing.core.PApplet#attribPosition(java.lang.String, float, float, float)
     */
    public void attribPosition(final String aName, final float aX, final float aY, final float aZ) {
        parent.attribPosition(aName, aX, aY, aZ);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>background</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aGray
     * @see processing.core.PApplet#background(float)
     */
    public void background(final float aGray) {
        parent.background(aGray);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>background</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aGray
     * @param aAlpha
     * @see processing.core.PApplet#background(float, float)
     */
    public void background(final float aGray, final float aAlpha) {
        parent.background(aGray, aAlpha);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>background</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aV1
     * @param aV2
     * @param aV3
     * @see processing.core.PApplet#background(float, float, float)
     */
    public void background(final float aV1, final float aV2, final float aV3) {
        parent.background(aV1, aV2, aV3);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>background</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aV1
     * @param aV2
     * @param aV3
     * @param aAlpha
     * @see processing.core.PApplet#background(float, float, float, float)
     */
    public void background(final float aV1, final float aV2, final float aV3, final float aAlpha) {
        parent.background(aV1, aV2, aV3, aAlpha);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>background</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aRgb
     * @see processing.core.PApplet#background(int)
     */
    public void background(final int aRgb) {
        parent.background(aRgb);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>background</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aRgb
     * @param aAlpha
     * @see processing.core.PApplet#background(int, float)
     */
    public void background(final int aRgb, final float aAlpha) {
        parent.background(aRgb, aAlpha);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>background</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aImage
     * @see processing.core.PApplet#background(processing.core.PImage)
     */
    public void background(final PImage aImage) {
        parent.background(aImage);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>beginCamera</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#beginCamera()
     */
    public void beginCamera() {
        parent.beginCamera();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>beginContour</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#beginContour()
     */
    public void beginContour() {
        parent.beginContour();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>beginPGL</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @return
     * @see processing.core.PApplet#beginPGL()
     */
    public PGL beginPGL() {
        return parent.beginPGL();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>beginRaw</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aRawGraphics
     * @see processing.core.PApplet#beginRaw(processing.core.PGraphics)
     */
    public void beginRaw(final PGraphics aRawGraphics) {
        parent.beginRaw(aRawGraphics);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>beginRaw</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aRenderer
     * @param aFilename
     * @return
     * @see processing.core.PApplet#beginRaw(java.lang.String, java.lang.String)
     */
    public PGraphics beginRaw(final String aRenderer, final String aFilename) {
        return parent.beginRaw(aRenderer, aFilename);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>beginRecord</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aRecorder
     * @see processing.core.PApplet#beginRecord(processing.core.PGraphics)
     */
    public void beginRecord(final PGraphics aRecorder) {
        parent.beginRecord(aRecorder);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>beginRecord</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aRenderer
     * @param aFilename
     * @return
     * @see processing.core.PApplet#beginRecord(java.lang.String, java.lang.String)
     */
    public PGraphics beginRecord(final String aRenderer, final String aFilename) {
        return parent.beginRecord(aRenderer, aFilename);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>beginShape</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#beginShape()
     */
    public void beginShape() {
        parent.beginShape();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>beginShape</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aKind
     * @see processing.core.PApplet#beginShape(int)
     */
    public void beginShape(final int aKind) {
        parent.beginShape(aKind);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>bezier</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aX1
     * @param aY1
     * @param aX2
     * @param aY2
     * @param aX3
     * @param aY3
     * @param aX4
     * @param aY4
     * @see processing.core.PApplet#bezier(float, float, float, float, float, float, float, float)
     */
    public void bezier(final float aX1, final float aY1, final float aX2, final float aY2, final float aX3,
        final float aY3, final float aX4, final float aY4) {
        parent.bezier(aX1, aY1, aX2, aY2, aX3, aY3, aX4, aY4);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>bezier</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aX1
     * @param aY1
     * @param aZ1
     * @param aX2
     * @param aY2
     * @param aZ2
     * @param aX3
     * @param aY3
     * @param aZ3
     * @param aX4
     * @param aY4
     * @param aZ4
     * @see processing.core.PApplet#bezier(float, float, float, float, float, float, float, float, float, float, float,
     *      float)
     */
    public void bezier(final float aX1, final float aY1, final float aZ1, final float aX2, final float aY2,
        final float aZ2, final float aX3, final float aY3, final float aZ3, final float aX4, final float aY4,
        final float aZ4) {
        parent.bezier(aX1, aY1, aZ1, aX2, aY2, aZ2, aX3, aY3, aZ3, aX4, aY4, aZ4);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>bezierDetail</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aDetail
     * @see processing.core.PApplet#bezierDetail(int)
     */
    public void bezierDetail(final int aDetail) {
        parent.bezierDetail(aDetail);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>bezierPoint</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aA
     * @param aB
     * @param aC
     * @param aD
     * @param aT
     * @return
     * @see processing.core.PApplet#bezierPoint(float, float, float, float, float)
     */
    public float bezierPoint(final float aA, final float aB, final float aC, final float aD, final float aT) {
        return parent.bezierPoint(aA, aB, aC, aD, aT);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>bezierTangent</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aA
     * @param aB
     * @param aC
     * @param aD
     * @param aT
     * @return
     * @see processing.core.PApplet#bezierTangent(float, float, float, float, float)
     */
    public float bezierTangent(final float aA, final float aB, final float aC, final float aD, final float aT) {
        return parent.bezierTangent(aA, aB, aC, aD, aT);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>bezierVertex</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aX2
     * @param aY2
     * @param aX3
     * @param aY3
     * @param aX4
     * @param aY4
     * @see processing.core.PApplet#bezierVertex(float, float, float, float, float, float)
     */
    public void bezierVertex(final float aX2, final float aY2, final float aX3, final float aY3, final float aX4,
        final float aY4) {
        parent.bezierVertex(aX2, aY2, aX3, aY3, aX4, aY4);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>bezierVertex</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aX2
     * @param aY2
     * @param aZ2
     * @param aX3
     * @param aY3
     * @param aZ3
     * @param aX4
     * @param aY4
     * @param aZ4
     * @see processing.core.PApplet#bezierVertex(float, float, float, float, float, float, float, float, float)
     */
    public void bezierVertex(final float aX2, final float aY2, final float aZ2, final float aX3, final float aY3,
        final float aZ3, final float aX4, final float aY4, final float aZ4) {
        parent.bezierVertex(aX2, aY2, aZ2, aX3, aY3, aZ3, aX4, aY4, aZ4);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>blend</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aSx
     * @param aSy
     * @param aSw
     * @param aSh
     * @param aDx
     * @param aDy
     * @param aDw
     * @param aDh
     * @param aMode
     * @see processing.core.PApplet#blend(int, int, int, int, int, int, int, int, int)
     */
    public void blend(final int aSx, final int aSy, final int aSw, final int aSh, final int aDx, final int aDy,
        final int aDw, final int aDh, final int aMode) {
        parent.blend(aSx, aSy, aSw, aSh, aDx, aDy, aDw, aDh, aMode);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>blend</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aSrc
     * @param aSx
     * @param aSy
     * @param aSw
     * @param aSh
     * @param aDx
     * @param aDy
     * @param aDw
     * @param aDh
     * @param aMode
     * @see processing.core.PApplet#blend(processing.core.PImage, int, int, int, int, int, int, int, int, int)
     */
    public void blend(final PImage aSrc, final int aSx, final int aSy, final int aSw, final int aSh, final int aDx,
        final int aDy, final int aDw, final int aDh, final int aMode) {
        parent.blend(aSrc, aSx, aSy, aSw, aSh, aDx, aDy, aDw, aDh, aMode);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>blendMode</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aMode
     * @see processing.core.PApplet#blendMode(int)
     */
    public void blendMode(final int aMode) {
        parent.blendMode(aMode);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>blue</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aRgb
     * @return
     * @see processing.core.PApplet#blue(int)
     */
    public final float blue(final int aRgb) {
        return parent.blue(aRgb);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>box</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aSize
     * @see processing.core.PApplet#box(float)
     */
    public void box(final float aSize) {
        parent.box(aSize);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>box</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aW
     * @param aH
     * @param aD
     * @see processing.core.PApplet#box(float, float, float)
     */
    public void box(final float aW, final float aH, final float aD) {
        parent.box(aW, aH, aD);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>brightness</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aRgb
     * @return
     * @see processing.core.PApplet#brightness(int)
     */
    public final float brightness(final int aRgb) {
        return parent.brightness(aRgb);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>camera</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#camera()
     */
    public void camera() {
        parent.camera();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>camera</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aEyeX
     * @param aEyeY
     * @param aEyeZ
     * @param aCenterX
     * @param aCenterY
     * @param aCenterZ
     * @param aUpX
     * @param aUpY
     * @param aUpZ
     * @see processing.core.PApplet#camera(float, float, float, float, float, float, float, float, float)
     */
    public void camera(final float aEyeX, final float aEyeY, final float aEyeZ, final float aCenterX,
        final float aCenterY, final float aCenterZ, final float aUpX, final float aUpY, final float aUpZ) {
        parent.camera(aEyeX, aEyeY, aEyeZ, aCenterX, aCenterY, aCenterZ, aUpX, aUpY, aUpZ);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>clear</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#clear()
     */
    public void clear() {
        parent.clear();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>clip</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aA
     * @param aB
     * @param aC
     * @param aD
     * @see processing.core.PApplet#clip(float, float, float, float)
     */
    public void clip(final float aA, final float aB, final float aC, final float aD) {
        parent.clip(aA, aB, aC, aD);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>color</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aFgray
     * @return
     * @see processing.core.PApplet#color(float)
     */
    public final int color(final float aFgray) {
        return parent.color(aFgray);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>color</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aFgray
     * @param aFalpha
     * @return
     * @see processing.core.PApplet#color(float, float)
     */
    public final int color(final float aFgray, final float aFalpha) {
        return parent.color(aFgray, aFalpha);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>color</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aV1
     * @param aV2
     * @param aV3
     * @return
     * @see processing.core.PApplet#color(float, float, float)
     */
    public final int color(final float aV1, final float aV2, final float aV3) {
        return parent.color(aV1, aV2, aV3);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>color</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aV1
     * @param aV2
     * @param aV3
     * @param aAlpha
     * @return
     * @see processing.core.PApplet#color(float, float, float, float)
     */
    public final int color(final float aV1, final float aV2, final float aV3, final float aAlpha) {
        return parent.color(aV1, aV2, aV3, aAlpha);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>color</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aGray
     * @return
     * @see processing.core.PApplet#color(int)
     */
    public final int color(final int aGray) {
        return parent.color(aGray);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>color</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aGray
     * @param aAlpha
     * @return
     * @see processing.core.PApplet#color(int, int)
     */
    public final int color(final int aGray, final int aAlpha) {
        return parent.color(aGray, aAlpha);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>color</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aV1
     * @param aV2
     * @param aV3
     * @return
     * @see processing.core.PApplet#color(int, int, int)
     */
    public final int color(final int aV1, final int aV2, final int aV3) {
        return parent.color(aV1, aV2, aV3);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>color</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aV1
     * @param aV2
     * @param aV3
     * @param aAlpha
     * @return
     * @see processing.core.PApplet#color(int, int, int, int)
     */
    public final int color(final int aV1, final int aV2, final int aV3, final int aAlpha) {
        return parent.color(aV1, aV2, aV3, aAlpha);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>colorMode</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aMode
     * @see processing.core.PApplet#colorMode(int)
     */
    public void colorMode(final int aMode) {
        parent.colorMode(aMode);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>colorMode</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aMode
     * @param aMax
     * @see processing.core.PApplet#colorMode(int, float)
     */
    public void colorMode(final int aMode, final float aMax) {
        parent.colorMode(aMode, aMax);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>colorMode</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aMode
     * @param aMax1
     * @param aMax2
     * @param aMax3
     * @see processing.core.PApplet#colorMode(int, float, float, float)
     */
    public void colorMode(final int aMode, final float aMax1, final float aMax2, final float aMax3) {
        parent.colorMode(aMode, aMax1, aMax2, aMax3);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>colorMode</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aMode
     * @param aMax1
     * @param aMax2
     * @param aMax3
     * @param aMaxA
     * @see processing.core.PApplet#colorMode(int, float, float, float, float)
     */
    public void colorMode(final int aMode, final float aMax1, final float aMax2, final float aMax3, final float aMaxA) {
        parent.colorMode(aMode, aMax1, aMax2, aMax3, aMaxA);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>copy</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @return
     * @see processing.core.PApplet#copy()
     */
    public PImage copy() {
        return parent.copy();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>copy</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aSx
     * @param aSy
     * @param aSw
     * @param aSh
     * @param aDx
     * @param aDy
     * @param aDw
     * @param aDh
     * @see processing.core.PApplet#copy(int, int, int, int, int, int, int, int)
     */
    public void copy(final int aSx, final int aSy, final int aSw, final int aSh, final int aDx, final int aDy,
        final int aDw, final int aDh) {
        parent.copy(aSx, aSy, aSw, aSh, aDx, aDy, aDw, aDh);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>copy</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aSrc
     * @param aSx
     * @param aSy
     * @param aSw
     * @param aSh
     * @param aDx
     * @param aDy
     * @param aDw
     * @param aDh
     * @see processing.core.PApplet#copy(processing.core.PImage, int, int, int, int, int, int, int, int)
     */
    public void copy(final PImage aSrc, final int aSx, final int aSy, final int aSw, final int aSh, final int aDx,
        final int aDy, final int aDw, final int aDh) {
        parent.copy(aSrc, aSx, aSy, aSw, aSh, aDx, aDy, aDw, aDh);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>createFont</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aName
     * @param aSize
     * @return
     * @see processing.core.PApplet#createFont(java.lang.String, float)
     */
    public PFont createFont(final String aName, final float aSize) {
        return parent.createFont(aName, aSize);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>createFont</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aName
     * @param aSize
     * @param aSmooth
     * @return
     * @see processing.core.PApplet#createFont(java.lang.String, float, boolean)
     */
    public PFont createFont(final String aName, final float aSize, final boolean aSmooth) {
        return parent.createFont(aName, aSize, aSmooth);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>createFont</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aName
     * @param aSize
     * @param aSmooth
     * @param aCharset
     * @return
     * @see processing.core.PApplet#createFont(java.lang.String, float, boolean, char[])
     */
    public PFont createFont(final String aName, final float aSize, final boolean aSmooth, final char[] aCharset) {
        return parent.createFont(aName, aSize, aSmooth, aCharset);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>createGraphics</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aW
     * @param aH
     * @return
     * @see processing.core.PApplet#createGraphics(int, int)
     */
    public PGraphics createGraphics(final int aW, final int aH) {
        return parent.createGraphics(aW, aH);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>createGraphics</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aW
     * @param aH
     * @param aRenderer
     * @return
     * @see processing.core.PApplet#createGraphics(int, int, java.lang.String)
     */
    public PGraphics createGraphics(final int aW, final int aH, final String aRenderer) {
        return parent.createGraphics(aW, aH, aRenderer);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>createGraphics</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aW
     * @param aH
     * @param aRenderer
     * @param aPath
     * @return
     * @see processing.core.PApplet#createGraphics(int, int, java.lang.String, java.lang.String)
     */
    public PGraphics createGraphics(final int aW, final int aH, final String aRenderer, final String aPath) {
        return parent.createGraphics(aW, aH, aRenderer, aPath);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>createImage</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aW
     * @param aH
     * @param aFormat
     * @return
     * @see processing.core.PApplet#createImage(int, int, int)
     */
    public PImage createImage(final int aW, final int aH, final int aFormat) {
        return parent.createImage(aW, aH, aFormat);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>createInput</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aFilename
     * @return
     * @see processing.core.PApplet#createInput(java.lang.String)
     */
    public InputStream createInput(final String aFilename) {
        return parent.createInput(aFilename);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>createInputRaw</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aFilename
     * @return
     * @see processing.core.PApplet#createInputRaw(java.lang.String)
     */
    public InputStream createInputRaw(final String aFilename) {
        return parent.createInputRaw(aFilename);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>createOutput</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aFilename
     * @return
     * @see processing.core.PApplet#createOutput(java.lang.String)
     */
    public OutputStream createOutput(final String aFilename) {
        return parent.createOutput(aFilename);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>createReader</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aFilename
     * @return
     * @see processing.core.PApplet#createReader(java.lang.String)
     */
    public BufferedReader createReader(final String aFilename) {
        return parent.createReader(aFilename);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>createShape</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @return
     * @see processing.core.PApplet#createShape()
     */
    public PShape createShape() {
        return parent.createShape();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>createShape</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aType
     * @return
     * @see processing.core.PApplet#createShape(int)
     */
    public PShape createShape(final int aType) {
        return parent.createShape(aType);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>createShape</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aKind
     * @param aP
     * @return
     * @see processing.core.PApplet#createShape(int, float[])
     */
    public PShape createShape(final int aKind, final float... aP) {
        return parent.createShape(aKind, aP);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>createWriter</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aFilename
     * @return
     * @see processing.core.PApplet#createWriter(java.lang.String)
     */
    public PrintWriter createWriter(final String aFilename) {
        return parent.createWriter(aFilename);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>cursor</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#cursor()
     */
    public void cursor() {
        parent.cursor();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>cursor</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aKind
     * @see processing.core.PApplet#cursor(int)
     */
    public void cursor(final int aKind) {
        parent.cursor(aKind);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>cursor</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aImg
     * @see processing.core.PApplet#cursor(processing.core.PImage)
     */
    public void cursor(final PImage aImg) {
        parent.cursor(aImg);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>cursor</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aImg
     * @param aX
     * @param aY
     * @see processing.core.PApplet#cursor(processing.core.PImage, int, int)
     */
    public void cursor(final PImage aImg, final int aX, final int aY) {
        parent.cursor(aImg, aX, aY);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>curve</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aX1
     * @param aY1
     * @param aX2
     * @param aY2
     * @param aX3
     * @param aY3
     * @param aX4
     * @param aY4
     * @see processing.core.PApplet#curve(float, float, float, float, float, float, float, float)
     */
    public void curve(final float aX1, final float aY1, final float aX2, final float aY2, final float aX3,
        final float aY3, final float aX4, final float aY4) {
        parent.curve(aX1, aY1, aX2, aY2, aX3, aY3, aX4, aY4);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>curve</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aX1
     * @param aY1
     * @param aZ1
     * @param aX2
     * @param aY2
     * @param aZ2
     * @param aX3
     * @param aY3
     * @param aZ3
     * @param aX4
     * @param aY4
     * @param aZ4
     * @see processing.core.PApplet#curve(float, float, float, float, float, float, float, float, float, float, float,
     *      float)
     */
    public void curve(final float aX1, final float aY1, final float aZ1, final float aX2, final float aY2,
        final float aZ2, final float aX3, final float aY3, final float aZ3, final float aX4, final float aY4,
        final float aZ4) {
        parent.curve(aX1, aY1, aZ1, aX2, aY2, aZ2, aX3, aY3, aZ3, aX4, aY4, aZ4);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>curveDetail</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aDetail
     * @see processing.core.PApplet#curveDetail(int)
     */
    public void curveDetail(final int aDetail) {
        parent.curveDetail(aDetail);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>curvePoint</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aA
     * @param aB
     * @param aC
     * @param aD
     * @param aT
     * @return
     * @see processing.core.PApplet#curvePoint(float, float, float, float, float)
     */
    public float curvePoint(final float aA, final float aB, final float aC, final float aD, final float aT) {
        return parent.curvePoint(aA, aB, aC, aD, aT);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>curveTangent</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aA
     * @param aB
     * @param aC
     * @param aD
     * @param aT
     * @return
     * @see processing.core.PApplet#curveTangent(float, float, float, float, float)
     */
    public float curveTangent(final float aA, final float aB, final float aC, final float aD, final float aT) {
        return parent.curveTangent(aA, aB, aC, aD, aT);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>curveTightness</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aTightness
     * @see processing.core.PApplet#curveTightness(float)
     */
    public void curveTightness(final float aTightness) {
        parent.curveTightness(aTightness);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>curveVertex</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aX
     * @param aY
     * @see processing.core.PApplet#curveVertex(float, float)
     */
    public void curveVertex(final float aX, final float aY) {
        parent.curveVertex(aX, aY);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>curveVertex</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aX
     * @param aY
     * @param aZ
     * @see processing.core.PApplet#curveVertex(float, float, float)
     */
    public void curveVertex(final float aX, final float aY, final float aZ) {
        parent.curveVertex(aX, aY, aZ);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>dataFile</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aWhere
     * @return
     * @see processing.core.PApplet#dataFile(java.lang.String)
     */
    public File dataFile(final String aWhere) {
        return parent.dataFile(aWhere);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>dataPath</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aWhere
     * @return
     * @see processing.core.PApplet#dataPath(java.lang.String)
     */
    public String dataPath(final String aWhere) {
        return parent.dataPath(aWhere);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>delay</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aNapTime
     * @see processing.core.PApplet#delay(int)
     */
    public void delay(final int aNapTime) {
        parent.delay(aNapTime);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>die</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aWhat
     * @see processing.core.PApplet#die(java.lang.String)
     */
    public void die(final String aWhat) {
        parent.die(aWhat);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>die</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aWhat
     * @param aE
     * @see processing.core.PApplet#die(java.lang.String, java.lang.Exception)
     */
    public void die(final String aWhat, final Exception aE) {
        parent.die(aWhat, aE);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>directionalLight</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aV1
     * @param aV2
     * @param aV3
     * @param aNx
     * @param aNy
     * @param aNz
     * @see processing.core.PApplet#directionalLight(float, float, float, float, float, float)
     */
    public void directionalLight(final float aV1, final float aV2, final float aV3, final float aNx, final float aNy,
        final float aNz) {
        parent.directionalLight(aV1, aV2, aV3, aNx, aNy, aNz);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>displayDensity</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @return
     * @see processing.core.PApplet#displayDensity()
     */
    public int displayDensity() {
        return parent.displayDensity();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>displayDensity</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aDisplay
     * @return
     * @see processing.core.PApplet#displayDensity(int)
     */
    public int displayDensity(final int aDisplay) {
        return parent.displayDensity(aDisplay);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>dispose</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#dispose()
     */
    public void dispose() {
        parent.dispose();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>draw</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#draw()
     */
    public void draw() {
        parent.draw();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>edge</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aEdge
     * @see processing.core.PApplet#edge(boolean)
     */
    public void edge(final boolean aEdge) {
        parent.edge(aEdge);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>ellipse</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aA
     * @param aB
     * @param aC
     * @param aD
     * @see processing.core.PApplet#ellipse(float, float, float, float)
     */
    public void ellipse(final float aA, final float aB, final float aC, final float aD) {
        parent.ellipse(aA, aB, aC, aD);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>ellipseMode</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aMode
     * @see processing.core.PApplet#ellipseMode(int)
     */
    public void ellipseMode(final int aMode) {
        parent.ellipseMode(aMode);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>emissive</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aGray
     * @see processing.core.PApplet#emissive(float)
     */
    public void emissive(final float aGray) {
        parent.emissive(aGray);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>emissive</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aV1
     * @param aV2
     * @param aV3
     * @see processing.core.PApplet#emissive(float, float, float)
     */
    public void emissive(final float aV1, final float aV2, final float aV3) {
        parent.emissive(aV1, aV2, aV3);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>emissive</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aRgb
     * @see processing.core.PApplet#emissive(int)
     */
    public void emissive(final int aRgb) {
        parent.emissive(aRgb);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>endCamera</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#endCamera()
     */
    public void endCamera() {
        parent.endCamera();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>endContour</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#endContour()
     */
    public void endContour() {
        parent.endContour();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>endPGL</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#endPGL()
     */
    public void endPGL() {
        parent.endPGL();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>endRaw</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#endRaw()
     */
    public void endRaw() {
        parent.endRaw();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>endRecord</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#endRecord()
     */
    public void endRecord() {
        parent.endRecord();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>endShape</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#endShape()
     */
    public void endShape() {
        parent.endShape();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>endShape</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aMode
     * @see processing.core.PApplet#endShape(int)
     */
    public void endShape(final int aMode) {
        parent.endShape(aMode);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>exit</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#exit()
     */
    public void exit() {
        parent.exit();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>exitActual</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#exitActual()
     */
    public void exitActual() {
        parent.exitActual();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>exitCalled</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @return
     * @see processing.core.PApplet#exitCalled()
     */
    public boolean exitCalled() {
        return parent.exitCalled();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>fill</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aGray
     * @see processing.core.PApplet#fill(float)
     */
    public void fill(final float aGray) {
        parent.fill(aGray);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>fill</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aGray
     * @param aAlpha
     * @see processing.core.PApplet#fill(float, float)
     */
    public void fill(final float aGray, final float aAlpha) {
        parent.fill(aGray, aAlpha);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>fill</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aV1
     * @param aV2
     * @param aV3
     * @see processing.core.PApplet#fill(float, float, float)
     */
    public void fill(final float aV1, final float aV2, final float aV3) {
        parent.fill(aV1, aV2, aV3);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>fill</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aV1
     * @param aV2
     * @param aV3
     * @param aAlpha
     * @see processing.core.PApplet#fill(float, float, float, float)
     */
    public void fill(final float aV1, final float aV2, final float aV3, final float aAlpha) {
        parent.fill(aV1, aV2, aV3, aAlpha);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>fill</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aRgb
     * @see processing.core.PApplet#fill(int)
     */
    public void fill(final int aRgb) {
        parent.fill(aRgb);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>fill</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aRgb
     * @param aAlpha
     * @see processing.core.PApplet#fill(int, float)
     */
    public void fill(final int aRgb, final float aAlpha) {
        parent.fill(aRgb, aAlpha);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>filter</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aKind
     * @see processing.core.PApplet#filter(int)
     */
    public void filter(final int aKind) {
        parent.filter(aKind);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>filter</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aKind
     * @param aParam
     * @see processing.core.PApplet#filter(int, float)
     */
    public void filter(final int aKind, final float aParam) {
        parent.filter(aKind, aParam);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>filter</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aShader
     * @see processing.core.PApplet#filter(processing.opengl.PShader)
     */
    public void filter(final PShader aShader) {
        parent.filter(aShader);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>flush</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#flush()
     */
    public void flush() {
        parent.flush();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>focusGained</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#focusGained()
     */
    public void focusGained() {
        parent.focusGained();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>focusLost</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#focusLost()
     */
    public void focusLost() {
        parent.focusLost();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>frameMoved</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aX
     * @param aY
     * @see processing.core.PApplet#frameMoved(int, int)
     */
    public void frameMoved(final int aX, final int aY) {
        parent.frameMoved(aX, aY);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>frameRate</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aFps
     * @see processing.core.PApplet#frameRate(float)
     */
    public void frameRate(final float aFps) {
        parent.frameRate(aFps);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>frameResized</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aW
     * @param aH
     * @see processing.core.PApplet#frameResized(int, int)
     */
    public void frameResized(final int aW, final int aH) {
        parent.frameResized(aW, aH);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>frustum</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aLeft
     * @param aRight
     * @param aBottom
     * @param aTop
     * @param aNear
     * @param aFar
     * @see processing.core.PApplet#frustum(float, float, float, float, float, float)
     */
    public void frustum(final float aLeft, final float aRight, final float aBottom, final float aTop, final float aNear,
        final float aFar) {
        parent.frustum(aLeft, aRight, aBottom, aTop, aNear, aFar);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>fullScreen</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#fullScreen()
     */
    public void fullScreen() {
        parent.fullScreen();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>fullScreen</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aDisplay
     * @see processing.core.PApplet#fullScreen(int)
     */
    public void fullScreen(final int aDisplay) {
        parent.fullScreen(aDisplay);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>fullScreen</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aRenderer
     * @see processing.core.PApplet#fullScreen(java.lang.String)
     */
    public void fullScreen(final String aRenderer) {
        parent.fullScreen(aRenderer);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>fullScreen</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aRenderer
     * @param aDisplay
     * @see processing.core.PApplet#fullScreen(java.lang.String, int)
     */
    public void fullScreen(final String aRenderer, final int aDisplay) {
        parent.fullScreen(aRenderer, aDisplay);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>get</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @return
     * @see processing.core.PApplet#get()
     */
    @Transient
    public PImage get() {
        return parent.get();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>get</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aX
     * @param aY
     * @return
     * @see processing.core.PApplet#get(int, int)
     */
    public int get(final int aX, final int aY) {
        return parent.get(aX, aY);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>get</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aX
     * @param aY
     * @param aW
     * @param aH
     * @return
     * @see processing.core.PApplet#get(int, int, int, int)
     */
    public PImage get(final int aX, final int aY, final int aW, final int aH) {
        return parent.get(aX, aY, aW, aH);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>getGraphics</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @return
     * @see processing.core.PApplet#getGraphics()
     */
    public PGraphics getGraphics() {
        return parent.getGraphics();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>getMatrix</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @return
     * @see processing.core.PApplet#getMatrix()
     */
    @Transient
    public PMatrix getMatrix() {
        return parent.getMatrix();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>getMatrix</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aTarget
     * @return
     * @see processing.core.PApplet#getMatrix(processing.core.PMatrix2D)
     */
    @Transient
    public PMatrix2D getMatrix(final PMatrix2D aTarget) {
        return parent.getMatrix(aTarget);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>getMatrix</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aTarget
     * @return
     * @see processing.core.PApplet#getMatrix(processing.core.PMatrix3D)
     */
    @Transient
    public PMatrix3D getMatrix(final PMatrix3D aTarget) {
        return parent.getMatrix(aTarget);
    }

    /** {@inheritDoc} */
    @Override
    @Transient
    public PApplet getParent() {
        return parent;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>getSurface</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @return
     * @see processing.core.PApplet#getSurface()
     */
    @Transient
    public PSurface getSurface() {
        return parent.getSurface();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>green</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aRgb
     * @return
     * @see processing.core.PApplet#green(int)
     */
    public final float green(final int aRgb) {
        return parent.green(aRgb);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>handleDraw</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#handleDraw()
     */
    public void handleDraw() {
        parent.handleDraw();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>hint</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aWhich
     * @see processing.core.PApplet#hint(int)
     */
    public void hint(final int aWhich) {
        parent.hint(aWhich);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>hue</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aRgb
     * @return
     * @see processing.core.PApplet#hue(int)
     */
    public final float hue(final int aRgb) {
        return parent.hue(aRgb);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>image</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aImg
     * @param aA
     * @param aB
     * @see processing.core.PApplet#image(processing.core.PImage, float, float)
     */
    public void image(final PImage aImg, final float aA, final float aB) {
        parent.image(aImg, aA, aB);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>image</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aImg
     * @param aA
     * @param aB
     * @param aC
     * @param aD
     * @see processing.core.PApplet#image(processing.core.PImage, float, float, float, float)
     */
    public void image(final PImage aImg, final float aA, final float aB, final float aC, final float aD) {
        parent.image(aImg, aA, aB, aC, aD);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>image</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aImg
     * @param aA
     * @param aB
     * @param aC
     * @param aD
     * @param aU1
     * @param aV1
     * @param aU2
     * @param aV2
     * @see processing.core.PApplet#image(processing.core.PImage, float, float, float, float, int, int, int, int)
     */
    public void image(final PImage aImg, final float aA, final float aB, final float aC, final float aD, final int aU1,
        final int aV1, final int aU2, final int aV2) {
        parent.image(aImg, aA, aB, aC, aD, aU1, aV1, aU2, aV2);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>imageMode</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aMode
     * @see processing.core.PApplet#imageMode(int)
     */
    public void imageMode(final int aMode) {
        parent.imageMode(aMode);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>insertFrame</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aWhat
     * @return
     * @see processing.core.PApplet#insertFrame(java.lang.String)
     */
    public String insertFrame(final String aWhat) {
        return parent.insertFrame(aWhat);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>isLooping</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @return
     * @see processing.core.PApplet#isLooping()
     */
    public boolean isLooping() {
        return parent.isLooping();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>keyPressed</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#keyPressed()
     */
    public void keyPressed() {
        parent.keyPressed();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>keyPressed</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aEvent
     * @see processing.core.PApplet#keyPressed(processing.event.KeyEvent)
     */
    public void keyPressed(final KeyEvent aEvent) {
        parent.keyPressed(aEvent);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>keyReleased</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#keyReleased()
     */
    public void keyReleased() {
        parent.keyReleased();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>keyReleased</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aEvent
     * @see processing.core.PApplet#keyReleased(processing.event.KeyEvent)
     */
    public void keyReleased(final KeyEvent aEvent) {
        parent.keyReleased(aEvent);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>keyTyped</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#keyTyped()
     */
    public void keyTyped() {
        parent.keyTyped();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>keyTyped</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aEvent
     * @see processing.core.PApplet#keyTyped(processing.event.KeyEvent)
     */
    public void keyTyped(final KeyEvent aEvent) {
        parent.keyTyped(aEvent);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>lerpColor</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aC1
     * @param aC2
     * @param aAmt
     * @return
     * @see processing.core.PApplet#lerpColor(int, int, float)
     */
    public int lerpColor(final int aC1, final int aC2, final float aAmt) {
        return parent.lerpColor(aC1, aC2, aAmt);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>lightFalloff</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aConstant
     * @param aLinear
     * @param aQuadratic
     * @see processing.core.PApplet#lightFalloff(float, float, float)
     */
    public void lightFalloff(final float aConstant, final float aLinear, final float aQuadratic) {
        parent.lightFalloff(aConstant, aLinear, aQuadratic);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>lights</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#lights()
     */
    public void lights() {
        parent.lights();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>lightSpecular</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aV1
     * @param aV2
     * @param aV3
     * @see processing.core.PApplet#lightSpecular(float, float, float)
     */
    public void lightSpecular(final float aV1, final float aV2, final float aV3) {
        parent.lightSpecular(aV1, aV2, aV3);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>line</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aX1
     * @param aY1
     * @param aX2
     * @param aY2
     * @see processing.core.PApplet#line(float, float, float, float)
     */
    public void line(final float aX1, final float aY1, final float aX2, final float aY2) {
        parent.line(aX1, aY1, aX2, aY2);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>line</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aX1
     * @param aY1
     * @param aZ1
     * @param aX2
     * @param aY2
     * @param aZ2
     * @see processing.core.PApplet#line(float, float, float, float, float, float)
     */
    public void line(final float aX1, final float aY1, final float aZ1, final float aX2, final float aY2,
        final float aZ2) {
        parent.line(aX1, aY1, aZ1, aX2, aY2, aZ2);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>link</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aUrl
     * @see processing.core.PApplet#link(java.lang.String)
     */
    public void link(final String aUrl) {
        parent.link(aUrl);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>listFiles</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aPath
     * @param aOptions
     * @return
     * @see processing.core.PApplet#listFiles(java.lang.String, java.lang.String[])
     */
    public File[] listFiles(final String aPath, final String... aOptions) {
        return parent.listFiles(aPath, aOptions);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>listPaths</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aPath
     * @param aOptions
     * @return
     * @see processing.core.PApplet#listPaths(java.lang.String, java.lang.String[])
     */
    public String[] listPaths(final String aPath, final String... aOptions) {
        return parent.listPaths(aPath, aOptions);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>loadBytes</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aFilename
     * @return
     * @see processing.core.PApplet#loadBytes(java.lang.String)
     */
    public byte[] loadBytes(final String aFilename) {
        return parent.loadBytes(aFilename);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>loadFont</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aFilename
     * @return
     * @see processing.core.PApplet#loadFont(java.lang.String)
     */
    public PFont loadFont(final String aFilename) {
        return parent.loadFont(aFilename);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>loadImage</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aFilename
     * @return
     * @see processing.core.PApplet#loadImage(java.lang.String)
     */
    public PImage loadImage(final String aFilename) {
        return parent.loadImage(aFilename);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>loadImage</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aFilename
     * @param aExtension
     * @return
     * @see processing.core.PApplet#loadImage(java.lang.String, java.lang.String)
     */
    public PImage loadImage(final String aFilename, final String aExtension) {
        return parent.loadImage(aFilename, aExtension);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>loadJSONArray</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aFilename
     * @return
     * @see processing.core.PApplet#loadJSONArray(java.lang.String)
     */
    public JSONArray loadJSONArray(final String aFilename) {
        return parent.loadJSONArray(aFilename);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>loadJSONObject</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aFilename
     * @return
     * @see processing.core.PApplet#loadJSONObject(java.lang.String)
     */
    public JSONObject loadJSONObject(final String aFilename) {
        return parent.loadJSONObject(aFilename);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>loadPixels</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#loadPixels()
     */
    public void loadPixels() {
        parent.loadPixels();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>loadShader</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aFragFilename
     * @return
     * @see processing.core.PApplet#loadShader(java.lang.String)
     */
    public PShader loadShader(final String aFragFilename) {
        return parent.loadShader(aFragFilename);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>loadShader</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aFragFilename
     * @param aVertFilename
     * @return
     * @see processing.core.PApplet#loadShader(java.lang.String, java.lang.String)
     */
    public PShader loadShader(final String aFragFilename, final String aVertFilename) {
        return parent.loadShader(aFragFilename, aVertFilename);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>loadShape</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aFilename
     * @return
     * @see processing.core.PApplet#loadShape(java.lang.String)
     */
    public PShape loadShape(final String aFilename) {
        return parent.loadShape(aFilename);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>loadShape</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aFilename
     * @param aOptions
     * @return
     * @see processing.core.PApplet#loadShape(java.lang.String, java.lang.String)
     */
    public PShape loadShape(final String aFilename, final String aOptions) {
        return parent.loadShape(aFilename, aOptions);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>loadStrings</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aFilename
     * @return
     * @see processing.core.PApplet#loadStrings(java.lang.String)
     */
    public String[] loadStrings(final String aFilename) {
        return parent.loadStrings(aFilename);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>loadTable</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aFilename
     * @return
     * @see processing.core.PApplet#loadTable(java.lang.String)
     */
    public Table loadTable(final String aFilename) {
        return parent.loadTable(aFilename);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>loadTable</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aFilename
     * @param aOptions
     * @return
     * @see processing.core.PApplet#loadTable(java.lang.String, java.lang.String)
     */
    public Table loadTable(final String aFilename, final String aOptions) {
        return parent.loadTable(aFilename, aOptions);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>loadXML</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aFilename
     * @return
     * @see processing.core.PApplet#loadXML(java.lang.String)
     */
    public XML loadXML(final String aFilename) {
        return parent.loadXML(aFilename);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>loadXML</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aFilename
     * @param aOptions
     * @return
     * @see processing.core.PApplet#loadXML(java.lang.String, java.lang.String)
     */
    public XML loadXML(final String aFilename, final String aOptions) {
        return parent.loadXML(aFilename, aOptions);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>loop</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#loop()
     */
    public void loop() {
        parent.loop();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>mask</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aImg
     * @see processing.core.PApplet#mask(processing.core.PImage)
     */
    public void mask(final PImage aImg) {
        parent.mask(aImg);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>method</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aName
     * @see processing.core.PApplet#method(java.lang.String)
     */
    public void method(final String aName) {
        parent.method(aName);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>millis</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @return
     * @see processing.core.PApplet#millis()
     */
    public int millis() {
        return parent.millis();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>modelX</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aX
     * @param aY
     * @param aZ
     * @return
     * @see processing.core.PApplet#modelX(float, float, float)
     */
    public float modelX(final float aX, final float aY, final float aZ) {
        return parent.modelX(aX, aY, aZ);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>modelY</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aX
     * @param aY
     * @param aZ
     * @return
     * @see processing.core.PApplet#modelY(float, float, float)
     */
    public float modelY(final float aX, final float aY, final float aZ) {
        return parent.modelY(aX, aY, aZ);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>modelZ</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aX
     * @param aY
     * @param aZ
     * @return
     * @see processing.core.PApplet#modelZ(float, float, float)
     */
    public float modelZ(final float aX, final float aY, final float aZ) {
        return parent.modelZ(aX, aY, aZ);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>mouseClicked</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#mouseClicked()
     */
    public void mouseClicked() {
        parent.mouseClicked();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>mouseClicked</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aEvent
     * @see processing.core.PApplet#mouseClicked(processing.event.MouseEvent)
     */
    public void mouseClicked(final MouseEvent aEvent) {
        parent.mouseClicked(aEvent);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>mouseDragged</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#mouseDragged()
     */
    public void mouseDragged() {
        parent.mouseDragged();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>mouseDragged</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aEvent
     * @see processing.core.PApplet#mouseDragged(processing.event.MouseEvent)
     */
    public void mouseDragged(final MouseEvent aEvent) {
        parent.mouseDragged(aEvent);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>mouseEntered</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#mouseEntered()
     */
    public void mouseEntered() {
        parent.mouseEntered();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>mouseEntered</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aEvent
     * @see processing.core.PApplet#mouseEntered(processing.event.MouseEvent)
     */
    public void mouseEntered(final MouseEvent aEvent) {
        parent.mouseEntered(aEvent);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>mouseExited</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#mouseExited()
     */
    public void mouseExited() {
        parent.mouseExited();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>mouseExited</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aEvent
     * @see processing.core.PApplet#mouseExited(processing.event.MouseEvent)
     */
    public void mouseExited(final MouseEvent aEvent) {
        parent.mouseExited(aEvent);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>mouseMoved</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#mouseMoved()
     */
    public void mouseMoved() {
        parent.mouseMoved();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>mouseMoved</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aEvent
     * @see processing.core.PApplet#mouseMoved(processing.event.MouseEvent)
     */
    public void mouseMoved(final MouseEvent aEvent) {
        parent.mouseMoved(aEvent);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>mousePressed</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#mousePressed()
     */
    public void mousePressed() {
        parent.mousePressed();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>mousePressed</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aEvent
     * @see processing.core.PApplet#mousePressed(processing.event.MouseEvent)
     */
    public void mousePressed(final MouseEvent aEvent) {
        parent.mousePressed(aEvent);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>mouseReleased</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#mouseReleased()
     */
    public void mouseReleased() {
        parent.mouseReleased();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>mouseReleased</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aEvent
     * @see processing.core.PApplet#mouseReleased(processing.event.MouseEvent)
     */
    public void mouseReleased(final MouseEvent aEvent) {
        parent.mouseReleased(aEvent);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>mouseWheel</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#mouseWheel()
     */
    public void mouseWheel() {
        parent.mouseWheel();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>mouseWheel</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aEvent
     * @see processing.core.PApplet#mouseWheel(processing.event.MouseEvent)
     */
    public void mouseWheel(final MouseEvent aEvent) {
        parent.mouseWheel(aEvent);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>noClip</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#noClip()
     */
    public void noClip() {
        parent.noClip();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>noCursor</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#noCursor()
     */
    public void noCursor() {
        parent.noCursor();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>noFill</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#noFill()
     */
    public void noFill() {
        parent.noFill();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>noise</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aX
     * @return
     * @see processing.core.PApplet#noise(float)
     */
    public float noise(final float aX) {
        return parent.noise(aX);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>noise</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aX
     * @param aY
     * @return
     * @see processing.core.PApplet#noise(float, float)
     */
    public float noise(final float aX, final float aY) {
        return parent.noise(aX, aY);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>noise</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aX
     * @param aY
     * @param aZ
     * @return
     * @see processing.core.PApplet#noise(float, float, float)
     */
    public float noise(final float aX, final float aY, final float aZ) {
        return parent.noise(aX, aY, aZ);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>noiseDetail</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aLod
     * @see processing.core.PApplet#noiseDetail(int)
     */
    public void noiseDetail(final int aLod) {
        parent.noiseDetail(aLod);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>noiseDetail</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aLod
     * @param aFalloff
     * @see processing.core.PApplet#noiseDetail(int, float)
     */
    public void noiseDetail(final int aLod, final float aFalloff) {
        parent.noiseDetail(aLod, aFalloff);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>noiseSeed</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aSeed
     * @see processing.core.PApplet#noiseSeed(long)
     */
    public void noiseSeed(final long aSeed) {
        parent.noiseSeed(aSeed);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>noLights</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#noLights()
     */
    public void noLights() {
        parent.noLights();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>noLoop</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#noLoop()
     */
    public void noLoop() {
        parent.noLoop();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>normal</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aNx
     * @param aNy
     * @param aNz
     * @see processing.core.PApplet#normal(float, float, float)
     */
    public void normal(final float aNx, final float aNy, final float aNz) {
        parent.normal(aNx, aNy, aNz);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>noSmooth</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#noSmooth()
     */
    public void noSmooth() {
        parent.noSmooth();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>noStroke</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#noStroke()
     */
    public void noStroke() {
        parent.noStroke();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>noTexture</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#noTexture()
     */
    public void noTexture() {
        parent.noTexture();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>noTint</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#noTint()
     */
    public void noTint() {
        parent.noTint();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>orientation</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aWhich
     * @see processing.core.PApplet#orientation(int)
     */
    public void orientation(final int aWhich) {
        parent.orientation(aWhich);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>ortho</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#ortho()
     */
    public void ortho() {
        parent.ortho();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>ortho</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aLeft
     * @param aRight
     * @param aBottom
     * @param aTop
     * @see processing.core.PApplet#ortho(float, float, float, float)
     */
    public void ortho(final float aLeft, final float aRight, final float aBottom, final float aTop) {
        parent.ortho(aLeft, aRight, aBottom, aTop);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>ortho</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aLeft
     * @param aRight
     * @param aBottom
     * @param aTop
     * @param aNear
     * @param aFar
     * @see processing.core.PApplet#ortho(float, float, float, float, float, float)
     */
    public void ortho(final float aLeft, final float aRight, final float aBottom, final float aTop, final float aNear,
        final float aFar) {
        parent.ortho(aLeft, aRight, aBottom, aTop, aNear, aFar);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>parseJSONArray</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aInput
     * @return
     * @see processing.core.PApplet#parseJSONArray(java.lang.String)
     */
    public JSONArray parseJSONArray(final String aInput) {
        return parent.parseJSONArray(aInput);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>parseJSONObject</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aInput
     * @return
     * @see processing.core.PApplet#parseJSONObject(java.lang.String)
     */
    public JSONObject parseJSONObject(final String aInput) {
        return parent.parseJSONObject(aInput);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>parseXML</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aXmlString
     * @return
     * @see processing.core.PApplet#parseXML(java.lang.String)
     */
    public XML parseXML(final String aXmlString) {
        return parent.parseXML(aXmlString);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>parseXML</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aXmlString
     * @param aOptions
     * @return
     * @see processing.core.PApplet#parseXML(java.lang.String, java.lang.String)
     */
    public XML parseXML(final String aXmlString, final String aOptions) {
        return parent.parseXML(aXmlString, aOptions);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>pause</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#pause()
     */
    public void pause() {
        parent.pause();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>perspective</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#perspective()
     */
    public void perspective() {
        parent.perspective();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>perspective</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aFovy
     * @param aAspect
     * @param aZNear
     * @param aZFar
     * @see processing.core.PApplet#perspective(float, float, float, float)
     */
    public void perspective(final float aFovy, final float aAspect, final float aZNear, final float aZFar) {
        parent.perspective(aFovy, aAspect, aZNear, aZFar);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>pixelDensity</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aDensity
     * @see processing.core.PApplet#pixelDensity(int)
     */
    public void pixelDensity(final int aDensity) {
        parent.pixelDensity(aDensity);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>point</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aX
     * @param aY
     * @see processing.core.PApplet#point(float, float)
     */
    public void point(final float aX, final float aY) {
        parent.point(aX, aY);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>point</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aX
     * @param aY
     * @param aZ
     * @see processing.core.PApplet#point(float, float, float)
     */
    public void point(final float aX, final float aY, final float aZ) {
        parent.point(aX, aY, aZ);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>pointLight</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aV1
     * @param aV2
     * @param aV3
     * @param aX
     * @param aY
     * @param aZ
     * @see processing.core.PApplet#pointLight(float, float, float, float, float, float)
     */
    public void pointLight(final float aV1, final float aV2, final float aV3, final float aX, final float aY,
        final float aZ) {
        parent.pointLight(aV1, aV2, aV3, aX, aY, aZ);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>popMatrix</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#popMatrix()
     */
    public void popMatrix() {
        parent.popMatrix();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>popStyle</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#popStyle()
     */
    public void popStyle() {
        parent.popStyle();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>postEvent</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aPe
     * @see processing.core.PApplet#postEvent(processing.event.Event)
     */
    public void postEvent(final Event aPe) {
        parent.postEvent(aPe);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>printCamera</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#printCamera()
     */
    public void printCamera() {
        parent.printCamera();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>printMatrix</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#printMatrix()
     */
    public void printMatrix() {
        parent.printMatrix();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>printProjection</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#printProjection()
     */
    public void printProjection() {
        parent.printProjection();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>pushMatrix</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#pushMatrix()
     */
    public void pushMatrix() {
        parent.pushMatrix();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>pushStyle</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#pushStyle()
     */
    public void pushStyle() {
        parent.pushStyle();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>quad</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aX1
     * @param aY1
     * @param aX2
     * @param aY2
     * @param aX3
     * @param aY3
     * @param aX4
     * @param aY4
     * @see processing.core.PApplet#quad(float, float, float, float, float, float, float, float)
     */
    public void quad(final float aX1, final float aY1, final float aX2, final float aY2, final float aX3,
        final float aY3, final float aX4, final float aY4) {
        parent.quad(aX1, aY1, aX2, aY2, aX3, aY3, aX4, aY4);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>quadraticVertex</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aCx
     * @param aCy
     * @param aX3
     * @param aY3
     * @see processing.core.PApplet#quadraticVertex(float, float, float, float)
     */
    public void quadraticVertex(final float aCx, final float aCy, final float aX3, final float aY3) {
        parent.quadraticVertex(aCx, aCy, aX3, aY3);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>quadraticVertex</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aCx
     * @param aCy
     * @param aCz
     * @param aX3
     * @param aY3
     * @param aZ3
     * @see processing.core.PApplet#quadraticVertex(float, float, float, float, float, float)
     */
    public void quadraticVertex(final float aCx, final float aCy, final float aCz, final float aX3, final float aY3,
        final float aZ3) {
        parent.quadraticVertex(aCx, aCy, aCz, aX3, aY3, aZ3);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>random</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aHigh
     * @return
     * @see processing.core.PApplet#random(float)
     */
    public final float random(final float aHigh) {
        return parent.random(aHigh);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>random</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aLow
     * @param aHigh
     * @return
     * @see processing.core.PApplet#random(float, float)
     */
    public final float random(final float aLow, final float aHigh) {
        return parent.random(aLow, aHigh);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>randomGaussian</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @return
     * @see processing.core.PApplet#randomGaussian()
     */
    public final float randomGaussian() {
        return parent.randomGaussian();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>randomSeed</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aSeed
     * @see processing.core.PApplet#randomSeed(long)
     */
    public final void randomSeed(final long aSeed) {
        parent.randomSeed(aSeed);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>rect</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aA
     * @param aB
     * @param aC
     * @param aD
     * @see processing.core.PApplet#rect(float, float, float, float)
     */
    public void rect(final float aA, final float aB, final float aC, final float aD) {
        parent.rect(aA, aB, aC, aD);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>rect</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aA
     * @param aB
     * @param aC
     * @param aD
     * @param aR
     * @see processing.core.PApplet#rect(float, float, float, float, float)
     */
    public void rect(final float aA, final float aB, final float aC, final float aD, final float aR) {
        parent.rect(aA, aB, aC, aD, aR);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>rect</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aA
     * @param aB
     * @param aC
     * @param aD
     * @param aTl
     * @param aTr
     * @param aBr
     * @param aBl
     * @see processing.core.PApplet#rect(float, float, float, float, float, float, float, float)
     */
    public void rect(final float aA, final float aB, final float aC, final float aD, final float aTl, final float aTr,
        final float aBr, final float aBl) {
        parent.rect(aA, aB, aC, aD, aTl, aTr, aBr, aBl);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>rectMode</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aMode
     * @see processing.core.PApplet#rectMode(int)
     */
    public void rectMode(final int aMode) {
        parent.rectMode(aMode);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>red</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aRgb
     * @return
     * @see processing.core.PApplet#red(int)
     */
    public final float red(final int aRgb) {
        return parent.red(aRgb);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>redraw</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#redraw()
     */
    public void redraw() {
        parent.redraw();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>registerMethod</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aMethodName
     * @param aTarget
     * @see processing.core.PApplet#registerMethod(java.lang.String, java.lang.Object)
     */
    public void registerMethod(final String aMethodName, final Object aTarget) {
        parent.registerMethod(aMethodName, aTarget);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>requestImage</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aFilename
     * @return
     * @see processing.core.PApplet#requestImage(java.lang.String)
     */
    public PImage requestImage(final String aFilename) {
        return parent.requestImage(aFilename);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>requestImage</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aFilename
     * @param aExtension
     * @return
     * @see processing.core.PApplet#requestImage(java.lang.String, java.lang.String)
     */
    public PImage requestImage(final String aFilename, final String aExtension) {
        return parent.requestImage(aFilename, aExtension);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>resetMatrix</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#resetMatrix()
     */
    public void resetMatrix() {
        parent.resetMatrix();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>resetShader</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#resetShader()
     */
    public void resetShader() {
        parent.resetShader();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>resetShader</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aKind
     * @see processing.core.PApplet#resetShader(int)
     */
    public void resetShader(final int aKind) {
        parent.resetShader(aKind);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>resume</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#resume()
     */
    public void resume() {
        parent.resume();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>rotate</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aAngle
     * @see processing.core.PApplet#rotate(float)
     */
    public void rotate(final float aAngle) {
        parent.rotate(aAngle);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>rotate</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aAngle
     * @param aX
     * @param aY
     * @param aZ
     * @see processing.core.PApplet#rotate(float, float, float, float)
     */
    public void rotate(final float aAngle, final float aX, final float aY, final float aZ) {
        parent.rotate(aAngle, aX, aY, aZ);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>rotateX</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aAngle
     * @see processing.core.PApplet#rotateX(float)
     */
    public void rotateX(final float aAngle) {
        parent.rotateX(aAngle);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>rotateY</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aAngle
     * @see processing.core.PApplet#rotateY(float)
     */
    public void rotateY(final float aAngle) {
        parent.rotateY(aAngle);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>rotateZ</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aAngle
     * @see processing.core.PApplet#rotateZ(float)
     */
    public void rotateZ(final float aAngle) {
        parent.rotateZ(aAngle);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>saturation</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aRgb
     * @return
     * @see processing.core.PApplet#saturation(int)
     */
    public final float saturation(final int aRgb) {
        return parent.saturation(aRgb);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>save</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aFilename
     * @see processing.core.PApplet#save(java.lang.String)
     */
    public void save(final String aFilename) {
        parent.save(aFilename);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>saveBytes</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aFilename
     * @param aData
     * @see processing.core.PApplet#saveBytes(java.lang.String, byte[])
     */
    public void saveBytes(final String aFilename, final byte[] aData) {
        parent.saveBytes(aFilename, aData);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>saveFile</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aWhere
     * @return
     * @see processing.core.PApplet#saveFile(java.lang.String)
     */
    public File saveFile(final String aWhere) {
        return parent.saveFile(aWhere);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>saveFrame</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#saveFrame()
     */
    public void saveFrame() {
        parent.saveFrame();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>saveFrame</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aFilename
     * @see processing.core.PApplet#saveFrame(java.lang.String)
     */
    public void saveFrame(final String aFilename) {
        parent.saveFrame(aFilename);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>saveJSONArray</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aJson
     * @param aFilename
     * @return
     * @see processing.core.PApplet#saveJSONArray(processing.data.JSONArray, java.lang.String)
     */
    public boolean saveJSONArray(final JSONArray aJson, final String aFilename) {
        return parent.saveJSONArray(aJson, aFilename);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>saveJSONArray</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aJson
     * @param aFilename
     * @param aOptions
     * @return
     * @see processing.core.PApplet#saveJSONArray(processing.data.JSONArray, java.lang.String, java.lang.String)
     */
    public boolean saveJSONArray(final JSONArray aJson, final String aFilename, final String aOptions) {
        return parent.saveJSONArray(aJson, aFilename, aOptions);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>saveJSONObject</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aJson
     * @param aFilename
     * @return
     * @see processing.core.PApplet#saveJSONObject(processing.data.JSONObject, java.lang.String)
     */
    public boolean saveJSONObject(final JSONObject aJson, final String aFilename) {
        return parent.saveJSONObject(aJson, aFilename);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>saveJSONObject</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aJson
     * @param aFilename
     * @param aOptions
     * @return
     * @see processing.core.PApplet#saveJSONObject(processing.data.JSONObject, java.lang.String, java.lang.String)
     */
    public boolean saveJSONObject(final JSONObject aJson, final String aFilename, final String aOptions) {
        return parent.saveJSONObject(aJson, aFilename, aOptions);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>savePath</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aWhere
     * @return
     * @see processing.core.PApplet#savePath(java.lang.String)
     */
    public String savePath(final String aWhere) {
        return parent.savePath(aWhere);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>saveStream</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aTarget
     * @param aSource
     * @return
     * @see processing.core.PApplet#saveStream(java.io.File, java.lang.String)
     */
    public boolean saveStream(final File aTarget, final String aSource) {
        return parent.saveStream(aTarget, aSource);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>saveStream</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aTarget
     * @param aSource
     * @return
     * @see processing.core.PApplet#saveStream(java.lang.String, java.io.InputStream)
     */
    public boolean saveStream(final String aTarget, final InputStream aSource) {
        return parent.saveStream(aTarget, aSource);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>saveStream</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aTarget
     * @param aSource
     * @return
     * @see processing.core.PApplet#saveStream(java.lang.String, java.lang.String)
     */
    public boolean saveStream(final String aTarget, final String aSource) {
        return parent.saveStream(aTarget, aSource);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>saveStrings</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aFilename
     * @param aData
     * @see processing.core.PApplet#saveStrings(java.lang.String, java.lang.String[])
     */
    public void saveStrings(final String aFilename, final String[] aData) {
        parent.saveStrings(aFilename, aData);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>saveTable</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aTable
     * @param aFilename
     * @return
     * @see processing.core.PApplet#saveTable(processing.data.Table, java.lang.String)
     */
    public boolean saveTable(final Table aTable, final String aFilename) {
        return parent.saveTable(aTable, aFilename);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>saveTable</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aTable
     * @param aFilename
     * @param aOptions
     * @return
     * @see processing.core.PApplet#saveTable(processing.data.Table, java.lang.String, java.lang.String)
     */
    public boolean saveTable(final Table aTable, final String aFilename, final String aOptions) {
        return parent.saveTable(aTable, aFilename, aOptions);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>saveXML</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aXml
     * @param aFilename
     * @return
     * @see processing.core.PApplet#saveXML(processing.data.XML, java.lang.String)
     */
    public boolean saveXML(final XML aXml, final String aFilename) {
        return parent.saveXML(aXml, aFilename);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>saveXML</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aXml
     * @param aFilename
     * @param aOptions
     * @return
     * @see processing.core.PApplet#saveXML(processing.data.XML, java.lang.String, java.lang.String)
     */
    public boolean saveXML(final XML aXml, final String aFilename, final String aOptions) {
        return parent.saveXML(aXml, aFilename, aOptions);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>scale</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aS
     * @see processing.core.PApplet#scale(float)
     */
    public void scale(final float aS) {
        parent.scale(aS);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>scale</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aX
     * @param aY
     * @see processing.core.PApplet#scale(float, float)
     */
    public void scale(final float aX, final float aY) {
        parent.scale(aX, aY);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>scale</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aX
     * @param aY
     * @param aZ
     * @see processing.core.PApplet#scale(float, float, float)
     */
    public void scale(final float aX, final float aY, final float aZ) {
        parent.scale(aX, aY, aZ);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>screenX</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aX
     * @param aY
     * @return
     * @see processing.core.PApplet#screenX(float, float)
     */
    public float screenX(final float aX, final float aY) {
        return parent.screenX(aX, aY);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>screenX</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aX
     * @param aY
     * @param aZ
     * @return
     * @see processing.core.PApplet#screenX(float, float, float)
     */
    public float screenX(final float aX, final float aY, final float aZ) {
        return parent.screenX(aX, aY, aZ);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>screenY</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aX
     * @param aY
     * @return
     * @see processing.core.PApplet#screenY(float, float)
     */
    public float screenY(final float aX, final float aY) {
        return parent.screenY(aX, aY);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>screenY</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aX
     * @param aY
     * @param aZ
     * @return
     * @see processing.core.PApplet#screenY(float, float, float)
     */
    public float screenY(final float aX, final float aY, final float aZ) {
        return parent.screenY(aX, aY, aZ);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>screenZ</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aX
     * @param aY
     * @param aZ
     * @return
     * @see processing.core.PApplet#screenZ(float, float, float)
     */
    public float screenZ(final float aX, final float aY, final float aZ) {
        return parent.screenZ(aX, aY, aZ);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>selectFolder</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aPrompt
     * @param aCallback
     * @see processing.core.PApplet#selectFolder(java.lang.String, java.lang.String)
     */
    public void selectFolder(final String aPrompt, final String aCallback) {
        parent.selectFolder(aPrompt, aCallback);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>selectFolder</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aPrompt
     * @param aCallback
     * @param aFile
     * @see processing.core.PApplet#selectFolder(java.lang.String, java.lang.String, java.io.File)
     */
    public void selectFolder(final String aPrompt, final String aCallback, final File aFile) {
        parent.selectFolder(aPrompt, aCallback, aFile);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>selectFolder</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aPrompt
     * @param aCallback
     * @param aFile
     * @param aCallbackObject
     * @see processing.core.PApplet#selectFolder(java.lang.String, java.lang.String, java.io.File, java.lang.Object)
     */
    public void selectFolder(final String aPrompt, final String aCallback, final File aFile,
        final Object aCallbackObject) {
        parent.selectFolder(aPrompt, aCallback, aFile, aCallbackObject);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>selectInput</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aPrompt
     * @param aCallback
     * @see processing.core.PApplet#selectInput(java.lang.String, java.lang.String)
     */
    public void selectInput(final String aPrompt, final String aCallback) {
        parent.selectInput(aPrompt, aCallback);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>selectInput</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aPrompt
     * @param aCallback
     * @param aFile
     * @see processing.core.PApplet#selectInput(java.lang.String, java.lang.String, java.io.File)
     */
    public void selectInput(final String aPrompt, final String aCallback, final File aFile) {
        parent.selectInput(aPrompt, aCallback, aFile);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>selectInput</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aPrompt
     * @param aCallback
     * @param aFile
     * @param aCallbackObject
     * @see processing.core.PApplet#selectInput(java.lang.String, java.lang.String, java.io.File, java.lang.Object)
     */
    public void selectInput(final String aPrompt, final String aCallback, final File aFile,
        final Object aCallbackObject) {
        parent.selectInput(aPrompt, aCallback, aFile, aCallbackObject);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>selectOutput</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aPrompt
     * @param aCallback
     * @see processing.core.PApplet#selectOutput(java.lang.String, java.lang.String)
     */
    public void selectOutput(final String aPrompt, final String aCallback) {
        parent.selectOutput(aPrompt, aCallback);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>selectOutput</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aPrompt
     * @param aCallback
     * @param aFile
     * @see processing.core.PApplet#selectOutput(java.lang.String, java.lang.String, java.io.File)
     */
    public void selectOutput(final String aPrompt, final String aCallback, final File aFile) {
        parent.selectOutput(aPrompt, aCallback, aFile);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>selectOutput</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aPrompt
     * @param aCallback
     * @param aFile
     * @param aCallbackObject
     * @see processing.core.PApplet#selectOutput(java.lang.String, java.lang.String, java.io.File, java.lang.Object)
     */
    public void selectOutput(final String aPrompt, final String aCallback, final File aFile,
        final Object aCallbackObject) {
        parent.selectOutput(aPrompt, aCallback, aFile, aCallbackObject);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>set</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aX
     * @param aY
     * @param aC
     * @see processing.core.PApplet#set(int, int, int)
     */
    public void set(final int aX, final int aY, final int aC) {
        parent.set(aX, aY, aC);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>set</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aX
     * @param aY
     * @param aImg
     * @see processing.core.PApplet#set(int, int, processing.core.PImage)
     */
    public void set(final int aX, final int aY, final PImage aImg) {
        parent.set(aX, aY, aImg);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>setMatrix</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aSource
     * @see processing.core.PApplet#setMatrix(processing.core.PMatrix)
     */
    public void setMatrix(final PMatrix aSource) {
        parent.setMatrix(aSource);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>setMatrix</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aSource
     * @see processing.core.PApplet#setMatrix(processing.core.PMatrix2D)
     */
    public void setMatrix(final PMatrix2D aSource) {
        parent.setMatrix(aSource);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>setMatrix</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aSource
     * @see processing.core.PApplet#setMatrix(processing.core.PMatrix3D)
     */
    public void setMatrix(final PMatrix3D aSource) {
        parent.setMatrix(aSource);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>setSize</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aWidth
     * @param aHeight
     * @see processing.core.PApplet#setSize(int, int)
     */
    public void setSize(final int aWidth, final int aHeight) {
        parent.setSize(aWidth, aHeight);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>settings</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#settings()
     */
    public void settings() {
        parent.settings();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>setup</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#setup()
     */
    public void setup() {
        parent.setup();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>shader</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aShader
     * @see processing.core.PApplet#shader(processing.opengl.PShader)
     */
    public void shader(final PShader aShader) {
        parent.shader(aShader);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>shader</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aShader
     * @param aKind
     * @see processing.core.PApplet#shader(processing.opengl.PShader, int)
     */
    public void shader(final PShader aShader, final int aKind) {
        parent.shader(aShader, aKind);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>shape</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aShape
     * @see processing.core.PApplet#shape(processing.core.PShape)
     */
    public void shape(final PShape aShape) {
        parent.shape(aShape);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>shape</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aShape
     * @param aX
     * @param aY
     * @see processing.core.PApplet#shape(processing.core.PShape, float, float)
     */
    public void shape(final PShape aShape, final float aX, final float aY) {
        parent.shape(aShape, aX, aY);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>shape</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aShape
     * @param aA
     * @param aB
     * @param aC
     * @param aD
     * @see processing.core.PApplet#shape(processing.core.PShape, float, float, float, float)
     */
    public void shape(final PShape aShape, final float aA, final float aB, final float aC, final float aD) {
        parent.shape(aShape, aA, aB, aC, aD);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>shapeMode</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aMode
     * @see processing.core.PApplet#shapeMode(int)
     */
    public void shapeMode(final int aMode) {
        parent.shapeMode(aMode);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>shearX</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aAngle
     * @see processing.core.PApplet#shearX(float)
     */
    public void shearX(final float aAngle) {
        parent.shearX(aAngle);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>shearY</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aAngle
     * @see processing.core.PApplet#shearY(float)
     */
    public void shearY(final float aAngle) {
        parent.shearY(aAngle);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>shininess</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aShine
     * @see processing.core.PApplet#shininess(float)
     */
    public void shininess(final float aShine) {
        parent.shininess(aShine);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>size</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aWidth
     * @param aHeight
     * @see processing.core.PApplet#size(int, int)
     */
    public void size(final int aWidth, final int aHeight) {
        parent.size(aWidth, aHeight);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>size</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aWidth
     * @param aHeight
     * @param aRenderer
     * @see processing.core.PApplet#size(int, int, java.lang.String)
     */
    public void size(final int aWidth, final int aHeight, final String aRenderer) {
        parent.size(aWidth, aHeight, aRenderer);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>size</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aWidth
     * @param aHeight
     * @param aRenderer
     * @param aPath
     * @see processing.core.PApplet#size(int, int, java.lang.String, java.lang.String)
     */
    public void size(final int aWidth, final int aHeight, final String aRenderer, final String aPath) {
        parent.size(aWidth, aHeight, aRenderer, aPath);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>sketchDisplay</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @return
     * @see processing.core.PApplet#sketchDisplay()
     */
    public final int sketchDisplay() {
        return parent.sketchDisplay();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>sketchFile</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aWhere
     * @return
     * @see processing.core.PApplet#sketchFile(java.lang.String)
     */
    public File sketchFile(final String aWhere) {
        return parent.sketchFile(aWhere);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>sketchFullScreen</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @return
     * @see processing.core.PApplet#sketchFullScreen()
     */
    public final boolean sketchFullScreen() {
        return parent.sketchFullScreen();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>sketchHeight</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @return
     * @see processing.core.PApplet#sketchHeight()
     */
    public final int sketchHeight() {
        return parent.sketchHeight();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>sketchOutputPath</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @return
     * @see processing.core.PApplet#sketchOutputPath()
     */
    public final String sketchOutputPath() {
        return parent.sketchOutputPath();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>sketchOutputStream</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @return
     * @see processing.core.PApplet#sketchOutputStream()
     */
    public final OutputStream sketchOutputStream() {
        return parent.sketchOutputStream();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>sketchPath</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @return
     * @see processing.core.PApplet#sketchPath()
     */
    public String sketchPath() {
        return parent.sketchPath();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>sketchPath</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aWhere
     * @return
     * @see processing.core.PApplet#sketchPath(java.lang.String)
     */
    public String sketchPath(final String aWhere) {
        return parent.sketchPath(aWhere);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>sketchPixelDensity</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @return
     * @see processing.core.PApplet#sketchPixelDensity()
     */
    public final int sketchPixelDensity() {
        return parent.sketchPixelDensity();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>sketchRenderer</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @return
     * @see processing.core.PApplet#sketchRenderer()
     */
    public final String sketchRenderer() {
        return parent.sketchRenderer();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>sketchSmooth</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @return
     * @see processing.core.PApplet#sketchSmooth()
     */
    public final int sketchSmooth() {
        return parent.sketchSmooth();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>sketchWidth</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @return
     * @see processing.core.PApplet#sketchWidth()
     */
    public final int sketchWidth() {
        return parent.sketchWidth();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>sketchWindowColor</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @return
     * @see processing.core.PApplet#sketchWindowColor()
     */
    public final int sketchWindowColor() {
        return parent.sketchWindowColor();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>smooth</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#smooth()
     */
    public void smooth() {
        parent.smooth();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>smooth</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aLevel
     * @see processing.core.PApplet#smooth(int)
     */
    public void smooth(final int aLevel) {
        parent.smooth(aLevel);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>specular</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aGray
     * @see processing.core.PApplet#specular(float)
     */
    public void specular(final float aGray) {
        parent.specular(aGray);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>specular</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aV1
     * @param aV2
     * @param aV3
     * @see processing.core.PApplet#specular(float, float, float)
     */
    public void specular(final float aV1, final float aV2, final float aV3) {
        parent.specular(aV1, aV2, aV3);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>specular</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aRgb
     * @see processing.core.PApplet#specular(int)
     */
    public void specular(final int aRgb) {
        parent.specular(aRgb);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>sphere</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aR
     * @see processing.core.PApplet#sphere(float)
     */
    public void sphere(final float aR) {
        parent.sphere(aR);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>sphereDetail</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aRes
     * @see processing.core.PApplet#sphereDetail(int)
     */
    public void sphereDetail(final int aRes) {
        parent.sphereDetail(aRes);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>sphereDetail</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aUres
     * @param aVres
     * @see processing.core.PApplet#sphereDetail(int, int)
     */
    public void sphereDetail(final int aUres, final int aVres) {
        parent.sphereDetail(aUres, aVres);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>spotLight</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aV1
     * @param aV2
     * @param aV3
     * @param aX
     * @param aY
     * @param aZ
     * @param aNx
     * @param aNy
     * @param aNz
     * @param aAngle
     * @param aConcentration
     * @see processing.core.PApplet#spotLight(float, float, float, float, float, float, float, float, float, float,
     *      float)
     */
    public void spotLight(final float aV1, final float aV2, final float aV3, final float aX, final float aY,
        final float aZ, final float aNx, final float aNy, final float aNz, final float aAngle,
        final float aConcentration) {
        parent.spotLight(aV1, aV2, aV3, aX, aY, aZ, aNx, aNy, aNz, aAngle, aConcentration);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>start</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#start()
     */
    public void start() {
        parent.start();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>stop</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#stop()
     */
    public void stop() {
        parent.stop();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>stroke</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aGray
     * @see processing.core.PApplet#stroke(float)
     */
    public void stroke(final float aGray) {
        parent.stroke(aGray);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>stroke</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aGray
     * @param aAlpha
     * @see processing.core.PApplet#stroke(float, float)
     */
    public void stroke(final float aGray, final float aAlpha) {
        parent.stroke(aGray, aAlpha);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>stroke</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aV1
     * @param aV2
     * @param aV3
     * @see processing.core.PApplet#stroke(float, float, float)
     */
    public void stroke(final float aV1, final float aV2, final float aV3) {
        parent.stroke(aV1, aV2, aV3);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>stroke</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aV1
     * @param aV2
     * @param aV3
     * @param aAlpha
     * @see processing.core.PApplet#stroke(float, float, float, float)
     */
    public void stroke(final float aV1, final float aV2, final float aV3, final float aAlpha) {
        parent.stroke(aV1, aV2, aV3, aAlpha);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>stroke</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aRgb
     * @see processing.core.PApplet#stroke(int)
     */
    public void stroke(final int aRgb) {
        parent.stroke(aRgb);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>stroke</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aRgb
     * @param aAlpha
     * @see processing.core.PApplet#stroke(int, float)
     */
    public void stroke(final int aRgb, final float aAlpha) {
        parent.stroke(aRgb, aAlpha);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>strokeCap</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aCap
     * @see processing.core.PApplet#strokeCap(int)
     */
    public void strokeCap(final int aCap) {
        parent.strokeCap(aCap);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>strokeJoin</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aJoin
     * @see processing.core.PApplet#strokeJoin(int)
     */
    public void strokeJoin(final int aJoin) {
        parent.strokeJoin(aJoin);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>strokeWeight</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aWeight
     * @see processing.core.PApplet#strokeWeight(float)
     */
    public void strokeWeight(final float aWeight) {
        parent.strokeWeight(aWeight);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>style</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aS
     * @see processing.core.PApplet#style(processing.core.PStyle)
     */
    public void style(final PStyle aS) {
        parent.style(aS);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>text</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aC
     * @param aX
     * @param aY
     * @see processing.core.PApplet#text(char, float, float)
     */
    public void text(final char aC, final float aX, final float aY) {
        parent.text(aC, aX, aY);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>text</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aC
     * @param aX
     * @param aY
     * @param aZ
     * @see processing.core.PApplet#text(char, float, float, float)
     */
    public void text(final char aC, final float aX, final float aY, final float aZ) {
        parent.text(aC, aX, aY, aZ);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>text</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aChars
     * @param aStart
     * @param aStop
     * @param aX
     * @param aY
     * @see processing.core.PApplet#text(char[], int, int, float, float)
     */
    public void text(final char[] aChars, final int aStart, final int aStop, final float aX, final float aY) {
        parent.text(aChars, aStart, aStop, aX, aY);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>text</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aChars
     * @param aStart
     * @param aStop
     * @param aX
     * @param aY
     * @param aZ
     * @see processing.core.PApplet#text(char[], int, int, float, float, float)
     */
    public void text(final char[] aChars, final int aStart, final int aStop, final float aX, final float aY,
        final float aZ) {
        parent.text(aChars, aStart, aStop, aX, aY, aZ);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>text</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aNum
     * @param aX
     * @param aY
     * @see processing.core.PApplet#text(float, float, float)
     */
    public void text(final float aNum, final float aX, final float aY) {
        parent.text(aNum, aX, aY);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>text</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aNum
     * @param aX
     * @param aY
     * @param aZ
     * @see processing.core.PApplet#text(float, float, float, float)
     */
    public void text(final float aNum, final float aX, final float aY, final float aZ) {
        parent.text(aNum, aX, aY, aZ);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>text</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aNum
     * @param aX
     * @param aY
     * @see processing.core.PApplet#text(int, float, float)
     */
    public void text(final int aNum, final float aX, final float aY) {
        parent.text(aNum, aX, aY);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>text</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aNum
     * @param aX
     * @param aY
     * @param aZ
     * @see processing.core.PApplet#text(int, float, float, float)
     */
    public void text(final int aNum, final float aX, final float aY, final float aZ) {
        parent.text(aNum, aX, aY, aZ);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>text</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aStr
     * @param aX
     * @param aY
     * @see processing.core.PApplet#text(java.lang.String, float, float)
     */
    public void text(final String aStr, final float aX, final float aY) {
        parent.text(aStr, aX, aY);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>text</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aStr
     * @param aX
     * @param aY
     * @param aZ
     * @see processing.core.PApplet#text(java.lang.String, float, float, float)
     */
    public void text(final String aStr, final float aX, final float aY, final float aZ) {
        parent.text(aStr, aX, aY, aZ);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>text</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aStr
     * @param aX1
     * @param aY1
     * @param aX2
     * @param aY2
     * @see processing.core.PApplet#text(java.lang.String, float, float, float, float)
     */
    public void text(final String aStr, final float aX1, final float aY1, final float aX2, final float aY2) {
        parent.text(aStr, aX1, aY1, aX2, aY2);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>textAlign</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aAlignX
     * @see processing.core.PApplet#textAlign(int)
     */
    public void textAlign(final int aAlignX) {
        parent.textAlign(aAlignX);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>textAlign</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aAlignX
     * @param aAlignY
     * @see processing.core.PApplet#textAlign(int, int)
     */
    public void textAlign(final int aAlignX, final int aAlignY) {
        parent.textAlign(aAlignX, aAlignY);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>textAscent</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @return
     * @see processing.core.PApplet#textAscent()
     */
    public float textAscent() {
        return parent.textAscent();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>textDescent</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @return
     * @see processing.core.PApplet#textDescent()
     */
    public float textDescent() {
        return parent.textDescent();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>textFont</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aWhich
     * @see processing.core.PApplet#textFont(processing.core.PFont)
     */
    public void textFont(final PFont aWhich) {
        parent.textFont(aWhich);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>textFont</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aWhich
     * @param aSize
     * @see processing.core.PApplet#textFont(processing.core.PFont, float)
     */
    public void textFont(final PFont aWhich, final float aSize) {
        parent.textFont(aWhich, aSize);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>textLeading</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aLeading
     * @see processing.core.PApplet#textLeading(float)
     */
    public void textLeading(final float aLeading) {
        parent.textLeading(aLeading);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>textMode</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aMode
     * @see processing.core.PApplet#textMode(int)
     */
    public void textMode(final int aMode) {
        parent.textMode(aMode);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>textSize</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aSize
     * @see processing.core.PApplet#textSize(float)
     */
    public void textSize(final float aSize) {
        parent.textSize(aSize);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>texture</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aImage
     * @see processing.core.PApplet#texture(processing.core.PImage)
     */
    public void texture(final PImage aImage) {
        parent.texture(aImage);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>textureMode</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aMode
     * @see processing.core.PApplet#textureMode(int)
     */
    public void textureMode(final int aMode) {
        parent.textureMode(aMode);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>textureWrap</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aWrap
     * @see processing.core.PApplet#textureWrap(int)
     */
    public void textureWrap(final int aWrap) {
        parent.textureWrap(aWrap);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>textWidth</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aC
     * @return
     * @see processing.core.PApplet#textWidth(char)
     */
    public float textWidth(final char aC) {
        return parent.textWidth(aC);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>textWidth</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aChars
     * @param aStart
     * @param aLength
     * @return
     * @see processing.core.PApplet#textWidth(char[], int, int)
     */
    public float textWidth(final char[] aChars, final int aStart, final int aLength) {
        return parent.textWidth(aChars, aStart, aLength);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>textWidth</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aStr
     * @return
     * @see processing.core.PApplet#textWidth(java.lang.String)
     */
    public float textWidth(final String aStr) {
        return parent.textWidth(aStr);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>thread</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aName
     * @see processing.core.PApplet#thread(java.lang.String)
     */
    public void thread(final String aName) {
        parent.thread(aName);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>tint</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aGray
     * @see processing.core.PApplet#tint(float)
     */
    public void tint(final float aGray) {
        parent.tint(aGray);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>tint</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aGray
     * @param aAlpha
     * @see processing.core.PApplet#tint(float, float)
     */
    public void tint(final float aGray, final float aAlpha) {
        parent.tint(aGray, aAlpha);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>tint</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aV1
     * @param aV2
     * @param aV3
     * @see processing.core.PApplet#tint(float, float, float)
     */
    public void tint(final float aV1, final float aV2, final float aV3) {
        parent.tint(aV1, aV2, aV3);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>tint</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aV1
     * @param aV2
     * @param aV3
     * @param aAlpha
     * @see processing.core.PApplet#tint(float, float, float, float)
     */
    public void tint(final float aV1, final float aV2, final float aV3, final float aAlpha) {
        parent.tint(aV1, aV2, aV3, aAlpha);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>tint</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aRgb
     * @see processing.core.PApplet#tint(int)
     */
    public void tint(final int aRgb) {
        parent.tint(aRgb);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>tint</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aRgb
     * @param aAlpha
     * @see processing.core.PApplet#tint(int, float)
     */
    public void tint(final int aRgb, final float aAlpha) {
        parent.tint(aRgb, aAlpha);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>translate</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aX
     * @param aY
     * @see processing.core.PApplet#translate(float, float)
     */
    public void translate(final float aX, final float aY) {
        parent.translate(aX, aY);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>translate</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aX
     * @param aY
     * @param aZ
     * @see processing.core.PApplet#translate(float, float, float)
     */
    public void translate(final float aX, final float aY, final float aZ) {
        parent.translate(aX, aY, aZ);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>triangle</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aX1
     * @param aY1
     * @param aX2
     * @param aY2
     * @param aX3
     * @param aY3
     * @see processing.core.PApplet#triangle(float, float, float, float, float, float)
     */
    public void triangle(final float aX1, final float aY1, final float aX2, final float aY2, final float aX3,
        final float aY3) {
        parent.triangle(aX1, aY1, aX2, aY2, aX3, aY3);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>unregisterMethod</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aName
     * @param aTarget
     * @see processing.core.PApplet#unregisterMethod(java.lang.String, java.lang.Object)
     */
    public void unregisterMethod(final String aName, final Object aTarget) {
        parent.unregisterMethod(aName, aTarget);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>updatePixels</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @see processing.core.PApplet#updatePixels()
     */
    public void updatePixels() {
        parent.updatePixels();
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>updatePixels</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aX1
     * @param aY1
     * @param aX2
     * @param aY2
     * @see processing.core.PApplet#updatePixels(int, int, int, int)
     */
    public void updatePixels(final int aX1, final int aY1, final int aX2, final int aY2) {
        parent.updatePixels(aX1, aY1, aX2, aY2);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>vertex</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aX
     * @param aY
     * @see processing.core.PApplet#vertex(float, float)
     */
    public void vertex(final float aX, final float aY) {
        parent.vertex(aX, aY);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>vertex</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aX
     * @param aY
     * @param aZ
     * @see processing.core.PApplet#vertex(float, float, float)
     */
    public void vertex(final float aX, final float aY, final float aZ) {
        parent.vertex(aX, aY, aZ);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>vertex</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aX
     * @param aY
     * @param aU
     * @param aV
     * @see processing.core.PApplet#vertex(float, float, float, float)
     */
    public void vertex(final float aX, final float aY, final float aU, final float aV) {
        parent.vertex(aX, aY, aU, aV);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>vertex</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aX
     * @param aY
     * @param aZ
     * @param aU
     * @param aV
     * @see processing.core.PApplet#vertex(float, float, float, float, float)
     */
    public void vertex(final float aX, final float aY, final float aZ, final float aU, final float aV) {
        parent.vertex(aX, aY, aZ, aU, aV);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Delegate method for the <code>vertex</code> method in <code>PApplet</code> class</DD>
     * <DT>Date:</DT>
     * <DD>Sep 1, 2017</DD>
     * </DL>
     *
     * @param aV
     * @see processing.core.PApplet#vertex(float[])
     */
    public void vertex(final float[] aV) {
        parent.vertex(aV);
    }

}
