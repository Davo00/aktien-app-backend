package com.example.demo.modules.group;

import com.example.demo.modules.group.request.CreateGroup;
import com.example.demo.modules.group.request.UpdateGroup;
import com.example.demo.modules.group.response.GroupResponse;
import com.example.demo.modules.user.User;
import com.example.demo.modules.user.UserRepository;
import com.example.demo.modules.user.response.UserResponse;
import com.example.demo.utils.AlreadyExistsException;
import com.example.demo.utils.DeletionIntegrityException;
import com.example.demo.utils.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    /*@Override
    public List<Group> findAllUsersByGroupName(String name) {
        return groupRepository.findAllUsersByGroupName();
    }*/

    @Override
    public Group findGroupByName(String name) {
        return groupRepository.findByName(name);
    }

    @Override
    public GroupResponse createGroup(CreateGroup request) {
        Group alreadyExist = groupRepository.findByName(request.getName());
        if (alreadyExist != null) {
            throw new AlreadyExistsException("A Group by the name " + request.getName() + " already exists");
        }
        Group group = new Group(request.getName());
        List<User> myUser = new ArrayList<>();
        for (String username : request.getUsernames()) {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new NotFoundException("User with the id " + username + " could not be found"));
            myUser.add(user);
        }
        for (User user : myUser) {
            group.addUser(user);
        }

        group = groupRepository.save(group);


        return new GroupResponse(group);
    }

    @Override
    public void deleteGroupById(long groupId) throws NotFoundException, DeletionIntegrityException {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new NotFoundException("Group could not be found"));
        List<User> toSafeAtTheEnd = new ArrayList<>();
        try {
            for (User user : group.getMyUsers()) {
                user.getJoinedGroups().remove(group);
                toSafeAtTheEnd.add(user);
            }
            group.getMyUsers().clear();
            groupRepository.delete(group);
            for (User user : toSafeAtTheEnd) {
                userRepository.save(user);
            }
        } catch (Exception e) {
            throw new DeletionIntegrityException(e.getMessage());
        }
    }


    @Override
    public List<UserResponse> getAllUserOfGroup(long groupId) throws NotFoundException {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new NotFoundException("Group could not be found"));
        List<User> myUsers = userRepository.findAllByJoinedGroups(group);
        List<UserResponse> userResponseList = new ArrayList<>();
        myUsers.forEach(user -> userResponseList.add(new UserResponse(user)));
        return userResponseList;
    }


    @Override
    public void addUserToGroup(long groupId, String username) throws NotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User: " + username + " not found"));
        if (user == null) {
            throw new NotFoundException("User wiht the username " + username + " could not be found");
        }
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new NotFoundException("Group with the id " + groupId + " could not be found"));
        for (User userInGroup : group.getMyUsers()) {
            if (userInGroup.getUsername().equals(username)) {
                throw new AlreadyExistsException("The User " + username + " is already part of the Group");
            }
        }

        group.addUser(user);
        userRepository.save(user);
        groupRepository.save(group);

    }


    @Override
    public GroupResponse updateGroupById(long groupId, UpdateGroup request) throws NotFoundException {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException("group could not be found "));

        if (request.getName() != null && !request.getName().isEmpty()) {
            group.setName(request.getName());
        }

        if (request.getUsernames() != null && !request.getUsernames().isEmpty()) {
            group.getMyUsers().forEach(user -> user.getJoinedGroups().remove(group));
            List<User> toSaveAtTheEnd = new ArrayList<>(group.getMyUsers());
            List<User> updatedUsers = request.getUsernames().stream().map(username -> userRepository.findByUsername(username)
                    .orElseThrow(() -> new NotFoundException("User could not be found"))).collect(Collectors.toList());
            group.setMyUsers(updatedUsers);
            groupRepository.save(group);
            updatedUsers.forEach(user -> {
                user.getJoinedGroups().add(group);
                userRepository.save(user);
            });
            toSaveAtTheEnd.forEach(userRepository::save);

        }


        return new GroupResponse(group);
    }
}
