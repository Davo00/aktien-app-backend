package com.example.demo.modules.expense;




import com.example.demo.modules.user.User;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Expense {
    @Id
    @GeneratedValue
    @Getter
    @Setter
    @NotNull
    private Long id;

    @Getter
    @Setter
    private String userPaid;

    @Getter
    @Setter
    private String debtName;

    @Getter
    @Setter
    private double amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserPaid() {
        return userPaid;
    }

    public void setUserPaid(String userPaid) {
        this.userPaid = userPaid;
    }

    public String getDebtName() {
        return debtName;
    }

    public void setDebtName(String debtName) {
        this.debtName = debtName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Getter
    @Setter
    private String description;


    public Expense (){};


    public Expense(Long id, String userPaid, String debtName, double amount, String description) {
        this.id = id;
        this.userPaid = userPaid;
        this.debtName = debtName;
        this.amount = amount;
        this.description = description;
    }


    public Expense(String userPaid, String debtName, double amount, String description) {
        this.userPaid = userPaid;
        this.debtName = debtName;
        this.amount = amount;
        this.description = description;
    }
}
