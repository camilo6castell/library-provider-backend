
package com.pinguinera.provider.models.entities.text;

public abstract class Text {
    protected String title;
    protected float basePrice;
    protected boolean isRetail;
    protected float totalPrice;

    public Text() {
    }

    public Text(String title, float basePrice, boolean isRetail) {
        this.title = title;
        this.basePrice = basePrice;
        this.isRetail = isRetail;

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

    public void setRetail(boolean retail) {
        isRetail = retail;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
