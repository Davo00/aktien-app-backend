package com.example.demo.modules.user.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginResponse {
    private String jwt;

    public UserLoginResponse(String jwt) {
        this.jwt = jwt;
    }
}
