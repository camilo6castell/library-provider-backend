package com.libraryproviderbackend.generic;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Base class for all domain events in the system.
 * Follows the Event Sourcing pattern: events are immutable records of what happened.
 */
public abstract class DomainEvent implements Serializable {

    private final String eventId;
    private LocalDateTime occurredOn;
    private String aggregateRootId;
    private String type;
    private long version;

    protected DomainEvent() {
        this.eventId = UUID.randomUUID().toString();
        this.type = this.getClass().getSimpleName();
    }

    protected DomainEvent(String type) {
        this.eventId = UUID.randomUUID().toString();
        this.type = type;
    }

    public String getEventId() {
        return eventId;
    }

    public LocalDateTime getOccurredOn() {
        return occurredOn;
    }

    public void setOccurredOn(LocalDateTime occurredOn) {
        this.occurredOn = occurredOn;
    }

    public String getAggregateRootId() {
        return aggregateRootId;
    }

    public void setAggregateRootId(String aggregateRootId) {
        this.aggregateRootId = aggregateRootId;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long initialVersion() {
        return 0L;
    }

    @Override
    public String toString() {
        return "DomainEvent{" +
                "eventId='" + eventId + '\'' +
                ", occurredOn=" + occurredOn +
                ", aggregateRootId='" + aggregateRootId + '\'' +
                ", type='" + type + '\'' +
                ", version=" + version +
                '}';
    }
}
