package com.example.NewsManagement.service;

import com.example.NewsManagement.model.Role;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface RoleService {
    Role save(Role role);

    Role update(Long roleId, Role role);

    Page<Role> findAll(Pageable pageable);

    ResponseEntity<Object> delete(Long roleId);

    Page<Role> findByNameContaining(@NonNull String name, Pageable pageable);
}