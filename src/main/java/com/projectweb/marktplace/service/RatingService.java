package com.projectweb.marktplace.service;

import com.projectweb.marktplace.dto.rating.CreateRatingRequest;
import com.projectweb.marktplace.dto.rating.RatingResponse;
import com.projectweb.marktplace.dto.rating.UpdateRatingRequest;
import com.projectweb.marktplace.dto.user.UserResponse;
import com.projectweb.marktplace.event.EventPublisher;
import com.projectweb.marktplace.event.RatingCreatedEvent;
import com.projectweb.marktplace.model.Purchase;
import com.projectweb.marktplace.model.Rating;
import com.projectweb.marktplace.model.User;
import com.projectweb.marktplace.repository.PurchaseRepository;
import com.projectweb.marktplace.repository.RatingRepository;
import com.projectweb.marktplace.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RatingService {
    private final RatingRepository repository;
    private final UserRepository userRepository;
    private final PurchaseRepository purchaseRepository;
    private final EventPublisher eventPublisher;

    @Autowired
    public RatingService(RatingRepository repository, UserRepository userRepository,
            PurchaseRepository purchaseRepository, EventPublisher eventPublisher) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.purchaseRepository = purchaseRepository;
        this.eventPublisher = eventPublisher;
    }

    public List<RatingResponse> listAll() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Optional<RatingResponse> findById(UUID id) {
        return repository.findById(id).map(this::toResponse);
    }

    public RatingResponse create(CreateRatingRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        Purchase purchase = purchaseRepository.findById(request.purchaseId())
                .orElseThrow(() -> new EntityNotFoundException("Compra não encontrada"));

        Rating rating = new Rating();
        rating.setScore(request.score());
        rating.setUser(user);
        rating.setPurchase(purchase);

        Rating savedRating = repository.save(rating);

        // Publicar evento de avaliação criada
        eventPublisher.publishRatingCreated(new RatingCreatedEvent(
                savedRating.getId(),
                savedRating.getUser().getId(),
                savedRating.getUser().getName(),
                savedRating.getPurchase().getId(),
                savedRating.getScore(),
                LocalDateTime.now()));

        return toResponse(savedRating);
    }

    public RatingResponse update(UUID id, UpdateRatingRequest request) {
        Rating rating = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Avaliação não encontrada"));
        rating.setScore(request.score());
        return toResponse(repository.save(rating));
    }

    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Avaliação não encontrada");
        }
        repository.deleteById(id);
    }

    private RatingResponse toResponse(Rating rating) {
        return new RatingResponse(
                rating.getId(),
                rating.getScore(),
                rating.getUser() != null ? new UserResponse(rating.getUser()) : null,
                rating.getPurchase() != null ? rating.getPurchase().getId() : null);
    }
}
