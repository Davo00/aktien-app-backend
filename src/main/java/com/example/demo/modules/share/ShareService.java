package com.example.demo.modules.share;

import com.example.demo.modules.share.request.CreateShare;
import com.example.demo.utils.DeletionIntegrityException;
import javassist.NotFoundException;

import java.util.List;

public interface ShareService {

    List<Share> findAllShare() throws Exception;

    Share createShare(CreateShare request) throws Exception;

    Share one(long id) throws Exception;

    void deleteShare(long id) throws NotFoundException, DeletionIntegrityException;

    List<Share> getPreferedSharesbyUser(String username) throws Exception;

    Share selectShareFromCreditor(String username, Long shareId) throws NotFoundException;

    double getSharePriceByDebt(Long debtId) throws Exception;

    double getSharePrice(Long shareId) throws Exception;

    void updateSharePrice(Share share) throws Exception;
}
