package com.example.demo.modules.debt;

import com.example.demo.modules.debt.request.CreateDebt;
import com.example.demo.modules.debt.request.ProposeDebt;
import com.example.demo.modules.debt.response.DebtResponse;
import com.example.demo.modules.user.User;
import com.example.demo.utils.NotFoundException;

import java.sql.Timestamp;
import java.util.List;

public interface DebtService {

    List<DebtResponse> findAllDebt();
    DebtResponse createDebt(CreateDebt request) throws NotFoundException;
    DebtResponse proposeDebt (User user, ProposeDebt proposeDebt) throws Exception;
    DebtResponse acceptDebt(Long debtId, Long userId) throws Exception;
}
