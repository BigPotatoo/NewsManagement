package com.example.NewsManagement.repository;

import com.example.NewsManagement.model.Role;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    Page<Role> findByNameContaining(@NonNull String name, Pageable pageable);
}