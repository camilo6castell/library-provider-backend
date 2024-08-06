package com.reactive.text;

import com.reactive.generic.EventChange;
import com.reactive.text.events.TextCreated;
import com.reactive.text.values.InitialPrice;
import com.reactive.text.values.TextTypeEnum;
import com.reactive.text.values.Title;
import com.reactive.text.values.Type;

public class TextBehavior extends EventChange {

    TextTypeEnum[] textTypeEnumValues = TextTypeEnum.values();

    public TextBehavior(Text text){
        addSubscriber((TextCreated event) -> {
            text.title = Title.of(event.title);
            text.type = Type.of(textTypeEnumValues[event.textType]);
            text.initialPrice = InitialPrice.of(event.initialPrice);
        });
    }
}
