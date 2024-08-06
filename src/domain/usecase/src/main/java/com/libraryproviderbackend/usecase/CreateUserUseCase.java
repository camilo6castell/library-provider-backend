package com.libraryproviderbackend.usecase;

import com.libraryproviderbackend.generic.DomainEvent;
import com.libraryproviderbackend.usecase.generic.UseCaseForCommand;
import com.libraryproviderbackend.usecase.generic.gateway.IUserRepository;
import com.libraryproviderbackend.user.User;
import com.libraryproviderbackend.user.commands.CreateUserCommand;
import com.libraryproviderbackend.user.values.identities.UserId;
import com.libraryproviderbackend.user.values.user.Email;
import com.libraryproviderbackend.user.values.user.EntryDate;
import com.libraryproviderbackend.user.values.user.Password;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class CreateUserUseCase extends UseCaseForCommand<CreateUserCommand> {
    private final IUserRepository repository;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public CreateUserUseCase(IUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<CreateUserCommand> createUserCommandMono) {
        return createUserCommandMono.flatMapMany(command -> {
            if (command == null) {
                return Flux.error(new IllegalArgumentException("CreateUserCommand must not be null"));
            };

            User user = new User(
                    UserId.of(UUID.randomUUID().toString()),
                    Email.of(command.email),
                    Password.of(command.password),
                    EntryDate.of(LocalDate.parse(command.entryDate, dateTimeFormatter)
            ));
            return Flux.fromIterable(user.getUncommittedChanges())
                    .flatMap(event -> repository.saveEvent(event)
                            .switchIfEmpty(Mono.error(new IllegalStateException("Failed to save event"))));
        });
    }
}
