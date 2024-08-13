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
}

