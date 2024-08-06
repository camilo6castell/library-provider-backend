package com.libraryproviderbackend.generic;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public abstract class EventChange {
    public Set<Consumer<? super DomainEvent>> subscribers = new HashSet<>();

    protected void addSubscriber(Consumer<? extends DomainEvent> changeEvent){
        subscribers.add((Consumer<? super DomainEvent>) changeEvent);
    }
}
