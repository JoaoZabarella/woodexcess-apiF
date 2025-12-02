package com.projectweb.marktplace.service;

import com.projectweb.marktplace.dto.material.CreateMaterialRequest;
import com.projectweb.marktplace.dto.material.MaterialResponse;
import com.projectweb.marktplace.dto.material.UpdateMaterialRequest;
import com.projectweb.marktplace.model.Material;
import com.projectweb.marktplace.repository.MaterialRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MaterialService {
    private final MaterialRepository repository;

    @Autowired
    public MaterialService(MaterialRepository repository) {
        this.repository = repository;
    }

    public List<MaterialResponse> listAll() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Optional<MaterialResponse> findById(UUID id) {
        return repository.findById(id)
                .map(this::toResponse);
    }

    public MaterialResponse create(CreateMaterialRequest request) {
        Material material = new Material();
        material.setType(request.type());
        Material saved = repository.save(material);
        return toResponse(saved);
    }

    public MaterialResponse update(UUID id, UpdateMaterialRequest request) {
        Material material = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Material não encontrado"));
        material.setType(request.type());
        Material updated = repository.save(material);
        return toResponse(updated);
    }

    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Material não encontrado");
        }
        repository.deleteById(id);
    }

    private MaterialResponse toResponse(Material material) {
        return new MaterialResponse(
                material.getId(),
                material.getType());
    }
}