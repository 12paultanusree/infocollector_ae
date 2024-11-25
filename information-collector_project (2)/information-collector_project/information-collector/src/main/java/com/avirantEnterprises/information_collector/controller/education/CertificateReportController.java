package com.avirantEnterprises.information_collector.controller.education;

import com.avirantEnterprises.information_collector.service.education.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Controller
public class CertificateReportController {

    @Autowired
    private CertificateService certificateReportService;

    @GetMapping("/certificate_form")
    public String showCertificateReportForm() {
        return "education/certificate_form";
    }

    @PostMapping("/submitCertificateForm")
    public String submitCertificate(
            @RequestParam("userName") String userName,
            @RequestParam("userId") String userId,
            @RequestParam("courseName") String courseName,
            @RequestParam("certificationTitle") String certificationTitle,
            @RequestParam("certificationDate")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate certificationDate,
            @RequestParam("certificatePath") MultipartFile certificatePath) {

        certificateReportService.saveCertificate(userName, userId, courseName, certificationTitle,certificationDate, certificatePath);
        return "education/success";
    }
}
