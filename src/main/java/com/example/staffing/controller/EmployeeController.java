package com.example.staffing.controller;


import com.example.staffing.dto.EmployeeDTO;
import com.example.staffing.service.EmployeeService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @Transactional
    @PostMapping("/add-employee")
    public ResponseEntity<EmployeeDTO> addEmployee(@RequestParam String name) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createEmployee(name));
    }

    @GetMapping("/get-employee/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable("employeeId") Long employeeId) {
        EmployeeDTO employee = service.getEmployee(employeeId);

        return employee.getId() != null
                ? ResponseEntity.ok(employee)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping("/get-employee/all-employees")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employees = service.getAllEmployees();
        if (employees.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(employees);
    }

    @Transactional
    @DeleteMapping("/delete-employee/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("employeeId") Long employeeId) {
        service.deleteEmployeeById(employeeId);
        return ResponseEntity.noContent().build();
    }
}
