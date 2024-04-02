package com.pinguinera.provider.models.entities.text;


public class Book extends Text {
    protected final float INCREMENT_BY_DEMAND = 1.3F;

    public Book() {
    }

    public Book(String title, float basePrice, boolean isRetail) {
        super(title, basePrice, isRetail);
    }

}
