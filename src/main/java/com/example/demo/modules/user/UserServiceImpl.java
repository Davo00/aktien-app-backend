package com.example.demo.modules.user;

import com.example.demo.modules.group.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User createUser(User request) throws UsernameReservedException{
        if (isUsernameReserved(request.getUsername())) {
            throw new UsernameReservedException();
        }
        User user = new User(request.getUsername(), request.getEmail());
        userRepository.save(user);
        return user;
    }

    @Override
    public List<User> getUsersByGroup(Group request) {
        return userRepository.findAllByJoinedGroups(request);
    }

    @Override
    public User getCurrentUser() {
        return userRepository.findById(1L).get();
    }

    @Override
    public void updateUser(User updatedUser) throws UsernameReservedException {

        if (isUsernameReserved(updatedUser.getUsername())) {
            throw new UsernameReservedException();
        }
        getCurrentUser().setEmail(updatedUser.getEmail());
        getCurrentUser().setUsername(updatedUser.getUsername());

        userRepository.save(updatedUser);
    }

    @Override
    public void deleteUser() {
        User user = getCurrentUser();
        userRepository.delete(user);
    }

    private boolean isUsernameReserved(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    static class UsernameReservedException extends Exception {
    }
}
