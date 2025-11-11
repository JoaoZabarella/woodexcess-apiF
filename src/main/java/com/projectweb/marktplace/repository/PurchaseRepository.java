package com.projectweb.marktplace.repository;

import com.projectweb.marktplace.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PurchaseRepository extends JpaRepository<Purchase, UUID> {
}
