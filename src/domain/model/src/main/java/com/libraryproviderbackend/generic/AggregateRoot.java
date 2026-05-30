package com.libraryproviderbackend.generic;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Base class for all aggregate roots in the domain model.
 * Manages the lifecycle of domain events following the Event Sourcing pattern.
 *
 * @param <I> the type of the identifier for the aggregate root.
 */
public abstract class AggregateRoot<I extends Identity> extends Entity<I> {

    private final ChangeEventSubscriber changeEventSubscriber;

    protected AggregateRoot(I id) {
        super(id);
        this.changeEventSubscriber = new ChangeEventSubscriber();
    }

    /**
     * Returns uncommitted domain events (events generated but not yet persisted).
     */
    public List<DomainEvent> getUncommittedChanges() {
        return List.copyOf(changeEventSubscriber.events());
    }

    /**
     * Clears the list of uncommitted events, typically called after successful persistence.
     */
    public void markChangesAsCommitted() {
        changeEventSubscriber.markCommitted();
    }

    /**
     * Registers an event handler (behavior) for this aggregate root.
     */
    protected final void subscribe(EventChange eventChange) {
        changeEventSubscriber.subscribe(eventChange);
    }

    /**
     * Replays a domain event onto the aggregate root (used for event sourcing reconstruction).
     * Does NOT add the event to uncommitted changes.
     */
    protected void applyEvent(DomainEvent domainEvent) {
        changeEventSubscriber.applyEvent(domainEvent);
    }

    /**
     * Appends a new domain event to the aggregate's uncommitted changes.
     * Sets the aggregateRootId and type before appending.
     */
    protected IChangeApply appendEvent(DomainEvent domainEvent) {
        domainEvent.setAggregateRootId(identity().value());
        domainEvent.setType(this.getClass().getSimpleName().toLowerCase());
        return changeEventSubscriber.appendEvent(domainEvent);
    }
}
