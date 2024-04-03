package com.pinguinera.provider.services;

import com.pinguinera.provider.models.DTOS.BudgetSaleDTO;
import com.pinguinera.provider.models.DTOS.RetailSaleDTO;
import com.pinguinera.provider.models.DTOS.WholeSaleDTO;
import com.pinguinera.provider.models.entities.quote.BudgetSaleQuote;
import com.pinguinera.provider.models.entities.quote.RetailSaleQuote;
import com.pinguinera.provider.models.entities.quote.WholesaleQuote;
import com.pinguinera.provider.models.entities.text.Book;
import com.pinguinera.provider.models.entities.text.Novel;
import com.pinguinera.provider.models.entities.text.Text;
import com.pinguinera.provider.models.factories.TextFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

import java.util.ArrayList;
import java.util.List;


@Service
public class QuoteService {
    private final TextFactory textFactory;
    private final LocalDate today = LocalDate.now();
    private List<Text> textsSortedAndCombined = new ArrayList<>();
    private List<RetailSaleDTO> rawTexts = new ArrayList<>();
    private List<String> booksQuote = new ArrayList<>();
    ;
    private List<String> novelsQuote = new ArrayList<>();
    ;
    private Book cheapestBook;
    private Novel cheapestNovel;
    private float seniorityDiscount;
    private float budget;
    private ArrayList<String> budgetQuote = new ArrayList<>();
    private float total;

    public QuoteService(TextFactory textFactory) {
        this.textFactory = textFactory;
    }

    public RetailSaleQuote calculateRetailSaleQuote(RetailSaleDTO payload) {
        Text text = textFactory.create(payload, true);
        calculateSeniorityDiscount(payload.getClientEntryDate());
        total = text.getTotalPrice() * seniorityDiscount;
        return new RetailSaleQuote(text.getTitle(), text.getClass().getSimpleName(), total);
    }

    public WholesaleQuote calculateWholesaleQuote(WholeSaleDTO payload) {
        getTextsSortedAndCombined(payload.bookList, payload.novelList);
        setQuotes();
        calculateSeniorityDiscount(payload.getClientEntryDate());
        float nonSeniorityTotal = total;
        total *= seniorityDiscount;
        return new WholesaleQuote(booksQuote, novelsQuote, "Subtotal: " + nonSeniorityTotal + " Descuentos: [ Descuento por antigüedad: " + ((seniorityDiscount * 100) - 100) + "% ] Total: " + total);
    }

    public BudgetSaleQuote calculateBudgetSaleQuote(BudgetSaleDTO payload) {
        rawTexts = payload.textList;

        getCheapestTexts();

        textsSortedAndCombined.sort((a, b) -> Float.compare(b.getBasePrice(), a.getBasePrice()));

        budget = payload.budget;

        int quantityBook = 0;
        int quantityNovel = 0;



        budget -= cheapestBook.getTotalPrice();
        total += cheapestBook.getTotalPrice();
        quantityBook += 1;
        budgetQuote.add("Título: " + cheapestBook.getTitle() + " - Tipo: " + cheapestBook.getClass().getSimpleName() + " Precio: " + cheapestBook.getTotalPrice());

        budget -= cheapestNovel.getTotalPrice();
        total += cheapestNovel.getTotalPrice();
        quantityNovel += 1;
        budgetQuote.add("Título: " + cheapestNovel.getTitle() + " - Tipo: " + cheapestNovel.getClass().getSimpleName() + " Precio: " + cheapestNovel.getTotalPrice());

        while (budget > 0) {
            if (budget - cheapestBook.getTotalPrice() < 0) {
                break;
            }
            budget -= cheapestBook.getTotalPrice();
            total += cheapestBook.getTotalPrice();
            quantityBook += 1;
            budgetQuote.add("Título: " + cheapestBook.getTitle() + " - Tipo: " + cheapestBook.getClass().getSimpleName() + " Precio: " + cheapestBook.getTotalPrice());
        }

        calculateSeniorityDiscount(payload.getClientEntryDate());
        float nonSeniorityTotal = total;
        total *= seniorityDiscount;
        return new BudgetSaleQuote(budgetQuote, "Para obtener el máximo ahorro en esta compra con el presupuesto que tienes y usando los textos que nos compartiste, la compra se debe hacer así: Libros a comprar: " + quantityBook + " - Novelas a comprar: " + quantityNovel + " . Y sobrarían " + budget + " del presupuesto que nos diste.");
    }

    private void getCheapestTexts() {
        for (RetailSaleDTO text : rawTexts) {
            textsSortedAndCombined.add(textFactory.create( text, true));
        }

        for (Text text : textsSortedAndCombined){
            if (text instanceof Book) {
                if (cheapestBook == null || text.getBasePrice() < cheapestBook.getBasePrice()) {
                    cheapestBook = (Book) text;
                }
            } else if (text instanceof Novel) {
                if (cheapestNovel == null || text.getBasePrice() < cheapestNovel.getBasePrice()) {
                    cheapestNovel = (Novel) text;
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

    private void setTextsQuote(Text text, boolean isRetail) {
        text.setIsRetail(isRetail);
        if (text.getClass().getSimpleName().equals("Book")) {
            booksQuote.add("Título: " + text.getTitle() + " - Subtotal : " + text.getPrice() + " - Descuentos: [ Porcentaje venta al por mayor: " + (isRetail ? "0" : "0.15%") + " ] -  Precio Final: " + text.getTotalPrice());
            total += text.getTotalPrice();
        } else {
            novelsQuote.add("Título: " + text.getTitle() + " - Subtotal : " + text.getPrice() + " - Descuentos: [ Porcentaje venta al por mayor: " + (isRetail ? "0" : "0.15%") + " ] -  Precio Final: " + text.getTotalPrice());
            total += text.getTotalPrice();
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

    private void getTextsSortedAndCombined(List<Book> books, List<Novel> novels) {
        textsSortedAndCombined.addAll(books);
        textsSortedAndCombined.addAll(novels);
        textsSortedAndCombined.sort((a, b) -> Float.compare(b.getBasePrice(), a.getBasePrice()));
    }

}
