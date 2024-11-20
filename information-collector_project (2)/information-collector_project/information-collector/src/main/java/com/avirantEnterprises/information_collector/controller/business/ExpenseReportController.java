package com.avirantEnterprises.information_collector.controller.business;

import com.avirantEnterprises.information_collector.service.business.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Controller
public class ExpenseReportController {

    @Autowired
    private ExpenseService expenseReportService;

    @GetMapping("/expense_report")
    public String showExpenseReportForm() {
        return "business/expense_report";
    }

    @PostMapping("/submitExpenseForm")
    public String submitExpense(
            @RequestParam("expenseDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate expenseDate,
            @RequestParam("amount") double amount,
            @RequestParam("category") String category,
            @RequestParam("description") String description,
            @RequestParam("receipt") MultipartFile receipt) {

        expenseReportService.saveExpense(expenseDate, amount, category, description, receipt);
        return "business/success";
    }
}
