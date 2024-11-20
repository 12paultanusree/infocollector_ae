package com.avirantEnterprises.information_collector.model.business;

import com.itextpdf.text.Image;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.File;
import java.time.LocalDate;

@Entity
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate invoiceDate;
    private double invoiceNumber;
    private double invoiceAmount;
    private String invoiceDescription;
    private String invoiceFile;

    // Getters and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public double getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(double invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public double getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(double invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public String getInvoiceDescription() {
        return invoiceDescription;
    }

    public void setInvoiceDescription(String invoiceDescription) {
        this.invoiceDescription = invoiceDescription;
    }

    public String getInvoiceFile() {
        return invoiceFile;
    }

    public void setInvoiceFile(String invoiceFile) {
        this.invoiceFile = invoiceFile;
    }

    public Object getAttachmentPreview() {
        try {
            if (invoiceFile != null && !invoiceFile.isEmpty()) {
                String fileType = getFileExtension(invoiceFile);

                switch (fileType.toLowerCase()) {
                    case "jpg":
                    case "jpeg":
                    case "png":
                    case "gif":
                        // Handle image files
                        return Image.getInstance(invoiceFile);

                    case "pdf":
                        // For PDF files, return the file or a PDF object
                        File pdfFile = new File(invoiceFile);
                        if (pdfFile.exists()) {
                            return pdfFile; // Placeholder: Open with an external viewer
                        }
                        break;

                    case "xls":
                    case "xlsx":
                        // For Excel files, return the file or a parsed representation
                        File excelFile = new File(invoiceFile);
                        if (excelFile.exists()) {
                            return excelFile; // Placeholder: Open with an external viewer
                        }
                        break;

                    default:
                        System.out.println("Unsupported file type: " + fileType);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();  // Handle any exceptions that may arise
        }
        return null;  // Return null if no valid preview can be generated
    }

    private String getFileExtension(String filePath) {
        String extension = "";
        int i = filePath.lastIndexOf('.');
        if (i > 0) {
            extension = filePath.substring(i + 1);
        }
        return extension;
    }
}
