package com.example.demo.modules.calculation;

import com.example.demo.modules.calculation.response.CreditOverview;
import com.example.demo.modules.calculation.response.WhoOwesWhom;
import com.example.demo.modules.debt.Debt;
import com.example.demo.modules.debt.DebtRepository;
import com.example.demo.modules.debt.request.CreateDebt;
import com.example.demo.modules.debt.response.DebtResponse;
import com.example.demo.modules.expense.Expense;
import com.example.demo.modules.expense.ExpenseRepository;
import com.example.demo.modules.group.Group;
import com.example.demo.modules.group.GroupRepository;
import com.example.demo.modules.user.User;
import com.example.demo.modules.user.UserRepository;
import com.example.demo.utils.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CalculationServiceImpl implements CalculationService{

    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DebtRepository debtRepository;


    @Override
    public List<CreditOverview> calculateOverview(long groupId) throws NotFoundException{
        Group group = groupRepository.findById(groupId).orElseThrow(()-> new NotFoundException("Group with groupId " + groupId +" could not be found "));
        List <Expense> allExpense = expenseRepository.findByGroupExpense(group);
        if (allExpense.isEmpty() || allExpense == null){
            throw new NotFoundException("No Expenses could be found");
        }
        List<CreditOverview> creditOverviews= new ArrayList<>();
        for(User user : group.getMyUsers()){
            creditOverviews.add(new CreditOverview(user));
        }

        for(Expense expense: allExpense){
            if(!expense.isUnpaid()){
                continue;
            }
            for(CreditOverview creditOverview : creditOverviews){
                if(expense.getUserPaid().equals(creditOverview.getUsername())){
                    creditOverview.addToCredit(expense.getAmount(), expense.getCopayer().size());
                }

                for(User user : expense.getCopayer()) {
                    if (user.getUsername().equals(creditOverview.getUsername())){
                        creditOverview.takeFromCredit(expense.getAmount(),expense.getCopayer().size());
                    }
                }
            }
        }

        return creditOverviews;
    }


    @Override
    public List<WhoOwesWhom> calculateDebts(long groupId) throws NotFoundException {
        List<WhoOwesWhom> whoOwesWhomList = new ArrayList<>();
        List <CreditOverview> creditOverviews = calculateOverview(groupId);
        int creditZeroCounter=0;
        boolean allZero=false;
        for (CreditOverview creditOverview: creditOverviews){
            if(creditOverview.getCredit()==0){
                creditZeroCounter++;
            }
        }
        if(creditOverviews.size()==creditZeroCounter){
            allZero=true;
        }


        if (creditOverviews==null || creditOverviews.isEmpty() || allZero){
            List<WhoOwesWhom> returnable = new ArrayList<>();
            returnable.add(new WhoOwesWhom("Nobody owes Nobody" ,"Nobody", 0.0));//do not change
            return returnable;
        }

        int usersAmoutCalculated=0;


        CreditOverview highest=null;
        CreditOverview lowest=null;

        while(creditOverviews.size()-1>usersAmoutCalculated){

            for(CreditOverview creditOverview: creditOverviews){
                if (creditOverview.getCredit()==0){
                    continue;
                }
                if(highest==null|| highest.getCredit()<creditOverview.getCredit()){
                    highest=creditOverview;
                }
                if(lowest==null|| lowest.getCredit()>creditOverview.getCredit()){
                    lowest=creditOverview;
                }
            }

            if(highest.getCredit()>(lowest.getCredit()*(-1))){
                whoOwesWhomList.add(new WhoOwesWhom(highest.getUsername(), lowest.getUsername(), lowest.getCredit()*-1));
                highest.takeFromCredit(lowest.getCredit());
                lowest.setCredit(0.0);
                usersAmoutCalculated++;
            }

            if(highest.getCredit()== (lowest.getCredit()*(-1))){
                whoOwesWhomList.add(new WhoOwesWhom(highest.getUsername(), lowest.getUsername(), lowest.getCredit()*-1));
                highest.takeFromCredit(lowest.getCredit());
                lowest.setCredit(0.0);
                usersAmoutCalculated++;
                usersAmoutCalculated++;
            }

            if(highest.getCredit()<(lowest.getCredit())*(-1)){
                whoOwesWhomList.add(new WhoOwesWhom(highest.getUsername(), lowest.getUsername(), highest.getCredit()));
                lowest.addToCredit(highest.getCredit());
                highest.setCredit(0.0);
                usersAmoutCalculated++;
            }

        }//while



        return whoOwesWhomList;
    }

    @Override
    public List<DebtResponse> finalCalculation(long groupId) throws NotFoundException {

        Group group = groupRepository.findById(groupId).orElseThrow(()-> new NotFoundException("Group with groupId " + groupId +" could not be found "));

        List<WhoOwesWhom> whoOwesWhomList = calculateDebts(groupId);

        String firstCreditor = whoOwesWhomList.get(0).getCreditor();
        if(firstCreditor.equals("Nobody owes Nobody")){
            List<DebtResponse> returnable = new ArrayList<>();
            returnable.add(new DebtResponse("No debt possible as nobody owes anything to nobody"));
            return returnable;
        }

        List <Expense> allExpense = expenseRepository.findByGroupExpense(group);
        for(Expense expense: allExpense){
            expense.setUnpaid(false);
        }
        List<Debt> allNewDebts = new ArrayList<>();

        for (WhoOwesWhom whom : whoOwesWhomList){
            User creditor = userRepository.findByUsername(whom.getCreditor());
            User debitor = userRepository.findByUsername(whom.getDebitor());
            allNewDebts.add(new Debt(false, whom.getAmount(), null, creditor, debitor, false, false, group.getName(), null));
        }

        for (Debt debt : allNewDebts){
            debt= debtRepository.save(debt);
        }

        List<DebtResponse> returnableDebts = new ArrayList<>();
        for(Debt debt: allNewDebts){
            returnableDebts.add(new DebtResponse(debt));
        }

        return returnableDebts;
    }
}
