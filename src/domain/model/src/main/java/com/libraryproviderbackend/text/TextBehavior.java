package com.libraryproviderbackend.text;

import com.libraryproviderbackend.generic.EventChange;
import com.libraryproviderbackend.text.events.TextCreated;
import com.libraryproviderbackend.text.values.InitialPrice;
import com.libraryproviderbackend.text.values.Title;
import com.libraryproviderbackend.text.values.Type;

/**
 * Handles domain event application for the {@link Text} aggregate root.
 */
public class TextBehavior extends EventChange {

    public TextBehavior(Text text) {
        addSubscriber(TextCreated.class, event -> {
            text.setTitle(Title.of(event.getTitle()));
            text.setType(Type.of(event.getTextType()));
            text.setInitialPrice(InitialPrice.of(event.getInitialPrice()));
        });
    }
}
