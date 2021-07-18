package com.example.demo.modules.expense;

import com.example.demo.modules.expense.request.CreateExpense;
import com.example.demo.modules.expense.request.UpdateExpense;
import com.example.demo.modules.expense.respone.ExpenseResponse;
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
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    ExpenseRepository expenseRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public List<ExpenseResponse> findAllExpense() {
        List<Expense> allExpense = expenseRepository.findAll();
        List<ExpenseResponse> expenseResponseList = new ArrayList<>();
        allExpense.forEach(expense -> expenseResponseList.add(new ExpenseResponse(expense)));
        return expenseResponseList;
    }


    @Override
    public ExpenseResponse createExpense(CreateExpense request) throws NotFoundException {
        Group group = groupRepository.findById(request.getGroupId()).orElseThrow(() -> new com.example.demo.utils.NotFoundException("Group could not be found"));

        List<User> copayers = new ArrayList<>();
        for (String name : request.getCopayerNames()) {
            User user = userRepository.findByUsername(name).orElseThrow(() ->
                    new UsernameNotFoundException("User " + name + " not found"));
            copayers.add(user);
        }
        User user = userRepository.findByUsername(request.getUserPaid()).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        if (!user.getUsername().equals(request.getUserPaid())) {
            throw new NotFoundException("The User who paid the bill could not be found, please enter a valid username");
        }

        Expense expense = new Expense(group, request.getUserPaid(), request.getName(), request.getAmount(), request.getDescription(), copayers);
        expense = expenseRepository.save(expense);
        return new ExpenseResponse(expense);
    }


    @Override
    public void deleteExpense(Long id) throws NotFoundException, DeletionIntegrityException {
        Expense expense = expenseRepository.findById(id).orElseThrow(() -> new NotFoundException("Expense could not be found"));
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

        } catch (Exception e) {

            throw new DeletionIntegrityException(e.getMessage());

        }


    }


    @Override
    public ExpenseResponse updateExpensebyId(Long id, UpdateExpense request) throws NotFoundException {
        Expense expense = expenseRepository.findById(id).orElseThrow(() -> new NotFoundException("Expense could not be found"));
        User userPaid = userRepository.findByUsername(request.getUserPaid()).orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        List<User> toSafeAtTheEnd = new ArrayList<>();

        if (request.getUserNames() != null && !request.getUserNames().isEmpty()) {
            List<User> newUserList = new ArrayList<>();
            for (int i = 0; i < request.getUserNames().size(); i++) {
                User user = userRepository.findByUsername(request.getUserNames().get(i)).orElseThrow(() -> new NotFoundException("User could not be found"));
                newUserList.add(user);
            }
            if (expense.getCopayer() != null && !expense.getCopayer().isEmpty()) {
                for (User user : expense.getCopayer()) {
                    user.getExpense().remove(expense);
                    toSafeAtTheEnd.add(user);

                }

                expense.getCopayer().clear();

            }
            for (User user : newUserList) {
                expense.addUser(user);
                toSafeAtTheEnd.add(user);
            }


        }

        if (request.getName() != null) {
            expense.setName(request.getName());
        }


        expense.setAmount(request.getAmount());
        //expense.setUnpaid(request.isOpen());
        expense.setConsumerCount(request.getConsumercount());

        if (request.getUserPaid() != null) {
            expense.setUserPaid(request.getUserPaid());
        }

        if (request.getDescription() != null) {
            expense.setDescription(request.getDescription());
        }



        expense = expenseRepository.save(expense);
        for (User user : toSafeAtTheEnd) {
            userRepository.save(user);

        }


        return new ExpenseResponse(expense);

    }

    @Override
    public Expense one(Long id) throws NotFoundException {
        return expenseRepository.findById(id).orElseThrow(() -> new NotFoundException("Group could not be found"));
    }

    @Override
    public List<ExpenseResponse> getAllExpensebyGroup(long groupId) throws NotFoundException {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new NotFoundException("Group could not be found"));
        List<Expense> e = expenseRepository.findByGroupExpense(group);
        List<ExpenseResponse> expenseResponseList = new ArrayList<>();
        e.forEach(expense -> expenseResponseList.add(new ExpenseResponse(expense)));
        return expenseResponseList;
    }


}



