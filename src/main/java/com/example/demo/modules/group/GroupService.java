package com.example.demo.modules.group;

import java.util.List;

public interface GroupService {
    //public List<Group> findAllUsersByGroupName(String name);
    Group findGroupByName(String name);
    Group createService(Group request);
}
