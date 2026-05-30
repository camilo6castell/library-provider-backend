package com.libraryproviderbackend;

import com.libraryproviderbackend.config.IMongoRepository;
import com.libraryproviderbackend.data.EventSaved;
import com.libraryproviderbackend.generic.DomainEvent;
import com.libraryproviderbackend.text.events.TextCreated;
import com.libraryproviderbackend.usecase.generic.gateway.ITextRepository;
import com.libraryproviderbackend.usecase.generic.gateway.IUserRepository;
import com.libraryproviderbackend.user.events.UserCreated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * MongoDB adapter implementing both {@link IUserRepository} and {@link ITextRepository}.
 * Persists and retrieves domain events using the Event Sourcing pattern.
 */
@Component
public class MongoRepositoryAdapter implements IUserRepository, ITextRepository {

    private static final Logger log = LoggerFactory.getLogger(MongoRepositoryAdapter.class);

    private static final String USER_CREATED_TYPE  = "com.libraryproviderbackend.user.events.UserCreated";
    private static final String TEXT_CREATED_TYPE  = "com.libraryproviderbackend.text.events.TextCreated";

    private final IMongoRepository repository;
    private final JSONMapper eventSerializer;

    public MongoRepositoryAdapter(IMongoRepository repository, JSONMapper eventSerializer) {
        this.repository = repository;
        this.eventSerializer = eventSerializer;
    }

    @Override
    public Flux<DomainEvent> getEventsByType(String eventType) {
        return repository.findByType(eventType)
                .map(stored -> stored.deserializeEvent(eventSerializer));
    }

    @Override
    public Flux<DomainEvent> getEventsByAggregateRootId(String aggregateRootId) {
        return repository.findByAggregateRootId(aggregateRootId)
                .map(stored -> stored.deserializeEvent(eventSerializer));
    }

    @Override
    public Mono<UserCreated> findByEmail(String email) {
        return getEventsByType(USER_CREATED_TYPE)
                .cast(UserCreated.class)
                .filter(event -> event.getEmail().equalsIgnoreCase(email))
                .next();
    }

    @Override
    public Mono<TextCreated> findByTitle(String title) {
        return getEventsByType(TEXT_CREATED_TYPE)
                .cast(TextCreated.class)
                .filter(event -> event.getTitle().equalsIgnoreCase(title))
                .next();
    }

    @Override
    public Mono<DomainEvent> saveEvent(DomainEvent domainEvent) {
        log.debug("Saving event: type='{}', aggregateRootId='{}'",
                domainEvent.getType(), domainEvent.getAggregateRootId());

        EventSaved eventSaved = new EventSaved(
                domainEvent.getAggregateRootId(),
                domainEvent.getClass().getName(),
                domainEvent.getOccurredOn(),
                EventSaved.wrapEvent(domainEvent, eventSerializer)
        );

        return repository.save(eventSaved)
                .map(saved -> {
                    DomainEvent deserialized = saved.deserializeEvent(eventSerializer);
                    log.debug("Event persisted with id='{}'", saved.getId());
                    return deserialized;
                });
    }
}
