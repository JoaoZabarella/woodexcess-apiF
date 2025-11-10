package com.projectweb.marktplace.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Image {
    @Id
    @GeneratedValue
    private UUID id;

    private String url;

    @ManyToOne
    @JoinColumn(name = "ad_id")
    private Ad ad;
}

