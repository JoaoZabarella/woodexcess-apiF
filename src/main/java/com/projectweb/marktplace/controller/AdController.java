package com.projectweb.marktplace.controller;

import com.projectweb.marktplace.model.Ad;
import com.projectweb.marktplace.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ads")
public class AdController {

    private final AdService service;

    @Autowired
    public AdController(AdService service) {
        this.service = service;
    }

    @GetMapping
    public List<Ad> getAll() {
        return service.listAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Ad> getById(@PathVariable UUID id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Ad> create(@RequestBody Ad ad) {
        Ad created = service.create(ad);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ad> update(@PathVariable UUID id, @RequestBody Ad adData) {
        Ad updated = service.update(id, adData);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
