package com.example.demo.modules.expense;

import javassist.NotFoundException;

import java.util.List;

public interface ExpenseService {

    List<Expense> findAllExpense();
    Expense createExpense(Expense request);
    Expense deleteExpense(Long id);
    Expense updateExpense(Expense request, String name, double amount, String userpaid);
    Expense one(Long id) throws NotFoundException;
    List<Expense> getAllExpensebyGroup(long groupId) throws NotFoundException;


}

