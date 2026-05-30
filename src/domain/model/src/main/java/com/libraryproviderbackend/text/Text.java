package com.libraryproviderbackend.text;

import com.libraryproviderbackend.generic.AggregateRoot;
import com.libraryproviderbackend.text.events.TextCreated;
import com.libraryproviderbackend.text.values.*;

import java.util.List;

/**
 * Text aggregate root.
 * Represents a book or novel available in the catalogue.
 */
public class Text extends AggregateRoot<TextId> {

    private Title title;
    private Type type;
    private InitialPrice initialPrice;

    public Text(TextId textId, Title title, Type type, InitialPrice initialPrice) {
        super(textId);
        subscribe(new TextBehavior(this));
        appendEvent(new TextCreated(title.value(), type.value(), initialPrice.value())).apply();
    }

    private Text(TextId textId) {
        super(textId);
        subscribe(new TextBehavior(this));
    }

    public static Text from(TextId textId, List<TextCreated> events) {
        Text text = new Text(textId);
        events.forEach(text::applyEvent);
        return text;
    }

    // ── Package-private setters used by TextBehavior ─────────────────────────

    void setTitle(Title title)              { this.title = title; }
    void setType(Type type)                 { this.type = type; }
    void setInitialPrice(InitialPrice p)    { this.initialPrice = p; }

    // ── Accessors ───────────────────────────────────────────────────────────

    public Title getTitle()                 { return title; }
    public Type getType()                   { return type; }
    public InitialPrice getInitialPrice()   { return initialPrice; }
    public Float getInitialFloatPrice()     { return initialPrice.value(); }
}
