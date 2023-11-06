package com.poly.truongnvph29176.repository;

import com.poly.truongnvph29176.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    Account findAccountByEmail(String email);
}
