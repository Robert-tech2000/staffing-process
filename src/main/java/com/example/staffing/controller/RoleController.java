package com.example.staffing.controller;

import com.example.staffing.dto.EmployeeDTO;
import com.example.staffing.dto.RoleDTO;
import com.example.staffing.service.RoleService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/security-roles")
public class RoleController {

    private final RoleService service;

    public RoleController(RoleService service) {
        this.service = service;
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<RoleDTO> addRole(@RequestParam String roleName) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createRole(roleName));
    }

    @GetMapping()
    public ResponseEntity<List<RoleDTO>> getAllEmployees() {
        List<RoleDTO> roles = service.getAllRoles();
        if (roles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(roles);
    }

    @Transactional
    @PutMapping("/{roleId}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody RoleDTO role) {
        return ResponseEntity.ok(service.updateRole(role));
    }

    @Transactional
    @DeleteMapping("/{roleId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("roleId") Long roleId) {
        service.deleteRoleById(roleId);
        return ResponseEntity.noContent().build();
    }

}
