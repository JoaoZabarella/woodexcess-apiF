package com.projectweb.marktplace.controller;

import com.projectweb.marktplace.model.Material;
import com.projectweb.marktplace.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/materials")
public class MaterialController {

    private final MaterialService service;

    @Autowired
    public MaterialController(MaterialService service) {
        this.service = service;
    }

    @GetMapping
    public List<Material> getAll() {
        return service.listAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Material> getById(@PathVariable UUID id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Material> create(@RequestBody Material material) {
        Material created = service.create(material);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Material> update(@PathVariable UUID id, @RequestBody Material materialData) {
        Material updated = service.update(id, materialData);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

