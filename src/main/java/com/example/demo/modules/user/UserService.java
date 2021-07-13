package com.example.demo.modules.user;

import com.example.demo.modules.group.response.GroupResponse;
import com.example.demo.modules.user.request.CreateUser;
import com.example.demo.modules.user.response.UserResponse;
import com.example.demo.utils.NotFoundException;

import java.util.List;

public interface UserService {
    List<GroupResponse> getAllGroupsOfUser(long userId) throws NotFoundException;

    UserResponse createUser(CreateUser request) throws UserServiceImpl.UsernameReservedException;

    User getCurrentUser(String token);

    void updateUser(User updatedUser, String token) throws UserServiceImpl.UsernameReservedException;

    void deleteUser(String token);
}
