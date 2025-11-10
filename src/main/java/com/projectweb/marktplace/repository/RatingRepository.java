package com.projectweb.marktplace.repository;

import com.projectweb.marktplace.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RatingRepository extends JpaRepository<Rating, UUID> {
}
