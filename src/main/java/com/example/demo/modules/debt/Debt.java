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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public Timestamp getTimestampCreation() {
        return timestampCreation;
    }

    public void setTimestampCreation(Timestamp timestampCreation) {
        this.timestampCreation = timestampCreation;
    }

    public Timestamp getTimestampDeadline() {
        return timestampDeadline;
    }

    public void setTimestampDeadline(Timestamp timestampDeadline) {
        this.timestampDeadline = timestampDeadline;
    }

    public User getCreditor() {
        return creditor;
    }

    public void setCreditor(User creditor) {
        this.creditor = creditor;
    }

    public User getDebtor() {
        return debtor;
    }

    public void setDebtor(User debtor) {
        this.debtor = debtor;
    }

    public boolean isCreditorConfirmed() {
        return creditorConfirmed;
    }

    public void setCreditorConfirmed(boolean creditorConfirmed) {
        this.creditorConfirmed = creditorConfirmed;
    }

    public boolean isDebtorConfirmed() {
        return debtorConfirmed;
    }

    public void setDebtorConfirmed(boolean debtorConfirmed) {
        this.debtorConfirmed = debtorConfirmed;
    }

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
    private User creditor; //gets the money

    @Getter
    @Setter
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
