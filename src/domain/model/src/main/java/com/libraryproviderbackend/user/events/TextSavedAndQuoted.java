package com.libraryproviderbackend.user.events;

import com.libraryproviderbackend.generic.DomainEvent;
import com.libraryproviderbackend.user.values.identities.UserId;

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
        super(UserEventsEnum.SAVE_AND_QUOTE_TEXT.toString());
        this.title = title;
        this.type = type;
        this.initialPrice = initialPrice;
        this.entryDate = entryDate;
    }
}
