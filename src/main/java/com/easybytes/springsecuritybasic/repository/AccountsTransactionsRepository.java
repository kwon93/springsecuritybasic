package com.easybytes.springsecuritybasic.repository;

import com.easybytes.springsecuritybasic.model.AccountTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountsTransactionsRepository extends JpaRepository<AccountTransactions, Long> {

    List<AccountTransactions> findByCustomerIdOrderByTransactionDtDesc(int customerId);
}
