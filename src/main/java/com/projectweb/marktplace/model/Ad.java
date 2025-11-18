package com.projectweb.marktplace.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Objects;
import java.util.UUID;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ad ad = (Ad) o;
        return Objects.equals(id, ad.id) && Objects.equals(title, ad.title) && Objects.equals(user, ad.user) && Objects.equals(images, ad.images);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, user, images);
    }
}
