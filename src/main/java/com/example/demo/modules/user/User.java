package com.example.demo.modules.user;

import com.example.demo.modules.group.Group;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue
    @NotNull
    @Getter
    @Setter
    private long id;

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
    @JoinTable(name = "user_group",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<Group> joinedGroups;

    public User() {
    }

    public User(@NotNull long id, @NotNull String username, @NotNull String email, double overall_score, Set<Group> myGroups) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.overall_score = overall_score;
        this.joinedGroups = myGroups;
    }

    public User(@NotNull String username, @NotNull String email) {
        this.username = username;
        this.email = email;
    }
}
