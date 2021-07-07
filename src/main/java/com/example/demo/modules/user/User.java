package com.example.demo.modules.user;

import com.example.demo.modules.expense.Expense;
import com.example.demo.modules.group.Group;
import com.example.demo.modules.share.Share;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class User {
    @Id
    @GeneratedValue
    @Getter
    @Setter
    private @NotNull Long id;

    @NotNull
    @Getter
    @Setter
    @Column(unique = true)
    private String username;

    @NotNull
    @Getter
    @Setter
    @Column(unique = true)
    private String email;

    @Getter
    @Setter
    private double overallScore;

    @Getter
    @Setter
    @ManyToMany
    @JoinTable(name = "player_pool",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "pool_id", nullable = true))
    @JsonBackReference
    private List<Group> joinedGroups;

    @Getter
    @Setter
    @ManyToMany(mappedBy = "users")
    @JsonManagedReference
    private List<Share> preferedShares;



    @Getter
    @Setter
    @ManyToMany(mappedBy = "copayer")
    //@JsonBackReference
    private List<Expense> expense = new ArrayList<>();

    public User() {
    }

    public User(@NotNull long id, @NotNull String username, @NotNull String email, double overall_score, List<Group> myGroups) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.overallScore = overall_score;
        this.joinedGroups = myGroups;
    }

    public User(@NotNull String username, @NotNull String email) {
        this.username = username;
        this.email = email;
        this.joinedGroups = new ArrayList<>();
    }
}
