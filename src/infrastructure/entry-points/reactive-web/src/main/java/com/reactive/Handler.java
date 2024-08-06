package com.reactive;

import com.reactive.Dtos.authentication.response.RegisterResponse;
//import com.reactive.Dtos.quote.response.BatchTextQuoteResponse;
import com.reactive.Dtos.quote.response.BudgetTextQuoteResponse;
import com.reactive.Dtos.quote.response.VariousTextQuoteResponse;
import com.reactive.usecase.*;
import com.reactive.user.commands.*;
import com.reactive.user.events.BatchTextsQuoted;
import com.reactive.user.events.BudgetTextsQuoted;
import com.reactive.user.values.batchquote.TextQuoteResponse;
import com.reactive.user.events.TextQuoteMade;
import com.reactive.user.events.VariousTextQuotedEvent;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


@Component
public class Handler {
    private final CreateUserUseCase createUseruseCase;
    private final SaveAndQuoteTextUseCase saveAndQuoteTextUseCase;
    private final QuoteVariousTextsUseCase quoteVariousTextsUseCase;
    private final QuoteTextsByBudgetUseCase quoteTextsByBudgetUseCase;
//    private final QuoteBatchQuoteUseCase quoteBatchQuoteUseCase;
    private final CreateTextUseCase createTextUseCase;

    public Handler(
            CreateUserUseCase createUseruseCase,
            SaveAndQuoteTextUseCase saveAndQuoteTextUseCase,
            QuoteVariousTextsUseCase quoteVariousTextsUseCase,
            QuoteTextsByBudgetUseCase quoteTextsByBudgetUseCase,
//            QuoteBatchQuoteUseCase quoteBatchQuoteUseCase,
            CreateTextUseCase createTextUseCase
    ) {
        this.createUseruseCase = createUseruseCase;
        this.saveAndQuoteTextUseCase = saveAndQuoteTextUseCase;
        this.quoteVariousTextsUseCase = quoteVariousTextsUseCase;
        this.quoteTextsByBudgetUseCase = quoteTextsByBudgetUseCase;
//        this.quoteBatchQuoteUseCase = quoteBatchQuoteUseCase;
        this.createTextUseCase = createTextUseCase;
    }

    public Mono<ServerResponse> listenPOSTCreateUserUseCase(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(CreateUserCommand.class)
                .flatMap(command -> createUseruseCase.apply(Mono.just(command))
                        .collectList()
                        .flatMap(events -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(
                                        BodyInserters.fromValue(
                                                new RegisterResponse(
                                                        true,
                                                        "User created"
                                                )
                                        )
                                )
                        )
                )
                .onErrorResume(e -> ServerResponse.badRequest()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(
                                new RegisterResponse(
                                        false,
                                        e.getMessage()
                                )
                        ))
                );
    }

    public Mono<ServerResponse> listenPOSTSaveAndQuoteTextUseCase(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(SaveAndQuoteTextCommand.class)
                .flatMap(command -> saveAndQuoteTextUseCase.apply(Mono.just(command))
                        .collectList()
                        .flatMap(events -> {
                            // Busca el evento TextQuotedEvent en la lista de eventos
                            TextQuoteMade textQuotedEvent = events.stream()
                                    .filter(event -> event instanceof TextQuoteMade)
                                    .map(event -> (TextQuoteMade) event)
                                    .findFirst()
                                    .orElse(null);

                            if (textQuotedEvent == null) {
                                return ServerResponse.badRequest()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .body(BodyInserters.fromValue(
                                                new RegisterResponse(
                                                        false,
                                                        "Quote result not found"
                                                )
                                        ));
                            }

                            return ServerResponse.ok()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body(BodyInserters.fromValue(
                                            new TextQuoteResponse(
                                                    textQuotedEvent.title,
                                                    textQuotedEvent.type,
                                                    textQuotedEvent.subtotal,
                                                    textQuotedEvent.discount,
                                                    textQuotedEvent.total
                                            )
                                    ));
                        })
                )
                .onErrorResume(e -> ServerResponse.badRequest()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(
                                new RegisterResponse(
                                        false, e.getMessage()
                                ))
                        )
                );
    }

    public Mono<ServerResponse> listenPOSTQuoteVariousTextsUseCase(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(QuoteVariousTextsCommand.class)
                .flatMap(command -> quoteVariousTextsUseCase.apply(Mono.just(command))
                        .collectList()
                        .flatMap(events -> {
                            // Busca el evento VariousTextQuotedEvent en la lista de eventos
                            VariousTextQuotedEvent quotedEvent = events.stream()
                                    .filter(event -> event instanceof VariousTextQuotedEvent)
                                    .map(event -> (VariousTextQuotedEvent) event)
                                    .findFirst()
                                    .orElse(null);

                            if (quotedEvent == null) {
                                return ServerResponse.badRequest()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .body(BodyInserters.fromValue(
                                                new RegisterResponse(
                                                        false,
                                                        "Quote result not found"
                                                )
                                        ));
                            }

                            return ServerResponse.ok()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body(BodyInserters.fromValue(
                                            new VariousTextQuoteResponse(
                                                    quotedEvent.getBookQuoteList(),
                                                    quotedEvent.getNovelQuoteList(),
                                                    quotedEvent.getSubtotal(),
                                                    quotedEvent.getDiscount(),
                                                    quotedEvent.getTotal()
                                            )
                                    ));
                        })
                )
                .onErrorResume(e -> ServerResponse.badRequest()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(
                                new RegisterResponse(
                                        false, e.getMessage()
                                ))
                        )
                );
    }

    public Mono<ServerResponse> listenPOSTQuoteTextsByBudgetUseCase(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(QuoteTextsByBudgetCommand.class)
                .flatMap(command -> quoteTextsByBudgetUseCase.apply(Mono.just(command))
                        .collectList()
                        .flatMap(events -> {
                            // Busca el evento BudgetTextsQuotedEvent en la lista de eventos
                            BudgetTextsQuoted quotedEvent = events.stream()
                                    .filter(event -> event instanceof BudgetTextsQuoted)
                                    .map(event -> (BudgetTextsQuoted) event)
                                    .findFirst()
                                    .orElse(null);

                            if (quotedEvent == null) {
                                return ServerResponse.badRequest()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .body(BodyInserters.fromValue(
                                                new RegisterResponse(
                                                        false,
                                                        "Quote result not found"
                                                )
                                        ));
                            }

                            return ServerResponse.ok()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body(BodyInserters.fromValue(
                                            new BudgetTextQuoteResponse(
                                                    quotedEvent.getChange(),
                                                    quotedEvent.getTotal(),
                                                    quotedEvent.getDiscount(),
                                                    quotedEvent.getSubtotal(),
                                                    quotedEvent.getTexts()
                                                    )
                                            )
                                    );
                        })
                )
                .onErrorResume(e -> ServerResponse.badRequest()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(
                                new RegisterResponse(
                                        false, e.getMessage()
                                ))
                        )
                );
    }

//    public Mono<ServerResponse> listenPOSTQuoteBatchQuoteUseCase(ServerRequest serverRequest) {
//        return serverRequest.bodyToMono(QuoteBatchQuoteCommand.class)
//                .flatMap(command -> quoteBatchQuoteUseCase.apply(Mono.just(command))
//                        .collectList()
//                        .flatMap(events -> {
//                            // Busca el evento BatchTextsQuoted en la lista de eventos
//                            BatchTextsQuoted quotedEvent = events.stream()
//                                    .filter(event -> event instanceof BatchTextsQuoted)
//                                    .map(event -> (BatchTextsQuoted) event)
//                                    .findFirst()
//                                    .orElse(null);
//
//                            if (quotedEvent == null) {
//                                return ServerResponse.badRequest()
//                                        .contentType(MediaType.APPLICATION_JSON)
//                                        .body(BodyInserters.fromValue(
//                                                new RegisterResponse(
//                                                        false,
//                                                        "Quote result not found"
//                                                )
//                                        ));
//                            }
//
//                            return ServerResponse.ok()
//                                    .contentType(MediaType.APPLICATION_JSON)
//                                    .body(BodyInserters.fromValue(
//                                            new BatchTextQuoteResponse(
//                                                    quotedEvent.getBatchQuotes(),
//                                                    quotedEvent.getSubtotal(),
//                                                    quotedEvent.getDiscount().toString(),
//                                                    quotedEvent.getTotal()
//                                            )
//                                    ));
//                        })
//                )
//                .onErrorResume(e -> ServerResponse.badRequest()
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(BodyInserters.fromValue(
//                                new RegisterResponse(
//                                        false, e.getMessage()
//                                ))
//                        )
//                );
//    }
}
