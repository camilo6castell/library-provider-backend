package com.reactive.user.values.batchquote;


public class TextQuoteResponse {
    public String title;
    public String type;
    public float price;
    public String discounts;
    public float totalPrice;

    public TextQuoteResponse(String title, String type, float price, String discounts, float totalPrice) {
        this.totalPrice = totalPrice;
        this.discounts = discounts;
        this.price = price;
        this.type = type;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDiscounts() {
        return discounts;
    }

    public void setDiscounts(String discounts) {
        this.discounts = discounts;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
