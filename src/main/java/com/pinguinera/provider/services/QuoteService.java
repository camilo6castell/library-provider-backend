package com.pinguinera.provider.services;

import com.pinguinera.provider.models.DTOS.RetailSaleDTO;
import com.pinguinera.provider.models.DTOS.WholeSaleDTO;
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
import java.util.Collections;
import java.util.List;


@Service
public class QuoteService {
    private final TextFactory textFactory;
    private final LocalDate today = LocalDate.now();
    private List<Text> textsSortedAndCombined = new ArrayList<>();
    private List<String> booksQuote = new ArrayList<>();;
    private List<String> novelsQuote = new ArrayList<>();;
    private float seniorityDiscount;
    private float total;

    public QuoteService(TextFactory textFactory){
        this.textFactory = textFactory;
    }

    public RetailSaleQuote calculateRetailSaleQuote(RetailSaleDTO payload){
        Text text = textFactory.create(payload, true);
        calculateSeniorityDiscount(payload.getClientEntryDate());
        total = text.getTotalPrice()*seniorityDiscount;
        return new RetailSaleQuote(text.getTitle(), text.getClass().getSimpleName(), total);
    }

    public WholesaleQuote calculateWholesaleQuote(WholeSaleDTO payload) {
        getTextsSortedAndCombined(payload.bookList, payload.novelList);
        setQuotes();
        calculateSeniorityDiscount(payload.getClientEntryDate());
        float nonSeniorityTotal = total;
        total *= seniorityDiscount;
        return new WholesaleQuote(booksQuote, novelsQuote, "Subtotal: " + nonSeniorityTotal + " Descuentos: [ Descuento por antigüedad: " + ((seniorityDiscount * 100)-100) +"% ] Total: " + total );
    }

    private void setQuotes(){
        for (int i = 0; i < textsSortedAndCombined.size(); i++) {
            if (i < 10){
                setTextsQuote(textsSortedAndCombined.get(i), true);
            } else {
                setTextsQuote(textsSortedAndCombined.get(i), false);
            }
        }
    }

    private void setTextsQuote(Text text, boolean isRetail){
        text.setIsRetail(isRetail);
        if (text.getClass().getSimpleName().equals("Book")){
            booksQuote.add("Título: " + text.getTitle() + " - Subtotal : " + text.getPrice() + " - Descuentos: [ Porcentaje venta al por mayor: " + (isRetail ? "0" : "0.15%") + " ] -  Precio Final: " + text.getTotalPrice());
            total += text.getTotalPrice();
        } else {
            novelsQuote.add("Título: " + text.getTitle() + " - Subtotal : " + text.getPrice() + " - Descuentos: [ Porcentaje venta al por mayor: " + (isRetail ? "0" : "0.15%") + " ] -  Precio Final: " + text.getTotalPrice());
            total += text.getTotalPrice();
        }
    }

    private void calculateSeniorityDiscount(LocalDate clientEntryDate){
        int years = Period.between(clientEntryDate, today).getYears();
        if (years < 1){
            seniorityDiscount = 1;
        } else if (years <= 2){
            seniorityDiscount = 0.88F;
        } else {
            seniorityDiscount = 0.83F;
        }
    }

    private void getTextsSortedAndCombined(List<Book> books, List<Novel> novels){
        textsSortedAndCombined.addAll(books);
        textsSortedAndCombined.addAll(novels);
        textsSortedAndCombined.sort((a, b) -> Float.compare(b.getBasePrice(), a.getBasePrice()));
    }

}
