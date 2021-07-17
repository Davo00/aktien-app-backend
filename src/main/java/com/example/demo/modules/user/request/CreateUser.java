package com.example.demo.modules.user.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
public class CreateUser {
    private String username;
    @Email
    private String email;
    private String password;

    public CreateUser(String username, String password, @Email String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
