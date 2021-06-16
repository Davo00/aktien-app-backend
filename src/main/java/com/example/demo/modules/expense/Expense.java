package com.example.demo.modules.expense;


import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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

/*    @Getter
    @Setter
    @Fetch(FetchMode.SELECT)
    @ManyToMany
    private List<User> copayers= new ArrayList<>();*/

    /*@ManyToMany
    @JoinTable(name = "users",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "expense_id"))
    private Set<User> copayers;*/


 /*   @Transient
    @Fetch(FetchMode.SELECT)*/
 //   private ArrayList<User> users = new ArrayList<User>();






    public Expense (){};

    
    
    
    


    public Expense(Long id, String userPaid, String name, double amount, String description) {
        this.id = id;
        this.userPaid = userPaid;
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.open = true;
        
        
        this.consumercount = 0;
    //    this.copayers = copayers;
    }


    public Expense(String userPaid, String name, double amount, String description) {
        this.userPaid = userPaid;
        this.name = name;
        this.amount = amount;
        this.description = description;

        this.open = true;
        this.consumercount = 0;
       // this.copayers = copayers;
    }



}
