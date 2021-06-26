package com.example.demo.modules.user;

import com.example.demo.modules.group.Group;
import com.example.demo.modules.group.GroupRepository;
import com.example.demo.utils.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private GroupRepository groupRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public User createUser(User request) throws UsernameReservedException {
        if (isUsernameReserved(request.getUsername())) {
            throw new UsernameReservedException();
        }
        User user = new User(request.getUsername(), request.getEmail());
        userRepository.save(user);
        return user;
    }

    @Override
    public List<User> getUsersByGroup(Group group) {
        return userRepository.findAllByJoinedGroups(group);
    }

    @Override
    public List<Group> getAllGroupsOfUser(long userId)throws NotFoundException{
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User could not be found!"));
        List<Group> allGroups = groupRepository.findAll();
        List<Group> myGroups = new ArrayList<>();
        for (Group group: allGroups){
            for (User userInGroup: group.getMyUsers()){
                if(userInGroup== user){
                    myGroups.add(group);
                    break;
                }
            }
        }

        return myGroups;
    }

    @Override
    public User getCurrentUser() {
        return userRepository.findById(1L).get(); //TODO implement real method with JWT
    }

    @Override
    public void updateUser(User updatedUser) throws UsernameReservedException {

        if (isUsernameReserved(updatedUser.getUsername())) {
            throw new UsernameReservedException();
        }
        getCurrentUser().setEmail(updatedUser.getEmail());
        getCurrentUser().setUsername(updatedUser.getUsername());

        userRepository.save(updatedUser);
    }

    @Override
    public void deleteUser() {
        User user = getCurrentUser();
        userRepository.delete(user);
    }

    private boolean isUsernameReserved(String username) {
        return userRepository.findByUsername(username) != null;
    }

    static class UsernameReservedException extends Exception {
        UsernameReservedException() {
            super("The username is already taken");
        }
    }
}
