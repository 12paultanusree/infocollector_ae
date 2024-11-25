package com.avirantEnterprises.information_collector.controller.education;

import com.avirantEnterprises.information_collector.model.education.Session;
import com.avirantEnterprises.information_collector.service.education.SessionService;
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
public class SessionFormController {

    @Autowired
    private SessionService sessionService;


    @GetMapping("/session/{id}")
    public String viewSession(@PathVariable Long id, Model model) {
        Session session = sessionService.getSessionById(id);
        model.addAttribute("session", session);
        return "education/session_view";
    }


    @GetMapping("/session/delete/{id}")
    public String deleteSession(@PathVariable Long id) {
        sessionService.deleteSessionById(id);
        return "redirect:/session_list";
    }


    @GetMapping("/session_list")
    public String listSessions(Model model) {
        List<Session> sessions = sessionService.getAllSessions();
        model.addAttribute("sessions", sessions);
        return "education/session_list";
    }


    @GetMapping("/session/update/{id}")
    public String showUpdateSessionForm(@PathVariable Long id, Model model) {
        Session session = sessionService.getSessionById(id);
        model.addAttribute("session", session);
        return "education/session_update";
    }


    @PostMapping("/updateSession")
    public String updateSession(@RequestParam("id") Long id,
                                @RequestParam("name") String name,
                                @RequestParam("email") String email,
                                @RequestParam("title") String title,
                                @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                @RequestParam("rating") String rating,
                                @RequestParam("comments") String comments,
                                @RequestParam("feedbackPath") MultipartFile feedbackPath) {
        sessionService.updateSession(id, name, email,title, date,rating,comments, feedbackPath);
        return "redirect:/session/" + id;
    }


    @GetMapping("/session/new")
    public String showSessionForm(Model model) {
        return "education/session_form";
    }


    @PostMapping("/submitSession")
    public String submitSession(   @RequestParam("name") String name,
                                   @RequestParam("email") String email,
                                   @RequestParam("title") String title,
                                   @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                   @RequestParam("rating") String rating,
                                   @RequestParam("comments") String comments,
                                   @RequestParam("feedbackPath") MultipartFile feedbackPath) {
        sessionService.saveSession(name, email,title, date,rating,comments, feedbackPath);
        return "education/success";
    }
}
