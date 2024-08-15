package com.libraryproviderbackend.usecase.generic.gateway;

import com.libraryproviderbackend.generic.DomainEvent;
import com.libraryproviderbackend.text.events.TextCreated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


public interface ITextRepository {

    Flux<DomainEvent> getEventsByAggregateRootId(String aggregateRootId);

    Flux<DomainEvent> getEventsByType(String eventType);

    Mono<DomainEvent> saveEvent(DomainEvent domainEvent);

    // particular

    Mono<TextCreated> findByTitle(String title);

}
