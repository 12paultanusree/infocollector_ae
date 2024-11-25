package com.avirantEnterprises.information_collector.service.education;

import com.avirantEnterprises.information_collector.model.education.Certificate;
import com.avirantEnterprises.information_collector.repository.education.CertificateRepository;
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
public class CertificateService {

    @Autowired
    private CertificateRepository certificateRepository;

    private final Path rootLocation = Paths.get("upload-dir");


    public void saveCertificate(String userName, String userId, String courseName, String certificationTitle, LocalDate certificationDate, MultipartFile certificatePath) {
        Certificate certificate = new Certificate();
        certificate.setUserName(userName);
        certificate.setUserId(userId);
        certificate.setCourseName(courseName);
        certificate.setCertificationTitle(certificationTitle);
        certificate.setCertificationDate(certificationDate);

        String certificateDocument = saveFile(certificatePath);
        certificate.setCertificateDocument(certificateDocument);

        certificateRepository.save(certificate);
    }


    public void updateCertificate(Long id, String userName, String userId, String courseName, String certificationTitle,LocalDate certificationDate, MultipartFile certificatePath) {
        Optional<Certificate> optionalCertificate = certificateRepository.findById(id);
        if (optionalCertificate.isPresent()) {
            Certificate certificate = optionalCertificate.get();
            certificate.setUserName(userName);
            certificate.setUserId(userId);
            certificate.setCourseName(courseName);
            certificate.setCertificationTitle(certificationTitle);
            certificate.setCertificationDate(certificationDate);
            if (!certificatePath.isEmpty()) {
                String certificateDocument = saveFile(certificatePath);
                certificate.setCertificateDocument(certificateDocument);
            }
            certificateRepository.save(certificate);
        }
    }


    public Certificate getCertificateById(Long id) {
        return certificateRepository.findById(id).orElse(null);
    }


    public List<Certificate> getAllCertificates() {
        return certificateRepository.findAll();
    }


    public void deleteCertificateById(Long id) {
        certificateRepository.deleteById(id);
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
