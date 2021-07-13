package com.example.demo.modules.user;

import com.example.demo.modules.expense.Expense;
import com.example.demo.modules.group.Group;
import com.example.demo.modules.share.Share;
import com.example.demo.modules.user.request.CreateUser;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class User implements UserDetails {
    @Id
    @GeneratedValue
    @Getter
    @Setter
    @NotNull
    private Long id;

    @NotNull
    @Getter
    @Setter
    @Column(unique = true)
    private String username;

    @NotNull
    @Getter
    @Setter
    private String password;

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

    public User(@NotNull long id, @NotNull String username, @NotNull String password, @NotNull String email, double overall_score, List<Group> myGroups) {
        this.id = id;
        this.username = username;
        this.password = passwordEncoder().encode(password);
        this.email = email;
        this.overallScore = overall_score;
        this.joinedGroups = myGroups;
    }

    public User(@NotNull String username, @NotNull String password, @NotNull String email) {
        this.username = username;
        this.password = passwordEncoder().encode(password);
        this.email = email;
        this.joinedGroups = new ArrayList<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isEnabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isEnabled();
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
