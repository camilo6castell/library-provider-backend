package com.libraryproviderbackend.usecase.generic;

import com.libraryproviderbackend.generic.Command;
import com.libraryproviderbackend.generic.DomainEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public abstract class UseCaseForCommandMono <R extends Command> implements Function<Mono<R>, Mono<DomainEvent>> {
    public abstract Mono<DomainEvent> apply(Mono<R> rMono);
}
