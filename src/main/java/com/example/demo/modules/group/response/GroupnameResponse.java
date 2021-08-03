package com.example.demo.modules.group.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupnameResponse {
    private String name;

    public GroupnameResponse(String name) {
        this.name = name;
    }
}
