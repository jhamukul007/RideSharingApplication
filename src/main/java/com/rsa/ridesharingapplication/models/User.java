package com.rsa.ridesharingapplication.models;

import com.rsa.ridesharingapplication.enums.Gender;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User extends BaseEntity{
    private String name;
    private Long phone;
    private Gender gender;
    private Integer age;
}
