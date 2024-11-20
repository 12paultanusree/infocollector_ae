package com.avirantEnterprises.information_collector.controller;

import com.avirantEnterprises.information_collector.model.Assignment;
import com.avirantEnterprises.information_collector.model.Profile;
import com.avirantEnterprises.information_collector.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    // Create a new assignment
    @PostMapping
    public Assignment createAssignment(@RequestBody Assignment assignment) {
        return assignmentService.createAssignment(assignment);
    }



    // Fetch assignments for a specific user
    @GetMapping("/user/{userId}")
    public List<Assignment> getAssignmentsByUser(@PathVariable Long userId) {
        Profile user = new Profile(); // Replace with fetching the user object
        user.setId(userId); // Simulating a user profile
        return assignmentService.getAssignmentsByUser(user);
    }

    // Fetch analytics data
    @GetMapping("/analytics")
    public Map<String, Object> getAnalytics() {
        Map<String, Object> analytics = new HashMap<>();
        analytics.put("totalAssignments", assignmentService.getTotalAssignments()); // Total forms assigned
        return analytics;
    }
}
