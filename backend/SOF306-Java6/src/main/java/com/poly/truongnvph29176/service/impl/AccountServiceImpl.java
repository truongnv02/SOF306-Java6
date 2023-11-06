package com.poly.truongnvph29176.service.impl;

import com.poly.truongnvph29176.dto.request.AccountRequest;
import com.poly.truongnvph29176.dto.response.AccountResponse;
import com.poly.truongnvph29176.entity.Account;
import com.poly.truongnvph29176.entity.Role;
import com.poly.truongnvph29176.exception.DataNotFoundException;
import com.poly.truongnvph29176.repository.AccountRepository;
import com.poly.truongnvph29176.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Page<AccountResponse> getAllAccounts(Pageable pageable) {
        Page<Account> accountPage = accountRepository.findAll(pageable);
        return accountPage.map(account -> AccountResponse
                .builder()
                .username(account.getUsername())
                .fullname(account.getFullname())
                .email(account.getEmail())
                .photo(account.getPhoto())
                .build());
    }

    @Override
    public Account createAccount(AccountRequest accountRequest) {
        Account existingUsername = accountRepository.findById(accountRequest.getUsername()).orElse(null);
        Account existingEmail = accountRepository.findAccountByEmail(accountRequest.getEmail());

        if (existingUsername != null) {
            throw new RuntimeException("Username đã tồn tại");
        }
        if (existingEmail != null) {
            throw new RuntimeException("Email đã tồn tại");
        }
        Account account = Account.builder()
                .username(accountRequest.getUsername())
                .password(passwordEncoder.encode(accountRequest.getPassword()))
                .fullname(accountRequest.getFullname())
                .email(accountRequest.getEmail())
                .photo(accountRequest.getPhoto())
                .build();
        return accountRepository.save(account);
    }

    @Override
    public Account getAccountByUsername(String username) throws Exception {
        return accountRepository.findById(username).orElseThrow(() ->
                new DataNotFoundException("Cannot find username = " + username)
        );
    }

    @Override
    public Account updateAccount(String username, AccountRequest accountRequest) throws Exception {
        Account existingAccount = getAccountByUsername(username);
        existingAccount.setPassword(passwordEncoder.encode(accountRequest.getPassword()));
        existingAccount.setFullname(accountRequest.getFullname());
        existingAccount.setEmail(accountRequest.getEmail());
        existingAccount.setPhoto(accountRequest.getPhoto());
        return accountRepository.save(existingAccount);
    }

    @Override
    public void deleteAccount(String username) {
        accountRepository.deleteById(username);
    }
}
