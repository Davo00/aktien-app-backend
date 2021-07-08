package com.example.demo.modules.expense;

import com.example.demo.modules.expense.request.CreateExpense;
import com.example.demo.modules.expense.request.UpdateExpense;
import com.example.demo.modules.expense.respone.ExpenseResponse;
import com.example.demo.utils.DeletionIntegrityException;
import javassist.NotFoundException;

import java.util.List;

public interface ExpenseService {

    List<ExpenseResponse> findAllExpense();
    ExpenseResponse createExpense(CreateExpense request) throws NotFoundException;
    void deleteExpense(Long id) throws NotFoundException, DeletionIntegrityException;
    ExpenseResponse updateExpensebyId(Long id, UpdateExpense request)throws NotFoundException;
    Expense one(Long id) throws NotFoundException;
    List<ExpenseResponse> getAllExpensebyGroup(long groupId) throws NotFoundException;


}

