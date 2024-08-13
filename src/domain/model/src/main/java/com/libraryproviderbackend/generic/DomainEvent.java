package com.libraryproviderbackend.generic;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DomainEvent is an abstract base class representing an event in the domain model.
 * Each event has a unique identifier, a timestamp indicating when it occurred, and a version number.
 * The event type is also included, which can be useful for handling and categorizing events.
 */
public abstract class DomainEvent implements Serializable {

    // A unique identifier for the event, usually represented as a UUID.
    public final String eventId;

    // The timestamp indicating when the event occurred.
    public LocalDateTime occurredOn;

    // The identifier of the aggregate root associated with the event.
    public String aggregateRootId;

    // The type of the event, used for categorization and handling.
    public String type;

    // The version of the event, used for event versioning and evolution.
    public long version;

    /**
     * Constructor for DomainEvent.
     * Initializes the event with a unique identifier and sets the type based on the class name.
     */
    protected DomainEvent() {
        this.eventId = UUID.randomUUID().toString();
        this.type = this.getClass().getSimpleName();
    }

    /**
     * Constructor for DomainEvent with a custom event type.
     * Useful when the event type needs to be something other than the class name.
     *
     * @param type the type of the event.
     */
    protected DomainEvent(String type) {
        this.eventId = UUID.randomUUID().toString();
        this.type = type;
    }

    /**
     * Gets the unique identifier for this event.
     *
     * @return the event's unique identifier.
     */
    public String getEventId() {
        return eventId;
    }

    /**
     * Gets the timestamp indicating when this event occurred.
     * This is usually set by the event subscriber or handler when the event is applied.
     *
     * @return the timestamp of when the event occurred.
     */
    public LocalDateTime getOccurredOn() {
        return occurredOn;
    }

    /**
     * Sets the timestamp indicating when this event occurred.
     * This method is typically called by the event subscriber or handler.
     *
     * @param occurredOn the timestamp to set.
     */
    public void setOccurredOn(LocalDateTime occurredOn) {
        this.occurredOn = occurredOn;
    }

    public String getAggregateRootId() {
        return aggregateRootId;
    }

    public void setAggregateRootId(String aggregateRootId) {
        this.aggregateRootId = aggregateRootId;
    }

    /**
     * Gets the version number of this event.
     * The version is useful for managing event evolution and ensuring compatibility.
     *
     * @return the version number of the event.
     */
    public long getVersion() {
        return version;
    }

    /**
     * Sets the version number of this event.
     * This is usually managed by the event subscriber or handler and should reflect the correct event version.
     *
     * @param version the version number to set.
     */
    public void setVersion(long version) {
        this.version = version;
    }

    /**
     * Gets the type of this event.
     * The event type is typically used for categorization and processing by event handlers.
     *
     * @return the type of the event.
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns the initial version of the event.
     * This method can be overridden by subclasses if a different initial version is needed.
     * Default is version 0.
     *
     * @return the initial version of the event.
     */
    public long initialVersion() {
        return 0L;
    }

    @Override
    public String toString() {
        return "DomainEvent{" +
                "eventId='" + eventId + '\'' +
                ", occurredOn=" + occurredOn +
                ", version=" + version +
                ", type='" + type + '\'' +
                '}';
    }
}
