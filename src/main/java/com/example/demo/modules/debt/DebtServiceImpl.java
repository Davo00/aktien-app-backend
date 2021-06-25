package com.example.demo.modules.debt;

import com.example.demo.modules.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
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

    @Override
    public Debt proposeDebt(Debt oldDebt, Share stock, Timestamp timestamp, User user){
        Debt newDebt = new Debt(false, null, timestamp, oldDebt.getCreditor(), oldDebt.getDebtor(), (user.getId() == oldDebt.getCreditor().getId())?true:false,  (user.getId() == oldDebt.getCreditor().getId())?false:true);
        debtRepository.deleteById(oldDebt.getId());
        debtRepository.save(newDebt);
        return newDebt;
    }

    @Override
    public Debt acceptDebt(Debt debt, User user){
        if(user.getId() == debt.getCreditor().getId()){
            debt.setCreditorConfirmed(true);
        }
        else if(user.getId() == debt.getDebtor().getId()){
            debt.setDebtorConfirmed(true);
        }
        if(debt.isCreditorConfirmed() && debt.isDebtorConfirmed()){
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            debt.setTimestampCreation(timestamp);
        }
        return debt;
    }

}
