package com.example.demo.modules.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GroupServiceImpl implements GroupService {

    @Autowired
    GroupRepository groupRepository;

    /*@Override
    public List<Group> findAllUsersByGroupName(String name) {
        return groupRepository.findAllUsersByGroupName();
    }*/

    @Override
    public Group findGroupByName(String name) {
        return groupRepository.findByName(name);
    }

    @Override
    public Group createService(Group request) {
        Group group = new Group(request.getName());
        group = groupRepository.save(group);
        return group;
    }
}
