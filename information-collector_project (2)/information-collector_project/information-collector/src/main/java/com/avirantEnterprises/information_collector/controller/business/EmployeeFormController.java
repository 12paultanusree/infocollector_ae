package com.avirantEnterprises.information_collector.controller.business;

import com.avirantEnterprises.information_collector.model.business.Employee;
import com.avirantEnterprises.information_collector.service.business.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Controller
public class EmployeeFormController {

    @Autowired
    private EmployeeService employeeService;

    // View specific expense details
    @GetMapping("/employee/{id}")
    public String viewEmployee(@PathVariable Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "business/employee_view"; // View template for displaying the specific expense
    }

    // Delete specific expense by id
    @GetMapping("/employee/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployeeById(id);
        return "business/success"; // Redirect to a list of expenses after deletion
    }

    // List all expenses
    @GetMapping("/employee_list")
    public String listEmployee(Model model) {
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        return "business/employee_list"; // Template that lists all expenses
    }

    // Show the form to update an existing expense
    @GetMapping("/employee/update/{id}")
    public String showUpdateEmployeeForm(@PathVariable Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "business/employee_update"; // Template to show update form
    }

    // Handle the submission of the expense update form
    @PostMapping("/updateEmployee")
    public String updateEmployee(@RequestParam("id") Long id,
                                @RequestParam("employeeName") String employeeName,
                                @RequestParam("employeeId") String employeeId,
                                @RequestParam("department") String department,
                                @RequestParam("performanceMetrics") String performanceMetrics,
                                 @RequestParam("performanceRating") String performanceRating,
                                @RequestParam("docpath") MultipartFile docpath) {
        employeeService.updateEmployee(id, employeeName, employeeId, department, performanceMetrics, performanceRating,docpath);
        return "redirect:/employee/" + id; // Redirect to the updated expense page
    }

    // Show the form to add a new expense
    @GetMapping("/employee/new")
    public String showEmployeeForm(Model model) {
        return "business/employee_form"; // Template to show the form for creating a new expense
    }

    // Handle the submission of the new expense form
    @PostMapping("/submitEmployee")
    public String submitEmployee(@RequestParam("employeeName") String employeeName,
                                @RequestParam("employeeId") String employeeId,
                                @RequestParam("department") String department,
                                @RequestParam("performanceMetrics") String performanceMetrics,
                                 @RequestParam("performanceRating") String performanceRating,
                                @RequestParam("docpath") MultipartFile docpath) {
        employeeService.saveEmployee(employeeName, employeeId, department, performanceMetrics,performanceRating, docpath);
        return "redirect:/employee_list"; // Redirect to the list of expenses after submission
    }
}
