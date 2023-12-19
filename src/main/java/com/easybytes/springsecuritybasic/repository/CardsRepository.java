package com.easybytes.springsecuritybasic.repository;

import com.easybytes.springsecuritybasic.model.Cards;
import com.easybytes.springsecuritybasic.model.Loans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardsRepository extends JpaRepository<Cards, Long> {
    List<Cards> findByCustomerId(int customerId);
}
