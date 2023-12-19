package com.easybytes.springsecuritybasic.controller;

import com.easybytes.springsecuritybasic.model.AccountTransactions;
import com.easybytes.springsecuritybasic.repository.AccountsTransactionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BalanceController {

    private final AccountsTransactionsRepository accountsTransactionsRepository;

    @GetMapping("/myBalance")
    public List<AccountTransactions> getBalanceDetails(@RequestParam int id) {
        List<AccountTransactions> accountTransactions = accountsTransactionsRepository.
                findByCustomerIdOrderByTransactionDtDesc(id);
        if (accountTransactions != null ) {
            return accountTransactions;
        }else {
            return null;
        }
    }
}
