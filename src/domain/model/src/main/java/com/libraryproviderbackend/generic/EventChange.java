package com.libraryproviderbackend.generic;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * The EventChange class provides a mechanism for managing event subscribers
 * in a domain-driven design context. Subscribers are functions (Consumers)
 * that handle specific types of domain events.
 */
public abstract class EventChange {

    /**
     * A set of subscribers, where each subscriber is a Consumer that handles
     * a specific type of DomainEvent. Subscribers are functions that will be
     * triggered when an event of the specified type occurs.
     */
    public final Set<Consumer<? super DomainEvent>> subscribers = new HashSet<>();

    /**
     * Adds a subscriber (event handler) for a specific type of domain event.
     * The subscriber is a function (Consumer) that accepts a DomainEvent of the
     * specified type.
     *
     * @param <T> The type of the DomainEvent that the subscriber can handle.
     * @param eventType The class type of the DomainEvent that the subscriber will handle.
     * @param changeEvent The event handler (Consumer) that processes the DomainEvent.
     */
    protected <T extends DomainEvent> void addSubscriber(Class<T> eventType, Consumer<T> changeEvent) {
        // Wrapper to ensure the consumer is correctly typed.
        subscribers.add(event -> {
            if (eventType.isInstance(event)) {
                changeEvent.accept(eventType.cast(event));
            }
        });
    }

    /**
     * Retrieves an unmodifiable set of all subscribers (event handlers) added to this EventChange.
     *
     * @return A set of Consumers that handle DomainEvents.
     */
    public Set<Consumer<? super DomainEvent>> getSubscribers() {
        return Set.copyOf(subscribers);
    }
}
