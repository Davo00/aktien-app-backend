package com.example.demo.modules.share;

import com.example.demo.modules.expense.ExpenseNotFoundException;
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

    @GetMapping
    public ResponseEntity<List<Share>> findAllShare(){
        return ResponseEntity.ok(shareService.findAllShare());
    }



    @PostMapping("bla")
    public ResponseEntity<Share> createShare(@RequestBody @Valid Share request, UriComponentsBuilder uriComponentsBuilder){
        Share share = shareService.createShare(request);
        UriComponents uriComponents = uriComponentsBuilder.path("share/{name}").buildAndExpand(share.getName());
        URI location = uriComponents.toUri();
        return ResponseEntity.created(location).body(share);
    }


    @GetMapping("/share/{id}")
    public ResponseEntity<Share> one(@PathVariable long id) throws ExpenseNotFoundException {
        Optional<Share> share = Optional.ofNullable(shareService.one(id));
        if(share.isEmpty()){

            throw new ShareNotFoundException((id));
        }



        return ResponseEntity.ok(shareService.one(id));

    }


    @DeleteMapping("/share/{id}")
    public ResponseEntity<Share> deleteShare(@PathVariable long id) throws ExpenseNotFoundException {
        Share share = shareService.deleteShare(id);

        return ResponseEntity.noContent().build();

    }


    //@GetMapping("id")
    //@PutMapping
}
