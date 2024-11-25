package com.avirantEnterprises.information_collector.service.education;

import com.avirantEnterprises.information_collector.model.education.Course;
import com.avirantEnterprises.information_collector.repository.education.CourseRepository;
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
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    private final Path rootLocation = Paths.get("upload-dir");


    public void saveCourse(String studentName, String studentId, String courseName, String courseDescription, MultipartFile idPath) {
        Course course = new Course();
        course.setStudentName(studentName);
        course.setStudentId(studentId);
        course.setCourseName(courseName);
        course.setCourseDescription(courseDescription);

        String idDocument = saveFile(idPath);
        course.setIdDocument(idDocument);

        courseRepository.save(course);
    }


    public void updateCourse(Long id, String studentName, String studentId, String courseName, String courseDescription, MultipartFile idPath) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            course.setStudentName(studentName);
            course.setStudentId(studentId);
            course.setCourseName(courseName);
            course.setCourseDescription(courseDescription);
            if (!idPath.isEmpty()) {
                String idDocument = saveFile(idPath);
                course.setIdDocument(idDocument);
            }
            courseRepository.save(course);
        }
    }


    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }


    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }


    public void deleteCourseById(Long id) {
        courseRepository.deleteById(id);
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
