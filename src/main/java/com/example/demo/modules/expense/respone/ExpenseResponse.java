package com.example.demo.modules.expense.respone;


import com.example.demo.modules.expense.Expense;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ExpenseResponse {

    private long id;
    private String userPaid;
    private String name;
    private double amount;
    private String description;
    private long groupId;
    private boolean unpaid;
    private int consumerCount;
    private List<Long> copayerIds;

    public ExpenseResponse(long id, String userPaid, String name, double amount, String description,
                           boolean unpaid, int consumerCount) {
        this.id = id;
        this.userPaid = userPaid;
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.unpaid = unpaid;
        this.consumerCount = consumerCount;
    }

    public ExpenseResponse(Expense expense) {
        this(expense.getId(), expense.getUserPaid(), expense.getName(), expense.getAmount(), expense.getDescription(), expense.isUnpaid(), expense.getConsumerCount());

        if (expense.getGroupExpense() != null) {
            this.setGroupId(expense.getGroupExpense().getId());
        }
        if (expense.getCopayer() != null && !expense.getCopayer().isEmpty()) {
            this.copayerIds = new ArrayList<>();
            expense.getCopayer().forEach(copayer -> copayerIds.add(copayer.getId()));
        }

    }


}
