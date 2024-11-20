package com.avirantEnterprises.information_collector.repository.business;

import com.avirantEnterprises.information_collector.model.business.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
