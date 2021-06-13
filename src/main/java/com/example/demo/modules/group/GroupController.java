package com.example.demo.modules.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    GroupService groupService;

    /*@GetMapping("name/{name}/all/user")
    public ResponseEntity<List<Group>> findAllExpense(@PathVariable String name) {
        return ResponseEntity.ok(groupService.findAllUsersByGroupName(name));
    }*/

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

}
