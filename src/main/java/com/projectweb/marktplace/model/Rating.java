package com.projectweb.marktplace.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.util.UUID;

@Entity
public class Rating {
    @Id
    @GeneratedValue
    private UUID id;

    private int score;

    @ManyToOne
    private User user;

    @ManyToOne
    private Purchase purchase;
}
