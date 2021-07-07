package com.example.demo.modules.debt.response;


import com.example.demo.modules.debt.Debt;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class DebtResponse {

    private Long Id;
    private boolean paid;
    private Timestamp creation;
    private Timestamp deadline;
    private Long creditor;
    private Long debtor;


    public DebtResponse(Long id, boolean paid, Timestamp creation, Timestamp deadline, Long creditor, Long debtor) {
        Id = id;
        this.paid = paid;
        this.creation = creation;
        this.deadline = deadline;
        this.creditor = creditor;
        this.debtor = debtor;
    }

    public DebtResponse(Debt debt){
        this(debt.getId(), debt.isPaid(), debt.getTimestampCreation(), debt.getTimestampDeadline(),debt.getCreditor().getId(),debt.getDebtor().getId());
    }

}
