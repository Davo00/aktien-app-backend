package com.example.demo.modules.share;

import com.example.demo.modules.user.User;
import lombok.Getter;
import lombok.Setter;
import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
public class Share {
    @Id
    @GeneratedValue
    @Getter
    @Setter
    @NotNull
    private Long id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Money price;

    @ManyToMany
    @JoinTable(name = "user_share",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "share_id"))
    @Getter
    @Setter
    private List<User> users = new ArrayList<>();

/*    @Getter
    @Setter
    @ManyToMany(mappedBy = "suggestions")
    private Set<Debt> debts;

    @Getter
    @Setter
    @OneToMany(mappedBy = "selectedShare", cascade = CascadeType.ALL)
    private List<Debt> usedFor;*/

    public Share() {
    }

    ;


    public Share(Long id, String name) {
        this.id = id;
        this.name = name;


    }


    public Share(Long id, String name, String abc) {
        this.id = id;
        this.name = name;
    }

    public Money getcurrentPrice() {
        Random generator = new Random();
        int amount = generator.nextInt(20 - 1) + 1;
        CurrencyUnit euro = Monetary.getCurrency("EUR");
        return Money.of(amount, euro);

    }

    public Money getPriceAt(Timestamp timestamp) {

        Random generator = new Random();
        Long time = timestamp.getTime();

        int amount = generator.nextInt(20 - 1) + 1;
        CurrencyUnit euro = Monetary.getCurrency("EUR");
        return Money.of(amount, euro);
    }


}
