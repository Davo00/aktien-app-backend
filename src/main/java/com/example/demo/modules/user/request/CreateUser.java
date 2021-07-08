package com.example.demo.modules.user.request;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import java.util.List;

@Getter
@Setter
public class CreateUser {
    private String username;
    @Email
    private String email;

    public CreateUser(String username, @Email String email) {
        this.username = username;
        this.email = email;
    }
}
