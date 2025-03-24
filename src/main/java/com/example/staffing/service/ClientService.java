package com.example.staffing.service;

import com.example.staffing.dto.ClientDTO;
import com.example.staffing.model.Client;
import com.example.staffing.repository.ClientRepository;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository repository;

    public ClientService(ClientRepository repository, ClientRepository repository1) {

        this.repository = repository1;
    }

    public ClientDTO createClient(String name) {
        return null;
    }

    public ClientDTO getClient(Long clientId) {
        Client client = repository.findById(clientId).orElseThrow();
        return convertClientToDTO(client);
    }

    public List<ClientDTO> getAllClients() {
        return Streamable.of(repository.findAll()).toList().stream().map(this::convertClientToDTO).toList();
    }

    public void deleteClientById(Long clientId) {
        repository.deleteById(clientId);
    }

    private ClientDTO convertClientToDTO(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(client.getId());
        clientDTO.setClientName(client.getClientName());
        clientDTO.setStaffingProcesses(client.getStaffingProcesses());
        return clientDTO;
    }
}
