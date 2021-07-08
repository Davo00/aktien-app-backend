package com.example.demo.modules.group;

import com.example.demo.modules.group.request.CreateGroup;
import com.example.demo.modules.group.request.UpdateGroup;
import com.example.demo.modules.group.response.GroupResponse;
import com.example.demo.modules.user.User;
import com.example.demo.modules.user.UserRepository;
import com.example.demo.utils.AlreadyExistsException;
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
    public GroupResponse createGroup(CreateGroup request) {
        Group alreadyExist = groupRepository.findByName(request.getName());
        if(alreadyExist!= null ){
            throw new AlreadyExistsException("A Group by the name "+ request.getName() + " already exists");
        }
        Group group = new Group(request.getName());
        List <User> myUser = new ArrayList<>();
        for (Long id : request.getMyUsersIds()){
            User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with the id "+ id + " could not be found"));
            myUser.add(user);
        }
        for(User user : myUser){
            group.addUser(user);
        }

        group = groupRepository.save(group);


        return new GroupResponse(group);
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
        User user = userRepository.findByUsername(username);
        if (user==null){
            throw new NotFoundException("User could not be found");
        }
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new NotFoundException("Group could not be found"));
        group.addUser(user);
        userRepository.save(user);
        groupRepository.save(group);

    }


    @Override
    public GroupResponse updateGroupById(long groupId, UpdateGroup request) throws NotFoundException {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new NotFoundException("group could not be found "));
        List<User> toSafeAtTheEnd = new ArrayList<>();

        if (request.getUserIds()!= null && !request.getUserIds().isEmpty()){
            List<User> newUserList = new ArrayList<>();
            for( int i =0; i<request.getUserIds().size(); i++){
                User user = userRepository.findById(request.getUserIds().get(i)).orElseThrow(() -> new NotFoundException("User could not be found"));
                if(user!= null){
                    newUserList.add(user);
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


        return new GroupResponse(group);
    }
}
