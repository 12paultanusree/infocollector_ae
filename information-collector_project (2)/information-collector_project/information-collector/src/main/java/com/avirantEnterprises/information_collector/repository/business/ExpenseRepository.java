package com.avirantEnterprises.information_collector.repository.business;

import com.avirantEnterprises.information_collector.model.business.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
