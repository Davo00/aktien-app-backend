package com.example.demo.modules.share;

import com.example.demo.modules.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShareServiceImpl implements ShareService {

    @Autowired
    ShareRepository shareRepository;




    @Override
    public List<Share> findAllShare() {
        List<Share> allShare = shareRepository.findAll();
        return allShare;
    }

    @Override
    public Share createShare(Share request) {
        Share share = new Share(request.getId(), request.getName());
        share = shareRepository.save(share);
        return share;
    }


    @Override
    public Share one(long id) {
        Share share = shareRepository.getOne(id);

        return share;
    }

    @Override
    public Share deleteShare(long id) {
        shareRepository.deleteById(id);
        return null;
    }

    @Override
    public Share getRandom() {
      return null;
    }

    @Override
    public User getRanking() {
        return null;
    }




}
