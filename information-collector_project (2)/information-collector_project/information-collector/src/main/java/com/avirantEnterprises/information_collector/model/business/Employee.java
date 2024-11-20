package com.avirantEnterprises.information_collector.model.business;

import com.itextpdf.text.Image;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.File;
import java.time.LocalDate;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String employeeName;
    private String employeeId;
    private String department;
    private String performanceMetrics;
    private String performanceRating;
    private String document;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPerformanceMetrics() {
        return performanceMetrics;
    }

    public void setPerformanceMetrics(String performanceMetrics) {
        this.performanceMetrics = performanceMetrics;
    }

    public String getPerformanceRating() {
        return performanceRating;
    }

    public void setPerformanceRating(String performanceRating) {
        this.performanceRating = performanceRating;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public Object getAttachmentPreview() {
        try {
            if (document != null && !document.isEmpty()) {
                String fileType = getFileExtension(document);

                switch (fileType.toLowerCase()) {
                    case "jpg":
                    case "jpeg":
                    case "png":
                    case "gif":
                        // Handle image files
                        return Image.getInstance(document);

                    case "pdf":
                        // For PDF files, return the file or a PDF object
                        File pdfFile = new File(document);
                        if (pdfFile.exists()) {
                            return pdfFile; // Placeholder: Open with an external viewer
                        }
                        break;

                    case "xls":
                    case "xlsx":
                        // For Excel files, return the file or a parsed representation
                        File excelFile = new File(document);
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
