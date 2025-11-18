package com.projectweb.marktplace.service;

import com.projectweb.marktplace.model.Material;
import com.projectweb.marktplace.repository.MaterialRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MaterialService {
    private final MaterialRepository repository;

    @Autowired
    public MaterialService(MaterialRepository repository) {
        this.repository = repository;
    }

    public List<Material> listAll() {
        return repository.findAll();
    }

    public Optional<Material> findById(UUID id) {
        return repository.findById(id);
    }

    public Material create(Material material) {
        return repository.save(material);
    }

    public Material update(UUID id, Material materialData) {
        Material material = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Material não encontrado"));
        material.setType(materialData.getType());
        return repository.save(material);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}