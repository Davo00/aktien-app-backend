package com.example.demo.modules.expense;


import com.example.demo.modules.user.User;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity

public class Expense {
    @Id
    @GeneratedValue
    @Getter
    @Setter
    @NotNull
    private long id;

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
    private long groupid;


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
    private Set<User> copayer = new HashSet<User>();


    public Expense() {
    }

    ;


    public Expense(@NotNull Long id, @NotNull Long groupid, String userPaid, String name, double amount, String description, Set<User> copayer) {
        this.id = id;
        this.groupid= groupid;
        this.userPaid = userPaid;
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.open = true;
        this.consumercount = 0;
        this.copayer = copayer;
    }


    public Expense(@NotNull Long groupid, String userPaid, String name, double amount, String description) {
        this.userPaid = userPaid;
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.open = true;
        this.consumercount = 0;
        this.groupid = groupid;
        this.copayer = new HashSet<User>();
    }


}
