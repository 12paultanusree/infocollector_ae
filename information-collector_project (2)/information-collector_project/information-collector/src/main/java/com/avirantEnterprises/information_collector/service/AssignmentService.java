package com.avirantEnterprises.information_collector.service;

import com.avirantEnterprises.information_collector.model.Assignment;
import com.avirantEnterprises.information_collector.model.Profile;
import com.avirantEnterprises.information_collector.repository.AssignmentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @PersistenceContext
    private EntityManager entityManager;

    // Save a new assignment
    public Assignment createAssignment(Assignment assignment) {
        assignment.setAssignedAt(java.time.LocalDateTime.now()); // Ensure assignedAt is set
        return assignmentRepository.save(assignment);
    }
    // Fetch all assignments for a user
    public List<Assignment> getAssignmentsByUser(Profile user) {
        return assignmentRepository.findByUser(user);
    }

    // Analytics: Total forms assigned
    public Long getTotalAssignments() {
        return assignmentRepository.countTotalAssignments();
    }
}
