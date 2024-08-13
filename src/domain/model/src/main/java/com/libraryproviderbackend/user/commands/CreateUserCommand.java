package com.libraryproviderbackend.user.commands;

import com.libraryproviderbackend.generic.Command;

public class CreateUserCommand extends Command {

    public String email;

    public String password;

    public String entryDate;

    public CreateUserCommand() {
        super();
    }

    public CreateUserCommand(String email, String password, String entryDate) {
        super();
        this.email = email;
        this.password = password;
        this.entryDate = entryDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }
}
