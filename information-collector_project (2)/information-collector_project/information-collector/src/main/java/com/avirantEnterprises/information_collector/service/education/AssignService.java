package com.avirantEnterprises.information_collector.service.education;

import com.avirantEnterprises.information_collector.model.education.Assign;
import com.avirantEnterprises.information_collector.repository.education.AssignRepository;
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
public class AssignService {

    @Autowired
    private AssignRepository assignRepository;

    private final Path rootLocation = Paths.get("upload-dir");


    public void saveAssign(String studentName, String studentId, String assignmentTitle, LocalDate submissionDate, MultipartFile assignmentPath) {
        Assign assign = new Assign();
        assign.setStudentName(studentName);
        assign.setStudentId(studentId);
        assign.setAssignmentTitle(assignmentTitle);
        assign.setSubmissionDate(submissionDate);

        String assignmentFile = saveFile(assignmentPath);
        assign.setAssignmentFile(assignmentFile);

        assignRepository.save(assign);
    }


    public void updateAssign(Long id, String studentName, String studentId, String assignmentTitle, LocalDate submissionDate, MultipartFile assignmentPath) {
        Optional<Assign> optionalAssign = assignRepository.findById(id);
        if (optionalAssign.isPresent()) {
            Assign assign = optionalAssign.get();
            assign.setStudentName(studentName);
            assign.setStudentId(studentId);
            assign.setAssignmentTitle(assignmentTitle);
            assign.setSubmissionDate(submissionDate);
            if (!assignmentPath.isEmpty()) {
                String assignmentFile = saveFile(assignmentPath);
                assign.setAssignmentFile(assignmentFile);
            }
            assignRepository.save(assign);
        }
    }


    public Assign getAssignById(Long id) {
        return assignRepository.findById(id).orElse(null);
    }


    public List<Assign> getAllAssigns() {
        return assignRepository.findAll();
    }


    public void deleteAssignById(Long id) {
        assignRepository.deleteById(id);
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
