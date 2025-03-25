package com.example.staffing.service;

import com.example.staffing.dto.StaffingProcessDTO;
import com.example.staffing.model.Client;
import com.example.staffing.model.Employee;
import com.example.staffing.model.StaffingProcess;
import com.example.staffing.repository.ClientRepository;
import com.example.staffing.repository.EmployeeRepository;
import com.example.staffing.repository.StaffingProcessRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffingService {

    private final StaffingProcessRepository repository;
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private static final Logger logger = LoggerFactory.getLogger(StaffingService.class);

    public StaffingService(StaffingProcessRepository repository, ClientRepository clientRepository, EmployeeRepository employeeRepository) {
        this.repository = repository;
        this.clientRepository = clientRepository;
        this.employeeRepository = employeeRepository;
    }

    private StaffingProcessDTO convertStaffingProcessToDTO(StaffingProcess staffingProcess) {
        StaffingProcessDTO dto = new StaffingProcessDTO();
        dto.setId(staffingProcess.getId());
        dto.setClient(staffingProcess.getClient());
        dto.setEmployee(staffingProcess.getEmployee());
        dto.setActive(staffingProcess.isActive());

        return dto;
    }

    public StaffingProcessDTO createStaffingProcess(Long clientID, Long employeeId) {
        StaffingProcess staffingProcess = new StaffingProcess();
        //todo: transfer clientID, employeeID or client and employee?
        Client client = clientRepository.findById(clientID).orElseThrow();
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();

        staffingProcess.setClient(client);
        staffingProcess.setEmployee(employee);
        staffingProcess.setActive(true);
        repository.save(staffingProcess);
        logger.info("Staffing Process created successfully with ID: {}", staffingProcess.getId());

        updateEmployeeAndClientStaffingProcesses(employee, client, staffingProcess);
        return convertStaffingProcessToDTO(staffingProcess);
    }

    private void updateEmployeeAndClientStaffingProcesses(Employee employee, Client client, StaffingProcess staffingProcess) {
        List<StaffingProcess> existingEmployeeStaffingProcesses = employee.getStaffingProcesses();
        existingEmployeeStaffingProcesses.add(staffingProcess);
        employee.setStaffingProcesses(existingEmployeeStaffingProcesses);

        List<StaffingProcess> existingClientStaffingProcesses = client.getStaffingProcesses();
        existingClientStaffingProcesses.add(staffingProcess);
        client.setStaffingProcesses(existingClientStaffingProcesses);

        employeeRepository.save(employee);
        clientRepository.save(client);
    }

    public StaffingProcessDTO getStaffingProcess(Long staffingProcessId) {
        StaffingProcess process = repository.findById(staffingProcessId).orElseThrow();
        logger.info("Staffing Process retrieved successfully with ID: {}", staffingProcessId);
        return convertStaffingProcessToDTO(process);
    }

    public List<StaffingProcessDTO> getAllStaffingProcesses() {
        logger.info("Get all Staffing Processes ");
        return Streamable.of(repository.findAll()).toList().stream().map(this::convertStaffingProcessToDTO).toList();
    }

    public void deleteStaffingProcessById(Long staffingProcessId) {
        logger.info("Delete Staffing Process by ID: {}", staffingProcessId);
        repository.deleteById(staffingProcessId);
    }

}
