package com.pinguinera.provider.models.persistence;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract  class TextEntity {
    private String title;
    private float basePrice;

    public TextEntity(String title, float basePrice) {
        this.title = title;
        this.basePrice = basePrice;
    }
}
