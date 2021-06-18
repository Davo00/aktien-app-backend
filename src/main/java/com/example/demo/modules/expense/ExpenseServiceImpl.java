package com.example.demo.modules.expense;

import com.example.demo.modules.group.GroupRepository;
import com.example.demo.modules.user.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExpenseServiceImpl implements ExpenseService{

    @Autowired
    ExpenseRepository expenseRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public List<Expense> findAllExpense() {
        List<Expense> allExpense = expenseRepository.findAll();
        return allExpense;
    }

    @Override
    public Expense createExpense(Expense request) {
        Expense expense = new Expense(request.getGroupExpense(), request.getUserPaid(), request.getName() , request.getAmount(), request.getDescription());
        expense = expenseRepository.save(expense);
        return expense;
    }

    @Override
    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }


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

    @Override
    public Expense one(Long id){
        return expenseRepository.getOne(id);
    }

    @Override
    public List<Expense> getAllExpensebyGroup(long groupId) throws NotFoundException{
       /* Group group = groupRepository.findById(groupId).orElseThrow(() -> new NotFoundException("Group could not be found"));
        List<Expense> e = expenseRepository.findByGroupExpense(group);*/
        List<Expense> e = new ArrayList<Expense>();
        return e;
    }




}



