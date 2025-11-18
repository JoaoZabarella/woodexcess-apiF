
package com.projectweb.marktplace.repository;

import com.projectweb.marktplace.model.Ad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdRepository extends JpaRepository<Ad, UUID> {
}

