package com.pinguinera.provider.models.objects.quote;

import com.pinguinera.provider.models.enums.TextType;

import java.util.ArrayList;
import java.util.List;

public class TextBatchObject {
    private String title;
    private TextType type;
    private float price;
    private List<DiscountObject> discounts;
    private float totalPrice;

    public TextBatchObject(
            String title,
            TextType type,
            float price,
            List<DiscountObject> discounts,
            float totalPrice
    ) {
        List<DiscountObject> discountAux = new ArrayList<>();
        discountAux.add(
                new DiscountObject(
                        "No aplica",
                        0
                )
        );
        this.title = title;
        this.type = type;
        this.price = price;
        this.discounts = discounts.isEmpty() ? discountAux : discounts;
        this.totalPrice = totalPrice;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public List<DiscountObject> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<DiscountObject> discounts) {
        this.discounts = discounts;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
