package com.libraryproviderbackend.generic;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public abstract class DomainEvent implements Serializable {

    public UUID uuid;
    public Instant when;
    public String aggregateRootId;
    public String type;

    public DomainEvent() {
    }

    public DomainEvent(String aggregateRootId, String type) {
        this.uuid = UUID.randomUUID();;
        this.when = Instant.now();
        this.aggregateRootId = aggregateRootId;
        this.type = type;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Instant getWhen() {
        return when;
    }

    public void setWhen(Instant when) {
        this.when = when;
    }

    public String getAggregateRootId() {
        return aggregateRootId;
    }

    public void setAggregateRootId(String aggregateRootId) {
        this.aggregateRootId = aggregateRootId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
