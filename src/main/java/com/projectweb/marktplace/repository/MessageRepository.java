package com.projectweb.marktplace.repository;

import com.projectweb.marktplace.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
}
