package com.avirantEnterprises.information_collector.controller;

import com.avirantEnterprises.information_collector.model.Form;
import com.avirantEnterprises.information_collector.model.Profile;
import com.avirantEnterprises.information_collector.repository.FormRepository;
import com.avirantEnterprises.information_collector.repository.ProfileRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Set;

@Controller
public class DashboardController {

    private final FormRepository formRepository;
    private final ProfileRepository profileRepository;

    public DashboardController(FormRepository formRepository, ProfileRepository profileRepository) {
        this.formRepository = formRepository;
        this.profileRepository = profileRepository;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "Login"; // Refers to login.html
    }

    // User Dashboard
    @GetMapping("/user_dashboard")
    public String userDashboard(Model model) {
        model.addAttribute("dashboardTitle", "User Dashboard");
        return "/user_dashboard"; // Ensure this corresponds to user_dashboard.html
    }



    @GetMapping("/user/signup")
    public String signupPage() {
        return "signup"; // Refers to signup.html
    }

    // Role-Based Dashboard Redirect
    @GetMapping("/dashboard")
    public String redirectDashboard(Authentication authentication) {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        // Redirect based on role
        if (roles.contains("ROLE_ADMIN")) {
            return "redirect:/admin/dashboard";
        } else if (roles.contains("ROLE_USER")) {
            return "redirect:/user/dashboard";
        }

        // Default fallback in case no role is found
        return "error/unauthorized";
    }

    // Admin Dashboard
    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("welcomeMessage", "Welcome Admin to Avirant Enterprises!");
        // Add additional admin-specific data if necessary
        return "/dashboard"; // Refers to admin_dashboard.html
    }

    // Business and Financial Data Dashboard
    @GetMapping("/business_dashboard")
    public String businessFinancialDashboard(Model model) {
        // Fetch forms from the database
        List<Form> forms = formRepository.findAll();
        model.addAttribute("forms", forms);

        // Fetch users from the database
        List<Profile> users = profileRepository.findAll();
        model.addAttribute("users", users);

        model.addAttribute("dashboardTitle", "Business and Financial Dashboard");
        return "business/business_dashboard"; // Refers to business/business_dashboard.html
    }

    // Project and Task Management Dashboard
    @GetMapping("/project_dashboard")
    public String projectTaskDashboard(Model model) {
        model.addAttribute("dashboardTitle", "Project and Task Management Dashboard");
        return "project/project_dashboard"; // Refers to project/project_dashboard.html
    }

    // Educational and Training Data Dashboard
    @GetMapping("/education_dashboard")
    public String educationTrainingDashboard(Model model) {
        model.addAttribute("dashboardTitle", "Educational and Training Dashboard");
        return "education/education_dashboard"; // Refers to education/education_dashboard.html
    }

    // Personal Information Collection Dashboard
    @GetMapping("/personal_dashboard")
    public String personalInfoDashboard(Model model) {
        model.addAttribute("dashboardTitle", "Personal Information Dashboard");
        return "personal/personal_dashboard"; // Refers to personal/personal_dashboard.html
    }
}
