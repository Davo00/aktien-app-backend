package com.example.demo.modules.expense;

public class ExpenseNotFoundException extends RuntimeException {

    ExpenseNotFoundException(long name) {
        super("Expense " + name +  "konnte nicht gefunden werden.");
    }
}
