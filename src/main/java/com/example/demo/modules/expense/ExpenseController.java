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
        UriComponents uriComponents = uriComponentsBuilder.path("expense/{name}").buildAndExpand(expense.getName());
        URI location = uriComponents.toUri();
        return ResponseEntity.created(location).body(expense);
    }

 /*   @GetMapping("/expenses/{id}")
    public ResponseEntity<Expense> one(@PathVariable long id) throws ExpenseNotFoundException {
           Expense expense = expenseService.one(id);

           return ResponseEntity.ok(expenseService.one(id));

    }*/


    @DeleteMapping("/expenses/{id}")
    public ResponseEntity<Expense> deleteExpense(@PathVariable long id) throws ExpenseNotFoundException {
        Expense expense = expenseService.deleteExpense(id);

        return ResponseEntity.noContent().build();

    }


    /*@PostMapping("expense/{expense_id}/copayer/{username}")
    public ResponseEntity<Expense> deleteCopayerfromExpense(@PathVariable long id,
                                                            @PathVariable String username,
                                                            UriComponentsBuilder uriComponentsBuilder) throws ExpenseNotFoundException, URISyntaxException {


        Expense expense = expenseService.one(id);
        expense =  expenseService.deleteCopayerFromExpense(expense, username);
        URI location = removeQueryParameter(uriComponentsBuilder.toUriString(), username);
        return ResponseEntity.ok(expense);
    }

    public URI removeQueryParameter(String url, String parameterName) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(url);
        List<NameValuePair> queryParameters = uriBuilder.getQueryParams();

        queryParameters.removeIf(param ->
                param.getName().equals(parameterName));

        uriBuilder.setParameters(queryParameters);

        return uriBuilder.build();
    }*/





/*    @PostMapping("expense/{expense_id}/{copayer}/{username}")
    public ResponseEntity<Expense>distributeExpense(@PathVariable(value = "expense_id") long id,
                                                    @PathVariable(value = "copayer") String copayerName,
                                                    @PathVariable(value = "username") String username,
                                                    UriComponentsBuilder uriComponentsBuilder

                                                    ){
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("id",String.valueOf(id));
        urlParams.put("username",username);
        Expense e = expenseService.one(id);
        expenseService.editExpense(e,copayerName,username);
        UriComponents uriComponents = uriComponentsBuilder.path("expense/{expense_id}/{copayer}/{username}").buildAndExpand(urlParams);
        URI location = uriComponents.toUri();
        return ResponseEntity.created(location).body(e);
       // return ResponseEntity.ok(e);

    }*/


    @PostMapping("expense/{expense_id}")
    public ResponseEntity<Expense>updateExpense     (@PathVariable(value = "expense_id") long id,
                                                    String name, String userpaid, double amount

    ){
        Expense e = expenseService.one(id);
        expenseService.updateExpense(e, name, amount, userpaid);
       return ResponseEntity.ok(e);

    }

    @GetMapping("/expenses/getAll/{id}")
    public ResponseEntity<List<Expense>> getAllByGroupID(@PathVariable(value = "id") long groupid){
        return ResponseEntity.ok(expenseService.allExpensebyGroup(groupid));
    }







    //@GetMapping("id")
    //@PutMapping
}
