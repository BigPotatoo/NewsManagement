package com.example.NewsManagement.controller;

import com.example.NewsManagement.model.UserAccount;
import com.example.NewsManagement.service.UserAccountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@Transactional
@RequestMapping("/api/v1/userAccounts")
public class UserAccountController {
    UserAccountService userAccountService;

    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping
    public Page<UserAccount> list(@PageableDefault(sort = "id") Pageable pageable) {
        return userAccountService.findAll(pageable);
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @PostMapping
    public UserAccount add(@Valid @RequestBody UserAccount userAccount) {
        return userAccountService.save(userAccount);
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @PutMapping("{userAccountId}")
    public UserAccount update(@PathVariable("userAccountId") Long userAccountId,
                              @Valid @RequestBody UserAccount userAccount) {
        return userAccountService.update(userAccountId, userAccount);
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @DeleteMapping("{userAccountId}")
    public ResponseEntity<?> delete(@PathVariable("userAccountId") Long userAccountId) {
        return userAccountService.delete(userAccountId);
    }

    @PostMapping("/registration")
    public UserAccount registration(@Valid @RequestBody UserAccount userAccount) {
        return userAccountService.registration(userAccount);
    }
}
