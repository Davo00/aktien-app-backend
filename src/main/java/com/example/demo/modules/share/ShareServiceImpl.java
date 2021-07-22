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
import java.util.Objects;
import java.util.Optional;

@Component
public class ShareServiceImpl implements ShareService {

    private final ShareRepository shareRepository;
    private final UserRepository userRepository;
    private final DebtRepository debtRepository;

    @Autowired
    public ShareServiceImpl(ShareRepository shareRepository, UserRepository userRepository, DebtRepository debtRepository) {
        this.shareRepository = shareRepository;
        this.userRepository = userRepository;
        this.debtRepository = debtRepository;
    }

    @Override
    public List<Share> findAllShare() throws Exception {
        List<Share> allShare = shareRepository.findAll();
        for (Share share : allShare) {
            updateSharePrice(share);
        }
        return allShare;
    }

    @Override
    public Share createShare(CreateShare request) throws Exception {
        List<User> userList = new ArrayList<>();
        for (String name : request.getUserNames()) {
            User user = userRepository.findByUsername(name)
                    .orElseThrow(() -> new UsernameNotFoundException("User " + name + " could not be found!"));
            userList.add(user);
        }
        Share share = new Share(request.getName(), request.getPrice(), userList);
        share = shareRepository.save(share);
        updateSharePrice(share);
        return share;
    }


    @Override
    public Share one(long id) throws Exception {
        Share share = shareRepository.findById(id).orElseThrow(() -> new NotFoundException("Share cound not be found"));
        updateSharePrice(share);
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
                userRepository.save(user);
            }
        } catch (Exception e) {
            throw new DeletionIntegrityException(e.getMessage());
        }


    }

    @Override
    public List<Share> getPreferedSharesbyUser(String username) throws Exception {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User " + username + " could not be found!"));

        List<Share> shares = user.getPreferedShares();
        if (!shares.isEmpty()) {
            for (Share share : shares) {
                updateSharePrice(share);
            }
        }

        return shares;
    }

    @Override
    public Share selectShareFromCreditor(String username, Long shareId) throws NotFoundException { //TODO Diese Methode löschen/implementieren?
        Optional<Share> s = shareRepository.findById(shareId);


        return null;
    }


    @Override
    public double getSharePriceByDebt(Long debtId) throws Exception {
        Debt debt = debtRepository.findById(debtId)
                .orElseThrow(() -> new NotFoundException("Debt: " + debtId + " not found"));
        if (!debt.isDebtorConfirmed() || !debt.isCreditorConfirmed()) {
            throw new Exception("Please select and accept a share");
        }
        Share share = shareRepository.findById(debt.getSelectedShare().getId())
                .orElseThrow(() -> new NotFoundException("Share: " + debt.getSelectedShare().getId() + " not found"));
        return getSharePrice(share.getId());
    }

    @Override
    public double getSharePrice(Long shareId) throws NotFoundException {
        double price = 0.0;
        Share share = shareRepository.findById(shareId)
                .orElseThrow(() -> new NotFoundException("Share: " + shareId + " not found"));
        String close = "0.0";
        try {
            close = Objects.requireNonNull(Stock.getStock(share.getName(), "TIME_SERIES_INTRADAY")).getClose();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (close == null)
            close = "0.0";
        close = close.replace("\"", "");
        price = Double.parseDouble(close);
        price = Math.round(price * 85.0) / 100.0;
        if (price <= 1) {
            price = share.getPrice();
        }
        return price;
    }

    @Override
    public void updateSharePrice(Share share) throws Exception {
        share.setPrice(getSharePrice(share.getId()));
        shareRepository.save(share);
    }

}
