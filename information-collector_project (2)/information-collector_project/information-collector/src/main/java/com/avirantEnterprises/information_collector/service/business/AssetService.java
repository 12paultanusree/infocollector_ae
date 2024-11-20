package com.avirantEnterprises.information_collector.service.business;

import com.avirantEnterprises.information_collector.model.business.Asset;
import com.avirantEnterprises.information_collector.repository.business.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class AssetService {

    @Autowired
    private AssetRepository assetRepository;

    private final Path rootLocation = Paths.get("upload-business");

    // Method to register a new expense
    public void saveAsset(String assetName, String assetId, String category, String description, MultipartFile document) {
        Asset asset = new  Asset();
        asset.setAssetName(assetName);
        asset.setAssetId(assetId);
        asset.setCategory(category);
        asset.setDescription(description);

        String docPath = saveFile(document);  // Save the receipt file and get its path
        asset.setDocPath(docPath);

        assetRepository.save(asset);  // Save the expense to the database
    }

    // Method to update an existing expense
    public void updateAsset(Long id, String assetName, String assetId, String category, String description, MultipartFile document) {
        Optional<Asset> optionalAsset = assetRepository.findById(id);
        if (optionalAsset.isPresent()) {
            Asset asset = optionalAsset.get();
            asset.setAssetName(assetName);
            asset.setAssetId(assetId);
            asset.setCategory(category);
            asset.setDescription(description);
            if (!document.isEmpty()) {
                String docPath = saveFile(document);  // Save the new receipt file if provided
                asset.setDocPath(docPath);
            }
            assetRepository.save(asset);  // Save the updated expense to the database
        }
    }

    // Method to get an expense by its ID
    public Asset getAssetById(Long id) {
        return assetRepository.findById(id).orElse(null);
    }

    // Method to get all expenses
    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }

    // Method to delete an expense by its ID
    public void deleteAssetById(Long id) {
        assetRepository.deleteById(id);
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
