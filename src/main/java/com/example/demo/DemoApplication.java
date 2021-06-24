package com.example.demo;

import com.example.demo.modules.debt.DebtRepository;
import com.example.demo.modules.expense.Expense;
import com.example.demo.modules.expense.ExpenseRepository;
import com.example.demo.modules.group.Group;
import com.example.demo.modules.group.GroupRepository;
import com.example.demo.modules.user.User;
import com.example.demo.modules.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner init(ExpenseRepository expenseRepository,
                           GroupRepository groupRepository,
                           UserRepository userRepository,
                           DebtRepository debtRepository){
        return args -> {

            User hendrik = new User("Hendrik", "hendrik@googlemail.com");
            User moritz = new User("Moritz", "moritz@googlemail.com");
            User davit = new User("Davit", "davit@googlemail.com");
            User ramona = new User("Ramona", "ramona@googlemail.com");


            List<User> userList = new ArrayList<>();
            //userList.add(hendrik);
            userList.add(moritz);
            userList.add(davit);
            userList.add(ramona);




            Group group = new Group("Shafi", userList);
            group.setMyUsers(userList);
            List<Group> groups = new ArrayList<>();
            groups.add(group);
            for(User user: userList){
                user.setJoinedGroups(groups);
            }

            groupRepository.save(group);
            userRepository.save(hendrik);
            userRepository.save(moritz);
            userRepository.save(davit);
            userRepository.save(ramona);


            Expense test = new Expense( group, "Hendrik", "Bier", 15.99, "Teuerstes Bier der Welt",userList);
            expenseRepository.save(test);


        };
    }


}
