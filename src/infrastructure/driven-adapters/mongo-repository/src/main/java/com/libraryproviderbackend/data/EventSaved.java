package com.libraryproviderbackend.data;

import com.libraryproviderbackend.JSONMapper;
import com.libraryproviderbackend.SerializationException;
import com.libraryproviderbackend.generic.DomainEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * MongoDB document that represents a persisted domain event.
 * Acts as the envelope for Event Sourcing storage.
 */
@Document(collection = "events")
public class EventSaved implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(EventSaved.class);

    @Id
    private String id;

    @Field("aggregateRootId")
    private String aggregateRootId;

    @Field("type")
    private String type;

    @Field("occurredOn")
    private LocalDateTime occurredOn;

    @Field("body")
    private String body;

    public EventSaved() {
    }

    public EventSaved(String aggregateRootId, String type, LocalDateTime occurredOn, String body) {
        this.aggregateRootId = aggregateRootId;
        this.type = type;
        this.occurredOn = occurredOn;
        this.body = body;
    }

    /**
     * Serializes a domain event into its JSON envelope for persistence.
     */
    public static String wrapEvent(DomainEvent domainEvent, JSONMapper eventSerializer) {
        return eventSerializer.writeToJson(domainEvent);
    }

    /**
     * Deserializes the stored JSON body back into a concrete {@link DomainEvent}.
     *
     * @throws SerializationException if the class cannot be found or deserialization fails.
     */
    public DomainEvent deserializeEvent(JSONMapper eventSerializer) {
        try {
            Class<?> eventClass = Class.forName(this.type);
            return (DomainEvent) eventSerializer.readFromJson(this.body, eventClass);
        } catch (ClassNotFoundException e) {
            log.error("Cannot deserialize event — class not found: '{}'. " +
                      "This may indicate a renamed or deleted event class.", this.type, e);
            throw new SerializationException("Event class not found: " + this.type, e);
        }
    }

    // ── Getters & Setters ────────────────────────────────────────────────────

    public String getId()                          { return id; }
    public void setId(String id)                   { this.id = id; }

    public String getAggregateRootId()             { return aggregateRootId; }
    public void setAggregateRootId(String v)       { this.aggregateRootId = v; }

    public String getType()                        { return type; }
    public void setType(String type)               { this.type = type; }

    public LocalDateTime getOccurredOn()           { return occurredOn; }
    public void setOccurredOn(LocalDateTime v)     { this.occurredOn = v; }

    public String getBody()                        { return body; }
    public void setBody(String body)               { this.body = body; }
}
