package com.fb.facebook.utils;

import lombok.Getter;

@Getter
public enum Gender {
    MALE("Male"),
    OTHERS("Prefer not to say"),
    FEMALE("Female");

    private final String type;
    Gender(String type) {
        this.type = type;
    }
}
