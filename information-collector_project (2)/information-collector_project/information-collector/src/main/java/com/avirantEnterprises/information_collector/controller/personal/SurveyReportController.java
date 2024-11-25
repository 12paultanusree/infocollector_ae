package com.avirantEnterprises.information_collector.controller.personal;

import com.avirantEnterprises.information_collector.service.business.AssetService;
import com.avirantEnterprises.information_collector.service.personal.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Controller
public class SurveyReportController {

    @Autowired
    private SurveyService surveyReportService;

    @GetMapping("/survey_form")
    public String showSurveyReportForm() {
        return "personal/survey_form";
    }

    @PostMapping("/submitSurveyForm")
    public String submitSurvey(
            @RequestParam("userName") String userName,
            @RequestParam("email") String email,
            @RequestParam("feedback") String feedback,
            @RequestParam("interests") String interests,
            @RequestParam("filePath") MultipartFile filePath) {

        surveyReportService.saveSurvey(userName, email, feedback, interests, filePath);
        return "personal/success";
    }
}
