package com.example.demo.modules.user;

import com.example.demo.modules.group.Group;
import com.example.demo.modules.group.GroupService;
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

    @Autowired
    UserService userService;

    @Autowired
    GroupService groupService;

    @PostMapping("register")
    public ResponseEntity<User> createUser(@RequestBody @Valid User request, UriComponentsBuilder uriComponentsBuilder,
                                             @PathVariable String username) throws UserServiceImpl.UsernameReservedException {
        User user = userService.createUser(request);
        UriComponents uriComponents = uriComponentsBuilder.path("user/{username}").buildAndExpand(username);
        URI location = uriComponents.toUri();
        return ResponseEntity.created(location).body(user);
    }

    @GetMapping("group")
    public ResponseEntity<List<User>> getUsersbyGroup(@RequestBody @Valid Group request,
                                                UriComponentsBuilder uriComponentsBuilder,
                                                @PathVariable String groupName) {
        Group group = groupService.findGroupByName(request.getName());
        List<User> users = userService.getUsersByGroup(group);
        UriComponents uriComponents = uriComponentsBuilder.path("{groupname}").buildAndExpand(group.getName());
        URI location = uriComponents.toUri();
        return ResponseEntity.created(location).body(users);
    }

    @PutMapping()
    public ResponseEntity<User> updateUser(@RequestBody @Valid User request, UriComponentsBuilder uriComponentsBuilder) throws UserServiceImpl.UsernameReservedException {
        userService.updateUser(request);
        UriComponents uriComponents = uriComponentsBuilder.path("").buildAndExpand();
        URI location = uriComponents.toUri();
        return ResponseEntity.created(location).body(userService.getCurrentUser());
    }

    @DeleteMapping()
    public ResponseEntity<User> deleteUser(UriComponentsBuilder uriComponentsBuilder) {


        userService.deleteUser();

        UriComponents uriComponents = uriComponentsBuilder.path("").buildAndExpand();
        URI location = uriComponents.toUri();
        return ResponseEntity.created(location).body(null);
    }

}
