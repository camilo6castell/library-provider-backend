package com.libraryproviderbackend.user.events;

import com.libraryproviderbackend.generic.DomainEvent;
import com.libraryproviderbackend.text.values.TextTypeEnum;
import com.libraryproviderbackend.text.values.Type;

public class TextQuoted extends DomainEvent {

    public String title;
    public String textType;
    public float subtotal;
    public String discount;
    public float total;

    public TextQuoted() {
    }

    public TextQuoted(String title, String textType, float subtotal, String discount, float total) {
        super(UserEventsEnum.TEXT_QUOTED.toString());
        this.title = title;
        this.textType = textType;
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

    public String getTextType() {
        return textType;
    }

    public void setTextType(String textType) {
        this.textType = textType;
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

