package com.example.demo.modules.expense;

import com.example.demo.modules.group.Group;
import com.example.demo.modules.group.GroupRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExpenseServiceImpl implements ExpenseService{

    @Autowired
    ExpenseRepository expenseRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    GroupRepository userRepository;

    @Override
    public List<Expense> findAllExpense() {
        List<Expense> allExpense = expenseRepository.findAll();
        return allExpense;
    }

    @Override
    public Expense createExpense(Expense request) {
        Expense expense = new Expense(request.getGroupid(), request.getUserPaid(), request.getName() , request.getAmount(), request.getDescription());
        expense = expenseRepository.save(expense);
        return expense;
    }

    @Override
    public Expense deleteExpense(Long id) {
        expenseRepository.deleteById(id);
        return null;
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
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new NotFoundException("Group could not be found"));
        List<Expense> e = expenseRepository.findBygroupid(groupId);
        return e;
    }




}



