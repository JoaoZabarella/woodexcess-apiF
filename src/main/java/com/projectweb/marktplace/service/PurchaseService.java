package com.projectweb.marktplace.service;

import com.projectweb.marktplace.model.Purchase;
import com.projectweb.marktplace.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PurchaseService {
    private final PurchaseRepository repository;

    @Autowired
    public PurchaseService(PurchaseRepository repository) {
        this.repository = repository;
    }

    public List<Purchase> listAll() {
        return repository.findAll();
    }

    public Optional<Purchase> findById(UUID id) {
        return repository.findById(id);
    }

    public Purchase create(Purchase purchase) {
        return repository.save(purchase);
    }

    public Purchase update(UUID id, Purchase data) {
        Purchase p = repository.findById(id).orElseThrow();
        p.setBuyer(data.getBuyer());
        p.setAd(data.getAd());
        return repository.save(p);
    }
    public void delete(UUID id) { repository.deleteById(id); }
}
