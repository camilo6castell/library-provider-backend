package com.libraryproviderbackend.text;

import com.libraryproviderbackend.generic.EventChange;
import com.libraryproviderbackend.text.events.TextCreated;
import com.libraryproviderbackend.text.values.InitialPrice;
import com.libraryproviderbackend.text.values.TextTypeEnum;
import com.libraryproviderbackend.text.values.Title;
import com.libraryproviderbackend.text.values.Type;

public class TextBehavior extends EventChange {

    TextTypeEnum[] textTypeEnumValues = TextTypeEnum.values();

    public TextBehavior(Text text){
        addSubscriber(TextCreated.class, event -> {
            text.title = Title.of(event.getTitle());
            text.type = Type.of(event.getTextType());
            text.initialPrice = InitialPrice.of(event.getInitialPrice());
        });
    }
}
