package com.easybytes.springsecuritybasic.repository;

import com.easybytes.springsecuritybasic.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
}
