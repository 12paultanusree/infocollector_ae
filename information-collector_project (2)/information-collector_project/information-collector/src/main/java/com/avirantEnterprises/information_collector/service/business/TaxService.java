package com.avirantEnterprises.information_collector.service.business;

import com.avirantEnterprises.information_collector.model.business.Tax;
import com.avirantEnterprises.information_collector.repository.business.TaxRepository;
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
public class TaxService {

    @Autowired
    private TaxRepository taxRepository;

    private final Path rootLocation = Paths.get("upload-business");


    public void saveTax(LocalDate taxYear, String taxpayerName, double income, double taxPaid,String filingStatus, MultipartFile taxreceipt) {
        Tax tax = new Tax();
        tax.setTaxYear(taxYear);
        tax.setTaxpayerName(taxpayerName);
        tax.setIncome(income);
        tax.setTaxPaid(taxPaid);
        tax.setFilingStatus(filingStatus);

        String taxDocument = saveFile(taxreceipt);
        tax.setTaxDocument(taxDocument);

        taxRepository.save(tax);
    }


    public void updateTax(Long id, LocalDate taxYear, String taxpayerName, double income, double taxPaid,String filingStatus, MultipartFile taxreceipt) {
        Optional<Tax> optionalTax = taxRepository.findById(id);
        if (optionalTax.isPresent()) {
            Tax tax = optionalTax.get();
            tax.setTaxYear(taxYear);
            tax.setTaxpayerName(taxpayerName);
            tax.setIncome(income);
            tax.setTaxPaid(taxPaid);
            tax.setFilingStatus(filingStatus);
            if (!taxreceipt.isEmpty()) {
                String receiptPath = saveFile(taxreceipt);
                tax.setTaxDocument(receiptPath);
            }
            taxRepository.save(tax);
        }
    }


    public Tax getTaxById(Long id) {
        return taxRepository.findById(id).orElse(null);
    }


    public List<Tax> getAllTaxes() {
        return taxRepository.findAll();
    }


    public void deleteTaxById(Long id) {
        taxRepository.deleteById(id);
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
