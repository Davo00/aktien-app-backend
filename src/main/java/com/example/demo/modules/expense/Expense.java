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
