package com.libraryproviderbackend.usecase;

import com.libraryproviderbackend.generic.DomainEvent;
import com.libraryproviderbackend.text.commands.CreateTextCommand;
import com.libraryproviderbackend.text.events.TextCreated;
import com.libraryproviderbackend.text.values.TextTypeEnum;
import com.libraryproviderbackend.usecase.generic.UseCaseForCommandMono;
import com.libraryproviderbackend.usecase.generic.gateway.ITextRepository;
import com.libraryproviderbackend.usecase.generic.gateway.IUserRepository;
import com.libraryproviderbackend.user.User;
import com.libraryproviderbackend.user.commands.SaveAndQuoteTextCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

/**
 * Saves a new text and quotes it for a given user.
 * Orchestrates {@link CreateTextUseCase} and user event-sourcing reconstruction.
 */
@Component
public class SaveAndQuoteTextUseCase extends UseCaseForCommandMono<SaveAndQuoteTextCommand> {

    private static final Logger log = LoggerFactory.getLogger(SaveAndQuoteTextUseCase.class);

    private final ITextRepository textRepository;
    private final IUserRepository userRepository;
    private final CreateTextUseCase createTextUseCase;

    public SaveAndQuoteTextUseCase(
            ITextRepository textRepository,
            IUserRepository userRepository,
            CreateTextUseCase createTextUseCase) {
        this.textRepository = textRepository;
        this.userRepository = userRepository;
        this.createTextUseCase = createTextUseCase;
    }

    @Override
    public Mono<DomainEvent> apply(Mono<SaveAndQuoteTextCommand> commandMono) {
        return commandMono
                .switchIfEmpty(Mono.error(new IllegalArgumentException("SaveAndQuoteTextCommand must not be null")))
                .flatMap(command ->
                        // Step 1: ensure text exists (idempotent)
                        createTextUseCase.apply(Mono.just(new CreateTextCommand(
                                        command.getTitle(),
                                        command.getTextType(),
                                        command.getInitialPrice())))
                                .cast(TextCreated.class)
                                .flatMap(textCreated ->
                                        // Step 2: rebuild user from event store
                                        userRepository.getEventsByAggregateRootId(command.getUserIdRaw())
                                                .collect(Collectors.toList())
                                                .flatMap(events -> {
                                                    if (events.isEmpty()) {
                                                        return Mono.error(new IllegalArgumentException(
                                                                "User not found: " + command.getUserIdRaw()));
                                                    }

                                                    User user = User.from(command.getUserIdRaw(), events);

                                                    // Step 3: apply domain behaviour
                                                    user.quoteText(
                                                            textCreated.getTitle(),
                                                            textCreated.getInitialPrice(),
                                                            TextTypeEnum.valueOf(textCreated.getTextType().toString()),
                                                            user.getEntryDate().value()
                                                    );

                                                    // Step 4: persist new user events
                                                    return Flux.fromIterable(user.getUncommittedChanges())
                                                            .flatMap(userRepository::saveEvent)
                                                            .next()
                                                            .switchIfEmpty(Mono.error(new IllegalStateException(
                                                                    "No events generated after quoting text")));
                                                })
                                )
                );
    }
}
