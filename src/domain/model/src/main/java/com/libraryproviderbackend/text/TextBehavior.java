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
        addSubscriber((TextCreated event) -> {
            text.title = Title.of(event.title);
            text.type = Type.of(textTypeEnumValues[event.textType]);
            text.initialPrice = InitialPrice.of(event.initialPrice);
        });
    }
}
