package com.avirantEnterprises.information_collector.controller.education;

import com.avirantEnterprises.information_collector.service.education.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Controller
public class EventReportController {

    @Autowired
    private EventService eventReportService;

    @GetMapping("/event_form")
    public String showEventReportForm() {
        return "education/event_form";
    }

    @PostMapping("/submitEventForm")
    public String submitEvent(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("eventName") String eventName,
            @RequestParam("eventDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate eventDate,
            @RequestParam("notes") String notes,
            @RequestParam("eventPath") MultipartFile eventPath) {

        eventReportService.saveEvent(name, email,phone, eventName,eventDate,notes, eventPath);
        return "education/success";
    }
}
