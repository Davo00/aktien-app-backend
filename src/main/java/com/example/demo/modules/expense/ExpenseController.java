package com.example.demo.modules.expense;

import javassist.NotFoundException;
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
    public ResponseEntity<List<Expense>> findAllExpense() {
        return ResponseEntity.ok(expenseService.findAllExpense());
    }

    @GetMapping("expenses/{groupId}")
    public ResponseEntity<List<Expense>> getExpensebyGroup(@PathVariable("groupId") long groupId) throws NotFoundException {
        return ResponseEntity.ok(expenseService.getAllExpensebyGroup(groupId));

    }

    @PostMapping("name")
    public ResponseEntity<Expense> createExpense(@RequestBody @Valid Expense request, UriComponentsBuilder uriComponentsBuilder) {
        Expense expense = expenseService.createExpense(request);
        UriComponents uriComponents = uriComponentsBuilder.path("expense/{name}").buildAndExpand(expense.getName());
        URI location = uriComponents.toUri();
        return ResponseEntity.created(location).body(expense);
    }


    @DeleteMapping("/expense/{id}")
    public ResponseEntity<Expense> deleteExpense(@PathVariable long id) throws NotFoundException {
            expenseService.deleteExpense(id);

        return ResponseEntity.noContent().build();

    }


    @PutMapping("{expenseId}")
    public ResponseEntity<Expense> updateExpenseById(@RequestBody @Valid UpdateExpense request , @PathVariable ("expenseId") long expenseId) throws NotFoundException{
        return ResponseEntity.ok(expenseService.updateExpensebyId(expenseId, request));
    }


}
