package com.example.demo.modules.group;

import com.example.demo.modules.group.request.CreateGroup;
import com.example.demo.modules.group.request.UpdateGroup;
import com.example.demo.modules.group.response.GroupResponse;
import com.example.demo.modules.user.User;
import com.example.demo.modules.user.response.UserResponse;
import com.example.demo.utils.DeletionIntegrityException;
import com.example.demo.utils.NotFoundException;

import java.util.List;

public interface GroupService {
    //public List<Group> findAllUsersByGroupName(String name);
    Group findGroupByName(String name);
    GroupResponse createGroup(CreateGroup request);
    void deleteGroupById(long groupId) throws NotFoundException, DeletionIntegrityException;
    List<UserResponse> getAllUserOfGroup(long groupId) throws NotFoundException;
    void addUserToGroup( long groupId, String username);
    GroupResponse updateGroupById(long groupId, UpdateGroup request)throws NotFoundException;
}
