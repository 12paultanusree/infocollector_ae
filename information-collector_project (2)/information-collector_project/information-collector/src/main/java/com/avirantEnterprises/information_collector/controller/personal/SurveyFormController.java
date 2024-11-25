package com.avirantEnterprises.information_collector.controller.personal;

import com.avirantEnterprises.information_collector.model.personal.Survey;
import com.avirantEnterprises.information_collector.service.personal.SurveyService;
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
public class SurveyFormController {

    @Autowired
    private SurveyService surveyService;


    @GetMapping("/survey/{id}")
    public String viewSurvey(@PathVariable Long id, Model model) {
        Survey survey = surveyService.getSurveyById(id);
        model.addAttribute("survey", survey);
        return "personal/survey_view";
    }


    @GetMapping("/survey/delete/{id}")
    public String deleteSurvey(@PathVariable Long id) {
        surveyService.deleteSurveyById(id);
        return "redirect:/survey_list";
    }


    @GetMapping("/survey_list")
    public String listSurvey(Model model) {
        List<Survey> surveys = surveyService.getAllSurveys();
        model.addAttribute("surveys", surveys);
        return "personal/survey_list";
    }


    @GetMapping("/survey/update/{id}")
    public String showUpdateSurveyForm(@PathVariable Long id, Model model) {
        Survey survey = surveyService.getSurveyById(id);
        model.addAttribute("survey", survey);
        return "personal/survey_update";
    }


    @PostMapping("/updateSurvey")
    public String updateSurvey(@RequestParam("id") Long id,
                               @RequestParam("userName") String userName,
                               @RequestParam("email") String email,
                               @RequestParam("feedback") String feedback,
                               @RequestParam("interests") String interests,
                               @RequestParam("filePath") MultipartFile filePath) {
        surveyService.updateSurvey(id, userName, email, feedback, interests, filePath);
        return "redirect:/survey/" + id;
    }


    @GetMapping("/survey/new")
    public String showSurveyForm(Model model) {
        return "personal/survey_form";
    }


    @PostMapping("/submitSurvey")
    public String submitSurvey(@RequestParam("userName") String userName,
                              @RequestParam("email") String email,
                              @RequestParam("feedback") String feedback,
                              @RequestParam("interests") String interests,
                              @RequestParam("filePath") MultipartFile filePath) {
        surveyService.saveSurvey(userName, email, feedback, interests, filePath);
        return "personal/success";
    }
}
