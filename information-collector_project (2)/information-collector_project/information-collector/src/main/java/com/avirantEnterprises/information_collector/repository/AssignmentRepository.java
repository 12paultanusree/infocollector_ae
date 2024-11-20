package com.avirantEnterprises.information_collector.repository;

import com.avirantEnterprises.information_collector.model.Assignment;
import com.avirantEnterprises.information_collector.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByUser(Profile user);
}
