package com.pinguinera.provider.models.objects.quote;

import java.util.List;

public class SummaryObject {
    private float subtotal;
    private List<DiscountObject> discounts;
    private float total;

    public SummaryObject(float subtotal, List<DiscountObject> discounts, float total) {
        this.subtotal = subtotal;
        this.discounts = discounts;
        this.total = total;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public List<DiscountObject> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<DiscountObject> discounts) {
        this.discounts = discounts;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
