package com.avirantEnterprises.information_collector.controller.business;

import com.avirantEnterprises.information_collector.model.business.Tax;
import com.avirantEnterprises.information_collector.service.business.TaxService;
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
public class TaxFormController {

    @Autowired
    private TaxService taxService;

    // View specific expense details
    @GetMapping("/tax/{id}")
    public String viewTax(@PathVariable Long id, Model model) {
       Tax tax = taxService.getTaxById(id);
        model.addAttribute("tax", tax);
        return "business/tax_view"; // View template for displaying the specific expense
    }

    // Delete specific expense by id
    @GetMapping("/tax/delete/{id}")
    public String deleteTax(@PathVariable Long id) {
        taxService.deleteTaxById(id);
        return "redirect:/tax_list"; // Redirect to a list of expenses after deletion
    }

    // List all expenses
    @GetMapping("/tax_list")
    public String listTaxes(Model model) {
        List<Tax> taxes = taxService.getAllTaxes();
        model.addAttribute("taxes", taxes);
        return "business/tax_list"; // Template that lists all expenses
    }

    // Show the form to update an existing expense
    @GetMapping("/tax/update/{id}")
    public String showUpdateTaxForm(@PathVariable Long id, Model model) {
        Tax tax = taxService.getTaxById(id);
        model.addAttribute("tax", tax);
        return "business/tax_update"; // Template to show update form
    }

    // Handle the submission of the expense update form
    @PostMapping("/updateTax")
    public String updateTax(@RequestParam("id") Long id,
                                @RequestParam("taxYear") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate taxYear,
                                @RequestParam("taxpayerName") String taxpayerName,
                                @RequestParam("income") double income,
                                @RequestParam("taxPaid") double taxPaid,
                            @RequestParam("filingStatus") String filingStatus,
                                @RequestParam("taxreceipt") MultipartFile taxreceipt) {
        taxService.updateTax(id, taxYear, taxpayerName, income, taxPaid, filingStatus,taxreceipt);
        return "redirect:/tax/" + id; // Redirect to the updated expense page
    }

    // Show the form to add a new expense
    @GetMapping("/tax/new")
    public String showTaxForm(Model model) {
        return "business/tax_form"; // Template to show the form for creating a new expense
    }

    // Handle the submission of the new expense form
    @PostMapping("/submitTax")
    public String submitTax(@RequestParam("taxYear") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate taxYear,
                                @RequestParam("taxpayerName") String taxpayerName,
                                @RequestParam("income") double income,
                                @RequestParam("taxPaid") double taxpaid,
                                @RequestParam("filingStatus") String filingStatus,
                                @RequestParam("taxreceipt") MultipartFile taxreceipt) {
        taxService.saveTax(taxYear, taxpayerName, income, taxpaid, filingStatus, taxreceipt);
        return "business/success"; // Redirect to the list of expenses after submission
    }
}
