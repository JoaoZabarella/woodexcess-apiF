package com.projectweb.marktplace.service;

import com.projectweb.marktplace.model.User;
import com.projectweb.marktplace.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
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

