package com.avirantEnterprises.information_collector.config;

import com.avirantEnterprises.information_collector.model.Form;
import com.avirantEnterprises.information_collector.repository.FormRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class FormDataSeeder {

    @Bean
    public CommandLineRunner seedForms(FormRepository formRepository) {
        return args -> {
            // Check if the database is already seeded
            if (formRepository.count() == 0) {
                // Create and save the 5 forms
                formRepository.save(new Form("Expense Report", "Form for reporting expenses"));
                formRepository.save(new Form("Invoice Submission", "Form for submitting invoices"));
                formRepository.save(new Form("Tax Form", "Form for tax submissions"));
                formRepository.save(new Form("Employee Performance", "Form for employee performance evaluation"));
                formRepository.save(new Form("Asset Registration", "Form for registering company assets"));

                System.out.println("5 business forms have been seeded into the database.");
            }
        };
    }
}
