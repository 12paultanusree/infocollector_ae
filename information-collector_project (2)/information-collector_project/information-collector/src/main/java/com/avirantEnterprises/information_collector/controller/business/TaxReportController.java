package com.avirantEnterprises.information_collector.controller.business;

import com.avirantEnterprises.information_collector.service.business.TaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Controller
public class TaxReportController {

    @Autowired
    private TaxService taxReportService;

    @GetMapping("/tax_form")
    public String showTaxReportForm() {
        return "business/tax_form";
    }

    @PostMapping("/submitTaxForm")
    public String submitTax(
            @RequestParam("taxYear") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate taxYear,
            @RequestParam("taxpayerName") String taxpayerName,
            @RequestParam("income") double income,
            @RequestParam("taxPaid") double taxPaid,
            @RequestParam("filingStatus") String filingStatus,
            @RequestParam("taxreceipt") MultipartFile taxreceipt) {

        taxReportService.saveTax(taxYear, taxpayerName, income, taxPaid, filingStatus, taxreceipt);
        return "business/success";
    }
}
