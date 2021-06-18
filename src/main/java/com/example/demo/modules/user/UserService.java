package com.example.demo.modules.user;

import com.example.demo.modules.group.Group;
import com.example.demo.utils.NotFoundException;

import java.util.List;

public interface UserService {
    User createUser(User request);

    List<Group> getAllGroupsOfUser(long userId)throws NotFoundException;
}
