package com.pinguinera.provider.services;

import com.pinguinera.provider.model.dto.quote.request.*;
import com.pinguinera.provider.model.dto.quote.request.shared.ItemFromTextBatchRequest;
import com.pinguinera.provider.model.dto.quote.request.shared.TextRequest;
import com.pinguinera.provider.model.dto.quote.response.BudgetSaleQuoteResponse;
import com.pinguinera.provider.model.dto.quote.response.GetTextsResponse;
import com.pinguinera.provider.model.dto.quote.response.WholesaleQuoteResponse;
import com.pinguinera.provider.model.dto.quote.response.shared.DiscountResponse;
import com.pinguinera.provider.model.dto.quote.response.shared.SummaryResponse;
import com.pinguinera.provider.model.dto.quote.response.shared.TextBatchResponse;
import com.pinguinera.provider.model.dto.quote.response.shared.TextQuoteResponse;
import com.pinguinera.provider.model.enums.TextType;
import com.pinguinera.provider.model.dto.quote.response.shared.StatusResponse;
import com.pinguinera.provider.model.object.text.BookObject;
import com.pinguinera.provider.model.object.text.NovelObject;
import com.pinguinera.provider.model.object.text.TextObject;
import com.pinguinera.provider.model.factory.TextFactory;
import com.pinguinera.provider.model.persistence.TextEntity;
import com.pinguinera.provider.repositories.TextRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class QuoteService {

    private final LocalDate today = LocalDate.now();
    private final TextRepository textRepository;
    private int countDown;
    private float budget;
    private float subTotal = 0;
    private float total = 0;

    private List<TextQuoteResponse> quoteObject = new ArrayList<>();

    private final TextFactory textFactory;

    private final JwtService jwtService;


    public QuoteService(TextRepository textRepository, TextFactory textFactory, JwtService jwtService) {
        this.textRepository = textRepository;
        this.textFactory = textFactory;
        this.jwtService = jwtService;
    }

    public StatusResponse createStock(CreateStockRequest payload) {
        if (payload.isFillDataBase()) {
            ArrayList<TextEntity> textEntityStock = new ArrayList<>();

            textEntityStock.add(new TextEntity("El origen de las especies", TextType.BOOK, 100));
            textEntityStock.add(new TextEntity("Breve historia del tiempo", TextType.BOOK, 50));
            textEntityStock.add(new TextEntity("La gran ilusión", TextType.BOOK, 10));
            textEntityStock.add(new TextEntity("El significado de la relatividad", TextType.BOOK, 1000));
            textEntityStock.add(new TextEntity("El mono desnudo", TextType.BOOK, 20));
            textEntityStock.add(new TextEntity("Crítica de la razón pura", TextType.BOOK, 300));
            textEntityStock.add(new TextEntity("Sin blanca en París y Londres", TextType.BOOK, 2));
            textEntityStock.add(new TextEntity("1984", TextType.BOOK, 150));
            textEntityStock.add(new TextEntity("Cien años de soledad", TextType.BOOK, 200));
            textEntityStock.add(new TextEntity("Don Quijote de la Mancha", TextType.BOOK, 250));
            textEntityStock.add(new TextEntity("Orgullo y prejuicio", TextType.BOOK, 180));
            textEntityStock.add(new TextEntity("Moby Dick", TextType.BOOK, 220));
            textEntityStock.add(new TextEntity("El señor de los anillos", TextType.BOOK, 300));
            textEntityStock.add(new TextEntity("Los miserables", TextType.BOOK, 280));
            textEntityStock.add(new TextEntity("Harry Potter y la piedra filosofal", TextType.BOOK, 180));
            textEntityStock.add(new TextEntity("El principito", TextType.BOOK, 120));
            textEntityStock.add(new TextEntity("El alquimista", TextType.BOOK, 150));
            textEntityStock.add(new TextEntity("El código Da Vinci", TextType.BOOK, 200));
            textEntityStock.add(new TextEntity("El amor en los tiempos del cólera", TextType.BOOK, 160));
            textEntityStock.add(new TextEntity("La sombra del viento", TextType.BOOK, 190));
            textEntityStock.add(new TextEntity("El retrato de Dorian Gray", TextType.BOOK, 170));
            textEntityStock.add(new TextEntity("La rebelión en la granja", TextType.BOOK, 1300));
            textEntityStock.add(new TextEntity("1984", TextType.NOVEL, 3030));
            textEntityStock.add(new TextEntity("Cien años de soledad", TextType.NOVEL, 550));
            textEntityStock.add(new TextEntity("El Conde de Montecristo", TextType.NOVEL, 900));
            textEntityStock.add(new TextEntity("Crimen y castigo", TextType.NOVEL, 500));
            textEntityStock.add(new TextEntity("Crónica de una muerte anunciada", TextType.NOVEL, 750));
            textEntityStock.add(new TextEntity("Don Quijote de la Mancha", TextType.NOVEL, 1200));
            textEntityStock.add(new TextEntity("Orgullo y prejuicio", TextType.NOVEL, 800));
            textEntityStock.add(new TextEntity("El señor de los anillos", TextType.NOVEL, 1500));
            textEntityStock.add(new TextEntity("Matar un ruiseñor", TextType.NOVEL, 700));
            textEntityStock.add(new TextEntity("Los miserables", TextType.NOVEL, 2000));
            textEntityStock.add(new TextEntity("Rayuela", TextType.NOVEL, 950));
            textEntityStock.add(new TextEntity("El amor en los tiempos del cólera", TextType.NOVEL, 1100));
            textEntityStock.add(new TextEntity("El guardián entre el centeno", TextType.NOVEL, 850));
            textEntityStock.add(new TextEntity("Memorias de África", TextType.NOVEL, 1000));
            textEntityStock.add(new TextEntity("El paciente inglés", TextType.NOVEL, 600));
            textEntityStock.add(new TextEntity("Anna Karenina", TextType.NOVEL, 1400));
            textEntityStock.add(new TextEntity("Rebelión en la granja", TextType.NOVEL, 1800));
            textEntityStock.add(new TextEntity("Cien años de soledad", TextType.NOVEL, 1300));
            textEntityStock.add(new TextEntity("Crónica de una muerte anunciada", TextType.NOVEL, 950));
            textEntityStock.add(new TextEntity("Los pilares de la Tierra", TextType.NOVEL, 1700));
            textRepository.saveAll(textEntityStock);
            return new StatusResponse(true, "Textos agregados correctamente a la base de datos.");
        } else {
            return new StatusResponse(false, "No se han hecho cambios a la base de datos.");
        }
    }

    public GetTextsResponse getStockTexts(GetTextRequest payload) {
        if (payload.isGetStock()) {
            return new GetTextsResponse(textRepository.findAll());
        } else {
            return new GetTextsResponse(new ArrayList<>());
        }
    }

    public TextQuoteResponse saveAndQuoteText(SaveAndQuoteTextRequest payload) {

        float seniorityDiscount = calculateSeniorityDiscount(getClientEntryDate(payload.token));
        List<DiscountResponse> discounts = new ArrayList<>();

        textRepository.save(new TextEntity(
                payload.text.title,
                payload.text.getType(),
                payload.text.basePrice
        ));

        TextObject newTextObject = textFactory.create(
                payload.text,
                true
        );

        if (seniorityDiscount != 1) {
            discounts.add(new DiscountResponse(
                    "seniority",
                    ((seniorityDiscount) * 100) - 100)
            );
        }

        total = newTextObject.getTotalPrice() * seniorityDiscount;

        TextQuoteResponse result = new TextQuoteResponse(
                newTextObject.getTitle(),
                newTextObject.getType(),
                newTextObject.getPrice(),
                discounts,
                total);

        total = 0;

        return result;
    }


    public WholesaleQuoteResponse calculateWholesaleQuote(WholeSaleRequest payload) {

        float seniorityDiscount = calculateSeniorityDiscount(getClientEntryDate(payload.token));

        List<DiscountResponse> discounts = new ArrayList<>();

        List<TextEntity> booksFromDb = createListTextEntity(true);
        List<TextEntity> novelsFromDb = createListTextEntity(false);

        List<TextEntity> chosenAndSortedTextsEntities = getChosenAndSortedTextsEntities(
                payload,
                booksFromDb,
                novelsFromDb
        );

        List<TextQuoteResponse> booksQuote = createTextQuote(true, chosenAndSortedTextsEntities);
        List<TextQuoteResponse> novelsQuote = createTextQuote(false, chosenAndSortedTextsEntities);

        if (seniorityDiscount != 1) {
            discounts.add(new DiscountResponse(
                    "seniority",
                    ((seniorityDiscount) * 100) - 100)
            );
        }

        total = subTotal * seniorityDiscount;

        WholesaleQuoteResponse result = new WholesaleQuoteResponse(
                booksQuote,
                novelsQuote,
                new SummaryResponse(
                        subTotal,
                        discounts, total)
        );

        total = 0;
        subTotal = 0;

        return result;
    }

    public BudgetSaleQuoteResponse calculateBudgetSaleQuote(BudgetSaleRequest payload) {

        BudgetSaleQuoteResponse result;

        float seniorityDiscount = calculateSeniorityDiscount(payload.getClientEntryDate());

        List<DiscountResponse> discounts = setDiscountList(seniorityDiscount);

        List<TextEntity> entitiesSortedFromIndicesBatch = getEntitiesSortedFromIndicesBatch(payload);

        if (containsBookAndNovel(entitiesSortedFromIndicesBatch)) {

            ArrayList<TextBatchResponse> suggestedTextsBatch = new ArrayList<>();
            List<DiscountResponse> emptyListItemBatchDiscount = new ArrayList<>();
            List<DiscountResponse> listItemBatchDiscount = new ArrayList<>();
            listItemBatchDiscount.add(new DiscountResponse(
                    "Descuento por compra al por mayor",
                    0.15F)
            );

            TextObject cheapestBookObject = getCheapestText(
                    entitiesSortedFromIndicesBatch,
                    TextType.BOOK
            );
            TextObject cheapestNovelObject = getCheapestText(
                    entitiesSortedFromIndicesBatch,
                    TextType.NOVEL
            );

            float bookPriceToPlay = cheapestBookObject.getBasePrice() * seniorityDiscount;
            float novelPriceToPlay = cheapestNovelObject.getBasePrice() * seniorityDiscount;

            budget = payload.budget;

            if (cheapestBookObject.getTotalPrice() > cheapestNovelObject.getTotalPrice()) {
                addCheapestTextObject(
                        cheapestBookObject,
                        suggestedTextsBatch,
                        emptyListItemBatchDiscount,
                        seniorityDiscount
                );
            } else {
                addCheapestTextObject(
                        cheapestNovelObject,
                        suggestedTextsBatch,
                        emptyListItemBatchDiscount,
                        seniorityDiscount
                );
            }

            setSuggestedTextsBatch(
                    bookPriceToPlay,
                    novelPriceToPlay,
                    cheapestBookObject,
                    cheapestNovelObject,
                    suggestedTextsBatch,
                    seniorityDiscount,
                    emptyListItemBatchDiscount,
                    listItemBatchDiscount
            );

            result = setBudgetSaleQuoteObject(suggestedTextsBatch, discounts);

        } else {
            result = new BudgetSaleQuoteResponse(
                    new ArrayList<>(),
                    new SummaryResponse(
                            0, new ArrayList<>(),
                            0),
                    new StatusResponse(
                            false,
                            "Error, se necesita por lo menos un libro y una novela " +
                                    "para poder hacer esta cotización."
                    )
            );
        }
        countDown = 0;
        budget = 0;
        total = 0;
        subTotal = 0;
        return result;
    }


//    // PRIVATE METHODS

    private List<DiscountResponse> setDiscountList(float seniorityDiscount) {
        List<DiscountResponse> auxDiscuountsList = new ArrayList<>();
        if (seniorityDiscount != 1) {
            auxDiscuountsList.add(
                    new DiscountResponse(
                            "seniority",
                            ((seniorityDiscount) * 100) - 100
                    )
            );
        }
        return auxDiscuountsList;
    }

    private void addCheapestTextObject(
            TextObject cheapestTextObject,
            List<TextBatchResponse> suggestedTextsBatch,
            List<DiscountResponse> emptyListItemBatchDiscount,
            float seniorityDiscount
    ) {
        TextObject cheapestClonedTextObject;
        if (cheapestTextObject instanceof BookObject) {
            cheapestClonedTextObject = new BookObject(
                    cheapestTextObject.getTitle(),
                    cheapestTextObject.getType(),
                    cheapestTextObject.getBasePrice(),
                    true
            );
        } else {
            cheapestClonedTextObject = new NovelObject(
                    cheapestTextObject.getTitle(),
                    cheapestTextObject.getType(),
                    cheapestTextObject.getBasePrice(),
                    true
            );
        }
        suggestedTextsBatch.add(
                new TextBatchResponse(
                        cheapestClonedTextObject.getTitle(),
                        cheapestClonedTextObject.getType(),
                        cheapestClonedTextObject.getPrice(),
                        emptyListItemBatchDiscount,
                        cheapestClonedTextObject.getTotalPrice()
                )
        );
        budget -= cheapestClonedTextObject.getTotalPrice() * seniorityDiscount;
        subTotal += cheapestClonedTextObject.getTotalPrice();
        total += cheapestClonedTextObject.getTotalPrice() * seniorityDiscount;
    }


    private void setBookItemBatch(
            TextObject cheapestBookObject,
            ArrayList<TextBatchResponse> suggestedTextsBatch,
            List<DiscountResponse> emptyListItemBatchDiscount,
            List<DiscountResponse> listItemBatchDiscount,
            float seniorityDiscount,
            float bookPriceToPlay
    ) {
        if (countDown != 0) {
            TextObject cheapestClonedBookObject = new BookObject(
                    cheapestBookObject.getTitle(),
                    cheapestBookObject.getType(),
                    cheapestBookObject.getBasePrice(),
                    true
            );
            suggestedTextsBatch.add(
                    new TextBatchResponse(
                            cheapestClonedBookObject.getTitle(),
                            cheapestClonedBookObject.getType(),
                            cheapestClonedBookObject.getPrice(),
                            emptyListItemBatchDiscount,
                            cheapestClonedBookObject.getTotalPrice()
                    )
            );
            budget -= cheapestClonedBookObject.getTotalPrice() * seniorityDiscount;
            total += cheapestClonedBookObject.getTotalPrice() * seniorityDiscount;
            subTotal += cheapestClonedBookObject.getTotalPrice();
            countDown -= 1;
        } else {
            TextObject cheapestClonedBookObject = cheapestBookObject.clone();
            cheapestClonedBookObject.setIsRetail(false);
            suggestedTextsBatch.add(
                    new TextBatchResponse(
                            cheapestClonedBookObject.getTitle(),
                            cheapestClonedBookObject.getType(),
                            cheapestClonedBookObject.getPrice(),
                            listItemBatchDiscount,
                            cheapestClonedBookObject.getTotalPrice()
                    )
            );
            budget -= bookPriceToPlay;
            subTotal += cheapestClonedBookObject.getTotalPrice();
            total += bookPriceToPlay;
        }
    }

    private void setNovelItemBatch(
            TextObject cheapestNovelObject,
            ArrayList<TextBatchResponse> suggestedTextsBatch,
            List<DiscountResponse> emptyListItemBatchDiscount,
            List<DiscountResponse> listItemBatchDiscount,
            float seniorityDiscount,
            float novelPriceToPlay
    ) {
        if (countDown != 0) {
            TextObject cheapestClonedNovelObject = new NovelObject(
                    cheapestNovelObject.getTitle(),
                    cheapestNovelObject.getType(),
                    cheapestNovelObject.getBasePrice(),
                    true
            );
            suggestedTextsBatch.add(
                    new TextBatchResponse(
                            cheapestClonedNovelObject.getTitle(),
                            cheapestClonedNovelObject.getType(),
                            cheapestClonedNovelObject.getPrice(),
                            emptyListItemBatchDiscount,
                            cheapestClonedNovelObject.getTotalPrice()
                    )
            );
            budget -= cheapestClonedNovelObject.getTotalPrice() * seniorityDiscount;
            total = cheapestClonedNovelObject.getTotalPrice() * seniorityDiscount;
            subTotal += cheapestClonedNovelObject.getTotalPrice();
            countDown -= 1;
        } else {
            TextObject cheapestClonedNovelObject = cheapestNovelObject.clone();
            cheapestClonedNovelObject.setIsRetail(false);
            suggestedTextsBatch.add(
                    new TextBatchResponse(
                            cheapestClonedNovelObject.getTitle(),
                            cheapestClonedNovelObject.getType(),
                            cheapestClonedNovelObject.getPrice(),
                            listItemBatchDiscount,
                            cheapestClonedNovelObject.getTotalPrice()
                    )
            );
            budget -= novelPriceToPlay;
            subTotal += cheapestClonedNovelObject.getTotalPrice();
            total += novelPriceToPlay;
        }
    }

    private void checkConditionsToSetBooksItemsBatch(
            float bookPriceToPlay,
            TextObject cheapestBookObject,
            ArrayList<TextBatchResponse> suggestedTextsBatch,
            List<DiscountResponse> emptyListItemBatchDiscount,
            List<DiscountResponse> listItemBatchDiscount,
            float seniorityDiscount
    ) {
        while (budget > 0) {
            if (budget - bookPriceToPlay < 0) {
                break;
            }
            setBookItemBatch(
                    cheapestBookObject,
                    suggestedTextsBatch,
                    emptyListItemBatchDiscount,
                    listItemBatchDiscount,
                    seniorityDiscount,
                    bookPriceToPlay
            );
        }
    }

    private void checkConditionsToSetNovelsItemsBatch(
            float novelPriceToPlay,
            TextObject cheapestNovelObject,
            ArrayList<TextBatchResponse> suggestedTextsBatch,
            List<DiscountResponse> emptyListItemBatchDiscount,
            List<DiscountResponse> listItemBatchDiscount,
            float seniorityDiscount
    ) {
        while (budget > 0) {
            if (budget - novelPriceToPlay < 0) {
                break;
            }
            setNovelItemBatch(
                    cheapestNovelObject,
                    suggestedTextsBatch,
                    emptyListItemBatchDiscount,
                    listItemBatchDiscount,
                    seniorityDiscount,
                    novelPriceToPlay
            );
        }
    }

    private void setSuggestedTextsBatch(
            float bookPriceToPlay,
            float novelPriceToPlay,
            TextObject cheapestBookObject,
            TextObject cheapestNovelObject,
            ArrayList<TextBatchResponse> suggestedTextsBatch,
            float seniorityDiscount,
            List<DiscountResponse> emptyListItemBatchDiscount,
            List<DiscountResponse> listItemBatchDiscount
    ) {
        countDown = 9;
        if (bookPriceToPlay < novelPriceToPlay) {
            checkConditionsToSetBooksItemsBatch(
                    bookPriceToPlay,
                    cheapestBookObject,
                    suggestedTextsBatch,
                    emptyListItemBatchDiscount,
                    listItemBatchDiscount,
                    seniorityDiscount
            );
        } else {
            checkConditionsToSetNovelsItemsBatch(
                    novelPriceToPlay,
                    cheapestNovelObject,
                    suggestedTextsBatch,
                    emptyListItemBatchDiscount,
                    listItemBatchDiscount,
                    seniorityDiscount
            );
        }
    }

    private BudgetSaleQuoteResponse setBudgetSaleQuoteObject(
            ArrayList<TextBatchResponse> suggestedTextsBatch,
            List<DiscountResponse> discounts) {
        return suggestedTextsBatch.size() > 10 ?
                new BudgetSaleQuoteResponse(
                        suggestedTextsBatch,
                        new SummaryResponse(
                                subTotal,
                                discounts,
                                total
                        ),
                        new StatusResponse(
                                true,
                                "Contización hecha con éxito, y te han sobrado: " + budget + "."))
                :
                new BudgetSaleQuoteResponse(
                        new ArrayList<>(),
                        new SummaryResponse(
                                0,
                                new ArrayList<>(),
                                0
                        ),
                        new StatusResponse(
                                false,
                                "Error, se necesitan por lo menos 11 textos para poder hacer esta cotización"
                        )
                );
    }

    private List<TextEntity> getEntitiesSortedFromIndicesBatch(BudgetSaleRequest payload) {
        List<TextEntity> dbTextsEntities = textRepository.findAll();
        List<Integer> validIndices = filterValidIndices(payload.textsIndices, dbTextsEntities.size());
        List<TextEntity> selectedEntities = mapIndicesToEntities(validIndices, dbTextsEntities);
        return sortEntitiesByBasePriceDesc(selectedEntities);
    }

    private List<Integer> filterValidIndices(List<Integer> indices, int maxIndex) {
        return indices.stream()
                .filter(index -> index >= 0 && index < maxIndex)
                .collect(Collectors.toList());
    }

    private List<TextEntity> mapIndicesToEntities(
            List<Integer> indices,
            List<TextEntity> entities
    ) {
        return indices.stream()
                .map(index -> entities.get(index - 1))
                .collect(Collectors.toList());
    }

    private List<TextEntity> sortEntitiesByBasePriceDesc(List<TextEntity> entities) {
        return entities.stream()
                .sorted(Comparator.comparing(TextEntity::getBasePrice).reversed())
                .collect(Collectors.toList());
    }

    public boolean containsBookAndNovel(List<TextEntity> texts) {
        boolean hasBook = hasTypeText(TextType.BOOK, texts);
        boolean hasNovel = hasTypeText(TextType.NOVEL, texts);
        return hasBook && hasNovel;
    }

    private boolean hasTypeText(TextType type, List<TextEntity> texts) {
        boolean hasTextType = false;
        for (TextEntity text : texts) {
            if (text.getType() == type) {
                hasTextType = true;
                break;
            }
        }
        return hasTextType;
    }

    public TextObject getCheapestText(
            List<TextEntity> entitiesSortedFromIndicesBatch,
            TextType type
    ) {
        TextObject cheapestText = null;
        float lowestPrice = Float.MAX_VALUE;

        for (TextEntity text : entitiesSortedFromIndicesBatch) {
            if (text.getType() == type) {
                TextObject auxText = textFactory.create(
                        new TextRequest(text.getTitle(),
                                text.getType(),
                                text.getBasePrice()),
                        false
                );
                if (auxText.getTotalPrice() < lowestPrice) {
                    lowestPrice = auxText.getTotalPrice();
                    cheapestText = auxText;
                }
            }
        }
        return cheapestText;
    }

    private float calculateSeniorityDiscount(LocalDate clientEntryDate) {
        int years = Period.between(clientEntryDate, today).getYears();
        float seniorityDiscount;
        if (years < 1) {
            seniorityDiscount = 1;
        } else if (years <= 2) {
            seniorityDiscount = 0.88F;
        } else {
            seniorityDiscount = 0.83F;
        }
        return seniorityDiscount;
    }

    private List<TextEntity> createListTextEntity(boolean isBookList) {
        List<TextEntity> auxBookEntitytList = new ArrayList<>();
        List<TextEntity> auxNovelEntitytList = new ArrayList<>();
        for (TextEntity text : textRepository.findAll()) {
            if (text.getType() == TextType.BOOK) {
                auxBookEntitytList.add(text);
            } else {
                auxNovelEntitytList.add(text);
            }
        }
        return isBookList ? auxBookEntitytList : auxNovelEntitytList;
    }

    private List<TextEntity> getChosenAndSortedTextsEntities(
            WholeSaleRequest payload,
            List<TextEntity> booksFromDb,
            List<TextEntity> novelsFromDb
    ) {
        List<ItemFromTextBatchRequest> bookIndicesAndQuantity = payload.getBookIndicesAndQuantity();
        List<ItemFromTextBatchRequest> novelIndicesAndQuantity = payload.getNovelIndicesAndQuantity();

        List<TextEntity> auxChosenTextsEntities = new ArrayList<>();

        addToChosenTextsEntities(bookIndicesAndQuantity, auxChosenTextsEntities, booksFromDb);
        addToChosenTextsEntities(novelIndicesAndQuantity, auxChosenTextsEntities, novelsFromDb);

        auxChosenTextsEntities.sort(Comparator.comparing(TextEntity::getBasePrice).reversed());

        return auxChosenTextsEntities;
    }

    private void addToChosenTextsEntities(
            List<ItemFromTextBatchRequest> indicesAndQuantity,
            List<TextEntity> chosenTexts,
            List<TextEntity> sourceEntities
    ) {
        indicesAndQuantity.forEach(item -> {
            int index = item.getIndex();
            int quantity = item.getQuantity();
            for (int i = 0; i < quantity; i++) {
                chosenTexts.add(sourceEntities.get(index));
            }
        });
    }

    private List<TextQuoteResponse> createTextQuote(
            boolean isBookQuote,
            List<TextEntity> chosenAndSortedTextsEntities
    ) {
        quoteObject = new ArrayList<>();

        for (int i = 0; i < chosenAndSortedTextsEntities.size(); i++) {
            TextEntity textEntity = chosenAndSortedTextsEntities.get(i);
            List<DiscountResponse> auxDiscount = new ArrayList<>();

            TextObject auxTextObject = textFactory.create(
                    new TextRequest(
                            textEntity.getTitle(),
                            textEntity.getType(),
                            textEntity.getBasePrice()),
                    i <= 10);

            if (i > 10) {
                auxDiscount.add(
                        new DiscountResponse(
                                "Descuento por compra al por mayor",
                                0.15F)
                );
            }

            setQuoteObjects(isBookQuote, textEntity, auxTextObject, auxDiscount);
        }

        return quoteObject;
    }

    private void setQuoteObjects(
            boolean isBookQuote,
            TextEntity textEntity,
            TextObject auxTextObject,
            List<DiscountResponse> auxDiscount
    ) {
        if ((isBookQuote && textEntity.getType() == TextType.BOOK)
                ||
                (!isBookQuote && textEntity.getType() == TextType.NOVEL)
        ) {
            quoteObject.add(
                    new TextQuoteResponse(
                            auxTextObject.getTitle(),
                            auxTextObject.getType(),
                            auxTextObject.getPrice(),
                            auxDiscount,
                            auxTextObject.getTotalPrice()
                    )
            );
            subTotal += auxTextObject.getTotalPrice();
        }
    }

    private LocalDate getClientEntryDate(String token){
        String jwt = token.substring(7);
        return jwtService.getEntryDateFromToken(jwt);
    }
}

