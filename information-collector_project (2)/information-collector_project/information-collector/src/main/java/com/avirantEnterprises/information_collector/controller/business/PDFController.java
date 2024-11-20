package com.avirantEnterprises.information_collector.controller.business;

import com.avirantEnterprises.information_collector.service.business.PDFService;  // Update to the correct service package
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
public class PDFController {

    @Autowired
    private PDFService pdfService;  // Inject the service that handles the expense PDF generation

    @GetMapping("/expense/download/{id}")
    public ResponseEntity<byte[]> downloadExpensePdf(@PathVariable Long id) throws IOException, DocumentException {
        byte[] pdfContent = pdfService.generateExpenseReportPdf(id);  // Call the service method to generate the PDF
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "expense_" + id + ".pdf");  // Adjusted the filename for expenses
        return ResponseEntity.ok().headers(headers).body(pdfContent);  // Return the PDF as a response
    }
    @GetMapping("/invoice/download/{id}")
    public ResponseEntity<byte[]> downloadInvoicePdf(@PathVariable Long id) throws IOException, DocumentException {
        byte[] pdfContent = pdfService.generateInvoiceReportPdf(id);  // Call the service method to generate the PDF
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "invoice_" + id + ".pdf");  // Adjusted the filename for expenses
        return ResponseEntity.ok().headers(headers).body(pdfContent);  // Return the PDF as a response
    }

    @GetMapping("/tax/download/{id}")
    public ResponseEntity<byte[]> downloadTaxPdf(@PathVariable Long id) throws IOException, DocumentException {
        byte[] pdfContent = pdfService.generateTaxReportPdf(id);  // Call the service method to generate the PDF
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "tax_" + id + ".pdf");  // Adjusted the filename for expenses
        return ResponseEntity.ok().headers(headers).body(pdfContent);  // Return the PDF as a response
    }

    @GetMapping("/employee/download/{id}")
    public ResponseEntity<byte[]> downloadEmployeePdf(@PathVariable Long id) throws IOException, DocumentException {
        byte[] pdfContent = pdfService.generateEmployeeReportPdf(id);  // Call the service method to generate the PDF
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "employee_" + id + ".pdf");  // Adjusted the filename for expenses
        return ResponseEntity.ok().headers(headers).body(pdfContent);  // Return the PDF as a response
    }

    @GetMapping("/asset/download/{id}")
    public ResponseEntity<byte[]> downloadAssetPdf(@PathVariable Long id) throws IOException, DocumentException {
        byte[] pdfContent = pdfService.generateAssetReportPdf(id);  // Call the service method to generate the PDF
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "asset_" + id + ".pdf");  // Adjusted the filename for expenses
        return ResponseEntity.ok().headers(headers).body(pdfContent);  // Return the PDF as a response
    }

}
