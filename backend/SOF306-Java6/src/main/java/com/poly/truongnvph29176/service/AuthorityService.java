package com.poly.truongnvph29176.service;

import com.poly.truongnvph29176.dto.request.AuthorityRequest;
import com.poly.truongnvph29176.dto.response.AuthorityResponse;
import com.poly.truongnvph29176.entity.Authority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuthorityService {
    Page<AuthorityResponse> getAllAuthority(Pageable pageable);
    Authority createAuthority(AuthorityRequest authorityRequest) throws Exception;
    Authority getAuthorityById(Integer id) throws Exception;
    Authority updateAuthority(Integer id, AuthorityRequest authorityRequest) throws Exception;
    void deleteAuthority(Integer id);
}
