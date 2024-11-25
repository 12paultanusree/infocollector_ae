package com.avirantEnterprises.information_collector.service.project;

import com.avirantEnterprises.information_collector.model.project.Project;
import com.avirantEnterprises.information_collector.repository.project.ProjectRepository;
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
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    private final Path rootLocation = Paths.get("upload-dir");


    public void saveProject(String fullName, String email, String projectTitle, String projectDescription, MultipartFile proposalPath) {
        Project project = new  Project();
        project.setFullName(fullName);
        project.setEmail(email);
        project.setProjectTitle(projectTitle);
        project.setProjectDescription(projectDescription);

        String proposalDocument = saveFile(proposalPath);
        project.setProposalDocument(proposalDocument);

        projectRepository.save(project);
    }


    public void updateProject(Long id, String fullName, String email, String projectTitle, String projectDescription, MultipartFile proposalPath) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            project.setFullName(fullName);
            project.setEmail(email);
            project.setProjectTitle(projectTitle);
            project.setProjectDescription(projectDescription);
            if (!proposalPath.isEmpty()) {
                String proposalDocument = saveFile(proposalPath);
                project.setProposalDocument(proposalDocument);
            }
            projectRepository.save(project);
        }
    }


    public Project getProjectById(Long id) {
        return projectRepository.findById(id).orElse(null);
    }


    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }


    public void deleteProjectById(Long id) {
        projectRepository.deleteById(id);
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
