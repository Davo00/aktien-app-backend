package com.example.demo.modules.debt;

import com.example.demo.modules.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DebtRepository extends JpaRepository<Debt, Long> {
    List<Debt> findAllByCreditorOrDebtor(User creditor, User debtor);
}
