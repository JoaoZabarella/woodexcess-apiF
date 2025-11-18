package com.projectweb.marktplace.service;

import com.projectweb.marktplace.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.projectweb.marktplace.model.Image;

@Service
public class ImageService {
    private final ImageRepository repository;

    @Autowired
    public ImageService(ImageRepository repository) {
        this.repository = repository;
    }

    public List<Image> listAll() {
        return repository.findAll();
    }

    public Optional<Image> findById(UUID id) {
        return repository.findById(id);
    }

    public Image create(Image image) {
        return repository.save(image);
    }

    public Image update(UUID id, Image data) {
        Image i = repository.findById(id).orElseThrow();
        i.setUrl(data.getUrl());
        i.setAd(data.getAd());
        return repository.save(i);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}

