package com.nrapopor.utilities.processing_code;

import java.util.List;

public enum Effects implements IAltNameAndIdAware {
    Opaque(0, null), Transparent(1, null), Negative(2, null), NegativeAndTransparent(3, "Negative & Transparent");

    private volatile static DDEnum<Effects> ddEnum = DDEnum.getNewInstance(Effects.class);

    public static Effects getById(final int in) {
        return ddEnum.getById(in);
    }

    public static Effects getByOrdinal(final int in) {
        return ddEnum.getByOrdinal(in);
    }

    public static int getByString(final String in) {
        return ddEnum.getByString(in);
    }

    public static List<String> getDropdownStrings() {
        return ddEnum.getDropdownStrings();
    }

    public static Effects radioEffect(final Effects effect) {
        int newEffectId = effect.getId() + 1;
        if (newEffectId >= Effects.values().length) {
            newEffectId = 0; //rollover
        }
        return Effects.getById(newEffectId);
    }

    private final int id;

    private final String altName;

    Effects(final int idIn, final String eAltName) {
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
