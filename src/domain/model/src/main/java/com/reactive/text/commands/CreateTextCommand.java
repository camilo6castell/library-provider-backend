package com.reactive.text.commands;

import com.reactive.generic.Command;

public class CreateTextCommand extends Command {
    public String title;
    public int type;
    public Float initialPrice;

    public CreateTextCommand() {
    }

    public CreateTextCommand(String title, int type, Float initialPrice) {
        this.title = title;
        this.type = type;
        this.initialPrice = initialPrice;
    }
}
