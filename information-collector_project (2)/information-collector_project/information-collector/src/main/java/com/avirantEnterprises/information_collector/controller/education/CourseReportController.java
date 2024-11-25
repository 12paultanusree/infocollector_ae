package com.avirantEnterprises.information_collector.controller.education;

import com.avirantEnterprises.information_collector.service.education.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class CourseReportController {

    @Autowired
    private CourseService courseReportService;

    @GetMapping("/course_form")
    public String showCourseReportForm() {
        return "education/course_form";
    }

    @PostMapping("/submitCourseForm")
    public String submitCourse(
            @RequestParam("studentName") String studentName,
            @RequestParam("studentId") String studentId,
            @RequestParam("courseName") String courseName,
            @RequestParam("courseDescription") String courseDescription,
            @RequestParam("idPath") MultipartFile idPath) {

       courseReportService.saveCourse(studentName, studentId, courseName, courseDescription, idPath);
        return "education/success";
    }
}
