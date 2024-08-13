package com.libraryproviderbackend.generic;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/**
 * ChangeEventSubscriber is responsible for managing domain events, handling subscriptions,
 * and applying events to the subscribed consumers. It also manages the versioning of events.
 */
public class ChangeEventSubscriber {

    // Stores the list of domain events that have been appended.
    private final List<DomainEvent> domainEvents = Collections.synchronizedList(new LinkedList<>());

    // Holds the set of subscribers that will consume the events when they occur.
    private final Set<Consumer<? super DomainEvent>> subscribers = ConcurrentHashMap.newKeySet();

    // Manages the versions of different types of events.
    private final Map<String, AtomicLong> versions = new ConcurrentHashMap<>();

    /**
     * Returns an unmodifiable list of all domain events that have been appended.
     *
     * @return an unmodifiable list of DomainEvent objects.
     */
    public List<DomainEvent> events() {
        return Collections.unmodifiableList(domainEvents);
    }

    /**
     * Allows adding subscribers that will be notified when an event occurs.
     *
     * @param eventChange the EventChange object containing the subscribers to add.
     */
    public void subscribe(EventChange eventChange) {
        this.subscribers.addAll(eventChange.subscribers);
    }

    /**
     * Appends a domain event to the list of events and returns an IChangeApply object
     * that can be used to apply the event to the subscribers.
     *
     * @param domainEvent the DomainEvent to append.
     * @return an IChangeApply object to apply the event.
     */
    public IChangeApply appendEvent(DomainEvent domainEvent) {
        domainEvent.setOccurredOn(LocalDateTime.now());
        long version = nextVersion(domainEvent);
        domainEvent.setVersion(version);
        domainEvents.add(domainEvent);
        return () -> applyEvent(domainEvent);
    }

    /**
     * Applies a domain event to all subscribed consumers. If a consumer is not able to
     * process the event due to a ClassCastException, the exception is logged and ignored.
     *
     * @param domainEvent the DomainEvent to apply.
     */
    public void applyEvent(DomainEvent domainEvent) {
        subscribers.forEach(consumer -> {
            try {
                consumer.accept(domainEvent);
            } catch (ClassCastException e) {
                System.err.println("Event could not be processed: " + e.getMessage());
            }
        });
    }

    /**
     * Calculates the next version number for the given domain event based on its type.
     * If the event type has not been encountered before, it starts with the initial version.
     *
     * @param domainEvent the DomainEvent for which the version is to be calculated.
     * @return the next version number for the domain event.
     */
    private long nextVersion(DomainEvent domainEvent) {
        return versions.computeIfAbsent(domainEvent.getType(), key -> new AtomicLong(domainEvent.initialVersion()))
                .incrementAndGet();
    }
}
