package com.reactive.text.events;

import com.reactive.generic.DomainEvent;

public class TextCreated extends DomainEvent {
    public String textId;
    public String title;
    public int textType;
    public Float initialPrice;

    public TextCreated() {
    }

    public TextCreated(
            String textId,
            String title,
            int textType,
            Float initialPrice
    ) {
        super(textId, TextEventsEnum.TEXT_CREATED.toString());
        this.textId = textId;
        this.title = title;
        this.textType = textType;
        this.initialPrice = initialPrice;
    }

    public String getTextId() {
        return textId;
    }

    public void setTextId(String textId) {
        this.textId = textId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTextType() {
        return textType;
    }

    public void setTextType(int textType) {
        this.textType = textType;
    }

    public Float getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(Float initialPrice) {
        this.initialPrice = initialPrice;
    }
}
