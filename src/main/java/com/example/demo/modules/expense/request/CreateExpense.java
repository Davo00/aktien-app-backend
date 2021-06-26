package com.example.demo.modules.expense.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreateExpense {
    private Long groupId;
    private String userPaid;
    private String name;
    private double amount;
    private String description;
    private List<String> copayerNames;
}
