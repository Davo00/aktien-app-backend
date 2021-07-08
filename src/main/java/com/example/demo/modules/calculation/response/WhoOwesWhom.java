package com.example.demo.modules.calculation.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WhoOwesWhom {

    private String creditor;
    private String debitor;
    private double amount;



    public WhoOwesWhom(String creditor, String debitor, double amount) {
        this.creditor = creditor;
        this.debitor = debitor;
        this.amount = amount;
    }

}


