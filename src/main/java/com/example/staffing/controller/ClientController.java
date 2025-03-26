package com.example.staffing.controller;

import com.example.staffing.dto.ClientDTO;
import com.example.staffing.dto.EmployeeDTO;
import com.example.staffing.service.ClientService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/clients")
public class ClientController {

    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @Transactional
    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientDTO> addClient(@RequestParam String name) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createClient(name));
    }

    @GetMapping("/{clientId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientDTO> getClient(@PathVariable("clientId") Long clientId) {
        ClientDTO client = service.getClient(clientId);

        return client.getId() != null
                ? ResponseEntity.ok(client)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        List<ClientDTO> clients = service.getAllClients();
        if (clients.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(clients);
    }

    @Transactional
    @PutMapping("/{clientId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmployeeDTO> updateClient(@RequestBody ClientDTO client) {
        return ResponseEntity.ok(service.updateClient(client));
    }

    @Transactional
    @DeleteMapping("/{clientId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteClient(@PathVariable("clientId") Long clientId) {
        service.deleteClientById(clientId);
        return ResponseEntity.noContent().build();
    }
}
