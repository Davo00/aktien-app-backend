package com.example.demo.modules.debt.request;


import com.example.demo.modules.debt.Debt;
import com.example.demo.modules.share.Share;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDebt {

    private boolean paid;
    private double amount;
    private String deadline; //   "dd/mm/yyyy"
    private Long creditorId;
    private Long debtorId;
    private boolean creditorConfirmed;
    private boolean debtorConfirmed;
    private String groupName;
    private Long selectedShareId;

    public CreateDebt(boolean paid, double amount, String deadline, Long creditorId, Long debtorId,
                      boolean creditorConfirmed, boolean debtorConfirmed, String groupName, Long selectedShareId) {
        this.paid = paid;
        this.amount = amount;
        this.deadline = deadline;
        this.creditorId = creditorId;
        this.debtorId = debtorId;
        this.creditorConfirmed = creditorConfirmed;
        this.debtorConfirmed = debtorConfirmed;
        this.groupName = groupName;
        this.selectedShareId = selectedShareId;
    }

    public CreateDebt(Debt debt) {
        this(false, debt.getAmount(), null, debt.getCreditor().getId(), debt.getDebtor().getId(), false, false, debt.getGroupName(), null);
    }
}
