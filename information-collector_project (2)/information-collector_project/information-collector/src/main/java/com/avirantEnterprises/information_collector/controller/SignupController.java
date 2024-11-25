package com.avirantEnterprises.information_collector.controller;

import com.avirantEnterprises.information_collector.model.Profile;
import com.avirantEnterprises.information_collector.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignupController {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // For encoding passwords

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("profile", new Profile());
        return "signup"; // Thymeleaf signup form
    }

    @PostMapping("/signup")
    public String processSignup(Profile profile, Model model) {
        // Check if the username is already taken
        if (profileRepository.findByUsername(profile.getUsername()).isPresent()) {
            model.addAttribute("error", "Username is already taken. Please choose another one.");
            return "signup"; // Return back to the signup page with an error
        }

        // Optional: Add additional validations here (e.g., email format, password strength)

        // Encode the password before saving
        profile.setPassword(passwordEncoder.encode(profile.getPassword()));

        // Assign default role as USER
        profile.setRole("USER");

        // Save the profile
        profileRepository.save(profile);

        // Redirect to the login page with a success message
        model.addAttribute("success", "Account created successfully. Please log in.");
        return "redirect:/login";
    }
}
