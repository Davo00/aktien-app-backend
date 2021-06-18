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

    @Autowired
    UserService userService;

    @Autowired
    GroupService groupService;

    @PostMapping("register")
    public ResponseEntity<User> createUser(@RequestBody @Valid User request, UriComponentsBuilder uriComponentsBuilder,
                                             @PathVariable String username) {
        User user = userService.createUser(request);
        UriComponents uriComponents = uriComponentsBuilder.path("user/{username}").buildAndExpand(username);
        URI location = uriComponents.toUri();
        return ResponseEntity.created(location).body(user);
    }


    @GetMapping("allGroups/{userId}")
    public ResponseEntity<List<Group>> getAllGroupsOfUser (@PathVariable ("userId") long userId) throws NotFoundException {
        return ResponseEntity.ok(userService.getAllGroupsOfUser(userId));
    }

}
