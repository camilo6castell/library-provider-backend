package com.reactive.usecase.generic.gateway;

import com.reactive.generic.DomainEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


public interface ITextRepository {

    Flux<DomainEvent> findById(String aggregateId);

    Flux<List<DomainEvent>> getEventsByType(String eventType);

    Mono<DomainEvent> saveEvent(DomainEvent domainEvent);

}
