package com.example.demo.modules.user;

import com.example.demo.modules.group.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByJoinedGroups(Group group);


}
