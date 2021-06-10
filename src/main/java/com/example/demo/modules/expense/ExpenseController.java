package com.example.demo.modules.expense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/expense")
public class ExpenseController {


    @Autowired
    ExpenseService expenseService;

    @GetMapping
    public ResponseEntity<List<Expense>> findAllExpense(){
        return ResponseEntity.ok(expenseService.findAllExpense());
    }

    @PostMapping("bla")
    public ResponseEntity<Expense> createExpense(@RequestBody @Valid Expense request, UriComponentsBuilder uriComponentsBuilder){
        Expense expense = expenseService.createExpense(request);
        UriComponents uriComponents = uriComponentsBuilder.path("expense/{name}").buildAndExpand(expense.getDebtName());
        URI location = uriComponents.toUri();
        return ResponseEntity.created(location).body(expense);
    }


    //@GetMapping("id")
    //@PutMapping
}
