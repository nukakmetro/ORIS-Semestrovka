package com.example.semestrovkafoodz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.semestrovkafoodz.entities.Role;
import com.example.semestrovkafoodz.repositories.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.findByName("ROLE_USER").get();
    }
}
