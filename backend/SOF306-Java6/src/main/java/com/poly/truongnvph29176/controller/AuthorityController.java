package com.poly.truongnvph29176.controller;

import com.poly.truongnvph29176.dto.request.AuthorityRequest;
import com.poly.truongnvph29176.dto.response.AuthorityResponse;
import com.poly.truongnvph29176.entity.Authority;
import com.poly.truongnvph29176.service.AuthorityService;
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
@RequestMapping("${api.prefix}/authorities")
@RequiredArgsConstructor
public class AuthorityController {
    private final AuthorityService authorityService;

    @GetMapping("")
    public ResponseEntity<?> getAllAuthority(@RequestParam(defaultValue = "0") Integer page,
                                             @RequestParam(defaultValue = "12") Integer limit) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by("id").ascending());
        Page<AuthorityResponse> authorityPage = authorityService.getAllAuthority(pageable);
        return ResponseEntity.ok(authorityPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAuthority(@PathVariable("id") Integer id) {
        try {
            Authority authority = authorityService.getAuthorityById(id);
            return ResponseEntity.ok(authority);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAuthority(@Valid @RequestBody AuthorityRequest authorityRequest,
                                             BindingResult result) {
        try {
            if(result.hasErrors()) {
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }else {
                Authority authority = authorityService.createAuthority(authorityRequest);
                return ResponseEntity.ok(authority);
            }
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAuthority(@PathVariable("id") Integer id,
                                             @Valid @RequestBody AuthorityRequest authorityRequest,
                                             BindingResult result) {
        try {
            if(result.hasErrors()) {
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }else {
                Authority authority = authorityService.updateAuthority(id, authorityRequest);
                return ResponseEntity.ok(authority);
            }
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable("id") Integer id) {
        try {
            authorityService.deleteAuthority(id);
            return ResponseEntity.ok("Deleted Role with id = " + id + " successfully");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
