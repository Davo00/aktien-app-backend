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

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupRepository groupRepository;

    @Override
    public User createUser(User request) {
        return new User(request.getUsername(), request.getEmail());
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
}
