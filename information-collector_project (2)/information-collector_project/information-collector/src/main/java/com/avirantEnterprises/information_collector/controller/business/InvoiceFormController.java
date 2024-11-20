package com.avirantEnterprises.information_collector.controller.business;

import com.avirantEnterprises.information_collector.model.business.Invoice;
import com.avirantEnterprises.information_collector.service.business.InvoiceService;
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
public class InvoiceFormController {

    @Autowired
    private InvoiceService invoiceService;

    // View specific expense details
    @GetMapping("/invoice/{id}")
    public String viewInvoice(@PathVariable Long id, Model model) {
        Invoice invoice = invoiceService.getInvoiceById(id);
        model.addAttribute("invoice", invoice);
        return "business/invoice_view"; // View template for displaying the specific expense
    }

    // Delete specific expense by id
    @GetMapping("/invoice/delete/{id}")
    public String deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoiceById(id);
        return "redirect:/invoice_list"; // Redirect to a list of expenses after deletion
    }

    // List all expenses
    @GetMapping("/invoice_list")
    public String listInvoices(Model model) {
        List<Invoice> invoices = invoiceService.getAllInvoices();
        model.addAttribute("invoices", invoices);
        return "business/invoice_list"; // Template that lists all expenses
    }

    // Show the form to update an existing expense
    @GetMapping("/invoice/update/{id}")
    public String showUpdateInvoiceForm(@PathVariable Long id, Model model) {
        Invoice invoice = invoiceService.getInvoiceById(id);
        model.addAttribute("invoice", invoice);
        return "business/invoice_update"; // Template to show update form
    }

    // Handle the submission of the expense update form
    @PostMapping("/updateInvoice")
    public String updateInvoice(@RequestParam("id") Long id,
                                @RequestParam("invoiceDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate invoiceDate,
                                @RequestParam("invoiceAmount") double invoiceAmount,
                                @RequestParam("invoiceNumber") double invoiceNumber,
                                @RequestParam("invoiceDescription") String invoiceDescription,
                                @RequestParam("invoicereceipt") MultipartFile invoicereceipt) {
        invoiceService.updateInvoice(id, invoiceDate, invoiceAmount, invoiceNumber, invoiceDescription, invoicereceipt);
        return "redirect:/invoice/" + id; // Redirect to the updated expense page
    }

    // Show the form to add a new expense
    @GetMapping("/invoice/new")
    public String showInvoiceForm(Model model) {
        return "business/invoice_form"; // Template to show the form for creating a new expense
    }

    // Handle the submission of the new expense form
    @PostMapping("/submitInvoice")
    public String submitInvoice(@RequestParam("invoiceDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate invoiceDate,
                                @RequestParam("invoiceAmount") double invoiceAmount,
                                @RequestParam("invoiceNumber") double invoiceNumber,
                                @RequestParam("invoiceDescription") String invoiceDescription,
                                @RequestParam("invoicereceipt") MultipartFile invoicereceipt) {
        invoiceService.saveInvoice(invoiceDate, invoiceAmount, invoiceNumber, invoiceDescription, invoicereceipt);
        return "business/success"; // Redirect to the list of expenses after submission
    }
}
