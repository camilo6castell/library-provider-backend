//package com.reactive.usecase;
//
//import com.reactive.generic.DomainEvent;
//import com.reactive.text.Text;
//import com.reactive.text.events.TextCreated;
//import com.reactive.text.values.TextTypeEnum;
//import com.reactive.usecase.generic.UseCaseForCommand;
//import com.reactive.usecase.generic.gateway.ITextRepository;
//import com.reactive.usecase.generic.gateway.IUserRepository;
//import com.reactive.user.User;
//import com.reactive.user.commands.QuoteBatchQuoteCommand;
//import com.reactive.user.entity.BatchQuote;
//import com.reactive.user.events.BatchTextsQuoted;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class QuoteBatchQuoteUseCase extends UseCaseForCommand<QuoteBatchQuoteCommand> {
//
//    private final IUserRepository userRepository;
//    private final ITextRepository textRepository;
//
//    public QuoteBatchQuoteUseCase(IUserRepository userRepository, ITextRepository textRepository) {
//        this.userRepository = userRepository;
//        this.textRepository = textRepository;
//    }
//
//    @Override
//    public Flux<DomainEvent> apply(Mono<QuoteBatchQuoteCommand> quoteBatchQuoteCommandMono) {
//        return quoteBatchQuoteCommandMono
//                .switchIfEmpty(Mono.error(new IllegalArgumentException("quoteBatchQuoteCommand must not be null")))
//                .flatMapMany(command ->
//                        userRepository.getEventByAggregateRootId(command.getUserId())
//                                .collectList()
//                                .flatMapMany(userEvents -> {
//                                    User user = User.from(command.getUserId(), userEvents.getFirst().getFirst());
//
//                                    return textRepository.getEventsByType("com.reactive.text.events.TextCreated")
//                                            .collectList()
//                                            .flatMapMany(events -> {
//                                                if (events.isEmpty()) {
//                                                    return Flux.<BatchTextsQuoted>empty();
//                                                }
//
//                                                List<DomainEvent> firstList = events.get(0);
//
//                                                // Convert events to Text instances
//                                                List<Text> allTexts = firstList.stream()
//                                                        .map(event -> (TextCreated) event)
//                                                        .map(Text::from)
//                                                        .collect(Collectors.toList());
//
//                                                // Separate texts into BOOK and NOVEL lists
//                                                List<Text> bookTexts = allTexts.stream()
//                                                        .filter(text -> text.getType().equals(TextTypeEnum.BOOK))
//                                                        .collect(Collectors.toList());
//
//                                                List<Text> novelTexts = allTexts.stream()
//                                                        .filter(text -> text.getType().equals(TextTypeEnum.NOVEL))
//                                                        .collect(Collectors.toList());
//
//                                                // Generate quotes for each list of indices
//                                                List<BatchQuote> batchQuotes = command.getTextsIndicesList().stream()
//                                                        .map(indices -> {
//                                                            List<Text> filteredBookTexts = indices.stream()
//                                                                    .filter(index -> index < bookTexts.size())
//                                                                    .map(bookTexts::get)
//                                                                    .collect(Collectors.toList());
//
//                                                            List<Text> filteredNovelTexts = indices.stream()
//                                                                    .filter(index -> index < novelTexts.size())
//                                                                    .map(novelTexts::get)
//                                                                    .collect(Collectors.toList());
//
//                                                            return user.calculateVariousTextQuote(filteredBookTexts, filteredNovelTexts, user.entryDate);
//                                                        })
//                                                        .collect(Collectors.toList());
//
//                                                // Create and return BatchTextsQuotedEvent
//                                                BatchTextsQuoted event = new BatchTextsQuoted(batchQuotes);
//                                                return Flux.just(event);
//                                            });
//                                })
//                )
//                .cast(DomainEvent.class);
//    }
//}