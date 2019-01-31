package com.academico.garagem.model.entity;

import com.academico.garagem.model.enumeration.ENotification;

public class Notification {

    private ENotification type;
    private String text;
    private Integer time;

    public String getType() {
        return type.getType();
    }

    public void setType(ENotification type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

}
