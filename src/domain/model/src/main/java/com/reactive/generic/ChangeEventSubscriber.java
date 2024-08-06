package com.reactive.generic;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

public class ChangeEventSubscriber {


    public final List<DomainEvent> domainEvents = new LinkedList<>();
    //Associated logic ready for execute when an event occurs
    private final Set<Consumer<? super DomainEvent>> subscribers = new HashSet<>();
    private final Map<String, AtomicLong> versions = new ConcurrentHashMap<>();


    //To get all events
    public List<DomainEvent> events(){
        return domainEvents;
    }

    //Allow us to add subscribers
    public final void subscribe(EventChange eventChange){
        this.subscribers.addAll(eventChange.subscribers);
    }

    //Allow us to add DomainEvents
    public final IChangeApply appendEvent(DomainEvent domainEvent){
        domainEvents.add(domainEvent);
        return () -> applyEvent(domainEvent);
    }

    //Apply domainEvent
    public final void applyEvent(DomainEvent domainEvent){
        subscribers.forEach(consumer -> {
            try{
                consumer.accept(domainEvent);
//                var map = versions.get(event.type);
//                long version = nextVersion(event, map);
//                event.setVersionType(version);
            } catch (ClassCastException ignored){}
        });
    }
    //
    //Find next domain version
//    private long nextVersion(Event event, AtomicLong map){
//        if (map == null) {
//            versions.put(event.type, new AtomicLong(event.versionType()));
//            return event.versionType();
//        }
//        return versions.get(event.type).incrementAndGet();
//    }
    //
}
