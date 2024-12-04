package com.ramsa.company.service;

import com.ramsa.company.dto.RoleDTO;
import com.ramsa.company.entity.Role;
import com.ramsa.company.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public RoleDTO getRoleById(Long id) {
        return roleRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public RoleDTO createRole(RoleDTO roleDTO) {
        Role role = convertToEntity(roleDTO);
        Role savedRole = roleRepository.save(role);
        return convertToDTO(savedRole);
    }

    public RoleDTO updateRole(Long id, RoleDTO roleDTO) {
        Optional<Role> existingRole = roleRepository.findById(id);
        if (existingRole.isPresent()) {
            Role role = convertToEntity(roleDTO);
            role.setId(id); // Ensure the ID is set for the update
            Role updatedRole = roleRepository.save(role);
            return convertToDTO(updatedRole);
        }
        return null; // Or throw an exception
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    public Page<RoleDTO> searchRoles(String name, Pageable pageable) {
        return roleRepository.findByNameContaining(name, pageable)
                .map(this::convertToDTO);
    }

    private RoleDTO convertToDTO(Role role) {
        return new RoleDTO(role.getId(), role.getName());
    }

    private Role convertToEntity(RoleDTO roleDTO) {
        Role role = new Role();
        role.setId(roleDTO.getId());
        role.setName(roleDTO.getName());
        return role;
    }
}