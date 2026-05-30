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

/**
 * Handles domain event application for the {@link User} aggregate root.
 * Each subscriber mutates the aggregate state in response to a specific event.
 */
public class UserBehavior extends EventChange {

    public UserBehavior(User user) {

        addSubscriber(UserCreated.class, event -> {
            user.setEmail(Email.of(event.getEmail()));
            user.setPassword(Password.of(event.getPassword()));
            user.setEntryDate(EntryDate.of(String.valueOf(event.getEntryDate())));
        });

        addSubscriber(TextQuoted.class, event -> {
            TextQuote textQuote = new TextQuote();
            textQuote.setTitle(Title.of(event.getTitle()));
            textQuote.setTextType(Type.of(TextTypeEnum.valueOf(event.getTextType())));
            textQuote.setSubtotal(Subtotal.of(event.getSubtotal()));
            textQuote.setDiscount(Discount.of(DiscountsEnum.valueOf(event.getDiscount())));
            textQuote.setTotal(Total.of(event.getTotal()));
            user.setLastTextQuote(textQuote);
        });
    }
}
