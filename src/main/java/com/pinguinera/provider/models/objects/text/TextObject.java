
package com.pinguinera.provider.models.objects.text;

public class TextObject {
    protected String title;
    protected float basePrice;
    protected float price;
    protected boolean isRetail = false;
    protected float totalPrice;

    public TextObject() {
    }

    public TextObject(String title, float basePrice) {
        this.title = title;
        this.basePrice = basePrice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(float basePrice) {
        this.basePrice = basePrice;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
