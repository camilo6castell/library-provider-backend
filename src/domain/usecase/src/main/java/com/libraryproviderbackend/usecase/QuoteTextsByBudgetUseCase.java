package com.libraryproviderbackend.usecase;

import com.libraryproviderbackend.generic.DomainEvent;
import com.libraryproviderbackend.text.Text;
import com.libraryproviderbackend.text.events.TextCreated;
import com.libraryproviderbackend.text.values.InitialPrice;
import com.libraryproviderbackend.text.values.TextId;
import com.libraryproviderbackend.text.values.Title;
import com.libraryproviderbackend.text.values.Type;
import com.libraryproviderbackend.usecase.generic.UseCaseForCommandFlux;
import com.libraryproviderbackend.usecase.generic.gateway.ITextRepository;
import com.libraryproviderbackend.usecase.generic.gateway.IUserRepository;
import com.libraryproviderbackend.user.User;
import com.libraryproviderbackend.user.commands.QuoteTextsByBudgetCommand;
import com.libraryproviderbackend.user.entity.BatchQuote;
import com.libraryproviderbackend.user.events.BudgetTextsQuoted;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Calculates which texts can be purchased within a given budget for a specific user.
 * Applies seniority discounts and maximises the number of items acquired.
 */
@Component
public class QuoteTextsByBudgetUseCase extends UseCaseForCommandFlux<QuoteTextsByBudgetCommand> {

    private final IUserRepository userRepository;
    private final ITextRepository textRepository;

    public QuoteTextsByBudgetUseCase(IUserRepository userRepository, ITextRepository textRepository) {
        this.userRepository = userRepository;
        this.textRepository = textRepository;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<QuoteTextsByBudgetCommand> commandMono) {
        return commandMono
                .switchIfEmpty(Mono.error(new IllegalArgumentException("QuoteTextsByBudgetCommand must not be null")))
                .flatMapMany(command ->
                        // Rebuild user from event store
                        userRepository.getEventsByAggregateRootId(command.getUserId())
                                .collect(Collectors.toList())
                                .flatMapMany(userEvents -> {
                                    if (userEvents.isEmpty()) {
                                        return Flux.error(new IllegalArgumentException(
                                                "User not found: " + command.getUserId()));
                                    }
                                    User user = User.from(command.getUserId(), userEvents);

                                    // Retrieve all available texts
                                    return textRepository.getEventsByType(
                                                    "com.libraryproviderbackend.text.events.TextCreated")
                                            .cast(TextCreated.class)
                                            .map(this::toText)
                                            .collect(Collectors.toList())
                                            .flatMapMany(allTexts -> {
                                                if (allTexts.isEmpty()) {
                                                    return Flux.error(new IllegalStateException(
                                                            "No texts available in the catalogue"));
                                                }

                                                // Filter texts by the requested indices
                                                List<Text> selectedTexts = command.getTextsIndices().stream()
                                                        .filter(idx -> idx >= 0 && idx < allTexts.size())
                                                        .map(allTexts::get)
                                                        .collect(Collectors.toList());

                                                if (selectedTexts.isEmpty()) {
                                                    return Flux.error(new IllegalArgumentException(
                                                            "No valid text indices provided"));
                                                }

                                                BatchQuote quote = user.calculateBudgetTextsQuote(
                                                        selectedTexts, command.getBudget());

                                                BudgetTextsQuoted event = new BudgetTextsQuoted(
                                                        quote.bookQuoteList,
                                                        quote.subtotal.value(),
                                                        quote.discount.value().toString(),
                                                        quote.total.value(),
                                                        quote.change.value()
                                                );

                                                return Flux.just(event);
                                            });
                                })
                );
    }

    private Text toText(TextCreated event) {
        return new Text(
                TextId.of(event.getAggregateRootId()),
                Title.of(event.getTitle()),
                Type.of(event.getTextType()),
                InitialPrice.of(event.getInitialPrice())
        );
    }
}
