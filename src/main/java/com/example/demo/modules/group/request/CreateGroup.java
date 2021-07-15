package com.example.demo.modules.group.request;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateGroup {

    private String name;
    private List<String> usernames;

}
