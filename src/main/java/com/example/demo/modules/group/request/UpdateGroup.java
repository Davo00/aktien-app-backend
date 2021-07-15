package com.example.demo.modules.group.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UpdateGroup {
    private String name;
    private List<String> usernames;
}
