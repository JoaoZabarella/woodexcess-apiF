package com.projectweb.marktplace.controller;

import com.projectweb.marktplace.model.Rating;
import com.projectweb.marktplace.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {
    @Autowired
    private RatingService service;

    @GetMapping
    public List<Rating> getAll() {
        return service.listAll();
    }
    @GetMapping("/{id}") public ResponseEntity<Rating> getById(@PathVariable UUID id) {
        return service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Rating> create(@RequestBody Rating rating) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(rating));
    }
    @PutMapping("/{id}") public ResponseEntity<Rating> update(@PathVariable UUID id, @RequestBody Rating data) {
        return ResponseEntity.ok(service.update(id, data));
    }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id); return ResponseEntity.noContent().build();
    }
}

