package com.avirantEnterprises.information_collector.model.education;

import com.itextpdf.text.Image;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.File;
import java.time.LocalDate;

@Entity
public class Assign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String studentName;
    private String studentId;
    private String assignmentTitle;
    private LocalDate submissionDate;
    private String assignmentFile;

    // Getters and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getAssignmentTitle() {
        return assignmentTitle;
    }

    public void setAssignmentTitle(String assignmentTitle) {
        this.assignmentTitle = assignmentTitle;
    }

    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }

    public String getAssignmentFile() {
        return assignmentFile;
    }

    public void setAssignmentFile(String assignmentFile) {
        this.assignmentFile = assignmentFile;
    }

    public Object getAttachmentPreview() {
        try {
            if (assignmentFile != null && !assignmentFile.isEmpty()) {
                String fileType = getFileExtension(assignmentFile);

                switch (fileType.toLowerCase()) {
                    case "jpg":
                    case "jpeg":
                    case "png":
                    case "gif":
                        // Handle image files
                        return Image.getInstance(assignmentFile);

                    case "pdf":
                        // For PDF files, return the file or a PDF object
                        File pdfFile = new File(assignmentFile);
                        if (pdfFile.exists()) {
                            return pdfFile; // Placeholder: Open with an external viewer
                        }
                        break;

                    case "xls":
                    case "xlsx":
                        // For Excel files, return the file or a parsed representation
                        File excelFile = new File(assignmentFile);
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
