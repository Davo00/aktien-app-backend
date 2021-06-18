package com.example.demo.modules.expense;

import com.example.demo.modules.group.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByGroupExpense(Group group);


}
