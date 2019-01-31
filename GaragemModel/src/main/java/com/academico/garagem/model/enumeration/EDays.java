package com.academico.garagem.model.enumeration;

import java.util.stream.Stream;

public enum EDays {

    SUNDAY(1, "Domingo"),
    MONDAY(2, "Segunda"),
    TUESDAY(3, "Terça"),
    WEDNESDAY(4, "Quarta"),
    THURSDAY(5, "Quinta"),
    FRIDAY(6, "Sexta"),
    SATURDAY(7, "Sábado");

    private final Integer value;
    private final String name;

    private EDays(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static EDays valueOf(int type) {
        return Stream.of(values())
                .filter(e -> e.getValue() == type)
                .findFirst().orElse(null);
    }

}
