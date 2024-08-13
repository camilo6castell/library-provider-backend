package com.libraryproviderbackend.user.events;

import com.libraryproviderbackend.generic.DomainEvent;
import com.libraryproviderbackend.user.values.identities.UserId;

import java.time.LocalDate;

public class UserCreated extends DomainEvent {
    private final String email;
    private final String password;
    private final LocalDate entryDate;

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
