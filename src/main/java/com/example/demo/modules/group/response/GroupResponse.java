package com.example.demo.modules.group.response;

import com.example.demo.modules.group.Group;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GroupResponse {

    private Long id;
    private String name;
    private List<String> myUsers;

    public GroupResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public GroupResponse(Group group) {
        this(group.getId(), group.getName());

        if (group.getMyUsers() != null && !group.getMyUsers().isEmpty()) {
            myUsers = new ArrayList<>();
            group.getMyUsers().forEach(user -> myUsers.add(user.getUsername()));
        }
    }
}
