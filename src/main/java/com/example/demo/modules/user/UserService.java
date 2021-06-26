package com.example.demo.modules.user;

import com.example.demo.modules.group.Group;
import com.example.demo.utils.NotFoundException;

import java.util.List;

public interface UserService {
    User createUser(User request);

    List<Group> getAllGroupsOfUser(long userId)throws NotFoundException;

    User createUser(User request) throws UserServiceImpl.UsernameReservedException;

    List<User> getUsersByGroup(Group group);

    User getCurrentUser();

    void updateUser(User updatedUser) throws UserServiceImpl.UsernameReservedException;

    void deleteUser();
}
