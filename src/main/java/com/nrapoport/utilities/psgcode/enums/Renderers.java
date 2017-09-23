/**
 * @author ubuntu - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 4, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapoport.utilities.psgcode.enums;

import java.util.List;

import processing.core.PConstants;

// public static final int SETTINGS_COUNT = 41;
/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>a convenience enum for renderers from Processing</DD>
 * <DT>Date:</DT>
 * <DD>Sep 16, 2017</DD>
 * </DL>
 *
 * @author ubuntu - Nick Rapoport
 *
 */
public enum Renderers implements IAltNameAndIdAware {
    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>JAVA2D (Renderers) =</DD>
     * </DL>
     */
    JAVA2D(0, PConstants.JAVA2D),
    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>P2D (Renderers) =</DD>
     * </DL>
     */
    P2D(0, PConstants.P2D),
    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>P3D (Renderers) =</DD>
     * </DL>
     */
    P3D(0, PConstants.P3D),
    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>OPENGL (Renderers) =</DD>
     * </DL>
     * 
     * @deprecated
     */
    OPENGL(0, PConstants.OPENGL),
    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>FX2D (Renderers) =</DD>
     * </DL>
     */
    FX2D(0, PConstants.FX2D),
    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>PDF (Renderers) =</DD>
     * </DL>
     */
    PDF(0, PConstants.PDF),
    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>SVG (Renderers) =</DD>
     * </DL>
     */
    SVG(0, PConstants.SVG),
    /**
     * <DL>
     * <DT>Field</DT>
     * <DD>DXF (Renderers) =</DD>
     * </DL>
     */
    DXF(0, PConstants.SVG);

    private volatile static DDEnum<Renderers> ddEnum = DDEnum.getNewInstance(Renderers.class);

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Return the ControlMode with the passed id</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param in
     * @return
     */
    public static Renderers getById(final int in) {
        return ddEnum.getById(in);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Return the ControlMode with the passed ordinal</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param in
     * @return
     */
    public static Renderers getByOrdinal(final int in) {
        return ddEnum.getByOrdinal(in);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Return the ControlMode with the passed nsme ot altName</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param in
     * @return
     */
    public static Renderers getByString(final String in) {
        return ddEnum.getByString(in);
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Return the ControlMode's list of altNames (or names if altName is null)</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param in
     * @return
     */
    public static List<String> getDropdownStrings() {
        return ddEnum.getDropdownStrings();
    }

    private final int id;

    private final String altName;

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>ControlMode Constructor</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param idIn
     * @param aAltName
     */
    Renderers(final int idIn, final String aAltName) {
        id = idIn;
        altName = aAltName;
    }

    /** {@inheritDoc} */
    @Override
    public String altName() {
        return altName;
    }

    /** {@inheritDoc} */
    @Override
    public int getId() {
        return id;
    }
}
