package com.example.staffing.repository;

import com.example.staffing.model.Client;
import com.example.staffing.model.Employee;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {
}
