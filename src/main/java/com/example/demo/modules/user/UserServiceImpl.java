package com.example.demo.modules.user;

import com.example.demo.modules.debt.Debt;
import com.example.demo.modules.debt.DebtRepository;
import com.example.demo.modules.debt.response.DebtResponse;
import com.example.demo.modules.group.Group;
import com.example.demo.modules.group.GroupRepository;
import com.example.demo.modules.security.JwtTokenUtil;
import com.example.demo.modules.group.response.GroupResponse;
import com.example.demo.modules.share.Share;
import com.example.demo.modules.user.request.CreateUser;
import com.example.demo.modules.user.response.UserResponse;
import com.example.demo.utils.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final DebtRepository debtRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, GroupRepository groupRepository,
                           JwtTokenUtil jwtTokenUtil, DebtRepository debtRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.debtRepository = debtRepository;
    }

    @Override
    public UserResponse createUser(CreateUser request) throws UsernameReservedException {
        if (isUsernameReserved(request.getUsername())) {
            throw new UsernameReservedException();
        }
        User user = new User(request.getUsername(), request.getPassword(), request.getEmail());
        userRepository.save(user);
        return new UserResponse(user);
    }

    @Override
    public List<GroupResponse> getAllGroupsOfUser(long userId) throws NotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with the id " + userId + " could not be found!"));
        List<Group> allGroups = groupRepository.findAll();
        List<Group> myGroups = new ArrayList<>();
        List<GroupResponse> groupResponseList = new ArrayList<>();

        for (Group group : allGroups) {
            for (User userInGroup : group.getMyUsers()) {
                if (userInGroup == user) {
                    myGroups.add(group);
                    break;
                }
            }
        }

        for (Group group : myGroups) {
            groupResponseList.add(new GroupResponse(group));
        }
        return groupResponseList;
    }


    @Override
    public User getCurrentUser(String token) {
        token = token.split(" ")[1];
        String username = jwtTokenUtil.getUsername(token);
        return userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User: " + username + "not found"));
    }

    @Override
    public void updateUser(User updatedUser, String token) throws UsernameReservedException {

        if (isUsernameReserved(updatedUser.getUsername())) {
            throw new UsernameReservedException();
        }
        User currentUser = getCurrentUser(token);
        currentUser.setEmail(updatedUser.getEmail());
        currentUser.setUsername(updatedUser.getUsername());

        userRepository.save(updatedUser);
    }

    @Override
    public void deleteUser(String token) {
        User user = getCurrentUser(token);
        userRepository.delete(user);
    }

    @Override
    public List<Share> getOwnPreferredShares(String token) {
        return getCurrentUser(token).getPreferedShares();
    }

    @Override
    public List<Share> getPreferredShares(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"))
                .getPreferedShares();
    }

    @Override
    public List<DebtResponse> getDebtsbyUser(User user) {
        List<Debt> debts = debtRepository.findAllByCreditorOrDebtor(user, user);
        List<DebtResponse> debtResponses = new ArrayList<>();
        for (Debt debt: debts) {
            debtResponses.add(new DebtResponse(debt));
        }
        return debtResponses;
    }

    private boolean isUsernameReserved(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    static class UsernameReservedException extends Exception {
        UsernameReservedException() {
            super("The username is already taken");
        }
    }
}
