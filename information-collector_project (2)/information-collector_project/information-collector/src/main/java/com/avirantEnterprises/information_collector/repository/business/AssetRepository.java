package com.avirantEnterprises.information_collector.repository.business;

import com.avirantEnterprises.information_collector.model.business.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<Asset, Long> {
}
