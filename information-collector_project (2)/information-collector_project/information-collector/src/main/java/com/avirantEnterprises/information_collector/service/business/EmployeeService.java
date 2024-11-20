package com.avirantEnterprises.information_collector.service.business;

import com.avirantEnterprises.information_collector.model.business.Employee;
import com.avirantEnterprises.information_collector.repository.business.EmployeeRepository;
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
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    private final Path rootLocation = Paths.get("upload-business");

    // Method to register a new expense
    public void saveEmployee(String employeeName, String employeeId, String department, String performanceMetrics, String performanceRating,MultipartFile docpath) {
        Employee employee = new Employee();
        employee.setEmployeeName(employeeName);
        employee.setEmployeeId(employeeId);
        employee.setDepartment(department);
        employee.setPerformanceMetrics(performanceMetrics);
        employee.setPerformanceRating(performanceRating);

        String document = saveFile(docpath);  // Save the receipt file and get its path
        employee.setDocument(document);

        employeeRepository.save(employee);  // Save the expense to the database
    }

    // Method to update an existing expense
    public void updateEmployee(Long id, String employeeName, String employeeId, String department, String performanceMetrics, String performanceRating, MultipartFile docpath) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            employee.setEmployeeName(employeeName);
            employee.setEmployeeId(employeeId);
            employee.setDepartment(department);
            employee.setPerformanceMetrics(performanceMetrics);
            employee.setPerformanceRating(performanceRating);
            if (!docpath.isEmpty()) {
                String document = saveFile(docpath);  // Save the new receipt file if provided
                employee.setDocument(document);
            }
            employeeRepository.save(employee);  // Save the updated expense to the database
        }
    }

    // Method to get an expense by its ID
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    // Method to get all expenses
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Method to delete an expense by its ID
    public void deleteEmployeeById(Long id) {
        employeeRepository.deleteById(id);
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
