package com.reactive.usecase;

import com.reactive.generic.DomainEvent;
import com.reactive.text.Text;
import com.reactive.text.events.TextCreated;
import com.reactive.text.values.TextTypeEnum;
import com.reactive.usecase.generic.UseCaseForCommand;
import com.reactive.usecase.generic.gateway.ITextRepository;
import com.reactive.usecase.generic.gateway.IUserRepository;
import com.reactive.user.User;
import com.reactive.user.commands.QuoteVariousTextsCommand;
import com.reactive.user.commands.shared.ItemFromTextBatchRequest;
import com.reactive.user.entity.BatchQuote;
import com.reactive.user.events.VariousTextQuotedEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class QuoteVariousTextsUseCase extends UseCaseForCommand<QuoteVariousTextsCommand> {

    public static TextTypeEnum[] textTypeEnumValues = TextTypeEnum.values();

    private final IUserRepository userRepository;
    private final ITextRepository textRepository;

    public QuoteVariousTextsUseCase(IUserRepository userRepository, ITextRepository textRepository) {
        this.userRepository = userRepository;
        this.textRepository = textRepository;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<QuoteVariousTextsCommand> quoteVariousTextsCommandMono) {
        return quoteVariousTextsCommandMono
                .switchIfEmpty(Mono.error(new IllegalArgumentException("quoteVariousTextsCommand must not be null")))
                .flatMapMany(command ->
                        userRepository.getEventByAggregateRootId(command.userId)
                                .flatMap(userEvents -> {
                                    User user = User.from(command.userId, userEvents.getFirst());

                                    Flux<List<TextCreated>> bookTextEvents = Flux.fromIterable(command.getBookIndicesAndQuantity())
                                            .flatMap(request ->
                                                    textRepository.getEventsByType("com.reactive.text.events.TextCreated")
                                                            .filter(events -> !events.isEmpty())
                                                            .map(events -> events.stream()
                                                                    .filter(event -> event instanceof TextCreated && textTypeEnumValues[((TextCreated) event).getTextType()].equals(TextTypeEnum.BOOK))
                                                                    .map(event -> (TextCreated) event)
                                                                    .collect(Collectors.toList()))
                                                            .filter(filteredEvents -> !filteredEvents.isEmpty())
                                            );


                                    Flux<List<TextCreated>> novelTextEvents = Flux.fromIterable(command.getNovelIndicesAndQuantity())
                                            .flatMap(request ->
                                                    textRepository.getEventsByType("com.reactive.text.events.TextCreated")
                                                            .filter(events -> !events.isEmpty())
                                                            .map(events -> events.stream()
                                                                    .filter(event -> event instanceof TextCreated && textTypeEnumValues[((TextCreated) event).getTextType()].equals(TextTypeEnum.NOVEL))
                                                                    .map(event -> (TextCreated) event)
                                                                    .collect(Collectors.toList()))
                                                            .filter(filteredEvents -> !filteredEvents.isEmpty())
                                            );

                                    return Flux.combineLatest(bookTextEvents.collectList(), novelTextEvents.collectList(), (bookEventsList, novelEventsList) -> {
                                        // Convert List<DomainEvent> to Text
                                        List<Text> bookTexts = bookEventsList.stream()
                                                .flatMap(List::stream)
                                                .map(textEvent -> Text.from((TextCreated) textEvent))
                                                .collect(Collectors.toList());

                                        List<Text> novelTexts = novelEventsList.stream()
                                                .flatMap(List::stream)
                                                .map(textEvent -> Text.from((TextCreated) textEvent))
                                                .collect(Collectors.toList());

                                        // Create new lists based on index and quantity
                                        List<Text> expandedBookTexts = new ArrayList<>();
                                        for (ItemFromTextBatchRequest request : command.getBookIndicesAndQuantity()) {
                                            Text text = bookTexts.get(request.getIndex());
                                            expandedBookTexts.addAll(IntStream.range(0, request.getQuantity())
                                                    .mapToObj(i -> text)
                                                    .collect(Collectors.toList()));
                                        }

                                        List<Text> expandedNovelTexts = new ArrayList<>();
                                        for (ItemFromTextBatchRequest request : command.getNovelIndicesAndQuantity()) {
                                            Text text = novelTexts.get(request.getIndex());
                                            expandedNovelTexts.addAll(IntStream.range(0, request.getQuantity())
                                                    .mapToObj(i -> text)
                                                    .collect(Collectors.toList()));
                                        }

                                        // Calculate quotes for books and novels
                                        BatchQuote quoteResponse = user.calculateVariousTextQuote(expandedBookTexts, expandedNovelTexts, user.entryDate);

                                        // Create and emit an event for the quote calculation
                                        return new VariousTextQuotedEvent(
                                                quoteResponse.getBookQuoteList(),
                                                quoteResponse.getNovelQuoteList(),
                                                quoteResponse.getSubtotal().value(),
                                                quoteResponse.getDiscount().value().toString(),
                                                quoteResponse.getTotal().value()
                                        );
                                    }).flatMap(Flux::just);
                                }));
    }
}