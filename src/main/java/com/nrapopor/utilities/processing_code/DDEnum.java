package com.nrapopor.utilities.processing_code;

import java.util.ArrayList;
import java.util.List;

public class DDEnum<T extends Enum<T> & IAltNameAndIdAware> {

    public static <T extends Enum<T> & IAltNameAndIdAware> DDEnum<T> getNewInstance(final Class<T> inT) {
        return new DDEnum<>(inT);
    }

    private final Class<T> enumType;

    private DDEnum(final Class<T> enumTypeIn) {
        enumType = enumTypeIn;
    }

    public T getById(final int id) {
        final T[] enumConstants = enumType.getEnumConstants(); // Similar to e.g. Day.values();
        for (final T t : enumConstants) {
            if (t.getId() == id) {
                return t;
            }
        }
        return enumConstants[0];
    }

    public T getByOrdinal(final int in) {
        final T[] enumConstants = enumType.getEnumConstants(); // Similar to e.g. Day.values();
        if (in < 0) {
            return enumConstants[0];
        } else if (in >= enumConstants.length) {
            return enumConstants[enumConstants.length - 1];
        }
        return enumConstants[in];
    }

    public int getByString(final String in) {
        int result;
        final T[] enumConstants = enumType.getEnumConstants(); // Similar to e.g. Day.values();
        result = enumConstants[0].getId();
        for (final T t : enumConstants) {
            if (t.name().equalsIgnoreCase(in) || t.altName().equalsIgnoreCase(in)) {
                return t.getId();
            }
        }
        return result;
    }

    public List<String> getDropdownStrings() {
        final T[] enumConstants = enumType.getEnumConstants(); // Similar to e.g. Day.values();
        final List<String> strings = new ArrayList<>(enumConstants.length);

        for (final T t : enumConstants) {
            strings.add(t.altName() == null ? t.name() : t.altName());
        }
        return strings;
    }

}
