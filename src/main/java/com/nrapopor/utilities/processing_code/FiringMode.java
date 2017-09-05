package com.nrapopor.utilities.processing_code;

import java.util.List;

public enum FiringMode implements IAltNameAndIdAware {
    Automatic(1, null), SemiAuto(3, "Semi-Automatic");

    private volatile static DDEnum<FiringMode> ddEnum = DDEnum.getNewInstance(FiringMode.class);

    public static FiringMode getById(final int in) {
        return ddEnum.getById(in);
    }

    public static FiringMode getByOrdinal(final int in) {
        return ddEnum.getByOrdinal(in);
    }

    public static int getByString(final String in) {
        return ddEnum.getByString(in);
    }

    public static List<String> getDropdownStrings() {
        return ddEnum.getDropdownStrings();
    }

    private final int id;

    private final String altName;

    FiringMode(final int idIn, final String eAltName) {
        id = idIn;
        altName = eAltName;
    }

    /** {@inheritDoc} */
    @Override
    public String altName() {
        return altName == null || altName.isEmpty() ? name() : altName;
    }

    @Override
    public int getId() {
        return id;
    }
}
