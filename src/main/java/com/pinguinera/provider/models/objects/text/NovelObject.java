package com.pinguinera.provider.models.objects.text;

public class NovelObject extends TextObject {
    protected final float INCREMENT_BY_DEMAND = 2f;

    public NovelObject(String title, float basePrice) {
        super(title, basePrice);
        price = basePrice*INCREMENT_BY_DEMAND;
        totalPrice = isRetail ? 1.02f*price : 0.9985f*price;
    }
}
