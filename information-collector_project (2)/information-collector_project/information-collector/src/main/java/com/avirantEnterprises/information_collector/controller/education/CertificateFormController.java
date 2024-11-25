package com.avirantEnterprises.information_collector.controller.education;

import com.avirantEnterprises.information_collector.model.education.Certificate;
import com.avirantEnterprises.information_collector.service.education.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Controller
public class CertificateFormController {

    @Autowired
    private CertificateService certificateService;


    @GetMapping("/certificate/{id}")
    public String viewCertificate(@PathVariable Long id, Model model) {
        Certificate certificate = certificateService.getCertificateById(id);
        model.addAttribute("certificate", certificate);
        return "education/certificate_view";
    }


    @GetMapping("/certificate/delete/{id}")
    public String deleteCertificate(@PathVariable Long id) {
        certificateService.deleteCertificateById(id);
        return "redirect:/certificate_list";
    }


    @GetMapping("/certificate_list")
    public String listCertificates(Model model) {
        List<Certificate> certificates = certificateService.getAllCertificates();
        model.addAttribute("certificates", certificates);
        return "education/certificate_list";
    }


    @GetMapping("/certificate/update/{id}")
    public String showUpdateCertificateForm(@PathVariable Long id, Model model) {
        Certificate certificate = certificateService.getCertificateById(id);
        model.addAttribute("certificate", certificate);
        return "education/certificate_update";
    }


    @PostMapping("/updateCertificate")
    public String updateCertificate(@RequestParam("id") Long id,
                               @RequestParam("userName") String userName,
                               @RequestParam("userId") String userId,
                               @RequestParam("courseName") String courseName,
                               @RequestParam("certificationTitle") String certificationTitle,
                                    @RequestParam("certificationDate")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate certificationDate,
                               @RequestParam("certificatePath") MultipartFile certificatePath) {
        certificateService.updateCertificate(id, userName, userId, courseName, certificationTitle,certificationDate, certificatePath);
        return "redirect:/certificate/" + id;
    }


    @GetMapping("/certificate/new")
    public String showCertificateForm(Model model) {
        return "education/certificate_form";
    }


    @PostMapping("/submitCertificate")
    public String submitCertificate(  @RequestParam("userName") String userName,
                                      @RequestParam("userId") String userId,
                                      @RequestParam("courseName") String courseName,
                                      @RequestParam("certificationTitle") String certificationTitle,
                                      @RequestParam("certificationDate")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate certificationDate,
                                      @RequestParam("certificatePath") MultipartFile certificatePath) {
        certificateService.saveCertificate(userName, userId, courseName, certificationTitle,certificationDate, certificatePath);
        return "education/success";
    }
}
