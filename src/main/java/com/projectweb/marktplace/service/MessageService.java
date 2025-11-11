package com.projectweb.marktplace.service;

import com.projectweb.marktplace.model.Material;
import com.projectweb.marktplace.model.Message;
import com.projectweb.marktplace.repository.MaterialRepository;
import com.projectweb.marktplace.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MessageService {

    private final MessageRepository repository;

    @Autowired
    public MessageService(MessageRepository repository) {
        this.repository = repository;
    }

    public List<Message> listAll() {
        return repository.findAll();
    }

    public Optional<Message> findById(UUID id) {
        return repository.findById(id);
    }

    public Message create(Message message) {
        return repository.save(message);
    }

    public Message update(UUID id, Message data) {
        var m  = repository.findById(id).orElseThrow();
        m.setAd(data.getAd());
        m.setContent(data.getContent());
        m.setSender(data.getSender());
        m.setReceiver(data.getReceiver());
        return repository.save(m);
    }
    public void delete(UUID id) { repository.deleteById(id); }
}
