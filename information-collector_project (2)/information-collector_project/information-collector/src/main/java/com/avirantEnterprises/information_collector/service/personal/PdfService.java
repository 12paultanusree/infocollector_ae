package com.avirantEnterprises.information_collector.service.personal;

import com.avirantEnterprises.information_collector.model.personal.Survey;

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
public class PdfService {

    @Autowired
    private SurveyService surveyService;
    // Service for handling expense data
    @Autowired
    public PdfService(SurveyService surveyService) {
        this.surveyService = surveyService;// Constructor injection ensures this is initialized
    }

    private final Path rootLocation = Paths.get("upload-personal"); // Directory to store business-related files

    // Method to generate Expense Report PDF
    public byte[] generateSurveyPdf(Long surveyId) throws IOException, DocumentException {
        // Retrieve the expense data from the database using the method from ExpenseService
        Survey survey = surveyService.getSurveyById(surveyId);

        if (survey == null) {
            throw new RuntimeException("Survey not found");
        }

        // Prepare to generate the PDF document
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // Add the header for the expense report
        document.add(new Paragraph("Survey Report"));
        document.add(new Paragraph("\n"));

        // Create a table for the expense details
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        // Add expense data to the table
        table.addCell("Name");
        table.addCell(survey.getUserName());

        table.addCell("Email");
        table.addCell(survey.getEmail());

        table.addCell("Feedback");
        table.addCell(survey.getFeedback());

        table.addCell("Interests");
        table.addCell(survey.getInterests());

        // Add the table to the document
        document.add(table);

        // If there is an attached receipt image for the expense, include it in the report

        // Close the document and return the generated PDF as a byte array
        document.close();

        return outputStream.toByteArray();
    }
}
