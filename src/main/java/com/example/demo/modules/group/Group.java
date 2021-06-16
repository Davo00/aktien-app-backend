package com.example.demo.modules.group;

import com.example.demo.modules.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity(name = "pool")
public class Group {

    @Id
    @GeneratedValue
    @Getter
    @Setter
    private @NotNull Long id;

    @NotNull
    @Column(unique = true)
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @ManyToMany(mappedBy = "joinedGroups")
    private Set<User> myUsers;

    public Group() {
    }

    public Group(@NotNull long id, @NotNull String name, Set<User> myUsers) {
        this.id = id;
        this.name = name;
        this.myUsers = myUsers;
    }

    public Group(@NotNull String name) {
        this.name = name;
    }

    public Group(@NotNull String name, @NotNull Set<User> myUsers) {
        this.name = name;
        this.myUsers = myUsers;
    }

}
