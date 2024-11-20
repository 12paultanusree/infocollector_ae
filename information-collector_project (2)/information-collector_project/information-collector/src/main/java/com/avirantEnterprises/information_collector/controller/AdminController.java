package com.avirantEnterprises.information_collector.controller;

import com.avirantEnterprises.information_collector.model.Assignment;
import com.avirantEnterprises.information_collector.model.Form;
import com.avirantEnterprises.information_collector.model.Profile;
import com.avirantEnterprises.information_collector.repository.AssignmentRepository;
import com.avirantEnterprises.information_collector.repository.FormRepository;
import com.avirantEnterprises.information_collector.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminController {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private FormRepository formRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @PostMapping("/admin/assignForm")
    public String assignForm(@RequestParam Long formId, @RequestParam Long userId, RedirectAttributes redirectAttributes) {
        Profile user = profileRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid form ID"));

        Assignment assignment = new Assignment();
        assignment.setUser(user);
        assignment.setForm(form);
        assignmentRepository.save(assignment);

        redirectAttributes.addFlashAttribute("success", "Form assigned successfully.");
        return "redirect:/business_dashboard";
    }
}
