package com.example.demo.modules.user;

import com.example.demo.modules.group.Group;
import com.example.demo.modules.group.GroupService;
import com.example.demo.modules.group.response.GroupResponse;
import com.example.demo.modules.user.request.CreateUser;
import com.example.demo.modules.user.response.UserResponse;
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

    @PostMapping("register")
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid CreateUser request, UriComponentsBuilder uriComponentsBuilder) {
        UserResponse user = userService.createUser(request);
        UriComponents uriComponents = uriComponentsBuilder.path("user/{username}").buildAndExpand(request.getUsername());
        URI location = uriComponents.toUri();
        return ResponseEntity.created(location).body(user);
    }


    @GetMapping("allGroups/{userId}")
    public ResponseEntity<List<GroupResponse>> getAllGroupsOfUser (@PathVariable ("userId") long userId) throws NotFoundException {
        return ResponseEntity.ok(userService.getAllGroupsOfUser(userId));
    }

}
