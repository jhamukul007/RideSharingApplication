package com.rsa.ridesharingapplication.models;

import lombok.Data;
import lombok.Getter;

import java.util.UUID;
@Data
public class BaseEntity {
    private String id;

    public BaseEntity() {
        this.id = UUID.randomUUID().toString();
    }
}
