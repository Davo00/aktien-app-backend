package com.example.demo.modules.debt;

import com.example.demo.modules.user.User;

import java.sql.Timestamp;
import java.util.List;

public interface DebtService {

    List<Debt> findAllDebt();
    Debt createDebt(Debt request);
    Debt proposeDebt(Debt oldDebt, Share stock, Timestamp timestamp, User user);
    Debt acceptDebt(Debt debt, User user);
}
