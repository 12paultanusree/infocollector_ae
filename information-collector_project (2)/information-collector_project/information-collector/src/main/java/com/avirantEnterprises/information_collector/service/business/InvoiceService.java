package com.avirantEnterprises.information_collector.service.business;

import com.avirantEnterprises.information_collector.model.business.Invoice;
import com.avirantEnterprises.information_collector.repository.business.InvoiceRepository;
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
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    private final Path rootLocation = Paths.get("upload-business");


    public void saveInvoice(LocalDate invoiceDate, double invoiceAmount, double invoiceNumber, String invoiceDescription, MultipartFile invoicereceipt) {
        Invoice invoice = new Invoice();
        invoice.setInvoiceDate(invoiceDate);
        invoice.setInvoiceAmount(invoiceAmount);
        invoice.setInvoiceNumber(invoiceNumber);
        invoice.setInvoiceDescription(invoiceDescription);

        String invoiceFile = saveFile(invoicereceipt);
        invoice.setInvoiceFile(invoiceFile);

        invoiceRepository.save(invoice);
    }

    // Method to update an existing expense
    public void updateInvoice(Long id, LocalDate invoiceDate, double invoiceAmount, double invoiceNumber, String invoiceDescription, MultipartFile invoicereceipt) {
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(id);
        if (optionalInvoice.isPresent()) {
            Invoice invoice = optionalInvoice.get();
            invoice.setInvoiceDate(invoiceDate);
            invoice.setInvoiceAmount(invoiceAmount);
            invoice.setInvoiceNumber(invoiceNumber);
            invoice.setInvoiceDescription(invoiceDescription);
            if (!invoicereceipt.isEmpty()) {
                String invoiceFile = saveFile(invoicereceipt);
                invoice.setInvoiceFile(invoiceFile);
            }
            invoiceRepository.save(invoice);
        }
    }


    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id).orElse(null);
    }


    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }


    public void deleteInvoiceById(Long id) {
        invoiceRepository.deleteById(id);
    }


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
