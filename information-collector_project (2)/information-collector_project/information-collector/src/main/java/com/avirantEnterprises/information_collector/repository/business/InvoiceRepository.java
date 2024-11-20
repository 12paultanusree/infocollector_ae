package com.avirantEnterprises.information_collector.repository.business;

import com.avirantEnterprises.information_collector.model.business.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
}
