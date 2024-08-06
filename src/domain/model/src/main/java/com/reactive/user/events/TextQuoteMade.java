package com.reactive.user.events;

import com.reactive.generic.DomainEvent;
import com.reactive.text.values.TextTypeEnum;
import com.reactive.text.values.Title;
import com.reactive.user.values.shared.Discount;
import com.reactive.user.values.shared.Subtotal;
import com.reactive.user.values.shared.Total;

public class TextQuoteMade extends DomainEvent {
    public String title;
    public float subtotal;
    public String discount;
    public float total;

    public TextQuoteMade() {
    }

    public TextQuoteMade(String title, float subtotal, String discount, float total) {
        super("result", "result");
        this.title = title;
        this.subtotal = subtotal;
        this.discount = discount;
        this.total = total;
    }
}

