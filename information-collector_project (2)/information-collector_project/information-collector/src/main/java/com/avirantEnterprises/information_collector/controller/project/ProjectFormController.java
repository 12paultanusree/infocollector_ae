package com.avirantEnterprises.information_collector.controller.project;

import com.avirantEnterprises.information_collector.model.project.Project;
import com.avirantEnterprises.information_collector.service.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Controller
public class ProjectFormController {

    @Autowired
    private ProjectService projectService;


    @GetMapping("/project/{id}")
    public String viewProject(@PathVariable Long id, Model model) {
        Project project = projectService.getProjectById(id);
        model.addAttribute("project", project);
        return "project/project_view";
    }


    @GetMapping("/project/delete/{id}")
    public String deleteProject(@PathVariable Long id) {
        projectService.deleteProjectById(id);
        return "redirect:/project_list";
    }


    @GetMapping("/project_list")
    public String listProject(Model model) {
        List<Project> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);
        return "project/project_list";
    }


    @GetMapping("/project/update/{id}")
    public String showUpdateProjectForm(@PathVariable Long id, Model model) {
        Project project = projectService.getProjectById(id);
        model.addAttribute("project", project);
        return "project/project_update";
    }


    @PostMapping("/updateProject")
    public String updateProject(@RequestParam("id") Long id,
                                @RequestParam("fullName") String fullName,
                                @RequestParam("email") String email,
                                @RequestParam("projectTitle") String projectTitle,
                                @RequestParam("projectDescription") String projectDescription,
                                @RequestParam("proposalPath") MultipartFile proposalPath) {
        projectService.updateProject(id, fullName, email, projectTitle, projectDescription, proposalPath);
        return "redirect:/project/" + id;
    }


    @GetMapping("/project/new")
    public String showProjectForm(Model model) {
        return "project/project_form";
    }


    @PostMapping("/submitProject")
    public String submitProject(@RequestParam("fullName") String fullName,
                                @RequestParam("email") String email,
                                @RequestParam("projectTitle") String projectTitle,
                                @RequestParam("projectDescription") String projectDescription,
                                @RequestParam("proposalPath") MultipartFile proposalPath) {
        projectService.saveProject(fullName, email, projectTitle, projectDescription, proposalPath);
        return "project/success";
    }
}
