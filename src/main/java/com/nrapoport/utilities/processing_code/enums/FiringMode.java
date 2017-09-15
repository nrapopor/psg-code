package com.nrapoport.utilities.processing_code.enums;

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

    public static FiringMode getByString(final String in) {
        return ddEnum.getByString(in);
    }

    public static List<String> getDropdownStrings() {
        return ddEnum.getDropdownStrings();
    }

    private final int id;

    private final String altName;

    FiringMode(final int idIn, final String aAltName) {
        id = idIn;
        altName = aAltName == null ? name() : aAltName;
        ;
    }

    /** {@inheritDoc} */
    @Override
    public String altName() {
        return altName;
    }

    @Override
    public int getId() {
        return id;
    }
}
