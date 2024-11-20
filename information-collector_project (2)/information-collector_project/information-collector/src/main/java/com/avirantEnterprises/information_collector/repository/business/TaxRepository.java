package com.avirantEnterprises.information_collector.repository.business;

import com.avirantEnterprises.information_collector.model.business.Tax;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxRepository extends JpaRepository<Tax, Long> {
}
