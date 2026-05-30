package com.libraryproviderbackend.generic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/**
 * Manages the registration and dispatching of domain events within an aggregate root.
 * Handles event versioning and tracks uncommitted events.
 */
public class ChangeEventSubscriber {

    private static final Logger log = LoggerFactory.getLogger(ChangeEventSubscriber.class);

    private final List<DomainEvent> domainEvents = Collections.synchronizedList(new LinkedList<>());
    private final Set<Consumer<? super DomainEvent>> subscribers = ConcurrentHashMap.newKeySet();
    private final Map<String, AtomicLong> versions = new ConcurrentHashMap<>();

    public List<DomainEvent> events() {
        return Collections.unmodifiableList(domainEvents);
    }

    public void markCommitted() {
        domainEvents.clear();
    }

    public void subscribe(EventChange eventChange) {
        this.subscribers.addAll(eventChange.subscribers);
    }

    /**
     * Appends a new event to uncommitted changes. Sets timestamp and version, then returns
     * an {@link IChangeApply} handle so the caller can apply (dispatch) it to subscribers.
     */
    public IChangeApply appendEvent(DomainEvent domainEvent) {
        domainEvent.setOccurredOn(LocalDateTime.now());
        domainEvent.setVersion(nextVersion(domainEvent));
        domainEvents.add(domainEvent);
        return () -> applyEvent(domainEvent);
    }

    /**
     * Dispatches a domain event to all registered subscribers.
     * ClassCastException is swallowed intentionally — a subscriber for a different event type
     * simply does nothing when it receives an incompatible event.
     */
    public void applyEvent(DomainEvent domainEvent) {
        subscribers.forEach(consumer -> {
            try {
                consumer.accept(domainEvent);
            } catch (ClassCastException e) {
                log.trace("Subscriber skipped event '{}': {}", domainEvent.getType(), e.getMessage());
            }
        });
    }

    private long nextVersion(DomainEvent domainEvent) {
        return versions
                .computeIfAbsent(domainEvent.getType(), key -> new AtomicLong(domainEvent.initialVersion()))
                .incrementAndGet();
    }
}
