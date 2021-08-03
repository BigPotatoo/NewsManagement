package com.example.NewsManagement.repository;

import com.example.NewsManagement.model.UserAccount;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepo extends JpaRepository<UserAccount, Long> {
    @Override
    @EntityGraph(attributePaths = {"roles"})
    @NonNull
    Page<UserAccount> findAll(@NonNull Pageable pageable);

    UserDetails findByUsername(String username);
}