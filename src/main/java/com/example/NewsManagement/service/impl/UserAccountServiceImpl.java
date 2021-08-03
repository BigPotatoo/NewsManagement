package com.example.NewsManagement.service.impl;

import com.example.NewsManagement.exception.ResourceNotFoundException;
import com.example.NewsManagement.model.UserAccount;
import com.example.NewsManagement.repository.UserAccountRepo;
import com.example.NewsManagement.service.UserAccountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserAccountServiceImpl implements UserAccountService, UserDetailsService {

    UserAccountRepo userAccountRepo;
    PasswordEncoder passwordEncoder;

    @Override
    public UserAccount save(UserAccount userAccount) {
        userAccount.setPassword(passwordEncoder.encode((userAccount.getPassword())));
        return userAccountRepo.save(userAccount);
    }

    @Override
    public UserAccount update(Long userAccountId, UserAccount userAccountRequest) {
        return userAccountRepo.findById(userAccountId).map(userAccount -> {
            userAccount.setUsername(userAccountRequest.getUsername());
            userAccount.setPassword(passwordEncoder.encode(userAccountRequest.getPassword()));
            return userAccountRepo.save(userAccount);
        }).orElseThrow(() -> new ResourceNotFoundException("UserAccountId " + userAccountId + " not found"));
    }

    @Override
    public Page<UserAccount> findAll(Pageable pageable) {
        return userAccountRepo.findAll(pageable);
    }

    @Override
    public ResponseEntity<Object> delete(Long userAccountId) {
        return userAccountRepo.findById(userAccountId).map(news -> {
            userAccountRepo.delete(news);
            return ResponseEntity.noContent().build();
        }).orElseThrow(() -> new ResourceNotFoundException("UserAccountId " + userAccountId + " not found"));
    }

    //now this registration is available for all roles, but everyone can register as an admin
    @Override
    public UserAccount registration(UserAccount userAccount) {
        userAccount.setPassword(passwordEncoder.encode((userAccount.getPassword())));
        return userAccountRepo.save(userAccount);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userAccountRepo.findByUsername(username);
    }
}
