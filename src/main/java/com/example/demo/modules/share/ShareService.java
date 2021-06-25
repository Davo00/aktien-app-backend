package com.example.demo.modules.share;

import com.example.demo.modules.share.request.CreateShare;
import com.example.demo.utils.DeletionIntegrityException;
import javassist.NotFoundException;

import java.util.List;

public interface ShareService {

    List<Share> findAllShare();
    Share createShare(CreateShare request) throws NotFoundException;
    Share one(long id) throws NotFoundException;
    void deleteShare(long id) throws NotFoundException, DeletionIntegrityException;
    List<Share>getPreferedSharesbyUser(String username) throws NotFoundException;
    Share selectShareFromCreditor(String username, Long shareId) throws NotFoundException;

}
