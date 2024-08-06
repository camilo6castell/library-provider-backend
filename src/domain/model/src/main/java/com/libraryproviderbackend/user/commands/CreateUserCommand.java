package com.libraryproviderbackend.user.commands;

import com.libraryproviderbackend.generic.Command;

public class CreateUserCommand extends Command {
    public String email;
    public String password;
    public String entryDate;

    public CreateUserCommand() {
    }

    public CreateUserCommand(
            String email,
            String password,
            String entryDate
    ) {
        this.email = email;
        this.password = password;
        this.entryDate = entryDate;
    }
}
