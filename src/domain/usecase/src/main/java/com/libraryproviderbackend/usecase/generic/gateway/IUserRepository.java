package com.libraryproviderbackend.usecase.generic.gateway;

import com.libraryproviderbackend.generic.DomainEvent;
import com.libraryproviderbackend.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


public interface IUserRepository {
    Flux<DomainEvent> getEventsByAggregateRootId(String aggregateRootId);

    Mono<User> findByEmail(String email);

    Flux<DomainEvent> getEventsByType(String eventType);

//    Flux<List<DomainEvent>> getEventsByType(String eventType);

    Mono<DomainEvent>  saveEvent(DomainEvent domainEvent);

}
