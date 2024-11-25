package com.avirantEnterprises.information_collector.controller.education;

import com.avirantEnterprises.information_collector.model.education.Event;
import com.avirantEnterprises.information_collector.service.education.EventService;
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
public class EventFormController {

    @Autowired
    private EventService eventService;


    @GetMapping("/event/{id}")
    public String viewEvent(@PathVariable Long id, Model model) {
        Event event = eventService.getEventById(id);
        model.addAttribute("event", event);
        return "education/event_view";
    }


    @GetMapping("/event/delete/{id}")
    public String deleteEvent(@PathVariable Long id) {
        eventService.deleteEventById(id);
        return "redirect:/event_list";
    }


    @GetMapping("/event_list")
    public String listEvents(Model model) {
        List<Event> events = eventService.getAllEvents();
        model.addAttribute("events", events);
        return "education/event_list";
    }


    @GetMapping("/event/update/{id}")
    public String showUpdateEventForm(@PathVariable Long id, Model model) {
        Event event = eventService.getEventById(id);
        model.addAttribute("event", event);
        return "education/event_update";
    }


    @PostMapping("/updateEvent")
    public String updateEvent(@RequestParam("id") Long id,
                              @RequestParam("name") String name,
                              @RequestParam("email") String email,
                              @RequestParam("phone") String phone,
                              @RequestParam("eventName") String eventName,
                              @RequestParam("eventDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate eventDate,
                              @RequestParam("notes") String notes,
                              @RequestParam("eventPath") MultipartFile eventPath) {
        eventService.updateEvent(id, name, email,phone, eventName,eventDate,notes, eventPath);
        return "redirect:/event/" + id;
    }


    @GetMapping("/event/new")
    public String showEventForm(Model model) {
        return "education/event_form";
    }


    @PostMapping("/submitEvent")
    public String submitEvent(   @RequestParam("name") String name,
                                 @RequestParam("email") String email,
                                 @RequestParam("phone") String phone,
                                 @RequestParam("eventName") String eventName,
                                 @RequestParam("eventDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate eventDate,
                                 @RequestParam("notes") String notes,
                                 @RequestParam("eventPath") MultipartFile eventPath) {
        eventService.saveEvent(name, email,phone, eventName,eventDate,notes, eventPath);
        return "education/success";
    }
}
