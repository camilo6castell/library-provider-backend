package com.libraryproviderbackend.user.events;

import com.libraryproviderbackend.generic.DomainEvent;

/**
 * Domain event recording the result of quoting a single text for a user.
 */
public class TextQuoted extends DomainEvent {

    private String title;
    private String textType;
    private float subtotal;
    private String discount;
    private float total;

    /** Required for Jackson deserialization. */
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

    public String getTitle()                      { return title; }
    public void setTitle(String title)            { this.title = title; }

    public String getTextType()                   { return textType; }
    public void setTextType(String textType)      { this.textType = textType; }

    public float getSubtotal()                    { return subtotal; }
    public void setSubtotal(float subtotal)       { this.subtotal = subtotal; }

    public String getDiscount()                   { return discount; }
    public void setDiscount(String discount)      { this.discount = discount; }

    public float getTotal()                       { return total; }
    public void setTotal(float total)             { this.total = total; }
}
