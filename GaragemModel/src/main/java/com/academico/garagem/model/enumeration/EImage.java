package com.academico.garagem.model.enumeration;

import java.util.stream.Stream;

public enum EImage {

    USER_PHOTO(1, "user"),
    USER_DOC_FRONT(2, "user"),
    USER_DOC_BACK(3, "user"),
    USER_DOC_HAB(4, "user"),
    VEHICLE_PHOTO(5, "vehicle"),
    VEHICLE_DOC(6, "vehicle"),
    GARAGE_PHOTO(7, "garage");

    private final int type;
    private final String table;

    private EImage(int type, String table) {
        this.type = type;
        this.table = table;
    }

    public int getType() {
        return type;
    }

    public String getTable() {
        return table;
    }

    public static EImage valueOf(int type) {
        return Stream.of(values())
                .filter(e -> e.getType() == type)
                .findFirst().orElse(null);
    }

}
