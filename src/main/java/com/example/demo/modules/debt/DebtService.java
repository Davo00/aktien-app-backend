package com.example.demo.modules.debt;

import java.util.List;

public interface DebtService {

    List<Debt> findAllDebt();
    Debt createDebt(Debt request);
}
