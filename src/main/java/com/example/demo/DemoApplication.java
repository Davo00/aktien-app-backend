package com.example.demo;

import com.example.demo.modules.debt.Debt;
import com.example.demo.modules.debt.DebtRepository;
import com.example.demo.modules.expense.Expense;
import com.example.demo.modules.expense.ExpenseRepository;
import com.example.demo.modules.group.Group;
import com.example.demo.modules.group.GroupRepository;
import com.example.demo.modules.share.Share;
import com.example.demo.modules.share.ShareRepository;
import com.example.demo.modules.user.User;
import com.example.demo.modules.user.UserRepository;
import org.javamoney.moneta.Money;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.NumberValue;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
                           DebtRepository debtRepository,
                           ShareRepository shareRepository){
        return args -> {

            User hendrik = new User("Hendrik", "hendrik@googlemail.com");
            User moritz = new User("Moritz", "moritz@googlemail.com");
            User davit = new User("Davit", "davit@googlemail.com");
            User ramona = new User("Ramona", "ramona@googlemail.com");


            /*CurrencyUnit euro = Monetary.getCurrency("EUR");
            int amount = 120;
            Money m =  Money.of(amount, euro);
            MonetaryAmount monetaryAmount = Money.of(123, "EUR");
            NumberValue value = monetaryAmount.getNumber();*/


            List<User> userList = new ArrayList<>();
            //userList.add(hendrik);
            userList.add(moritz);
            userList.add(davit);
            userList.add(ramona);

            List<User> sapEmployees = new ArrayList<>();
            sapEmployees.add(davit);
            sapEmployees.add(ramona);
            sapEmployees.add(moritz);


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

            Share s = new Share("SAP",127.99, sapEmployees);
            shareRepository.save(s);

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = dateFormat.parse("23/09/2021");
            long time = date.getTime();
            Timestamp deadline = new Timestamp(time);

            Debt debt = new Debt(false, deadline, hendrik, davit, false, false, "Shafi",s);
            //debt.getSuggestions().add(s);

            debtRepository.save(debt);


        };
    }


}
