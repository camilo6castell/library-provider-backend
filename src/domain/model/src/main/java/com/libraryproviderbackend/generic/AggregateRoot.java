package com.libraryproviderbackend.generic;

import java.time.LocalDateTime;
import java.util.List;

/**
 * AggregateRoot is an abstract base class for all aggregate roots in the domain model.
 * It extends the Entity class and provides mechanisms to manage domain events via ChangeEventSubscriber.
 *
 * @param <I> the type of the identifier for the aggregate root.
 */
public abstract class AggregateRoot<I extends Identity> extends Entity<I> {

    // Subscriber for domain events, used to track and apply events within the aggregate root.
    private final ChangeEventSubscriber changeEventSubscriber;

    /**
     * Constructor for AggregateRoot.
     * Initializes the aggregate with an identifier and sets up the ChangeEventSubscriber.
     *
     * @param id the identifier for the aggregate root.
     */
    public AggregateRoot(I id) {
        super(id);
        this.changeEventSubscriber = new ChangeEventSubscriber();
    }

    /**
     * Retrieves all uncommitted domain events.
     * These are events that have been applied but not yet committed to the event store or database.
     *
     * @return a list of uncommitted domain events.
     */
    public List<DomainEvent> getUncommittedChanges() {
        return List.copyOf(changeEventSubscriber.events());
    }

    /**
     * Marks all changes as committed.
     * This clears the list of uncommitted events, typically after they have been persisted.
     */
    public void markChangesAsCommitted() {
        changeEventSubscriber.events().clear();
    }

    /**
     * Subscribes to event changes within the aggregate root.
     * Allows the aggregate to respond to domain events by applying corresponding changes.
     *
     * @param eventChange the event change handler that contains the logic to apply events.
     */
    protected final void subscribe(EventChange eventChange) {
        changeEventSubscriber.subscribe(eventChange);
    }

    /**
     * Applies a domain event to the aggregate root.
     * This method will invoke any subscribed event handlers to modify the aggregate's state.
     *
     * @param domainEvent the domain event to apply.
     */
    protected void applyEvent(DomainEvent domainEvent) {
        domainEvent.setOccurredOn(LocalDateTime.now()); // Set the timestamp when the event is applied
        domainEvent.setVersion(domainEvent.initialVersion()); // Set the initial version for the event
        changeEventSubscriber.applyEvent(domainEvent);
    }

    /**
     * Appends a domain event to the list of uncommitted events and applies it.
     * This method also sets the aggregate root ID in the event.
     *
     * @param domainEvent the domain event to append and apply.
     * @return an instance of IChangeApply that can be used to apply the event.
     */
    protected IChangeApply appendEvent(DomainEvent domainEvent) {
        String aggregateRootName = this.getClass().getSimpleName().replaceAll("AggregateRoot", "").toLowerCase();
        domainEvent.setAggregateRootId(identity().value());
        domainEvent.setType(aggregateRootName); // Set the event type based on the aggregate root name
        return changeEventSubscriber.appendEvent(domainEvent);
    }
}

//public abstract class AggregateRoot<I extends Identity> extends Entity<I>{
//    private final ChangeEventSubscriber changeEventSubscriber;
//    public AggregateRoot(I id) {
//        super(id);
//        changeEventSubscriber = new ChangeEventSubscriber();
//    }
//
//
//    // TO USE IN USE CASES
//    // To get all events from changeEventsSubscriber
//    public List<DomainEvent> getUncommittedChanges(){
//        return List.copyOf(changeEventSubscriber.events());
//    }
//    //
//
//    //To clean events
//    public void markChangesAsCommitted(){
//        changeEventSubscriber.events().clear();
//    }
//    //
//    //
//
//    //Subscribe to eventChangeSubscriber
//    protected final void subscribe(EventChange eventChange){
//        changeEventSubscriber.subscribe(eventChange);
//    }
//    //
//    //ApplyEvent
//    protected void applyEvent(DomainEvent domainEvent){
//        changeEventSubscriber.applyEvent(domainEvent);
//    }
//    //
//    //Add event to events in changeEventSubscriber
//    protected IChangeApply appendEvent(DomainEvent domainEvent){
//        var nameClass = identity().getClass().getSimpleName(); //Larry
//        //var nameClass = this.getClass().getSimpleName().toLowerCase(); //Jacob
//        var aggregate = nameClass.replaceAll("Identity|Id|ID", "").toLowerCase();
//        //domainEvent.setAggregateRootName(nameClass);
//        //event.setAggregateRootName(aggregate);
//        domainEvent.setAggregateRootId(identity().value());
//        return changeEventSubscriber.appendEvent(domainEvent);
//    }
////

