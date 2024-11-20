package com.avirantEnterprises.information_collector.controller.business;

import com.avirantEnterprises.information_collector.model.business.Expense;
import com.avirantEnterprises.information_collector.service.business.ExpenseService;
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
public class ExpenseFormController {

    @Autowired
    private ExpenseService expenseService;

    // View specific expense details
    @GetMapping("/expense/{id}")
    public String viewExpense(@PathVariable Long id, Model model) {
        Expense expense = expenseService.getExpenseById(id);
        model.addAttribute("expense", expense);
        return "business/expense_view"; // View template for displaying the specific expense
    }

    // Delete specific expense by id
    @GetMapping("/expense/delete/{id}")
    public String deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpenseById(id);
        return "redirect:/expense_list"; // Redirect to a list of expenses after deletion
    }

    // List all expenses
    @GetMapping("/expense_list")
    public String listExpenses(Model model) {
        List<Expense> expenses = expenseService.getAllExpenses();
        model.addAttribute("expenses", expenses);
        return "business/expense_list"; // Template that lists all expenses
    }

    // Show the form to update an existing expense
    @GetMapping("/expense/update/{id}")
    public String showUpdateExpenseForm(@PathVariable Long id, Model model) {
        Expense expense = expenseService.getExpenseById(id);
        model.addAttribute("expense", expense);
        return "business/expense_update"; // Template to show update form
    }

    // Handle the submission of the expense update form
    @PostMapping("/updateExpense")
    public String updateExpense(@RequestParam("id") Long id,
                                @RequestParam("expenseDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate expenseDate,
                                @RequestParam("amount") double amount,
                                @RequestParam("category") String category,
                                @RequestParam("description") String description,
                                @RequestParam("receipt") MultipartFile receipt) {
        expenseService.updateExpense(id, expenseDate, amount, category, description, receipt);
        return "redirect:/expense/" + id; // Redirect to the updated expense page
    }

    // Show the form to add a new expense
    @GetMapping("/expense/new")
    public String showExpenseForm(Model model) {
        return "business/expense_form"; // Template to show the form for creating a new expense
    }

    // Handle the submission of the new expense form
    @PostMapping("/submitExpense")
    public String submitExpense(@RequestParam("expenseDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate expenseDate,
                                @RequestParam("amount") double amount,
                                @RequestParam("category") String category,
                                @RequestParam("description") String description,
                                @RequestParam("receipt") MultipartFile receipt) {
        expenseService.saveExpense(expenseDate, amount, category, description, receipt);
        return "business/success"; // Redirect to the list of expenses after submission
    }
}
