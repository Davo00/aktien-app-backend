package com.example.demo.modules.debt;

import com.example.demo.modules.debt.request.AcceptDebt;
import com.example.demo.modules.debt.request.CreateDebt;
import com.example.demo.modules.debt.response.DebtResponse;
import com.example.demo.modules.share.Share;
import com.example.demo.modules.share.ShareRepository;
import com.example.demo.modules.user.User;
import com.example.demo.modules.user.UserRepository;
import com.example.demo.utils.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class DebtServiceImpl implements DebtService{

    @Autowired
    DebtRepository debtRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ShareRepository shareRepository;

    @Override
    public List<DebtResponse> findAllDebt(){
        List<Debt> allDebt = debtRepository.findAll();
        List<DebtResponse> debtResponses = new ArrayList<>();
        allDebt.forEach(debt -> debtResponses.add(new DebtResponse(debt)));
        return debtResponses;
    }

    @Override
    public Debt createDebt(CreateDebt request) throws NotFoundException{
        String endDate = request.getDeadline();
        Timestamp deadline= null;
        try {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = dateFormat.parse(endDate);
            long time = date.getTime();
            deadline = new Timestamp(time);
        }catch (Exception e){
            System.out.println("please provide the String in this \"dd/MM/yyyy\" format");
            e.printStackTrace();}



        User creditor = userRepository.findById(request.getCreditorId()).orElseThrow(()-> new NotFoundException("Creditor mit der Id " +request.getCreditorId() + " could not be found"));

        User debtor = userRepository.findById(request.getDebtorId()).orElseThrow(()-> new NotFoundException("Debtor with the Id " + request.getDebtorId() + " could not be found"));


        Share share = shareRepository.findById(request.getSelectedShareId()).orElseThrow(() -> new NotFoundException("Share with the Id " + request.getSelectedShareId() + " could not be found"));

        Debt debt = new Debt(request.isPaid(), /*request.getTimestampCreation(),*/ deadline, creditor, debtor, request.isCreditorConfirmed(), request.isDebtorConfirmed(), request.getGroupName(), share);
        debt = debtRepository.save(debt);
        return debt;
    }

    @Override
    public Debt proposeDebt(Debt oldDebt, Share stock, Timestamp timestamp, User user){
        Debt newDebt = new Debt(false, /*null,*/ timestamp, oldDebt.getCreditor(), oldDebt.getDebtor(), (user.getId() == oldDebt.getCreditor().getId())?true:false,  (user.getId() == oldDebt.getCreditor().getId())?false:true, oldDebt.getGroupName(), stock);
        debtRepository.deleteById(oldDebt.getId());
        debtRepository.save(newDebt);
        return newDebt;
    }

    @Override
    public Debt acceptDebt(long debtId, long userId) throws NotFoundException{

        Debt debt = debtRepository.findById(debtId).orElseThrow(()-> new NotFoundException("Debt with the Id " + debtId + " could not be found"));

        User user = userRepository.findById(userId).orElseThrow(()-> new NotFoundException("User with the Id " +userId +  " could not be found"));


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
        debt=debtRepository.save(debt);
        return debt;
    }

}
