package com.avirantEnterprises.information_collector.controller;

import com.avirantEnterprises.information_collector.model.Assignment;
import com.avirantEnterprises.information_collector.model.Profile;
import com.avirantEnterprises.information_collector.repository.AssignmentRepository;
import com.avirantEnterprises.information_collector.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @GetMapping("/user/dashboard")
    public String userDashboard(Model model, Authentication authentication) {
        // Fetch the logged-in user
        Profile user = profileRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Fetch forms assigned to the user
        List<Assignment> assignments = assignmentRepository.findByUser(user);

        // Pass the assigned forms to the model
        model.addAttribute("assignments", assignments);

        return "user_dashboard"; // Refers to user_dashboard.html
    }
}
