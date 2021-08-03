package com.example.NewsManagement.service.impl;

import com.example.NewsManagement.exception.ResourceNotFoundException;
import com.example.NewsManagement.model.Role;
import com.example.NewsManagement.repository.RoleRepo;
import com.example.NewsManagement.service.RoleService;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class RoleServiceImpl implements RoleService {

    RoleRepo roleRepo;

    @Override
    public Role save(Role role) {
        return roleRepo.save(role);
    }

    @Override
    public Role update(Long roleId, Role roleRequest) {
        return roleRepo.findById(roleId).map(r -> {
            r.setName(roleRequest.getName());
            return roleRepo.save(r);
        }).orElseThrow(() -> new ResourceNotFoundException("RoleId " + roleId + " not found"));
    }

    @Override
    public Page<Role> findAll(Pageable pageable) {
        return roleRepo.findAll(pageable);
    }

    @Override
    public ResponseEntity<Object> delete(Long roleId) {
        return roleRepo.findById(roleId).map(role -> {
            roleRepo.delete(role);
            return ResponseEntity.noContent().build();
        }).orElseThrow(() -> new ResourceNotFoundException("RoleId " + roleId + " not found"));
    }

    @Override
    public Page<Role> findByNameContaining(@NonNull String name, Pageable pageable) {
        return roleRepo.findByNameContaining(name, pageable);
    }
}