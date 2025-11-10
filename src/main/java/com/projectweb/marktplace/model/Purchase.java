package com.projectweb.marktplace.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.util.UUID;

@Entity
public class Purchase {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private User buyer;

    @ManyToOne
    private Ad ad;
}

