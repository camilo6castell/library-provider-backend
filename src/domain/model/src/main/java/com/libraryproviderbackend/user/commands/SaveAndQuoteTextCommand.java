package com.libraryproviderbackend.user.commands;

import com.libraryproviderbackend.generic.Command;
import com.libraryproviderbackend.user.values.identities.UserId;

public class SaveAndQuoteTextCommand extends Command {

    private String userId;
    private String title;
    private String textType;
    private Float initialPrice;

    public SaveAndQuoteTextCommand() {
    }

    public SaveAndQuoteTextCommand(String userId, String title, String textType, Float initialPrice) {
        if (userId == null || userId.isBlank()) throw new IllegalArgumentException("User ID cannot be null or empty");
        if (title == null || title.isBlank())   throw new IllegalArgumentException("Title cannot be null or empty");
        if (initialPrice == null || initialPrice < 0) throw new IllegalArgumentException("Initial price cannot be negative");
        this.userId = userId;
        this.title = title;
        this.textType = textType;
        this.initialPrice = initialPrice;
    }

    public UserId getUserId()                         { return UserId.of(userId); }
    public String getUserIdRaw()                      { return userId; }
    public void setUserId(String userId)              { this.userId = userId; }

    public String getTitle()                          { return title; }
    public void setTitle(String title)               { this.title = title; }

    public String getTextType()                       { return textType; }
    public void setTextType(String textType)         { this.textType = textType; }

    public Float getInitialPrice()                    { return initialPrice; }
    public void setInitialPrice(Float initialPrice)  { this.initialPrice = initialPrice; }
}
