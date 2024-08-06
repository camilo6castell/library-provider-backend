package com.libraryproviderbackend.usecase;

import com.libraryproviderbackend.generic.DomainEvent;
import com.libraryproviderbackend.text.Text;
import com.libraryproviderbackend.text.commands.CreateTextCommand;
import com.libraryproviderbackend.text.values.*;
import com.libraryproviderbackend.usecase.generic.UseCaseForCommand;
import com.libraryproviderbackend.usecase.generic.gateway.ITextRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public class CreateTextUseCase extends UseCaseForCommand<CreateTextCommand> {
    private final ITextRepository repository;
    public CreateTextUseCase(ITextRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<CreateTextCommand> CreateTextCommandMono) {
        return CreateTextCommandMono.flatMapMany(command -> {
            if (command == null) {
                return Flux.error(new IllegalArgumentException("CreateUserCommand must not be null"));
            };

            TextTypeEnum[] textTypeEnumValues = TextTypeEnum.values();
            Text text = new Text(
                    TextId.of(UUID.randomUUID().toString()),
                    Title.of(command.title),
                    Type.of(textTypeEnumValues[command.type]),
                    InitialPrice.of(command.initialPrice)
            );
            return Flux.fromIterable(text.getUncommittedChanges())
                    .flatMap(event -> repository.saveEvent(event)
                            .switchIfEmpty(Mono.error(new IllegalStateException("Failed to save event")))
                    );
        });
    }
}
