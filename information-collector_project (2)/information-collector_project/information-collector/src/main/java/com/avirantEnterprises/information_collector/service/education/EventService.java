package com.avirantEnterprises.information_collector.service.education;

import com.avirantEnterprises.information_collector.model.education.Event;
import com.avirantEnterprises.information_collector.repository.education.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    private final Path rootLocation = Paths.get("upload-dir");


    public void saveEvent(String name, String email, String phone,String eventName, LocalDate eventDate,String notes, MultipartFile eventPath) {
        Event event = new Event();
        event.setName(name);
        event.setEmail(email);
        event.setPhone(phone);
        event.setEventName(eventName);
        event.setEventDate(eventDate);
        event.setNotes(notes);

        String eventFile = saveFile(eventPath);
        event.setEventFile(eventFile);

        eventRepository.save(event);
    }


    public void updateEvent(Long id, String name, String email, String phone,String eventName, LocalDate eventDate,String notes, MultipartFile eventPath) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            event.setName(name);
            event.setEmail(email);
            event.setPhone(phone);
            event.setEventName(eventName);
            event.setEventDate(eventDate);
            event.setNotes(notes);
            if (!eventPath.isEmpty()) {
                String assignmentFile = saveFile(eventPath);
                event.setEventFile(assignmentFile);
            }
            eventRepository.save(event);
        }
    }


    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }


    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }


    public void deleteEventById(Long id) {
        eventRepository.deleteById(id);
    }


    private String sanitizeFileName(String fileName) {
        return fileName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");  // Sanitize the file name
    }


    private String saveFile(MultipartFile file) {
        try {
            Files.createDirectories(rootLocation);  // Ensure the directory exists
            String sanitizedFileName = sanitizeFileName(file.getOriginalFilename());
            Path destinationFile = rootLocation.resolve(Paths.get(sanitizedFileName))
                    .normalize().toAbsolutePath();
            file.transferTo(destinationFile);  // Save the file to the specified location
            return sanitizedFileName;  // Return the sanitized file name
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file.", e);
        }
    }
}
