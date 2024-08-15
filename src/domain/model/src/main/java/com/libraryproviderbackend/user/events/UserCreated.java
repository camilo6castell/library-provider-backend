package com.libraryproviderbackend.user.events;

import com.libraryproviderbackend.generic.DomainEvent;
import com.libraryproviderbackend.user.values.identities.UserId;

import java.time.LocalDate;

public class UserCreated extends DomainEvent {
    public String email;
    public String password;
    public LocalDate entryDate;

    public UserCreated() {
    }

    public UserCreated(
            String email,
            String password,
            LocalDate entryDate
    ) {
        super(UserEventsEnum.USER_CREATED.toString());
        this.email = email;
        this.password = password;
        this.entryDate = entryDate;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }
}
