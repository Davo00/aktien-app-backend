package com.example.demo.modules.group;

import com.example.demo.modules.user.User;
import com.example.demo.utils.DeletionIntegrityException;
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
@RequestMapping("/group")
public class GroupController {

    @Autowired
    GroupService groupService;

    /*@GetMapping("name/{name}/all/user")
    public ResponseEntity<List<Group>> findAllExpense(@PathVariable String name) {
        return ResponseEntity.ok(groupService.findAllUsersByGroupName(name));
    }*/

    @GetMapping("allUsers/{groupId}")
    public ResponseEntity<List<User>> getUsersbyGroup(@PathVariable("groupId") long groupId) throws NotFoundException {
        return ResponseEntity.ok(groupService.getAllUserOfGroup(groupId));

    }

    @PostMapping("name")
    public ResponseEntity<Group> createGroup(@RequestBody @Valid Group request, UriComponentsBuilder uriComponentsBuilder,
                                             @PathVariable String name) {
        Group group = groupService.createGroup(request);
        UriComponents uriComponents = uriComponentsBuilder.path("group/{name}").buildAndExpand(name);
        URI location = uriComponents.toUri();
        return ResponseEntity.created(location).body(group);
    }

    @PostMapping("username")
    public ResponseEntity<Group> addUserToGroup(@RequestBody @Valid Group request,
                                                UriComponentsBuilder uriComponentsBuilder,
                                                @PathVariable String username) {
        Group group = groupService.createGroup(request);
        UriComponents uriComponents = uriComponentsBuilder.path("group/{username}").buildAndExpand(username);
        URI location = uriComponents.toUri();
        return ResponseEntity.created(location).body(group);
    }


    @DeleteMapping("{groupId}")
    public ResponseEntity<Void> deleteGroupById ( @PathVariable("groupId") long groupId) throws NotFoundException, DeletionIntegrityException {
        groupService.deleteGroupById(groupId);
        return ResponseEntity.noContent().build();
    }



}
