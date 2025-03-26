package com.example.staffing.controller;

import com.example.staffing.dto.CommentDTO;
import com.example.staffing.dto.EmployeeDTO;
import com.example.staffing.dto.StaffingProcessDTO;
import com.example.staffing.service.StaffingService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/staffing-processes")
public class StaffingController {

    private final StaffingService service;

    public StaffingController(StaffingService service) {
        this.service = service;
    }

    @Transactional
    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StaffingProcessDTO> addStaffingProcess(@RequestParam Long clientID, @RequestParam Long employeeId, @RequestParam String title) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createStaffingProcess(clientID, employeeId, title));
    }

    @GetMapping("/{staffingProcessId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StaffingProcessDTO> getStaffingProcess(@PathVariable("staffingProcessId") Long staffingProcessId) {
        StaffingProcessDTO staffingProcess = service.getStaffingProcess(staffingProcessId);

        return staffingProcess.getId() != null
                ? ResponseEntity.ok(staffingProcess)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<StaffingProcessDTO>> getAllStaffingProcesses() {
        List<StaffingProcessDTO> staffingProcesses = service.getAllStaffingProcesses();
        if (staffingProcesses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(staffingProcesses);
    }

    @Transactional
    @PutMapping("/{staffingProcessId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody StaffingProcessDTO staffingProcess) {
        return ResponseEntity.ok(service.updateStaffingProcess(staffingProcess));
    }

    @Transactional
    @DeleteMapping("/{staffingProcessId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteClient(@PathVariable("staffingProcessId") Long staffingProcessId) {
        service.deleteStaffingProcessById(staffingProcessId);
        return ResponseEntity.noContent().build();
    }


}
