package com.example.demo.modules.group;

import com.example.demo.modules.user.User;
import com.example.demo.utils.DeletionIntegrityException;
import com.example.demo.utils.NotFoundException;

import java.util.List;

public interface GroupService {
    //public List<Group> findAllUsersByGroupName(String name);
    Group findGroupByName(String name);
    Group createGroup(Group request);
    void deleteGroupById(long groupId) throws NotFoundException, DeletionIntegrityException;
    List<User> getAllUserOfGroup(long groupId) throws NotFoundException;
    void addUserToGroup( long groupId, String username);
}
