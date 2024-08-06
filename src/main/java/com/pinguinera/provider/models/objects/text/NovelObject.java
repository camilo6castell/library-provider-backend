package com.pinguinera.provider.models.objects.text;

import com.pinguinera.provider.models.enums.TextType;

public class NovelObject extends TextObject {
    protected final float INCREMENT_BY_DEMAND = 2f;

    public NovelObject(String title, TextType type, float basePrice, boolean isRetail) {
        super(title, type, basePrice, isRetail);
        float priceAux = basePrice*INCREMENT_BY_DEMAND;
        totalPrice = isRetail ? 1.02f*priceAux : 0.9985f*priceAux;
        price = isRetail ? totalPrice : priceAux;
    }
}
