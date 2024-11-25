package com.avirantEnterprises.information_collector.controller.education;

import com.avirantEnterprises.information_collector.service.education.EducationPdfService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

@Controller
public class EducationPdfController {

    @Autowired
    private EducationPdfService educationPdfService;  // Inject the service that handles the expense PDF generation

    @GetMapping("/course/download/{id}")
    public ResponseEntity<byte[]> downloadCoursePdf(@PathVariable Long id) throws IOException, DocumentException {
        byte[] pdfContent = educationPdfService.generateCourseReportPdf(id);  // Call the service method to generate the PDF
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "course_" + id + ".pdf");  // Adjusted the filename for expenses
        return ResponseEntity.ok().headers(headers).body(pdfContent);  // Return the PDF as a response
    }

}
