package com.example.demo.modules.expense;

import com.example.demo.modules.expense.request.CreateExpense;
import com.example.demo.modules.expense.request.UpdateExpense;
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

    @GetMapping("{groupId}")
    public ResponseEntity<List<Expense>> getExpensebyGroup(@PathVariable("groupId") long groupId) throws NotFoundException {
        return ResponseEntity.ok(expenseService.getAllExpensebyGroup(groupId));

    }

    @PostMapping
    public ResponseEntity<Expense> createExpense(@RequestBody @Valid CreateExpense request, UriComponentsBuilder uriComponentsBuilder) throws NotFoundException {
        Expense expense = expenseService.createExpense(request);
        UriComponents uriComponents = uriComponentsBuilder.path("expense/{name}").buildAndExpand(expense.getName());
        URI location = uriComponents.toUri();
        return ResponseEntity.created(location).body(expense);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Expense> deleteExpense(@PathVariable long id) throws NotFoundException {
            expenseService.deleteExpense(id);

        return ResponseEntity.noContent().build();

    }


    @PutMapping("{expenseId}")
    public ResponseEntity<Expense> updateExpenseById(@RequestBody @Valid UpdateExpense request , @PathVariable ("expenseId") long expenseId) throws NotFoundException{
        return ResponseEntity.ok(expenseService.updateExpensebyId(expenseId, request));
    }


}
