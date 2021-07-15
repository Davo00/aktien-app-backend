package com.example.demo.modules.share;

import com.example.demo.modules.debt.Debt;
import com.example.demo.modules.debt.DebtRepository;
import com.example.demo.modules.share.request.CreateShare;
import com.example.demo.modules.user.User;
import com.example.demo.modules.user.UserRepository;
import com.example.demo.utils.DeletionIntegrityException;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ShareServiceImpl implements ShareService {

    private ShareRepository shareRepository;
    private UserRepository userRepository;
    private DebtRepository debtRepository;

    @Autowired
    public ShareServiceImpl(ShareRepository shareRepository, UserRepository userRepository, DebtRepository debtRepository) {
        this.shareRepository = shareRepository;
        this.userRepository = userRepository;
        this.debtRepository = debtRepository;
    }

    @Override
    public List<Share> findAllShare() {
        List<Share> allShare = shareRepository.findAll();
        return allShare;
    }

    @Override
    public Share createShare(CreateShare request) {
        List<User> userList = new ArrayList<>();
        for (String name : request.getUserNames()) {
            User user = userRepository.findByUsername(name).orElseThrow(() -> new UsernameNotFoundException("User " + name + " could not be found!"));
            userList.add(user);
        }
        Share share = new Share(request.getName(), request.getPrice(), userList);
        share = shareRepository.save(share);
        return share;
    }


    @Override
    public Share one(long id) throws NotFoundException {
        Share share = shareRepository.findById(id).orElseThrow(() -> new NotFoundException("Share cound not be found"));
        return share;
    }

    @Override
    public void deleteShare(long id) throws NotFoundException, DeletionIntegrityException {

        Share share = shareRepository.findById(id).orElseThrow(() -> new NotFoundException("Share could not be found"));
        List<User> toSafeAtTheEnd = new ArrayList<>();
        try {
            for (User user : share.getUsers()) {
                user.getPreferedShares().remove(share);
                toSafeAtTheEnd.add(user);
            }

            share.getUsers().clear();
            shareRepository.delete(share);
            for (User user : toSafeAtTheEnd) {
                user = userRepository.save(user);
            }
        } catch (Exception e) {
            throw new DeletionIntegrityException(e.getMessage());
        }


    }

    @Override
    public List<Share> getPreferedSharesbyUser(String username) throws NotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User " + username + " could not be found!"));

        List<Share> shares = user.getPreferedShares();
        //   List<Share> shares = Wird nachgeholt, wenn User_sharelist da ist
        // TODO

        return shares;
    }

    @Override
    public Share selectShareFromCreditor(String username, Long shareId) throws NotFoundException {
        Optional<Share> s = shareRepository.findById(shareId);


        return null;
    }


    @Override
    public double getSharePriceByDebt(Long debtId) throws Exception {
        double price = 0.0;
        Debt debt = debtRepository.findById(debtId)
                .orElseThrow(() -> new NotFoundException("Debt: " + debtId + " not found"));
        if (!debt.isDebtorConfirmed() || !debt.isCreditorConfirmed()) {
            throw new Exception("Please select and accept a share");
        }
        Share share = shareRepository.findById(debt.getSelectedShare().getId())
                .orElseThrow(() -> new NotFoundException("Share: " + debt.getSelectedShare().getId() + " not found"));
        String close = Stock.getStock(share.getName(), "TIME_SERIES_INTRADAY").getClose();
        if (close == null)
            close = "0.0";
        price = Double.parseDouble(close);
        return price;
    }


}
