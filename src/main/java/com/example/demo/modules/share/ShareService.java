package com.example.demo.modules.share;

import java.util.List;

public interface ShareService {

    List<Share> findAllShare();
    Share createShare(Share request);
    Share one(long id);


}
