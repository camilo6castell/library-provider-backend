package com.libraryproviderbackend.user;

import com.libraryproviderbackend.generic.EventChange;
import com.libraryproviderbackend.text.values.TextTypeEnum;
import com.libraryproviderbackend.user.events.TextSavedAndQuoted;
import com.libraryproviderbackend.user.events.UserCreated;
import com.libraryproviderbackend.user.values.shared.DiscountsEnum;
import com.libraryproviderbackend.user.values.user.Email;
import com.libraryproviderbackend.user.values.user.EntryDate;
import com.libraryproviderbackend.user.values.user.Password;

public class UserBehavior extends EventChange {

    DiscountsEnum[] discountsEnum = DiscountsEnum.values();
    TextTypeEnum[] textTypeEnum = TextTypeEnum.values();

    public UserBehavior(User user){
        addSubscriber((UserCreated event) -> {
            user.email = Email.of(event.email);
            user.password = Password.of(event.password);
            user.entryDate = EntryDate.of(event.entryDate);
        });
        addSubscriber((TextSavedAndQuoted event) -> {
            user.quoteText(event.title, event.initialPrice, textTypeEnum[event.type], DiscountsEnum.NONE);
        });
    }
}
