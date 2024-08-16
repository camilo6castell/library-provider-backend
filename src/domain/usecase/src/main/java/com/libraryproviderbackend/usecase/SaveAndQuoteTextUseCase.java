package com.libraryproviderbackend.usecase;

import com.libraryproviderbackend.generic.DomainEvent;
import com.libraryproviderbackend.text.commands.CreateTextCommand;
import com.libraryproviderbackend.text.events.TextCreated;
import com.libraryproviderbackend.text.values.*;
import com.libraryproviderbackend.usecase.generic.UseCaseForCommandMono;
import com.libraryproviderbackend.usecase.generic.gateway.ITextRepository;
import com.libraryproviderbackend.usecase.generic.gateway.IUserRepository;
import com.libraryproviderbackend.user.User;
import com.libraryproviderbackend.user.commands.SaveAndQuoteTextCommand;
import com.libraryproviderbackend.user.entity.TextQuote;
import com.libraryproviderbackend.user.events.TextQuoted;
import com.libraryproviderbackend.user.values.shared.DiscountsEnum;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Component
public class SaveAndQuoteTextUseCase extends UseCaseForCommandMono<SaveAndQuoteTextCommand> {

    private final ITextRepository textRepository;
    private final IUserRepository userRepository;
    private final CreateTextUseCase createTextUseCase;

    public SaveAndQuoteTextUseCase(ITextRepository textRepository, IUserRepository userRepository, CreateTextUseCase createTextUseCase) {
        this.textRepository = textRepository;
        this.userRepository = userRepository;
        this.createTextUseCase = createTextUseCase;
    }

    @Override
    public Mono<DomainEvent> apply(Mono<SaveAndQuoteTextCommand> saveAndQuoteTextCommandMono) {
        return saveAndQuoteTextCommandMono
                .flatMap(command -> {
                    // Step 1: Create or retrieve the Text
                    return createTextUseCase.apply(Mono.just(new CreateTextCommand(
                                    command.getTitle(),
                                    command.getTextType(),
                                    command.getInitialPrice()
                            )))
                            .cast(TextCreated.class)
                            .flatMap(textCreated -> {
                                // Step 2: Retrieve the User and quote the Text
                                return userRepository.getEventsByAggregateRootId(command.getUserId().value())
                                        .collect(Collectors.toList())
                                        .flatMap(events -> {
                                            User user = User.from(command.getUserId().value(), events);
                                            // Perform the quoting
                                            user.quoteText(
                                                    textCreated.getTitle(),
                                                    textCreated.getInitialPrice(),
                                                    TextTypeEnum.valueOf(textCreated.getTextType().toString()),
                                                    user.entryDate.value()
                                            );

                                            return Flux.fromIterable(user.getUncommittedChanges())
                                                    .flatMap(userRepository::saveEvent)
                                                    .collectList()
                                                    .map(list -> (TextQuoted) list.get(0)); // Devuelve el primer evento de la lista

                                            // Optionally save user changes or handle further logic
//                                            return Mono.just(textQuote)
//                                                    .map(userEvents -> {
//                                                        TextQuoted textQuoted = new TextQuoted();
//                                                        textQuoted.setTitle(textQuote.getTitle().value());
//                                                        textQuoted.setType(textQuote.getType().value().toString());
//                                                        textQuoted.setDiscount(DiscountsEnum.NONE.toString());
//                                                        textQuoted.setSubtotal(textQuote.getSubtotal().value().floatValue());
//                                                        textQuoted.setTotal(textQuote.getTotal().value().floatValue());
//                                                        return textQuoted;
//                                                    });
                                        });
                            });
                });
    }
}


//@Component
//public class SaveAndQuoteTextUseCase extends UseCaseForCommandMono<SaveAndQuoteTextCommand> {
//
//    TextTypeEnum[] textTypeEnumValues = TextTypeEnum.values();
//    UserEventsEnum[] userEventsEnums = UserEventsEnum.values();
//
//    private final IUserRepository userRepository;
//    private final ITextRepository textRepository;
//    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//    public SaveAndQuoteTextUseCase(
//            IUserRepository userRepository,
//            ITextRepository textRepository
//    ) {
//        this.userRepository = userRepository;
//        this.textRepository = textRepository;
//    }
//
//    @Override
//    public Mono<DomainEvent> apply(Mono<SaveAndQuoteTextCommand> saveAndQuoteTextCommandMono) {
//        return saveAndQuoteTextCommandMono
//                .switchIfEmpty(Mono.error(new IllegalArgumentException("saveAndQuoteTextCommand must not be null")))
//                .flatMapMany(command ->
//                        userRepository.getEventsByAggregateRootId(command.userId.value())
//
//                                .flatMap(eventRepo -> {
//
//
//                                    User user = User.from(command.userId.value(), eventRepo.getFirst());
//
//                                    Text text = new Text(
//                                            new TextId(),
//                                            Title.of(command.title),
//                                            Type.of(textTypeEnumValues[command.type]),
//                                            InitialPrice.of(command.initialPrice)
//                                    );
//
//                                    // Realiza la cotización del texto
//                                    TextQuote quoteResult = user.quoteText(
//                                            text.getTitle().value(),
//                                            text.getInitialPrice().value(),
//                                            text.getType().value(),
//                                            DiscountsEnum.NONE
//                                    );
//                                    TextQuoteMade textQuoteMade = new TextQuoteMade();
//
//                                    textQuoteMade.title = quoteResult.title.value();
//                                    textQuoteMade.type = quoteResult.type.value().toString();
//                                    textQuoteMade.discount = quoteResult.discount.value().toString();
//                                    textQuoteMade.subtotal = quoteResult.subtotal.value();
//                                    textQuoteMade.total = quoteResult.total.value();
//
//
//                                    // Guarda los cambios no confirmados del texto
//                                    Flux<DomainEvent> textEvents = Flux.fromIterable(text.getUncommittedChanges())
//                                            .flatMap(event -> textRepository.saveEvent(event)
//                                                    .switchIfEmpty(Mono.error(new IllegalStateException("Failed to save textEvent")))
//                                                    .thenReturn(event));  // Devuelve el evento guardado
//
//                                    // Agrega el texto al usuario o realiza alguna operación con el usuario
////                                    user.addText(text);
//
//                                    // Guarda los cambios no confirmados del usuario
//                                    Flux<DomainEvent> userEvents = Flux.fromIterable(user.getUncommittedChanges())
//                                            .flatMap(event -> userRepository.saveEvent(event)
//                                                    .switchIfEmpty(Mono.error(new IllegalStateException("Failed to save user event")))
//                                                    .thenReturn(event));  // Devuelve el evento guardado
//
//                                    // Combina ambos flujos de eventos y devuelve el resultado de la cotización
//                                    return Flux.concat(textEvents, userEvents)
//                                            .thenMany(Flux.just(textQuoteMade));
//                                })
//                );
//
//
//    }
//}





