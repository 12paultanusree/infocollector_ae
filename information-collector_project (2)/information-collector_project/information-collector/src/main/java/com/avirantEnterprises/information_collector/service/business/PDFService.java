package com.avirantEnterprises.information_collector.service.business;

import com.avirantEnterprises.information_collector.model.business.Expense;
import com.avirantEnterprises.information_collector.model.business.Invoice;
import com.avirantEnterprises.information_collector.model.business.Tax;
import com.avirantEnterprises.information_collector.model.business.Employee;
import com.avirantEnterprises.information_collector.model.business.Asset;
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
public class PDFService {

    @Autowired
    private ExpenseService expenseService;
    private InvoiceService invoiceService;
    private TaxService taxService;
    private EmployeeService employeeService;
    private AssetService assetService;
    // Service for handling expense data
    @Autowired
    public PDFService(ExpenseService expenseService, InvoiceService invoiceService, TaxService taxService , EmployeeService employeeService, AssetService assetService) {
        this.expenseService = expenseService;
        this.invoiceService = invoiceService;
        this.taxService = taxService;
        this.employeeService = employeeService;
        this.assetService= assetService;// Constructor injection ensures this is initialized
    }

    private final Path rootLocation = Paths.get("upload-business"); // Directory to store business-related files

    // Method to generate Expense Report PDF
    public byte[] generateExpenseReportPdf(Long expenseId) throws IOException, DocumentException {
        // Retrieve the expense data from the database using the method from ExpenseService
        Expense expense = expenseService.getExpenseById(expenseId);

        if (expense == null) {
            throw new RuntimeException("Expense not found");
        }

        // Prepare to generate the PDF document
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // Add the header for the expense report
        document.add(new Paragraph("Expense Report"));
        document.add(new Paragraph("\n"));

        // Create a table for the expense details
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        // Add expense data to the table
        table.addCell("Amount");
        table.addCell(String.valueOf(expense.getAmount()));

        table.addCell("Date");
        table.addCell(expense.getExpenseDate().toString());

        table.addCell("Category");
        table.addCell(expense.getCategory());

        table.addCell("Description");
        table.addCell(expense.getDescription());

        // Add the table to the document
        document.add(table);

        // If there is an attached receipt image for the expense, include it in the report

        // Close the document and return the generated PDF as a byte array
        document.close();

        return outputStream.toByteArray();
    }
    public byte[] generateInvoiceReportPdf(Long invoiceId) throws IOException, DocumentException {
        // Retrieve the expense data from the database using the method from ExpenseService
        Invoice invoice = invoiceService.getInvoiceById(invoiceId);

        if (invoice == null) {
            throw new RuntimeException("Invoice not found");
        }

        // Prepare to generate the PDF document
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // Add the header for the expense report
        document.add(new Paragraph("Invoice Report"));
        document.add(new Paragraph("\n"));

        // Create a table for the expense details
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        // Add expense data to the table
        table.addCell("Amount");
        table.addCell(String.valueOf(invoice.getInvoiceAmount()));

        table.addCell("Date");
        table.addCell(invoice.getInvoiceDate().toString());

        table.addCell("Number");
        table.addCell(String.valueOf(invoice.getInvoiceNumber()));

        table.addCell("Description");
        table.addCell(invoice.getInvoiceDescription());

        // Add the table to the document
        document.add(table);

        // If there is an attached receipt image for the expense, include it in the report

        // Close the document and return the generated PDF as a byte array
        document.close();

        return outputStream.toByteArray();
    }
    public byte[] generateTaxReportPdf(Long taxId) throws IOException, DocumentException {
        // Retrieve the expense data from the database using the method from ExpenseService
        Tax tax = taxService.getTaxById(taxId);

        if (tax == null) {
            throw new RuntimeException("Tax not found");
        }

        // Prepare to generate the PDF document
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // Add the header for the expense report
        document.add(new Paragraph("Tax Report"));
        document.add(new Paragraph("\n"));

        // Create a table for the expense details
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        // Add expense data to the table
        table.addCell("Tax Date");
        table.addCell(String.valueOf(tax.getTaxYear()));

        table.addCell("Payer Name");
        table.addCell(tax.getTaxpayerName());

        table.addCell("Amount");
        table.addCell(String.valueOf(tax.getIncome()));

        table.addCell("Tax Paid");
        table.addCell(String.valueOf(tax.getTaxPaid()));

        // Add the table to the document
        document.add(table);

        // If there is an attached receipt image for the expense, include it in the report


        // Close the document and return the generated PDF as a byte array
        document.close();

        return outputStream.toByteArray();
    }

    public byte[] generateEmployeeReportPdf(Long employeeId) throws IOException, DocumentException {
        // Retrieve the expense data from the database using the method from ExpenseService
        Employee employee = employeeService.getEmployeeById(employeeId);

        if (employee == null) {
            throw new RuntimeException("Employee not found");
        }

        // Prepare to generate the PDF document
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // Add the header for the expense report
        document.add(new Paragraph("Employee Report"));
        document.add(new Paragraph("\n"));

        // Create a table for the expense details
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        // Add expense data to the table
        table.addCell("Employee Name");
        table.addCell(String.valueOf(employee.getEmployeeName()));

        table.addCell("Employee Id");
        table.addCell(employee.getEmployeeId());

        table.addCell("Department");
        table.addCell(String.valueOf(employee.getDepartment()));

        table.addCell("Performance Metrics");
        table.addCell(String.valueOf(employee.getPerformanceMetrics()));

        table.addCell("Performance Rating");
        table.addCell(String.valueOf(employee.getPerformanceRating()));

        // Add the table to the document
        document.add(table);

        // If there is an attached receipt image for the expense, include it in the report


        // Close the document and return the generated PDF as a byte array
        document.close();

        return outputStream.toByteArray();
    }

    public byte[] generateAssetReportPdf(Long assetId) throws IOException, DocumentException {
        // Retrieve the expense data from the database using the method from ExpenseService
        Asset asset = assetService.getAssetById(assetId);

        if (asset == null) {
            throw new RuntimeException("Asset not found");
        }

        // Prepare to generate the PDF document
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // Add the header for the expense report
        document.add(new Paragraph("Asset Report"));
        document.add(new Paragraph("\n"));

        // Create a table for the expense details
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        // Add expense data to the table
        table.addCell("Asset Name");
        table.addCell(asset.getAssetName());

        table.addCell("Asset Id");
        table.addCell(asset.getAssetId());

        table.addCell("Category");
        table.addCell(asset.getCategory());

        table.addCell("Description");
        table.addCell(asset.getDescription());

        // Add the table to the document
        document.add(table);

        // If there is an attached receipt image for the expense, include it in the report

        // Close the document and return the generated PDF as a byte array
        document.close();

        return outputStream.toByteArray();
    }

}
