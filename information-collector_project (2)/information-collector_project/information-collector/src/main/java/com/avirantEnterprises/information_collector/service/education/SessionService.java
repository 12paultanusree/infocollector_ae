package com.avirantEnterprises.information_collector.service.education;

import com.avirantEnterprises.information_collector.model.education.Assign;
import com.avirantEnterprises.information_collector.model.education.Session;
import com.avirantEnterprises.information_collector.repository.education.SessionRepository;
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
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    private final Path rootLocation = Paths.get("upload-dir");


    public void saveSession(String name, String email, String title, LocalDate date,String rating,String comments, MultipartFile feedbackPath) {
        Session session = new Session();
        session.setName(name);
        session.setEmail(email);
        session.setTitle(title);
        session.setDate(date);
        session.setRating(rating);
        session.setComments(comments);

        String feedbackFile = saveFile(feedbackPath);
        session.setFeedbackFile(feedbackFile);

        sessionRepository.save(session);
    }


    public void updateSession(Long id, String name, String email, String title, LocalDate date,String rating,String comments, MultipartFile feedbackPath) {
        Optional<Session> optionalSession = sessionRepository.findById(id);
        if (optionalSession.isPresent()) {
            Session session = optionalSession.get();
            session.setName(name);
            session.setEmail(email);
            session.setTitle(title);
            session.setDate(date);
            session.setRating(rating);
            session.setComments(comments);
            if (!feedbackPath.isEmpty()) {
                String feedbackFile = saveFile(feedbackPath);
                session.setFeedbackFile(feedbackFile);
            }
            sessionRepository.save(session);
        }
    }


    public Session getSessionById(Long id) {
        return sessionRepository.findById(id).orElse(null);
    }


    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }


    public void deleteSessionById(Long id) {
        sessionRepository.deleteById(id);
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
