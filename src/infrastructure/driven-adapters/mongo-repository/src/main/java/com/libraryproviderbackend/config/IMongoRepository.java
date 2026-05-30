package com.libraryproviderbackend.config;

import com.libraryproviderbackend.data.EventSaved;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Spring Data reactive repository for {@link EventSaved} documents.
 * Custom query methods leverage Spring Data's derived query mechanism.
 */
@Repository
public interface IMongoRepository extends ReactiveMongoRepository<EventSaved, String> {

    Flux<EventSaved> findByAggregateRootId(String aggregateRootId);

    Flux<EventSaved> findByType(String type);
}
