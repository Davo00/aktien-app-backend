package com.example.demo.modules.group;

import com.example.demo.modules.group.request.UpdateGroup;
import com.example.demo.modules.user.User;
import com.example.demo.modules.user.UserRepository;
import com.example.demo.utils.DeletionIntegrityException;
import com.example.demo.utils.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        List<User> toSafeAtTheEnd = new ArrayList<>();
        try{
            for (User user : group.getMyUsers()) {
                user.getJoinedGroups().remove(group);
                toSafeAtTheEnd.add(user);
            }
            group.getMyUsers().clear();
            groupRepository.delete(group);
            for (User user : toSafeAtTheEnd){
                user = userRepository.save(user);
            }
        }catch (Exception e ){
            throw new DeletionIntegrityException(e.getMessage());
        }
    }


    @Override
    public List<User> getAllUserOfGroup(long groupId) throws NotFoundException {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new NotFoundException("Group could not be found"));
        List<User> myUsers = userRepository.findAllByJoinedGroups(group);
        return myUsers;
    }


    @Override
    public void addUserToGroup(long groupId, String username) throws NotFoundException{
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User: " + username + " not found"));
        if (user==null){
            throw new NotFoundException("User could not be found");
        }
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new NotFoundException("Group could not be found"));
        group.addUser(user);
        userRepository.save(user);
        groupRepository.save(group);

    }


    @Override
    public Group updateGroupById(long groupId, UpdateGroup request) throws NotFoundException {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new NotFoundException("group could not be found "));
        List<User> toSafeAtTheEnd = new ArrayList<>();

        if (request.getUserIds()!= null && !request.getUserIds().isEmpty()){
            List<User> newUserList = new ArrayList<>();
            for( int i =0; i<request.getUserIds().size(); i++){
                System.out.println(request.getUserIds().get(i));
                User user = userRepository.findById(request.getUserIds().get(i)).orElseThrow(() -> new NotFoundException("User could not be found"));
                if(user!= null){
                    newUserList.add(user);
                    System.out.println(user.getUsername());
                }
            }
            if (group.getMyUsers()!=null && !group.getMyUsers().isEmpty()){
                for (User user : group.getMyUsers()){
                    user.getJoinedGroups().remove(group);
                    toSafeAtTheEnd.add(user);
                }
                group.getMyUsers().clear();
            }
            for ( User user : newUserList){
                System.out.println("to Add" + user.getUsername());
                group.addUser(user);
                toSafeAtTheEnd.add(user);
            }
        }
        if (request.getName()!= null){
            group.setName(request.getName());
        }



        group= groupRepository.save(group);
        for(User user : toSafeAtTheEnd){
            user = userRepository.save(user);

        }


        return group;
    }
}
