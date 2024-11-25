package com.avirantEnterprises.information_collector.model.education;

import com.itextpdf.text.Image;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.File;
import java.time.LocalDate;

@Entity
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String title;
    private LocalDate date;
    private String rating;
    private String comments;
    private String feedbackFile;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getFeedbackFile() {
        return feedbackFile;
    }

    public void setFeedbackFile(String feedbackFile) {
        this.feedbackFile = feedbackFile;
    }

    public Object getAttachmentPreview() {
        try {
            if (feedbackFile != null && !feedbackFile.isEmpty()) {
                String fileType = getFileExtension(feedbackFile);

                switch (fileType.toLowerCase()) {
                    case "jpg":
                    case "jpeg":
                    case "png":
                    case "gif":
                        // Handle image files
                        return Image.getInstance(feedbackFile);

                    case "pdf":
                        // For PDF files, return the file or a PDF object
                        File pdfFile = new File(feedbackFile);
                        if (pdfFile.exists()) {
                            return pdfFile; // Placeholder: Open with an external viewer
                        }
                        break;

                    case "xls":
                    case "xlsx":
                        // For Excel files, return the file or a parsed representation
                        File excelFile = new File(feedbackFile);
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
