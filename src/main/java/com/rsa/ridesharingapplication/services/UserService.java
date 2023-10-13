package com.rsa.ridesharingapplication.services;

import com.rsa.ridesharingapplication.enums.Gender;
import com.rsa.ridesharingapplication.exceptions.UserFoundException;
import com.rsa.ridesharingapplication.logging.Logger;
import com.rsa.ridesharingapplication.models.User;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserService {
    private final List<User> userList;
    private final Logger logger;

    public UserService(Logger logger) {
        this.logger = logger;
        this.userList = new ArrayList<>();
    }

    public User registerUser(@NonNull String name, @NonNull Long phone, @NonNull Gender gender, @NonNull int age) {
        if (findByPhone(phone).isPresent())
            throw new UserFoundException();
        User user = User.builder()
                .name(name)
                .phone(phone)
                .gender(gender)
                .age(age)
                .build();
        userList.add(user);
        logger.log(String.format("User with name %s has been created ", name));
        return user;
    }

    public Optional<User> findByPhone(Long phone) {
        return userList.stream().filter(user -> Objects.deepEquals(phone, user.getPhone())).findFirst();
    }
}
