package com.example.demo.modules.user;

import com.example.demo.modules.group.Group;
import com.example.demo.modules.group.GroupService;
import com.example.demo.modules.security.JwtTokenUtil;
import com.example.demo.modules.user.request.UserLogin;
import com.example.demo.modules.group.response.GroupResponse;
import com.example.demo.modules.user.request.CreateUser;
import com.example.demo.modules.user.response.UserResponse;
import com.example.demo.utils.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public UserController(UserService userService, GroupService groupService, AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.groupService = groupService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("register")
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid CreateUser request, UriComponentsBuilder uriComponentsBuilder) {
        UserResponse user = userService.createUser(request);
        UriComponents uriComponents = uriComponentsBuilder.path("user/{username}").buildAndExpand(request.getUsername());
        URI location = uriComponents.toUri();
        return ResponseEntity.created(location).body(user);
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestBody @Valid UserLogin request) {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    request.getUsername(), request.getPassword()
                            )
                    );

            User user = (User) authenticate.getPrincipal();

            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            jwtTokenUtil.generateAccessToken(user)
                    ).build();
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("group/{groupName}")
    public ResponseEntity<List<User>> getUsersbyGroup(UriComponentsBuilder uriComponentsBuilder,
                                                      @PathVariable String groupName) {
        Group group = groupService.findGroupByName(groupName);
        List<User> users = userService.getUsersByGroup(group);
        UriComponents uriComponents = uriComponentsBuilder.path("{groupname}").buildAndExpand(group.getName());
        URI location = uriComponents.toUri();
        return ResponseEntity.created(location).body(users);
    }

    @GetMapping("allGroups/{userId}")
    public ResponseEntity<List<GroupResponse>> getAllGroupsOfUser (@PathVariable ("userId") long userId) throws NotFoundException {
        return ResponseEntity.ok(userService.getAllGroupsOfUser(userId));
    }

    @PutMapping()
    public ResponseEntity<User> updateUser(@RequestBody @Valid User request, @RequestHeader("Authorization") String token, UriComponentsBuilder
            uriComponentsBuilder) throws UserServiceImpl.UsernameReservedException {
        userService.updateUser(request, token);
        UriComponents uriComponents = uriComponentsBuilder.path("").buildAndExpand();
        URI location = uriComponents.toUri();
        return ResponseEntity.created(location).body(userService.getCurrentUser(token));
    }

    @DeleteMapping()
    public ResponseEntity<User> deleteUser(@RequestHeader("Authorization") String token) {
        userService.deleteUser(token);
        return ResponseEntity.noContent().build();
    }

}
