package com.example.demo.modules.user;

import com.example.demo.modules.group.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByJoinedGroups(Group group);
    Optional<User> findByUsername(String username);


}
