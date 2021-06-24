package com.example.demo.modules.share;

import javassist.NotFoundException;

import java.util.List;

public interface ShareService {

    List<Share> findAllShare();
    Share createShare(Share request);
    Share one(long id);
    Share deleteShare(long id) throws Exception;
    List<Share>getPreferedSharesbyUser(String username) throws NotFoundException;
    Share selectShareFromCreditor(String username, Long shareId) throws NotFoundException;

}
