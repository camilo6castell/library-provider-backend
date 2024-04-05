package com.pinguinera.provider.models.objects.text;


public class BookObject extends TextObject {
    protected final float INCREMENT_BY_DEMAND = 1.33f;

    public BookObject(String title, float basePrice) {
        super(title, basePrice);
        price = basePrice*INCREMENT_BY_DEMAND;
        totalPrice = isRetail ? 1.02f*price : 0.9985f*price;
    }

}
