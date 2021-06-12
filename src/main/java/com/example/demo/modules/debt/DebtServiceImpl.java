package com.example.demo.modules.debt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DebtServiceImpl implements DebtService{

    @Autowired
    DebtRepository debtRepository;

    @Override
    public List<Debt> findAllDebt(){
        List<Debt> allDebt = debtRepository.findAll();
        return allDebt;
    }

    @Override
    public Debt createDebt(Debt request){
        Debt debt = new Debt(request.isPaid(), request.getTimestampCreation(), request.getTimestampDeadline(), request.getCreditor(), request.getDebtor(), request.isCreditorConfirmed(), request.isDebtorConfirmed());
        debt = debtRepository.save(debt);
        return debt;
    }

}
