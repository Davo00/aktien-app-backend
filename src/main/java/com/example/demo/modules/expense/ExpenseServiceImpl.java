package com.example.demo.modules.expense;

import com.example.demo.modules.group.Group;
import com.example.demo.modules.group.GroupRepository;
import com.example.demo.modules.user.User;
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
        Expense expense = new Expense(request.getGroupExpense(),request.getUserPaid(), request.getName(), request.getAmount(), request.getDescription(), request.getCopayer());
        expense = expenseRepository.save(expense);
        return expense;
    }

    @Override
    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }


    @Override
    public Expense updateExpensebyId(Long id, UpdateExpense request) throws NotFoundException {
       Expense expense = expenseRepository.findById(id).orElseThrow(() ->new NotFoundException("Expense could not be found"));
        //Group

       //Am Montag nochmal mit Hendrik besprechen




        //Group


        List<User> toSafeAtTheEnd = new ArrayList<>();
        if (request.getUserIds()!= null && !request.getUserIds().isEmpty()){
           List<User> newUserList = new ArrayList<>();
           for(int i=0; i<request.getUserIds().size();i++){
             System.out.println(request.getUserIds().get(i));
              User user = userRepository.findById(request.getUserIds().get(i)).orElseThrow(() -> new NotFoundException("User could not be found"));
               if(user!=null){
                   newUserList.add(user);
                   System.out.println(user.getUsername());
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
                System.out.println("to Add" + user.getUsername());
                expense.addUser(user);
                toSafeAtTheEnd.add(user);
            }


        }

        if (request.getName()!= null){
            expense.setName(request.getName());
        }


        expense.setAmount(request.getAmount());
        expense.setOpen(request.isOpen());
        expense.setConsumercount(request.getConsumercount());

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


        //andere Attribute noch setzen





        expense= expenseRepository.save(expense);
        for(User user : toSafeAtTheEnd){
            user = userRepository.save(user);

        }


        return null;

    }

    @Override
    public Expense one(Long id){
        return expenseRepository.getOne(id);
    }

    @Override
    public List<Expense> getAllExpensebyGroup(long groupId) throws NotFoundException{
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new NotFoundException("Group could not be found"));
        List<Expense> e = expenseRepository.findByGroupExpense(group);
      //  List<Expense> e = new ArrayList<Expense>();
        return e;
    }




}



