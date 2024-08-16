package com.libraryproviderbackend.user;

import com.libraryproviderbackend.generic.EventChange;
import com.libraryproviderbackend.text.values.TextTypeEnum;
import com.libraryproviderbackend.text.values.Title;
import com.libraryproviderbackend.text.values.Type;
import com.libraryproviderbackend.user.entity.TextQuote;
import com.libraryproviderbackend.user.events.TextQuoted;
import com.libraryproviderbackend.user.events.UserCreated;
import com.libraryproviderbackend.user.values.shared.Discount;
import com.libraryproviderbackend.user.values.shared.DiscountsEnum;
import com.libraryproviderbackend.user.values.shared.Subtotal;
import com.libraryproviderbackend.user.values.shared.Total;
import com.libraryproviderbackend.user.values.user.Email;
import com.libraryproviderbackend.user.values.user.EntryDate;
import com.libraryproviderbackend.user.values.user.Password;

public class UserBehavior extends EventChange {

    DiscountsEnum[] discountsEnum = DiscountsEnum.values();
    TextTypeEnum[] textTypeEnum = TextTypeEnum.values();

    public UserBehavior(User user){

        addSubscriber(UserCreated.class, event -> {
            user.email = Email.of(event.getEmail());
            user.password = Password.of(event.getPassword());
            user.entryDate = EntryDate.of(String.valueOf(event.getEntryDate()));
        });

        addSubscriber(TextQuoted.class, event -> {
            user.textQuote = new TextQuote();
            user.textQuote.setTitle(Title.of(event.getTitle()));
            user.textQuote.setTextType(Type.of(TextTypeEnum.valueOf(event.getTextType())));
            user.textQuote.setSubtotal(Subtotal.of(event.getSubtotal()));
            user.textQuote.setDiscount(Discount.of(DiscountsEnum.valueOf(event.getDiscount())));
            user.textQuote.setTotal(Total.of(event.getTotal()));
        });
    }
}
