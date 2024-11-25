package com.avirantEnterprises.information_collector.repository.project;

import com.avirantEnterprises.information_collector.model.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
