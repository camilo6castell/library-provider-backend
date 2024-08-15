package com.libraryproviderbackend.user;

import com.libraryproviderbackend.generic.AggregateRoot;
import com.libraryproviderbackend.generic.DomainEvent;
import com.libraryproviderbackend.text.Text;
import com.libraryproviderbackend.text.values.TextTypeEnum;
import com.libraryproviderbackend.user.entity.BatchQuote;
import com.libraryproviderbackend.user.entity.Quote;
import com.libraryproviderbackend.user.entity.TextQuote;
import com.libraryproviderbackend.user.events.UserCreated;
import com.libraryproviderbackend.user.values.identities.UserId;
import com.libraryproviderbackend.user.values.shared.DiscountsEnum;
import com.libraryproviderbackend.user.values.user.Email;
import com.libraryproviderbackend.user.values.user.EntryDate;
import com.libraryproviderbackend.user.values.user.Password;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;


public class User extends AggregateRoot<UserId> {

    public Email email;
    public Password password;
    public EntryDate entryDate;

    public Quote quote;



    public User(UserId userId, Email email, Password password, EntryDate entryDate) {
        super(userId);
        subscribe(new UserBehavior(this));
        appendEvent(
                new UserCreated(
                        email.value(),
                        password.value(),
                        entryDate.value()
                )
        ).apply();

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private User(UserId userId) {
        super(userId);
        subscribe(new UserBehavior(this));
    }

    public static User from(
            String userId,
            List<DomainEvent> domainEvents
    ) {
        User user = new User(UserId.of(userId));
        domainEvents.forEach(user::applyEvent);
        return user;
    }




    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public TextQuote quoteText(String title, Float initialPrice, TextTypeEnum type, DiscountsEnum discount) {
        return new TextQuote(title, initialPrice, type, discount);
    }


//    public List<TextQuote> calculateBatchTextsQuote(List<Text> textList, User user){
//
//        float discountValue = calculateSeniorityDiscount(user.entryDate.value());
//        int toWholeSale = 0;
//        List<TextQuote> auxTextQuoteList = new ArrayList<>();
//        float totalAux = 0f;
//        float subTotalAux = 0f;
//        for (int i = 0; i < textList.size(); i++) {
//
//            auxTextQuoteList.add(
//                    new TextQuote(
//                            textList.get(i).getTitle().value(),
//                            textList.get(i).getInitialFloatPrice(),
//                            textList.get(i).getType().value(),
//                            toWholeSale < 10 ? DiscountsEnum.NONE : DiscountsEnum.WHOLESALE
//                    )
//            );
//            toWholeSale += 1;
//            TextQuote lastTextQuote = auxTextQuoteList.getLast();
//            subTotalAux += lastTextQuote.total.value();
//            totalAux += auxTextQuoteList.getLast().total.value();
//        }
//
//        totalAux = totalAux * discountValue;
//
//        return auxTextQuoteList;
//    }


    public BatchQuote calculateVariousTextQuote(List<Text> bookList, List<Text> novelList, EntryDate entryDate) {
        return new BatchQuote(bookList, novelList, entryDate.value());
    }

    public BatchQuote calculateBudgetTextsQuote(List<Text> textList, Float budget, EntryDate entryDate) {
        return new BatchQuote(textList, budget, entryDate.value());
    }

    public static float calculateSeniorityDiscount(LocalDate clientEntryDate) {
        LocalDate today = LocalDate.now();
        int years = Period.between(clientEntryDate, today).getYears();
        float seniorityDiscount;
        if (years < 1) {
            seniorityDiscount = 1f;
        } else if (years <= 2) {
            seniorityDiscount = 0.88f;
        } else {
            seniorityDiscount = 0.83f
            ;
        }
        return seniorityDiscount;
    }
}
