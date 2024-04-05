package com.pinguinera.provider.models.persistence;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract  class TextEntity {
    private String title;
    private float basePrice;

    public TextEntity() {
    }

    public TextEntity(String title, float basePrice) {
        this.title = title;
        this.basePrice = basePrice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(float basePrice) {
        this.basePrice = basePrice;
    }

}
