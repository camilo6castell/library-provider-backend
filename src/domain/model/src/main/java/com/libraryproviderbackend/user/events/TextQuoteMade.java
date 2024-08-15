package com.libraryproviderbackend.user.events;

import com.libraryproviderbackend.generic.DomainEvent;

public class TextQuoteMade extends DomainEvent {
    public String title;
    public float subtotal;
    public String discount;
    public float total;

    public TextQuoteMade() {
    }

    public TextQuoteMade(String title, float subtotal, String discount, float total) {
        super("result");
        this.title = title;
        this.subtotal = subtotal;
        this.discount = discount;
        this.total = total;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}

