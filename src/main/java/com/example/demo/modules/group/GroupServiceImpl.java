package com.example.demo.modules.group;

import com.example.demo.modules.user.User;
import com.example.demo.modules.user.UserRepository;
import com.example.demo.utils.DeletionIntegrityException;
import com.example.demo.utils.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GroupServiceImpl implements GroupService {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserRepository userRepository;

    /*@Override
    public List<Group> findAllUsersByGroupName(String name) {
        return groupRepository.findAllUsersByGroupName();
    }*/

    @Override
    public Group findGroupByName(String name) {
        return groupRepository.findByName(name);
    }

    @Override
    public Group createGroup(Group request) {
        Group group = new Group(request.getName());
        group = groupRepository.save(group);
        return group;
    }

    @Override
    public void deleteGroupById(long groupId) throws NotFoundException, DeletionIntegrityException {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new NotFoundException("Group could not be found"));
        try{
            groupRepository.delete(group);
        }catch (Exception e ){
            throw new DeletionIntegrityException(e.getMessage());
        }
    }


    @Override
    public List<User> getAllUserOfGroup(long groupId) throws NotFoundException {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new NotFoundException("Group could not be found"));
        List<User> myUsers = userRepository.findAllByJoinedGroups(group);
                /*group.getMyUsers().forEach(user -> myUsers.add(user));
        for(User user : group.getMyUsers()){
            myUsers.add(user);
        }*/
        return myUsers;
    }


    @Override
    public void addUserToGroup(long groupId, String username) throws NotFoundException{
        User user = userRepository.findByUsername(username);
        if (user==null){
            throw new NotFoundException("User could not be found");
        }
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new NotFoundException("Group could not be found"));
        group.addUser(user);
        userRepository.save(user);
        groupRepository.save(group);

    }
}
