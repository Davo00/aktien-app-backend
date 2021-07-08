package com.example.demo.modules.user;

import com.example.demo.modules.group.Group;
import com.example.demo.modules.group.GroupRepository;
import com.example.demo.modules.group.response.GroupResponse;
import com.example.demo.modules.user.request.CreateUser;
import com.example.demo.modules.user.response.UserResponse;
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
    public UserResponse createUser(CreateUser request) {
        User user = new User(request.getUsername(), request.getEmail());
        user = userRepository.save(user);
        return new UserResponse(user);
        //return user;
    }

    @Override
    public List<GroupResponse> getAllGroupsOfUser(long userId)throws NotFoundException{
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with the id " +userId + " could not be found!"));
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

        List<GroupResponse> groupResponseList = new ArrayList<>();
        for (Group group : myGroups){
            groupResponseList.add(new GroupResponse(group));
        }
        return groupResponseList;
    }
}
