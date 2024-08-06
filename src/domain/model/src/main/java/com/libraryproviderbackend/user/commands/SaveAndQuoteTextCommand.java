package com.libraryproviderbackend.user.commands;

import com.libraryproviderbackend.generic.Command;
import com.libraryproviderbackend.user.values.identities.UserId;

public class SaveAndQuoteTextCommand extends Command {
    public UserId userId;
    public String title;
    public int type;
    public Float initialPrice;
    public String entryDate;

    public SaveAndQuoteTextCommand() {
    }

    public SaveAndQuoteTextCommand(UserId userId, String entryDate, Float initialPrice, int type, String title) {
        this.userId = userId;
        this.entryDate = entryDate;
        this.initialPrice = initialPrice;
        this.type = type;
        this.title = title;
    }
}
