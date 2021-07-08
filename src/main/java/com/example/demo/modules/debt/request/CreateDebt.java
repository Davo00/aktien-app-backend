package com.example.demo.modules.debt.request;


import com.example.demo.modules.share.Share;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDebt {

    private boolean paid;
    private double amount;
    private String deadline; //   "dd/mm/yyyy"
    private Long creditorId;
    private Long debtorId;
    private boolean creditorConfirmed;
    private boolean debtorConfirmed;
    private String groupName;
    private Long selectedShareId;
}
