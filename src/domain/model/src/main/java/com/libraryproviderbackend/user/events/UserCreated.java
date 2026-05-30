package com.libraryproviderbackend.user.events;

import com.libraryproviderbackend.generic.DomainEvent;

import java.time.LocalDate;

/**
 * Domain event representing the successful creation of a user account.
 */
public class UserCreated extends DomainEvent {

    private String email;
    private String password;
    private LocalDate entryDate;

    /** Required for Jackson deserialization. */
    public UserCreated() {
    }

    public UserCreated(String email, String password, LocalDate entryDate) {
        super(UserEventsEnum.USER_CREATED.toString());
        this.email = email;
        this.password = password;
        this.entryDate = entryDate;
    }

    public String getEmail()                         { return email; }
    public void setEmail(String email)               { this.email = email; }

    public String getPassword()                      { return password; }
    public void setPassword(String password)         { this.password = password; }

    public LocalDate getEntryDate()                  { return entryDate; }
    public void setEntryDate(LocalDate entryDate)    { this.entryDate = entryDate; }
}
