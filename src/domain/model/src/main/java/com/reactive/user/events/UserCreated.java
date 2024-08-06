package com.reactive.user.events;

import com.reactive.generic.DomainEvent;
import com.reactive.user.values.identities.UserId;

import java.time.LocalDate;
import java.util.UUID;

public class UserCreated extends DomainEvent {
    public String email;
    public String password;
    public LocalDate entryDate;

    public UserCreated() {
    }

    public UserCreated(
            UserId userId,
            String email,
            String password,
            LocalDate entryDate
    ) {
        super(userId.value(), UserEventsEnum.USER_CREATED.toString());
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

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }
}
