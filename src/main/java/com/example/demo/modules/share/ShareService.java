package com.example.demo.modules.share;

import com.example.demo.modules.user.User;

import java.util.List;

public interface ShareService {

    List<Share> findAllShare();
    Share createShare(Share request);
    Share one(long id);
    Share deleteShare(long id);
    Share getRandom();
    User getRanking();

}
