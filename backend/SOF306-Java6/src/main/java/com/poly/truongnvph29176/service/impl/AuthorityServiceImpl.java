package com.poly.truongnvph29176.service.impl;

import com.poly.truongnvph29176.dto.request.AuthorityRequest;
import com.poly.truongnvph29176.dto.response.AuthorityResponse;
import com.poly.truongnvph29176.entity.Account;
import com.poly.truongnvph29176.entity.Authority;
import com.poly.truongnvph29176.entity.Role;
import com.poly.truongnvph29176.exception.DataNotFoundException;
import com.poly.truongnvph29176.repository.AccountRepository;
import com.poly.truongnvph29176.repository.AuthorityRepository;
import com.poly.truongnvph29176.repository.RoleRepository;
import com.poly.truongnvph29176.service.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {
    private final AuthorityRepository authorityRepository;
    private final AccountRepository accountRepository;
    private final RoleRepository repository;

    @Override
    public Page<AuthorityResponse> getAllAuthority(Pageable pageable) {
        Page<Authority> authorityPage = authorityRepository.findAll(pageable);
        return authorityPage.map(authority -> AuthorityResponse.builder()
                .id(authority.getId())
                .username(authority.getAccount().getUsername())
                .role(authority.getRole().getId())
                .build());
    }

    @Override
    public Authority createAuthority(AuthorityRequest authorityRequest) throws Exception {
        Account account = accountRepository.findById(authorityRequest.getUsername())
                .orElseThrow(() ->
                        new DataNotFoundException(
                                "Cannot find Account with username = " + authorityRequest.getUsername())
                );
        Role role = repository.findById(authorityRequest.getRole())
                .orElseThrow(() ->
                        new DataNotFoundException(
                                "Cannot find Role with id = " + authorityRequest.getRole())
                );
        Authority authority = Authority.builder()
                .account(account)
                .role(role)
                .build();
        return authorityRepository.save(authority);
    }

    @Override
    public Authority getAuthorityById(Integer id) throws Exception {
        return authorityRepository.findById(id).orElseThrow(() ->
                    new DataNotFoundException("Cannot find with id = " + id)
                );
    }

    @Override
    @Transactional
    public Authority updateAuthority(Integer id, AuthorityRequest authorityRequest) throws Exception {
        Authority authority = getAuthorityById(id);
        if(authority != null) {
            Account account = accountRepository.findById(authorityRequest.getUsername())
                    .orElseThrow(() ->
                                new DataNotFoundException(
                                        "Cannot find Account with username = " + authorityRequest.getUsername())
                            );
            Role role = repository.findById(authorityRequest.getRole())
                    .orElseThrow(() ->
                                new DataNotFoundException("Cannot find Role with id = " + authorityRequest.getRole())
                            );
            authority.setAccount(account);
            authority.setRole(role);
            return authorityRepository.save(authority);
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteAuthority(Integer id) {
        authorityRepository.deleteById(id);
    }
}
