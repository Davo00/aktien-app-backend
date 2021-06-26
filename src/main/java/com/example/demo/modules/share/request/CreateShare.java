package com.example.demo.modules.share.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.money.NumberValue;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreateShare {
    private String name;
    private double price;
    private List<String> userNames;
}
