package com.avirantEnterprises.information_collector.repository.education;

import com.avirantEnterprises.information_collector.model.education.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
