package com.example.staffing.controller;

import com.example.staffing.model.Client;
import com.example.staffing.model.Comment;
import com.example.staffing.model.Employee;
import com.example.staffing.model.StaffingProcess;
import com.example.staffing.repository.ClientRepository;
import com.example.staffing.repository.CommentRepository;
import com.example.staffing.repository.EmployeeRepository;
import com.example.staffing.repository.StaffingProcessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/dev")
@RequiredArgsConstructor
public class DevController {

    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final StaffingProcessRepository staffingRepository;
    private final CommentRepository commentRepository;

    @PostMapping("/generateRandomData")
    public ResponseEntity<String> generateRandomData() {
        clientRepository.deleteAll();
        employeeRepository.deleteAll();
        staffingRepository.deleteAll();
        commentRepository.deleteAll();

        List<Client> clients = IntStream.range(1, 11)
                .mapToObj(i -> {
                    Client c = new Client();
                    c.setClientName("Client " + i);
                    return clientRepository.save(c);
                })
                .toList();

        List<Employee> employees = IntStream.range(1, 11)
                .mapToObj(i -> {
                    Employee e = new Employee();
                    e.setName("Employee " + i);
                    e.setAvailable(true);
                    return employeeRepository.save(e);
                })
                .toList();

        Random random = new Random();
        for (int i = 1; i <= 10; i++) {
            StaffingProcess process = new StaffingProcess();
            process.setTitle("Staffing Process " + i);
            process.setClient(clients.get(random.nextInt(clients.size())));
            process.setEmployee(employees.get(random.nextInt(employees.size())));
            process.setActive(random.nextBoolean());

            StaffingProcess saved = staffingRepository.save(process);

            for (int j = 0; j < random.nextInt(4); j++) {
                Comment c = new Comment();
                c.setTitle("Comment Title " + j);
                c.setComment("Random feedback " + j);
                c.setAuthor(employees.get(random.nextInt(employees.size())));
                c.setCommentParent(null); // no nesting for now
                c.setStaffingProcess(saved);
                commentRepository.save(c);
            }
        }

        return ResponseEntity.ok("Random data generated!");
    }
}
