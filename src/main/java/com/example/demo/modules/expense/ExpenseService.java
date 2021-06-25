package com.example.demo.modules.expense;

import com.example.demo.utils.DeletionIntegrityException;
import javassist.NotFoundException;

import java.util.List;

public interface ExpenseService {

    List<Expense> findAllExpense();
    Expense createExpense(Expense request);
    void deleteExpense(Long id) throws NotFoundException, DeletionIntegrityException;
    Expense updateExpensebyId(Long id, UpdateExpense request)throws NotFoundException;
    Expense one(Long id) throws NotFoundException;
    List<Expense> getAllExpensebyGroup(long groupId) throws NotFoundException;


}

