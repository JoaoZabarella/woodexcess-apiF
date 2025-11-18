package com.projectweb.marktplace.controller;

import com.projectweb.marktplace.model.Purchase;
import com.projectweb.marktplace.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {
    @Autowired
    private PurchaseService service;

    @GetMapping
    public List<Purchase> getAll() {
        return service.listAll();
    }

    @GetMapping("/{id}") public ResponseEntity<Purchase> getById(@PathVariable UUID id) {
        return service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Purchase> create(@RequestBody Purchase purchase) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(purchase));
    }

    @PutMapping("/{id}") public ResponseEntity<Purchase> update(@PathVariable UUID id, @RequestBody Purchase data) {
        return ResponseEntity.ok(service.update(id, data));
    }

    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id); return ResponseEntity.noContent().build();
    }
}

