package com.example.staffing.service;

import com.example.staffing.dto.ClientDTO;
import com.example.staffing.dto.EmployeeDTO;
import com.example.staffing.model.Client;
import com.example.staffing.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

    public ClientService(ClientRepository repository, ClientRepository repository1) {

        this.repository = repository1;
    }

    public ClientDTO createClient(String name) {
        Client client = new Client();
        client.setClientName(name);
        repository.save(client);
        logger.info("Client created successfully with ID: {}", client.getId());
        return convertClientToDTO(client);
    }

    public ClientDTO getClient(Long clientId) {
        Client client = repository.findById(clientId).orElseThrow();
        logger.info("Client found with ID: {}", client.getId());
        return convertClientToDTO(client);
    }

    public List<ClientDTO> getAllClients() {
        logger.info("Getting all clients");
        return Streamable.of(repository.findAll()).toList().stream().map(this::convertClientToDTO).toList();
    }

    public void deleteClientById(Long clientId) {
        logger.info("Deleting client with ID: {}", clientId);
        repository.deleteById(clientId);
    }

    public EmployeeDTO updateClient(ClientDTO client) {
        return null;
    }

    private ClientDTO convertClientToDTO(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(client.getId());
        clientDTO.setClientName(client.getClientName());
        clientDTO.setStaffingProcesses(client.getStaffingProcesses());
        return clientDTO;
    }

}
