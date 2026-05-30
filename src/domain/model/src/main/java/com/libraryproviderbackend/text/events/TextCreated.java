package com.libraryproviderbackend.text.events;

import com.libraryproviderbackend.generic.DomainEvent;
import com.libraryproviderbackend.text.values.TextTypeEnum;

/**
 * Domain event representing the creation of a new text in the catalogue.
 */
public class TextCreated extends DomainEvent {

    private String title;
    private TextTypeEnum textType;
    private Float initialPrice;

    /** Required for Jackson deserialization. */
    public TextCreated() {
    }

    public TextCreated(String title, TextTypeEnum textType, Float initialPrice) {
        super(TextEventsEnum.TEXT_CREATED.toString());
        this.title = title;
        this.textType = textType;
        this.initialPrice = initialPrice;
    }

    public String getTitle()                         { return title; }
    public void setTitle(String title)               { this.title = title; }

    public TextTypeEnum getTextType()                { return textType; }
    public void setTextType(TextTypeEnum textType)   { this.textType = textType; }

    public Float getInitialPrice()                   { return initialPrice; }
    public void setInitialPrice(Float initialPrice)  { this.initialPrice = initialPrice; }
}
