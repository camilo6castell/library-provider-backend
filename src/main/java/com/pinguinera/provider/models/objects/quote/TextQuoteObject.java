package com.pinguinera.provider.models.objects.quote;

import com.pinguinera.provider.models.enums.TextType;

import java.util.ArrayList;
import java.util.List;

public class TextQuoteObject {
    public String title;
    public TextType type;
    public float price;
    public List<DiscountObject> discounts;
    public float totalPrice;

    public TextQuoteObject(String title, TextType type, float price, List<DiscountObject> discounts, float totalPrice) {
        List<DiscountObject> discountAux = new ArrayList<>();
        discountAux.add(new DiscountObject("No aplica", 0));
        this.title = title;
        this.type = type;
        this.price = price;
        this.discounts = discounts.isEmpty() ? discountAux : discounts;
        this.totalPrice = totalPrice;
    }
}
