package com.projectweb.marktplace.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.List;
import java.util.UUID;

@Entity
public class Material {
    @Id
    @GeneratedValue
    private UUID id;

    private String type;
}
