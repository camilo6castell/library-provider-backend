package com.reactive.generic;

import java.util.List;

public abstract class AggregateRoot<I extends Identity> extends Entity<I>{
    private final ChangeEventSubscriber changeEventSubscriber;
    public AggregateRoot(I id) {
        super(id);
        changeEventSubscriber = new ChangeEventSubscriber();
    }


    // TO USE IN USE CASES
    // To get all events from changeEventsSubscriber
    public List<DomainEvent> getUncommittedChanges(){
        return List.copyOf(changeEventSubscriber.events());
    }
    //

    //To clean events
    public void markChangesAsCommitted(){
        changeEventSubscriber.events().clear();
    }
    //
    //

    //Subscribe to eventChangeSubscriber
    protected final void subscribe(EventChange eventChange){
        changeEventSubscriber.subscribe(eventChange);
    }
    //
    //ApplyEvent
    protected void applyEvent(DomainEvent domainEvent){
        changeEventSubscriber.applyEvent(domainEvent);
    }
    //
    //Add event to events in changeEventSubscriber
    protected IChangeApply appendEvent(DomainEvent domainEvent){
        var nameClass = identity().getClass().getSimpleName(); //Larry
        //var nameClass = this.getClass().getSimpleName().toLowerCase(); //Jacob
        var aggregate = nameClass.replaceAll("Identity|Id|ID", "").toLowerCase();
        //domainEvent.setAggregateRootName(nameClass);
        //event.setAggregateRootName(aggregate);
        domainEvent.setAggregateRootId(identity().value());
        return changeEventSubscriber.appendEvent(domainEvent);
    }
    //
}

