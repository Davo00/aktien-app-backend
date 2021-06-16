package com.example.demo.modules.user;

import com.example.demo.modules.group.Group;

import java.util.List;

public interface UserService {
    User createUser(User request);
    List<User> getUsersByGroup(Group group);
}
