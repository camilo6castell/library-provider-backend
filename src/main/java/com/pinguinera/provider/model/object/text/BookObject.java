package com.pinguinera.provider.model.object.text;


import com.pinguinera.provider.model.enums.TextType;

public class BookObject extends TextObject {
    protected final float INCREMENT_BY_DEMAND = 1.33f;

    public BookObject(String title, TextType type, float basePrice, boolean isRetail) {
        super(title, type, basePrice, isRetail);
        float priceAux = basePrice*INCREMENT_BY_DEMAND;
        totalPrice = isRetail ? 1.02f*priceAux : 0.9985f*priceAux;
        price = isRetail ? totalPrice : priceAux;
    }

    public BookObject() {
    }
}
