package com.example.demo.modules.debt;

import com.example.demo.modules.share.Share;
import com.example.demo.modules.calculation.response.WhoOwesWhom;
import com.example.demo.modules.share.Share;
import com.example.demo.modules.user.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

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
    private double amount;

    @Getter
    @Setter
    @CreationTimestamp
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


    @Getter
    @Setter
    private String groupName;

    /*@Getter
    @Setter
    @ManyToMany//(cascade = CascadeType.ALL)
    private List<Share> suggestions;*/

    @Getter
    @Setter
    @ManyToOne//(cascade = CascadeType.ALL)
    private Share selectedShare;

    public Debt() {
    }

    ;

    public Debt(Long id, boolean paid, double amount, Timestamp timestampCreation, Timestamp timestampDeadline, User creditor, User debtor, boolean creditorConfirmed, boolean debtorConfirmed, String groupName, Share selectedShare) {
        this.id = id;
        this.paid = paid;
        this.amount = amount;
        this.timestampCreation = timestampCreation;
        this.timestampDeadline = timestampDeadline;
        this.creditor = creditor;
        this.debtor = debtor;
        this.creditorConfirmed = creditorConfirmed;
        this.debtorConfirmed = debtorConfirmed;
        this.groupName = groupName;
        this.selectedShare = selectedShare;
    }

    public Debt(boolean paid, double amount,/*Timestamp timestampCreation,*/ Timestamp timestampDeadline, User creditor, User debtor, boolean creditorConfirmed, boolean debtorConfirmed, String groupName, Share selectedShare) {
        this.paid = paid;
        //this.timestampCreation = timestampCreation;
        this.amount = amount;
        this.timestampDeadline = timestampDeadline;
        this.creditor = creditor;
        this.debtor = debtor;
        this.creditorConfirmed = creditorConfirmed;
        this.debtorConfirmed = debtorConfirmed;
        this.groupName = groupName;
        this.selectedShare = selectedShare;
    }



/*    @OneToMany(mappedBy = "selectedForDebt")
    private Collection<Share> share;*/

 /*   public Collection<Share> getShare() {
        return share;
    }
*/
 /*   public void setShare(Collection<Share> share) {
        this.share = share;
    }*/
}
