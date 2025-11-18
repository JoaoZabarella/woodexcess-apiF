package com.projectweb.marktplace.service;

import com.projectweb.marktplace.model.Rating;
import com.projectweb.marktplace.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RatingService {
    private final RatingRepository repository;

    @Autowired
    public RatingService(RatingRepository repository) {
        this.repository = repository;
    }

    public List<Rating> listAll() {
        return repository.findAll();
    }

    public Optional<Rating> findById(UUID id) {
        return repository.findById(id);
    }

    public Rating create(Rating rating) {
        return repository.save(rating);
    }

    public Rating update(UUID id, Rating data) {
        Rating r = repository.findById(id).orElseThrow();
        r.setScore(data.getScore());
        r.setUser(data.getUser());
        r.setPurchase(data.getPurchase());
        return repository.save(r);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
