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


    @GetMapping("/employee/{id}")
    public String viewEmployee(@PathVariable Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "business/employee_view";
    }


    @GetMapping("/employee/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployeeById(id);
        return "redirect:/employee_list";
    }


    @GetMapping("/employee_list")
    public String listEmployee(Model model) {
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        return "business/employee_list";
    }


    @GetMapping("/employee/update/{id}")
    public String showUpdateEmployeeForm(@PathVariable Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "business/employee_update";
    }


    @PostMapping("/updateEmployee")
    public String updateEmployee(@RequestParam("id") Long id,
                                @RequestParam("employeeName") String employeeName,
                                @RequestParam("employeeId") String employeeId,
                                @RequestParam("department") String department,
                                @RequestParam("performanceMetrics") String performanceMetrics,
                                 @RequestParam("performanceRating") String performanceRating,
                                @RequestParam("docpath") MultipartFile docpath) {
        employeeService.updateEmployee(id, employeeName, employeeId, department, performanceMetrics, performanceRating,docpath);
        return "redirect:/employee/" + id;
    }


    @GetMapping("/employee/new")
    public String showEmployeeForm(Model model) {
        return "business/employee_form";
    }


    @PostMapping("/submitEmployee")
    public String submitEmployee(@RequestParam("employeeName") String employeeName,
                                @RequestParam("employeeId") String employeeId,
                                @RequestParam("department") String department,
                                @RequestParam("performanceMetrics") String performanceMetrics,
                                 @RequestParam("performanceRating") String performanceRating,
                                @RequestParam("docpath") MultipartFile docpath) {
        employeeService.saveEmployee(employeeName, employeeId, department, performanceMetrics,performanceRating, docpath);
        return "business/success";
    }
}
