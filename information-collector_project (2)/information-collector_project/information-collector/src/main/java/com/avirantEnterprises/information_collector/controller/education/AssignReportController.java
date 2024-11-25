package com.avirantEnterprises.information_collector.controller.education;

import com.avirantEnterprises.information_collector.service.education.AssignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Controller
public class AssignReportController {

    @Autowired
    private AssignService assignReportService;

    @GetMapping("/assignment_form")
    public String showAssignReportForm() {
        return "education/assignment_form";
    }

    @PostMapping("/submitAssignForm")
    public String submitAssign(
            @RequestParam("studentName") String studentName,
            @RequestParam("studentId") String studentId,
            @RequestParam("assignmentTitle") String assignmentTitle,
            @RequestParam("submissionDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate submissionDate,
            @RequestParam("assignmentPath") MultipartFile assignmentPath) {

        assignReportService.saveAssign(studentName, studentId,assignmentTitle, submissionDate, assignmentPath);
        return "education/success";
    }
}
