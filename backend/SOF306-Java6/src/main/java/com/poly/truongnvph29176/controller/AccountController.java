package com.poly.truongnvph29176.controller;

import com.poly.truongnvph29176.dto.request.AccountRequest;
import com.poly.truongnvph29176.dto.response.AccountResponse;
import com.poly.truongnvph29176.entity.Account;
import com.poly.truongnvph29176.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("")
    public ResponseEntity<?> getAllAccounts(@RequestParam(defaultValue = "0") Integer page,
                                            @RequestParam(defaultValue = "12") Integer limit) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by("username").ascending());
        Page<AccountResponse> list = accountService.getAllAccounts(pageable);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getAccount(@PathVariable("username") String username) {
        try {
            Account account = accountService.getAccountByUsername(username);
            return ResponseEntity.ok(account);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@Valid @RequestBody AccountRequest accountRequest,
                                           BindingResult result) {
        try {
            if(result.hasErrors()) {
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }else {
                Account account = accountService.createAccount(accountRequest);
                return ResponseEntity.ok(account);
            }
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> updateAccount(@PathVariable("username") String username,
                                           @Valid @RequestBody AccountRequest accountRequest,
                                           BindingResult result) {
        try {
            if(result.hasErrors()) {
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }else {
                Account account = accountService.updateAccount(username, accountRequest);
                return ResponseEntity.ok(account);
            }
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteAccount(@PathVariable("username") String username) {
        accountService.deleteAccount(username);
        return ResponseEntity.ok("Deleted Account with username = " + username + " successfully");
    }
}
