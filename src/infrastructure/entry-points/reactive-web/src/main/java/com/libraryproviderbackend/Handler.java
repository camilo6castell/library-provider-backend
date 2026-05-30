package com.libraryproviderbackend;

import com.libraryproviderbackend.Dtos.authentication.response.RegisterResponse;
import com.libraryproviderbackend.Dtos.quote.response.BudgetTextQuoteResponse;
import com.libraryproviderbackend.user.commands.CreateUserCommand;
import com.libraryproviderbackend.user.commands.QuoteTextsByBudgetCommand;
import com.libraryproviderbackend.user.commands.SaveAndQuoteTextCommand;
import com.libraryproviderbackend.user.events.BudgetTextsQuoted;
import com.libraryproviderbackend.usecase.CreateTextUseCase;
import com.libraryproviderbackend.usecase.CreateUserUseCase;
import com.libraryproviderbackend.usecase.QuoteTextsByBudgetUseCase;
import com.libraryproviderbackend.usecase.SaveAndQuoteTextUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * WebFlux functional handler.  Each method maps a route to a use case and converts
 * the domain result into an HTTP response.
 */
@Component
public class Handler {

    private static final Logger log = LoggerFactory.getLogger(Handler.class);

    private final CreateUserUseCase createUserUseCase;
    private final SaveAndQuoteTextUseCase saveAndQuoteTextUseCase;
    private final QuoteTextsByBudgetUseCase quoteTextsByBudgetUseCase;
    private final CreateTextUseCase createTextUseCase;

    public Handler(
            CreateUserUseCase createUserUseCase,
            SaveAndQuoteTextUseCase saveAndQuoteTextUseCase,
            QuoteTextsByBudgetUseCase quoteTextsByBudgetUseCase,
            CreateTextUseCase createTextUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.saveAndQuoteTextUseCase = saveAndQuoteTextUseCase;
        this.quoteTextsByBudgetUseCase = quoteTextsByBudgetUseCase;
        this.createTextUseCase = createTextUseCase;
    }

    // ── POST /api/v1/users ───────────────────────────────────────────────────

    public Mono<ServerResponse> createUser(ServerRequest request) {
        return createUserUseCase
                .apply(request.bodyToMono(CreateUserCommand.class))
                .flatMap(event -> ok(new RegisterResponse(true, event.getAggregateRootId())))
                .onErrorResume(e -> {
                    log.warn("createUser failed: {}", e.getMessage());
                    return badRequest(new RegisterResponse(false, e.getMessage()));
                });
    }

    // ── POST /api/v1/texts/quote ─────────────────────────────────────────────

    public Mono<ServerResponse> saveAndQuoteText(ServerRequest request) {
        return saveAndQuoteTextUseCase
                .apply(request.bodyToMono(SaveAndQuoteTextCommand.class))
                .flatMap(this::ok)
                .onErrorResume(e -> {
                    log.warn("saveAndQuoteText failed: {}", e.getMessage());
                    return badRequest(new RegisterResponse(false, e.getMessage()));
                });
    }

    // ── POST /api/v1/texts/quote-by-budget ───────────────────────────────────

    public Mono<ServerResponse> quoteTextsByBudget(ServerRequest request) {
        return request.bodyToMono(QuoteTextsByBudgetCommand.class)
                .flatMap(command -> quoteTextsByBudgetUseCase.apply(Mono.just(command))
                        .collectList()
                        .flatMap(events -> {
                            BudgetTextsQuoted quotedEvent = events.stream()
                                    .filter(e -> e instanceof BudgetTextsQuoted)
                                    .map(e -> (BudgetTextsQuoted) e)
                                    .findFirst()
                                    .orElse(null);

                            if (quotedEvent == null) {
                                return badRequest(new RegisterResponse(false, "Quote result not found"));
                            }

                            return ok(new BudgetTextQuoteResponse(
                                    quotedEvent.getChange(),
                                    quotedEvent.getTotal(),
                                    quotedEvent.getDiscount(),
                                    quotedEvent.getSubtotal(),
                                    quotedEvent.getTexts()));
                        })
                )
                .onErrorResume(e -> {
                    log.warn("quoteTextsByBudget failed: {}", e.getMessage());
                    return badRequest(new RegisterResponse(false, e.getMessage()));
                });
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private Mono<ServerResponse> ok(Object body) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(body));
    }

    private Mono<ServerResponse> badRequest(Object body) {
        return ServerResponse.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(body));
    }
}
