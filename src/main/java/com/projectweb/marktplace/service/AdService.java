package com.projectweb.marktplace.service;

import com.projectweb.marktplace.dto.ad.AdResponse;
import com.projectweb.marktplace.dto.ad.CreateAdRequest;
import com.projectweb.marktplace.dto.ad.UpdateAdRequest;
import com.projectweb.marktplace.dto.user.UserResponse;
import com.projectweb.marktplace.model.Ad;
import com.projectweb.marktplace.model.Image;
import com.projectweb.marktplace.model.User;
import com.projectweb.marktplace.repository.AdRepository;
import com.projectweb.marktplace.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AdService {

    private final AdRepository repository;
    private final UserRepository userRepository;

    @Autowired
    public AdService(AdRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public List<AdResponse> listAll() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Optional<AdResponse> findById(UUID id) {
        return repository.findById(id)
                .map(this::toResponse);
    }

    public AdResponse create(CreateAdRequest request) {
        // Pega o email do usuário autenticado do contexto de segurança
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        Ad ad = new Ad();
        ad.setTitle(request.title());
        ad.setUser(user);

        Ad saved = repository.save(ad);
        return toResponse(saved);
    }

    public AdResponse update(UUID id, UpdateAdRequest request) {
        Ad ad = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Anúncio não encontrado"));
        ad.setTitle(request.title());
        Ad updated = repository.save(ad);
        return toResponse(updated);
    }

    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Anúncio não encontrado");
        }
        repository.deleteById(id);
    }

    private AdResponse toResponse(Ad ad) {
        UserResponse userResponse = ad.getUser() != null ? new UserResponse(ad.getUser()) : null;

        List<UUID> imageIds = ad.getImages() != null ? ad.getImages().stream()
                .map(Image::getId)
                .collect(Collectors.toList()) : List.of();

        return new AdResponse(
                ad.getId(),
                ad.getTitle(),
                userResponse,
                imageIds);
    }
}
