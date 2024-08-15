package com.libraryproviderbackend.usecase.generic.gateway;

import com.libraryproviderbackend.generic.DomainEvent;
import com.libraryproviderbackend.user.User;
import com.libraryproviderbackend.user.events.UserCreated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


public interface IUserRepository {
    Flux<DomainEvent> getEventsByAggregateRootId(String aggregateRootId);

    Flux<DomainEvent> getEventsByType(String eventType);

    Mono<DomainEvent>  saveEvent(DomainEvent domainEvent);

    // particular

    Mono<UserCreated> findByEmail(String email);

}
