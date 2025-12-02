package com.projectweb.marktplace.service;

import com.projectweb.marktplace.dto.ad.AdResponse;
import com.projectweb.marktplace.dto.purchase.CreatePurchaseRequest;
import com.projectweb.marktplace.dto.purchase.PurchaseResponse;
import com.projectweb.marktplace.dto.user.UserResponse;
import com.projectweb.marktplace.event.EventPublisher;
import com.projectweb.marktplace.event.PurchaseCreatedEvent;
import com.projectweb.marktplace.model.Ad;
import com.projectweb.marktplace.model.Purchase;
import com.projectweb.marktplace.model.User;
import com.projectweb.marktplace.repository.AdRepository;
import com.projectweb.marktplace.repository.PurchaseRepository;
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
public class PurchaseService {
    private final PurchaseRepository repository;
    private final UserRepository userRepository;
    private final AdRepository adRepository;
    private final EventPublisher eventPublisher;

    @Autowired
    public PurchaseService(PurchaseRepository repository, UserRepository userRepository, AdRepository adRepository,
            EventPublisher eventPublisher) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.adRepository = adRepository;
        this.eventPublisher = eventPublisher;
    }

    public List<PurchaseResponse> listAll() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Optional<PurchaseResponse> findById(UUID id) {
        return repository.findById(id).map(this::toResponse);
    }

    public PurchaseResponse create(CreatePurchaseRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        User buyer = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        Ad ad = adRepository.findById(request.adId())
                .orElseThrow(() -> new EntityNotFoundException("Anúncio não encontrado"));

        Purchase purchase = new Purchase();
        purchase.setBuyer(buyer);
        purchase.setAd(ad);

        Purchase savedPurchase = repository.save(purchase);

        // Publicar evento de compra criada
        eventPublisher.publishPurchaseCreated(new PurchaseCreatedEvent(
                savedPurchase.getId(),
                savedPurchase.getBuyer().getId(),
                savedPurchase.getBuyer().getName(),
                savedPurchase.getAd().getId(),
                savedPurchase.getAd().getTitle(),
                savedPurchase.getAd().getUser().getId(),
                savedPurchase.getAd().getUser().getName(),
                LocalDateTime.now()));

        return toResponse(savedPurchase);
    }

    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Compra não encontrada");
        }
        repository.deleteById(id);
    }

    private PurchaseResponse toResponse(Purchase purchase) {
        UserResponse buyerResponse = purchase.getBuyer() != null ? new UserResponse(purchase.getBuyer()) : null;
        AdResponse adResponse = purchase.getAd() != null ? new AdResponse(
                purchase.getAd().getId(),
                purchase.getAd().getTitle(),
                purchase.getAd().getUser() != null ? new UserResponse(purchase.getAd().getUser()) : null,
                List.of()) : null;

        return new PurchaseResponse(
                purchase.getId(),
                buyerResponse,
                adResponse);
    }
}
