package com.example.NewsManagement.service;

import com.example.NewsManagement.model.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface UserAccountService {
    UserAccount save(UserAccount userAccount);

    UserAccount update(Long userAccountId, UserAccount userAccountRequest);

    Page<UserAccount> findAll(Pageable pageable);

    ResponseEntity<Object> delete(Long userAccountId);

    UserAccount registration(UserAccount userAccount);
}