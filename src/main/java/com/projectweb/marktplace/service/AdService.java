package com.projectweb.marktplace.service;

import com.projectweb.marktplace.model.Ad;
import com.projectweb.marktplace.repository.AdRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AdService {

    private final AdRepository repository;

    @Autowired
    public AdService(AdRepository repository) {
        this.repository = repository;
    }

    public List<Ad> listAll() {
        return repository.findAll();
    }

    public Optional<Ad> findById(UUID id) {
        return repository.findById(id);
    }

    public Ad create(Ad ad) {
        return repository.save(ad);
    }

    public Ad update(UUID id, Ad adData) {
        Ad ad = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Anúncio não encontrado"));
        ad.setTitle(adData.getTitle());

        return repository.save(ad);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}


