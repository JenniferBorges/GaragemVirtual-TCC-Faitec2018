package com.academico.garagem.model.enumeration;

public enum ENotification {

    SUCCESS("success"),
    ERROR("error");

    private String type;

    private ENotification(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
