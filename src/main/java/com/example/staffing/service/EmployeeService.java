package com.example.staffing.service;


import com.example.staffing.dto.EmployeeDTO;
import com.example.staffing.model.Employee;
import com.example.staffing.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public EmployeeDTO createEmployee(String name) {
        Employee employee = new Employee(name, true);
        repository.save(employee);
        logger.info("Employee created successfully with ID: {}", employee.getId());
        return convertEmployeeToDTO(employee);
    }

    public EmployeeDTO getEmployee(Long employeeId) {
        Employee employee = repository.findById(employeeId).orElseThrow();
        logger.info("Employee found with ID: {}", employee.getId());
        return convertEmployeeToDTO(employee);
    }

    public List<EmployeeDTO> getAllEmployees() {
        logger.info("Get all employees");
        return Streamable.of(repository.findAll()).toList().stream().map(this::convertEmployeeToDTO).toList();
    }

    public EmployeeDTO updateEmployee(EmployeeDTO employee) {
        return null;
    }

    public void deleteEmployeeById(Long employeeId) {
        logger.info("Delete employee with ID: {}", employeeId);
        repository.deleteById(employeeId);
    }

    private EmployeeDTO convertEmployeeToDTO(Employee employee) {
        return null;
    }

}
