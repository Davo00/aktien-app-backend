package com.example.demo.modules.share;

import com.example.demo.modules.share.request.CreateShare;
import com.example.demo.modules.user.User;
import com.example.demo.modules.user.UserRepository;
import com.example.demo.utils.DeletionIntegrityException;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ShareServiceImpl implements ShareService {

    @Autowired
    ShareRepository shareRepository;

    @Autowired
    UserRepository userRepository;


    @Override
    public List<Share> findAllShare() {
        List<Share> allShare = shareRepository.findAll();
        return allShare;
    }

    @Override
    public Share createShare(CreateShare request) throws NotFoundException{
        List<User> userList = new ArrayList<>();
        for(String name: request.getUserNames()){
            User user = userRepository.findByUsername(name);
            if(user == null){
                throw new NotFoundException("User "+ name + " could not be found!");
            }
            userList.add(user);
        }
        Share share = new Share(request.getName(), request.getPrice(), userList);
        share = shareRepository.save(share);
        return share;
    }


    @Override
    public Share one(long id) throws NotFoundException{
        Share share = shareRepository.findById(id).orElseThrow(() -> new NotFoundException("Share cound not be found"));

        return share;
    }

    @Override
    public void deleteShare(long id) throws NotFoundException, DeletionIntegrityException {

        Share share = shareRepository.findById(id).orElseThrow(() -> new NotFoundException("Share could not be found"));
        List<User> toSafeAtTheEnd = new ArrayList<>();
        try{
            for (User user : share.getUsers()){
                user.getPreferedShares().remove(share);
                toSafeAtTheEnd.add(user);
            }

            share.getUsers().clear();
            shareRepository.delete(share);
            for (User user : toSafeAtTheEnd){
                user = userRepository.save(user);
            }
        }catch (Exception e ){
            throw new DeletionIntegrityException(e.getMessage());
        }


    }

    @Override
    public List<Share> getPreferedSharesbyUser(String username) throws NotFoundException {
        User user = userRepository.findByUsername(username);

       List<Share> shares =user.getPreferedShares();
     //   List<Share> shares = Wird nachgeholt, wenn User_sharelist da ist
      // To do

        return shares;
    }

    @Override
    public Share selectShareFromCreditor(String username, Long shareId) throws NotFoundException {
        Optional<Share> s = shareRepository.findById(shareId);


        return null;
    }


}
