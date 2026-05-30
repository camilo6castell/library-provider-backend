package com.libraryproviderbackend.usecase;

import com.libraryproviderbackend.generic.DomainEvent;
import com.libraryproviderbackend.text.Text;
import com.libraryproviderbackend.text.commands.CreateTextCommand;
import com.libraryproviderbackend.text.events.TextCreated;
import com.libraryproviderbackend.text.values.*;
import com.libraryproviderbackend.usecase.generic.UseCaseForCommandMono;
import com.libraryproviderbackend.usecase.generic.gateway.ITextRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

/**
 * Creates a new text entry and persists it, or returns the existing one if a text with
 * the same title already exists (idempotent creation).
 */
@Component
public class CreateTextUseCase extends UseCaseForCommandMono<CreateTextCommand> {

    private static final Logger log = LoggerFactory.getLogger(CreateTextUseCase.class);

    private final ITextRepository textRepository;

    public CreateTextUseCase(ITextRepository textRepository) {
        this.textRepository = textRepository;
    }

    @Override
    public Mono<DomainEvent> apply(Mono<CreateTextCommand> commandMono) {
        return commandMono
                .switchIfEmpty(Mono.error(new IllegalArgumentException("CreateTextCommand must not be null")))
                .flatMap(command ->
                        textRepository.findByTitle(command.getTitle())
                                .doOnNext(existing -> log.debug("Text with title '{}' already exists, returning existing event.", command.getTitle()))
                                .cast(DomainEvent.class)
                                .switchIfEmpty(Mono.defer(() -> createAndPersist(command)))
                );
    }

    private Mono<DomainEvent> createAndPersist(CreateTextCommand command) {
        Text text = new Text(
                TextId.of(UUID.randomUUID().toString()),
                Title.of(command.getTitle()),
                Type.of(TextTypeEnum.valueOf(command.getTextType())),
                InitialPrice.of(command.getInitialPrice())
        );

        List<DomainEvent> events = text.getUncommittedChanges();

        return Flux.fromIterable(events)
                .flatMap(textRepository::saveEvent)
                .next()
                .switchIfEmpty(Mono.error(new IllegalStateException("No events generated for text creation")));
    }
}
