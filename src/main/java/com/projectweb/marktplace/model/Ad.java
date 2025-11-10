package com.projectweb.marktplace.model;

import jakarta.persistence.*;


import java.util.UUID;
import java.util.List;

@Entity
public class Ad {
    @Id
    @GeneratedValue
    private UUID id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "ad")
    private List<Image> images;

}
