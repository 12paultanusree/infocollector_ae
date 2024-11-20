package com.avirantEnterprises.information_collector.controller.business;

import com.avirantEnterprises.information_collector.service.business.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class EmployeeReportController {

    @Autowired
    private EmployeeService employeeReportService;

    @GetMapping("/employee_form")
    public String showEmployeeReportForm() {
        return "business/employee_form";
    }

    @PostMapping("/submitEmployeeForm")
    public String submitEmployee(
            @RequestParam("employeeName") String employeeName,
            @RequestParam("employeeId") String employeeId,
            @RequestParam("department") String department,
            @RequestParam("performanceMetrics") String performanceMetrics,
            @RequestParam("performanceRating") String performanceRating,
            @RequestParam("docpath") MultipartFile docpath) {

        employeeReportService.saveEmployee(employeeName, employeeId, department, performanceMetrics, performanceRating, docpath);
        return "business/success";
    }
}
