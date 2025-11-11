package com.projectweb.marktplace.service;

import com.projectweb.marktplace.model.Material;
import com.projectweb.marktplace.repository.MaterialRepository;
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

    public Material update(UUID id, Material data) {
        Material m  = repository.findById(id).orElseThrow();
        m.setType(data.getType());
        return repository.save(m);
    }
    public void delete(UUID id) { repository.deleteById(id); }
}


