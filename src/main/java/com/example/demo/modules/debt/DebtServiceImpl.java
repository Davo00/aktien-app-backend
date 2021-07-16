package com.example.demo.modules.debt;

import com.example.demo.modules.debt.request.CreateDebt;
import com.example.demo.modules.debt.request.ProposeDebt;
import com.example.demo.modules.debt.response.DebtResponse;
import com.example.demo.modules.share.Share;
import com.example.demo.modules.share.ShareRepository;
import com.example.demo.modules.share.ShareService;
import com.example.demo.modules.user.User;
import com.example.demo.modules.user.UserRepository;
import com.example.demo.utils.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class DebtServiceImpl implements DebtService {

    private final DebtRepository debtRepository;
    private final UserRepository userRepository;
    private final ShareRepository shareRepository;
    private final ShareService shareService;

    @Autowired
    public DebtServiceImpl(DebtRepository debtRepository, UserRepository userRepository, ShareRepository shareRepository, ShareService shareService) {
        this.debtRepository = debtRepository;
        this.userRepository = userRepository;
        this.shareRepository = shareRepository;
        this.shareService = shareService;
    }

    @Override
    public List<DebtResponse> findAllDebt() {
        List<Debt> allDebt = debtRepository.findAll();
        List<DebtResponse> debtResponses = new ArrayList<>();
        allDebt.forEach(debt -> debtResponses.add(new DebtResponse(debt)));
        return debtResponses;
    }

    @Override
    public DebtResponse createDebt(CreateDebt request) throws NotFoundException {
        String endDate = request.getDeadline();
        Timestamp deadline = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = dateFormat.parse(endDate);
            long time = date.getTime();
            deadline = new Timestamp(time);
        } catch (Exception e) {
            System.out.println("please provide the String in this \"dd/MM/yyyy\" format");
            e.printStackTrace();
        }

        User creditor = userRepository.findById(request.getCreditorId())
                .orElseThrow(() -> new NotFoundException("Creditor with the Id " + request.getCreditorId() + " could not be found"));

        User debtor = userRepository.findById(request.getDebtorId())
                .orElseThrow(() -> new NotFoundException("Debtor with the Id " + request.getDebtorId() + " could not be found"));


        Share share = shareRepository.findById(request.getSelectedShareId())
                .orElseThrow(() -> new NotFoundException("Share with the Id " + request.getSelectedShareId() + " could not be found"));

        Debt debt = new Debt(request.isPaid(), request.getAmount(),/*request.getTimestampCreation(),*/ deadline,
                creditor, debtor, request.isCreditorConfirmed(), request.isDebtorConfirmed(),
                request.getGroupName(), share, 0);
        debt = debtRepository.save(debt);
        return new DebtResponse(debt);
    }

    @Override
    public DebtResponse proposeDebt(User proposer, ProposeDebt proposeDebt) throws Exception {
        Debt debt = debtRepository.findById(proposeDebt.getDebtId())
                .orElseThrow(() -> new NotFoundException("Debt with the Id " + proposeDebt.getDebtId() + " could not be found"));
        boolean isDebtor = proposer.getId().equals(debt.getDebtor().getId());
        boolean isCreditor = proposer.getId().equals(debt.getCreditor().getId());
        if (!isCreditor && !isDebtor) {
            throw new Exception("User is neither Creditor nor Debtor, no rights to propose Share for this debt");
        } else if (debt.isCreditorConfirmed() && debt.isDebtorConfirmed()) {
            throw new Exception("Debt has been accepted already. You can not propose other share after accepting");
        } else {
            debt.setDebtorConfirmed(isDebtor);
            debt.setCreditorConfirmed(isCreditor);
            debt.setSelectedShare(
                    shareRepository.findById(proposeDebt.getShareId())
                            .orElseThrow(() -> new NotFoundException("Share with ID: " + proposeDebt.getShareId() + " not found"))
            );
            debtRepository.save(debt);
        }
        return new DebtResponse(debt);
    }

    @Override
    public DebtResponse acceptDebt(Long debtId, Long userId) throws Exception {
        Debt debt = debtRepository.findById(debtId)
                .orElseThrow(() -> new NotFoundException("Debt with the Id " + debtId + " could not be found"));

        boolean isDebtor = userId.equals(debt.getDebtor().getId());
        boolean isCreditor = userId.equals(debt.getCreditor().getId());

        if (isCreditor) {
            debt.setCreditorConfirmed(true);
        } else if (isDebtor) {
            debt.setDebtorConfirmed(true);
        }

        if (debt.isCreditorConfirmed() && debt.isDebtorConfirmed()) {
            /*double currentPrice = shareService.getSharePriceByDebt(debt.getId());
            debt.setShareProportion(debt.getAmount()/currentPrice);*/ //TODO uncoment after Allphavantage merged in main
            debt = debtRepository.save(debt);
        } else {
            throw new Exception("Debt can't be accepted, please select a Share or try accepting an other debt");
        }

        return new DebtResponse(debt);
    }

}
