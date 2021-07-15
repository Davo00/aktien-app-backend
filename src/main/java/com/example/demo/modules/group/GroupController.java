package com.example.demo.modules.group;

import com.example.demo.modules.group.request.CreateGroup;
import com.example.demo.modules.group.request.UpdateGroup;
import com.example.demo.modules.group.response.GroupResponse;
import com.example.demo.modules.user.User;
import com.example.demo.modules.user.UserService;
import com.example.demo.modules.user.response.UserResponse;
import com.example.demo.utils.DeletionIntegrityException;
import com.example.demo.utils.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {


    private GroupService groupService;
    private UserService userService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("allUsers/{groupId}")
    public ResponseEntity<List<UserResponse>> getUsersbyGroup(@PathVariable("groupId") long groupId) throws NotFoundException {
        return ResponseEntity.ok(groupService.getAllUserOfGroup(groupId));

    }

    @PostMapping
    public ResponseEntity<GroupResponse> createGroup(@RequestHeader("Authorization") String token,
                                                     @RequestBody @Valid CreateGroup request,
                                                     UriComponentsBuilder uriComponentsBuilder) {
        String requester = userService.getCurrentUser(token).getUsername();
        request.getUsernames().add(requester);
        GroupResponse group = groupService.createGroup(request);
        UriComponents uriComponents = uriComponentsBuilder.path("group/{name}").buildAndExpand(request.getName());
        URI location = uriComponents.toUri();
        return ResponseEntity.created(location).body(group);
    }

    @PutMapping("{groupId}/{username}")
    public ResponseEntity<Void> addUserToGroup(@PathVariable("groupId") long groupId,
                                               @PathVariable("username") String username) {
        groupService.addUserToGroup(groupId, username);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{groupId}")
    public ResponseEntity<GroupResponse> updateGroupById(@RequestBody @Valid UpdateGroup request, @PathVariable("groupId") long groupId) throws NotFoundException {
        return ResponseEntity.ok(groupService.updateGroupById(groupId, request));
    }


    @DeleteMapping("{groupId}")
    public ResponseEntity<Void> deleteGroupById(@PathVariable("groupId") long groupId) throws NotFoundException, DeletionIntegrityException {

        groupService.deleteGroupById(groupId);
        return ResponseEntity.noContent().build();
    }


}
