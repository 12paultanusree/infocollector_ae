package com.avirantEnterprises.information_collector.service.personal;

import com.avirantEnterprises.information_collector.model.personal.Survey;
import com.avirantEnterprises.information_collector.repository.personal.SurveyRepository;
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
public class SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    private final Path rootLocation = Paths.get("upload-personal");

    // Method to register a new expense
    public void saveSurvey(String userName, String email, String feedback, String interests, MultipartFile filePath) {
        Survey survey = new  Survey();
        survey.setUserName(userName);
        survey.setEmail(email);
        survey.setFeedback(feedback);
        survey.setInterests(interests);

        String fileUpload = saveFile(filePath);  // Save the receipt file and get its path
        survey.setFileUpload(fileUpload);

        surveyRepository.save(survey);  // Save the expense to the database
    }

    // Method to update an existing expense
    public void updateSurvey(Long id, String userName, String email, String feedback, String interests, MultipartFile filePath) {
        Optional<Survey> optionalSurvey = surveyRepository.findById(id);
        if (optionalSurvey.isPresent()) {
            Survey survey = optionalSurvey.get();
            survey.setUserName(userName);
            survey.setEmail(email);
            survey.setFeedback(feedback);
            survey.setInterests(interests);
            if (!filePath.isEmpty()) {
                String fileUpload = saveFile(filePath);  // Save the new receipt file if provided
                survey.setFileUpload(fileUpload);
            }
            surveyRepository.save(survey);  // Save the updated expense to the database
        }
    }

    // Method to get an expense by its ID
    public Survey getSurveyById(Long id) {
        return surveyRepository.findById(id).orElse(null);
    }

    // Method to get all expenses
    public List<Survey> getAllSurveys() {
        return surveyRepository.findAll();
    }

    // Method to delete an expense by its ID
    public void deleteSurveyById(Long id) {
        surveyRepository.deleteById(id);
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
