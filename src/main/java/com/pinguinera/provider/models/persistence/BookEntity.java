package com.pinguinera.provider.models.persistence;

import jakarta.persistence.*;

@Entity
@Table(name = "Book")
public class BookEntity extends TextEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public BookEntity(String title, float basePrice) {
        super(title, basePrice);
    }
}
