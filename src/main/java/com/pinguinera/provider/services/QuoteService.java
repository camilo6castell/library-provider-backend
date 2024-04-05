package com.pinguinera.provider.services;

import com.pinguinera.provider.models.DTOS.BudgetSaleDTO;
import com.pinguinera.provider.models.DTOS.FillDataBaseDTO;
import com.pinguinera.provider.models.DTOS.RetailSaleDTO;
import com.pinguinera.provider.models.DTOS.WholeSaleDTO;
import com.pinguinera.provider.models.objects.quote.BudgetSaleQuoteObject;
import com.pinguinera.provider.models.objects.quote.RetailSaleQuoteObject;
import com.pinguinera.provider.models.objects.quote.WholesaleQuoteObject;
import com.pinguinera.provider.models.objects.response.ResponseObject;
import com.pinguinera.provider.models.objects.text.BookObject;
import com.pinguinera.provider.models.objects.text.NovelObject;
import com.pinguinera.provider.models.objects.text.TextObject;
import com.pinguinera.provider.models.factories.TextFactory;
import com.pinguinera.provider.models.persistence.BookEntity;
import com.pinguinera.provider.models.persistence.NovelEntity;
import com.pinguinera.provider.repositories.BookRepository;
import com.pinguinera.provider.repositories.NovelRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

import java.util.ArrayList;
import java.util.List;


@Service
public class QuoteService {

    private final LocalDate today = LocalDate.now();
    private List<TextObject> textsSortedAndCombined = new ArrayList<>();
    private List<RetailSaleDTO> rawTexts = new ArrayList<>();
    private List<String> booksQuote = new ArrayList<>();
    private List<String> novelsQuote = new ArrayList<>();
    private BookObject cheapestBookObject;
    private NovelObject cheapestNovelObject;
    private float seniorityDiscount;
    private float budget;
    private ArrayList<String> budgetQuote = new ArrayList<>();

    private final BookRepository bookRepository;
    private final NovelRepository novelRepository;
    private final TextFactory textFactory;

    private float total;

    public QuoteService(BookRepository bookRepository, NovelRepository novelRepository, TextFactory textFactory) {
        this.bookRepository = bookRepository;
        this.novelRepository = novelRepository;
        this.textFactory = textFactory;
    }

    public ResponseObject createStock(FillDataBaseDTO payload){
        if (payload.isFillDataBase()){
            ArrayList<BookEntity> bookEntityStock = new ArrayList<>();
            ArrayList<NovelEntity> novelEntityStock = new ArrayList<>();
            bookEntityStock.add(new BookEntity("El origen de las especies", 100));
            bookEntityStock.add(new BookEntity("Breve historia del tiempo", 50));
            bookEntityStock.add(new BookEntity("La gran ilusión", 10));
            bookEntityStock.add(new BookEntity("El significado de la relatividad", 1000));
            bookEntityStock.add(new BookEntity("El mono desnudo", 20));
            bookEntityStock.add(new BookEntity("Crítica de la razón pura", 300));
            bookEntityStock.add(new BookEntity("Sin blanca en París y Londres", 2));
            novelEntityStock.add(new NovelEntity("La rebelión en la granja", 1300));
            novelEntityStock.add(new NovelEntity("1984", 3030));
            novelEntityStock.add(new NovelEntity("Cien años de soledad", 550));
            novelEntityStock.add(new NovelEntity("El Conde de Montecristo", 900));
            novelEntityStock.add(new NovelEntity("Crimen y castigo", 500));
            novelEntityStock.add(new NovelEntity("Crónica de una muerte anunciada", 750));
            bookRepository.saveAll(bookEntityStock);
            novelRepository.saveAll(novelEntityStock);
            return new ResponseObject("Textos agregados correctamente a la base de datos ;)");
        } else {
            return new ResponseObject("No se han hecho cambios a la base de datos. ;)");
        }
    }

    public RetailSaleQuoteObject calculateRetailSaleQuote(RetailSaleDTO payload) {
        TextObject textObject = textFactory.create(payload, true);
        calculateSeniorityDiscount(payload.getClientEntryDate());
        total = textObject.getTotalPrice() * seniorityDiscount;
        return new RetailSaleQuoteObject(textObject.getTitle(), textObject.getClass().getSimpleName(), total);
    }

    public WholesaleQuoteObject calculateWholesaleQuote(WholeSaleDTO payload) {
        getTextsSortedAndCombined(payload.bookObjectList, payload.novelObjectList);
        setQuotes();
        calculateSeniorityDiscount(payload.getClientEntryDate());
        float nonSeniorityTotal = total;
        total *= seniorityDiscount;
        return new WholesaleQuoteObject(booksQuote, novelsQuote, "Subtotal: " + nonSeniorityTotal + " Descuentos: [ Descuento por antigüedad: " + ((seniorityDiscount * 100) - 100) + "% ] Total: " + total);
    }

    public BudgetSaleQuoteObject calculateBudgetSaleQuote(BudgetSaleDTO payload) {
        rawTexts = payload.textList;

        getCheapestTexts();

        textsSortedAndCombined.sort((a, b) -> Float.compare(b.getBasePrice(), a.getBasePrice()));

        budget = payload.budget;

        int quantityBook = 0;
        int quantityNovel = 0;

        budget -= cheapestBookObject.getTotalPrice();
        total += cheapestBookObject.getTotalPrice();
        quantityBook += 1;
        budgetQuote.add("Título: " + cheapestBookObject.getTitle() + " - Tipo: " + cheapestBookObject.getClass().getSimpleName() + " Precio: " + cheapestBookObject.getTotalPrice());

        budget -= cheapestNovelObject.getTotalPrice();
        total += cheapestNovelObject.getTotalPrice();
        quantityNovel += 1;
        budgetQuote.add("Título: " + cheapestNovelObject.getTitle() + " - Tipo: " + cheapestNovelObject.getClass().getSimpleName() + " Precio: " + cheapestNovelObject.getTotalPrice());

        while (budget > 0) {
            if (budget - cheapestBookObject.getTotalPrice() < 0) {
                break;
            }
            budget -= cheapestBookObject.getTotalPrice();
            total += cheapestBookObject.getTotalPrice();
            quantityBook += 1;
            budgetQuote.add("Título: " + cheapestBookObject.getTitle() + " - Tipo: " + cheapestBookObject.getClass().getSimpleName() + " Precio: " + cheapestBookObject.getTotalPrice());
        }

        calculateSeniorityDiscount(payload.getClientEntryDate());
        float nonSeniorityTotal = total;
        total *= seniorityDiscount;
        return new BudgetSaleQuoteObject(budgetQuote, "Para obtener el máximo ahorro en esta compra con el presupuesto que tienes y usando los textos que nos compartiste, la compra se debe hacer así: Libros a comprar: " + quantityBook + " - Novelas a comprar: " + quantityNovel + " . Y sobrarían " + budget + " del presupuesto que nos diste.");
    }

    // PRIVATE METHODS

    private void getCheapestTexts() {
        for (RetailSaleDTO text : rawTexts) {
            textsSortedAndCombined.add(textFactory.create( text, true));
        }

        for (TextObject textObject : textsSortedAndCombined){
            if (textObject instanceof BookObject) {
                if (cheapestBookObject == null || textObject.getBasePrice() < cheapestBookObject.getBasePrice()) {
                    cheapestBookObject = (BookObject) textObject;
                }
            } else if (textObject instanceof NovelObject) {
                if (cheapestNovelObject == null || textObject.getBasePrice() < cheapestNovelObject.getBasePrice()) {
                    cheapestNovelObject = (NovelObject) textObject;
                }
            }
        }
    }

    private void setQuotes() {
        for (int i = 0; i < textsSortedAndCombined.size(); i++) {
            if (i < 10) {
                setTextsQuote(textsSortedAndCombined.get(i), true);
            } else {
                setTextsQuote(textsSortedAndCombined.get(i), false);
            }
        }
    }

    private void setTextsQuote(TextObject textObject, boolean isRetail) {
        textObject.setIsRetail(isRetail);
        if (textObject.getClass().getSimpleName().equals("Book")) {
            booksQuote.add("Título: " + textObject.getTitle() + " - Subtotal : " + textObject.getPrice() + " - Descuentos: [ Porcentaje venta al por mayor: " + (isRetail ? "0" : "0.15%") + " ] -  Precio Final: " + textObject.getTotalPrice());
            total += textObject.getTotalPrice();
        } else {
            novelsQuote.add("Título: " + textObject.getTitle() + " - Subtotal : " + textObject.getPrice() + " - Descuentos: [ Porcentaje venta al por mayor: " + (isRetail ? "0" : "0.15%") + " ] -  Precio Final: " + textObject.getTotalPrice());
            total += textObject.getTotalPrice();
        }
    }

    private void calculateSeniorityDiscount(LocalDate clientEntryDate) {
        int years = Period.between(clientEntryDate, today).getYears();
        if (years < 1) {
            seniorityDiscount = 1;
        } else if (years <= 2) {
            seniorityDiscount = 0.88F;
        } else {
            seniorityDiscount = 0.83F;
        }
    }

    private void getTextsSortedAndCombined(List<BookObject> bookObjects, List<NovelObject> novelObjects) {
        textsSortedAndCombined.addAll(bookObjects);
        textsSortedAndCombined.addAll(novelObjects);
        textsSortedAndCombined.sort((a, b) -> Float.compare(b.getBasePrice(), a.getBasePrice()));
    }

}
