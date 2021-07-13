package com.example.demo.modules.user;

import com.example.demo.modules.group.Group;
import com.example.demo.modules.group.GroupRepository;
import com.example.demo.modules.security.JwtTokenUtil;
import com.example.demo.modules.group.response.GroupResponse;
import com.example.demo.modules.user.request.CreateUser;
import com.example.demo.modules.user.response.UserResponse;
import com.example.demo.utils.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private GroupRepository groupRepository;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, GroupRepository groupRepository, JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public User createUser(User request) throws UsernameReservedException {
        if (isUsernameReserved(request.getUsername())) {
            throw new UsernameReservedException();
        }
        User user = new User(request.getUsername(), request.getPassword(), request.getEmail());
        userRepository.save(user);
        return user;
    public UserResponse createUser(CreateUser request) {
        User user = new User(request.getUsername(), request.getEmail());
        user = userRepository.save(user);
        return new UserResponse(user);
    }

        @Override
        public List<GroupResponse> getAllGroupsOfUser ( long userId)throws NotFoundException {
            User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with the id " + userId + " could not be found!"));
            List<Group> allGroups = groupRepository.findAll();
            List<Group> myGroups = new ArrayList<>();
            for (Group group : allGroups) {
                for (User userInGroup : group.getMyUsers()) {
                    if (userInGroup == user) {
                        myGroups.add(group);
                        break;
                    }
                }
            }

            List<GroupResponse> groupResponseList = new ArrayList<>();
            for (Group group : myGroups) {
                groupResponseList.add(new GroupResponse(group));
            }
            return groupResponseList;
        }


    @Override
    public User getCurrentUser(String token) {
        String username = jwtTokenUtil.getUsername(token);
        return userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User: " + username + "not found"));
    }

    @Override
    public void updateUser(User updatedUser, String token) throws UsernameReservedException {

        if (isUsernameReserved(updatedUser.getUsername())) {
            throw new UsernameReservedException();
        }
        User currentUser = getCurrentUser(token);
        currentUser.setEmail(updatedUser.getEmail());
        currentUser.setUsername(updatedUser.getUsername());

        userRepository.save(updatedUser);
    }

    @Override
    public void deleteUser(String token) {
        User user = getCurrentUser(token);
        userRepository.delete(user);
    }

    private boolean isUsernameReserved(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    static class UsernameReservedException extends Exception {
        UsernameReservedException() {
            super("The username is already taken");
        }
    }
}
