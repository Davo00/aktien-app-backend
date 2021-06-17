package com.example.demo.modules.expense;


import com.example.demo.modules.group.Group;
import com.example.demo.modules.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

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
    private String name;

    @Getter
    @Setter
    private double amount;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    @ManyToOne()
    @JoinColumn(name="pool_id")
    private Group groupExpense;


    @Getter
    @Setter
    private boolean open;

    @Getter
    @Setter
    private int consumercount;

    @ManyToMany
    @JoinTable(name = "player_expense",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "expense_id"))
    @Getter
    @Setter
    private Set<User> copayer;


    public Expense() {
    }

    ;


    public Expense(@NotNull Long id, @NotNull Group group, String userPaid, String name, double amount, String description, Set<User> copayer) {
        this.id = id;
        this.groupExpense = group;
        this.userPaid = userPaid;
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.open = true;
        this.consumercount = 0;
        this.copayer = copayer;
    }


    public Expense(@NotNull Group group, String userPaid, String name, double amount, String description) {
        this.userPaid = userPaid;
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.open = true;
        this.consumercount = 0;
        this.groupExpense = group;
        this.copayer = new HashSet<User>();
    }


}
