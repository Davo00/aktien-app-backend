package com.example.demo.modules.debt;

import com.example.demo.modules.debt.request.AcceptDebt;
import com.example.demo.modules.debt.request.CreateDebt;
import com.example.demo.modules.debt.response.DebtResponse;
import com.example.demo.modules.share.Share;
import com.example.demo.modules.user.User;
import com.example.demo.utils.NotFoundException;

import java.sql.Timestamp;
import java.util.List;

public interface DebtService {

    List<DebtResponse> findAllDebt();
    DebtResponse createDebt(CreateDebt request) throws NotFoundException;
    Debt proposeDebt(Debt oldDebt, Share stock, Timestamp timestamp, User user);
    Debt acceptDebt(long debtId, long userId) throws NotFoundException;
}
