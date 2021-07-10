package com.example.demo.modules.user.response;

import com.example.demo.modules.group.Group;
import com.example.demo.modules.share.Share;
import com.example.demo.modules.user.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserResponse {
    private long id;
    private String username;
    private String email;
    private double overallScore;
    private List<Long> myGroupsIds;
    private List<Long> prefferedSharesIds;
    private List<Long> myExpensesIds;

    public UserResponse(long id, String username, String email, double overallScore) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.overallScore = overallScore;
    }

    public UserResponse (User user){
        this(user.getId(), user.getUsername(), user.getEmail(), user.getOverallScore());
        if( user.getJoinedGroups()!=null&& !user.getJoinedGroups().isEmpty()){
            myGroupsIds = new ArrayList<>();
            user.getJoinedGroups().forEach(group -> myGroupsIds.add(group.getId()));
        }
        if(user.getPreferedShares()!=null&& !user.getPreferedShares().isEmpty()){
            prefferedSharesIds = new ArrayList<>();
            user.getPreferedShares().forEach(share -> prefferedSharesIds.add(share.getId()));
        }
        if(user.getExpense()!=null &&!user.getExpense().isEmpty()){
            myExpensesIds = new ArrayList<>();
            user.getExpense().forEach(expense -> myExpensesIds.add(expense.getId()));
        }

    }

}
