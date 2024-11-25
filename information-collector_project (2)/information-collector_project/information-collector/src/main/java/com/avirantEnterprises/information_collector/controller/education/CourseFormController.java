package com.avirantEnterprises.information_collector.controller.education;

import com.avirantEnterprises.information_collector.model.education.Course;
import com.avirantEnterprises.information_collector.service.education.CourseService;
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
public class CourseFormController {

    @Autowired
    private CourseService courseService;


    @GetMapping("/course/{id}")
    public String viewCourse(@PathVariable Long id, Model model) {
        Course course = courseService.getCourseById(id);
        model.addAttribute("course", course);
        return "education/course_view"; // View template for displaying the specific expense
    }


    @GetMapping("/course/delete/{id}")
    public String deleteCourse(@PathVariable Long id) {
        courseService.deleteCourseById(id);
        return "redirect:/course_list"; // Redirect to a list of expenses after deletion
    }


    @GetMapping("/course_list")
    public String listCourses(Model model) {
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        return "education/course_list"; // Template that lists all expenses
    }


    @GetMapping("/course/update/{id}")
    public String showUpdateCourseForm(@PathVariable Long id, Model model) {
        Course course = courseService.getCourseById(id);
        model.addAttribute("course", course);
        return "education/course_update"; // Template to show update form
    }


    @PostMapping("/updateCourse")
    public String updateCourse(@RequestParam("id") Long id,
                               @RequestParam("studentName") String studentName,
                               @RequestParam("studentId") String studentId,
                               @RequestParam("courseName") String courseName,
                               @RequestParam("courseDescription") String courseDescription,
                               @RequestParam("idPath") MultipartFile idPath) {
        courseService.updateCourse(id, studentName, studentId, courseName, courseDescription, idPath);
        return "redirect:/course/" + id; // Redirect to the updated expense page
    }


    @GetMapping("/course/new")
    public String showCourseForm(Model model) {
        return "education/course_form"; // Template to show the form for creating a new expense
    }


    @PostMapping("/submitCourse")
    public String submitCourse( @RequestParam("studentName") String studentName,
                                @RequestParam("studentId") String studentId,
                                @RequestParam("courseName") String courseName,
                                @RequestParam("courseDescription") String courseDescription,
                                @RequestParam("idPath") MultipartFile idPath) {
        courseService.saveCourse(studentName, studentId, courseName, courseDescription, idPath);
        return "education/success"; // Redirect to the list of expenses after submission
    }
}
