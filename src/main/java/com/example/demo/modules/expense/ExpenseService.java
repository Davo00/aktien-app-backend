package com.example.demo.modules.expense;

import javassist.NotFoundException;

import java.util.List;

public interface ExpenseService {

    List<Expense> findAllExpense();
    Expense createExpense(Expense request);
    void deleteExpense(Long id);
    Expense updateExpensebyId(Long id, UpdateExpense request)throws NotFoundException;
    Expense one(Long id) throws NotFoundException;
    List<Expense> getAllExpensebyGroup(long groupId) throws NotFoundException;


}

