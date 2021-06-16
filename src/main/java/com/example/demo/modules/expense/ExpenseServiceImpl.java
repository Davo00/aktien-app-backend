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
        Expense expense = new Expense(request.getUserPaid(), request.getName() , request.getAmount(), request.getDescription());
        expense = expenseRepository.save(expense);
        return expense;
    }

    @Override
    public Expense one(long id) {
  Expense      expense = expenseRepository.getOne(id);
        return expense;

    }

    public Expense Expense (String name) {
        Expense expense = expenseRepository.findByname(name);
        return expense;

    }





    @Override
    public Expense deleteExpense(long id) {

        expenseRepository.deleteById(id);

        return null;
    }

/*    @Override
    public Expense deleteCopayerFromExpense(Expense request, String username) {
        Iterator<User> it = request.getCopayers().iterator();

        User u;
        while (it.hasNext()) {
            u= it.next();
            if (u.getUsername().equals(username)){
                request.getCopayers().remove(u);
                break;
            }

        }


        expenseRepository.save(request);
        return request;
    }*/


  /*  @Override
    public Expense editExpense(Expense request, String copayer, String username) {
        //  User u = userservice.getUserbyname(copayerName);
        User u = new User(Long.valueOf(10),"Ramona");
        request.getCopayers().add(u);
        request.setUserPaid(username);
        expenseRepository.save(request);
        return request;
    }*/

    @Override
    public Expense updateExpense(Expense request, String name, double amount, String userpaid) {
        if(!name.isEmpty()){
            request.setName(name);
        }
        if(amount!= 0){
            request.setAmount(amount);
        }
        if(!userpaid.isEmpty()){

            request.setUserPaid(userpaid);
        }
        expenseRepository.save(request);
        return request;
    }

/*    @Override
    public Expense acceptExpense(long expenseid, String username) {

    Expense e =  expenseRepository.getOne(expenseid);
    Iterator<

            ExpenseUser> it = e.getExpenseUser().iterator();
        while(it.hasNext()) {
            ExpenseUser eu = it.next();

        }
        return null;
    }*/

    @Override
    public List<Expense> allExpensebyGroup(long groupid) {

    return expenseRepository.findBygroupid(groupid);


    }
}
