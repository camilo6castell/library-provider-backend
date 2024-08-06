package com.reactive.usecase;

import com.reactive.generic.DomainEvent;
import com.reactive.text.Text;
import com.reactive.text.events.TextCreated;
import com.reactive.usecase.generic.UseCaseForCommand;
import com.reactive.usecase.generic.gateway.ITextRepository;
import com.reactive.usecase.generic.gateway.IUserRepository;
import com.reactive.user.User;
import com.reactive.user.commands.QuoteTextsByBudgetCommand;
import com.reactive.user.entity.BatchQuote;
import com.reactive.user.events.BudgetTextsQuoted;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

public class QuoteTextsByBudgetUseCase extends UseCaseForCommand<QuoteTextsByBudgetCommand> {

    private final IUserRepository userRepository;
    private final ITextRepository textRepository;

    public QuoteTextsByBudgetUseCase(IUserRepository userRepository, ITextRepository textRepository) {
        this.userRepository = userRepository;
        this.textRepository = textRepository;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<QuoteTextsByBudgetCommand> quoteTextsByBudgetCommandMono) {
        return quoteTextsByBudgetCommandMono
                .switchIfEmpty(Mono.error(new IllegalArgumentException("quoteTextsByBudgetCommand must not be null")))
                .flatMapMany(command ->
                        userRepository.getEventByAggregateRootId(command.getUserId())
                                .collectList()
                                .flatMapMany(userEvents -> {
                                    User user = User.from(command.getUserId(), userEvents.getFirst().getFirst());

                                    return textRepository.getEventsByType("com.reactive.text.events.TextCreated")
                                            .collectList()
                                            .flatMapMany(events -> {
                                                if (events.isEmpty()) {
                                                    return Flux.<BudgetTextsQuoted>empty();
                                                }

                                                List<DomainEvent> firtsList = events.getFirst();

                                                // Convert events to Text instances
                                                List<Text> allTexts = firtsList.stream().map(event -> (TextCreated) event).map(Text::from).toList();

                                                // Filter the Text instances based on indices in the command
                                                List<Text> filteredTexts = command.getTextsIndices().stream()
                                                        .map(allTexts::get)
                                                        .collect(Collectors.toList());

                                                // Calculate quote response
                                                BatchQuote quoteResponse = user.calculateBudgetTextsQuote(filteredTexts, command.getBudget(), user.entryDate);

                                                // Create BudgetTextsQuotedEvent
                                                BudgetTextsQuoted event = new BudgetTextsQuoted(
                                                        quoteResponse.bookQuoteList,
                                                        quoteResponse.getSubtotal().value(),
                                                        quoteResponse.getDiscount().value().toString(),
                                                        quoteResponse.getTotal().value(),
                                                        quoteResponse.getChange().value()
                                                );

                                                return Flux.just(event);
                                            });
                                })
                )
                .cast(DomainEvent.class);
    }
}