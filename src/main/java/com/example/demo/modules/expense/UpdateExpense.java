package com.example.demo.modules.expense;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UpdateExpense{
    private String userPaid;
    private double amount;
    private String description;
    private String name;
    private boolean open;
    private int consumercount;

    private List<Long> userIds;
    private Long groupIds;
}