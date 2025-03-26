package com.example.staffing.service;

import com.example.staffing.dto.EmployeeDTO;
import com.example.staffing.dto.RoleDTO;
import com.example.staffing.model.Role;
import com.example.staffing.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(RoleService.class);

    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public RoleDTO createRole(String roleName) {
        Role role = new Role();
        role.setName(roleName);
        repository.save(role);
        logger.info("Role created successfully with ID: {}", role.getId());
        return convertSecurityRoleToDTO(role);
    }

    public List<RoleDTO> getAllRoles() {
        logger.info("Getting all Security Roles");
        return Streamable.of(repository.findAll()).toList().stream().map(this::convertSecurityRoleToDTO).toList();
    }

    public EmployeeDTO updateRole(RoleDTO role) {
        return null;
    }

    public void deleteRoleById(Long roleId) {
        logger.info("Deleting Security Role with ID: {}", roleId);
        repository.deleteById(roleId);
    }

    private RoleDTO convertSecurityRoleToDTO(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());
        return roleDTO;
    }
}
