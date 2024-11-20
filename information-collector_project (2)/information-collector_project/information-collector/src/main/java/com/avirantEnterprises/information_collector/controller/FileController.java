package com.avirantEnterprises.information_collector.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/files")
public class FileController {

    @GetMapping("/{packageType}/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String packageType, @PathVariable String filename) {
        try {
            // Determine the correct directory based on the packageType (personal or business)
            String baseDirectory = "upload-dir";  // Default to personal directory

            // If the packageType is "business", use the business directory
            if ("business".equalsIgnoreCase(packageType)) {
                baseDirectory = "upload-business";
            }

            // Resolve the file path based on the selected directory
            Path filePath = Paths.get("C://Users//pault//IdeaProjects//information-collector_project (2)//information-collector_project//information-collector" + baseDirectory)
                    .resolve(filename);

            Resource file = new UrlResource(filePath.toUri());

            if (file.exists() || file.isReadable()) {
                // Set the content type based on the file extension, here we assume it's an image (adjust as needed)
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)  // You can dynamically set the content type based on the file extension
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                        .body(file);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
