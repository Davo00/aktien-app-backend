package com.example.demo.modules.debt;

import com.example.demo.modules.user.User;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.security.Timestamp;

@Entity
public class Debt {

    @Id
    @GeneratedValue
    @Getter
    @Setter
    @NotNull
    private Long id;

    @Getter
    @Setter
    private boolean paid;

    @Getter
    @Setter
    private Timestamp timestamp_creation;
    
    @Getter
    @Setter
    private Timestamp timestamp_deadline;

    @Getter
    @Setter
    private User creditor; //gets the money

    @Getter
    @Setter
    private User debtor; //owes the money

    @Getter
    @Setter
    private boolean creditor_confirmed;

    @Getter
    @Setter
    private boolean debtor_confirmed;

    public Debt (){};

    public Debt (Long id, boolean paid, Timestamp timestamp_creation, Timestamp timestamp_deadline, User creditor, User debtor, boolean creditor_confirmed,  boolean debtor_confirmed){
        this.id = id;
        this.paid = paid;
        this.timestamp_creation = timestamp_creation;
        this.timestamp_deadline = timestamp_deadline;
        this.creditor = creditor;
        this.debtor = debtor;
        this.creditor_confirmed = creditor_confirmed;
        this.debtor_confirmed = debtor_confirmed;
    }

    public Debt (boolean paid, Timestamp timestamp_creation, Timestamp timestamp_deadline, User creditor, User debtor, boolean creditor_confirmed,  boolean debtor_confirmed){
        this.paid = paid;
        this.timestamp_creation = timestamp_creation;
        this.timestamp_deadline = timestamp_deadline;
        this.creditor = creditor;
        this.debtor = debtor;
        this.creditor_confirmed = creditor_confirmed;
        this.debtor_confirmed = debtor_confirmed;
    }
}
