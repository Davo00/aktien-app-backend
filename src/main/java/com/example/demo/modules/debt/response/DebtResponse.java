package com.example.demo.modules.debt.response;

import com.example.demo.modules.debt.Debt;
import com.example.demo.modules.share.Share;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class DebtResponse {

    private Long id;
    private double amount;
    private double shareProportion;
    private boolean paid;
    private Timestamp creation;
    private Timestamp deadline;
    private String creditorUsername;
    private String debtorUsername;
    private boolean creditorConfirmed;
    private boolean debtorConfirmed;
    private String groupName;
    private Share selectedShare;

    public DebtResponse(String message) {
        this.groupName = message;
    }


    public DebtResponse(Long id, double amount, boolean paid, Timestamp creation, Timestamp deadline,
                        String creditorUsername, String debtorUsername, boolean creditorConfirmed,
                        boolean debtorConfirmed, String groupName,
                        double shareProportion) {
        this.id = id;
        this.amount = amount;
        this.paid = paid;
        this.creation = creation;
        this.deadline = deadline;
        this.creditorUsername = creditorUsername;
        this.debtorUsername = debtorUsername;
        this.creditorConfirmed = creditorConfirmed;
        this.debtorConfirmed = debtorConfirmed;
        this.groupName = groupName;
        this.shareProportion = shareProportion;

    }

    public DebtResponse(Debt debt) {
        this(
                debt.getId(),
                debt.getAmount(),
                debt.isPaid(),
                debt.getTimestampCreation(),
                debt.getTimestampDeadline(),
                debt.getCreditor().getUsername(),
                debt.getDebtor().getUsername(),
                debt.isCreditorConfirmed(),
                debt.isDebtorConfirmed(),
                debt.getGroupName(),
                debt.getShareProportion()
        );

        if (debt.getSelectedShare() != null) {
            this.setSelectedShare(debt.getSelectedShare());
        }
    }

}
