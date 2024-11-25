package com.avirantEnterprises.information_collector.repository;

import com.avirantEnterprises.information_collector.model.Assignment;
import com.avirantEnterprises.information_collector.model.Form;
import com.avirantEnterprises.information_collector.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    // Get all assignments for a specific user
    List<Assignment> findByUser(Profile user);
    boolean existsByUserAndForm(Profile user, Form form);
    // Count total assignments
    @Query("SELECT COUNT(a) FROM Assignment a")
    Long countTotalAssignments();

}
