package com.reactive.text;

import com.reactive.generic.AggregateRoot;
import com.reactive.text.events.TextCreated;
import com.reactive.text.values.*;

public class Text extends AggregateRoot<TextId> {

    public static TextTypeEnum[] textTypeEnumValues = TextTypeEnum.values();

    public Title title;
    public Type type;
    public InitialPrice initialPrice;

    public Text() {
        super(new TextId());
    }

    public Text(
            TextId textId,
            Title title,
            Type type,
            InitialPrice initialPrice
    ) {
        super(textId);
        subscribe(new TextBehavior(this));
        appendEvent(
                new TextCreated(
                        textId.value(),
                        title.value(),
                        type.value().ordinal(),
                        initialPrice.value()
                )
        ).apply();
    }


    public static Text from(
            TextCreated event
    ) {
        Text text = new Text();
        text.title = Title.of(event.title);
        text.type = Type.of(textTypeEnumValues[event.textType]);
        text.initialPrice = InitialPrice.of(event.initialPrice);
        return text;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public InitialPrice getInitialPrice() {
        return initialPrice;
    }

    public float getInitialFloatPrice() {
        return initialPrice.value();
    }
    public void setInitialPrice(InitialPrice initialPrice) {
        this.initialPrice = initialPrice;
    }
}
