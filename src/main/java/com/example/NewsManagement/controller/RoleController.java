package com.example.NewsManagement.controller;

import com.example.NewsManagement.model.Role;
import com.example.NewsManagement.service.RoleService;
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
@RequestMapping("/api/v1/roles")
public class RoleController {

    RoleService roleService;

    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping
    public Page<Role> list(@PageableDefault(sort = "id") Pageable pageable) {
        return roleService.findAll(pageable);
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @PostMapping
    public Role add(@Valid @RequestBody Role role) {
        return roleService.save(role);
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @PutMapping("{roleId}")
    public Role update(@PathVariable("roleId") Long roleId,
                       @Valid @RequestBody Role role) {
        return roleService.update(roleId, role);
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @DeleteMapping("{roleId}")
    public ResponseEntity<?> delete(@PathVariable("roleId") Long roleId) {
        return roleService.delete(roleId);
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping("/search")
    public Page<Role> search(@RequestParam String name, @PageableDefault Pageable pageable) {
        return roleService.findByNameContaining(name, pageable);
    }
}