package com.libraryproviderbackend.usecase;

import com.libraryproviderbackend.generic.DomainEvent;
import com.libraryproviderbackend.text.Text;
import com.libraryproviderbackend.text.commands.CreateTextCommand;
import com.libraryproviderbackend.text.events.TextCreated;
import com.libraryproviderbackend.text.values.*;
import com.libraryproviderbackend.usecase.generic.UseCaseForCommandFlux;
import com.libraryproviderbackend.usecase.generic.UseCaseForCommandMono;
import com.libraryproviderbackend.usecase.generic.gateway.ITextRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Component
public class CreateTextUseCase extends UseCaseForCommandMono<CreateTextCommand> {

    private final ITextRepository textRepository;

    public CreateTextUseCase(ITextRepository textRepository) {
        this.textRepository = textRepository;
    }

    @Override
    public Mono<DomainEvent> apply(Mono<CreateTextCommand> createTextCommandMono) {
        return createTextCommandMono
                .flatMap(command ->
                        textRepository.findByTitle(command.getTitle())
                                .flatMap(existingTextCreated -> {
                                    System.out.println("Existing Text Created: " + existingTextCreated);
                                    // Si existe un Text con el mismo título, devolver el evento existente
                                    return Mono.just(existingTextCreated);
                                })
                                .switchIfEmpty(Mono.defer(() -> {
                                    // Si no existe, crear un nuevo Text
                                    Text text = new Text(
                                            TextId.of(UUID.randomUUID().toString()),
                                            Title.of(command.getTitle()),
                                            Type.of(TextTypeEnum.valueOf(command.getTextType())),
                                            InitialPrice.of(command.getInitialPrice())
                                    );

                                    List<DomainEvent> events = text.getUncommittedChanges();

                                    // Guardar en la base de datos y devolver el evento TextCreated
                                    return Flux.fromIterable(events)
                                            .flatMap(textRepository::saveEvent)
                                            .collectList()
                                            .map(list -> (TextCreated) list.get(0)); // Devuelve el primer evento de la lista
                                }))
                );
    }
}


//@Component
//public class CreateTextUseCase extends UseCaseForCommandFlux<CreateTextCommand> {
//    private final ITextRepository repository;
//    public CreateTextUseCase(ITextRepository repository) {
//        this.repository = repository;
//    }
//
//    @Override
//    public Flux<DomainEvent> apply(Mono<CreateTextCommand> CreateTextCommandMono) {
//        return CreateTextCommandMono.flatMapMany(command -> {
//            if (command == null) {
//                return Flux.error(new IllegalArgumentException("CreateUserCommand must not be null"));
//            };
//
//            TextTypeEnum[] textTypeEnumValues = TextTypeEnum.values();
//            Text text = new Text(
//                    TextId.of(UUID.randomUUID().toString()),
//                    Title.of(command.title),
//                    Type.of(textTypeEnumValues[command.type]),
//                    InitialPrice.of(command.initialPrice)
//            );
//            return Flux.fromIterable(text.getUncommittedChanges())
//                    .flatMap(event -> repository.saveEvent(event)
//                            .switchIfEmpty(Mono.error(new IllegalStateException("Failed to save event")))
//                    );
//        });
//    }
//}
