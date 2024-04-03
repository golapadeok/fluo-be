package com.golapadeok.fluo.domain.task.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum LabelColor {
    RED("red"),
    ORANGE("orange"),
    YELLOW("yellow"),
    GREEN("green"),
    INDIGO("indigo"),
    BLUE("blue"),
    PURPLE("purple"),
    PINK("pink");

    private final String label;

    LabelColor(String label) {
        this.label = label;
    }

    @JsonCreator
    public static LabelColor from(String s) {
        return LabelColor.valueOf(s.toUpperCase());
    }
}


