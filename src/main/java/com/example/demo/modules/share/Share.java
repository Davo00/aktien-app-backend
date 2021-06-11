package com.example.demo.modules.share;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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

    public Share(){};


    public Share( Long id, String name) {
        this.id = id;
        this.name = name;

    }


    public Share(Long id, String name, String abc) {
        this.id = id;
        this.name = name;
    }
}
