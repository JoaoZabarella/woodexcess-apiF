package com.projectweb.marktplace.controller;

import com.projectweb.marktplace.model.Image;
import com.projectweb.marktplace.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/images")
public class ImageController {
    @Autowired
    private ImageService service;

    @GetMapping
    public List<Image> getAll() {
        return service.listAll();
    }

    @GetMapping("/{id}") public ResponseEntity<Image> getById(@PathVariable UUID id) {
        return service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<Image> create(@RequestBody Image image) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(image));
    }
    @PutMapping("/{id}") public ResponseEntity<Image> update(@PathVariable UUID id, @RequestBody Image data) {
        return ResponseEntity.ok(service.update(id, data));
    }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id); return ResponseEntity.noContent().build();
    }
}

