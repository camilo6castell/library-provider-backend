package com.pinguinera.provider.model.dto.quote.response.shared;

import java.util.List;

public class SummaryResponse {
    private float subtotal;
    private List<DiscountResponse> discounts;
    private float total;

    public SummaryResponse(float subtotal, List<DiscountResponse> discounts, float total) {
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

    public List<DiscountResponse> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<DiscountResponse> discounts) {
        this.discounts = discounts;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
