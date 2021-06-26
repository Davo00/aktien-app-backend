package com.example.demo.modules.user;

import com.example.demo.modules.group.Group;
import com.example.demo.modules.group.GroupService;
import com.example.demo.utils.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private GroupService groupService;

    @Autowired
    public UserController(UserService userService, GroupService groupService) {
        this.userService = userService;
        this.groupService = groupService;
    }

    @PostMapping("register")
    public ResponseEntity<User> createUser(@RequestBody @Valid User request, UriComponentsBuilder uriComponentsBuilder) throws UserServiceImpl.UsernameReservedException {
        User user = userService.createUser(request);
        UriComponents uriComponents = uriComponentsBuilder.path("user/{username}").buildAndExpand(user.getUsername());
        URI location = uriComponents.toUri();
        return ResponseEntity.created(location).body(user);
    }

    @GetMapping("group")
    public ResponseEntity<List<User>> getUsersbyGroup(UriComponentsBuilder uriComponentsBuilder,
                                                      @PathVariable String groupName) {
        Group group = groupService.findGroupByName(groupName);
        List<User> users = userService.getUsersByGroup(group);
        UriComponents uriComponents = uriComponentsBuilder.path("{groupname}").buildAndExpand(group.getName());
        URI location = uriComponents.toUri();
        return ResponseEntity.created(location).body(users);

        @GetMapping("allGroups/{userId}")
        public ResponseEntity<List<Group>> getAllGroupsOfUser ( @PathVariable("userId") long userId) throws
        NotFoundException {
            return ResponseEntity.ok(userService.getAllGroupsOfUser(userId));
        }

        @PutMapping()
        public ResponseEntity<User> updateUser (@RequestBody @Valid User request, UriComponentsBuilder
        uriComponentsBuilder) throws UserServiceImpl.UsernameReservedException {
            userService.updateUser(request);
            UriComponents uriComponents = uriComponentsBuilder.path("").buildAndExpand();
            URI location = uriComponents.toUri();
            return ResponseEntity.created(location).body(userService.getCurrentUser());
        }

        @DeleteMapping()
        public ResponseEntity<User> deleteUser () {
            userService.deleteUser();
            return ResponseEntity.noContent().build();
        }

    }
