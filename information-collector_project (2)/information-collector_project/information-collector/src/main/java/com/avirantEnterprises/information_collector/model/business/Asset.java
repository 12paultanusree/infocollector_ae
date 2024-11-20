package com.avirantEnterprises.information_collector.model.business;

import com.itextpdf.text.Image;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

@Entity
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String assetName;
    private String assetId;
    private String category;
    private String description;
    private String docPath;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDocPath() {
        return docPath;
    }

    public void setDocPath(String docPath) {
        this.docPath = docPath;
    }

    public Object getAttachmentPreview() {
        try {
            if (docPath != null && !docPath.isEmpty()) {
                String fileType = getFileExtension(docPath);

                switch (fileType.toLowerCase()) {
                    case "jpg":
                    case "jpeg":
                    case "png":
                    case "gif":
                        // Handle image files
                        return Image.getInstance(docPath);

                    case "pdf":
                        // For PDF files, return the file or a PDF object
                        File pdfFile = new File(docPath);
                        if (pdfFile.exists()) {
                            return pdfFile; // Placeholder: Open with an external viewer
                        }
                        break;

                    case "xls":
                    case "xlsx":
                        // For Excel files, return the file or a parsed representation
                        File excelFile = new File(docPath);
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
