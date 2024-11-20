package com.avirantEnterprises.information_collector.service.business;

import com.avirantEnterprises.information_collector.model.business.Expense;
import com.avirantEnterprises.information_collector.repository.business.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    private final Path rootLocation = Paths.get("upload-business");

    // Method to register a new expense
    public void saveExpense(LocalDate expenseDate, double amount, String category, String description, MultipartFile receipt) {
        Expense expense = new Expense();
        expense.setExpenseDate(expenseDate);
        expense.setAmount(amount);
        expense.setCategory(category);
        expense.setDescription(description);

        String receiptPath = saveFile(receipt);  // Save the receipt file and get its path
        expense.setReceiptPath(receiptPath);

        expenseRepository.save(expense);  // Save the expense to the database
    }

    // Method to update an existing expense
    public void updateExpense(Long id, LocalDate expenseDate, double amount, String category, String description, MultipartFile receipt) {
        Optional<Expense> optionalExpense = expenseRepository.findById(id);
        if (optionalExpense.isPresent()) {
            Expense expense = optionalExpense.get();
            expense.setExpenseDate(expenseDate);
            expense.setAmount(amount);
            expense.setCategory(category);
            expense.setDescription(description);
            if (!receipt.isEmpty()) {
                String receiptPath = saveFile(receipt);  // Save the new receipt file if provided
                expense.setReceiptPath(receiptPath);
            }
            expenseRepository.save(expense);  // Save the updated expense to the database
        }
    }

    // Method to get an expense by its ID
    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id).orElse(null);
    }

    // Method to get all expenses
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    // Method to delete an expense by its ID
    public void deleteExpenseById(Long id) {
        expenseRepository.deleteById(id);
    }

    // Helper method to sanitize the file name before saving
    private String sanitizeFileName(String fileName) {
        return fileName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");  // Sanitize the file name
    }

    // Method to save the receipt file and return its path
    private String saveFile(MultipartFile file) {
        try {
            Files.createDirectories(rootLocation);  // Ensure the directory exists
            String sanitizedFileName = sanitizeFileName(file.getOriginalFilename());
            Path destinationFile = rootLocation.resolve(Paths.get(sanitizedFileName))
                    .normalize().toAbsolutePath();
            file.transferTo(destinationFile);  // Save the file to the specified location
            return sanitizedFileName;  // Return the sanitized file name
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file.", e);
        }
    }
}
