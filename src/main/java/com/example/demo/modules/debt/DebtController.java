package com.example.demo.modules.debt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/debt")
public class DebtController {

    @Autowired
    DebtService debtService;

    @GetMapping
    public ResponseEntity<List<Debt>> findAllDebt() {return ResponseEntity.ok(debtService.findAllDebt()); }

    @PostMapping("bla")
    public ResponseEntity<Debt> createDebt(@RequestBody @Valid Debt request, UriComponentsBuilder uriComponentsBuilder){
        Debt debt = debtService.createDebt(request);
        UriComponents uriComponents = uriComponentsBuilder.path("debt/{id}").buildAndExpand(debt.getId());
        URI location = uriComponents.toUri();
        return ResponseEntity.created(location).body(debt);
    }
}
