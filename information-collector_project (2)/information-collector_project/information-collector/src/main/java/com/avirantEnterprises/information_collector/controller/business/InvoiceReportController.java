package com.avirantEnterprises.information_collector.controller.business;

import com.avirantEnterprises.information_collector.service.business.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Controller
public class InvoiceReportController {

    @Autowired
    private InvoiceService invoiceReportService;

    @GetMapping("/invoice_form")
    public String showInvoiceReportForm() {
        return "business/invoice_form";
    }

    @PostMapping("/submitInvoiceForm")
    public String submitInvoice(
            @RequestParam("invoiceDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate invoiceDate,
            @RequestParam("invoiceAmount") double invoiceAmount,
            @RequestParam("invoiceNumber") double invoiceNumber,
            @RequestParam("invoiceDescription") String invoiceDescription,
            @RequestParam("invoicereceipt") MultipartFile invoicereceipt) {

        invoiceReportService.saveInvoice(invoiceDate, invoiceAmount, invoiceNumber, invoiceDescription, invoicereceipt);
        return "business/success";
    }
}
