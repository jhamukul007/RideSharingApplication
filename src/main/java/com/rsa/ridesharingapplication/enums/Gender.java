package com.rsa.ridesharingapplication.enums;

import lombok.Getter;

@Getter
public enum Gender {
    MALE("M"), FEMALE("F");

    Gender(String gender) {
        this.gender = gender;
    }

    private String gender;

}
