package com.libraryproviderbackend;

import com.libraryproviderbackend.config.IMongoRepository;
import com.libraryproviderbackend.data.EventSaved;
import com.libraryproviderbackend.usecase.generic.gateway.ITextRepository;
import com.libraryproviderbackend.usecase.generic.gateway.IUserRepository;

import com.libraryproviderbackend.generic.DomainEvent;
import com.libraryproviderbackend.user.User;
import com.libraryproviderbackend.user.events.UserCreated;
import com.libraryproviderbackend.user.values.identities.UserId;
import com.libraryproviderbackend.user.values.user.Email;
import com.libraryproviderbackend.user.values.user.EntryDate;
import com.libraryproviderbackend.user.values.user.Password;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;

/**
 * Adapter class for MongoDB operations related to Users and Texts.
 * Implements repository interfaces for user and text domain models.
 */
@Component
public class MongoRepositoryAdapter implements IUserRepository, ITextRepository {

    private static final Logger logger = LoggerFactory.getLogger(MongoRepositoryAdapter.class);

    private final IMongoRepository repository;
    private final JSONMapper eventSerializer;
    private final ReactiveMongoTemplate template;

    /**
     * Constructs a new MongoRepositoryAdapter with the provided dependencies.
     *
     * @param repository       The MongoDB repository interface for saving events.
     * @param eventSerializer  Serializer/deserializer for domain events.
     * @param template         Reactive MongoDB template for executing queries.
     */
    public MongoRepositoryAdapter(IMongoRepository repository, JSONMapper eventSerializer, ReactiveMongoTemplate template) {
        this.repository = repository;
        this.eventSerializer = eventSerializer;
        this.template = template;
    }

    /**
     * Retrieves a stream of domain events associated with a specific aggregate root ID.
     *
     * @param aggregateRootId The ID of the aggregate root.
     * @return A Flux stream of DomainEvents associated with the provided aggregate root ID.
     */
    @Override
    public Flux<DomainEvent> getEventsByAggregateRootId(String aggregateRootId) {
        return template.find(Query.query(Criteria.where("aggregateRootId").is(aggregateRootId)), EventSaved.class)
                .map(storedEvent -> storedEvent.deserializeEvent(eventSerializer));
    }

    /**
     * Finds a user by their email address by searching for a UserCreated event.
     *
     * @param email The email address to search for.
     * @return A Mono emitting the found User or Mono.empty() if no user was found.
     */
    @Override
    public Mono<User> findByEmail(String email) {
        return getEventsByType("com.libraryproviderbackend.user.events.UserCreated")
                .filter(event -> {
                    if (event instanceof UserCreated) {
                        return ((UserCreated) event).getEmail().equalsIgnoreCase(email);
                    }
                    return false;
                })
                .next()  // Returns the first matching event found.
                .map(event -> {
                    UserCreated userCreatedEvent = (UserCreated) event;
                    return new User(
                            UserId.of(userCreatedEvent.getAggregateRootId()),
                            Email.of(userCreatedEvent.getEmail()),
                            Password.of(userCreatedEvent.getPassword()),
                            EntryDate.of(userCreatedEvent.getEntryDate().toString())
                    );
                })
                .switchIfEmpty(Mono.empty());  // Returns Mono.empty() if no matching event is found.
    }

    /**
     * Retrieves a stream of domain events by their event type.
     *
     * @param eventType The fully qualified class name of the event type to search for.
     * @return A Flux stream of DomainEvents matching the provided event type.
     */
    @Override
    public Flux<DomainEvent> getEventsByType(String eventType) {
        return template.find(Query.query(Criteria.where("type").is(eventType)), EventSaved.class)
                .map(storedEvent -> storedEvent.deserializeEvent(eventSerializer));
    }

    /**
     * Saves a domain event to the MongoDB repository.
     *
     * @param domainEvent The DomainEvent to save.
     * @return A Mono emitting the saved DomainEvent, deserialized from storage.
     */
    @Override
    public Mono<DomainEvent> saveEvent(DomainEvent domainEvent) {
        logger.info("Saving event: {}", domainEvent);

        // Convert the domain event to an EventSaved object for persistence
        EventSaved eventSaved = new EventSaved();
        eventSaved.setAggregateRootId(domainEvent.getAggregateRootId());
        eventSaved.setType(domainEvent.getClass().getTypeName());
        eventSaved.setOccurredOn(domainEvent.getOccurredOn());
        eventSaved.setBody(EventSaved.wrapEvent(domainEvent, eventSerializer));

        // Save the EventSaved object and return the deserialized domain event
        return repository.save(eventSaved)
                .map(newEventSaved -> {
                    DomainEvent deserializedDomainEvent = newEventSaved.deserializeEvent(eventSerializer);
                    if (deserializedDomainEvent == null) {
                        logger.error("Deserialized event is null for eventSaved: {}", newEventSaved);
                        throw new RuntimeException("Deserialized event is null");
                    }
                    logger.info("Event saved and deserialized: {}", deserializedDomainEvent);
                    return deserializedDomainEvent;
                });
    }
}


//@Component
//public class MongoRepositoryAdapter implements IUserRepository, ITextRepository {
//
//    private static final Logger logger = LoggerFactory.getLogger(MongoRepositoryAdapter.class);
//
//    private final IMongoRepository repository;
//    private final JSONMapper eventSerializer;
//
//    private final ReactiveMongoTemplate template;
//
//    public MongoRepositoryAdapter(IMongoRepository repository, JSONMapper eventSerializer, ReactiveMongoTemplate template) {
//        this.repository = repository;
//        this.eventSerializer = eventSerializer;
//        this.template = template;
//    }
//
////    @Override
////    public Flux<List<DomainEvent>> getEventByAggregateRootId(String aggregateRootId) {
////        return template.find(new Query(Criteria.where("type").is(aggregateRootId)), EventSaved.class)
////                .sort(Comparator.comparing(EventSaved::getWhen))
////                .map(storedEvent -> storedEvent.deserializeEvent(eventSerializer))
////                .collectList()
////                .flux();
////    }
//
//    @Override
//    public Flux<List<DomainEvent>>  getEventByAggregateRootId(String aggregateId) {
//        return template.find(new Query(Criteria.where("type").is("com.reactive.user.events.UserCreated")), EventSaved.class)
//                .sort(Comparator.comparing(EventSaved::getOccurredOn))
//                .flatMap(storedEvent -> {
//                    DomainEvent event = storedEvent.deserializeEvent(eventSerializer);
//                    if (event != null && aggregateId.equals(event.getAggregateRootId())) {
//                        return Flux.just(List.of(event));
//                    }
//                    return Flux.empty();
//                });
//    }
//
//    @Override
//    public Mono<User> findByEmail(String email) {
//        return null;
//    }
//
//
//    // DO NOT TOUCH
//    @Override
//    public Flux<DomainEvent> findById(String aggregateId) {
//        return template.find(new Query(Criteria.where("type").is("com.reactive.text.events.TextCreated")), EventSaved.class)
//                .sort(Comparator.comparing(EventSaved::getOccurredOn))
//                .flatMap(storedEvent -> {
//                    DomainEvent event = storedEvent.deserializeEvent(eventSerializer);
//                    if (event != null && event.aggregateRootId.equals(event.getAggregateRootId())) {
//                        return Flux.just(event);
//                    }
//                    return Flux.empty();
//                });
//    }
//
//
//
//
////    com.reactive.text.events.TextCreated"
//
//    @Override
//    public Flux<List<DomainEvent>> getEventsByType(String eventType) {
//        return template.find(new Query(Criteria.where("type").is(eventType)), EventSaved.class)
//                .sort(Comparator.comparing(EventSaved::getOccurredOn))
//                .map(storedEvent -> storedEvent.deserializeEvent(eventSerializer))
//                .collectList()
//                .flux();
//    }
//
//    @Override
//    public Mono<DomainEvent> saveEvent(DomainEvent domainEvent) {
//        logger.info("Saving event: {}", domainEvent);
//        EventSaved eventSaved = new EventSaved();
//        eventSaved.setAggregateRootId(domainEvent.getAggregateRootId());
//        eventSaved.setType(domainEvent.getClass().getTypeName());
//        eventSaved.setOccurredOn(domainEvent.getOccurredOn());
//        eventSaved.setBody(EventSaved.wrapEvent(domainEvent, eventSerializer));
//
//        return repository.save(eventSaved)
//                .map(newEventSaved -> {
//                    DomainEvent deserializedDomainEvent = newEventSaved.deserializeEvent(eventSerializer);
//                    if (deserializedDomainEvent == null) {
//                        logger.error("Deserialized event is null for eventSaved: {}", newEventSaved);
//                        throw new RuntimeException("Deserialized event is null");
//                    }
//                    logger.info("Event saved and deserialized: {}", deserializedDomainEvent);
//                    return deserializedDomainEvent;
//                });
//
//    }
//
//}