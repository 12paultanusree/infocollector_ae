package com.avirantEnterprises.information_collector.repository.education;

import com.avirantEnterprises.information_collector.model.education.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
}
