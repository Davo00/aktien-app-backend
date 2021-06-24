package com.example.demo.modules.user;

import com.example.demo.modules.expense.Expense;
import com.example.demo.modules.group.Group;
import com.example.demo.modules.share.Share;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
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
    private double overall_score;

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
    private List<Share> preferedShares;



    @Getter
    @Setter
    @ManyToMany(mappedBy = "copayer")
    private Set<Expense> expense;

    public User() {
    }

    public User(@NotNull long id, @NotNull String username, @NotNull String email, double overall_score, List<Group> myGroups) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.overall_score = overall_score;
        this.joinedGroups = myGroups;
    }

    public User(@NotNull String username, @NotNull String email) {
        this.username = username;
        this.email = email;
        this.joinedGroups = new ArrayList<>();
    }
}
