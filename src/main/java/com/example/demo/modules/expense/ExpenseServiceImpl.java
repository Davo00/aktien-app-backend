package com.example.demo.modules.expense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExpenseServiceImpl implements ExpenseService{

    @Autowired
    ExpenseRepository expenseRepository;




    @Override
    public List<Expense> findAllExpense() {
        List<Expense> allExpense = expenseRepository.findAll();
        return allExpense;
    }

    @Override
    public Expense createExpense(Expense request) {
        Expense expense = new Expense(request.getUserPaid(), request.getDebtName() , request.getAmount(), request.getDescription());
        expense = expenseRepository.save(expense);
        return expense;
    }
}
