package com.example.demo.modules.debt;

import com.example.demo.modules.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/debt")
public class DebtController {

    @Autowired
    DebtService debtService;



    @GetMapping
    public ResponseEntity<List<Debt>> findAllDebt() {return ResponseEntity.ok(debtService.findAllDebt()); }

    @PostMapping("")
    public ResponseEntity<Debt> createDebt(@RequestBody @Valid Debt request, UriComponentsBuilder uriComponentsBuilder){
        Debt debt = debtService.createDebt(request);
        UriComponents uriComponents = uriComponentsBuilder.path("debt/{id}").buildAndExpand(debt.getId());
        URI location = uriComponents.toUri();
        return ResponseEntity.created(location).body(debt);
    }

    @PostMapping("propose")
    public ResponseEntity<Debt> proposeDebt(@RequestBody @Valid Debt oldDebt, @RequestBody @Valid Share stock, @RequestBody @Valid Timestamp timestamp, UriComponentsBuilder uriComponentsBuilder){
        //TODO find the actual user who proposed the change
        User user = new User();//TODO mock function to return a user, later the logged on user
        Debt debt = debtService.proposeDebt(oldDebt, stock, timestamp, user);
        UriComponents uriComponents = uriComponentsBuilder.path("debt/{id}").buildAndExpand(debt.getId());
        URI location = uriComponents.toUri();
        return ResponseEntity.created(location).body(debt);
    }
    @PostMapping("accept")
    public ResponseEntity<Debt> acceptDebt(@RequestBody @Valid Debt debt, UriComponentsBuilder uriComponentsBuilder){
        //TODO find the actual user who proposed the change
        User user = new User();//TODO mock function to return a user, later the logged on user
        debt = debtService.acceptDebt(debt, user);
        UriComponents uriComponents = uriComponentsBuilder.path("debt/{id}").buildAndExpand(debt.getId());
        URI location = uriComponents.toUri();
        return ResponseEntity.created(location).body(debt);
    }
}
