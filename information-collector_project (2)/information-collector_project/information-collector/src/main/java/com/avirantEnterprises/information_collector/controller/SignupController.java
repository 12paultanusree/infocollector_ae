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
    private ProfileRepository profileRepository; // User repository

    @Autowired
    private PasswordEncoder passwordEncoder; // For encoding passwords

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("profile", new Profile()); // Bind form to Profile entity
        return "signup"; // Thymeleaf signup form
    }

    @PostMapping("/signup")
    public String processSignup(Profile profile) {
        // Encode the user's password before saving
        profile.setPassword(passwordEncoder.encode(profile.getPassword()));
        profile.setRole("USER"); // Assign default role as USER
        profileRepository.save(profile); // Save user to the database
        return "redirect:/login"; // Redirect to login after successful signup
    }
}
