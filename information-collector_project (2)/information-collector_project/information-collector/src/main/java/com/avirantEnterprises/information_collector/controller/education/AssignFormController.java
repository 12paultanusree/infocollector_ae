package com.avirantEnterprises.information_collector.controller.education;

import com.avirantEnterprises.information_collector.model.education.Assign;
import com.avirantEnterprises.information_collector.service.education.AssignService;
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
public class AssignFormController {

    @Autowired
    private AssignService assignService;


    @GetMapping("/assign/{id}")
    public String viewAssign(@PathVariable Long id, Model model) {
        Assign assign = assignService.getAssignById(id);
        model.addAttribute("assign", assign);
        return "education/assignment_view"; // View template for displaying the specific expense
    }


    @GetMapping("/assign/delete/{id}")
    public String deleteAssign(@PathVariable Long id) {
        assignService.deleteAssignById(id);
        return "redirect:/assignment_list"; // Redirect to a list of expenses after deletion
    }


    @GetMapping("/assignment_list")
    public String listAssigns(Model model) {
        List<Assign> assigns = assignService.getAllAssigns();
        model.addAttribute("assigns", assigns);
        return "education/assignment_list"; // Template that lists all expenses
    }


    @GetMapping("/assign/update/{id}")
    public String showUpdateAssignForm(@PathVariable Long id, Model model) {
        Assign assign = assignService.getAssignById(id);
        model.addAttribute("assign", assign);
        return "education/assignment_update"; // Template to show update form
    }


    @PostMapping("/updateAssign")
    public String updateAssign(@RequestParam("id") Long id,
                               @RequestParam("studentName") String studentName,
                               @RequestParam("studentId") String studentId,
                               @RequestParam("assignmentTitle") String assignmentTitle,
                               @RequestParam("submissionDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate submissionDate,
                               @RequestParam("assignmentPath") MultipartFile assignmentPath) {
        assignService.updateAssign(id, studentName, studentId, assignmentTitle, submissionDate, assignmentPath);
        return "redirect:/assign/" + id; // Redirect to the updated expense page
    }


    @GetMapping("/assign/new")
    public String showAssignForm(Model model) {
        return "education/assignment_form"; // Template to show the form for creating a new expense
    }


    @PostMapping("/submitAssignment")
    public String submitAssign(  @RequestParam("studentName") String studentName,
                                 @RequestParam("studentId") String studentId,
                                 @RequestParam("assignmentTitle") String assignmentTitle,
                                 @RequestParam("submissionDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate submissionDate,
                                 @RequestParam("assignmentPath") MultipartFile assignmentPath) {
        assignService.saveAssign(studentName, studentId, assignmentTitle, submissionDate, assignmentPath);
        return "education/success"; // Redirect to the list of expenses after submission
    }
}
