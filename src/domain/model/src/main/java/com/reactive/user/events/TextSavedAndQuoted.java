package com.reactive.user.events;

import com.reactive.generic.DomainEvent;
import com.reactive.user.values.identities.UserId;

import java.time.LocalDate;

public class TextSavedAndQuoted extends DomainEvent {
    public String title;
    public int type;
    public Float initialPrice;
    public LocalDate entryDate;

    public TextSavedAndQuoted() {
    }

    public TextSavedAndQuoted(
            UserId userId,
            String title,
            int type,
            Float initialPrice,
            LocalDate entryDate
    ) {
        super(userId.value(), UserEventsEnum.SAVE_AND_QUOTE_TEXT.toString());
        this.title = title;
        this.type = type;
        this.initialPrice = initialPrice;
        this.entryDate = entryDate;
    }
}
