package com.reactive.user;

import com.reactive.generic.EventChange;
import com.reactive.text.Text;
import com.reactive.text.values.TextTypeEnum;
import com.reactive.user.events.TextSavedAndQuoted;
import com.reactive.user.events.UserCreated;
import com.reactive.user.values.shared.DiscountsEnum;
import com.reactive.user.values.user.Email;
import com.reactive.user.values.user.EntryDate;
import com.reactive.user.values.user.Password;

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
