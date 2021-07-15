package com.example.demo.modules.expense;


import com.example.demo.modules.group.Group;
import com.example.demo.modules.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

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
    private String userPaid; //Username!!!!

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
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "group_id")
    @JsonBackReference
    private Group groupExpense;


    @Getter
    @Setter
    private boolean unpaid;

    @Getter
    @Setter
    private int consumerCount;

    @ManyToMany
    @JoinTable(name = "expense_player",
            joinColumns = @JoinColumn(name = "expense_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id"))
    @Getter
    @Setter
    //@JsonManagedReference
    private List<User> copayer = new ArrayList<>();


    public Expense() {
    }


    public Expense(@NotNull Long id, @NotNull Group group, String userPaid, String name, double amount, String description, List<User> copayer) {
        this.id = id;
        this.groupExpense = group;
        this.userPaid = userPaid;
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.unpaid = true;
        this.consumerCount = 0;
        this.copayer = copayer;
    }


    public Expense(@NotNull Group group, String userPaid, String name, double amount, String description, @NotNull List<User> copayers) {
        this.userPaid = userPaid;
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.unpaid = true;
        this.consumerCount = 0;
        this.groupExpense = group;
        //this.copayer = copayers;
        if (!copayers.isEmpty() && copayers != null) {
            copayers.forEach(user -> addUser(user));
        }
    }


    public void addUser(User user) {
        copayer.add(user);
        user.getExpense().add(this);
    }


}
