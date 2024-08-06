package com.reactive.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.reactive.IJSONMapper;
import com.reactive.JSONMapper;
import com.reactive.generic.DomainEvent;
import com.reactive.user.events.UserCreated;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Document
public class EventSaved implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(EventSaved.class);


    @Id
    public String aggregateRootId;
    public String type;
    public Instant when;
    public String body;

    public EventSaved() {
    }

    public EventSaved(
            String aggregateRootId,
            String type,
            Instant when,
            String body
    ) {
        this.aggregateRootId = aggregateRootId;
        this.type = type;
        this.when = when;
        this.body = body;
    }

    public static String wrapEvent(DomainEvent domainEvent, JSONMapper eventSerializer){
        return eventSerializer.writeToJson(domainEvent);
    }


//    public static String wrapEvent(DomainEvent event, IJSONMapper eventSerializer) {
//        try {
//            return eventSerializer.serialize(event);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException("Failed to serialize event", e);
//        }
//    }

//    public Event deserializeEvent(IJSONMapper eventSerializer) {
//        try {
//            return (Event) eventSerializer
//                    .deserialize(this.getBody(), Class.forName(this.getType()));
//        } catch (ClassNotFoundException e) {
//            return null;
//        }
//    }

    public DomainEvent deserializeEvent(JSONMapper eventSerializer) {
        try {
            return (DomainEvent) eventSerializer
                    .readFromJson(this.getBody(), Class.forName(this.getType()));
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

//    public DomainEvent deserializeEvent(IJSONMapper eventSerializer) {
//        try {
//            return (DomainEvent) eventSerializer.deserialize(this.getBody(), Class.forName(this.getType()));
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException("Failed to deserialize event", e);
//        } catch (ClassNotFoundException e) {
//            return null;
//        }
//    }

//    public static String wrapEvent(Event domainEvent, IJSONMapper eventSerializer){
//        return eventSerializer.serialize(domainEvent);
//    }

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

    public Instant getWhen() {
        return when;
    }

    public void setWhen(Instant when) {
        this.when = when;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public interface EventSerializer {
        <T extends DomainEvent> T deserialize(String aSerialization, final Class<?> aType);

        String serialize(DomainEvent object);
    }
}
