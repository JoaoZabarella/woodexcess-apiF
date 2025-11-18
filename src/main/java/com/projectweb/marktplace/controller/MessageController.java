
package com.projectweb.marktplace.controller;

import com.projectweb.marktplace.model.Message;
import com.projectweb.marktplace.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private MessageService service;

    @GetMapping
    public List<Message> getAll() {
        return service.listAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getById(@PathVariable UUID id) {
        return service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Message> create(@RequestBody Message message) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(message));
    }

    @PutMapping("/{id}") public ResponseEntity<Message> update(@PathVariable UUID id, @RequestBody Message data) {
        return ResponseEntity.ok(service.update(id, data));
    }

    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id); return ResponseEntity.noContent().build();
    }
}

