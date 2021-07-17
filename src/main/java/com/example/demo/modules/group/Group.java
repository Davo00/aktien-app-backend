package com.example.demo.modules.group;

import com.example.demo.modules.expense.Expense;
import com.example.demo.modules.group.request.CreateGroup;
import com.example.demo.modules.user.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
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
    @JsonManagedReference
    private List<User> myUsers = new ArrayList<>();

    @Getter
    @Setter
    @OneToMany(mappedBy = "groupExpense", cascade = CascadeType.ALL)
    private List<Expense> myExpenses;


    public Group() {
    }

    public Group(@NotNull long id, @NotNull String name, List<User> myUsers) {
        this.id = id;
        this.name = name;
        this.myUsers = myUsers;
    }

    public Group(@NotNull String name) {
        this.name = name;
    }


    public Group(@NotNull String name, @NotNull List<User> myUsers) {
        this.name = name;
        if (!myUsers.isEmpty() && myUsers != null) {
            myUsers.forEach(user -> addUser(user));
        }
    }

    public void addUser(User user) {
        myUsers.add(user);
        user.getJoinedGroups().add(this);
    }


}
