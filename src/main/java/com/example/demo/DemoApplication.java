package com.example.demo;

import com.example.demo.modules.expense.Expense;
import com.example.demo.modules.expense.ExpenseRepository;
import com.example.demo.modules.group.Group;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;



@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner init(ExpenseRepository expenseRepository){
        return args -> {
            Group g = new Group("WWI19SEB");
            Expense test = new Expense(g,"Hendrik", "Bier", 15.99, "Teuerstes Bier der Welt");
            expenseRepository.save(test);
        };
    }


}
