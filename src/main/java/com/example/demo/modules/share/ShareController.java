package com.example.demo.modules.share;

import com.example.demo.modules.share.request.CreateShare;
import com.example.demo.modules.user.UserService;
import com.example.demo.utils.DeletionIntegrityException;
import javassist.NotFoundException;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/share")
public class ShareController {


    @Autowired
    ShareService shareService;
    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<Share>> findAllShare(){
        return ResponseEntity.ok(shareService.findAllShare());
    }

    @PostMapping("")
    public ResponseEntity<Share> createShare(@RequestBody @Valid CreateShare request, UriComponentsBuilder uriComponentsBuilder) throws NotFoundException {
        Share share = shareService.createShare(request);
        UriComponents uriComponents = uriComponentsBuilder.path("share/{name}").buildAndExpand(share.getName());
        URI location = uriComponents.toUri();
        return ResponseEntity.created(location).body(share);
    }

    @GetMapping("{id}")
    public ResponseEntity<Share> one(@PathVariable long id) throws NotFoundException {
        Optional<Share> share = Optional.ofNullable(shareService.one(id));
        Share s = share.orElseThrow(() -> new NotFoundException("Share could not be found"));
        return ResponseEntity.ok(s);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Share> deleteShare(@PathVariable long id) throws NotFoundException, DeletionIntegrityException {
        shareService.deleteShare(id);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("{username}")
    public ResponseEntity<List<Share>> getPreferedSharesbyUsername(@PathVariable String username) throws NotFoundException {
         Optional<List<Share>> share = Optional.ofNullable(shareService.getPreferedSharesbyUser(username));
        List<Share> s = share.orElseThrow(() -> new NotFoundException("Sharelist could not be found"));
        return ResponseEntity.ok(s);
    }



    @PostMapping("share_id/username")
    public  ResponseEntity<Share> selectShareFromPartner(@PathVariable Long share_id, @PathVariable String username)throws NotFoundException{
    Share s = null;
    //To do
    return ResponseEntity.ok(s);
    }


}
