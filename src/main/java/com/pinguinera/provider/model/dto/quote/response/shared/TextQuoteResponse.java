package com.pinguinera.provider.model.dto.quote.response.shared;

import com.pinguinera.provider.model.enums.TextType;

import java.util.ArrayList;
import java.util.List;

public class TextQuoteResponse {
    public String title;
    public TextType type;
    public float price;
    public List<DiscountResponse> discounts;
    public float totalPrice;

    public TextQuoteResponse(
            String title,
            TextType type,
            float price,
            List<DiscountResponse> discounts,
            float totalPrice
    ) {
        List<DiscountResponse> discountAux = new ArrayList<>();
        discountAux.add(new DiscountResponse("No aplica", 0));
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

    public List<DiscountResponse> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<DiscountResponse> discounts) {
        this.discounts = discounts;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
