package com.example.demo.modules.calculation.response;

import com.example.demo.modules.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreditOverview {
    private String username;
    private double credit;


    public CreditOverview(User user) {
        this.username = user.getUsername();
        this.credit = 0;
    }

    public void addToCredit(double amount, int copayerCount) {
        double amountPerPerson = (amount / copayerCount);
        amountPerPerson = Math.round(100.0 * amountPerPerson) / 100.0;
        credit += (amountPerPerson * copayerCount);
        credit = Math.round(100.0 * credit) / 100.0;
    }

    public void addToCredit(double amount) {
        credit += amount; //amount ist positiv aber credit ist negativ
        credit = Math.round(100.0 * credit) / 100.0;
    }

    public void takeFromCredit(double amount, int copayerCount) {
        credit -= (amount / copayerCount);
        credit = Math.round(100.0 * credit) / 100.0;
    }

    public void takeFromCredit(double amount) {
        credit += amount; // amount ist immer negativ
        credit = Math.round(100.0 * credit) / 100.0;

    }

}
