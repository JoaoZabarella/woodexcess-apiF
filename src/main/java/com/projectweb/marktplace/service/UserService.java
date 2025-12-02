package com.projectweb.marktplace.service;

import com.projectweb.marktplace.controller.mapper.UserMapper;
import com.projectweb.marktplace.dto.auth.RegisterRequest;
import com.projectweb.marktplace.dto.auth.RegisterResponse;
import com.projectweb.marktplace.event.EventPublisher;
import com.projectweb.marktplace.event.UserRegisteredEvent;
import com.projectweb.marktplace.exception.users.EmailAlredyExistException;
import com.projectweb.marktplace.model.User;
import com.projectweb.marktplace.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;
    private final EventPublisher eventPublisher;

    @Autowired
    public UserService(UserRepository repository, UserMapper mapper, PasswordEncoder encoder,
            EventPublisher eventPublisher) {
        this.repository = repository;
        this.mapper = mapper;
        this.encoder = encoder;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public RegisterResponse registerUser(RegisterRequest dto) {
        if (repository.findByEmail(dto.email()).isPresent()) {
            throw new EmailAlredyExistException("Email already exists");
        }
        User user = mapper.toEntity(dto);
        user.setPassword(encoder.encode(dto.password()));
        User savedUser = repository.save(user);

        // Publicar evento de usuário registrado
        eventPublisher.publishUserRegistered(new UserRegisteredEvent(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                LocalDateTime.now()));

        return mapper.toRegisterResponse(savedUser);
    }

    public List<User> listAll() {
        return repository.findAll();
    }

    public Optional<User> findById(UUID id) {
        return repository.findById(id);
    }

    public User create(User user) {
        return repository.save(user);
    }

    public User update(UUID id, User userData) {
        User user = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        user.setName(userData.getName());
        user.setEmail(userData.getEmail());
        return repository.save(user);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
