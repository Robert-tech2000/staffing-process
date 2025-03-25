package com.example.staffing.controller;

import com.example.staffing.dto.StaffingProcessDTO;
import com.example.staffing.service.StaffingService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class StaffingController {

    private final StaffingService service;

    public StaffingController(StaffingService service) {
        this.service = service;
    }

    @Transactional
    @PostMapping("/add-staffing-process")
    public ResponseEntity<StaffingProcessDTO> addStaffingProcess(@RequestParam Long clientID, @RequestParam Long employeeId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createStaffingProcess(clientID, employeeId));
    }

    @GetMapping("/get-staffing-process/{staffingProcessId}")
    public ResponseEntity<StaffingProcessDTO> getClient(@PathVariable("staffingProcessId") Long staffingProcessId) {
        StaffingProcessDTO staffingProcess = service.getStaffingProcess(staffingProcessId);

        return staffingProcess.getId() != null
                ? ResponseEntity.ok(staffingProcess)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping("/get-staffing-process/all-staffing-processes")
    public ResponseEntity<List<StaffingProcessDTO>> getAllStaffingProcesses() {
        List<StaffingProcessDTO> staffingProcesses = service.getAllStaffingProcesses();
        if (staffingProcesses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(staffingProcesses);
    }

    @Transactional
    @DeleteMapping("/delete-staffng-process/{staffingProcessId}")
    public ResponseEntity<Void> deleteClient(@PathVariable("staffingProcessId") Long staffingProcessId) {
        service.deleteStaffingProcessById(staffingProcessId);
        return ResponseEntity.noContent().build();
    }


}
