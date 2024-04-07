package com.pinguinera.provider.services;

import com.pinguinera.provider.models.DTOS.BudgetSaleDTO;
import com.pinguinera.provider.models.DTOS.CreateStockDTO;
import com.pinguinera.provider.models.DTOS.SaveAndQuoteTextDTO;
import com.pinguinera.provider.models.DTOS.WholeSaleDTO;
import com.pinguinera.provider.models.enums.TextType;
import com.pinguinera.provider.models.objects.quote.BudgetSaleQuoteObject;
import com.pinguinera.provider.models.objects.quote.DiscountObject;
import com.pinguinera.provider.models.objects.quote.TextQuoteObject;
import com.pinguinera.provider.models.objects.quote.WholesaleQuoteObject;
import com.pinguinera.provider.models.objects.response.ResponseObject;
import com.pinguinera.provider.models.objects.text.BookObject;
import com.pinguinera.provider.models.objects.text.NovelObject;
import com.pinguinera.provider.models.objects.text.TextObject;
import com.pinguinera.provider.models.factories.TextFactory;
import com.pinguinera.provider.models.persistence.TextEntity;
import com.pinguinera.provider.repositories.TextRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class QuoteService {

    private final LocalDate today = LocalDate.now();
    private final TextRepository textRepository;


    private List<TextObject> textsSortedAndCombined = new ArrayList<>();
    private List<SaveAndQuoteTextDTO> processedTexts = new ArrayList<>();
    private List<String> booksQuote = new ArrayList<>();
    private List<String> novelsQuote = new ArrayList<>();
    private BookObject cheapestBookObject;
    private NovelObject cheapestNovelObject;

    private float budget;
    private ArrayList<String> budgetQuote = new ArrayList<>();


    private final TextFactory textFactory;


    public QuoteService(TextRepository textRepository, TextFactory textFactory) {
        this.textRepository = textRepository;
        this.textFactory = textFactory;
    }

    public ResponseObject createStock(CreateStockDTO payload) {
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
            return new ResponseObject("Textos agregados correctamente a la base de datos.");
        } else {
            return new ResponseObject("No se han hecho cambios a la base de datos.");
        }
    }

    public TextQuoteObject saveAndQuoteText(SaveAndQuoteTextDTO payload) {

        float seniorityDiscount = calculateSeniorityDiscount(payload.getClientEntryDate());
        List<DiscountObject> discounts = new ArrayList<>();
        float total;

        textRepository.save(new TextEntity(payload.text.title, payload.text.getType(), payload.text.basePrice));

        TextObject newTextObject = textFactory.create(payload.text, true);

        if (seniorityDiscount != 1) {
            discounts.add(new DiscountObject("seniority", ((seniorityDiscount) * 100) - 100));
        }

        total = newTextObject.getTotalPrice() * seniorityDiscount;

        return new TextQuoteObject(newTextObject.getTitle(), newTextObject.getType(), newTextObject.getPrice(), discounts, total);
    }


    //    public WholesaleQuoteObject calculateWholesaleQuote(WholeSaleDTO payload) {
//
//        List<BookEntity> bookList = createBooksEntitiesFromIndices(payload.bookIndices);
//        List<NovelEntity> novelList = createNovelsEntitiesFromIndices(payload.novelIndices);
//
//        List<TextObject> bookObjectList =  createBookObjectList(bookList);
//        List<TextObject> novelObjectList =  createNovelObjectList(novelList);
//
//        getTextsSortedAndCombined(bookObjectList, novelObjectList);
//        setQuotes();
//        calculateSeniorityDiscount(payload.getClientEntryDate());
//
//        float nonSeniorityTotal = total;
//        total *= seniorityDiscount;
//        return new WholesaleQuoteObject(booksQuote, novelsQuote, "Subtotal: " + nonSeniorityTotal + " Descuentos: [ Descuento por antigüedad: " + ((seniorityDiscount * 100) - 100) + "% ] Total: " + total);
//    }
//
//    public BudgetSaleQuoteObject calculateBudgetSaleQuote(BudgetSaleDTO payload) {
//
//        List<SaveAndQuoteTextDTO> rawTexts = new ArrayList<>();
//
//        List<BookEntity> bookDb = textRepository.findAll();
//        List<NovelEntity> novelDb = novelRepository.findAll();
//
//        for (BookEntity book : bookDb){
//            rawTexts.add(new SaveAndQuoteTextDTO(book.getTitle(), TextType.BOOK, book.getBasePrice()));
//        }
//        for (NovelEntity novel : novelDb){
//            rawTexts.add(new SaveAndQuoteTextDTO(novel.getTitle(), TextType.NOVEL, novel.getBasePrice()));
//        }
//
//        for (Integer index : payload.textsIndices) {
//            if (index >= 0 && index < rawTexts.size()) {
//                processedTexts.add(rawTexts.get(index));
//            }
//        }
//
//        getCheapestTexts();
//
//        textsSortedAndCombined.sort((a, b) -> Float.compare(b.getBasePrice(), a.getBasePrice()));
//
//        budget = payload.budget;
//
//        int quantityBook = 0;
//        int quantityNovel = 0;
//
//        budget -= cheapestBookObject.getTotalPrice();
//        total += cheapestBookObject.getTotalPrice();
//        quantityBook += 1;
//        budgetQuote.add("Título: " + cheapestBookObject.getTitle() + " - Tipo: " + cheapestBookObject.getClass().getSimpleName() + " Precio: " + cheapestBookObject.getTotalPrice());
//
//        budget -= cheapestNovelObject.getTotalPrice();
//        total += cheapestNovelObject.getTotalPrice();
//        quantityNovel += 1;
//        budgetQuote.add("Título: " + cheapestNovelObject.getTitle() + " - Tipo: " + cheapestNovelObject.getClass().getSimpleName() + " Precio: " + cheapestNovelObject.getTotalPrice());
//
//        while (budget > 0) {
//            if (budget - cheapestBookObject.getTotalPrice() < 0) {
//                break;
//            }
//            budget -= cheapestBookObject.getTotalPrice();
//            total += cheapestBookObject.getTotalPrice();
//            quantityBook += 1;
//            budgetQuote.add("Título: " + cheapestBookObject.getTitle() + " - Tipo: " + cheapestBookObject.getClass().getSimpleName() + " Precio: " + cheapestBookObject.getTotalPrice());
//        }
//
//        calculateSeniorityDiscount(payload.getClientEntryDate());
//        float nonSeniorityTotal = total;
//        total *= seniorityDiscount;
//        return new BudgetSaleQuoteObject(budgetQuote, "Para obtener el máximo ahorro en esta compra con el presupuesto que tienes y usando los textos que nos compartiste, la compra se debe hacer así: Libros a comprar: " + quantityBook + " - Novelas a comprar: " + quantityNovel + " . Y sobrarían " + budget + " del presupuesto que nos diste.");
//    }
//
//    // PRIVATE METHODS
//
//    public List<BookEntity> createBooksEntitiesFromIndices(List<Long> indices) {
//        List<BookEntity> booksEntities = new ArrayList<>();
//        for (Long index : indices) {
//            Optional<BookEntity> optionalProduct = textRepository.findById(index);
//            optionalProduct.ifPresent(booksEntities::add);
//        }
//        return booksEntities;
//    }
//    public List<NovelEntity> createNovelsEntitiesFromIndices(List<Long> indices) {
//        List<NovelEntity> novelsEntities = new ArrayList<>();
//        for (Long index : indices) {
//            Optional<NovelEntity> optionalProduct = novelRepository.findById(index);
//            optionalProduct.ifPresent(novelsEntities::add);
//        }
//        return novelsEntities;
//    }
//    private void getCheapestTexts() {
//        for (SaveAndQuoteTextDTO text : processedTexts) {
//            textsSortedAndCombined.add(textFactory.create( text, true));
//        }
//
//        for (TextObject textObject : textsSortedAndCombined){
//            if (textObject instanceof BookObject) {
//                if (cheapestBookObject == null || textObject.getBasePrice() < cheapestBookObject.getBasePrice()) {
//                    cheapestBookObject = (BookObject) textObject;
//                }
//            } else if (textObject instanceof NovelObject) {
//                if (cheapestNovelObject == null || textObject.getBasePrice() < cheapestNovelObject.getBasePrice()) {
//                    cheapestNovelObject = (NovelObject) textObject;
//                }
//            }
//        }
//    }
//
//    private void setQuotes() {
//        for (int i = 0; i < textsSortedAndCombined.size(); i++) {
//            if (i < 10) {
//                setTextsQuote(textsSortedAndCombined.get(i), true);
//            } else {
//                setTextsQuote(textsSortedAndCombined.get(i), false);
//            }
//        }
//    }
//
//    private void setTextsQuote(TextObject textObject, boolean isRetail) {
//        textObject.setIsRetail(isRetail);
//        if (textObject instanceof BookObject) {
//            booksQuote.add("Título: " + textObject.getTitle() + " - Subtotal : " + textObject.getPrice() + " - Descuentos: [ Porcentaje venta al por mayor: " + (isRetail ? "0" : "0.15%") + " ] -  Precio Final: " + textObject.getTotalPrice());
//            total += textObject.getTotalPrice();
//        } else {
//            novelsQuote.add("Título: " + textObject.getTitle() + " - Subtotal : " + textObject.getPrice() + " - Descuentos: [ Porcentaje venta al por mayor: " + (isRetail ? "0" : "0.15%") + " ] -  Precio Final: " + textObject.getTotalPrice());
//            total += textObject.getTotalPrice();
//        }
//    }
//
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
//
//    private void getTextsSortedAndCombined(List<TextObject> bookObjectList, List<TextObject> novelObjectList) {
//        textsSortedAndCombined.addAll(bookObjectList);
//        textsSortedAndCombined.addAll(novelObjectList);
//        textsSortedAndCombined.sort((a, b) -> Float.compare(b.getBasePrice(), a.getBasePrice()));
//    }
//
//    private List<TextObject> createBookObjectList(List<BookEntity> textEntities){
//        List<TextObject> textObjects = new ArrayList<>();
//        for (TextEntity text : textEntities){
//            textObjects.add(textFactory.create(new SaveAndQuoteTextDTO(text.getTitle(), TextType.BOOK, text.getBasePrice()), false));
//        }
//        return textObjects;
//    }
//
//    private List<TextObject> createNovelObjectList(List<NovelEntity> textEntities){
//        List<TextObject> textObjects = new ArrayList<>();
//        for (TextEntity text : textEntities){
//            textObjects.add(textFactory.create(new SaveAndQuoteTextDTO(text.getTitle(), TextType.NOVEL, text.getBasePrice()), false));
//        }
//        return textObjects;
//    }

}
