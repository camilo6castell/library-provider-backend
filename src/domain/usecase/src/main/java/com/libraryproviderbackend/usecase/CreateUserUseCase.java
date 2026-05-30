package com.libraryproviderbackend.usecase;

import com.libraryproviderbackend.generic.DomainEvent;
import com.libraryproviderbackend.usecase.generic.UseCaseForCommandMono;
import com.libraryproviderbackend.usecase.generic.gateway.IUserRepository;
import com.libraryproviderbackend.user.User;
import com.libraryproviderbackend.user.commands.CreateUserCommand;
import com.libraryproviderbackend.user.values.identities.UserId;
import com.libraryproviderbackend.user.values.user.Email;
import com.libraryproviderbackend.user.values.user.EntryDate;
import com.libraryproviderbackend.user.values.user.Password;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

/**
 * Creates a new user and persists the resulting domain events.
 * Validates that no user with the same email already exists.
 *
 * <p><strong>Security note:</strong> In a production system the password should be hashed
 * (e.g. BCrypt) before being stored in the domain event. This use case expects the
 * command to carry either a raw password (validated by the {@link Password} value object)
 * or a pre-hashed value passed from the presentation layer.
 */
@Component
public class CreateUserUseCase extends UseCaseForCommandMono<CreateUserCommand> {

    private final IUserRepository repository;

    public CreateUserUseCase(IUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<DomainEvent> apply(Mono<CreateUserCommand> commandMono) {
        return commandMono
                .switchIfEmpty(Mono.error(new IllegalArgumentException("CreateUserCommand must not be null")))
                .flatMap(command ->
                        repository.findByEmail(command.getEmail())
                                .flatMap(existing -> Mono.<CreateUserCommand>error(
                                        new IllegalArgumentException("A user with email '" + command.getEmail() + "' already exists")))
                                .switchIfEmpty(Mono.just(command))
                )
                .flatMap(command -> {
                    User user = new User(
                            UserId.of(UUID.randomUUID().toString()),
                            Email.of(command.getEmail()),
                            Password.of(command.getPassword()),
                            EntryDate.of(command.getEntryDate())
                    );

                    List<DomainEvent> events = user.getUncommittedChanges();

                    return Flux.fromIterable(events)
                            .flatMap(repository::saveEvent)
                            .next()
                            .switchIfEmpty(Mono.error(new IllegalStateException("No events generated for user creation")));
                });
    }
}
