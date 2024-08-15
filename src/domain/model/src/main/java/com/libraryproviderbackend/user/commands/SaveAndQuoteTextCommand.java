package com.libraryproviderbackend.user.commands;

import com.libraryproviderbackend.generic.Command;
import com.libraryproviderbackend.user.values.identities.UserId;

public class SaveAndQuoteTextCommand extends Command {
    public  UserId userId;
    public  String title;
    public  String textType;
    public  Float initialPrice;

    public SaveAndQuoteTextCommand() {
        super();
    }

    public SaveAndQuoteTextCommand(UserId userId, String title, String textType, Float initialPrice) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        if (initialPrice == null || initialPrice < 0) {
            throw new IllegalArgumentException("Initial price cannot be null or negative");
        }

        this.userId = userId;
        this.title = title;
        this.textType = textType;
        this.initialPrice = initialPrice;
    }

    public UserId getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getTextType() {
        return textType;
    }

    public Float getInitialPrice() {
        return initialPrice;
    }
}






//public class SaveAndQuoteTextCommand extends Command {
//    public UserId userId;
//    public String title;
//    public int type;
//    public Float initialPrice;
//    public String entryDate;
//
//    public SaveAndQuoteTextCommand() {
//    }
//
//    public SaveAndQuoteTextCommand(UserId userId, String entryDate, Float initialPrice, int type, String title) {
//        this.userId = userId;
//        this.entryDate = entryDate;
//        this.initialPrice = initialPrice;
//        this.type = type;
//        this.title = title;
//    }
//}
