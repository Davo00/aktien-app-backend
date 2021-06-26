package com.example.demo.modules.share;

import com.example.demo.modules.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.NumberValue;
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
    //@Lob
    private double price;

    @ManyToMany
    @JoinTable(name = "user_share",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "share_id"))
    @Getter
    @Setter
    @JsonBackReference
    private List<User> users = new ArrayList<>();


    public Share() {
    }

    public Share(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Share(String name, double price, List<User> users) {
        this.name = name;
        this.price = price;
        this.users = users;
    }

    /*public Money getcurrentPrice() {
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
    }*/

}
