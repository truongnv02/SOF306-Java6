package com.poly.truongnvph29176.service;

import com.poly.truongnvph29176.dto.request.AccountRequest;
import com.poly.truongnvph29176.dto.request.LoginRequest;
import com.poly.truongnvph29176.dto.response.AccountResponse;
import com.poly.truongnvph29176.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountService {
    Page<AccountResponse> getAllAccounts(Pageable pageable);
    Account createAccount(AccountRequest accountRequest);
    Account getAccountByUsername(String username) throws Exception;
    Account updateAccount(String username, AccountRequest accountRequest) throws Exception;
    String login(LoginRequest loginRequest);
    String verifyAccount(String email, String otp);
    void deleteAccount(String username);
}
