package com.golapadeok.fluo.domain.tag.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum ColorCode {
    RED("red"),
    ORANGE("orange"),
    YELLOW("yellow"),
    GREEN("green"),
    INDIGO("indigo"),
    BLUE("blue"),
    PURPLE("purple"),
    PINK("pink");

    private final String color;

    ColorCode(String color) {
        this.color = color;
    }

    @JsonCreator
    public static ColorCode from(String s) {
        return ColorCode.valueOf(s.toUpperCase());
    }
}
