package com.pinguinera.provider.models.entities.text;

public class Novel extends Text{
    protected final float INCREMENT_BY_DEMAND = 2f;

    public Novel(String title, float basePrice) {
        super(title, basePrice);
        price = basePrice*INCREMENT_BY_DEMAND;
        totalPrice = isRetail ? 1.02f*price : 0.9985f*price;
    }
}
