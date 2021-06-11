package com.example.demo.modules.group;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Group {

    @Id
    @GeneratedValue
    @NotNull
    @Getter
    @Setter
    private long id;

    @NotNull
    @Column(unique = true)
    @Getter
    @Setter
    private String name;

    public Group() {
    }

    public Group(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Group(String name) {
        this.name = name;
    }

}
