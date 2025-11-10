package com.projectweb.marktplace.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
public class AdMaterial {
    @EmbeddedId
    private AdMaterialId id;

    @ManyToOne
    @MapsId("adId")
    @JoinColumn(name = "ad_id")
    private Ad ad;

    @ManyToOne
    @MapsId("materialId")
    @JoinColumn(name = "material_id")
    private Material material;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AdMaterial that = (AdMaterial) o;
        return Objects.equals(id, that.id) && Objects.equals(ad, that.ad) && Objects.equals(material, that.material);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ad, material);
    }
}

