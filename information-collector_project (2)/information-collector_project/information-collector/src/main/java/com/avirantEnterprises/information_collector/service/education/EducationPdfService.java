package com.avirantEnterprises.information_collector.service.education;

import com.avirantEnterprises.information_collector.model.education.Course;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class EducationPdfService {

    @Autowired
    private CourseService courseService;
    @Autowired
    public EducationPdfService(CourseService courseService) {
        this.courseService = courseService;
    }

    private final Path rootLocation = Paths.get("upload-dir"); // Directory to store business-related files

    // Method to generate Expense Report PDF
    public byte[] generateCourseReportPdf(Long courseId) throws IOException, DocumentException {
        // Retrieve the expense data from the database using the method from ExpenseService
        Course course = courseService.getCourseById(courseId);

        if (course == null) {
            throw new RuntimeException("Course not found");
        }

        // Prepare to generate the PDF document
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // Add the header for the expense report
        document.add(new Paragraph("Course Report"));
        document.add(new Paragraph("\n"));

        // Create a table for the expense details
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        // Add expense data to the table
        table.addCell("Student Name");
        table.addCell(course.getStudentName());

        table.addCell("Student Id");
        table.addCell(course.getStudentId());

        table.addCell("Course Name");
        table.addCell(course.getCourseName());

        table.addCell("Course Description");
        table.addCell(course.getCourseDescription());

        // Add the table to the document
        document.add(table);

        // If there is an attached receipt image for the expense, include it in the report

        // Close the document and return the generated PDF as a byte array
        document.close();

        return outputStream.toByteArray();
    }


}
