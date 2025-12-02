package com.projectweb.marktplace.service;

import com.projectweb.marktplace.dto.image.CreateImageRequest;
import com.projectweb.marktplace.dto.image.ImageResponse;
import com.projectweb.marktplace.dto.image.UpdateImageRequest;
import com.projectweb.marktplace.model.Ad;
import com.projectweb.marktplace.model.Image;
import com.projectweb.marktplace.repository.AdRepository;
import com.projectweb.marktplace.repository.ImageRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ImageService {
    private final ImageRepository repository;
    private final AdRepository adRepository;

    @Autowired
    public ImageService(ImageRepository repository, AdRepository adRepository) {
        this.repository = repository;
        this.adRepository = adRepository;
    }

    public List<ImageResponse> listAll() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Optional<ImageResponse> findById(UUID id) {
        return repository.findById(id).map(this::toResponse);
    }

    public ImageResponse create(CreateImageRequest request) {
        Ad ad = adRepository.findById(request.adId())
                .orElseThrow(() -> new EntityNotFoundException("Anúncio não encontrado"));

        Image image = new Image();
        image.setUrl(request.url());
        image.setAd(ad);

        return toResponse(repository.save(image));
    }

    public ImageResponse update(UUID id, UpdateImageRequest request) {
        Image image = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Imagem não encontrada"));
        image.setUrl(request.url());
        return toResponse(repository.save(image));
    }

    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Imagem não encontrada");
        }
        repository.deleteById(id);
    }

    private ImageResponse toResponse(Image image) {
        return new ImageResponse(
                image.getId(),
                image.getUrl(),
                image.getAd() != null ? image.getAd().getId() : null);
    }
}
