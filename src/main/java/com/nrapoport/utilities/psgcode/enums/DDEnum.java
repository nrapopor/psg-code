/**
 * @author ubuntu - Nick Rapoport
 * @copyright Copyright 2017 ( Sep 3, 2017 ) Nick Rapoport all rights reserved.
 */
package com.nrapoport.utilities.psgcode.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * <DL>
 * <DT>Description:</DT>
 * <DD>a helper class for enums</DD>
 * <DT>Date:</DT>
 * <DD>Sep 16, 2017</DD>
 * </DL>
 *
 * @author ubuntu - Nick Rapoport
 *
 * @param <T>
 */
public class DDEnum<T extends Enum<T> & IAltNameAndIdAware> {

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>return a new instance of this class for an appropriate enum</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     *
     * @param <T>
     * @param inT
     * @return
     */
    public static <T extends Enum<T> & IAltNameAndIdAware> DDEnum<T> getNewInstance(final Class<T> inT) {
        return new DDEnum<>(inT);
    }

    private final Class<T> enumType;

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>DDEnum Constructor</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     * 
     * @param enumTypeIn
     */
    private DDEnum(final Class<T> enumTypeIn) {
        enumType = enumTypeIn;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>Returns the enum value that has the passed id (may or may not be the same as ordinal. This is useful when you
     * need to use the enum as a map for names to specific integers like error codes.</DD>
     * <DT>Date:</DT>
     * <DD>Sep 12, 2017</DD>
     * </DL>
     *
     * @param id
     * @return
     */
    public T getById(final int id) {
        final T[] enumConstants = enumType.getEnumConstants(); // Similar to e.g. Day.values();
        for (final T t : enumConstants) {
            if (t.getId() == id) {
                return t;
            }
        }
        return enumConstants[0];
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>return the enum represented by the passed ordinal value. if the value passed is outside of the enum size or
     * less than zero, then it will be adjust to the maximum ordinal or zero, respectively</DD>
     * <DT>Date:</DT>
     * <DD>Sep 12, 2017</DD>
     * </DL>
     *
     * @param in
     * @return
     */
    public T getByOrdinal(final int in) {
        final T[] enumConstants = enumType.getEnumConstants(); // Similar to e.g. Day.values();
        if (in < 0) {
            return enumConstants[0];
        } else if (in >= enumConstants.length) {
            return enumConstants[enumConstants.length - 1];
        }
        return enumConstants[in];
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>This method acts as equivalent of the valueOf method of the Enum class, but it is not case sensitive,
     * <code>trim()</code>s the passed argument value and will also consider the altName (if any). Will return the first
     * enum value if not found.</DD>
     * <DT>Date:</DT>
     * <DD>Sep 12, 2017</DD>
     * </DL>
     *
     * @param in
     * @return
     */
    public T getByString(final String in) {
        T result;
        final String trimmed = in.trim();
        final T[] enumConstants = enumType.getEnumConstants(); // Similar to e.g. Day.values();
        result = enumConstants[0];
        for (final T t : enumConstants) {
            if (t.name().equalsIgnoreCase(trimmed) || t.altName().equalsIgnoreCase(trimmed)) {
                return t;
            }
        }
        return result;
    }

    /**
     * <DL>
     * <DT>Description:</DT>
     * <DD>return a list of altNames (or names if the altName is null)</DD>
     * <DT>Date:</DT>
     * <DD>Sep 16, 2017</DD>
     * </DL>
     * 
     * @return
     */
    public List<String> getDropdownStrings() {
        final T[] enumConstants = enumType.getEnumConstants(); // Similar to e.g. Day.values();
        final List<String> strings = new ArrayList<>(enumConstants.length);

        for (final T t : enumConstants) {
            strings.add(t.altName() == null ? t.name() : t.altName());
        }
        return strings;
    }

}
