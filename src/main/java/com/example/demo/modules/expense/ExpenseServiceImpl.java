package com.example.demo.modules.expense;

import com.example.demo.modules.expense.request.CreateExpense;
import com.example.demo.modules.expense.request.UpdateExpense;
import com.example.demo.modules.group.Group;
import com.example.demo.modules.group.GroupRepository;
import com.example.demo.modules.user.User;
import com.example.demo.modules.user.UserRepository;
import com.example.demo.utils.DeletionIntegrityException;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public Expense createExpense(CreateExpense request) throws NotFoundException {
        Group group = groupRepository.findById(request.getGroupId()).orElseThrow(() -> new com.example.demo.utils.NotFoundException("Group could not be found"));

        List<User> copayers = new ArrayList<>();
        for (String name: request.getCopayerNames()){
            User user = userRepository.findByUsername(name).orElseThrow(() ->
                    new UsernameNotFoundException("User " + name + " not found"));
            if(user ==null){
                throw new NotFoundException("User "+ name +" could not be found");
            }
            copayers.add(user);
        }
        Expense expense = new Expense(group,request.getUserPaid(), request.getName(), request.getAmount(), request.getDescription(), copayers);
        expense = expenseRepository.save(expense);
        return expense;
    }

    @Override
    public void deleteExpense(Long id) throws NotFoundException,DeletionIntegrityException  {
        Expense expense = expenseRepository.findById(id).orElseThrow(()-> new NotFoundException("Expense could not be found"));
        List<User> toSafeAtTheEnd = new ArrayList<>();
        Group safeGroup;

        try {
            for (User user : expense.getCopayer()) {
                user.getExpense().remove(expense);
                toSafeAtTheEnd.add(user);

            }
            expense.getCopayer().clear();
            expenseRepository.delete(expense);
            for (User user : toSafeAtTheEnd) {
                user = userRepository.save(user);
            }

        }catch (Exception e){

            throw new DeletionIntegrityException(e.getMessage());

        }



    }


    @Override
    public Expense updateExpensebyId(Long id, UpdateExpense request) throws NotFoundException {
       Expense expense = expenseRepository.findById(id).orElseThrow(() ->new NotFoundException("Expense could not be found"));

        List<User> toSafeAtTheEnd = new ArrayList<>();
        if (request.getUserIds()!= null && !request.getUserIds().isEmpty()){
           List<User> newUserList = new ArrayList<>();
           for(int i=0; i<request.getUserIds().size();i++){
             System.out.println(request.getUserIds().get(i));
              User user = userRepository.findById(request.getUserIds().get(i)).orElseThrow(() -> new NotFoundException("User could not be found"));
               if(user!=null){
                   newUserList.add(user);
               }
           }
           if(expense.getCopayer()!= null && !expense.getCopayer().isEmpty()){
               for(User user: expense.getCopayer()){
                   user.getExpense().remove(expense);
                   toSafeAtTheEnd.add(user);

               }

               expense.getCopayer().clear();

           }
            for ( User user : newUserList){
                expense.addUser(user);
                toSafeAtTheEnd.add(user);
            }


        }

        if (request.getName()!= null){
            expense.setName(request.getName());
        }


        expense.setAmount(request.getAmount());
        expense.setUnpaid(request.isOpen());
        expense.setConsumerCount(request.getConsumercount());

        if (request.getUserPaid()!= null){
            expense.setUserPaid(request.getUserPaid());
        }

        if (request.getDescription()!= null){
            expense.setDescription(request.getDescription());
        }

        if (request.getDescription()!= null){
            expense.setDescription(request.getDescription());
        }


        if (request.getDescription()!= null){
            expense.setDescription(request.getDescription());
        }



        expense= expenseRepository.save(expense);
        for(User user : toSafeAtTheEnd){
            user = userRepository.save(user);

        }

        return expense;

    }

    @Override
    public Expense one(Long id) throws NotFoundException{

        Expense expense= expenseRepository.findById(id).orElseThrow(() -> new NotFoundException("Group could not be found"));
        return expense;
    }

    @Override
    public List<Expense> getAllExpensebyGroup(long groupId) throws NotFoundException{
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new NotFoundException("Group could not be found"));
        List<Expense> e = expenseRepository.findByGroupExpense(group);
        return e;
    }




}



