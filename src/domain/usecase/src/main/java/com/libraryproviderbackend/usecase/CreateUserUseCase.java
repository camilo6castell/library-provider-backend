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

@Component
public class CreateUserUseCase extends UseCaseForCommandMono<CreateUserCommand> {

    private final IUserRepository repository;

    public CreateUserUseCase(IUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<DomainEvent> apply(Mono<CreateUserCommand> createUserCommandMono) {
        return createUserCommandMono
                .flatMap(command ->
                        repository.findByEmail(command.email)
                                .flatMap(existingUser -> Mono.<CreateUserCommand>error(
                                        new IllegalArgumentException("Email already exists")))
                                .switchIfEmpty(Mono.just(command))  // Si no existe un usuario con ese email, continúa
                )
                .flatMap(command -> {
                    // Crear el usuario con los datos del comando
                    User user = new User(
                            UserId.of(UUID.randomUUID().toString()),  // Crear nuevo ID para el usuario
                            Email.of(command.email),
                            Password.of(command.password),
                            EntryDate.of(command.entryDate)
                    );

                    // Obtener los eventos generados por el usuario (Uncommitted Changes)
                    List<DomainEvent> events = user.getUncommittedChanges();

                    // Guardar cada evento en la base de datos
                    return Flux.fromIterable(events)
                            .flatMap(repository::saveEvent)
                            .collectList()
                            .map(List::getFirst);  // Devuelve el primer evento de la lista
                });
    }

}