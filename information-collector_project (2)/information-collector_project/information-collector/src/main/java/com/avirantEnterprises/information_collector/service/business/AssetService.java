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


    public void saveAsset(String assetName, String assetId, String category, String description, MultipartFile document) {
        Asset asset = new  Asset();
        asset.setAssetName(assetName);
        asset.setAssetId(assetId);
        asset.setCategory(category);
        asset.setDescription(description);

        String docPath = saveFile(document);
        asset.setDocPath(docPath);

        assetRepository.save(asset);
    }


    public void updateAsset(Long id, String assetName, String assetId, String category, String description, MultipartFile document) {
        Optional<Asset> optionalAsset = assetRepository.findById(id);
        if (optionalAsset.isPresent()) {
            Asset asset = optionalAsset.get();
            asset.setAssetName(assetName);
            asset.setAssetId(assetId);
            asset.setCategory(category);
            asset.setDescription(description);
            if (!document.isEmpty()) {
                String docPath = saveFile(document);
                asset.setDocPath(docPath);
            }
            assetRepository.save(asset);
        }
    }


    public Asset getAssetById(Long id) {
        return assetRepository.findById(id).orElse(null);
    }


    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }


    public void deleteAssetById(Long id) {
        assetRepository.deleteById(id);
    }


    private String sanitizeFileName(String fileName) {
        return fileName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");  // Sanitize the file name
    }


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
