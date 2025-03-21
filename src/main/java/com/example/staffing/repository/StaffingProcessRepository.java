package com.example.staffing.repository;

import com.example.staffing.model.Employee;
import com.example.staffing.model.StaffingProcess;
import org.springframework.data.repository.CrudRepository;

public interface StaffingProcessRepository extends CrudRepository<StaffingProcess, Long> {
}
