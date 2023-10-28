package com.fb.facebook.models;

import com.fb.facebook.utils.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
public class User {
    private String firstName;
    private String lastName;
    private String password;
    private String username;
    private String emailAddress;
    private int totalNumberOfPosts;
    private UUID userId;
    private Gender gender;

    public String fullName() {
        return getFirstName() + " " + getLastName();
    }
}