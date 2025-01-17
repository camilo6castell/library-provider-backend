package com.libraryproviderbackend.text.commands;

import com.libraryproviderbackend.generic.Command;


public class CreateTextCommand extends Command {
    public String title;
    public String textType;
    public Float initialPrice;

    public CreateTextCommand() {
        super();
    }

    public CreateTextCommand(String title, String textType, Float initialPrice) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        if (textType == null) {
            throw new IllegalArgumentException("Type cannot be null");
        }
        if (initialPrice == null || initialPrice < 0) {
            throw new IllegalArgumentException("Initial price cannot be null or negative");
        }

        this.title = title;
        this.textType = textType;
        this.initialPrice = initialPrice;
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






//public class CreateTextCommand extends Command {
//    public String title;
//    public int type;
//    public Float initialPrice;
//
//    public CreateTextCommand() {
//    }
//
//    public CreateTextCommand(String title, int type, Float initialPrice) {
//        this.title = title;
//        this.type = type;
//        this.initialPrice = initialPrice;
//    }
//}
