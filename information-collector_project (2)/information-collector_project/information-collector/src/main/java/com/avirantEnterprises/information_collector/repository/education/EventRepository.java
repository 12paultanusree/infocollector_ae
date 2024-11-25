package com.avirantEnterprises.information_collector.repository.education;

import com.avirantEnterprises.information_collector.model.education.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
