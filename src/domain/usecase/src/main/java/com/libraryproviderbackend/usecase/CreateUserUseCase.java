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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class CreateUserUseCase extends UseCaseForCommandMono<CreateUserCommand> {
    private final IUserRepository repository;


    public CreateUserUseCase(IUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<DomainEvent> apply(Mono<CreateUserCommand> createUserCommandMono) {
        return createUserCommandMono
                .flatMap(command -> {

                    User user = new User(
                            UserId.of(UUID.randomUUID().toString()),
                            Email.of(command.email),
                            Password.of(command.password),
                            EntryDate.of(command.entryDate)
                    );

                    // Asumiendo que solo te interesa el primer evento, puedes hacer esto:
                    return Flux.fromIterable(user.getUncommittedChanges())
                            .flatMap(event -> repository.saveEvent(event)
                                    .thenReturn(event)
                                    .switchIfEmpty(Mono.error(new IllegalStateException("Failed to save event"))))
                            .next();  // 'next()' toma solo el primer elemento del Flux y lo convierte a Mono
                });
    }
}
