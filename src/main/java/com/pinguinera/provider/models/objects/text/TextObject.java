
package com.pinguinera.provider.models.objects.text;

import com.pinguinera.provider.models.enums.TextType;

public class TextObject {
    protected String title;
    protected TextType type;
    protected float basePrice;
    protected float price;
    protected boolean isRetail = false;
    protected float totalPrice;

    public TextObject() {
    }

    public TextObject(String title, TextType type, float basePrice, boolean isRetail) {
        this.title = title;
        this.type = type;
        this.basePrice = basePrice;
        this.isRetail = isRetail;
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

    public float getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(float basePrice) {
        this.basePrice = basePrice;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isRetail() {
        return isRetail;
    }

    public void setIsRetail(boolean retail) {
        isRetail = retail;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
