package com.avirantEnterprises.information_collector.controller.personal;

import com.avirantEnterprises.information_collector.service.personal.PdfService;
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
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @GetMapping("/survey/download/{id}")
    public ResponseEntity<byte[]> downloadSurveyPdf(@PathVariable Long id) throws IOException, DocumentException {
        byte[] pdfContent = pdfService.generateSurveyPdf(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "survey_" + id + ".pdf");
        return ResponseEntity.ok().headers(headers).body(pdfContent);
    }

}
