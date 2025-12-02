package com.projectweb.marktplace.service;

import com.projectweb.marktplace.dto.message.CreateMessageRequest;
import com.projectweb.marktplace.dto.message.MessageResponse;
import com.projectweb.marktplace.dto.message.UpdateMessageRequest;
import com.projectweb.marktplace.dto.user.UserResponse;
import com.projectweb.marktplace.event.EventPublisher;
import com.projectweb.marktplace.event.MessageReceivedEvent;
import com.projectweb.marktplace.model.Ad;
import com.projectweb.marktplace.model.Message;
import com.projectweb.marktplace.model.User;
import com.projectweb.marktplace.repository.AdRepository;
import com.projectweb.marktplace.repository.MessageRepository;
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
public class MessageService {
    private final MessageRepository repository;
    private final UserRepository userRepository;
    private final AdRepository adRepository;
    private final EventPublisher eventPublisher;

    @Autowired
    public MessageService(MessageRepository repository, UserRepository userRepository, AdRepository adRepository,
            EventPublisher eventPublisher) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.adRepository = adRepository;
        this.eventPublisher = eventPublisher;
    }

    public List<MessageResponse> listAll() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Optional<MessageResponse> findById(UUID id) {
        return repository.findById(id).map(this::toResponse);
    }

    public MessageResponse create(CreateMessageRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        User sender = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        User receiver = userRepository.findById(request.receiverId())
                .orElseThrow(() -> new EntityNotFoundException("Destinatário não encontrado"));
        Ad ad = adRepository.findById(request.adId())
                .orElseThrow(() -> new EntityNotFoundException("Anúncio não encontrado"));

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setAd(ad);
        message.setContent(request.content());

        Message savedMessage = repository.save(message);

        // Publicar evento de mensagem recebida
        eventPublisher.publishMessageReceived(new MessageReceivedEvent(
                savedMessage.getId(),
                savedMessage.getSender().getId(),
                savedMessage.getSender().getName(),
                savedMessage.getReceiver().getId(),
                savedMessage.getReceiver().getName(),
                savedMessage.getAd().getId(),
                savedMessage.getAd().getTitle(),
                savedMessage.getContent(),
                LocalDateTime.now()));

        return toResponse(savedMessage);
    }

    public MessageResponse update(UUID id, UpdateMessageRequest request) {
        Message message = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mensagem não encontrada"));
        message.setContent(request.content());
        return toResponse(repository.save(message));
    }

    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Mensagem não encontrada");
        }
        repository.deleteById(id);
    }

    private MessageResponse toResponse(Message message) {
        return new MessageResponse(
                message.getId(),
                message.getSender() != null ? new UserResponse(message.getSender()) : null,
                message.getReceiver() != null ? new UserResponse(message.getReceiver()) : null,
                message.getAd() != null ? message.getAd().getId() : null,
                message.getContent());
    }
}
