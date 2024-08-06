package com.libraryproviderbackend.config;

import com.libraryproviderbackend.data.EventSaved;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface IMongoRepository extends ReactiveMongoRepository<EventSaved, String> {

    Mono<EventSaved> getEventByAggregateRootId(String aggregateRootId);

    Flux<List<EventSaved>> getEventsByType(String eventType);

}
