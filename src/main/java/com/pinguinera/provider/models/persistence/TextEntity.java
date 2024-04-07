package com.pinguinera.provider.models.persistence;

import com.pinguinera.provider.models.enums.TextType;
import jakarta.persistence.*;

// import jakarta.persistence.MappedSuperclass;

// @MappedSuperclass
@Entity
@Table(name = "Texts")
public class TextEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private TextType type;
    private float basePrice;

    public TextEntity() {
    }

    public TextEntity(String title, TextType type, float basePrice) {
        this.title = title;
        this.type = type;
        this.basePrice = basePrice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TextType getType() {
        return type;
    }

    public void setType(TextType type) {
        this.type = type;
    }

    public float getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(float basePrice) {
        this.basePrice = basePrice;
    }
}
