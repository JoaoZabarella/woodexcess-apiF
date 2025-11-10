package com.projectweb.marktplace.repository;

import com.projectweb.marktplace.model.AdMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdMaterialRepository extends JpaRepository<AdMaterial, UUID> {
}
