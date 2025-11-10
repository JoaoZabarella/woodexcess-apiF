package com.projectweb.marktplace.repository;

import com.projectweb.marktplace.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MaterialRepository extends JpaRepository<Material, UUID> {
}
