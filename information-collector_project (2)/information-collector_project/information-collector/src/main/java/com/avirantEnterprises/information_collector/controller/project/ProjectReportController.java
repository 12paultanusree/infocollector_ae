package com.avirantEnterprises.information_collector.controller.project;

import com.avirantEnterprises.information_collector.service.project.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Controller
public class ProjectReportController {

    @Autowired
    private ProjectService projectReportService;

    @GetMapping("/project_form")
    public String showProjectReportForm() {
        return "project/project_form";
    }

    @PostMapping("/submitProjectForm")
    public String submitProject(
            @RequestParam("fullName") String fullName,
            @RequestParam("email") String email,
            @RequestParam("projectTitle") String projectTitle,
            @RequestParam("projectDescription") String projectDescription,
            @RequestParam("proposalPath") MultipartFile proposalPath) {

        projectReportService.saveProject(fullName, email, projectTitle, projectDescription, proposalPath);
        return "project/success";
    }
}
