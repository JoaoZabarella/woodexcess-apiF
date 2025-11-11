package com.projectweb.marktplace.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue
    private UUID id;

    private String url;

    @ManyToOne
    @JoinColumn(name = "ad_id")
    private Ad ad;
}

