package com.pinguinera.provider.models.persistence;

import jakarta.persistence.*;

@Entity
@Table(name = "Novel")
public class NovelEntity extends TextEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public NovelEntity(String title, float basePrice) {
        super(title, basePrice);
    }
}
