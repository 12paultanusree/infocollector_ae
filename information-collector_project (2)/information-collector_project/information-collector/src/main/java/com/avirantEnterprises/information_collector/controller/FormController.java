package com.avirantEnterprises.information_collector.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FormController {

    @GetMapping("/form/view/{id}")
    public String viewForm(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        if (id == 1) {
            return "redirect:/expense_report"; // Redirect to the Expense Report form
        } else if (id == 2) {
            return "redirect:/invoice_form"; // Redirect to the Invoice Submission form
        } else if (id == 3) {
            return "redirect:/tax_form"; // Redirect to the Tax Form
        } else if (id == 4) {
            return "redirect:/employee_form"; // Redirect to the Employee Performance form
        } else if (id == 5) {
            return "redirect:/asset_form"; // Redirect to the Asset Registration form
        }


        // Handle invalid form ID
        redirectAttributes.addFlashAttribute("errorMessage", "Form not found!");
        return "redirect:/user/dashboard"; // Redirect back to the dashboard if form is invalid
    }
}
