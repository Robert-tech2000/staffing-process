package com.example.staffing.service;

import com.example.staffing.dto.StaffingProcessDTO;
import com.example.staffing.model.Client;
import com.example.staffing.model.StaffingProcess;
import com.example.staffing.model.User;
import com.example.staffing.repository.ClientRepository;
import com.example.staffing.repository.StaffingProcessRepository;
import com.example.staffing.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffingService {

    private final StaffingProcessRepository repository;
    private final ClientRepository clientRepository;
    private static final Logger logger = LoggerFactory.getLogger(StaffingService.class);
    private final UserRepository userRepository;

    public StaffingService(StaffingProcessRepository repository, ClientRepository clientRepository, UserRepository userRepository) {
        this.repository = repository;
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
    }

    public StaffingProcessDTO createStaffingProcess(Long clientID, Long employeeId, String title) {
        StaffingProcess staffingProcess = new StaffingProcess();
        Client client = clientRepository.findById(clientID).orElseThrow();
        User employee = userRepository.findById(employeeId).orElseThrow();

        staffingProcess.setClient(client);
        staffingProcess.setEmployee(employee);
        staffingProcess.setTitle(title);
        staffingProcess.setActive(true);
        repository.save(staffingProcess);
        logger.info("Staffing Process created successfully with ID: {}", staffingProcess.getId());

        updateEmployeeAndClientStaffingProcesses(employee, client, staffingProcess);
        return convertStaffingProcessToDTO(staffingProcess);
    }

    private void updateEmployeeAndClientStaffingProcesses(User employee, Client client, StaffingProcess staffingProcess) {
        List<StaffingProcess> existingEmployeeStaffingProcesses = employee.getStaffingProcesses();
        existingEmployeeStaffingProcesses.add(staffingProcess);
        employee.setStaffingProcesses(existingEmployeeStaffingProcesses);

        List<StaffingProcess> existingClientStaffingProcesses = client.getStaffingProcesses();
        existingClientStaffingProcesses.add(staffingProcess);
        client.setStaffingProcesses(existingClientStaffingProcesses);

        userRepository.save(employee);
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

    public StaffingProcessDTO updateStaffingProcess(StaffingProcessDTO staffingProcess) {
        StaffingProcess staffingProcessToUpdate = repository.findById(staffingProcess.getId()).orElseThrow();
        staffingProcessToUpdate.setActive(staffingProcess.isActive());
        staffingProcessToUpdate.setTitle(staffingProcess.getTitle());
        staffingProcessToUpdate = repository.save(staffingProcessToUpdate);

        return convertStaffingProcessToDTO(staffingProcessToUpdate);
    }

    public void deleteStaffingProcessById(Long staffingProcessId) {
        logger.info("Delete Staffing Process by ID: {}", staffingProcessId);
        repository.deleteById(staffingProcessId);
    }

    private StaffingProcessDTO convertStaffingProcessToDTO(StaffingProcess staffingProcess) {
        StaffingProcessDTO dto = new StaffingProcessDTO();
        dto.setId(staffingProcess.getId());
        dto.setTitle(staffingProcess.getTitle());
        dto.setClient(staffingProcess.getClient());
        dto.setEmployee(staffingProcess.getEmployee());
        dto.setComments(staffingProcess.getComments());
        dto.setActive(staffingProcess.isActive());

        return dto;
    }

}
