package com.example.demo.modules.debt;

import com.example.demo.modules.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    private Timestamp timestampCreation;
    
    @Getter
    @Setter
    private Timestamp timestampDeadline;

    @Getter
    @Setter
    @ManyToOne
    private User creditor; //gets the money

    @Getter
    @Setter
    @ManyToOne
    private User debtor; //owes the money

    @Getter
    @Setter
    private boolean creditorConfirmed;

    @Getter
    @Setter
    private boolean debtorConfirmed;

    public Debt (){};

    public Debt (Long id, boolean paid, Timestamp timestampCreation, Timestamp timestampDeadline, User creditor, User debtor, boolean creditorConfirmed,  boolean debtorConfirmed){
        this.id = id;
        this.paid = paid;
        this.timestampCreation = timestampCreation;
        this.timestampDeadline = timestampDeadline;
        this.creditor = creditor;
        this.debtor = debtor;
        this.creditorConfirmed = creditorConfirmed;
        this.debtorConfirmed = debtorConfirmed;
    }

    public Debt (boolean paid, Timestamp timestampCreation, Timestamp timestampDeadline, User creditor, User debtor, boolean creditorConfirmed,  boolean debtorConfirmed){
        this.paid = paid;
        this.timestampCreation = timestampCreation;
        this.timestampDeadline = timestampDeadline;
        this.creditor = creditor;
        this.debtor = debtor;
        this.creditorConfirmed = creditorConfirmed;
        this.debtorConfirmed = debtorConfirmed;
    }
}