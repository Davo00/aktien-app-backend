package com.example.demo.modules.expense;

import java.util.List;

public interface ExpenseService {

    List<Expense> findAllExpense();
    Expense createExpense(Expense request);
    Expense one(long id);



    Expense deleteExpense(long id);
   // Expense deleteCopayerFromExpense(Expense request, String username);
    //Expense editExpense(Expense request);
 //   Expense editExpense(Expense request, String copayer,String username);
    Expense updateExpense(Expense request, String name, double amount, String userpaid);
  //  Expense acceptExpense(long expenseid, String username);
    List<Expense>allExpensebyGroup(long groupid);
}

