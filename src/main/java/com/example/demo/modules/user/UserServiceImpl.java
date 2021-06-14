package com.example.demo.modules.user;

import com.example.demo.modules.group.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User createUser(User request) {
        return new User(request.getUsername(), request.getEmail());
    }

    @Override
    public List<User> getUsersByGroup(Group request) {
        return userRepository.findAllByJoinedGroups(request);
    }
}
