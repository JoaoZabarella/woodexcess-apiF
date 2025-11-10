package com.projectweb.marktplace.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class AdMaterialId implements Serializable {
    private UUID adId;
    private UUID materialId;
}
