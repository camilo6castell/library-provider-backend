package com.libraryproviderbackend;

import com.libraryproviderbackend.config.IMongoRepository;
import com.libraryproviderbackend.data.EventSaved;
import com.libraryproviderbackend.usecase.generic.gateway.ITextRepository;
import com.libraryproviderbackend.usecase.generic.gateway.IUserRepository;

import com.libraryproviderbackend.generic.DomainEvent;
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


@Component
public class MongoRepositoryAdapter implements IUserRepository, ITextRepository {

    private static final Logger logger = LoggerFactory.getLogger(MongoRepositoryAdapter.class);

    private final IMongoRepository repository;
    private final JSONMapper eventSerializer;

    private final ReactiveMongoTemplate template;

    public MongoRepositoryAdapter(IMongoRepository repository, JSONMapper eventSerializer, ReactiveMongoTemplate template) {
        this.repository = repository;
        this.eventSerializer = eventSerializer;
        this.template = template;
    }

//    @Override
//    public Flux<List<DomainEvent>> getEventByAggregateRootId(String aggregateRootId) {
//        return template.find(new Query(Criteria.where("type").is(aggregateRootId)), EventSaved.class)
//                .sort(Comparator.comparing(EventSaved::getWhen))
//                .map(storedEvent -> storedEvent.deserializeEvent(eventSerializer))
//                .collectList()
//                .flux();
//    }

    @Override
    public Flux<List<DomainEvent>>  getEventByAggregateRootId(String aggregateId) {
        return template.find(new Query(Criteria.where("type").is("com.reactive.user.events.UserCreated")), EventSaved.class)
                .sort(Comparator.comparing(EventSaved::getWhen))
                .flatMap(storedEvent -> {
                    DomainEvent event = storedEvent.deserializeEvent(eventSerializer);
                    if (event != null && aggregateId.equals(event.getAggregateRootId())) {
                        return Flux.just(List.of(event));
                    }
                    return Flux.empty();
                });
    }




    // DO NOT TOUCH
    @Override
    public Flux<DomainEvent> findById(String aggregateId) {
        return template.find(new Query(Criteria.where("type").is("com.reactive.text.events.TextCreated")), EventSaved.class)
                .sort(Comparator.comparing(EventSaved::getWhen))
                .flatMap(storedEvent -> {
                    DomainEvent event = storedEvent.deserializeEvent(eventSerializer);
                    if (event != null && event.aggregateRootId.equals(event.getAggregateRootId())) {
                        return Flux.just(event);
                    }
                    return Flux.empty();
                });
    }




//    com.reactive.text.events.TextCreated"

    @Override
    public Flux<List<DomainEvent>> getEventsByType(String eventType) {
        return template.find(new Query(Criteria.where("type").is(eventType)), EventSaved.class)
                .sort(Comparator.comparing(EventSaved::getWhen))
                .map(storedEvent -> storedEvent.deserializeEvent(eventSerializer))
                .collectList()
                .flux();
    }

    @Override
    public Mono<DomainEvent> saveEvent(DomainEvent domainEvent) {
        logger.info("Saving event: {}", domainEvent);
        EventSaved eventSaved = new EventSaved();
        eventSaved.setAggregateRootId(domainEvent.getAggregateRootId());
        eventSaved.setType(domainEvent.getClass().getTypeName());
        eventSaved.setWhen(domainEvent.getWhen());
        eventSaved.setBody(EventSaved.wrapEvent(domainEvent, eventSerializer));

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