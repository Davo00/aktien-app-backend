package com.example.demo.modules.debt.response;


import com.example.demo.modules.debt.Debt;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class DebtResponse {

    private Long Id;
    private double amount;
    private boolean paid;
    private Timestamp creation;
    private Timestamp deadline;
    private Long creditorId;
    private Long debtorId;
    private boolean creditorConfirmed;
    private boolean debtorConfirmed;
    private String groupName;
    private Long selectedShareId;


    public DebtResponse(Long id, double amount, boolean paid, Timestamp creation, Timestamp deadline, Long creditorId
            , Long debtorId, boolean creditorConfirmed, boolean debtorConfirmed, String groupName) {
        Id = id;
        this.amount = amount;
        this.paid = paid;
        this.creation = creation;
        this.deadline = deadline;
        this.creditorId = creditorId;
        this.debtorId = debtorId;
        this.creditorConfirmed = creditorConfirmed;
        this.debtorConfirmed = debtorConfirmed;
        this.groupName = groupName;

    }

    public DebtResponse(Debt debt){
        this(debt.getId(),
                debt.getAmount(),
                debt.isPaid(),
                debt.getTimestampCreation(),
                debt.getTimestampDeadline(),
                debt.getCreditor().getId(),
                debt.getDebtor().getId(),
                debt.isCreditorConfirmed(),
                debt.isDebtorConfirmed(),
                debt.getGroupName());

        if(debt.getSelectedShare()!=null){
            this.setSelectedShareId(debt.getSelectedShare().getId());
        }
    }

}
