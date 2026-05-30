package com.libraryproviderbackend.user;

import com.libraryproviderbackend.generic.AggregateRoot;
import com.libraryproviderbackend.generic.DomainEvent;
import com.libraryproviderbackend.text.Text;
import com.libraryproviderbackend.text.values.TextTypeEnum;
import com.libraryproviderbackend.user.entity.BatchQuote;
import com.libraryproviderbackend.user.entity.TextQuote;
import com.libraryproviderbackend.user.events.TextQuoted;
import com.libraryproviderbackend.user.events.UserCreated;
import com.libraryproviderbackend.user.values.identities.UserId;
import com.libraryproviderbackend.user.values.user.Email;
import com.libraryproviderbackend.user.values.user.EntryDate;
import com.libraryproviderbackend.user.values.user.Password;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

/**
 * User aggregate root.
 * Encapsulates user state and domain behavior related to text quoting.
 */
public class User extends AggregateRoot<UserId> {

    private Email email;
    private Password password;
    private EntryDate entryDate;
    private TextQuote lastTextQuote;

    /**
     * Constructor for creating a brand new User (write side).
     */
    public User(UserId userId, Email email, Password password, EntryDate entryDate) {
        super(userId);
        subscribe(new UserBehavior(this));
        appendEvent(
                new UserCreated(
                        email.value(),
                        password.value(),
                        entryDate.value()
                )
        ).apply();
    }

    /**
     * Private reconstruction constructor used by {@link #from(String, List)}.
     */
    private User(UserId userId) {
        super(userId);
        subscribe(new UserBehavior(this));
    }

    /**
     * Reconstructs a User from a stream of persisted domain events (event sourcing).
     */
    public static User from(String userId, List<DomainEvent> domainEvents) {
        User user = new User(UserId.of(userId));
        domainEvents.forEach(user::applyEvent);
        return user;
    }

    // ── Domain behaviour ────────────────────────────────────────────────────

    public void quoteText(String title, Float initialPrice, TextTypeEnum textType, LocalDate clientEntryDate) {
        TextQuote textQuote = new TextQuote(title, initialPrice, textType, calculateSeniorityDiscount(clientEntryDate));
        appendEvent(
                new TextQuoted(
                        textQuote.getTitle().value(),
                        textQuote.getTextType().value().toString(),
                        textQuote.getSubtotal().value(),
                        textQuote.getDiscount().value().toString(),
                        textQuote.getTotal().value()
                )
        ).apply();
    }

    public BatchQuote calculateVariousTextQuote(List<Text> bookList, List<Text> novelList) {
        return new BatchQuote(bookList, novelList, entryDate.value());
    }

    public BatchQuote calculateBudgetTextsQuote(List<Text> textList, Float budget) {
        return new BatchQuote(textList, budget, entryDate.value());
    }

    /**
     * Calculates a seniority-based discount multiplier based on how long the user has been a client.
     * <ul>
     *   <li>Less than 1 year  → no discount  (1.0)</li>
     *   <li>1–2 years         → 12% discount (0.88)</li>
     *   <li>More than 2 years → 17% discount (0.83)</li>
     * </ul>
     */
    public static float calculateSeniorityDiscount(LocalDate clientEntryDate) {
        int years = Period.between(clientEntryDate, LocalDate.now()).getYears();
        if (years < 1) return 1f;
        if (years <= 2) return 0.88f;
        return 0.83f;
    }

    // ── Package-private setters used by UserBehavior ─────────────────────────

    void setEmail(Email email)             { this.email = email; }
    void setPassword(Password password)    { this.password = password; }
    void setEntryDate(EntryDate entryDate) { this.entryDate = entryDate; }
    void setLastTextQuote(TextQuote tq)    { this.lastTextQuote = tq; }

    // ── Accessors ───────────────────────────────────────────────────────────

    public Email getEmail()               { return email; }
    public Password getPassword()         { return password; }
    public EntryDate getEntryDate()       { return entryDate; }
    public TextQuote getLastTextQuote()   { return lastTextQuote; }
}
