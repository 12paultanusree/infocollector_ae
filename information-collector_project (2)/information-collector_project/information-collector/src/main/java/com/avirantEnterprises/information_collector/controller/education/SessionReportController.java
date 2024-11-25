package com.avirantEnterprises.information_collector.controller.education;

import com.avirantEnterprises.information_collector.service.education.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Controller
public class SessionReportController {

    @Autowired
    private SessionService sessionReportService;

    @GetMapping("/session_form")
    public String showSessionReportForm() {
        return "education/session_form";
    }

    @PostMapping("/submitSessionForm")
    public String submitSession(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("title") String title,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam("rating") String rating,
            @RequestParam("comments") String comments,
            @RequestParam("feedbackPath") MultipartFile feedbackPath) {

        sessionReportService.saveSession(name, email,title, date,rating,comments, feedbackPath);
        return "education/success";
    }
}
