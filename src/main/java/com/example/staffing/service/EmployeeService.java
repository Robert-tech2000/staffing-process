package com.example.staffing.service;


import com.example.staffing.dto.EmployeeDTO;
import com.example.staffing.model.Employee;
import com.example.staffing.repository.EmployeeRepository;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public EmployeeDTO createEmployee(String name) {
        Employee employee = new Employee(name, true);
        return convertEmployeeToDTO(employee);
    }

    public EmployeeDTO getEmployee(Long employeeId) {
        Employee employee = repository.findById(employeeId).orElseThrow();
        return convertEmployeeToDTO(employee);
    }

    public List<EmployeeDTO> getAllEmployees() {
        return Streamable.of(repository.findAll()).toList().stream().map(this::convertEmployeeToDTO).toList();
    }

    public void deleteEmployeeById(Long employeeId) {
        repository.deleteById(employeeId);
    }

    private EmployeeDTO convertEmployeeToDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setAvailable(employee.isAvailable());
        employeeDTO.setStaffingProcesses(employee.getStaffingProcesses());
        return employeeDTO;
    }


}
