package com.libraryproviderbackend.usecase;

import com.libraryproviderbackend.generic.DomainEvent;
import com.libraryproviderbackend.text.Text;
import com.libraryproviderbackend.text.values.*;
import com.libraryproviderbackend.usecase.generic.UseCaseForCommand;
import com.libraryproviderbackend.usecase.generic.gateway.ITextRepository;
import com.libraryproviderbackend.usecase.generic.gateway.IUserRepository;
import com.libraryproviderbackend.user.User;
import com.libraryproviderbackend.user.commands.SaveAndQuoteTextCommand;
import com.libraryproviderbackend.user.entity.TextQuote;
import com.libraryproviderbackend.user.events.TextQuoteMade;
import com.libraryproviderbackend.user.events.UserEventsEnum;
import com.libraryproviderbackend.user.values.shared.DiscountsEnum;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.format.DateTimeFormatter;

public class SaveAndQuoteTextUseCase extends UseCaseForCommand<SaveAndQuoteTextCommand> {

    TextTypeEnum[] textTypeEnumValues = TextTypeEnum.values();
    UserEventsEnum[] userEventsEnums = UserEventsEnum.values();

    private final IUserRepository userRepository;
    private final ITextRepository textRepository;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public SaveAndQuoteTextUseCase(
            IUserRepository userRepository,
            ITextRepository textRepository
    ) {
        this.userRepository = userRepository;
        this.textRepository = textRepository;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<SaveAndQuoteTextCommand> saveAndQuoteTextCommandMono) {
        return saveAndQuoteTextCommandMono
                .switchIfEmpty(Mono.error(new IllegalArgumentException("saveAndQuoteTextCommand must not be null")))
                .flatMapMany(command ->
                        userRepository.getEventByAggregateRootId(command.userId.value())

                                .flatMap(eventRepo -> {


                                    User user = User.from(command.userId.value(), eventRepo.getFirst());

                                    Text text = new Text(
                                            new TextId(),
                                            Title.of(command.title),
                                            Type.of(textTypeEnumValues[command.type]),
                                            InitialPrice.of(command.initialPrice)
                                    );

                                    // Realiza la cotización del texto
                                    TextQuote quoteResult = user.quoteText(
                                            text.getTitle().value(),
                                            text.getInitialPrice().value(),
                                            text.getType().value(),
                                            DiscountsEnum.NONE
                                    );
                                    TextQuoteMade textQuoteMade = new TextQuoteMade();

                                    textQuoteMade.title = quoteResult.title.value();
                                    textQuoteMade.type = quoteResult.type.value().toString();
                                    textQuoteMade.discount = quoteResult.discount.value().toString();
                                    textQuoteMade.subtotal = quoteResult.subtotal.value();
                                    textQuoteMade.total = quoteResult.total.value();


                                    // Guarda los cambios no confirmados del texto
                                    Flux<DomainEvent> textEvents = Flux.fromIterable(text.getUncommittedChanges())
                                            .flatMap(event -> textRepository.saveEvent(event)
                                                    .switchIfEmpty(Mono.error(new IllegalStateException("Failed to save textEvent")))
                                                    .thenReturn(event));  // Devuelve el evento guardado

                                    // Agrega el texto al usuario o realiza alguna operación con el usuario
//                                    user.addText(text);

                                    // Guarda los cambios no confirmados del usuario
                                    Flux<DomainEvent> userEvents = Flux.fromIterable(user.getUncommittedChanges())
                                            .flatMap(event -> userRepository.saveEvent(event)
                                                    .switchIfEmpty(Mono.error(new IllegalStateException("Failed to save user event")))
                                                    .thenReturn(event));  // Devuelve el evento guardado

                                    // Combina ambos flujos de eventos y devuelve el resultado de la cotización
                                    return Flux.concat(textEvents, userEvents)
                                            .thenMany(Flux.just(textQuoteMade));
                                })
                );


    }
}





