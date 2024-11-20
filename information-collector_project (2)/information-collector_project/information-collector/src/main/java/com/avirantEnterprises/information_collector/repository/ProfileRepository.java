package com.avirantEnterprises.information_collector.repository;
import com.avirantEnterprises.information_collector.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUsername(String username); // Fetch a profile by username
}